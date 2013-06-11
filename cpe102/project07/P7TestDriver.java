/**
 * Program 7 Test Driver - LinkedList (P6) plus ordered insertion and Iterable<E>
 *
 * @author Kurt Mammen
 * @version 2/16/2010 
 */
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class P7TestDriver
{
   private static final String RESULTS_FOR = "Results for Program 7";
   private static Comparator<Integer> comparator = new Descending();
   
   private static class Descending implements Comparator<Integer>
   {
      public int compare(Integer a, Integer b)
      {
         return b.compareTo(a);
      }
   }

   public static void main(String[] args) throws ClassNotFoundException
   {
      boolean pass = true;
      
      printHeader(args);

      pass &= testLinkedListArch();
      pass &= testListIteratorArch();
      
      System.out.println();
      
      pass &= testEmptyList();
      pass &= testUnorderedListOfOne();
      pass &= testUnorderedListOfTwo();
      pass &= testUnorderedListOfThree();
      pass &= testConstructor2();
      pass &= testOrderedListOfOne();
      pass &= testOrderedListOfTwo();
      pass &= testOrderedListOfThree();     
      pass &= testConstructor4();
      pass &= testAddForOrderOnePerformance();
      pass &= testSizeForOrderOnePerformance();
      pass &= testClearForOrderOnePerformance();
      pass &= testAddClearRemove();
      pass &= testAddAtIndex();
      pass &= testAddList();
      pass &= testSet();
      pass &= testIndexOf();

      System.out.println();
      
      pass &= testIterator();
      pass &= testListIterator();
      pass &= testListIteratorForOrderOnePerformance();
      
      printResults(pass);
      
      if (!pass)
      {
         System.exit(-1);
      }
   }
   
   private static boolean testLinkedListArch() throws ClassNotFoundException
   {
      boolean pass = true;
      int cnt;
      Class cl;
      Class[] temp;

      System.out.println("LinkedList architecture tests...");

      cl = LinkedList.class;
      
      pass &= test(cl.getSuperclass() == Class.forName("java.lang.Object"),
                   "Class extends something other than Object");

      temp = cl.getInterfaces();
      pass &= test(temp.length == 1, "Incorrect number of implemented interfaces");
      pass &= test(temp[0] == java.lang.Iterable.class, "Incorrect interface implemented");
      pass &= test(cl.getConstructors().length == 4, "Incorrect number of constructors");

      String[] names = {"add", "add", "add", "get", "size", "clear", "indexOf", "remove", "set",
                        "iterator", "listIterator"};

      cnt = countModifiers(cl.getDeclaredMethods(), Modifier.PUBLIC);     
      pass &= test(cnt == names.length, "Incorrect number of public methods");
      pass &= test(verifyNames(cl.getDeclaredMethods(), Modifier.PUBLIC, names),
                   "Above method(s) were not specified");
                 
      cnt = cl.getFields().length;
      pass &= test(cnt == 0, "public instance fields declared");
      
      cnt = countModifiers(cl.getDeclaredFields(), Modifier.PROTECTED);
      pass &= test(cnt == 0, "Protected instance fields declared");
      
      cnt = countModifiers(cl.getDeclaredFields(), Modifier.PRIVATE);
      pass &= test(cnt <= 4, "Too many instance fields declared");
      
      // Count and test number of of PACKAGE fields
      cnt = countPackage(cl.getDeclaredFields());
      pass &= test(cnt == 0, "package instance fields declared");

      pass &= test(checkInstanceVars(cl.getDeclaredFields()),
                   "Disallowed instance variable type used");

      return pass;
   }

   private static boolean testListIteratorArch() throws ClassNotFoundException
   {
      boolean pass = true;
      int cnt;
      Class cl;
      Class[] temp;
      LinkedList<Integer> list = new LinkedList<Integer>(comparator);

      System.out.println("ListIterator architecture tests...");

      cl = list.listIterator().getClass();
      
      pass &= test(cl.getSuperclass() == Class.forName("java.lang.Object"),
       "Class extends something other than Object");
      pass &= test(cl.getInterfaces().length == 1,
       "ListIterator implements incorrect number of interfaces");
      pass &= test(cl.getInterfaces()[0].getName().equals("ListIterator"),
       "listIterator() returns incorrect type");
      pass &= test(cl.getInterfaces()[0].getInterfaces().length == 1,
       "ListIterator extends incorrect number of interfaces");
      pass &= test(cl.getInterfaces()[0].getInterfaces()[0].getName().equals("java.util.Iterator"),
       "ListIterator extends incorrect interface");
      temp = cl.getInterfaces();
               
      pass &= test(cl.getConstructors().length <= 1, "Incorrect number of constructors");
      String[] names = {"hasNext", "next", "hasPrevious", "previous", "remove"};

      cnt = countModifiers(cl.getDeclaredMethods(), Modifier.PUBLIC);     
      pass &= test(cnt == names.length, "Incorrect number of public methods");
      pass &= test(verifyNames(cl.getDeclaredMethods(), Modifier.PUBLIC, names),
                   "Unspecified method name(s)");
      
      cnt = cl.getFields().length;
      pass &= test(cnt == 0, "public instance fields declared");
      
      cnt = countModifiers(cl.getDeclaredFields(), Modifier.PROTECTED);
      pass &= test(cnt == 0, "Protected instance fields declared");
      
      cnt = countModifiers(cl.getDeclaredFields(), Modifier.PRIVATE);
      pass &= test(cnt <= 3, "Too many instance fields declared");
      
      // Count and test number of of PACKAGE fields
      // NOTE: "this" is a package variable for inner classes - so cnt == 1 expected.
      cnt = countPackage(cl.getDeclaredFields());
      pass &= test(cnt == 1, "package instance fields declared");

      return pass;
   }
   
   private static boolean testEmptyList()
   {
      System.out.println("Testing an empty LinkedList...");  

      boolean pass = true;
      boolean caught = false;
      LinkedList<Integer> list = new LinkedList<Integer>();
    
      try
      {
         caught = false;
         list.get(0);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "get(0)");

      try
      {
         caught = false;
         list.get(-99);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }

      pass &= test(caught, "get(-99)");
      pass &= test(list.size() == 0, "size()");
      
      try
      {
         caught = false;
         list.indexOf(new Integer(5));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "indexOf(new Integer(5))");

      try
      {
         caught = false;
         list.remove(0);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "remove(0)");

      try
      {
         caught = false;
         list.remove(-99);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }

      pass &= test(caught, "remove(-99)");
      
      return pass;
   }
   
   private static boolean testUnorderedListOfOne()
   {
      System.out.println("Testing an unordered LinkedList of one element...");  

      boolean pass = true;
      boolean caught = false;
      LinkedList<Integer> list = new LinkedList<Integer>();
      
      list.add(new Integer(99));
      
      try
      {
         list.get(1);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "get(1)");

      try
      {
         list.get(-99);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }

      pass &= test(caught, "get(-99)");
      
      pass &= test(list.get(0) == 99, "get(0)");
      pass &= test(list.size() == 1, "size()");
      
      try
      {
         caught = false;
         list.indexOf(new Integer(5));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "indexOf(new Integer(5)) (value not in list)");

      int index = -1;
      try
      {
         caught = false;
         index = list.indexOf(new Integer(99));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(!caught, "indexOf(new Integer(99)) (value in list)");
      pass &= test(index == 0, "indexOf(new Integer(99) (value in list)");
      
      try
      {
         caught = false;
         list.remove(1);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "remove(1)");

      try
      {
         caught = false;
         list.remove(-99);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }

      pass &= test(caught, "remove(-99)");
      
      return pass;
   }

   private static boolean testUnorderedListOfTwo()
   {
      System.out.println("Testing an unordered LinkedList of two elements...");

      boolean pass = true;
      boolean caught = false;
      LinkedList<Integer> list = new LinkedList<Integer>();
      
      list.add(new Integer(99));
      list.add(new Integer(1111));
      
      try
      {
         list.get(2);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "get(2) did not throw IndexOutOfBoundsException");

      try
      {
         list.get(-99);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }

      pass &= test(caught, "get(-99) did not throw IndexOutOfBoundsException");
      
      pass &= test(list.get(0) == 99, "get(0)");
      pass &= test(list.get(1) == 1111, "get(1)");
      pass &= test(list.size() == 2, "size()");
      
      try
      {
         caught = false;
         list.indexOf(new Integer(5));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "indexOf(new Integer(5)) (value not in list)");

      int index = -1;
      try
      {
         caught = false;
         index = list.indexOf(new Integer(99));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(!caught, "indexOf(new Integer(99)) (value in list)");
      pass &= test(index == 0, "indexOf(new Integer(99)) (value in list)");

      index = -1;
      try
      {
         caught = false;
         index = list.indexOf(new Integer(1111));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(!caught, "indexOf(new Integer(1111)) (value in list)");
      pass &= test(index == 1, "indexOf(new Integer(1111) (value in list)");
      
      try
      {
         caught = false;
         list.remove(2);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "remove(2)");

      try
      {
         caught = false;
         list.remove(-99);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }

      pass &= test(caught, "remove(-99)");
      
      return pass;
   }
   
   private static boolean testUnorderedListOfThree()
   {
      System.out.println("Testing an unordered LinkedList of three elements...");

      boolean pass = true;
      boolean caught = false;
      LinkedList<Integer> list = new LinkedList<Integer>();
      
      list.add(new Integer(99));
      list.add(new Integer(1111));
      list.add(new Integer(-777));
      
      try
      {
         list.get(3);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "get(3)");

      try
      {
         list.get(-99);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }

      pass &= test(caught, "get(-99)");
      
      pass &= test(list.get(0) == 99, "get(0)");
      pass &= test(list.get(1) == 1111, "get(1)");
      pass &= test(list.get(2) == -777, "get(2)");
      pass &= test(list.size() == 3, "size()");
      
      try
      {
         caught = false;
         list.indexOf(new Integer(5));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "indexOf(new Integer(5)) (value not in list)");

      int index = -1;
      try
      {
         caught = false;
         index = list.indexOf(new Integer(1111));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(!caught, "indexOf(new Integer(1111)) (value in list)");
      pass &= test(index == 1, "indexOf(new Integer(1111) (value in list)");

      index = -1;
      try
      {
         caught = false;
         index = list.indexOf(new Integer(99));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(!caught, "indexOf(new Integer(99)) (value in list)");
      pass &= test(index == 0, "indexOf(new Integer(99) (value in list)");

      index = -1;
      try
      {
         caught = false;
         index = list.indexOf(new Integer(-777));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(!caught, "indexOf(new Integer(-777)) (value in list)");
      pass &= test(index == 2, "indexOf(new Integer(-777) (value in list)");

      try
      {
         caught = false;
         list.remove(3);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "remove(3)");

      try
      {
         caught = false;
         list.remove(-99);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }

      pass &= test(caught, "remove(-99)");
      
      return pass;
   }

   private static boolean testOrderedListOfOne()
   {
      System.out.println("Testing an ordered LinkedList of one element...");  

      boolean pass = true;
      boolean caught = false;
      LinkedList<Integer> list = new LinkedList<Integer>(comparator);
      
      list.add(new Integer(99));
      
      try
      {
         list.get(1);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "get(1)");

      try
      {
         list.get(-99);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }

      pass &= test(caught, "get(-99)");
      
      pass &= test(list.get(0) == 99, "get(0)");
      pass &= test(list.size() == 1, "size()");
      
      try
      {
         caught = false;
         list.indexOf(new Integer(5));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "indexOf(new Integer(5)) (value not in list)");

      int index = -1;
      try
      {
         caught = false;
         index = list.indexOf(new Integer(99));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(!caught, "indexOf(new Integer(99)) (value in list)");
      pass &= test(index == 0, "indexOf(new Integer(99) (value in list)");
      
      try
      {
         caught = false;
         list.remove(1);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "remove(1)");

      try
      {
         caught = false;
         list.remove(-99);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }

      pass &= test(caught, "remove(-99)");
      
      return pass;
   }

   private static boolean testOrderedListOfTwo()
   {
      System.out.println("Testing an ordered LinkedList of two elements...");

      boolean pass = true;
      boolean caught = false;
      LinkedList<Integer> list = new LinkedList<Integer>(comparator);
      
      list.add(new Integer(99));
      list.add(new Integer(1111));
		System.out.println("SIZE: " + list.size());
      
      try
      {
         list.get(2);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "get(2) did not throw IndexOutOfBoundsException");

      try
      {
         list.get(-99);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }

      pass &= test(caught, "get(-99) did not throw IndexOutOfBoundsException");
      System.out.println("INDEX0: " + list.get(0));
      pass &= test(list.get(0) == 1111, "get(0)");
      pass &= test(list.get(1) == 99, "get(1)");
      pass &= test(list.size() == 2, "size()");
      
      try
      {
         caught = false;
         list.indexOf(new Integer(5));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "indexOf(new Integer(5)) (value not in list)");

      int index = -1;
      try
      {
         caught = false;
         index = list.indexOf(new Integer(99));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(!caught, "indexOf(new Integer(99)) (value in list)");
      pass &= test(index == 1, "indexOf(new Integer(99)) (value in list)");

      index = -1;
      try
      {
         caught = false;
         index = list.indexOf(new Integer(1111));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(!caught, "indexOf(new Integer(1111)) (value in list)");
      pass &= test(index == 0, "indexOf(new Integer(1111) (value in list)");
      
      try
      {
         caught = false;
         list.remove(2);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "remove(2)");

      try
      {
         caught = false;
         list.remove(-99);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }

      pass &= test(caught, "remove(-99)");
      
      return pass;
   }
   
   private static boolean testOrderedListOfThree()
   {
      System.out.println("Testing an ordered LinkedList of three elements...");

      boolean pass = true;
      boolean caught = false;
      LinkedList<Integer> list = new LinkedList<Integer>(comparator);
      
      list.add(new Integer(99));
      list.add(new Integer(1111));
      list.add(new Integer(-777));
      
      try
      {
         list.get(3);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "get(3)");

      try
      {
         list.get(-99);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }

      pass &= test(caught, "get(-99)");
      
      pass &= test(list.get(0) == 1111, "get(0)");
      pass &= test(list.get(1) == 99, "get(1)");
      pass &= test(list.get(2) == -777, "get(2)");
      pass &= test(list.size() == 3, "size()");
      
      try
      {
         caught = false;
         list.indexOf(new Integer(5));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "indexOf(new Integer(5)) (value not in list)");

      int index = -1;
      try
      {
         caught = false;
         index = list.indexOf(new Integer(1111));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(!caught, "indexOf(new Integer(1111)) (value in list)");
      pass &= test(index == 0, "indexOf(new Integer(1111) (value in list)");

      index = -1;
      try
      {
         caught = false;
         index = list.indexOf(new Integer(99));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(!caught, "indexOf(new Integer(99)) (value in list)");
      pass &= test(index == 1, "indexOf(new Integer(99) (value in list)");

      index = -1;
      try
      {
         caught = false;
         index = list.indexOf(new Integer(-777));
      }
      catch(java.util.NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(!caught, "indexOf(new Integer(-777)) (value in list)");
      pass &= test(index == 2, "indexOf(new Integer(-777) (value in list)");

      try
      {
         caught = false;
         list.remove(3);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "remove(3)");

      try
      {
         caught = false;
         list.remove(-99);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }

      pass &= test(caught, "remove(-99)");
      
      return pass;
   }
   
   
   private static boolean testConstructor2()
   {
      System.out.println("Testing LinkedList(java.util.List)...");
      boolean pass = true;
      int[] ints = new int[] {66, 77, 55, -11, 22, 70, 88, -22};
      int[] expected = new int[] {66, 77, 55, -11, 22, 70, 88, -22};
      java.util.LinkedList<Integer> in = new java.util.LinkedList<Integer>();
      
      for(Integer i : ints)
      {
         in.add(i);
      }
      
      try
      {
         LinkedList<Integer> list = new LinkedList<Integer>(in);
         
         for(int i = 0; i < list.size(); i++)
         {
            if(list.get(i) != expected[i])
            {
               pass &= test(false, "Out of order");
               break;
            }
         }
      }
      catch (Exception e)
      {
         pass &= test(false, "Exception in LinkedList(java.util.List)");
         throw new RuntimeException("Rethown from test driver", e);
      }
      
      return pass;
   }

   private static boolean testConstructor4()
   {
      System.out.println("Testing LinkedList(java.util.Comparator, java.util.List)...");
      boolean pass = true;
      int[] ints = new int[] {66, 77, 55, -11, 22, 70, 88, -22};
      int[] expected = new int[] {88, 77, 70, 66, 55, 22, -11, -22};
      java.util.LinkedList<Integer> in = new java.util.LinkedList<Integer>();
      
      for(Integer i : ints)
      {
         in.add(i);
      }
      
      try
      {
         LinkedList<Integer> list = new LinkedList<Integer>(comparator, in);
         
         for(int i = 0; i < list.size(); i++)
         {
            if(list.get(i) != expected[i])
            {
               pass &= test(false, "Out of order");
               break;
            }
         }
      }
      catch (Exception e)
      {
         pass &= test(false, "Exception in LinkedList(java.util.List)");
         throw new RuntimeException("Rethown from test driver", e);
      }
      
      return pass;
   }
   
   private static boolean testAddForOrderOnePerformance()
   {
      System.out.println("Testing add() for O(1) performance...");
      System.out.println("   If running from console <cntl>-C will terminate");
      boolean pass = true;  
      
      LinkedList<Long> ll = new LinkedList<Long>();
      
      for (int i = 0; i < 100000; i++)
      {
         ll.add((long)i);
      }
   
      long start = System.nanoTime();
      ll.add((long)-99);
      double time = System.nanoTime() - start;
      pass &= test(time < 1200000, 
                   "Performance does not appear to be O(1), try running the test driver again"); 

      return pass;
   }

   private static boolean testSizeForOrderOnePerformance()
   {
      System.out.println("Testing size() for O(1) performance...");
      System.out.println("   If running from console <cntl>-C will terminate");
      boolean pass = true;  
      double sum = 0;
      
      LinkedList<Long> ll = new LinkedList<Long>();
      for (int i = 0; i < 200000; i++)
      {
         ll.add((long)i);
      }

      long start = System.nanoTime();
      ll.size();
      double time = System.nanoTime() - start;

      pass &= test(time < 400000,
                   "Performance does not appear to be O(1), try running the test driver again");

      return pass;
   }
      private static boolean testClearForOrderOnePerformance()
   {
      System.out.println("Testing clear() for O(1) performance...");
      System.out.println("   If running from console <cntl>-C will terminate");
      boolean pass = true;  
      double sum = 0;
      
      LinkedList<Long> ll = new LinkedList<Long>();
      for (int i = 0; i < 200000; i++)
      {
         ll.add((long)i);
      }

      long start = System.nanoTime();
      ll.clear();
      double time = System.nanoTime() - start;

      pass &= test(time < 400000,
                   "Performance does not appear to be O(1), try running the test driver again");

      return pass;
   }
 
   private static boolean testAddClearRemove()
   {
      System.out.println("Testing add(int), clear(), and remove(int)...");
      boolean pass = true;
      LinkedList<Integer> list = new LinkedList<Integer>();
      
      pass &= test(list.size() == 0, "size()");
      
      list.clear();
      pass &= test(list.size() == 0, "size() after clear()");
      
      // Add some in order...
      int[] ints = new int[] {99, 88, 77, 66, 55};
      
      for (int i = 0; i < ints.length; i++)
      {
         list.add(ints[i]);
         pass &= test(list.size() == i + 1, "size() while adding elements");

         for(int j = 0; j < list.size(); j++)
         {
            pass &= test(list.get(j) == ints[j], "get(int)");
         }
      }
      
      list.clear();
      pass &= test(list.size() == 0, "size() after clear()");
      
      boolean caught = false;
      
      try
      {
         list.get(0);
      }
      catch (IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "get(0) after clear()");

      // Add some in reverse order...
      for (int i = ints.length - 1; i > -1; i--)
      {
         list.add(ints[i]);
         
         pass &= test(list.size() == ints.length - i, "size() while adding elements");
         int k = ints.length - list.size();
         for(int j = 0; j < list.size(); j++)
         {
            pass &= test(list.get(j) == ints[ints.length - 1 - j], "get(int)");
         }
      }
      
      // Remove last...
      pass &= test(list.remove(4) == 99, "remove(int) - last one");
      pass &= test(list.size() == 4, "size() - after remove");
      
      for (int i = 0; i < ints.length - 1; i++)
      {
         pass &= test(list.get(i) == ints[ints.length - 1 - i], "get(int) after remove");
      }
      
      // Remove first...
      pass &= test(list.remove(0) == 55, "remove(int) - first one");
      pass &= test(list.size() == 3, "size() - after remove");
      
      for (int i = 0; i < ints.length - 2; i++)
      {
         pass &= test(list.get(i) == ints[ints.length - 2 - i], "get(int) after remove");
      }
      
      // Remove middle...
      pass &= test(list.remove(1) == 77, "remove(int) - middle value");
      pass &= test(list.size() == 2, "size() - after remove");
      pass &= test(list.get(0) == 66, "get(int) after remove");
      pass &= test(list.get(1) == 88, "get(int) after remove");
      
      // Remove all
      pass &= test(list.remove(0) == 66, "remove(0) - non-empty list");
      pass &= test(list.size() == 1, "size() after remove()");

      pass &= test(list.remove(0) == 88, "remove(0) - non-empty list");
      pass &= test(list.size() == 0, "size() after remove()");

      list.add(9999);
      pass &= test(list.size() == 1, "size() after add(E)");
      pass &= test(list.get(0) == 9999, "get(int) after remove(int) all");
      
      return pass;
   }

   private static boolean testIndexOf()
   {
      System.out.println("Testing indexOf(E)...");
      boolean pass = true;
      LinkedList<Integer> list = new LinkedList<Integer>();
      
      // Add some in order...
      int[] ints = new int[] {99, 88, 77, 66, 55};
      
      for (int i = 0; i < ints.length; i++)
      {
         list.add(ints[i]);
         
         for (int j = 0; j < list.size(); j++)
         {
            pass &= test(list.indexOf(ints[j]) == j, "indexOf(E) - existing value");
         }
      }
      
      return pass;
   }

   private static boolean testAddAtIndex()
   {
      System.out.println("Testing add(int index, E element)...");
      boolean pass = true;
      LinkedList<Integer> list = new LinkedList<Integer>();
      
      boolean caught = false;
      try
      {
         list.add(-89, 7);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "Did not throw IndexOutOfBoundsException for index -89");
      
      caught = false;
      try
      {
         list.add(1, 7);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "Did not throw IndexOutOfBoundsException for index 1");

      int[] ints = new int[] {1, 10, 15, 20};
     
      list.add(0, 10);
      pass &= test(list.get(0) == 10, "add(0,10) - expected 10, found " + list.get(0));
      
      list.add(0, 1);
      pass &= test(list.get(0) == 1, "add(0,1) - expected 1, found " + list.get(0));
      pass &= test(list.get(1) == 10, "add(0,1) - expected 10, found " + list.get(1));  
      
      list.add(2, 20);
      pass &= test(list.get(0) == 1, "add(2,20) - expected 1, found " + list.get(0));
      pass &= test(list.get(1) == 10, "add(2,20) - expected 10, found " + list.get(1));
      pass &= test(list.get(2) == 20, "add(2,20) - expected 20, found " + list.get(2));
   
      list.add(2, 15);
      pass &= test(list.get(0) == 1, "add(2,15) - expected 1, found " + list.get(0));
      pass &= test(list.get(1) == 10, "add(2,15) - expected 10, found " + list.get(1));
      pass &= test(list.get(2) == 15, "add(2,15) - expected 15, found " + list.get(2));
      pass &= test(list.get(3) == 20, "add(2,15) - expected 20, found " + list.get(3));
      
      for (int i = 0; i < ints.length; i++)
      {
         pass &= test(list.get(i) == ints[i], "add(" + i + ", " + ints[i] + ")");
      }
      
      // New for P7 (ordered LinkedList)

      list = new LinkedList<Integer>(comparator);
      list.add(0, 7);
      
      caught = false;
      try
      {
         list.add(0, 6); 
      }
      catch(IllegalArgumentException e)
      {
         caught = true;
      }
      pass &= test(caught, "Did not throw expected IllegalArgumentException"); 

      caught = false;
      try
      {
         list.add(1, 8); 
      }
      catch(IllegalArgumentException e)
      {
         caught = true;
      }
      pass &= test(caught, "Did not throw expected IllegalArgumentException");

      list.add(0, 7);
      list.add(1, 7);
      list.add(3, 7);
      list.add(0, 8);
      list.add(5, 6);

      pass &= test(list.get(0) == 8, "Incorrect value after add(int indext, E element)");
      pass &= test(list.get(1) == 7, "Incorrect value after add(int indext, E element)");
      pass &= test(list.get(2) == 7, "Incorrect value after add(int indext, E element)");
      pass &= test(list.get(4) == 7, "Incorrect value after add(int indext, E element)");
      pass &= test(list.get(5) == 6, "Incorrect value after add(int indext, E element)");
        
      return pass;
   }

   private static boolean testAddList()
   {
      System.out.println("Testing add(List<E> list)...");
      boolean pass = true;
      
      ArrayList<Double> values;
      LinkedList<Double> list;
      
      for (int i = 0; i < 3; i++)
      {
         if (!pass)
         {
            break; // Avoid lots of superfulous output
         }
         
         values = new ArrayList<Double>();
         
         for (int j = 0; j < i * 10; j++)
         {
            values.add(Math.random());
         }
         
         list = new LinkedList<Double>();
         list.add(values);
         pass &= test(list.size() == values.size(), 
                      "size(), found " + list.size() + ", expected " + values.size());
         
         for (int j = 0; j < values.size(); j++)
         {
            pass &= test(list.get(j) == values.get(j), "get(" + i + ")");
            
            if (!pass)
            {
               break; // Avoid lots of superfulous output
            }
         }
      }
        
      return pass;
   }

   private static boolean testSet()
   {
      System.out.println("Testing set(int index, E element)...");
      boolean pass = true;
      LinkedList<Integer> list = new LinkedList<Integer>();
      
      boolean caught = false;
      try
      {
         list.set(0, 5);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "Did not throw IndexOutOfBoundsException for set(0, 5)");
      
      list.add(99);
      double returnedValue = list.set(0, -111);
      pass &= test(returnedValue == 99, "Incorrect return value for set(0, -111)");
      pass &= test(list.get(0) == -111, "Incorrect get(0) value after set(0, -111)");
      
      caught = false;
      try
      {
         list.set(1, 8);
      }
      catch(IndexOutOfBoundsException e)
      {
         caught = true;
      }
    
      pass &= test(caught, "Did not throw IndexOutOfBoundsException for set(1, 8");
           
      list.add(-55);
      list.add(-22);
      
      list.set(0, 11);
      pass &= test(list.get(0) == 11, "set(0, 11)");
      
      list.set(1, 22);
      pass &= test(list.get(1) == 22, "set(0, 22)");
      
      list.set(2, 33);
      pass &= test(list.get(2) == 33, "set(0, 33)");
 
      // New for P7 (ordered LinkedList)
      caught = false;
      try
      {
         list = new LinkedList<Integer>(comparator);
         list.add(7);
         list.set(0, 7);
         list.set(0, 6);
         list.set(0, 8);

         list.add(5);
         list.add(11);

         list.set(0, 12);
         list.set(0, 10);
         list.set(1, 8);
         list.set(2, 7);
         list.set(2, 3);
      }
      catch(IllegalArgumentException e)
      {
         caught = true;
      }
      pass &= test(!caught, "Threw unexpected IllegalArgumentException"); 

      pass &= test(list.get(0) == 10, "set(0, 10)");
      pass &= test(list.get(1) == 8, "set(1, 8)");
      pass &= test(list.get(2) == 3, "set(2, 3)");
      
      caught = false;
      try
      {
         list.set(0, 7);
      }
      catch(IllegalArgumentException e)
      {
         caught = true;
      }
      pass &= test(caught, "Did not throw expected IllegalArgumentException");
      
      caught = false;
      try
      {
         list.set(1, 2);
      }
      catch(IllegalArgumentException e)
      {
         caught = true;
      }
      pass &= test(caught, "Did not throw expected IllegalArgumentException");
      
      caught = false;
      try
      {
         list.set(2, 10);
      }
      catch(IllegalArgumentException e)
      {
         caught = true;
      }
      pass &= test(caught, "Did not throw expected IllegalArgumentException");
      
      caught = false;
      try
      {
         ArrayList<Integer> arrayList = new ArrayList<Integer>();
         arrayList.add(7);
         list = new LinkedList<Integer>(comparator, arrayList);
         list.set(0, 7);
         list.set(0, 6);
         list.set(0, 8);

         arrayList.add(5);
         arrayList.add(11);
         list = new LinkedList<Integer>(comparator, arrayList);

         list.set(0, 12);
         list.set(0, 10);
         list.set(1, 8);
         list.set(2, 7);
         list.set(2, 3);
      }
      catch(IllegalArgumentException e)
      {
         caught = true;
      }
      pass &= test(!caught, "Threw unexpected IllegalArgumentException");

      pass &= test(list.get(0) == 10, "set(0, 10)");
      pass &= test(list.get(1) == 8, "set(1, 8)");
      pass &= test(list.get(2) == 3, "set(2, 3)");

      caught = false;
      try
      {
         list.set(0, 7);
      }
      catch(IllegalArgumentException e)
      {
         caught = true;
      }
      pass &= test(caught, "Did not throw expected IllegalArgumentException");
      
      caught = false;
      try
      {
         list.set(1, 2);
      }
      catch(IllegalArgumentException e)
      {
         caught = true;
      }
      pass &= test(caught, "Did not throw expected IllegalArgumentException");
      
      caught = false;
      try
      {
         list.set(2, 10);
      }
      catch(IllegalArgumentException e)
      {
         caught = true;
      }
      pass &= test(caught, "Did not throw expected IllegalArgumentException");
      
      return pass;
   }

   private static boolean testIterator()
   {
      System.out.println("Testing Iterator...");
      boolean pass = true;
      LinkedList<Integer> list = new LinkedList<Integer>(comparator);
      int[] ints = new int[] {99, 88, 77, 66, 55};
      
      for (Integer i : list)
      {
         pass &= test(false, "Iterating on an empty LinkedList");
      }
      
      list.add(ints[4]);
      list.add(ints[2]);
      list.add(ints[3]);
      list.add(ints[1]);
      list.add(ints[0]);

      int ndx = 0;
      for (Integer i : list)
      {
         pass &= test(i == ints[ndx], "Iterating on a non-empty LinkedList");
         ndx++;
      }
     
      // ListIterator isA Iterator and should share same methods/data
      Iterator<Integer> iter = list.iterator();
      ListIterator<Integer> listIter = (ListIterator<Integer>)iter;
      
      pass &= test(iter.next() == 99, "Iterator.next() returned incorrect value");
      pass &= test(listIter.next() == 88, "ListIterator.next() returned incorrect value");
      
      return pass;
   }

   private static boolean testListIterator()
   {
      System.out.println("Testing ListIterator...");
      boolean pass = true;
      LinkedList<Integer> list = new LinkedList<Integer>(comparator);
      int[] ints = new int[] {99, 88, 77, 66, 55};
      
      ListIterator<Integer> iter;
      
      // Test empty list...
      iter = list.listIterator();
        
      pass &= test(!iter.hasNext(), "ListIterator.hasNext() on empty list");
      pass &= test(!iter.hasPrevious(), "ListIterator.hasPrevious() on empty list");
      
      boolean caught = false;
      try
      {
         iter.next();
      }
      catch (NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "ListIterator.next() not throwing when it should");

      caught = false;
      try
      {
         iter.previous();
      }
      catch (NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "ListIterator.previous() not throwing when it should");

      // Test list of one...
      list.add(ints[0]);
      iter = list.listIterator();

      pass &= test(iter.hasNext(), "ListIterator.hasNext() on empty list");
      pass &= test(!iter.hasPrevious(), "ListIterator.hasPrevious() on empty list");
      
      caught = false;
      try
      {
         pass &= test(iter.next() == ints[0], "ListIterator.hasNext() on non-empty list");
      }
      catch (NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(!caught, "ListIterator.next() throwing when it should not");

      caught = false;
      try
      {
         pass &= test(iter.previous() == ints[0], "ListIterator.hasPrevious() on non-empty list");
      }
      catch (NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(!caught, "ListIterator.previous() throwing when it should not");

      caught = false;
      try
      {
         iter.next();
         iter.next();
      }
      catch (NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "ListIterator.next() not throwing when it should");

      // Test list of two...
      list.add(ints[1]);
      iter = list.listIterator();
      
      int i = 0;
      while(iter.hasNext())
      {
         pass &= test(iter.next() == ints[i++], "ListIterator not working correctly");
      }
      
      caught = false;
      try
      {
         iter.next();
       }
      catch (NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "ListIterator.next() not throwing when it should");

      while(iter.hasPrevious())
      {
         pass &= test(iter.previous() == ints[--i], "ListIterator not working correctly");
      }

      caught = false;
      try
      {
         iter.previous();
      }
      catch (NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "ListIterator.previous() not throwing when it should");

      // Test list of many...
      list.add(ints[4]);
      list.add(ints[2]);
      list.add(ints[3]);
      iter = list.listIterator();

      i = 0;
      while(iter.hasNext())
      {
         pass &= test(iter.next() == ints[i++], "ListIterator not working correctly");
      }
      
      caught = false;
      try
      {
         iter.next();
       }
      catch (NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "ListIterator.next() not throwing when it should");

      while(iter.hasPrevious())
      {
         pass &= test(iter.previous() == ints[--i], "ListIterator not working correctly");
      }

      caught = false;
      try
      {
         iter.previous();
      }
      catch (NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "ListIterator.previous() not throwing when it should");
      
      // Remove some elements and iterate over remaining ones...
      pass &= test(list.remove(2) == ints[2], "remove(int) after iteration");
      pass &= test(list.remove(3) == ints[4], "remove(int) after iteration");
      pass &= test(list.remove(0) == ints[0], "remove(int) after iteration");
      
      iter = list.listIterator();
      
      pass &= test(iter.hasNext(), "hasNext() after remove(int)");
      pass &= test(iter.next() == ints[1], "next() after remove(int)");
      
      pass &= test(iter.hasNext(), "hasNext() after remove(int)");
      pass &= test(iter.next() == ints[3], "next() after remove(int)");

      // Add some elements and iterate over list...
      list.add(ints[2]);
      list.add(ints[4]);
      list.add(ints[0]);
      iter = list.listIterator();
      
      pass &= test(list.size() == 5,"size() after remove-add");
      
      iter = list.listIterator();
      i = 0;
      while(iter.hasNext())
      {
         pass &= test(iter.next() == ints[i++], "ListIterator not working correctly");
      }
      
      caught = false;
      try
      {
         iter.next();
      }
      catch (NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "ListIterator.next() not throwing when it should");

      while(iter.hasPrevious())
      {
         pass &= test(iter.previous() == ints[--i], "ListIterator not working correctly");
      }

      caught = false;
      try
      {
         iter.previous();
      }
      catch (NoSuchElementException e)
      {
         caught = true;
      }
      
      pass &= test(caught, "ListIterator.previous() not throwing when it should");

      // Remove all and iterate...
      list.remove(4);
      list.remove(1);
      list.remove(2);
      list.remove(0);
      list.remove(0);
      
      pass &= test(list.size() == 0, "size() after remove(int)");
      
      iter = list.listIterator();
      
      pass &= test(!iter.hasNext(), "ListIterator.hasNext() after remove(int)");
      pass &= test(!iter.hasPrevious(), "ListIterator.hasPrevious() after remove(int)");
      
      // Add some and iterate...
      for (i = ints.length - 1; i > -1; i--)
      {
         list.add(ints[i]);
      }
      
      iter = list.listIterator();

      i = 0;
      while(iter.hasNext())
      {
         pass &= test(iter.next() == ints[i++], "ListIterator.next() after add(E)");
      }

      pass &= test(iter.hasPrevious(), "ListIterator.hasPrevious() after next()");

      // Clear and iterate...
      list.clear();

      iter = list.listIterator();
      
      pass &= test(!iter.hasNext(), "ListIterator.hasNext() after clear()");
      pass &= test(!iter.hasPrevious(), "ListIterator.hasPrevious() after clear()");
      
      return pass;
   }

   private static boolean testListIteratorForOrderOnePerformance()
   {
      System.out.println("Testing ListIterator() for O(1) performance...");
      System.out.println("   If running from console <cntl>-C will terminate");
      boolean pass = true;  
      
      LinkedList<Long> ll = new LinkedList<Long>();
      
      for (int i = 0; i < 1000; i++)
      {
         ll.add((long)i);
      }
   
      double temp = 0;
      ListIterator<Long> it = ll.listIterator();
      
      long start = System.nanoTime();     
      while(it.hasNext())
      {
         temp += it.next();
      }
      double time = System.nanoTime() - start;
      
      pass &= test(time < 3200000, 
                   "next() performance does not appear to be O(1), try running the test driver again"); 

      start = System.nanoTime();     
      while(it.hasPrevious())
      {
         temp += it.previous();
      }
      time = System.nanoTime() - start;
      
      pass &= test(time < 3200000, 
                   "previous() performance does not appear to be O(1), try running the test driver again"); 

      return pass;
   }

   private static void printHeader(String[] args)
   {
      if (args.length == 1)
      {
         System.out.println(args[0]);
      }
      
      System.out.println(RESULTS_FOR + "\n");
   }
   
   private static void printResults(boolean pass)
   {
      String msg;
      
      if(pass)
      {
         msg = "\nCongratulations, you MAY HAVE passed all the tests!\n\n"
            + "The tests checking for O(1) performance are designed to work\n"
            + "correctly on vogon.  You should run your solution with this\n"
            + "test driver on vogon to avoid the possiblity your system is\n"
            + "so blazingly fast it covered up performance problems in your\n"
            + "solution. Make sure your solution passes several times before\n"
            + "considering it correct. If you pass all of the tests consistently\n"
            + "your grade will be based on when you turn in your functionally\n"
            + "correct solution and any deductions for the quality of your\n"
            + "implementation.  Quality is based on, but not limited to,\n"
            + "coding style, documentation requirements, compiler warnings,\n"
            + "and the efficiency and elegance of your code.\n";
      }
      else
      {
         msg = "\nNot done yet - you failed one or more tests!\n\n"
            + "If you are failing because of the tests checking for O(1)\n"
            + "performance you should try running on vogon to avoid\n"
            + "the possibility that the reason you are failing is simply\n"
            + "that your system is too slow.  If you are running on vogon\n"
            + "and your code is failing consistently, you will need to fix\n"
            + "your code.\n";
      }
      
      System.out.print(msg);       
   }
   
   private static int countModifiers(Field[] fields, int modifier)
   {
      int count = 0;
      
      for (Field f : fields)
      {
         if (f.getModifiers() == modifier)
         {
            count++;
         }
      }
      
      return count;
   }
   
   private static int countModifiers(Method[] methods, int modifier)
   {
      int count = 0;
      
      for (Method m : methods)
      {
         if (m.getModifiers() == modifier)
         {
            count++;
         }
      }
      
      return count;
   }

   private static boolean checkInstanceVars(Field[] fields)
   {
      for (Field f : fields)
      {
         if (f.toString().contains("java."))
         {
            if (!f.toString().contains("java.util.Comparator"))
            {
               return false;
            }
         }
      }

      return true;
   }
   
   private static boolean approx(double a, double b, double epsilon)
   {
      return Math.abs(a - b) < epsilon;
   }
   
   private static boolean verifyNames(Method[] methods, int modifier, String[] names)
   {
      boolean pass = true;
      Arrays.sort(names);
      
      for (Method m : methods)
      {
         if (m.getModifiers() == modifier)
         {
            if (Arrays.binarySearch(names, m.getName()) < 0)
            {
               System.out.print("      Class contains unspecified public ");
               System.out.println("method: " + m.getName());
               pass &= false;
            }
         }
      }
      
      return pass;
   }
      
   private static boolean test(boolean pass, String msg)
   {
      if (!pass)
      {
         (new Throwable("   Failed: " + msg)).printStackTrace();
      }
      
      return pass;
   }
   
   private static int countPackage(Field[] fields)
   {
      int cnt = fields.length
                - countModifiers(fields, Modifier.PRIVATE)
                - countModifiers(fields, Modifier.PROTECTED)
                - countModifiers(fields, Modifier.PUBLIC);

      // Adjust for students that have written assert statment(s) in their code
      // The package field specified below gets added to the .class file when
      // assert statements are present in the source.
      for (Field f : fields)
      {
         int mods = f.getModifiers();
         
         if (Modifier.isStatic(mods)
          && Modifier.isFinal(mods)
          && f.getName().equals("$assertionsDisabled"))
         {
            cnt--;
         }
      }
      
      return cnt;
   } 
}
