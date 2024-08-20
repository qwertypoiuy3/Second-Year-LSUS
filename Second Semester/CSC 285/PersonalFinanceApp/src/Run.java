
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Run {
    public static void main(String[] args) 
    {
        PersonalFinance a = new PersonalFinance();
        
        a.updateCash(87);
        a.updateCash(-12);
        System.out.println(a.getCash());
        
        System.out.println("\n****************************************************\n");

        
        a.addCreditCard("Karin", 1500);
        a.addCreditCard("Kelly", 1700);
        a.addCreditCard("Juan", 486);
//        a.removeCreditCard("Juan");
        a.updateCreditCardDebt("Kelly", -48);
        System.out.println(a.getCreditCards());
        
        System.out.println("\n***************************************************\n");
        
        a.addDebitAccount("Jasmin", 600);
        a.addDebitAccount("Jose", 780);
        a.addDebitAccount("Gaby", 458);
//        a.removeDebitAccount("Gaby");
        a.updateDebitAccountBalance("Jose", -78);
        System.out.println(a.getDebitAccounts());
        
        System.out.println("\n****************************************************\n");
        
        a.addCategory("Car", ICategory.TransactionType.EXPENDITURE);
        a.addCategory("House", ICategory.TransactionType.INCOME);
        a.addCategory("Furniture", ICategory.TransactionType.EXPENDITURE);
        a.updateCategoryType("Furniture", null);
        System.out.println(a.getCategories());
        
        System.out.println("\n****************************************************\n");
                
        Date startRent = new Date();
        startRent.setMonth(4);
        startRent.setDate(17);
        Date endRent = new Date();
        endRent.setMonth(6);
        endRent.setDate(3);
        
        Date startGroceries = new Date();
        startGroceries.setMonth(10);
        startGroceries.setDate(18);
        Date endGroceries = new Date();
        endGroceries.setMonth(11);
        endGroceries.setDate(1);
        
        Date startEntertainment = new Date();
        startEntertainment.setMonth(1);
        startEntertainment.setDate(23);
        Date endEntertainment = new Date();
        startEntertainment.setMonth(1);
        endEntertainment.setDate(29);
        
        Map<String, Double> rent = new HashMap<>();
        rent.put("Rent", 450.0);
        
        Map<String, Double> groceries = new HashMap<>();
        groceries.put("bread", 5.99);
        groceries.put("eggs", 10.50);
        groceries.put("butter", 9.99);
        
        Map<String, Double> entertainment = new HashMap<>();
        entertainment.put("movie", 49.54);
        entertainment.put("game", 60.99);
        entertainment.put("bowling", 10.58);
        
        
        a.addBudget("Rent", startRent, endRent, rent);
        a.addBudget("Groceries", startGroceries, endGroceries, groceries);
        a.addBudget("Entertainment", startEntertainment, endEntertainment, entertainment);
        a.removeBudgetItem("Entertainment", "game");
        
        System.out.println(a.getBudgets());
        
        System.out.println("\n****************************************************\n");

        Date first = new Date();
        first.setDate(7);
        Date second = new Date();
        second.setDate(25);
        
        Date tr = new Date();
        tr.setDate(21);
        Date fr = new Date();
        fr.setDate(24);
        
        Date after = new Date();
        after.setDate(23);
        
        a.addTransaction(tr, "Rent", 500, "House", "Jasmin");
        a.addTransaction(fr, "Game", 60.5, "Entertainment", "Karin");
        
        a.updateTransactionCategory(0, "Apartment");
        
        System.out.println(a.getTransactions(first, second));
        
        System.out.println(a.getCreditCards());
        System.out.println(a.getDebitAccounts());
        
        System.out.println("\n****************************************************\n");

        Category bread = Category.getInstace("bread", ICategory.TransactionType.EXPENDITURE);
        Category eggs = Category.getInstace("eggs", ICategory.TransactionType.EXPENDITURE);
        Category butter = Category.getInstace("butter", ICategory.TransactionType.EXPENDITURE);
        Category jam = Category.getInstace("jam", ICategory.TransactionType.EXPENDITURE);

        Map<ICategory, Double> actual = new HashMap<>();
        actual.put(bread, 5.99);
        actual.put(eggs, 10.50);
        actual.put(butter, 9.99);
        actual.put(jam, 9.6);
        

        
        Map<ICategory, Double> projected = new HashMap<>();
        projected.put(bread, 5.99);
        projected.put(eggs, 10.50);
//        projected.put(butter, 9.99);
//        projected.put(jam, 9.6);
        

        System.out.println(a.getStatus("Groceries", projected, actual));

        System.out.println("\n****************************************************");
    }
}