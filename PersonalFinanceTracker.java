import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Aplikasi utama Personal Finance Tracker
 */
public class PersonalFinanceTracker {
    private ICategoryMgt categoryManager;
    private ITransactionMgt transactionManager;
    private IBudgetMgt budgetManager;
    private IReportGenerator reportGenerator;
    private Scanner scanner;

    public PersonalFinanceTracker() {
        // Inisialisasi komponen-komponen
        this.categoryManager = new CategoryManager();
        this.transactionManager = new TransactionManager();
        this.budgetManager = new BudgetManager();
        this.reportGenerator = new ReportGenerator();
        this.scanner = new Scanner(System.in);

        // Melakukan koneksi antar komponen
        ((TransactionManager) transactionManager).connectTo(categoryManager);
        ((BudgetManager) budgetManager).connectToCategoryManager(categoryManager);
        ((BudgetManager) budgetManager).connectToTransactionManager(transactionManager);
        ((ReportGenerator) reportGenerator).connectToCategoryManager(categoryManager);
        ((ReportGenerator) reportGenerator).connectToTransactionManager(transactionManager);
    }

    /**
     * Menampilkan menu utama
     */
    public void showMainMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n==== PERSONAL FINANCE TRACKER ====");
            System.out.println("1. Tambah Transaksi");
            System.out.println("2. Lihat Semua Transaksi");
            System.out.println("3. Tambah Kategori");
            System.out.println("4. Lihat Semua Kategori");
            System.out.println("5. Tetapkan Anggaran");
            System.out.println("6. Lihat Penggunaan Anggaran");
            System.out.println("7. Lihat Laporan Pengeluaran per Kategori");
            System.out.println("8. Lihat Laporan Pendapatan vs Pengeluaran");
            System.out.println("0. Keluar");
            System.out.print("Pilihan Anda: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (Exception e) {
                scanner.nextLine(); // Consume invalid input
            }

            switch (choice) {
                case 1:
                    addTransaction();
                    break;
                case 2:
                    viewAllTransactions();
                    break;
                case 3:
                    addCategory();
                    break;
                case 4:
                    viewAllCategories();
                    break;
                case 5:
                    setBudget();
                    break;
                case 6:
                    viewBudgetUsage();
                    break;
                case 7:
                    generateExpenseReport();
                    break;
                case 8:
                    generateIncomeVsExpenseReport();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Terima kasih! Sampai jumpa.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    /**
     * Menambahkan transaksi baru
     */
    private void addTransaction() {
        System.out.println("\n==== TAMBAH TRANSAKSI ====");

        // Tampilkan kategori yang tersedia
        viewAllCategories();

        System.out.print("Deskripsi transaksi: ");
        String description = scanner.nextLine();

        System.out.print("Jumlah (positif untuk pendapatan, negatif untuk pengeluaran): ");
        double amount = 0;
        try {
            amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
        } catch (Exception e) {
            System.out.println("Input tidak valid. Transaksi dibatalkan.");
            scanner.nextLine(); // Consume invalid input
            return;
        }

        System.out.print("ID Kategori: ");
        int categoryId = 0;
        try {
            categoryId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (Exception e) {
            System.out.println("Input tidak valid. Transaksi dibatalkan.");
            scanner.nextLine(); // Consume invalid input
            return;
        }

        // Gunakan tanggal hari ini sebagai default
        String date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        int result = transactionManager.addTransaction(description, amount, categoryId, date);

        if (result > 0) {
            System.out.println("Transaksi berhasil ditambahkan dengan ID: " + result);
        } else {
            System.out.println("Gagal menambahkan transaksi. Pastikan ID kategori valid.");
        }
    }

    /**
     * Menampilkan semua transaksi
     */
    private void viewAllTransactions() {
        System.out.println("\n==== SEMUA TRANSAKSI ====");

        Transaction[] transactions = transactionManager.getAllTransactions();

        if (transactions.length == 0) {
            System.out.println("Belum ada transaksi.");
            return;
        }

        for (Transaction t : transactions) {
            Category category = categoryManager.getCategoryById(t.getCategoryId());
            String categoryName = (category != null) ? category.getName() : "Kategori tidak diketahui";

            String amountStr = (t.getAmount() >= 0) ? "+Rp." + String.format("%.0f", t.getAmount())
                    : "-Rp." + String.format("%.0f", Math.abs(t.getAmount()));

            System.out.println(t.getId() + ". " + t.getDescription() + " (" + categoryName + ") " +
                    amountStr + " pada " + t.getDate());
        }

        System.out.println("\nTotal Pendapatan: Rp." + String.format("%.0f", transactionManager.getTotalIncome()));
        System.out.println("Total Pengeluaran: Rp." + String.format("%.0f", transactionManager.getTotalExpense()));
    }

    /**
     * Menambahkan kategori baru
     */
    private void addCategory() {
        System.out.println("\n==== TAMBAH KATEGORI ====");

        System.out.print("Nama kategori: ");
        String name = scanner.nextLine();

        System.out.println("Tipe kategori:");
        System.out.println("1. INCOME (Pendapatan)");
        System.out.println("2. EXPENSE (Pengeluaran)");
        System.out.print("Pilihan Anda (1/2): ");

        int typeChoice = 0;
        try {
            typeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (Exception e) {
            System.out.println("Input tidak valid. Kategori dibatalkan.");
            scanner.nextLine(); // Consume invalid input
            return;
        }

        String type = (typeChoice == 1) ? "INCOME" : "EXPENSE";

        int result = categoryManager.addCategory(name, type);

        if (result > 0) {
            System.out.println("Kategori berhasil ditambahkan dengan ID: " + result);
        } else {
            System.out.println("Gagal menambahkan kategori. Mungkin nama sudah digunakan.");
        }
    }

    /**
     * Menampilkan semua kategori
     */
    private void viewAllCategories() {
        System.out.println("\n==== SEMUA KATEGORI ====");

        Category[] categories = categoryManager.getAllCategories();

        if (categories.length == 0) {
            System.out.println("Belum ada kategori.");
            return;
        }

        System.out.println("ID | Nama Kategori | Tipe");
        System.out.println("-------------------------");
        for (Category c : categories) {
            System.out.println(c.getId() + " | " + c.getName() + " | " + c.getType());
        }
    }

    /**
     * Menetapkan anggaran untuk kategori
     */
    private void setBudget() {
        System.out.println("\n==== TETAPKAN ANGGARAN ====");

        // Tampilkan kategori pengeluaran
        System.out.println("Kategori Pengeluaran:");
        Category[] categories = categoryManager.getAllCategories();
        boolean hasExpenseCategory = false;

        for (Category c : categories) {
            if (c.getType().equals("EXPENSE")) {
                System.out.println(c.getId() + ". " + c.getName());
                hasExpenseCategory = true;
            }
        }

        if (!hasExpenseCategory) {
            System.out.println("Tidak ada kategori pengeluaran.");
            return;
        }

        System.out.print("ID Kategori: ");
        int categoryId = 0;
        try {
            categoryId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (Exception e) {
            System.out.println("Input tidak valid.");
            scanner.nextLine(); // Consume invalid input
            return;
        }

        // Verifikasi kategori
        Category category = categoryManager.getCategoryById(categoryId);
        if (category == null || !category.getType().equals("EXPENSE")) {
            System.out.println("Kategori tidak valid atau bukan kategori pengeluaran.");
            return;
        }

        System.out.print("Jumlah anggaran: Rp.");
        double amount = 0;
        try {
            amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
        } catch (Exception e) {
            System.out.println("Input tidak valid.");
            scanner.nextLine(); // Consume invalid input
            return;
        }

        // Periode anggaran selalu bulanan untuk kesederhanaan
        String period = "MONTHLY";

        boolean result = budgetManager.setBudget(categoryId, amount, period);

        if (result) {
            System.out.println("Anggaran berhasil ditetapkan untuk " + category.getName() + ": Rp." +
                    String.format("%.0f", amount) + " per bulan.");
        } else {
            System.out.println("Gagal menetapkan anggaran.");
        }
    }

    /**
     * Melihat penggunaan anggaran
     */
    private void viewBudgetUsage() {
        System.out.println("\n==== PENGGUNAAN ANGGARAN ====");

        // Tampilkan kategori pengeluaran yang memiliki anggaran
        Category[] categories = categoryManager.getAllCategories();
        boolean hasBudget = false;

        System.out.println("Kategori | Anggaran | Penggunaan | Persentase");
        System.out.println("-------------------------------------------");

        for (Category c : categories) {
            if (c.getType().equals("EXPENSE")) {
                double budget = budgetManager.getBudgetForCategory(c.getId());
                if (budget > 0) {
                    double usagePercentage = budgetManager.getBudgetUsagePercentage(c.getId());
                    double usage = (usagePercentage / 100) * budget;

                    System.out.println(c.getName() + " | Rp." + String.format("%.0f", budget) +
                            " | Rp." + String.format("%.0f", usage) +
                            " | " + String.format("%.1f", usagePercentage) + "%");
                    hasBudget = true;
                }
            }
        }

        if (!hasBudget) {
            System.out.println("Belum ada anggaran yang ditetapkan.");
        }
    }

    /**
     * Membuat laporan pengeluaran per kategori
     */
    private void generateExpenseReport() {
        System.out.println("\n==== LAPORAN PENGELUARAN PER KATEGORI ====");

        String startDate = promptForDate("Tanggal awal (yyyy-MM-dd) atau tekan enter untuk bulan ini: ");
        String endDate = promptForDate("Tanggal akhir (yyyy-MM-dd) atau tekan enter untuk hari ini: ");

        String report = reportGenerator.generateExpenseReportByCategory(startDate, endDate);
        System.out.println("\n" + report);
    }

    /**
     * Membuat laporan pendapatan vs pengeluaran
     */
    private void generateIncomeVsExpenseReport() {
        System.out.println("\n==== LAPORAN PENDAPATAN VS PENGELUARAN ====");

        String startDate = promptForDate("Tanggal awal (yyyy-MM-dd) atau tekan enter untuk bulan ini: ");
        String endDate = promptForDate("Tanggal akhir (yyyy-MM-dd) atau tekan enter untuk hari ini: ");

        String report = reportGenerator.generateIncomeVsExpenseReport(startDate, endDate);
        System.out.println("\n" + report);
    }

    /**
     * Meminta input tanggal dengan nilai default
     */
    private String promptForDate(String message) {
        System.out.print(message);
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            if (message.contains("awal")) {
                // Default untuk tanggal awal: awal bulan ini
                return LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
            } else {
                // Default untuk tanggal akhir: hari ini
                return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
            }
        }

        // Validasi format tanggal
        try {
            LocalDate.parse(input);
            return input;
        } catch (Exception e) {
            System.out.println("Format tanggal tidak valid. Menggunakan tanggal default.");
            if (message.contains("awal")) {
                return LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
            } else {
                return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
            }
        }
    }

    /**
     * Entry point aplikasi
     */
    public static void main(String[] args) {
        PersonalFinanceTracker app = new PersonalFinanceTracker();
        app.showMainMenu();
    }
}
