public class Tester
{
   public static void main(String[] args) throws java.io.FileNotFoundException,java.io.IOException
   {
      java.util.ArrayList<String> list = new java.util.ArrayList();
      String string1 = "dog";
      String string2 = "cat";
      
      list.add(string1);
      list.add(string2);
      list.add(string1);
      list.add(string2);
      
      ListWriter writer = new ListWriter();
      
      writer.write(list, "TESTFILE.txt");
   }
}