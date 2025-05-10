import java.util.HashMap;

/**
 * Implementasi manajemen anggaran
 * REQUIRED: ICategoryMgt (categoryExists, getCategoryById)
 * REQUIRED: ITransactionMgt (getAllTransactions)
 */
public class BudgetManager implements IBudgetMgt {
    private HashMap<Integer, Budget> budgets;
    private ICategoryMgt categoryManager; // REQUIRED INTERFACE
    private ITransactionMgt transactionManager; // REQUIRED INTERFACE

    public BudgetManager() {
        this.budgets = new HashMap<>();
        this.categoryManager = null;
        this.transactionManager = null;
    }

    /**
     * Metode untuk koneksi ke komponen CategoryManager
     *
     * @param categoryManager komponen kategori manager
     */
    public void connectToCategoryManager(ICategoryMgt categoryManager) {
        this.categoryManager = categoryManager;
    }

    /**
     * Metode untuk koneksi ke komponen TransactionManager
     *
     * @param transactionManager komponen transaksi manager
     */
    public void connectToTransactionManager(ITransactionMgt transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public boolean setBudget(int categoryId, double amount, String period) {
        if (amount <= 0 || period == null || categoryManager == null ||
                !categoryManager.categoryExists(categoryId)) {
            return false;
        }

        Budget budget = new Budget(categoryId, amount, period);
        budgets.put(categoryId, budget);
        return true;
    }

    @Override
    public double getBudgetForCategory(int categoryId) {
        if (categoryManager == null || !categoryManager.categoryExists(categoryId)) {
            return 0;
        }

        Budget budget = budgets.get(categoryId);
        return (budget != null) ? budget.getAmount() : 0;
    }

    @Override
    public double getBudgetUsagePercentage(int categoryId) {
        if (categoryManager == null || !categoryManager.categoryExists(categoryId) ||
                transactionManager == null) {
            return 0;
        }

        double budget = getBudgetForCategory(categoryId);
        if (budget <= 0) {
            return 0;
        }

        Category category = categoryManager.getCategoryById(categoryId);
        if (category == null || !category.getType().equals("EXPENSE")) {
            return 0;
        }

        // Hitung total pengeluaran untuk kategori ini
        double categoryExpense = 0;
        for (Transaction t : transactionManager.getAllTransactions()) {
            if (t.getCategoryId() == categoryId && t.getAmount() < 0) {
                categoryExpense += Math.abs(t.getAmount());
            }
        }

        return (categoryExpense / budget) * 100;
    }
}
