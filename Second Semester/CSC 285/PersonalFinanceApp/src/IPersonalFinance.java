import java.util.Date;
import java.util.Map;
import java.util.Collection;

/**
 * This interface should be implemented by your personal finance app facade
 */
public interface IPersonalFinance {
   /**
    * Display cash at hand
    * @return the cash amount
    */
   double getCash();

   /**
    * Update the cash at hand
    * @param newAmount the new cash amount
    * @return true if the cash amount was updated successfully, false otherwise (invalid values etc)
    */
   boolean updateCash(double newAmount);

   /**
    * Get all credit cards
    * @return An unmodifiable collection (such as arraylist) of objects that implement the {@link IAccount} interface
    */
   Collection<IAccount> getCreditCards();

   /**
    * Add a new credit card
    * @param name - the name of the card (must be unique)
    * @param debt - the debt in the card
    * @return true if the card was successfully added, false otherwise (if the card name is not unique for example)
    */
   boolean addCreditCard(String name, double debt);

   /**
    * Remove a credit card and all associated transactions
    * @param name - the name of the credit card to remove
    * @return true if the card was removed successfully, false otherwise (card not found etc.)
    */
   boolean removeCreditCard(String name);

   /**
    * Update the name of an existing credit card
    * @param name - name of the credit card to be updated
    * @param newName - the new name (must be unique)
    * @return true if the card was updated successfully, false otherwise (card not found, new name not unique, etc)
    */
   boolean updateCreditCardName(String name, String newName);

   /**
    * Update the debt of an existing credit card
    * @param name - name of the credit card to be updated
    * @param newDebt - the new debt
    * @return true if the card was updated successfully, false otherwise (card not found etc)
    */
   boolean updateCreditCardDebt(String name, double newDebt);

   /**
    * Get all debit accounts
    * @return An unmodifiable collection (such as arraylist) of objects that implement the {@link IAccount} interface
    */
   Collection<IAccount> getDebitAccounts();

   /**
    * Add a new debit account
    * @param name - the name of the debit account (must be unique)
    * @param balance - the balance in the account
    * @return - true if the account was successfully added, false otherwise (name not unique etc)
    */
   boolean addDebitAccount(String name, double balance);

   /**
    * Remove a debit account and all associated transactions
    * @param name - name of the account to remove
    * @return - true if the account was removed successfully, false otherwise (account not found etc)
    */
   boolean removeDebitAccount(String name);

   /**
    * Update the name of an existing bank account
    * @param name - name of the debit account to be updated
    * @param newName - the new name (must be unique)
    * @return - true if the update was successful, false otherwise (name not unique etc)
    */
   boolean updateDebitAccountName(String name, String newName);

   /**
    * Update the balance of an existing bank account
    * @param name - name of the debit account to be updated
    * @param newBalance - the new balance
    * @return true if the update was successful, false otherwise (name not unique etc)
    */
   boolean updateDebitAccountBalance(String name, double newBalance);

   /**
    * Get all categories present
    * @return An unmodifiable collection (such as arraylist) of objects that implement the {@link ICategory} interface
    */
   Collection<ICategory> getCategories();

   /**
    * Add a new category
    * @param name - the name of the category (must be unique)
    * @param transactionType - the type ({@link ICategory.TransactionType#INCOME},
    *                        {@link ICategory.TransactionType#EXPENDITURE})
    *                        of transactions that belong to this category
    * @return true if the category was added successfully, false otherwise (name not unique etc)
    */
   boolean addCategory(String name, ICategory.TransactionType transactionType);

   /**
    * Remove a category and categorize all associated transactions as
    * "Miscellaneous Income" or "Miscellaneous Expenditure" based on the transaction type of the category
    * @param name - the name of the category to be removed
    * @return true if the category was successfully removed, false otherwise (not found etc)
    */
   boolean removeCategory(String name);

   /**
    * Update the name of a category
    * @param name - the name of the category to be updated
    * @param newName - the new name of the category
    * @return true if the category was added successfully, false otherwise (name not found etc)
    */
   boolean updateCategoryName(String name, String newName);

   /**
    * Update the transaction type of a category
    * @param name - the name of the category to be updated
    * @param newType - the new transaction type ({@link ICategory.TransactionType}) of the category
    * @return true if the category was successfully updated, false otherwise (name not found etc)
    */
   boolean updateCategoryType(String name, ICategory.TransactionType newType);

   /**
    * Get all budgets
    * @return An unmodifiable collection (such as arraylist) of objects that implement the {@link IBudget} interface
    */
   Collection<IBudget> getBudgets();

   /**
    * Add a new budget
    * @param name - the name of the budget (must be unique)
    * @param startDate - the start date of the budget
    * @param endDate - the end date of the budget
    * @param items - a map (key-value pair) of category-names and their corresponding projected income/expenditure.
    *              To learn how to use the Map interface, visit the official tutorial at
    *              https://docs.oracle.com/javase/tutorial/collections/interfaces/map.html
    * @return true if the budget was added successfully, false otherwise (name not unique, invalid dates, etc)
    */
   boolean addBudget(String name, Date startDate, Date endDate, Map<String, Double> items);

   /**
    * Remove an existing budget
    * @param name - the name of the budget to remove
    * @return true if the budget was removed successfully, false otherwise (not found etc)
    */
   boolean removeBudget(String name);

   /**
    * Update the name of a budget
    * @param name - the current name of the budget to be updated
    * @param newName - the new name (must be unique)
    * @return true if the budget name was successfully updated, false otherwise (name not found, new name not unique, etc)
    */
   boolean updateBudgetName(String name, String newName);

   /**
    * Update the start date of the budget
    * @param name - the name of the budget to be updated
    * @param newStartDate - the new start date of the budget
    * @return true if the budget's start date was successfully updated, false otherwise (name not found, invalid start date etc)
    */
   boolean updateBudgetStartDate(String name, Date newStartDate);

   /**
    * Update the end date of the budget
    * @param name - the name of the budget to be updated
    * @param newEndDate - the new end date of the budget
    * @return true if the budget's end date was successfully updated, false otherwise (name not found, invalid end date etc)
    */
   boolean updateBudgetEndDate(String name, Date newEndDate);

   /**
    * Update the projected income/expenditure of a category in the budget or
    * add a new (category, projected amount) pair in the if the category doesn't exist in the current budget
    * @param name - the name of the budget to be updated
    * @param categoryName - the name of the category of the budget item to be updated or added
    * @param value - the projected income/expenditure in the specified category
    * @return true if the budget item was successfully added/updated, false otherwise (name not found etc)
    */
   boolean addOrUpdateBudgetItem(String name, String categoryName, double value);

   /**
    * Remove a budget category and the corresponding projected income/expenditure from the budget
    * @param name - the name of the budget to be updated
    * @param categoryName - the name of the category to be removed
    * @return true if the budget name was successfully updated, false otherwise (name not found, categoryName etc)
    */
   boolean removeBudgetItem(String name, String categoryName);

   /**
    * Get all transactions between start date and end date (inclusive of both)
    * @param startDate - the start of the date range
    * @param endDate - the end of the date range
    * @return An unmodifiable collection (such as arraylist) of objects that implement the {@link ITransaction} interface
    */
   Collection<ITransaction> getTransactions(Date startDate, Date endDate);

   /**
    * Add a new transaction and assign it a unique auto-generated id. The id starts from 0 and gets incremented every time
    * a new transaction is added.
    * @param date - the date of the transaction
    * @param description - a description of the transaction
    * @param amount - the amount of the transaction
    * @param categoryName - the name of the category object associated with this transaction
    * @param accountName - the name of the account object to which the transaction is linked
    * @return - the id (greater than or equal to 0) of the newly created transaction or -1 if the transaction couldn't be created
    * (due to invalid arguments)
    */
   int addTransaction(Date date, String description, double amount, String categoryName, String accountName);

   /**
    * Remove the transaction with the id specified. This id is available for reuse and the next transaction
    * that is created will be assigned this id.
    * @param id - the id of the transaction to remove
    * @return true if the transaction was removed successfully, false otherwise (transaction not found etc)
    */
   boolean removeTransaction(int id);

   /**
    * Update the date of a transaction
    * @param id - the id of the transaction to update
    * @param newDate - the new date of the transaction
    * @return - true if the transaction was successfully updated, false otherwise (invalid id etc)
    */
   boolean updateTransactionDate(int id, Date newDate);

   /**
    * Update the description of a transaction
    * @param id - the id of the transaction to update
    * @param newDescription - the new description of the transaction
    * @return true if the transaction was successfully updated, false otherwise (invalid id etc)
    */
   boolean updateTransactionDescription(int id, String newDescription);

   /**
    * Update the amount of a transaction
    * @param id - the id of the transaction to update
    * @param newAmount - the new amount of the transaction
    * @return true if the transaction was successfully updated, false otherwise (invalid id etc)
    */
   boolean updateTransactionAmount(int id, double newAmount);

   /**
    * Update the transaction category
    * @param id - the id of the transaction to update
    * @param newCategoryName - the name of the new category of the transaction
    * @return true if the transaction was updated successfully, false otherwise (invalid id etc)
    */
   boolean updateTransactionCategory(int id, String newCategoryName);

   /**
    * Get the budgeted income/expenditure vs actual income/expenditure during the budget's date-range
    * @param budgetName - the name of the budget which which the actual income/expenditure is compared
    * @param projected - an empty map. This map will be filled up by this function with
    *                  categories (objects implementing the {@link ICategory} interface)
    *                  and projected incomes/expenditures (double).
    * @param actual - an empty map. This map will be filled up by this function with
    *               categories and actual incomes/expenditures
    * @return true if the map objects were successfully filled, false otherwise
    * (budget with the specified name couldn't be found etc)
    */
   boolean getStatus(String budgetName, Map<ICategory, Double> projected, Map<ICategory, Double> actual);
}
