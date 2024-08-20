import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;


public class PersonalFinance implements IPersonalFinance{
    
    Cash cash = Cash.getInstance(0);
    CreditCard credit;
    ArrayList<CreditCard> creditList = new ArrayList(0);
    DebitCard debit; 
    ArrayList<DebitCard> debitList = new ArrayList(0);
    Category category;
    ArrayList<Category> categoryList = new ArrayList(0);
    Budget budget;
    ArrayList<Budget> budgetList = new ArrayList(0);
    Transaction transaction;
    ArrayList<Transaction> transactionList = new ArrayList(0);
    
    @Override
    public double getCash() {
        System.out.print("Cash: $");
        return cash.getValue();
    }

    @Override
    public boolean updateCash(double newAmount) {
        if(newAmount <= -1)
            return false;
        else
            cash.setValue(newAmount);
        return true;
    }

    @Override
    public Collection<IAccount> getCreditCards() {
        ArrayList<IAccount> o = new ArrayList();
        for(int i = 0; i < creditList.size(); i++)
        {
            CreditCard a = creditList.get(i);
            o.add(a);
        }
        System.out.println("Credit Accounts: ");
        return o;
    }

    @Override
    public boolean addCreditCard(String name, double debt) {
        credit = CreditCard.getInstance(name, debt);
        for(int i = 0; i < creditList.size(); i++) {
            if(creditList.get(i).getName().equals(name))
                return false;
        }
        if(debt <= -1 || name == null)
            return false;
        
        creditList.add(credit);
        return true;   
    }

    @Override
    public boolean removeCreditCard(String name) {
        for(int i = 0; i < creditList.size(); i++)
        {
            if(creditList.get(i).getName().equals(name))
            {
                creditList.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateCreditCardName(String name, String newName) {
        if(newName == null)
            return false;
        for(int i = 0; i < creditList.size(); i++)
        {
            if(creditList.get(i).getName().equals(name))
            {
                CreditCard a = creditList.get(i);
                a.setName(newName);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateCreditCardDebt(String name, double newDebt) {
        if(newDebt <= -1)
            return false;
        for(int i = 0; i < creditList.size(); i++)
        {
            if(creditList.get(i).getName().equals(name))
            {
                CreditCard a = creditList.get(i);
                a.setDebt(newDebt);
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<IAccount> getDebitAccounts() {
        ArrayList<IAccount> o = new ArrayList();
        for(int i = 0; i < debitList.size(); i++)
        {
            DebitCard a = debitList.get(i);
            o.add(a);
        }
        System.out.println("Debit Accounts: ");
        return o;
    }

    @Override
    public boolean addDebitAccount(String name, double balance) {
        debit = DebitCard.getInstance(name, balance);
        for(int i = 0; i < debitList.size(); i++) {
            if(debitList.get(i).getName().equals(name))
                return false;
        }
        if(name == null || balance <= -1)
            return false;
        
        debitList.add(debit);
        return true;
    }

    @Override
    public boolean removeDebitAccount(String name) {
        for(int i = 0; i < debitList.size(); i++)
        {
            if(debitList.get(i).getName().equals(name))
            {
                debitList.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateDebitAccountName(String name, String newName) {
        if(newName == null)
            return false;
        for(int i = 0; i < debitList.size(); i++) {
            if(debitList.get(i).getName().equals(newName))
                return false;
        }
        for(int i = 0; i < debitList.size(); i++) {
            if(debitList.get(i).getName().equals(name)) {
                debit = debitList.get(i);
                debit.setName(newName);
            }
        }
        return false;
    }

    @Override
    public boolean updateDebitAccountBalance(String name, double newBalance) {
        if(newBalance <= -1)
            return false;
        for(int i = 0; i < debitList.size(); i++) {
            if(debitList.get(i).getName().equals(name)) {
                DebitCard a = debitList.get(i);
                a.setBalance(newBalance);
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<ICategory> getCategories() {
        ArrayList<ICategory> o = new ArrayList();
        for(int i = 0; i < categoryList.size(); i++)
        {
            Category a = categoryList.get(i);
            o.add(a);
        }
        System.out.println("Categories: ");
        return o;
    }

    @Override
    public boolean addCategory(String name, ICategory.TransactionType transactionType) {
        if(name == null || transactionType == null)
            return false;
        
        category = Category.getInstace(name, transactionType);
        for(int i = 0; i < categoryList.size(); i++) {
            if(categoryList.get(i).getName().equals(name))
                return false;
        }
        categoryList.add(category);
        return true;
    }

    @Override
    public boolean removeCategory(String name) {
        for(int i = 0; i < categoryList.size(); i++)
        {
            if(categoryList.get(i).getName().equals(name))
            {
                categoryList.remove(i);
                return true;
            }
        }

        
        return false;
    }

    @Override
    public boolean updateCategoryName(String name, String newName) {
        if(newName == null || newName.equals("null"))
            return false;
        for(int i = 0; i < categoryList.size(); i++) {
            if(categoryList.get(i).getName().equals(newName))
                return false;
        }
        for(int i = 0; i < categoryList.size(); i++) {
            if(categoryList.get(i).getName().equals(name)) {
                category = categoryList.get(i);
                category.setName(newName);
            }
        }
        return false;
    }

    @Override
    public boolean updateCategoryType(String name, ICategory.TransactionType newType) {
        if(newType == null)
            return false;
        
        for(int i = 0; i < categoryList.size(); i++)
        {
            if(categoryList.get(i).getName().equals(name))
            {
                Category a = categoryList.get(i);
                a.setType(newType);
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<IBudget> getBudgets() {
        ArrayList<IBudget> o = new ArrayList();
        for(int i = 0; i < budgetList.size(); i++)
        {
            Budget a = budgetList.get(i);
            o.add(a);
        }
        System.out.println("Budgets: ");
        return o;
    }

    @Override
    public boolean addBudget(String name, Date startDate, Date endDate, Map<String, Double> items) {
        if(startDate == null || endDate == null || name == null )
            return false;
        
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM, d");
        
        budget = Budget.getInstace(name, startDate, endDate, items);
        for(int i = 0; i < budgetList.size(); i++) {
            if(budgetList.get(i).getName().equals(name))
                return false;
        }
       
            
        budgetList.add(budget);
        return true;
    }

    @Override
    public boolean removeBudget(String name) {
        for(int i = 0; i < budgetList.size(); i++)
        {
            if(budgetList.get(i).getName().equals(name))
            {
                budgetList.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateBudgetName(String name, String newName) {
        if(newName == null)
            return false;
        
        for(int i = 0; i < budgetList.size(); i++) {
            if(budgetList.get(i).getName().equals(newName)) {
                return false;
            }
        }
        
        for(int i = 0; i < budgetList.size(); i++) {
            if(budgetList.get(i).getName().equals(name)) {
                budget = budgetList.get(i);
                budget.setName(newName);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateBudgetStartDate(String name, Date newStartDate) {
        if(newStartDate.after(budget.getEndDate()) || newStartDate == null)
            return false;
        
        for(int i = 0; i < budgetList.size(); i++) {
            if(budgetList.get(i).getName().equals(name)) {
                Budget a = budgetList.get(i);
                a.setStartDate(newStartDate);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateBudgetEndDate(String name, Date newEndDate) {
        
        if(newEndDate.before(budget.getStartDate()) || newEndDate == null)
            return false;
        
        for(int i = 0; i < budgetList.size(); i++) {
            if(budgetList.get(i).getName().equals(name)) {
                Budget a = budgetList.get(i);
                a.setEndDate(newEndDate);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addOrUpdateBudgetItem(String name, String categoryName, double value) {
        
        if(categoryName == null || value <= -1)
            return false;
        
        for(int i = 0; i < budgetList.size(); i++) {
            if(budgetList.get(i).getName().equals(name)) {
                Budget a = budgetList.get(i);
                
                Map b = a.getItems();
                b.put(categoryName, value);
                a.setItems(b);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeBudgetItem(String name, String categoryName) {
        if(categoryName == null)
            return false;
        
        for(int i = 0; i < budgetList.size(); i++) {
            if(budgetList.get(i).getName().equals(name)) {
                Budget a = budgetList.get(i);
                a.getItems().remove(categoryName);
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<ITransaction> getTransactions(Date startDate, Date endDate) {
        
        ArrayList<ITransaction> o = new ArrayList();
        for(int i = 0; i < transactionList.size(); i++) {
            Transaction a = transactionList.get(i);
            if(a.getDate().after(startDate) && a.getDate().before(endDate))
                    o.add(a);
        }
        System.out.println("Transactions: ");
        return o;
    }

    @Override
    public int addTransaction(Date date, String description, double amount, String categoryName, String accountName) {
        
        if(description == null || description.isEmpty() || amount <= -1 ||
                categoryName == null || accountName == null || date == null)
            return -1;
        
        for(int i = 0; i < creditList.size(); i++) {
            credit = creditList.get(i);
            if(creditList.get(i).getName().equals(accountName)) {
                double a = credit.getValue() - amount;
                if(a <= -1)
                    return -1;
                else
                    credit.setDebt(a);
            }
        }
        for(int i = 0; i < debitList.size(); i++) {
            debit = debitList.get(i);
            if(debitList.get(i).getName().equals(accountName)) {
                double a = debit.getValue() - amount;
                if(a <= -1)
                    return -1;
                else
                    debit.setBalance(a);
            }
        }
     
        transaction = Transaction.getInstace(date, description, amount, categoryName, accountName);
        transactionList.add(transaction);
        for(int i = 0; i < transactionList.size(); i++) {
            transaction.setId(i);
        }
        return transaction.getId();
    }

    @Override
    public boolean removeTransaction(int id) {
        if(id <= -1)
            return false;
        for(int i = 0; i < transactionList.size(); i++) {
            if(transactionList.get(i).getId() == id) {
                transactionList.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateTransactionDate(int id, Date newDate) {
                       
        for(int i = 0; i < transactionList.size(); i++) {
            transaction = transactionList.get(i);
            if(transactionList.get(i).getId() == id)
            {
                transaction.setDate(newDate);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateTransactionDescription(int id, String newDescription) {
        
        if(newDescription == null || newDescription.isEmpty() || newDescription.startsWith(" "))
            return false;
        
        for(int i = 0; i < transactionList.size(); i++) {
            if(transactionList.get(i).getId() == id) {
                Transaction a = transactionList.get(i);
                a.setDescription(newDescription);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateTransactionAmount(int id, double newAmount) {
        if(newAmount <= -1)
            return false;
        
        for(int i = 0; i < transactionList.size(); i++) {
            if(transactionList.get(i).getId() == id) {
                transaction = transactionList.get(i);
                transaction.setAmount(newAmount);
                return true;
            }
        }        
        return false;
    }

    @Override
    public boolean updateTransactionCategory(int id, String newCategoryName) {
        
        if(newCategoryName == null)
            return false;
                
        for(int i = 0; i < categoryList.size(); i++) {
            category = categoryList.get(i);
            if(!category.getName().equals(newCategoryName)) {
                return false;
            }
        }
        
        for(int i = 0; i < transactionList.size(); i++) {
            if(transactionList.get(i).getId() == id) {
                transaction = transactionList.get(i);
                transaction.setCategory(newCategoryName);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean getStatus(String budgetName, Map<ICategory, Double> projected, Map<ICategory, Double> actual) {
        ArrayList<Double> projectedList = new ArrayList();
        ArrayList<Double> actualList = new ArrayList();
        
        double projectedValue = 0;
        double actualValue = 0;
        
        if(budgetName == null)
            return false;
        
        for(int i = 0; i < budgetList.size(); i++) {
            if(budgetList.get(i).getName().equals(budgetName)) {
                projectedList.addAll(projected.values());
                actualList.addAll(actual.values());
            }  
        }
        
        for(int i = 0; i < projectedList.size(); i++)
            projectedValue += projectedList.get(i);
        for(int i = 0; i < actualList.size(); i++)
            actualValue += actualList.get(i);
        
        DecimalFormat round = new DecimalFormat("###,###.##");
        
        if(projectedValue > actualValue) {
            System.out.println("Status: Within Budget\nProjected expenditure: $" + 
                    round.format(projectedValue) + "\nActual expenditure: $" + 
                    round.format(actualValue));
            return true;
        }
        else
            System.out.println("Status: Exceeded Budget\nProjected expenditure: $" + 
                    round.format(projectedValue) + "\nActual expenditure: $" + 
                    round.format(actualValue));
        
        return false;
    }  
}