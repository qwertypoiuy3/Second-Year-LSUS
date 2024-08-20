public class DebitCard implements IAccount{

    private  String name;
    private  double amount;
    private static DebitCard myCard;
    private static int num = 1;
    
    private DebitCard(String name, double creditAmount)
    {
        this.name = name;
        this.amount = creditAmount;
    }
    
    public static DebitCard getInstance(String name, double amount) {
        if(num > 0) 
            myCard = new DebitCard(name, amount);
        num++;
        return myCard;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getValue() {
        return amount;
    }   
    
    public void setName(String newName)
    {
        name = newName;
    }
    
    public void setBalance(double newBalance) 
    {
        amount = newBalance;
    }
      
    public String toString() {
        return name + ": $" + amount; 
    }
}
