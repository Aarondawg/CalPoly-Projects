import java.util.Iterator;
import java.lang.reflect.*;

public class Lab2C
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

      reportTest("Public Fields",1,!hasPublicFields("LinkedList"));
      reportTest("Cloneable Interface",1,
         implementsInterface("LinkedList","java.lang.Cloneable"));

      // addFirst,removeFirst,isEmpty,length,clear

      LinkedList list = new LinkedList();
      int i,ctr;
      boolean caught;

      try { list.removeFirst(); caught = false;}
      catch(LinkedList.Error e) { caught = true; }
      reportTest("1",1,caught);

      for(i=1;i<=20;++i)
         list.addFirst(new Integer(i));
      for(ctr=0,i=20;i>=1;--i)
         if(((Integer)(list.removeFirst())).intValue() != i)
            ++ctr;
      reportTest("2",1,ctr==0);

      try { list.removeFirst(); caught = false;}
      catch(LinkedList.Error e) { caught = true; }
      reportTest("3",1,caught);
      reportTest("4",1,list.isEmpty());
      reportTest("5",1,list.length()==0);
      for(i=1;i<=20;++i)
         list.addFirst(new Integer(i));
      list.clear();
      reportTest("6",1,list.isEmpty());
      reportTest("7",1,list.length()==0);

      // addLast,removeLast,clone

      list = new LinkedList();
      try { list.removeLast(); caught = false;}
      catch(LinkedList.Error e) { caught = true; }
      reportTest("8",1,caught);

      for(i=1;i<=20;++i)
         list.addLast(new Integer(i));

      LinkedList list2 = (LinkedList)(list.clone());

      for(ctr=0,i=20;i>=1;--i)
         if(((Integer)(list.removeLast())).intValue() != i)
            ++ctr;
      reportTest("9",1,ctr==0);

      try { list.removeLast(); caught = false;}
      catch(LinkedList.Error e) { caught = true; }
      reportTest("10",1,caught);

      for(ctr=0,i=20;i>=1;--i)
         if(((Integer)(list2.removeLast())).intValue() != i)
            ++ctr;
      reportTest("11",1,ctr==0);
      reportTest("12",1,list.isEmpty()&&list2.isEmpty());

      // remove

      list = new LinkedList();
      reportTest("13",1,!list.remove(new Integer(1)));
      list.addFirst(new Integer(1));
      list.addFirst(new Integer(1));
      reportTest("14",1,!list.remove(new Integer(2)));
      reportTest("15",1,list.remove(new Integer(1)));
      reportTest("16",1,list.remove(new Integer(1)));
      reportTest("17",1,list.isEmpty());
      list = new LinkedList();

      for(i=1;i<=10;++i)
         if(i%2==0)
            list.addFirst(new Integer(i));
         else
            list.addLast(new Integer(i));
      list2 = (LinkedList)(list.clone());
      for(ctr=0,i=1;i<=10;++i)
         if(!list.remove(new Integer(i)))
            ++ctr;
      reportTest("18",1,ctr==0);
      reportTest("19",1,list.isEmpty()&&list.length()==0);
      reportTest("20",1,!list2.isEmpty()&&list2.length()!=0);
      for(ctr=0,i=10;i>=2;i=i-2)
         if(list2.remove(new Integer(i)))
            ++ctr;
      reportTest("21",1,ctr==5);
      for(ctr=0,i=1;i<=10;++i)
         if(!list2.remove(new Integer(i)))
            ++ctr;
      reportTest("22",1,ctr==5);
      list.clear();
      for(i=1;i<=10;++i)
         list.addFirst(new Integer(1));
      removeAll(list,new Integer(1));
      reportTest("23",1,list.isEmpty());

      // contains

      list = new LinkedList();
      reportTest("24",1,!list.contains(new Integer(1)));
      for(i=1;i<=10;++i)
         if(i%2==0)
            list.addFirst(new Integer(1));
         else
            list.addLast(new Double(2));
      list2 = (LinkedList)(list.clone());
      for(ctr=0,i=1;i<=10;++i)
         if(i%2==0)
         {
            if(list.contains(new Integer(1)))
               if(list.remove(new Integer(1)))
                  ++ctr;
         }
         else
            if(list.contains(new Double(2)))
               if(list.remove(new Double(2)))
                  ++ctr;
      reportTest("25",1,ctr==10&&list.isEmpty());
      reportTest("26",1,
         list2.contains(new Integer(1))&&list2.contains(new Double(2)));
      for(ctr=0,i=1;i<=10;++i)
         if(list.contains(new Double(1)))
            ++ctr;
      reportTest("27",1,ctr==0);
      list = new LinkedList();
      list.addFirst(new Integer(0));
      reportTest("28",1,list.contains(new Integer(0)));

      // iterators,integration

      list = new LinkedList();
      Iterator it = list.iterator();
      reportTest("29",1,!it.hasNext());
      list.addFirst(new Integer(0));
      it = list.iterator();
      reportTest("30",1,it.hasNext());
      it.next();
      reportTest("31",1,!it.hasNext());
      list.clear();
      for(i=1;i<=10;++i)
         if(i%2==0)
            list.addFirst(new Integer(i));
         else
            list.addLast(new Double(i));

      list2 = (LinkedList)(list.clone());
      reportTest("32",1,equals(list,list2)&&equals(list2,list));
      list.addFirst(new Integer(0));
      reportTest("33",1,!(equals(list,list2)&&equals(list2,list)));

      list.clear();
      list2 = (LinkedList)(list.clone());
      reportTest("34",1,equals(list,list2)&&equals(list2,list));

      try { list.iterator().next(); caught = false;}
      catch(LinkedList.Error e) { caught = true; }
      reportTest("35",1,caught);

      for(i=1;i<=10;++i)
         list.addLast(new Integer(i*i));
      it = list.iterator();
      int total = 0;
      while(it.hasNext())
      {
         total += ((Integer)(it.next())).intValue(); 
      }
      reportTest("36",1,total==10*(10+1)*(2*10+1)/6);

      list.clear();
      list2.clear();
      for(i=1;i<=10;++i)
      {
         list.addFirst(new Integer(i));
         list2.addLast(new Integer(11-i));
      }
      reportTest("37",1,equals(list,list2)&&equals(list2,list));
      list.remove(new Integer(10));
      list2.remove(new Integer(10));
      reportTest("38",1,equals(list,list2)&&equals(list2,list));
      list.removeFirst();
      list2.removeFirst();
      reportTest("39",1,equals(list,list2)&&equals(list2,list));

      // Summary

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

   private static boolean equals(LinkedList x,LinkedList y)
   {
      Iterator itx = x.iterator();
      Iterator ity = y.iterator();
      while(itx.hasNext()&&ity.hasNext())
         if(!itx.next().equals(ity.next()))
            return false;
      if(itx.hasNext()||ity.hasNext())
         return false;
      else
         return true;
   }

   private static void removeAll(LinkedList list, Object item)
   {
      while(list.remove(item));
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

   private static boolean hasPublicFields(String className)
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
      Field[] fields = theClass.getDeclaredFields();
   
      for(int i=0;i<fields.length;++i)
         if (!Modifier.isPrivate(fields[i].getModifiers()))
            return true;
      return false;
   }
}