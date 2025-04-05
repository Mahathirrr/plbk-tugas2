import java.util.ArrayList;

/**
 * Implementasi manajemen kategori
 * REQUIRED: Tidak ada
 */
public class CategoryManager implements ICategoryManager {
    private ArrayList<Category> categories;
    private int nextId;

    public CategoryManager() {
        this.categories = new ArrayList<>();
        this.nextId = 1;

        // Inisialisasi dengan beberapa kategori default
        addCategory("Gaji", "INCOME");
        addCategory("Bonus", "INCOME");
        addCategory("Makanan", "EXPENSE");
        addCategory("Transportasi", "EXPENSE");
        addCategory("Hiburan", "EXPENSE");
        addCategory("Belanja", "EXPENSE");
        addCategory("Pendidikan", "EXPENSE");
        addCategory("Kesehatan", "EXPENSE");
    }

    @Override
    public int addCategory(String name, String type) {
        if (name == null || name.isEmpty() || type == null ||
                (!type.equals("INCOME") && !type.equals("EXPENSE"))) {
            return -1;
        }

        if (categoryNameExists(name)) {
            return -1;
        }

        Category category = new Category(nextId, name, type);
        categories.add(category);
        return nextId++;
    }

    @Override
    public Category[] getAllCategories() {
        return categories.toArray(new Category[0]);
    }

    @Override
    public boolean categoryExists(int categoryId) {
        for (Category c : categories) {
            if (c.getId() == categoryId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Category getCategoryById(int categoryId) {
        for (Category c : categories) {
            if (c.getId() == categoryId) {
                return c;
            }
        }
        return null;
    }

    /**
     * Memeriksa apakah kategori dengan nama tertentu sudah ada
     */
    private boolean categoryNameExists(String name) {
        for (Category c : categories) {
            if (c.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
