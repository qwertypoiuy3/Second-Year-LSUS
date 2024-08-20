import java.util.function.Supplier;

public class ExceptionTest<T> extends Test<T>{
   private Exception e;
   public ExceptionTest(String description, Supplier<T> testFor, Exception e) {
      super(description, testFor);
      this.e = e;
   }
   @Override
   public void run() {
      try {
         T received = testFor.get();
         printResult(description, false);
      } catch (Exception e) {
         if (e.getClass().getName().equals(this.e.getClass().getName()))
            printResult(description, true);
         printResult(description, false,
               String.format("%s at %s", e.toString(), e.getStackTrace()[0]));
      }
   }
}
