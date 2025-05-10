/**
 * INTERFACE PROVIDED
 * Interface provided untuk manajemen transaksi keuangan
 */
public interface ITransactionMgt {
    /**
     * Menambahkan transaksi baru ke sistem
     * @param description deskripsi transaksi
     * @param amount jumlah uang (positif untuk pemasukan, negatif untuk pengeluaran)
     * @param categoryId id kategori transaksi
     * @param date tanggal transaksi dalam format "yyyy-MM-dd"
     * @return ID transaksi jika berhasil, -1 jika gagal
     *
     * pre: description != null
     * pre: date != null && isValidDate(date)
     * pre: categoryExists(categoryId) // REQUIRED dari ICategoryManager
     *
     * post: result > 0 => transactionExists(result)
     */
    public int addTransaction(String description, double amount, int categoryId, String date);

    /**
     * Mendapatkan semua transaksi dalam sistem
     * @return array dari semua transaksi
     *
     * post: result != null
     */
    public Transaction[] getAllTransactions();

    /**
     * Mendapatkan transaksi berdasarkan rentang tanggal
     * @param startDate tanggal awal dalam format "yyyy-MM-dd"
     * @param endDate tanggal akhir dalam format "yyyy-MM-dd"
     * @return array dari transaksi yang sesuai dengan rentang tanggal
     *
     * pre: startDate != null && endDate != null
     * pre: isValidDate(startDate) && isValidDate(endDate)
     * pre: startDate <= endDate (secara kronologis)
     *
     * post: result != null
     */
    public Transaction[] getTransactionsByDateRange(String startDate, String endDate);

    /**
     * Mendapatkan total pendapatan (transaksi dengan amount positif)
     * @return total pendapatan
     *
     * post: result >= 0
     */
    public double getTotalIncome();

    /**
     * Mendapatkan total pengeluaran (transaksi dengan amount negatif)
     * @return total pengeluaran (nilai positif)
     *
     * post: result >= 0
     */
    public double getTotalExpense();
}