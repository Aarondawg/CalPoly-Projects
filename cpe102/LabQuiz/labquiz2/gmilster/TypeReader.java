/**
 * @author Garrett Milster
 * @version Lab Quiz 3
 */
 
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TypeReader
{
   private byte temp;
   private String filename;
   DataInputStream data = new DataInputStream(new FileInputStream(new File(filename)));
   
   public TypeReader(String file) throws FileNotFoundException, IOException
   {
      filename = file;
   }
   
   public byte nextType() throws UnknownTypeException, FileNotFoundException, IOException
   {
      try
      {
         temp = data.readByte();
         
         if(temp == 0 || temp == 1 || temp == 2)
         {
            return temp;
         }
         else
         {
            throw new UnknownTypeException();
         }
      }
      catch(java.io.EOFException e)
      {
         return -1;
      }
   }
   
   public boolean nextBoolean() throws FileNotFoundException, IOException
   {
      boolean tempboolean = false;
      
      if(temp == 0)
      {
         tempboolean = data.readBoolean();
         return tempboolean;
      }
      else
      {
         throw new InvalidNextCallException();
      }
   }
   
   public short nextShort() throws FileNotFoundException, IOException
   {
      short tempshort = 0;
      
      if(temp == 1)
      {
         tempshort = data.readShort();
         return tempshort;
      }
      else
      {
         throw new InvalidNextCallException();
      }
   }

   public float nextFloat() throws FileNotFoundException, IOException
   {
      float tempfloat = 0;
      
      if(temp == 2)
      {
         tempfloat = data.readFloat();
         return tempfloat;
      }
      else
      {
         throw new InvalidNextCallException();
      }
   }      
}
          
         