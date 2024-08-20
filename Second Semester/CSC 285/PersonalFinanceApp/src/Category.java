public class Category implements ICategory{

    private String name;
    private TransactionType type;
    private static Category myCategory;
    private static int num = 1;
    enum Misc {Miscelleanous, misc2}
    
    private Category(String name, ICategory.TransactionType transactionType) {
        this.name = name;
        type = transactionType;
    }
    
    public static Category getInstace(String name, ICategory.TransactionType transactionType) {
        if(num > 0)
            myCategory = new Category(name, transactionType);
        num++;
        return myCategory;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public TransactionType getType() {
        return type;
    }
    
    public void setName(String newName) {
        name = newName;
    }
    
    public void setType(TransactionType newType) {
        type = newType;
    }
    
    public String toString() {
        return "Category: " + name + " \tType: " + type + "\n";
    }
}
