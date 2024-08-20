/**
 * This interface must be implemented by the user-defined Category class
 */
public interface ICategory {
   enum TransactionType {INCOME, EXPENDITURE}

   /**
    * @return - the name of the category
    */
   String getName();

   /**
    * @return - the transaction type of the category
    */
   TransactionType getType();
}