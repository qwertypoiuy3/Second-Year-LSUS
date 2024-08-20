import java.util.Date;

public class Transaction implements ITransaction{

    private int id;
    private Date date;
    private String description;
    private double amount;
    private String categoryName;
    private String accountName;
    private Category category;
    private IAccount account;
    private static Transaction myTransaction;
    private static int num = 1;
    
    private Transaction(Date date, String description, double amount, String
            categoryName, String accountName) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.categoryName = categoryName;
        this.accountName = accountName;
    }
    
    public static Transaction getInstace(Date date, String description, double 
            amount, String categoryName, String accountName) {
        if(num > 0)
            return myTransaction = new Transaction(date, description, amount, 
                    categoryName, accountName);
        num++;
        return myTransaction;
    }
    
    @Override
    public int getId() {
        return id;
    }
    
    public void setId(int newId) {
        id = newId;
    }

    @Override
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date newDate) {
        date = newDate;
    }

    @Override
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String newDescription) {
        description = newDescription;
    }

    @Override
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(double newAmount) {
        amount = newAmount;
    }

    @Override
    public ICategory getCategory() {
        return category;
    }
    
    public void setCategory(String newCategory) {
        categoryName = newCategory;
    }

    @Override
    public IAccount getAccount() {
        return account;
    }
    
    public String toString() {
        return "Account: " + accountName + " Date: " + date.getMonth() + "/" + 
                date.getDate() + " Description: " + description + " $" + amount 
                + " Category: " + categoryName + "\n";
    }
    
}
