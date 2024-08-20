import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

public class TestMain {
   public static void main(String[] args) {
      IPersonalFinance pf = new PersonalFinance();
      ArrayList<Test> tests = new ArrayList<>();
      testCash(pf, tests);
      testCredit(pf, tests);
      testDebit(pf, tests);
      testCategory(pf, tests);
      testBudget(pf, tests);
      testTransaction(pf, tests);
      testIntegration(pf, tests);
      for (Test t: tests)
         t.run();
   }

   private static void testCash(IPersonalFinance pf, ArrayList<Test> tests) {
      tests.add(new Test(
         "The initial cash at hand is 0",
         pf::getCash,
         0.0
      ));
      tests.add(new Test(
            "The cash cannot be updated with a negative value",
            ()->{
               boolean rc = !pf.updateCash(-1.23);
               return rc && pf.getCash() == 0;
            },
            true
      ));
      tests.add(new Test(
            "The cash can be updated with a positive value",
            ()->{
               boolean rc = pf.updateCash(1.23);
               rc = rc && pf.getCash() == 1.23;
               rc = rc && pf.updateCash(123.45);
               return rc && pf.getCash() == 123.45;
            },
            true
      ));
   }

   private static void testCredit(IPersonalFinance pf, ArrayList<Test> tests) {
      tests.add(new Test(
            "The list of credit cards is not null",
            pf::getCreditCards
      ));
      tests.add(new Test(
            "The number of credit cards is initially 0",
            ()->pf.getCreditCards().size(),
            0
      ));
      tests.add(new ExceptionTest(
            "Trying to modify the list of credit cards is unsuccessful",
            ()->pf.getCreditCards().add(null),
            new UnsupportedOperationException()
      ));
      tests.add(new Test(
            "Trying to add a new credit card with a null name is unsuccessful",
            ()->{
               boolean rc = pf.addCreditCard(null, 0);
               return !rc && pf.getCreditCards().size() == 0;
            },
            true
      ));
      tests.add(new Test(
            "Trying to add a new credit card with a negative debt is unsuccessful",
            ()->{
               boolean rc = pf.addCreditCard("Amex", -1.0);
               return !rc && pf.getCreditCards().size() == 0;
            },
            true
      ));
      tests.add(new Test(
            "Trying to remove a credit card before adding any is unsuccessful",
            () -> pf.removeCreditCard("Amex"),
            false
      ));
      tests.add(new Test(
            "Trying to update the name or debt of a credit card before adding any is unsuccessful",
            () -> {
               boolean r1 = pf.updateCreditCardName("Amex", "American Express");
               boolean r2 = pf.updateCreditCardDebt("American Express", 123.45);
               return r1 || r2;
            },
            false
      ));
      tests.add(new Test(
            "A new valid credit card can be added and retrieved",
            ()->{
               boolean rc = pf.addCreditCard("Amex", 1.23);
               rc = rc && pf.getCreditCards().size() == 1;
               IAccount ac = pf.getCreditCards().iterator().next();
               rc = rc && ac.getName().equals("Amex") && ac.getValue() == 1.23;
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "Multiple valid credit cards can be added and retrieved",
            ()->{
               boolean[] r = new boolean[5];
               r[0] = pf.addCreditCard("Chase Freedom", 12.34);
               r[1] = pf.addCreditCard("Discover", 56.78);
               for (IAccount a : pf.getCreditCards()) {
                  if (a.getName().equals("Amex") && a.getValue() == 1.23)
                     r[2] = true;
                  else if (a.getName().equals("Chase Freedom") && a.getValue() == 12.34)
                     r[3] = true;
                  else if (a.getName().equals("Discover") && a.getValue() == 56.78)
                     r[4] = true;
               }
               boolean rc = true;
               for (boolean i : r) rc &= i;
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "Trying to add a new credit card with a duplicate name is unsuccessful",
            ()-> pf.addCreditCard("Discover", 56.78),
            false
      ));
      tests.add(new Test(
            "Trying to remove a credit card with an invalid name is unsuccessful",
            () -> !pf.removeCreditCard("Chase"),
            true
      ));
      tests.add(new Test(
            "Trying to remove a credit card with a null name is unsuccessful",
            () -> !pf.removeCreditCard(null),
            true
      ));
      tests.add(new Test(
            "A credit card with a valid name can be removed",
            () -> {
               boolean[] r = new boolean[3];
               r[0] = pf.removeCreditCard("Amex");
               for (IAccount a : pf.getCreditCards()) {
                  if (a.getName().equals("Amex") || a.getValue() == 1.23)
                     r[0] = false;
                  else if (a.getName().equals("Chase Freedom") && a.getValue() == 12.34)
                     r[1] = true;
                  else if (a.getName().equals("Discover") && a.getValue() == 56.78)
                     r[2] = true;
               }
               boolean rc = true;
               for (boolean i : r) rc &= i;
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "The name of an existing credit card can be updated",
            () -> {
               boolean[] r = new boolean[5];
               boolean m = true;
               r[0] = pf.addCreditCard("Amex", 234.56);
               r[1] = pf.updateCreditCardName("Chase Freedom", "Chase Sapphire");
               for (IAccount a : pf.getCreditCards()) {
                  if (a.getName().equals("Amex") || a.getValue() == 234.56)
                     r[2] = true;
                  else if (a.getName().equals("Chase Sapphire") && a.getValue() == 12.34)
                     r[3] = true;
                  else if (a.getName().equals("Discover") && a.getValue() == 56.78)
                     r[4] = true;
                  else m = false;
               }
               boolean rc = true;
               for (boolean i : r) rc &= i;
               return rc && m;
            },
            true
      ));
      tests.add(new Test(
            "Trying to update the name or debt of a credit card which is not present is unsuccessful",
            () -> pf.updateCreditCardName("BoA", "Bank of America") ||
                  pf.updateCreditCardDebt("Bank of America", 98.76),
            false
      ));
      tests.add(new Test(
            "Trying to change the credit card's name to a null or non-unique name is unsuccessful",
            () -> pf.updateCreditCardName("Discover", "Amex") ||
                  pf.updateCreditCardName("Discover", null),
            false
      ));
      tests.add(new Test(
            "Trying to update a credit card with a negative debt is unsuccessful",
            () -> pf.updateCreditCardDebt("Discover", -123.45),
            false
      ));
      tests.add(new Test(
            "Invalid operations must not change the list of credit cards",
            () -> {
               boolean[] r = new boolean[3];
               boolean m = true;
               for (IAccount a : pf.getCreditCards()) {
                  if (a.getName().equals("Amex") || a.getValue() == 234.56)
                     r[0] = true;
                  else if (a.getName().equals("Chase Sapphire") && a.getValue() == 12.34)
                     r[1] = true;
                  else if (a.getName().equals("Discover") && a.getValue() == 56.78)
                     r[2] = true;
                  else m = false;
               }
               boolean rc = true;
               for (boolean i : r) rc &= i;
               return rc && m;
            },
            true
      ));
   }

   private static void testDebit(IPersonalFinance pf, ArrayList<Test> tests) {
      tests.add(new Test(
            "The list of debit accounts is not null",
            pf::getDebitAccounts
      ));
      tests.add(new Test(
            "The number of debit accounts is initially 0",
            ()->pf.getDebitAccounts().size(),
            0
      ));
      tests.add(new ExceptionTest(
            "Trying to modify the list of debit accounts is unsuccessful",
            ()->pf.getDebitAccounts().add(null),
            new UnsupportedOperationException()
      ));
      tests.add(new Test(
            "Trying to add a new debit account with a null name is unsuccessful",
            ()->{
               boolean rc = pf.addDebitAccount(null, 0);
               return !rc && pf.getDebitAccounts().size() == 0;
            },
            true
      ));
      tests.add(new Test(
            "Trying to add a new debit account with a negative balance is unsuccessful",
            ()->{
               boolean rc = pf.addDebitAccount("CFCU", -1.0);
               return !rc && pf.getDebitAccounts().size() == 0;
            },
            true
      ));
      tests.add(new Test(
            "Trying to remove a debit account before adding any is unsuccessful",
            () -> pf.removeDebitAccount("CFCU"),
            false
      ));
      tests.add(new Test(
            "Trying to update the name or balance of a debit account before adding any is unsuccessful",
            () -> {
               boolean r1 = pf.updateDebitAccountName("CFCU", "Campus Federal Credit Union");
               boolean r2 = pf.updateDebitAccountBalance("Campus Federal Credit Union", 123.45);
               return r1 || r2;
            },
            false
      ));
      tests.add(new Test(
            "A new valid debit account can be added and retrieved",
            ()->{
               boolean rc = pf.addDebitAccount("CFCU", 1.23);
               rc = rc && pf.getDebitAccounts().size() == 1;
               IAccount ac = pf.getDebitAccounts().iterator().next();
               rc = rc && ac.getName().equals("CFCU") && ac.getValue() == 1.23;
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "Multiple valid debit accounts can be added and retrieved",
            ()->{
               boolean[] r = new boolean[5];
               r[0] = pf.addDebitAccount("Chase", 12.34);
               r[1] = pf.addDebitAccount("Red River", 56.78);
               for (IAccount a : pf.getDebitAccounts()) {
                  if (a.getName().equals("CFCU") && a.getValue() == 1.23)
                     r[2] = true;
                  else if (a.getName().equals("Chase") && a.getValue() == 12.34)
                     r[3] = true;
                  else if (a.getName().equals("Red River") && a.getValue() == 56.78)
                     r[4] = true;
               }
               boolean rc = true;
               for (boolean i : r) rc &= i;
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "Trying to add a new debit account with a duplicate name is unsuccessful",
            ()-> pf.addDebitAccount("Red River", 56.78) ||
                  pf.addDebitAccount("Amex", 123.45),
            false
      ));
      tests.add(new Test(
            "Trying to remove a debit account with an invalid name is unsuccessful",
            () -> !pf.removeDebitAccount("Chase Sapphire"),
            true
      ));
      tests.add(new Test(
            "Trying to remove a debit account with a null name is unsuccessful",
            () -> !pf.removeDebitAccount(null),
            true
      ));
      tests.add(new Test(
            "A debit account with a valid name can be removed",
            () -> {
               boolean[] r = new boolean[3];
               r[0] = pf.removeDebitAccount("CFCU");
               for (IAccount a : pf.getDebitAccounts()) {
                  if (a.getName().equals("CFCU") || a.getValue() == 1.23)
                     r[0] = false;
                  else if (a.getName().equals("Chase") && a.getValue() == 12.34)
                     r[1] = true;
                  else if (a.getName().equals("Red River") && a.getValue() == 56.78)
                     r[2] = true;
               }
               boolean rc = true;
               for (boolean i : r) rc &= i;
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "The name of an existing debit account can be updated",
            () -> {
               boolean[] r = new boolean[5];
               boolean m = true;
               r[0] = pf.addDebitAccount("CFCU", 234.56);
               r[1] = pf.updateDebitAccountName("Chase", "Chase Checking");
               for (IAccount a : pf.getDebitAccounts()) {
                  if (a.getName().equals("CFCU") || a.getValue() == 234.56)
                     r[2] = true;
                  else if (a.getName().equals("Chase Checking") && a.getValue() == 12.34)
                     r[3] = true;
                  else if (a.getName().equals("Red River") && a.getValue() == 56.78)
                     r[4] = true;
                  else m = false;
               }
               boolean rc = true;
               for (boolean i : r) rc &= i;
               return rc && m;
            },
            true
      ));
      tests.add(new Test(
            "Trying to update the name or balance of a debit account which is not present is unsuccessful",
            () -> pf.updateDebitAccountName("BoA", "Bank of America") ||
                  pf.updateDebitAccountBalance("Bank of America", 98.76),
            false
      ));
      tests.add(new Test(
            "Trying to change the debit account's name to a null or non-unique name is unsuccessful",
            () -> pf.updateDebitAccountName("Red River", "Amex") ||
                  pf.updateDebitAccountName("Red River", "CFCU") ||
                  pf.updateDebitAccountName("Red River", null),
            false
      ));
      tests.add(new Test(
            "Trying to update a debit account with a negative balance is unsuccessful",
            () -> pf.updateDebitAccountBalance("Red River", -123.45),
            false
      ));
      tests.add(new Test(
            "Invalid operations must not change the list of debit accounts",
            () -> {
               boolean[] r = new boolean[3];
               boolean m = true;
               for (IAccount a : pf.getCreditCards()) {
                  if (a.getName().equals("CFCU") || a.getValue() == 234.56)
                     r[0] = true;
                  else if (a.getName().equals("Chase Checking") && a.getValue() == 12.34)
                     r[1] = true;
                  else if (a.getName().equals("Red River") && a.getValue() == 56.78)
                     r[2] = true;
                  else m = false;
               }
               boolean rc = true;
               for (boolean i : r) rc &= i;
               return rc && m;
            },
            true
      ));
   }

   private static void testCategory(IPersonalFinance pf, ArrayList<Test> tests) {
      tests.add(new Test(
            "The list of categories is not null",
            pf::getCategories
      ));
      tests.add(new Test(
            "The number of categories is initially 0",
            ()->pf.getCategories().size(),
            0
      ));
      tests.add(new ExceptionTest(
            "Trying to modify the list of categories is unsuccessful",
            ()->pf.getCategories().add(null),
            new UnsupportedOperationException()
      ));
      tests.add(new Test(
            "Trying to remove a category before adding any is unsuccessful",
            () -> pf.removeCategory("Rent"),
            false
      ));
      tests.add(new Test(
            "Trying to add a new category with a null name is unsuccessful",
            () -> !pf.addCategory(null, ICategory.TransactionType.EXPENDITURE) &&
                  pf.getCategories().size() == 0,
            true
      ));
      tests.add(new Test(
            "Trying to add a new category with a null category type is unsuccessful",
            () -> !pf.addCategory("Rent", null) &&
                  pf.getCategories().size() == 0,
            true
      ));
      tests.add(new Test(
            "Trying to update a category before adding any is unsuccessful",
            () -> pf.updateCategoryName("Rent", "Home rent") ||
                  pf.updateCategoryType("Home Rent", ICategory.TransactionType.INCOME) ||
                  pf.getCategories().size() != 0,
            false
      ));
      tests.add(new Test(
            "A valid new category can be added and retrieved",
            () -> {
               boolean rc = pf.addCategory("Paycheck", ICategory.TransactionType.INCOME);
               rc = rc && pf.getCategories().size() == 1;
               ICategory c = pf.getCategories().iterator().next();
               rc = rc && c.getName().equals("Paycheck") && c.getType() == ICategory.TransactionType.INCOME;
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "Multiple categories can be added and retrieved",
            () -> {
               boolean[] r = new boolean[6];
               r[0] = true;
               r[1] = pf.addCategory("Rent", ICategory.TransactionType.EXPENDITURE);
               r[2] = pf.addCategory("Groceries", ICategory.TransactionType.EXPENDITURE);
               for (ICategory a : pf.getCategories()) {
                  if (a.getName().equals("Paycheck") && a.getType() == ICategory.TransactionType.INCOME)
                     r[3] = true;
                  else if (a.getName().equals("Rent") && a.getType() == ICategory.TransactionType.EXPENDITURE)
                     r[4] = true;
                  else if (a.getName().equals("Groceries") && a.getType() == ICategory.TransactionType.EXPENDITURE)
                     r[5] = true;
                  else r[0] = false;
               }
               boolean rc = true;
               for (boolean i : r) rc &= i;
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "Trying to add a new category with a duplicate name is unsuccessful",
            () -> !pf.addCategory("Rent", ICategory.TransactionType.EXPENDITURE),
            true
      ));
      tests.add(new Test(
            "Trying to remove a category with a null or invalid name is unsuccessful",
            () -> !pf.removeCategory("Electricity") &&
                  !pf.removeCategory(null),
            true
      ));
      tests.add(new Test(
            "Trying to update a category which is not present is unsuccessful",
            () -> pf.updateCategoryName("Electricity", "Leccy") ||
                  pf.updateCategoryType("Insurance", ICategory.TransactionType.EXPENDITURE),
            false
      ));
      tests.add(new Test(
            "Trying to update a category with a non-unique name is unsuccessful",
            () -> pf.updateCategoryName("Rent", "Groceries") ||
                  pf.updateCategoryName("Groceries", "Groceries"),
            false
      ));
      tests.add(new Test(
            "Trying to update a category with a null name is unsuccessful",
            () -> pf.updateCategoryName("Groceries", null),
            true
      ));
      tests.add(new Test(
            "Trying to update a category with a null type is unsuccessful",
            () -> pf.updateCategoryType("Rent", null),
            true
      ));
      tests.add(new Test(
            "Invalid operations must not change the list of categories",
            () -> {
               boolean[] r = new boolean[3];
               boolean m = true;
               for (ICategory a : pf.getCategories()) {
                  if (a.getName().equals("Paycheck") || a.getType() == ICategory.TransactionType.INCOME)
                     r[0] = true;
                  else if (a.getName().equals("Rent") && a.getType() == ICategory.TransactionType.EXPENDITURE)
                     r[1] = true;
                  else if (a.getName().equals("Groceries") && a.getType() == ICategory.TransactionType.EXPENDITURE)
                     r[2] = true;
                  else m = false;
               }
               boolean rc = true;
               for (boolean i : r) rc &= i;
               return rc && m;
            },
            true
      ));
   }

   private static void testBudget(IPersonalFinance pf, ArrayList<Test> tests) {
      tests.add(new Test(
            "The list of budgets is not null",
            pf::getBudgets
      ));
      tests.add(new Test(
            "The number of budgets is initially 0",
            ()->pf.getBudgets().size(),
            0
      ));
      tests.add(new ExceptionTest(
            "Trying to modify the list of budgets is unsuccessful",
            ()->pf.getBudgets().add(null),
            new UnsupportedOperationException()
      ));
      tests.add(new Test(
            "Trying to remove a budget before adding any is unsuccessful",
            () -> {
               return pf.removeBudget("May 2021");
            },
            false
      ));
      tests.add(new Test(
            "Trying to update a budget before adding any is unsuccessful",
            () -> {
               try {
                  return pf.updateBudgetName("May 2021", "May '21") ||
                        pf.updateBudgetStartDate("May 2021", DateFormat.getDateInstance().parse("May 1, 2021")) ||
                        pf.updateBudgetEndDate("May 2021", DateFormat.getDateInstance().parse("May 31, 2021")) ||
                        pf.addOrUpdateBudgetItem("May 2021", "Rent", 1000.00);
               } catch (ParseException e) {
                  e.printStackTrace();
                  return true;
               }
            },
            false
      ));
      tests.add(new Test(
            "A new budget with a null or empty map of budget items can be added and retrieved",
            () -> {
               try {
                  boolean rc =  pf.addBudget("May 2021",
                              DateFormat.getDateInstance().parse("May 1, 2021"),
                              DateFormat.getDateInstance().parse("May 31, 2021"),
                              null);
                  rc = rc && pf.addBudget("June 2021",
                        DateFormat.getDateInstance().parse("June 1, 2021"),
                        DateFormat.getDateInstance().parse("June 30, 2021"),
                        new HashMap<>());
                  rc = rc && pf.getCategories().size() == 2;
                  IBudget b = pf.getBudgets().iterator().next();
                  rc = rc && b.getName().equals("May 2021") &&
                        b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                        b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 31, 2021")) == 0 &&
                        b.getItems().isEmpty();
                  rc = rc && b.getName().equals("June 2021") &&
                        b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 1, 2021")) == 0 &&
                        b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                        b.getItems().isEmpty();
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to add a new budget with a null or duplicate name is unsuccessful",
            () -> {
               try {
                  return pf.addBudget("May 2021",
                              DateFormat.getDateInstance().parse("May 1, 2021"),
                              DateFormat.getDateInstance().parse("May 31, 2021"),
                              null) ||
                        pf.addBudget(null,
                              DateFormat.getDateInstance().parse("May 1, 2021"),
                              DateFormat.getDateInstance().parse("May 31, 2021"),
                              null);
               } catch (ParseException e) {
                  e.printStackTrace();
                  return true;
               }
            },
            false
      ));
      tests.add(new Test(
            "Trying to add a new budget with a null or invalid start or end date is unsuccessful",
            () -> {
               try {
                  return pf.addBudget("April 2021",
                              DateFormat.getDateInstance().parse(null),
                              DateFormat.getDateInstance().parse("April 30, 2021"),
                              null) ||
                        pf.addBudget("April 2021",
                              DateFormat.getDateInstance().parse("April 30, 2021"),
                              DateFormat.getDateInstance().parse("April 1, 2021"),
                              null) ||
                        pf.addBudget("April 2021",
                              DateFormat.getDateInstance().parse("April 1, 2021"),
                              DateFormat.getDateInstance().parse(null),
                              null);
               } catch (ParseException e) {
                  e.printStackTrace();
                  return true;
               }
            },
            false
      ));
      tests.add(new Test(
            "Trying to add a new budget with a map containing invalid category name is unsuccessful",
            () -> {
               Map<String, Double> items = new HashMap<>();
               items.put("Electricity", 123.45);
               try {
                  return pf.addBudget("April 2021",
                        DateFormat.getDateInstance().parse("April 1, 2021"),
                        DateFormat.getDateInstance().parse("April 30, 2021"),
                        items);
               } catch (ParseException e) {
                  e.printStackTrace();
                  return true;
               }
            },
            false
      ));
      tests.add(new Test(
            "Trying to add a new budget with a map containing negative value is unsuccessful",
            () -> {
               Map<String, Double> items = new HashMap<>();
               items.put("Rent", -123.45);
               try {
                  return pf.addBudget("April 2021",
                        DateFormat.getDateInstance().parse("April 1, 2021"),
                        DateFormat.getDateInstance().parse("April 30, 2021"),
                        items);
               } catch (ParseException e) {
                  e.printStackTrace();
                  return true;
               }
            },
            false
      ));
      tests.add(new Test(
            "A new budget with a map of items can be added and retrieved",
            () -> {
               Map<String, ICategory> map = new HashMap<>();
               Collection<ICategory> categories = pf.getCategories();
               for (ICategory c: categories)
                  map.put(c.getName(), c);
               Map<String, Double> items = new HashMap<>();
               items.put("Paycheck", 2000.00);
               items.put("Rent", 500.00);
               items.put("Groceries", 200.00);
               try {
                  boolean[] rc = new boolean[6];
                  rc[0] = true;
                  rc[1] = pf.addBudget("April 2021",
                        DateFormat.getDateInstance().parse("April 1, 2021"),
                        DateFormat.getDateInstance().parse("April 30, 2021"),
                        items);
                  rc[2] = pf.getBudgets().size() == 3;
                  for (IBudget b: pf.getBudgets()) {
                     if (b.getName().equals("May 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 31, 2021")) == 0 &&
                           b.getItems().isEmpty())    
                        rc[3] = true;
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty())
                        rc[4] = true;
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2000.00 &&
                           b.getItems().get(map.get("Rent")) == 500.00 &&
                           b.getItems().get(map.get("Groceries")) == 200.00)
                        rc[5] = true;
                     else rc[0] = false;
                  }
                  boolean r = true;
                  for (boolean i : rc) r = r && i;
                  return r;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Multiple budgets with maps of items can be added and retrieved",
            () -> {
               Map<String, ICategory> map = new HashMap<>();
               Collection<ICategory> categories = pf.getCategories();
               for (ICategory c: categories)
                  map.put(c.getName(), c);
               Map<String, Double> items = new HashMap<>();
               items.put("Paycheck", 2100.00);
               items.put("Rent", 510.00);
               items.put("Groceries", 210.00);
               try {
                  boolean[] rc = new boolean[9];
                  rc[0] = true;
                  rc[1] = pf.addBudget("March 2021",
                        DateFormat.getDateInstance().parse("March 1, 2021"),
                        DateFormat.getDateInstance().parse("March 31, 2021"),
                        items);
                  items.put("Paycheck", 2200.00);
                  items.put("Rent", 520.00);
                  items.put("Groceries", 220.00);
                  rc[2] = pf.addBudget("February 2021",
                        DateFormat.getDateInstance().parse("February 1, 2021"),
                        DateFormat.getDateInstance().parse("February 28, 2021"),
                        items);
                  rc[3] = pf.getBudgets().size() == 5;
                  for (IBudget b: pf.getBudgets()) {
                     if (b.getName().equals("May 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 31, 2021")) == 0 &&
                           b.getItems().isEmpty())
                        rc[4] = true;
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty())
                        rc[5] = true;
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2000.00 &&
                           b.getItems().get(map.get("Rent")) == 500.00 &&
                           b.getItems().get(map.get("Groceries")) == 200.00)
                        rc[6] = true;
                     else if (b.getName().equals("March 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("March 31, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2100.00 &&
                           b.getItems().get(map.get("Rent")) == 510.00 &&
                           b.getItems().get(map.get("Groceries")) == 210.00)
                        rc[7] = true;
                     else if (b.getName().equals("February 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("February 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("February 28, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2200.00 &&
                           b.getItems().get(map.get("Rent")) == 520.00 &&
                           b.getItems().get(map.get("Groceries")) == 220.00)
                        rc[8] = true;
                     else rc[0] = false;
                  }
                  boolean r = true;
                  for (boolean i : rc) r = r && i;
                  return r;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "A budget with a valid name can be removed",
            () -> {
               Map<String, ICategory> map = new HashMap<>();
               Collection<ICategory> categories = pf.getCategories();
               for (ICategory c: categories)
                  map.put(c.getName(), c);
               boolean rc = pf.removeBudget("February 2021");
               rc = rc && pf.getBudgets().size() == 4;
               for (IBudget b: pf.getBudgets()) {
                  try {
                     if (b.getName().equals("May 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 31, 2021")) == 0 &&
                           b.getItems().isEmpty()) {}
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty()) {}
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2000.00 &&
                           b.getItems().get(map.get("Rent")) == 500.00 &&
                           b.getItems().get(map.get("Groceries")) == 200.00) {}
                     else if (b.getName().equals("March 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("March 31, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2100.00 &&
                           b.getItems().get(map.get("Rent")) == 510.00 &&
                           b.getItems().get(map.get("Groceries")) == 210.00) {}
                     else rc = false;
                  } catch (ParseException e) {
                     e.printStackTrace();
                     return false;
                  }
               }
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "Trying to remove a budget with an invalid or null name is unsuccessful",
            () -> {
               Map<String, ICategory> map = new HashMap<>();
               Collection<ICategory> categories = pf.getCategories();
               for (ICategory c: categories)
                  map.put(c.getName(), c);
               boolean rc = !pf.removeBudget("May '21");
               rc = rc && !pf.removeBudget(null);
               rc = rc && pf.getBudgets().size() == 4;
               for (IBudget b: pf.getBudgets()) {
                  try {
                     if (b.getName().equals("May 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 31, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2000.00 &&
                           b.getItems().get(map.get("Rent")) == 500.00 &&
                           b.getItems().get(map.get("Groceries")) == 200.00) {
                     }
                     else if (b.getName().equals("March 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("March 31, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2100.00 &&
                           b.getItems().get(map.get("Rent")) == 510.00 &&
                           b.getItems().get(map.get("Groceries")) == 210.00) {
                     }
                     else rc = false;
                  } catch (ParseException e) {
                     e.printStackTrace();
                     return false;
                  }
               }
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "The name of an existing budget can be updated",
            () -> {
               boolean rc = pf.updateBudgetName("May 2021", "May '21");
               rc = rc && pf.getBudgets().size() == 4;
               Map<String, ICategory> map = new HashMap<>();
               Collection<ICategory> categories = pf.getCategories();
               for (ICategory c: categories)
                  map.put(c.getName(), c);
               for (IBudget b: pf.getBudgets()) {
                  try {
                     if (b.getName().equals("May '21") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 31, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2000.00 &&
                           b.getItems().get(map.get("Rent")) == 500.00 &&
                           b.getItems().get(map.get("Groceries")) == 200.00) {
                     }
                     else if (b.getName().equals("March 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("March 31, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2100.00 &&
                           b.getItems().get(map.get("Rent")) == 510.00 &&
                           b.getItems().get(map.get("Groceries")) == 210.00) {
                     }
                     else rc = false;
                  } catch (ParseException e) {
                     e.printStackTrace();
                     return false;
                  }
               }
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a budget which is not present is unsuccessful",
            () -> {
               try {
                  boolean rc = !pf.updateBudgetName("Jan 2021", "January 2021") &&
                        !pf.updateBudgetStartDate("Jan 2021", DateFormat.getDateInstance().parse("Jan 1, 2021")) &&
                        !pf.updateBudgetEndDate("Jan 2021", DateFormat.getDateInstance().parse("Jan 31, 2021")) &&
                        !pf.addOrUpdateBudgetItem("Jan 2021", "Rent", 1200.0);

                  rc = rc && pf.getBudgets().size() == 4;
                  Map<String, ICategory> map = new HashMap<>();
                  Collection<ICategory> categories = pf.getCategories();
                  for (ICategory c: categories)
                     map.put(c.getName(), c);
                  for (IBudget b: pf.getBudgets()) {
                     if (b.getName().equals("May '21") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 31, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2000.00 &&
                           b.getItems().get(map.get("Rent")) == 500.00 &&
                           b.getItems().get(map.get("Groceries")) == 200.00) {
                     }
                     else if (b.getName().equals("March 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("March 31, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2100.00 &&
                           b.getItems().get(map.get("Rent")) == 510.00 &&
                           b.getItems().get(map.get("Groceries")) == 210.00) {
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a budget's name to a null or non-unique name is unsuccessful",
            () -> {
               try {
                  boolean rc = !pf.updateBudgetName("June 2021", "April 2021") &&
                        !pf.updateBudgetName("May' 21", null);
                  rc = rc && pf.getBudgets().size() == 4;
                  Map<String, ICategory> map = new HashMap<>();
                  Collection<ICategory> categories = pf.getCategories();
                  for (ICategory c: categories)
                     map.put(c.getName(), c);
                  for (IBudget b: pf.getBudgets()) {
                     if (b.getName().equals("May '21") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 31, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2000.00 &&
                           b.getItems().get(map.get("Rent")) == 500.00 &&
                           b.getItems().get(map.get("Groceries")) == 200.00) {
                     }
                     else if (b.getName().equals("March 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("March 31, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2100.00 &&
                           b.getItems().get(map.get("Rent")) == 510.00 &&
                           b.getItems().get(map.get("Groceries")) == 210.00) {
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "The start date or end date of an existing budget can be updated",
            () -> {
               try {
                  boolean rc = pf.updateBudgetStartDate("June 2021", DateFormat.getDateInstance().parse("June 2, 2021")) &&
                        pf.updateBudgetEndDate("May '21", DateFormat.getDateInstance().parse("May 29, 2021"));
                  rc = rc && pf.getBudgets().size() == 4;
                  Map<String, ICategory> map = new HashMap<>();
                  Collection<ICategory> categories = pf.getCategories();
                  for (ICategory c: categories)
                     map.put(c.getName(), c);
                  for (IBudget b: pf.getBudgets()) {
                     if (b.getName().equals("May '21") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 29, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 2, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2000.00 &&
                           b.getItems().get(map.get("Rent")) == 500.00 &&
                           b.getItems().get(map.get("Groceries")) == 200.00) {
                     }
                     else if (b.getName().equals("March 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("March 31, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2100.00 &&
                           b.getItems().get(map.get("Rent")) == 510.00 &&
                           b.getItems().get(map.get("Groceries")) == 210.00) {
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a budget with a null or invalid start date is unsuccessful",
            () -> {
               try {
                  boolean rc = !pf.updateBudgetStartDate("June 2021", null) &&
                        !pf.updateBudgetStartDate("May '21", DateFormat.getDateInstance().parse("June 29, 2021"));
                  rc = rc && pf.getBudgets().size() == 4;
                  Map<String, ICategory> map = new HashMap<>();
                  Collection<ICategory> categories = pf.getCategories();
                  for (ICategory c: categories)
                     map.put(c.getName(), c);
                  for (IBudget b: pf.getBudgets()) {
                     if (b.getName().equals("May '21") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 29, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 2, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2000.00 &&
                           b.getItems().get(map.get("Rent")) == 500.00 &&
                           b.getItems().get(map.get("Groceries")) == 200.00) {
                     }
                     else if (b.getName().equals("March 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("March 31, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2100.00 &&
                           b.getItems().get(map.get("Rent")) == 510.00 &&
                           b.getItems().get(map.get("Groceries")) == 210.00) {
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a budget with a null or invalid end date is unsuccessful",
            () -> {
               try {
                  boolean rc = !pf.updateBudgetEndDate("June 2021", null) &&
                        !pf.updateBudgetEndDate("May '21", DateFormat.getDateInstance().parse("April 30, 2021"));
                  rc = rc && pf.getBudgets().size() == 4;
                  Map<String, ICategory> map = new HashMap<>();
                  Collection<ICategory> categories = pf.getCategories();
                  for (ICategory c: categories)
                     map.put(c.getName(), c);
                  for (IBudget b: pf.getBudgets()) {
                     if (b.getName().equals("May '21") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 29, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 2, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2000.00 &&
                           b.getItems().get(map.get("Rent")) == 500.00 &&
                           b.getItems().get(map.get("Groceries")) == 200.00) {
                     }
                     else if (b.getName().equals("March 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("March 31, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2100.00 &&
                           b.getItems().get(map.get("Rent")) == 510.00 &&
                           b.getItems().get(map.get("Groceries")) == 210.00) {
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "A new budget item can be added or updated",
            () -> {
               try {
                  boolean rc = pf.addOrUpdateBudgetItem("May '21", "Rent", 1200.0) &&
                        pf.addOrUpdateBudgetItem("April 2021", "Rent", 600);
                  rc = rc && pf.getBudgets().size() == 4;
                  Map<String, ICategory> map = new HashMap<>();
                  Collection<ICategory> categories = pf.getCategories();
                  for (ICategory c: categories)
                     map.put(c.getName(), c);
                  for (IBudget b: pf.getBudgets()) {
                     if (b.getName().equals("May '21") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 29, 2021")) == 0 &&
                           b.getItems().get(map.get("Rent")) == 1200.00) {
                     }
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 2, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2000.00 &&
                           b.getItems().get(map.get("Rent")) == 600.00 &&
                           b.getItems().get(map.get("Groceries")) == 200.00) {
                     }
                     else if (b.getName().equals("March 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("March 31, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2100.00 &&
                           b.getItems().get(map.get("Rent")) == 510.00 &&
                           b.getItems().get(map.get("Groceries")) == 210.00) {
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a budget with a null or invalid category-name is unsuccessful",
            () -> {
               try {
                  boolean rc = !pf.addOrUpdateBudgetItem("May '21", null, 1300.0) &&
                        !pf.addOrUpdateBudgetItem("April 2021", "Leccy", 700);
                  rc = rc && pf.getBudgets().size() == 4;
                  Map<String, ICategory> map = new HashMap<>();
                  Collection<ICategory> categories = pf.getCategories();
                  for (ICategory c: categories)
                     map.put(c.getName(), c);
                  for (IBudget b: pf.getBudgets()) {
                     if (b.getName().equals("May '21") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 29, 2021")) == 0 &&
                           b.getItems().get(map.get("Rent")) == 1200.00) {
                     }
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 2, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2000.00 &&
                           b.getItems().get(map.get("Rent")) == 600.00 &&
                           b.getItems().get(map.get("Groceries")) == 200.00) {
                     }
                     else if (b.getName().equals("March 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("March 31, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2100.00 &&
                           b.getItems().get(map.get("Rent")) == 510.00 &&
                           b.getItems().get(map.get("Groceries")) == 210.00) {
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a budget with a negative value is unsuccessful",
            () -> {
               try {
                  boolean rc = !pf.addOrUpdateBudgetItem("May '21", "Rent", -1300.0);
                  rc = rc && pf.getBudgets().size() == 4;
                  Map<String, ICategory> map = new HashMap<>();
                  Collection<ICategory> categories = pf.getCategories();
                  for (ICategory c: categories)
                     map.put(c.getName(), c);
                  for (IBudget b: pf.getBudgets()) {
                     if (b.getName().equals("May '21") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 29, 2021")) == 0 &&
                           b.getItems().get(map.get("Rent")) == 1200.00) {
                     }
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 2, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2000.00 &&
                           b.getItems().get(map.get("Rent")) == 600.00 &&
                           b.getItems().get(map.get("Groceries")) == 200.00) {
                     }
                     else if (b.getName().equals("March 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("March 31, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2100.00 &&
                           b.getItems().get(map.get("Rent")) == 510.00 &&
                           b.getItems().get(map.get("Groceries")) == 210.00) {
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "A budget item in the current budget can be removed",
            () -> {
               try {
                  boolean rc = pf.removeBudgetItem("May '21", "Rent");
                  rc = rc && pf.getBudgets().size() == 4;
                  Map<String, ICategory> map = new HashMap<>();
                  Collection<ICategory> categories = pf.getCategories();
                  for (ICategory c: categories)
                     map.put(c.getName(), c);
                  for (IBudget b: pf.getBudgets()) {
                     if (b.getName().equals("May '21") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 29, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 2, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2000.00 &&
                           b.getItems().get(map.get("Rent")) == 600.00 &&
                           b.getItems().get(map.get("Groceries")) == 200.00) {
                     }
                     else if (b.getName().equals("March 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("March 31, 2021")) == 0 &&
                           b.getItems().get(map.get("Paycheck")) == 2100.00 &&
                           b.getItems().get(map.get("Rent")) == 510.00 &&
                           b.getItems().get(map.get("Groceries")) == 210.00) {
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
   }

   private static void testTransaction(IPersonalFinance pf, ArrayList<Test> tests) {
      tests.add(new Test(
            "The list of transactions is not null",
            () -> {
               try {
                  return pf.getTransactions(
                        DateFormat.getDateInstance().parse("March 1, 2021"),
                        DateFormat.getDateInstance().parse("March 31, 2021")
                  );
               } catch (ParseException e) {
                  e.printStackTrace();
                  return null;
               }
            }
      ));
      tests.add(new Test(
            "The number of transactions in any date range is initially 0",
            ()-> {
               try {
                  return pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2021"),
                        DateFormat.getDateInstance().parse("March 31, 2021")).size();
               } catch (ParseException e) {
                  e.printStackTrace();
                  return -1;
               }
            },
            0
      ));
      tests.add(new ExceptionTest(
            "Trying to modify the list of transactions is unsuccessful",
            ()-> {
               try {
                  return pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2021"),
                        DateFormat.getDateInstance().parse("March 1, 2021")).add(null);
               } catch (ParseException e) {
                  e.printStackTrace();
                  return true;
               }
            },
            new UnsupportedOperationException()
      ));
      tests.add(new Test(
            "Trying to get a list of transactions for null or invalid dates is unsuccessful",
            () -> {
               try {
                  boolean rc =  pf.getTransactions(DateFormat.getDateInstance().parse("March 31, 2021"),
                        DateFormat.getDateInstance().parse("March 1, 2021")).isEmpty();
                  rc = rc && pf.getTransactions(null, DateFormat.getDateInstance().parse("March 1, 2021")).isEmpty();
                  rc = rc && pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2021"), null).isEmpty();
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to add a new transaction with a null or future date is unsuccessful",
            () -> {
               try {
                  boolean rc = pf.addTransaction(null, "Kroger", 50.0, "Groceries", "Amex") == -1;
                  rc = rc && pf.addTransaction(DateFormat.getDateInstance().parse("August 1, 2021"),
                        "Kroger", 50.0, "Groceries", "Amex") == -1;
                  rc = rc && pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022")).size() == 0;
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to add a new transaction with a null or empty description is unsuccessful",
            () -> {
               try {
                  boolean rc = pf.addTransaction(DateFormat.getDateInstance().parse("March 1, 2021"),
                        "", 50.0, "Groceries", "Amex") == -1;
                  rc = rc && pf.addTransaction(DateFormat.getDateInstance().parse("March 1, 2021"),
                        null, 50.0, "Groceries", "Amex") == -1;
                  rc = rc && pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022")).size() == 0;
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to add a new transaction with a negative amount is unsuccessful",
            () -> {
               try {
                  boolean rc = pf.addTransaction(DateFormat.getDateInstance().parse("March 1, 2021"),
                        "Kroger", -50.0, "Groceries", "Amex") == -1;
                  rc = rc && pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022")).size() == 0;
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to add a new transaction which will negate the value in the associated account is unsuccessful",
            () -> {
               try {
                  boolean rc = pf.addTransaction(DateFormat.getDateInstance().parse("March 1, 2021"),
                        "Kroger", 50.0, "Groceries", "Chase Checking") == -1;
                  rc = rc && pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022")).size() == 0;
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to add a new transaction with a null or invalid category name is unsuccessful",
            () -> {
               try {
                  boolean rc = pf.addTransaction(DateFormat.getDateInstance().parse("March 1, 2021"),
                        "Kroger", 50.0, null, "Amex") == -1;
                  rc = rc && pf.addTransaction(DateFormat.getDateInstance().parse("March 1, 2021"),
                        "Kroger", 50.0, "Necessities", "Amex") == -1;
                  rc = rc && pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022")).size() == 0;
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to add a new transaction with a null or invalid account name is unsuccessful",
            () -> {
               try {
                  boolean rc = pf.addTransaction(DateFormat.getDateInstance().parse("March 1, 2021"),
                        "Kroger", 50.0, "Groceries", null) == -1;
                  rc = rc && pf.addTransaction(DateFormat.getDateInstance().parse("March 1, 2021"),
                        "Kroger", 50.0, "Groceries", "Capital One") == -1;
                  rc = rc && pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022")).size() == 0;
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "The first valid transaction to be added will have an id 0",
            () -> {
               try {
                  boolean rc = pf.addTransaction(DateFormat.getDateInstance().parse("March 1, 2021"),
                        "Kroger", 50.0, "Groceries", "Amex") == 0;
                  rc = rc && pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022")).size() == 1;
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "The second valid transaction to be added will have an id 1",
            () -> {
               try {
                  boolean rc = pf.addTransaction(DateFormat.getDateInstance().parse("March 2, 2021"),
                        "Whole Foods", 75.0, "Groceries", "Amex") == 1;
                  rc = rc && pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022")).size() == 2;
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "A number of valid transactions can be added and retrieved back",
            () -> {
               try {
                  boolean rc = pf.addTransaction(DateFormat.getDateInstance().parse("March 15, 2021"),
                        "LSUS PPD", 2000.0, "Paycheck", "CFCU") == 2;
                  rc = rc && pf.addTransaction(DateFormat.getDateInstance().parse("April 1, 2021"),
                        "UL Coleman", 500.0, "Rent", "CFCU") == 3;
                  Collection<ITransaction> transactions = pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022"));
                  rc = rc && transactions.size() == 4;
                  for (ITransaction i: transactions) {
                     if (i.getId() == 0) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 50.0;
                        rc = rc && i.getCategory().getName().equals("Groceries");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Kroger");
                     }
                     else if (i.getId() == 1) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 75.0;
                        rc = rc && i.getCategory().getName().equals("Groceries");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 2, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Whole Foods");
                     }
                     else if (i.getId() == 2) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 2000.0;
                        rc = rc && i.getCategory().getName().equals("Paycheck");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 15, 2021")) == 0;
                        rc = rc && i.getDescription().equals("LSUS PPD");
                     }
                     else if (i.getId() == 3) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 500.0;
                        rc = rc && i.getCategory().getName().equals("Rent");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("UL Coleman");
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to remove a transaction with an invalid id is unsuccessful",
            () -> {
               try {
                  boolean rc = !pf.removeTransaction(4);
                  Collection<ITransaction> transactions = pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022"));
                  rc = rc && transactions.size() == 4;
                  for (ITransaction i: transactions) {
                     if (i.getId() == 0) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 50.0;
                        rc = rc && i.getCategory().getName().equals("Groceries");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Kroger");
                     }
                     else if (i.getId() == 1) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 75.0;
                        rc = rc && i.getCategory().getName().equals("Groceries");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 2, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Whole Foods");
                     }
                     else if (i.getId() == 2) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 2000.0;
                        rc = rc && i.getCategory().getName().equals("Paycheck");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 15, 2021")) == 0;
                        rc = rc && i.getDescription().equals("LSUS PPD");
                     }
                     else if (i.getId() == 3) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 500.0;
                        rc = rc && i.getCategory().getName().equals("Rent");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("UL Coleman");
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to remove a transaction with a valid id is successful",
            () -> {
               try {
                  boolean rc = pf.removeTransaction(0);
                  Collection<ITransaction> transactions = pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022"));
                  rc = rc && transactions.size() == 3;
                  for (ITransaction i: transactions) {
                     if (i.getId() == 1) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 75.0;
                        rc = rc && i.getCategory().getName().equals("Groceries");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 2, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Whole Foods");
                     }
                     else if (i.getId() == 2) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 2000.0;
                        rc = rc && i.getCategory().getName().equals("Paycheck");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 15, 2021")) == 0;
                        rc = rc && i.getDescription().equals("LSUS PPD");
                     }
                     else if (i.getId() == 3) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 500.0;
                        rc = rc && i.getCategory().getName().equals("Rent");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("UL Coleman");
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a transaction with an invalid id is unsuccessful",
            () -> {
               try {
                  boolean rc = !pf.updateTransactionAmount(4, 100.0);
                  rc = rc && !pf.updateTransactionCategory(5, "Rent");
                  rc = rc && !pf.updateTransactionDate(6, DateFormat.getDateInstance().parse("March 31, 2021"));
                  rc = rc && !pf.updateTransactionDescription(7, "Walmart");
                  Collection<ITransaction> transactions = pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022"));
                  rc = rc && transactions.size() == 3;
                  for (ITransaction i: transactions) {
                     if (i.getId() == 1) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 75.0;
                        rc = rc && i.getCategory().getName().equals("Groceries");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 2, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Whole Foods");
                     }
                     else if (i.getId() == 2) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 2000.0;
                        rc = rc && i.getCategory().getName().equals("Paycheck");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 15, 2021")) == 0;
                        rc = rc && i.getDescription().equals("LSUS PPD");
                     }
                     else if (i.getId() == 3) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 500.0;
                        rc = rc && i.getCategory().getName().equals("Rent");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("UL Coleman");
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a transaction with a null or future date is unsuccessful",
            () -> {
               try {
                  boolean rc = !pf.updateTransactionDate(2, null);
                  rc = rc && !pf.updateTransactionDate(1, DateFormat.getDateInstance().parse("August 31, 2022"));
                  Collection<ITransaction> transactions = pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022"));
                  rc = rc && transactions.size() == 3;
                  for (ITransaction i: transactions) {
                     if (i.getId() == 1) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 75.0;
                        rc = rc && i.getCategory().getName().equals("Groceries");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 2, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Whole Foods");
                     }
                     else if (i.getId() == 2) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 2000.0;
                        rc = rc && i.getCategory().getName().equals("Paycheck");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 15, 2021")) == 0;
                        rc = rc && i.getDescription().equals("LSUS PPD");
                     }
                     else if (i.getId() == 3) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 500.0;
                        rc = rc && i.getCategory().getName().equals("Rent");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("UL Coleman");
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a transaction with a valid id and date is successful",
            () -> {
               try {
                  boolean rc = !pf.updateTransactionDate(2, DateFormat.getDateInstance().parse("March 21, 2021"));
                  Collection<ITransaction> transactions = pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022"));
                  rc = rc && transactions.size() == 3;
                  for (ITransaction i: transactions) {
                     if (i.getId() == 1) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 75.0;
                        rc = rc && i.getCategory().getName().equals("Groceries");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 2, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Whole Foods");
                     }
                     else if (i.getId() == 2) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 2000.0;
                        rc = rc && i.getCategory().getName().equals("Paycheck");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 21, 2021")) == 0;
                        rc = rc && i.getDescription().equals("LSUS PPD");
                     }
                     else if (i.getId() == 3) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 500.0;
                        rc = rc && i.getCategory().getName().equals("Rent");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("UL Coleman");
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a transaction with a null or empty description is unsuccessful",
            () -> {
               try {
                  boolean rc = !pf.updateTransactionDescription(3, null);
                  rc = rc && !pf.updateTransactionDescription(3, "");
                  Collection<ITransaction> transactions = pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022"));
                  rc = rc && transactions.size() == 3;
                  for (ITransaction i: transactions) {
                     if (i.getId() == 1) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 75.0;
                        rc = rc && i.getCategory().getName().equals("Groceries");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 2, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Whole Foods");
                     }
                     else if (i.getId() == 2) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 2000.0;
                        rc = rc && i.getCategory().getName().equals("Paycheck");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 21, 2021")) == 0;
                        rc = rc && i.getDescription().equals("LSUS PPD");
                     }
                     else if (i.getId() == 3) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 500.0;
                        rc = rc && i.getCategory().getName().equals("Rent");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("UL Coleman");
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a transaction with a valid id and description is successful",
            () -> {
               try {
                  boolean rc = pf.updateTransactionDescription(3, "Riverview Apts.");
                  Collection<ITransaction> transactions = pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022"));
                  rc = rc && transactions.size() == 3;
                  for (ITransaction i: transactions) {
                     if (i.getId() == 1) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 75.0;
                        rc = rc && i.getCategory().getName().equals("Groceries");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 2, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Whole Foods");
                     }
                     else if (i.getId() == 2) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 2000.0;
                        rc = rc && i.getCategory().getName().equals("Paycheck");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 21, 2021")) == 0;
                        rc = rc && i.getDescription().equals("LSUS PPD");
                     }
                     else if (i.getId() == 3) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 500.0;
                        rc = rc && i.getCategory().getName().equals("Rent");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Riverview Apts.");
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a transaction with a negative amount is unsuccessful",
            () -> {
               try {
                  boolean rc = !pf.updateTransactionAmount(3, -500.0);
                  Collection<ITransaction> transactions = pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022"));
                  rc = rc && transactions.size() == 3;
                  for (ITransaction i: transactions) {
                     if (i.getId() == 1) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 75.0;
                        rc = rc && i.getCategory().getName().equals("Groceries");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 2, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Whole Foods");
                     }
                     else if (i.getId() == 2) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 2000.0;
                        rc = rc && i.getCategory().getName().equals("Paycheck");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 21, 2021")) == 0;
                        rc = rc && i.getDescription().equals("LSUS PPD");
                     }
                     else if (i.getId() == 3) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 500.0;
                        rc = rc && i.getCategory().getName().equals("Rent");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Riverview Apts.");
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a transaction with a valid id and amount is successful",
            () -> {
               try {
                  boolean rc = pf.updateTransactionAmount(3, 550.0);
                  Collection<ITransaction> transactions = pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022"));
                  rc = rc && transactions.size() == 3;
                  for (ITransaction i: transactions) {
                     if (i.getId() == 1) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 75.0;
                        rc = rc && i.getCategory().getName().equals("Groceries");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 2, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Whole Foods");
                     }
                     else if (i.getId() == 2) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 2000.0;
                        rc = rc && i.getCategory().getName().equals("Paycheck");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 21, 2021")) == 0;
                        rc = rc && i.getDescription().equals("LSUS PPD");
                     }
                     else if (i.getId() == 3) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 550.0;
                        rc = rc && i.getCategory().getName().equals("Rent");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Riverview Apts.");
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a transaction with a null or invalid category name is unsuccessful",
            () -> {
               try {
                  boolean rc = !pf.updateTransactionCategory(1, null);
                  rc = rc && !pf.updateTransactionCategory(1, "Necessities");
                  Collection<ITransaction> transactions = pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022"));
                  rc = rc && transactions.size() == 3;
                  for (ITransaction i: transactions) {
                     if (i.getId() == 1) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 75.0;
                        rc = rc && i.getCategory().getName().equals("Groceries");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 2, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Whole Foods");
                     }
                     else if (i.getId() == 2) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 2000.0;
                        rc = rc && i.getCategory().getName().equals("Paycheck");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 21, 2021")) == 0;
                        rc = rc && i.getDescription().equals("LSUS PPD");
                     }
                     else if (i.getId() == 3) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 550.0;
                        rc = rc && i.getCategory().getName().equals("Rent");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Riverview Apts.");
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Trying to update a transaction with a valid id and category name is successful",
            () -> {
               try {
                  boolean rc = pf.addCategory("Food", ICategory.TransactionType.EXPENDITURE);
                  rc = rc && pf.updateTransactionCategory(1, "Food");
                  Collection<ITransaction> transactions = pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022"));
                  rc = rc && transactions.size() == 3;
                  for (ITransaction i: transactions) {
                     if (i.getId() == 1) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 75.0;
                        rc = rc && i.getCategory().getName().equals("Food");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 2, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Whole Foods");
                     }
                     else if (i.getId() == 2) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 2000.0;
                        rc = rc && i.getCategory().getName().equals("Paycheck");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 21, 2021")) == 0;
                        rc = rc && i.getDescription().equals("LSUS PPD");
                     }
                     else if (i.getId() == 3) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 550.0;
                        rc = rc && i.getCategory().getName().equals("Rent");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Riverview Apts.");
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
   }

   private static void testIntegration(IPersonalFinance pf, ArrayList<Test> tests) {
      tests.add(new Test(
            "A category with a valid name can be removed",
            () -> {
               boolean rc = pf.removeCategory("Food");
               rc &= pf.removeCategory("Paycheck");
               for (ICategory a : pf.getCategories()) {
                  if (a.getName().equals("Rent") && a.getType() == ICategory.TransactionType.EXPENDITURE) {}
                  else rc = false;
               }
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "Existing transactions belonging to a removed category must be renamed",
            () -> {
               try {
                  Collection<ITransaction> transactions = pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022"));
                  boolean rc = transactions.size() == 3;
                  for (ITransaction i: transactions) {
                     if (i.getId() == 1) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 75.0;
                        rc = rc && i.getCategory().getName().equals("Miscellaneous Expenditure");
                        rc = rc && i.getCategory().getType() == ICategory.TransactionType.EXPENDITURE;
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 2, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Whole Foods");
                     }
                     else if (i.getId() == 2) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 2000.0;
                        rc = rc && i.getCategory().getName().equals("Miscellaneous Income");
                        rc = rc && i.getCategory().getType() == ICategory.TransactionType.INCOME;
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 21, 2021")) == 0;
                        rc = rc && i.getDescription().equals("LSUS PPD");
                     }
                     else if (i.getId() == 3) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 550.0;
                        rc = rc && i.getCategory().getName().equals("Rent");
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Riverview Apts.");
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "The name of an existing category can be updated",
            () -> {
               boolean rc = pf.updateCategoryName("Rent", "Mortgage");
               for (ICategory a : pf.getCategories()) {
                  if (a.getName().equals("Mortgage") && a.getType() == ICategory.TransactionType.EXPENDITURE) {}
                  else rc = false;
               }
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "The type of an existing category can be updated",
            () -> {
               boolean rc = pf.updateCategoryType("Mortgage", ICategory.TransactionType.INCOME);
               for (ICategory a : pf.getCategories()) {
                  if (a.getName().equals("Mortgage") && a.getType() == ICategory.TransactionType.INCOME) {}
                  else rc = false;
               }
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "Transactions in the updated category must reflect the change in name or type",
            () -> {
               try {
                  Collection<ITransaction> transactions = pf.getTransactions(DateFormat.getDateInstance().parse("March 1, 2020"),
                        DateFormat.getDateInstance().parse("March 31, 2022"));
                  boolean rc = transactions.size() == 3;
                  for (ITransaction i: transactions) {
                     if (i.getId() == 1) {
                        rc = rc && i.getAccount().getName().equals("Amex");
                        rc = rc && i.getAmount() == 75.0;
                        rc = rc && i.getCategory().getName().equals("Miscellaneous Expenditure");
                        rc = rc && i.getCategory().getType() == ICategory.TransactionType.EXPENDITURE;
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 2, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Whole Foods");
                     }
                     else if (i.getId() == 2) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 2000.0;
                        rc = rc && i.getCategory().getName().equals("Miscellaneous Income");
                        rc = rc && i.getCategory().getType() == ICategory.TransactionType.INCOME;
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("March 21, 2021")) == 0;
                        rc = rc && i.getDescription().equals("LSUS PPD");
                     }
                     else if (i.getId() == 3) {
                        rc = rc && i.getAccount().getName().equals("CFCU");
                        rc = rc && i.getAmount() == 550.0;
                        rc = rc && i.getCategory().getName().equals("Mortgage");
                        rc = rc && i.getCategory().getType() == ICategory.TransactionType.INCOME;
                        rc = rc && i.getDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0;
                        rc = rc && i.getDescription().equals("Riverview Apts.");
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "Budgets containing the updated category must reflect the change in name or type",
            () -> {
               try {
                  boolean rc = pf.getBudgets().size() == 4;
                  Map<String, ICategory> map = new HashMap<>();
                  Collection<ICategory> categories = pf.getCategories();
                  for (ICategory c: categories)
                     map.put(c.getName(), c);
                  for (IBudget b: pf.getBudgets()) {
                     if (b.getName().equals("May '21") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("May 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("May 29, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("June 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("June 2, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("June 30, 2021")) == 0 &&
                           b.getItems().isEmpty()) {
                     }
                     else if (b.getName().equals("April 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("April 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("April 30, 2021")) == 0 &&
                           b.getItems().get(map.get("Mortgage")) == 600.00) {
                     }
                     else if (b.getName().equals("March 2021") &&
                           b.getStartDate().compareTo(DateFormat.getDateInstance().parse("March 1, 2021")) == 0 &&
                           b.getEndDate().compareTo(DateFormat.getDateInstance().parse("March 31, 2021")) == 0 &&
                           b.getItems().get(map.get("Mortgage")) == 510.00) {
                     }
                     else rc = false;
                  }
                  return rc;
               } catch (ParseException e) {
                  e.printStackTrace();
                  return false;
               }
            },
            true
      ));
      tests.add(new Test(
            "A new transaction will reuse a a transaction id when possible",
            () -> {
               boolean rc = pf.addCategory("Interest income", ICategory.TransactionType.INCOME);
               rc = rc && pf.addCategory("Interest due", ICategory.TransactionType.EXPENDITURE);
               IAccount ac1 = pf.getCreditCards().iterator().next();
               IAccount ac2 = pf.getDebitAccounts().iterator().next();
               double amount1 = ac1.getValue(), amount2 = ac2.getValue();
               try {
                  rc = rc && pf.addTransaction(DateFormat.getDateInstance().parse("March 1, 2021"),
                        "Interest", 2.0, "Interest due", ac1.getName()) == 0;
                  rc = rc && pf.addTransaction(DateFormat.getDateInstance().parse("March 1, 2021"),
                        "Interest", 2.0, "Interest income", ac2.getName()) == 4;
               } catch (ParseException e) {
                  e.printStackTrace();
               }
               return rc;
            },
            true
      ));
      tests.add(new Test(
            "Every time a transaction is added, the value in the associated account will be changed accordingly.",
            () -> {
               IAccount ac1 = pf.getCreditCards().iterator().next();
               IAccount ac2 = pf.getDebitAccounts().iterator().next();
               double amount1 = ac1.getValue(), amount2 = ac2.getValue();
               boolean rc = true;
               try {
                  rc = rc && pf.addTransaction(DateFormat.getDateInstance().parse("March 2, 2021"),
                        "Interest", 2.0, "Interest due", ac1.getName()) == 0;
                  rc = rc && pf.addTransaction(DateFormat.getDateInstance().parse("March 2, 2021"),
                        "Interest", 2.0, "Interest income", ac2.getName()) == 4;
               } catch (ParseException e) {
                  e.printStackTrace();
               }
               rc = rc && ac1.getValue() == amount1 - 2.0;
               rc = rc && ac2.getValue() == amount2 + 2.0;
               return rc;
            },
            true
      ));
   }
}
