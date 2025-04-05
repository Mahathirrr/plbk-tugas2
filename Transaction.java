/**
 * Kelas model untuk menyimpan data transaksi
 */
public class Transaction {
    private int id;
    private String description;
    private double amount;
    private int categoryId;
    private String date;

    public Transaction(int id, String description, double amount, int categoryId, String date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.categoryId = categoryId;
        this.date = date;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Transaction #" + id + ": " + description + " (Rp." +
                String.format("%.0f", amount) + ") on " + date;
    }
}
