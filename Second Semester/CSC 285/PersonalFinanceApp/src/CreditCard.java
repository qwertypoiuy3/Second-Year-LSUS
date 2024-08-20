public class CreditCard implements IAccount{

    private String name;
    private double creditAmount;
    private static CreditCard myCard;
    private static int num = 1;
    
    private CreditCard(String name, double creditAmount)
    {
        this.name = name;
        this.creditAmount = creditAmount;
    }
    
    public static CreditCard getInstance(String name, double amount) {
        if(num > 0)
            myCard = new CreditCard(name, amount);
        num++;
        return myCard;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getValue() {
        return creditAmount;
    }
    
    public void setName(String newName)
    {
        name = newName;
    }
    
    public void setDebt(double newDebt) 
    {
        creditAmount = newDebt;
    }
      
    public String toString() {
        return name + ": $" + creditAmount; 
    }
}
