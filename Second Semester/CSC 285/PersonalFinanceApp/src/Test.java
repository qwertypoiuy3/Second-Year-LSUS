import java.util.function.Supplier;

public class Test<T> {
   private static final String ANSI_RED_BOLD = "\u001B[1;31m";
   private static final String ANSI_GREEN_BOLD = "\u001B[1;32m";
   private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
   private static final String ANSI_BLUE_UNDERLINED = "\033[4;34m";   // BLUE
   private static final String ANSI_RESET = "\u001B[0m";

   protected static void printResult(String test, boolean hasPassed) {
      printResult(test, hasPassed, null);
   }
   protected static void printResult(String test, boolean hasPassed, String msg) {
      String out = ANSI_BLUE_UNDERLINED + "Condition:" + ANSI_RESET + " " + test;
      if (msg != null) out += " -> " +
            ANSI_YELLOW_BACKGROUND +
            msg + ANSI_RESET;

      if (hasPassed)
         System.out.println(ANSI_GREEN_BOLD + "[PASS] " + out);
      else
         System.out.println(ANSI_RED_BOLD + "[FAIL] " + out);
   }

   protected final String description;
   protected final Supplier<T> testFor;
   private final T expected;
   private final boolean testNotNull;
   private Test(String description, Supplier<T> testFor, T expect, boolean testNotNull) {
      this.description = description;
      this.testFor = testFor;
      this.expected = expect;
      this.testNotNull = testNotNull;
   }
   public Test(String description, Supplier<T> testFor, T expect) {
      this(description, testFor, expect, false);
   }

   public Test(String description, Supplier<T> testFor) {
      this(description, testFor, null, true);
   }

   public void run() {
      try {
         T received = testFor.get();
         if (testNotNull)
            printResult(description, received != null);
         else printResult(description, received.equals(expected));
      } catch (Exception e) {
         printResult(description, false,
               String.format("%s at %s", e.toString(), e.getStackTrace()[0]));
      }
   }
}
