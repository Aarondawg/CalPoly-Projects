public class Lab1A
{
   private static int passed = 0;
   private static int failed = 0;
   private static int totalPoints = 0;

   public static void main(String[] args) 
   {  
      try  // Used to catch unexpected exceptions and report partial grade
      {
      System.out.println();
      System.out.println("BEGIN TESTING: Number in parentheses represents the point value");
      System.out.println("of the test case.");
      System.out.println();

      Stack s = new Stack(10);
      int i;
      System.out.println("The following lines show the incremental building of a " +
         "stack using "); 
      System.out.println("the items 1..10. The top of the stack is on the left.");
      for(i=1;i<=10;++i)
      {
         s.push(new Integer(i));
         System.out.println(s);
      }
      reportTest("1",1,s.capacity()==10);
      reportTest("2",1,s.height()==10);
      s.push(new Integer(11));
      reportTest("3",1,s.capacity()>=15);
      reportTest("4",1,s.height()==11);
      int oldCapacity = s.capacity();

      for(i=12;i<=15;++i)
         s.push(new Integer(i));
      reportTest("5",1,s.capacity()==oldCapacity);
      Stack s2 = (Stack) s.clone();
      reportTest("6",1,s.capacity()==s2.capacity());
      reportTest("7",1,s.height()==s2.height());
      reportTest("8",1,s.equals(s2)&&s2.equals(s));
      s2.push(new Integer(16));
      reportTest("9",1,!(s.equals(s2)||s2.equals(s)));
      s.push(new Integer(16));
      reportTest("10",1,s.equals(s2)&&s2.equals(s));
      s = new Stack();
      s.push(new Integer(1));
      s2 = new Stack();
      s2.push(s);
      reportTest("11",1,!s2.equals(s));
      reportTest("12",1,!s2.equals(new Integer(12)));
      reportTest("13",1,!s2.equals(null));
      reportTest("14",1,s2.equals(s2));
      boolean caught;
      s = new Stack();
      int defaultCapacity = s.capacity();
      try
      {
         for(i=0;i<defaultCapacity*defaultCapacity;++i)
            s.push(new Integer(i));
         caught = false;
      }
      catch (Exception e)
      {
         caught = true;
      }
      reportTest("15",1,!caught);
      s = new Stack(5);
      s2 = new Stack(10);
      reportTest("16",1,s.equals(s2)&&s2.equals(s));

      }  // End of wrapping try block for all of main

      catch (Exception e)
      {
         System.out.println();
         System.out.println(
            "ERROR: Testing is being aborted due to the following unexpected exception.");
       
         // NOTE: The following information is written to System.err and will not be
         // seen if Standard.out is piped to more.

         e.printStackTrace();
         System.out.println();
         System.out.println("INCOMPLETE GRADE");
         System.out.println("   Points Passed.......... " + passed);
         System.exit(1);
      }
      printSummary();
   }
   
   private static void reportTest(String testID,int weight,boolean passedTest)
   {
      totalPoints += weight;
      if (passedTest)
      {
         passed += weight;
         System.out.println("Passed test case " + testID + " (" + weight + ")");
      }
      else
      {
         failed += weight;
         System.out.println("FAILED test case " + testID + " (" + weight + ")");
      }
   }

   private static void printSummary()
   {
      double pct = 100.0*passed/totalPoints;
      int points = (int)Math.round(pct/100.0*25);

      System.out.println();
      System.out.println("TESTING SUMMARY");
      System.out.println("   Total Points...... " + totalPoints);
      System.out.println("   Passed............ " + passed);
      System.out.println("   Failed............ " + failed);
      System.out.println("   Grade............. " + Math.round(pct) + "% = " + 
                                                    points + "/25");
   }
}
