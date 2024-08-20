import java.util.Date;
import java.util.Map;

public class Budget implements IBudget {

    private String name;
    private Date startDate;
    private Date endDate;
    private Map map;
    private static Budget myBudget;
    private static int num = 1;
    
    private Budget(String name, Date startDate, Date endDate, Map<String, 
            Double> items) 
    {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        map = items;
    }
    
    
    public static Budget getInstace(String name, Date startDate, Date endDate, 
            Map<String, Double> items) {
        if(num > 0)
            myBudget = new Budget(name, startDate, endDate, items);
        num++;
        return myBudget;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public Map<ICategory, Double> getItems() {
        return map;
    }
    
    public void setItems(Map a) {
        map = a;
    }
    
    public void setName(String newName) {
        name = newName;
    }
    
    public void setStartDate(Date newDate) {
        startDate = newDate;
    }
 
    public void setEndDate(Date newDate) {
        endDate = newDate;
    }   
    
    public String toString() {
        return name + ": from " + startDate.getMonth() + "/"+ startDate.getDate() + 
                " to " + endDate.getMonth() + "/" + endDate.getDate() + "\nList:\t" + 
                map + "\n";
    }
}
