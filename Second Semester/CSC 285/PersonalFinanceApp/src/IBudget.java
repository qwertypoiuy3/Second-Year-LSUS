import java.util.Date;
import java.util.Map;

/**
 * This interface must be implemented by the user-defined Budget class
 */
public interface IBudget {
   /**
    * @return - the name of the budget
    */
   String getName();

   /**
    * @return - the start date of the budget
    */
   Date getStartDate();

   /**
    * @return - the end date of the budget
    */
   Date getEndDate();

   /**
    * @return - a map of items in the budget. The map consists of categories and the corresponding amounts.
    */
   Map<ICategory, Double> getItems();
}
