import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementasi generator laporan
 * REQUIRED: ITransactionManager (getTransactionsByDateRange)
 * REQUIRED: ICategoryManager (getCategoryById)
 */
public class ReportGenerator implements IReportGenerator {
    private ITransactionManager transactionManager; // REQUIRED INTERFACE
    private ICategoryManager categoryManager; // REQUIRED INTERFACE

    public ReportGenerator() {
        this.transactionManager = null;
        this.categoryManager = null;
    }

    /**
     * Metode untuk koneksi ke komponen TransactionManager
     *
     * @param transactionManager komponen transaksi manager
     */
    public void connectToTransactionManager(ITransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * Metode untuk koneksi ke komponen CategoryManager
     *
     * @param categoryManager komponen kategori manager
     */
    public void connectToCategoryManager(ICategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    @Override
    public String generateExpenseReportByCategory(String startDate, String endDate) {
        if (transactionManager == null || categoryManager == null ||
                startDate == null || endDate == null ||
                !isValidDate(startDate) || !isValidDate(endDate)) {
            return "Tidak dapat membuat laporan.";
        }

        Transaction[] transactions = transactionManager.getTransactionsByDateRange(startDate, endDate);

        // Map untuk menyimpan total pengeluaran per kategori
        Map<Integer, Double> categoryExpenses = new HashMap<>();

        // Hitung total pengeluaran per kategori
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) { // Pengeluaran
                int categoryId = t.getCategoryId();
                double expense = Math.abs(t.getAmount());

                categoryExpenses.put(categoryId,
                        categoryExpenses.getOrDefault(categoryId, 0.0) + expense);
            }
        }

        // Buat laporan
        StringBuilder report = new StringBuilder();
        report.append("LAPORAN PENGELUARAN PER KATEGORI\n");
        report.append("Periode: ").append(startDate).append(" sampai ").append(endDate).append("\n\n");
        report.append("Kategori | Jumlah Pengeluaran\n");
        report.append("------------------------------\n");

        double totalExpense = 0;

        for (Map.Entry<Integer, Double> entry : categoryExpenses.entrySet()) {
            int categoryId = entry.getKey();
            double expense = entry.getValue();
            Category category = categoryManager.getCategoryById(categoryId);

            if (category != null) {
                report.append(category.getName())
                        .append(" | Rp.")
                        .append(String.format("%.0f", expense))
                        .append("\n");

                totalExpense += expense;
            }
        }

        report.append("------------------------------\n");
        report.append("TOTAL | Rp.").append(String.format("%.0f", totalExpense)).append("\n");

        return report.toString();
    }

    @Override
    public String generateIncomeVsExpenseReport(String startDate, String endDate) {
        if (transactionManager == null || categoryManager == null ||
                startDate == null || endDate == null ||
                !isValidDate(startDate) || !isValidDate(endDate)) {
            return "Tidak dapat membuat laporan.";
        }

        Transaction[] transactions = transactionManager.getTransactionsByDateRange(startDate, endDate);

        double totalIncome = 0;
        double totalExpense = 0;

        // Hitung total pendapatan dan pengeluaran
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) { // Pendapatan
                totalIncome += t.getAmount();
            } else { // Pengeluaran
                totalExpense += Math.abs(t.getAmount());
            }
        }

        // Buat laporan
        StringBuilder report = new StringBuilder();
        report.append("LAPORAN PENDAPATAN VS PENGELUARAN\n");
        report.append("Periode: ").append(startDate).append(" sampai ").append(endDate).append("\n\n");

        report.append("Total Pendapatan: Rp.").append(String.format("%.0f", totalIncome)).append("\n");
        report.append("Total Pengeluaran: Rp.").append(String.format("%.0f", totalExpense)).append("\n");

        double balance = totalIncome - totalExpense;
        report.append("Saldo: Rp.").append(String.format("%.0f", balance)).append("\n");

        report.append("\nStatus: ");
        if (balance > 0) {
            report.append("SURPLUS");
        } else if (balance < 0) {
            report.append("DEFISIT");
        } else {
            report.append("SEIMBANG");
        }

        return report.toString();
    }

    /**
     * Metode helper untuk validasi format tanggal
     */
    private boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
