/**
 * INTERFACE PROVIDED
 * Interface provided untuk manajemen anggaran
 */
public interface IBudgetManager {
    /**
     * Menetapkan anggaran untuk kategori tertentu
     * 
     * @param categoryId ID kategori
     * @param amount     jumlah anggaran
     * @param period     periode anggaran ("MONTHLY", "WEEKLY", dll)
     * @return true jika berhasil, false jika gagal
     *
     *         pre: amount > 0
     *         pre: period != null
     *         pre: categoryExists(categoryId) // REQUIRED dari ICategoryManager
     *
     *         post: result => getBudgetForCategory(categoryId) == amount
     */
    public boolean setBudget(int categoryId, double amount, String period);

    /**
     * Mendapatkan anggaran untuk kategori tertentu
     * 
     * @param categoryId ID kategori
     * @return jumlah anggaran, 0 jika tidak ada anggaran
     *
     *         pre: categoryExists(categoryId) // REQUIRED dari ICategoryManager
     *
     *         post: result >= 0
     */
    public double getBudgetForCategory(int categoryId);

    /**
     * Memeriksa persentase penggunaan anggaran
     * 
     * @param categoryId ID kategori
     * @return persentase penggunaan (0-100+)
     *
     *         pre: categoryExists(categoryId) // REQUIRED dari ICategoryManager
     *         pre: getBudgetForCategory(categoryId) > 0
     *
     *         post: result >= 0
     */
    public double getBudgetUsagePercentage(int categoryId);
}
