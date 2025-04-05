/**
 * Kelas model untuk menyimpan data kategori
 */
public class Category {
    private int id;
    private String name;
    private String type; // "INCOME" atau "EXPENSE"

    public Category(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}