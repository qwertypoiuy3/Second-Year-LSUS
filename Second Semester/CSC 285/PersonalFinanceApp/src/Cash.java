public class Cash implements IAccount{
    
    private String name;
    private double amount;
    private static Cash myCash;
    private static int num = 1;
    
    private Cash(double amount) {
        this.amount = amount;
    }
    
    public static Cash getInstance(double amount) {
        if(num > 0)
            myCash = new Cash(amount);
        num++;
        return myCash;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getValue() {
        return amount;
    }
    
    public void setValue(double newValue) {
        amount = newValue;
    }
    
    
//    public String toString() {
//        return "Cash: " + amount;
//    }
}
