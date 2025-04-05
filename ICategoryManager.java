/**
 * INTERFACE PROVIDED
 * Interface provided untuk manajemen kategori
 */
public interface ICategoryManager {
    /**
     * Menambahkan kategori baru
     * 
     * @param name nama kategori
     * @param type tipe kategori ("INCOME" atau "EXPENSE")
     * @return ID kategori jika berhasil, -1 jika gagal
     *
     *         pre: name != null && name.length() > 0
     *         pre: type != null && (type.equals("INCOME") ||
     *         type.equals("EXPENSE"))
     *         pre: !categoryNameExists(name)
     *
     *         post: result > 0 => categoryExists(result)
     */
    public int addCategory(String name, String type);

    /**
     * Mendapatkan semua kategori dalam sistem
     * 
     * @return array dari semua kategori
     *
     *         post: result != null
     */
    public Category[] getAllCategories();

    /**
     * Memeriksa apakah kategori dengan ID tertentu ada
     * 
     * @param categoryId ID kategori
     * @return true jika kategori ada, false jika tidak
     */
    public boolean categoryExists(int categoryId);

    /**
     * Mendapatkan informasi kategori berdasarkan ID
     * 
     * @param categoryId ID kategori
     * @return objek Category jika ditemukan, null jika tidak
     *
     *         pre: categoryExists(categoryId)
     *
     *         post: result != null
     */
    public Category getCategoryById(int categoryId);
}