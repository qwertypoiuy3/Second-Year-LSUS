abstract class Handler
{
    Handler succesor;
    abstract public String dispense(int amount);
    
    public Handler setNext(Handler handler) {
        this.succesor = handler;
        return handler;
    }
    
}

class Handler500 extends Handler
{   
    private Handler next;
    
    public Handler setNext(Handler next)
    {
        this.succesor = next;
        return next;
    }
    
    @Override
    public String dispense(int amount) { 
        if(amount > 500)
        {
            int a = amount / 500;
            int b = amount % 500;
            System.out.print(a + " bills of $500");
            if(b != 0)
                this.succesor.dispense(b);
        }
        else
            this.succesor.dispense(amount);
        return "";
    }
   
}

class Handler100 extends Handler
{
    private Handler next;
    
    public Handler setNext(Handler next)
    {
        this.succesor = next;
        return next;
    }
    
    @Override
    public String dispense(int amount) {
        if(amount > 100)
        {
            int a = amount / 100;
            int b = amount % 100;
            System.out.print(a + " bills of $100");
            if(b != 0)
                this.succesor.dispense(b);
        }
        else
            this.succesor.dispense(amount);
        return "";
    }
}

class Handler50 extends Handler
{
    private Handler next;
    
    public Handler setNext(Handler next)
    {
        this.succesor = next;
        return next;
    }
    
    
    @Override
    public String dispense(int amount) {
        if(amount > 50)
        {
            int a = amount / 50;
            int b = amount % 50;
            System.out.print(a + " bills of $50");
            if(b != 0)
                this.succesor.dispense(b);
        }
        else
            this.succesor.dispense(amount);
        return "";
    }
}

class Handler10 extends Handler
{
    private Handler next;
    
    public Handler setNext(Handler next)
    {
        this.succesor = next;
        return next;
    }
    
    @Override
    public String dispense(int amount) {
        if(amount > 10)
        {
            int a = amount / 10;
            int b = amount % 10;
            System.out.print(a + " bills of $10");
            if(b != 0)
                this.succesor.dispense(b);
        }
        else
            this.succesor.dispense(amount);
        return "";
    }
}

class Handler5 extends Handler
{
    private Handler next;
    
    public Handler setNext(Handler next)
    {
        this.succesor = next;
        return next;
    }
    
    @Override
    public String dispense(int amount) {
        if(amount >= 5)
        {
            int a = amount / 5;
            int b = amount % 5;
            System.out.print(a + " bills of $5");
            if(b != 0)
                this.succesor.dispense(b);
        }
        else
            this.succesor.dispense(amount);
        return "";
    }
}

class Handler1 extends Handler
{
    private Handler next;
    
    public Handler setNext(Handler next)
    {
        this.succesor = next;
        return next;
    }
    
    @Override
    public String dispense(int amount) {
        if(amount > 1)
        {
            int a = amount / 1;
            int b = amount % 1;
            System.out.print(a + " bills of $1");
            if(b != 0)
                this.succesor.dispense(b);
        }
        else
            this.succesor.dispense(amount);
        return "";
    }
}

class ATM
{
    Handler handler;
    public ATM(Handler handler)
    {
        this.handler = handler;
    }

    public String dispense(int amount)
    {
        return handler.dispense(amount);
    }
}


public class ATMTester
{
    public static void main(String[] args)
    {
        ATM atm;
        Handler handler;
        
        
        /*I tried the way it is presented on the Power Point, but I could not 
         figure out how to pass it to the next handler.
        */
        Handler handler500 = new Handler500();
        Handler handler100 = new Handler100();
        Handler handler50 = new Handler50();
        Handler handler10 = new Handler10();
        Handler handler5 = new Handler5();
        Handler handler1 = new Handler1();
        
        handler500.setNext(handler100);
        handler100.setNext(handler50);
        handler50.setNext(handler10);
        handler10.setNext(handler5);
        handler5.setNext(handler1);
        
    	/// TODO - create an ATM object with the appropriate handlers and store it in the variable "atm"
        atm = new ATM(handler500);

    	System.out.println(atm.dispense(1234)); // should display: 2 bills of $500 + 2 bills of $100 + 3 bills of $10 + 4 bills of $1

    	System.out.println(atm.dispense(1235)); // should display: 2 bills of $500 + 2 bills of $100 + 3 bills of $10 + 1 bills of $5
    }
}
