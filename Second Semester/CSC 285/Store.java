import java.util.Scanner;

//Creates all of Crust variants with a method to return what type it is
abstract class Crust {
    private String crust;
}
class ThinCrust extends Crust {
    public String getCrust() {
        return "thin crust, ";
    }
}
class ThickCrust extends Crust {
    public String getCrust() {
        return "thick crust, ";
    }
}
class VeryThinCrust extends Crust {
    public String getCrust() {
        return "very thin crust, ";
    }
}

//Creates all of Sauce variants with a method to return what type it is
abstract class Sauce {
    private String sauce;
}
class MarinaraSauce extends Sauce {
    public String getSauce() {
        return "marinara sauce, ";
    }
}
class PlumTomatoSauce extends Sauce {
    public String getSauce() {
        return "plum tomato sauce, ";
    }
}
class BruschettaSauce extends Sauce {
    public String getSauce() {
        return "bruschetta sauce, ";
    }
}

//Creates all of Cheese variants with a method to return what type it is
abstract class Cheese {
    private String cheese;
}
class MozzarellaCheese extends Cheese {
    public String getCheese() {
        return "mozzarella cheese";
    }
}
class ReggianoCheese extends Cheese {
    public String getCheese() {
        return "reggiano cheese";
    }
}
class GoatCheese extends Cheese {
    public String getCheese() {
        return "goat cheese";
    }
}

//Creates an abstract Pizza with all its components
abstract class Pizza {
    protected Crust crust;
    protected Sauce sauce;
    protected Cheese cheese;
}

//Creaates the template of a Chicago style pizza with its corresponding elements
class ChicagoStyle extends Pizza {
    ThickCrust crust = new ThickCrust();
    PlumTomatoSauce sauce = new PlumTomatoSauce();
    MozzarellaCheese cheese = new MozzarellaCheese();
    
    public String toString() {
        return "Chicago style pizza: " + crust.getCrust() + sauce.getSauce() + 
        cheese.getCheese();
    }
}

//Creaates the template of a New York style pizza with its corresponding elements
class NewYorkStyle extends Pizza {
    ThinCrust crust = new ThinCrust();
    MarinaraSauce sauce = new MarinaraSauce();
    ReggianoCheese cheese = new ReggianoCheese();
    
    public String toString() {
        return "New York style pizza: " + crust.getCrust() + sauce.getSauce() +
        cheese.getCheese();
    }
}

//Creaates the template of a California style pizza with its corresponding elements
class CaliforniaStyle extends Pizza {
    VeryThinCrust crust = new VeryThinCrust();
    BruschettaSauce sauce = new BruschettaSauce();
    GoatCheese cheese = new GoatCheese();
    
    public String toString() {
        return "California style pizza: " + crust.getCrust() + sauce.getSauce() 
        + cheese.getCheese();
    }
}

//Abstract factory method
class PizzaMaker {
    protected Pizza pizza;
    protected Crust crust;
    protected Sauce sauce;
    protected Cheese cheese;
    
    //Takes a Pizza as a parameter in order to create a Pizza object
    public PizzaMaker(Pizza a) {
        pizza = a;
    }
    
    //Prints the type of pizza passed as a parameter
    public void order() {
        System.out.println(pizza);
    }
}


public class Store {
    public static void main(String[] args) {
        //Creates a Scanner class in order to know what type of pizza should be created
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Enter the type of pizza: (Chicago, New York or California)\n");
        String type = scan.nextLine();
        
        //Creates the desried pizza using PizzaMaker (Abstract Factory)
        PizzaMaker maker = null;
        if(type.equals("Chicago"))
            maker = new PizzaMaker(new ChicagoStyle());
        else if(type.equals("New York"))
            maker = new PizzaMaker(new NewYorkStyle());
        else if(type.equals("California"))
            maker = new PizzaMaker(new CaliforniaStyle());
        maker.order();
    }
}

