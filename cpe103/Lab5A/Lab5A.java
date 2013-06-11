public class Lab5A
{
   public static void main(String[] args)
   {
      int[] data = {12,8,10,19,6,9,15,16,1,4,7,2,11,14,17,5,18,20,3,13};
      int[] parent = {6,4,2,1,4,8,6,12,10,8,10,0,14,15,19,15,16,17,12,19};
      MyBST tree = new MyBST();
      int i,j;
      for(i=0;i<data.length;++i)
         tree.insert(new Integer(data[i]));

      java.util.Arrays.sort(data);
      Comparable item;

      int ctr = 0;
      for(i=0;i<data.length;++i)
      {
         item = tree.parent(new Integer(data[i]));
         if (item == null)
            j = 0;
         else
            j = ((Integer)item).intValue();
         if (j != parent[i])
         {
            ++ctr;
            System.out.println("ERROR: The parent of " + data[i] + " is not " + item);
         }
      }
      item = tree.parent(new Integer(-1));
      if (item != null)
      {
         ++ctr;
         System.out.println("ERROR: The parent of -1 is not " + item);
      }
      if (ctr == 0)
         System.out.println("Passed all parent tests");

      ctr = 0;
      item = tree.predecessor(new Integer(data[0]));
      if (item != null)
      {
         ++ctr;
         System.out.println("ERROR: The predecessor of " + data[0] + " is not " + item);
      }
      for(i=1;i<data.length;++i)
      {
         item = tree.predecessor(new Integer(data[i]));
         if (item == null)
         {
            ++ctr;
            System.out.println("ERROR: The predecessor of " + data[i] + " is not null");
         }
         else if (((Integer)item).intValue() != data[i-1])
         {
            ++ctr;
            System.out.println("ERROR: The predecessor of " + data[i] + " is not " + item);
         }
      }
      item = tree.predecessor(new Integer(-1));
      if (item != null)
      {
         ++ctr;
         System.out.println("ERROR: The predecessor of -1 is not " + item);
      }
      if (ctr == 0)
         System.out.println("Passed all predecessor tests");

      ctr = 0;
      for(i=0;i<data.length-1;++i)
      {
         item = tree.successor(new Integer(data[i]));
         if (item == null)
         {
            ++ctr;
            System.out.println("ERROR: The successor of " + data[i] + " is not null");
         }
         else if (((Integer)item).intValue() != data[i+1])
         {
            ++ctr;
            System.out.println("ERROR: The successor of " + data[i] + " is not " + item);
         }
      }
      item = tree.successor(new Integer(data[data.length-1]));
      if (item != null)
      {
         ++ctr;
         System.out.println("ERROR: The successor of " + data[data.length-1] + 
            " is not " + item);
      }
      item = tree.successor(new Integer(-1));
      if (item != null)
      {
         ++ctr;
         System.out.println("ERROR: The successor of -1 is not " + item);
      }
      if (ctr == 0)
         System.out.println("Passed all successor tests");
   }
}