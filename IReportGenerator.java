/**
 * INTERFACE PROVIDED
 * Interface provided untuk pembuatan laporan
 */
public interface IReportGenerator {
    /**
     * Membuat laporan pengeluaran berdasarkan kategori
     * 
     * @param startDate tanggal awal dalam format "yyyy-MM-dd"
     * @param endDate   tanggal akhir dalam format "yyyy-MM-dd"
     * @return laporan dalam bentuk string
     *
     *         pre: startDate != null && endDate != null
     *         pre: isValidDate(startDate) && isValidDate(endDate)
     *         pre: startDate <= endDate (secara kronologis)
     *
     *         post: result != null
     */
    public String generateExpenseReportByCategory(String startDate, String endDate);

    /**
     * Membuat laporan pendapatan vs pengeluaran
     * 
     * @param startDate tanggal awal dalam format "yyyy-MM-dd"
     * @param endDate   tanggal akhir dalam format "yyyy-MM-dd"
     * @return laporan dalam bentuk string
     *
     *         pre: startDate != null && endDate != null
     *         pre: isValidDate(startDate) && isValidDate(endDate)
     *         pre: startDate <= endDate (secara kronologis)
     *
     *         post: result != null
     */
    public String generateIncomeVsExpenseReport(String startDate, String endDate);
}