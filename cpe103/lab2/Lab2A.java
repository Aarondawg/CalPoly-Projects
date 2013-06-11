import java.util.*;

public class Lab2A
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

      LinkedList list = new LinkedList();
      int i,j,ctr;
      boolean caught;

      System.out.println("The following lines show the incremental building of a " +
         "list containing "); 
      System.out.println("the items 1..10 using addFirst.");
      for(i=1;i<=10;++i)
      {
         list.addFirst(new Integer(i));
         System.out.println(list);
      }
      reportTest("1",1,list.length()==10);
      reportTest("2",1,!list.isEmpty());
      ctr = 0;
      for(i=0;i<=11;++i)
         if (!list.contains(new Integer(i)))
            ++ctr;
      reportTest("3",1,ctr==2);

      list.clear();
      reportTest("4",1,list.length()==0);
      reportTest("5",1,list.isEmpty());
      ctr = 0;
      for(i=0;i<=11;++i)
         if (list.contains(new Integer(i)))
            ++ctr;
      reportTest("6",1,ctr==0);

      list = new LinkedList();
      System.out.println("The following lines show the incremental building of a " +
         "list containing "); 
      System.out.println("the items 1..10 using addLast.");
      for(i=1;i<=10;++i)
      {
         list.addLast(new Integer(i));
         System.out.println(list);
      }
      reportTest("7",1,list.length()==10);
      reportTest("8",1,!list.isEmpty());
      ctr = 0;
      for(i=0;i<=11;++i)
         if (!list.contains(new Integer(i)))
            ++ctr;
      reportTest("9",1,ctr==2);

      // Make sure there is a "last" instance variable

      list  = new LinkedList();
      System.out.println("Perhaps a 5 second pause - be patient.");
      boolean tooLong = false;
      java.util.Date start = new java.util.Date();
      java.util.Date stop;
      for(i=1;i<=100000&&!tooLong;++i)
      {
         list.addLast(new Integer(i));
         stop = new java.util.Date();
         if (stop.getTime()-start.getTime() > 5000)
            tooLong = true;
      }
      reportTest("10A",3,!tooLong);


      list = new LinkedList();
      System.out.println("The following lines show the incremental building of a " +
         "list containing "); 
      System.out.println("the items 1..10 using both addFirst and addLast.  The even " +
         "integers");
      System.out.println("should be printed first with 10 on the left."); 
      for(i=1;i<=10;++i)
      {
         if (i%2==0)
            list.addFirst(new Integer(i));
         else
            list.addLast(new Integer(i));
         System.out.println(list);
      }
      reportTest("11",1,list.length()==10);
      reportTest("12",1,!list.isEmpty());
      ctr = 0;
      for(i=0;i<=11;++i)
         if (!list.contains(new Integer(i)))
            ++ctr;
      reportTest("13",1,ctr==2);

      list = new LinkedList();
      Random g = new Random(123456789);

      for(i=1;i<=1000;++i)
      {
         int n = g.nextInt(200);
         Integer x = new Integer(n);
         if (n==100)
            list.clear();
         else if (!list.contains(x))
         {
            if (n%3==0)
               list.addFirst(x);
            else
               list.addLast(x);
         }
      } 
      reportTest("14",1,list.length()==94);
      
      list = new LinkedList();
      try
      {
         list.removeFirst();
         caught = false;
      }
      catch (LinkedList.Error e)
      {
         caught = true;
      }
      reportTest("15",1,caught);

      try
      {
         list.removeLast();
         caught = false;
      }
      catch (LinkedList.Error e)
      {
         caught = true;
      }
      reportTest("16",1,caught);

      for(i=0;i<20;++i)
         list.addFirst(new Integer(i));
      ctr =0;
      for(i=19;i>=0;--i)
         if(((Integer)list.removeFirst()).intValue() != i)
            ++ctr;
      reportTest("17",1,ctr==0);

      try
      {
         list.removeFirst();
         caught = false;
      }
      catch (LinkedList.Error e)
      {
         caught = true;
      }
      reportTest("18",1,caught);

      list = new LinkedList();
      for(i=0;i<20;++i)
         list.addLast(new Integer(i));
      ctr =0;
      for(i=19;i>=0;--i)
         if(((Integer)list.removeLast()).intValue() != i)
            ++ctr;
      reportTest("19",1,ctr==0);

      try
      {
         list.removeFirst();
         caught = false;
      }
      catch (LinkedList.Error e)
      {
         caught = true;
      }
      reportTest("20",1,caught);

      list = new LinkedList();
      for(i=0;i<20;++i)
         if (i%2 == 0)
            list.addFirst(new Integer(i));
         else
            list.addLast(new Integer(i));
      ctr =0;
      i = ((Integer)list.removeLast()).intValue();
      while(!list.isEmpty())
      {
         if (i%2 == 0)
            j = ((Integer)list.removeLast()).intValue();
         else
            j = ((Integer)list.removeFirst()).intValue();
         if (j != i-1)
            ++ctr;
         i = j;
      }
      reportTest("21",1,ctr==0);

      // Make sure equals satisfies the prologue criteria of Horstmann
      list = new LinkedList();
      reportTest("22",1,list.equals(list) && !list.equals(null) && !list.equals("list"));
 
      // Simple test cases for equals
      LinkedList list2 = new LinkedList();
      list.addFirst(new Integer(1));
      reportTest("23",1,!list.equals(list2)&&!list2.equals(list));
      list2.addLast(new Integer(1));
      reportTest("24A",1,list.equals(list2)&&list2.equals(list));
      list.removeFirst();
      reportTest("24B",1,!list.equals(list2)&&!list2.equals(list));
      list2.removeLast();
      reportTest("24C",1,list.equals(list2)&&list2.equals(list));
      list = new LinkedList();
      list2 = new LinkedList();
      list.addFirst(new Integer(1));
      list2.addLast(new Integer(1));
      list.addFirst(new Integer(2));
      list2.addLast(new Integer(2));
      reportTest("24D",1,!list.equals(list2)&&!list2.equals(list));
      list.addLast(new Integer(2));
      list2.addFirst(new Integer(2));
      reportTest("24E",1,list.equals(list2)&&list2.equals(list));

      // Simple test cases for searching linked list of linked lists
      list = new LinkedList();
      list2 = new LinkedList();
      list.addFirst(new Integer(1));
      list2.addFirst(list);
      reportTest("25",1,list2.contains(list));
      list.addLast(new Integer(1));
      reportTest("26",1,list2.contains(list));

      // Make sure that adding to an empty list correctly sets last in addFirst
      list = new LinkedList();
      list.addFirst(new Integer(1));
      Object x = list.removeLast();
      reportTest("27",1,x.equals(new Integer(1)));

      // Make sure that adding to an empty list correctly sets first in addLast
      list = new LinkedList();
      list.addLast(new Integer(1));
      x = list.removeFirst();
      reportTest("28",1,x.equals(new Integer(1)));

      // Make sure that removing the only item correctly sets first in removeFirst
      list = new LinkedList();
      list.addFirst(new Integer(1));
      list.removeFirst();
      list.addFirst(new Integer(2));
      reportTest("29",1,list.contains(new Integer(2)));

      // Make sure that removing the only item correctly sets first in removeLast
      list = new LinkedList();
      list.addLast(new Integer(1));
      list.removeLast();
      list.addFirst(new Integer(2));
      reportTest("30",1,list.contains(new Integer(2)));

      // Make sure that removing the only item correctly sets last in removeFirst
      list = new LinkedList();
      list.addFirst(new Integer(1));
      list.removeFirst();
      list.addLast(new Integer(2));
      reportTest("31",1,list.contains(new Integer(2)));

      // Make sure that removing the only item correctly sets last in removeLast
      list = new LinkedList();
      list.addLast(new Integer(1));
      list.removeLast();
      list.addFirst(new Integer(2));
      reportTest("32",1,list.contains(new Integer(2)));

      // Make sure list is doubly linked

      list  = new LinkedList();
      for(i=1;i<=100000;++i)
         list.addFirst(new Integer(i));
      System.out.println("Perhaps a 5 second pause - be patient.");
      java.util.Date startD = new java.util.Date();
      java.util.Date stopD;
      caught = false;
      for(i=1;i<=100000&&!caught;++i)
      {
         list.removeLast();
         stopD = new java.util.Date();
         if (stopD.getTime()-startD.getTime() > 5000)
            caught = true;
      }
      reportTest("33",5,!caught);

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