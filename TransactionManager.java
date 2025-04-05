import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

/**
 * Implementasi manajemen transaksi
 * REQUIRED: ICategoryManager (categoryExists)
 */
public class TransactionManager implements ITransactionManager {
    private ArrayList<Transaction> transactions;
    private ICategoryManager categoryManager; // REQUIRED INTERFACE
    private int nextId;

    public TransactionManager() {
        this.transactions = new ArrayList<>();
        this.nextId = 1;
        this.categoryManager = null;
    }

    /**
     * Metode untuk koneksi ke komponen CategoryManager
     * 
     * @param categoryManager komponen kategori manager
     */
    public void connectTo(ICategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    @Override
    public int addTransaction(String description, double amount, int categoryId, String date) {
        if (description == null || date == null || !isValidDate(date)) {
            return -1;
        }

        if (categoryManager == null || !categoryManager.categoryExists(categoryId)) {
            return -1;
        }

        Transaction transaction = new Transaction(nextId, description, amount, categoryId, date);
        transactions.add(transaction);
        return nextId++;
    }

    @Override
    public Transaction[] getAllTransactions() {
        return transactions.toArray(new Transaction[0]);
    }

    @Override
    public Transaction[] getTransactionsByDateRange(String startDate, String endDate) {
        if (startDate == null || endDate == null ||
                !isValidDate(startDate) || !isValidDate(endDate)) {
            return new Transaction[0];
        }

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        if (start.isAfter(end)) {
            return new Transaction[0];
        }

        List<Transaction> result = new ArrayList<>();

        for (Transaction t : transactions) {
            LocalDate transactionDate = LocalDate.parse(t.getDate());
            if ((transactionDate.isEqual(start) || transactionDate.isAfter(start)) &&
                    (transactionDate.isEqual(end) || transactionDate.isBefore(end))) {
                result.add(t);
            }
        }

        return result.toArray(new Transaction[0]);
    }

    @Override
    public double getTotalIncome() {
        double total = 0;
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) {
                total += t.getAmount();
            }
        }
        return total;
    }

    @Override
    public double getTotalExpense() {
        double total = 0;
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                total += Math.abs(t.getAmount());
            }
        }
        return total;
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

    /**
     * Memeriksa apakah transaksi dengan ID tertentu ada
     */
    private boolean transactionExists(int transactionId) {
        for (Transaction t : transactions) {
            if (t.getId() == transactionId) {
                return true;
            }
        }
        return false;
    }
}