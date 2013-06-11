/**
 * @author Garrett Milster
 * @version Lab Quiz 3
 */
 
 import java.util.List;
 import java.io.PrintWriter;
 import java.io.File;
 
public class ListWriter
{
   public ListWriter()
   {
   }

   public void write(List<String> list, String filename) throws java.io.FileNotFoundException
   {
      File file = new File(filename);
      PrintWriter writer = new PrintWriter(file);
      
      for(int i = 0; i < list.size(); i++)
      {
         writer.write(list.get(i));
         writer.println("");
      }
      writer.close();
   }
   
   
}