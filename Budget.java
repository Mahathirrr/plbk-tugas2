/**
 * Kelas model untuk menyimpan data anggaran
 */
public class Budget {
    private int categoryId;
    private double amount;
    private String period;

    public Budget(int categoryId, double amount, String period) {
        this.categoryId = categoryId;
        this.amount = amount;
        this.period = period;
    }

    // Getters
    public int getCategoryId() {
        return categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPeriod() {
        return period;
    }
}
