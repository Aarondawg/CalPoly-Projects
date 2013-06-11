import java.util.Iterator;
import java.lang.reflect.*;

public class Lab2B
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

      // Make sure that the Cloneable interface is implemented

      reportTest("Cloneable Interface",2,
                 implementsInterface("LinkedList","java.lang.Cloneable"));

      int i,ctr;
      LinkedList list = new LinkedList();
      reportTest("1",1,!list.remove(new Integer(1)));
      list.addFirst(new Integer(1));
      list.addFirst(new Integer(1));
      reportTest("2",1,!list.remove(new Integer(2)));
      reportTest("3",1,list.remove(new Integer(1)));
      reportTest("4",1,list.remove(new Integer(1)));
      reportTest("5",1,list.isEmpty());
      list = new LinkedList();

      for(i=1;i<=10;++i)
         if(i%2==0)
            list.addFirst(new Integer(i));
         else
            list.addLast(new Integer(i));
      LinkedList list2 = (LinkedList)(list.clone());

      // Make sure clone actually makes an identical copy

      reportTest("6",1,list2.equals(list)&&list.equals(list2));
      reportTest("7",1,list2.length()==list.length());

      // Make sure clone performs deep copy 

      list.addLast(new Integer(100));
      reportTest("8",3,
         list.contains(new Integer(100))&&!list2.contains(new Integer(100)));

      list.removeLast();
      System.out.println(list.toString());
      for(ctr=0,i=1;i<=10;++i)
               
         if(!list.remove(new Integer(i)))
         {
            System.out.println(i + " has not been removed");
            ++ctr;
            System.out.println(list.toString());
         }

      reportTest("9",1,ctr==0);
      System.out.println("isEmpty: " + list.isEmpty());
      System.out.println("Length: " + list.length());
      reportTest("10",1,list.isEmpty()&&list.length()==0);
      reportTest("11",1,!list2.isEmpty()&&list2.length()!=0);
      for(ctr=0,i=10;i>=2;i=i-2)
         if(list2.remove(new Integer(i)))
            ++ctr;
      reportTest("12",1,ctr==5);
      for(ctr=0,i=1;i<=10;++i)
         if(!list2.remove(new Integer(i)))
            ++ctr;
      reportTest("13",1,ctr==5);
      list.clear();
      for(i=1;i<=10;++i)
         list.addFirst(new Integer(1));
      removeAll(list,new Integer(1));
      reportTest("14",1,list.isEmpty());

      list = new LinkedList();
      list2 = new LinkedList();
      list.addFirst(new Integer(1));
      LinkedList list3 = (LinkedList) list.clone();
      list2.addFirst(list);
      reportTest("15",1,list2.contains(list));
      reportTest("16",1,list2.contains(list3));
      list.addLast(new Integer(1));
      reportTest("17",1,list2.contains(list));
      reportTest("18",1,!list2.contains(list3));

      // Make sure that removing the only item correctly sets first
      list = new LinkedList();
      list.addFirst(new Integer(1));
      list.remove(new Integer(1));
      list.addFirst(new Integer(2));
      reportTest("19",1,list.contains(new Integer(2)));

      // Make sure that removing the only item correctly sets last
      list = new LinkedList();
      list.addFirst(new Integer(1));
      list.remove(new Integer(1));
      list.addLast(new Integer(2));
      reportTest("20",1,list.contains(new Integer(2)));

      // Make sure that the FIRST occurrence of an item is removed
      list = new LinkedList();
      list.addFirst(new Integer(1));
      list.addLast(new Integer(2));
      list.addLast(new Integer(3));
      list.addLast(new Integer(2));
      list.remove(new Integer(2));
      reportTest("21",2,((Integer)(list.removeLast())).equals(new Integer(2)));

      boolean caught;
      list = new LinkedList();
      Iterator it = list.iterator();
      reportTest("22",1,!it.hasNext());
      list.addFirst(new Integer(0));
      it = list.iterator();
      reportTest("23",1,it.hasNext());
      Integer x = (Integer) it.next();
      reportTest("24",1,!it.hasNext());
      list.clear();
      for(i=1;i<=10;++i)
         if(i%2==0)
            list.addFirst(new Integer(i));
         else
            list.addLast(new Double(i));

      list2 = (LinkedList)(list.clone());
      reportTest("25",1,myEquals(list,list2));
      list.addFirst(new Integer(0));
      reportTest("26",1,!(myEquals(list,list2)));

      list.clear();
      list2 = (LinkedList)(list.clone());
      reportTest("27",2,myEquals(list,list2));

      try { list.iterator().next(); caught = false;}
      catch(LinkedList.Error e) { caught = true; }
      reportTest("28",1,caught);

      for(i=1;i<=10;++i)
         list.addLast(new Integer(i*i));
      it = list.iterator();
      int total = 0;
      while(it.hasNext())
      {
         total += ((Integer)(it.next())).intValue(); 
      }
      reportTest("29",1,total==10*(10+1)*(2*10+1)/6);

      list.clear();
      for(i=1;i<=10;++i)
         list.addLast(new Integer(i));
      it = list.iterator();
      total = 0;
      while (it.hasNext())
      {
         Object obj = it.next();
         Iterator it2 = list.iterator();
         while(it2.hasNext())
         {
            Object obj2 = it2.next();
            ++total;
            if (obj == obj2)
               break;
         }
      }
      reportTest("30",2,total==10*(10+1)/2);

      list.clear();
      list2.clear();
      for(i=1;i<=10;++i)
      {
         list.addFirst(new Integer(i));
         list2.addLast(new Integer(11-i));
      }
      reportTest("31",1,myEquals(list,list2));
      list.remove(new Integer(10));
      list2.remove(new Integer(10));
      reportTest("32",2,myEquals(list,list2));
      list.removeFirst();
      list2.removeFirst();
      reportTest("33",1,myEquals(list,list2));
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
   
   private static boolean myEquals(LinkedList x,LinkedList y)
   {
      int test1 = equalViaIterators(x,y);
      int test2 = equalViaIterators(y,x);
      int test3 = x.equals(y) ? 1:0;
      int test4 = y.equals(x) ? 1:0;
      int total = test1+test2+test3+test4;
      if (total == 4)
         return true;
      else if (total == 0)
         return false;
      else
         throw new RuntimeException("Inconsistent tests for equality");
   }
 
   private static int equalViaIterators(LinkedList x,LinkedList y)
   {
      int areEqual = 1;
      Iterator itx = x.iterator();
      Iterator ity = y.iterator();
      while(areEqual==1 && itx.hasNext() && ity.hasNext())
         if(!itx.next().equals(ity.next()))
            areEqual = 0;
      if(areEqual==1 && (itx.hasNext()||ity.hasNext()))
         areEqual = 0;
      return areEqual;
   }
   private static void removeAll(LinkedList list, Object item)
   {
      while(list.remove(item));
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

   private static boolean implementsInterface
                                  (String className,String interfaceName)
   {
      Class theClass;
      try
      {
         theClass = Class.forName(className);
      }
      catch(ClassNotFoundException e)
      {
         throw new RuntimeException("Internal error");
      }
      Class[] interfaces = theClass.getInterfaces();
      for(int i=0;i<interfaces.length;++i)
         if (interfaces[i].getName().equals(interfaceName))
            return true;
      return false;
   }
}
