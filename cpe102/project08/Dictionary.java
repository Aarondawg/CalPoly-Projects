/**
 * A Dictionary program which reads files, sorts them, and decrypts them if need be.
 * 
 *
 * @author Garrett Milster   
 * @version Program 8            
 */
 
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;


public class Dictionary implements java.lang.Iterable<String>
{
   private ArrayList<String> list = new ArrayList();
   
   public Dictionary(java.lang.String fileName, boolean sorted)
   {     
      
      try
      {
         File file = new File(fileName);
         
         Scanner in = new Scanner(file);
         while (in.hasNext()) 
         {  
            list.add(in.next());
         }
         
         in.close();
      }
      catch(java.io.FileNotFoundException e)
      {

      }
            
      if(sorted == false)
      {
         sort();
      }
   }
   
   public Dictionary(java.lang.String fileName, boolean sorted, long seed) 
   {  
      int[] map = MapArray(seed);
      int newline = 0;
      newline = map[10];
      
      try
      {
         java.io.File file = new java.io.File(fileName);
         java.io.FileInputStream filein = new java.io.FileInputStream(file);
         java.io.DataInputStream in = new java.io.DataInputStream(filein);
         ArrayList<Character> chars = new ArrayList();
         boolean built = false;
         String tempString = "";

         try
         {
            while(built == false)
            {
               byte temp = in.readByte();
               if(temp == newline)
               {
                  list.add(tempString);
                  tempString = "";
               }
               else
               {
                  char character = (char)temp;
                  tempString = tempString + character;
                 
               }
            }
         }
         catch(java.io.EOFException e)
         {
            
         }
         
         in.close();
      }   
      catch(java.io.FileNotFoundException e)
      {
      }
      catch(java.io.IOException j)
      {
         
      }
      
      decrypt(seed);

      if(sorted == false)
      {
         sort();
      }

   }
   private void sort() 
   {
      int size = list.size();
      for (int pass = 1; pass < size; pass++)
      {   
         for (int i=0; i < size - pass; i++) 
         {
              //System.out.println("COMPARING: " + list.get(i));
              //System.out.println("WITH: " + list.get(i+1));
              //System.out.println("VALUE: " + (list.get(i)).compareTo(list.get(i+1)));
              
              if((list.get(i)).compareTo(list.get(i+1)) > 0) 
              {
                 String temp = list.get(i);  
                 list.set(i, list.get(i+1));  
                 list.set(i+1, temp);
              }
         }
      }
   }
   
   private void decrypt(long seed)
   {
      int[] map = MapArray(seed);
      

      for(int k = 0; k < list.size(); k++)
      {
          String temp = list.get(k);
          //System.out.println(temp);
          int count[] = new int[temp.length()];
          char count2[] = new char[temp.length()];
          int index2 = 0;
          
          for(int r = 0; r < temp.length(); r++)
          {
             count[r] = temp.charAt(r);
          }
          
          for(int t = 0; t < count.length; t++)
          {
             for(int w = 0; w < map.length; w++)
             {
                if(count[t] == map[w])
                {
                   index2 = w;
                }
             }
             
             count2[t] = (char)index2;
          }
          
          String temp2 = new String(count2);
          //System.out.println(temp2);
          list.set(k, temp2);
      }
      
      
   }
   
   private int[] MapArray(long seed)
   {
      Random random = new Random(seed);
      int[] map = new int[128];
      
      for(int i = 0; i < 128; i++)
      {
         int index = random.nextInt(128);
         
         //System.out.println("WORD: " + i);
         
         while(checker(index, map, i) == 1)
         {
            index = random.nextInt(128);
         }
         
         map[i] = index;
         
         //System.out.println("");
      }
      
      return(map);
   }

   
   private int checker(int index,int[] map, int i)
   {
      for(int j = 0; j < i; j++)
      {
         if(map[j] == index)
         {
            //System.out.println("REPEATED VALUE: " + index + " AT INDEX: " + j);
            //System.out.println("REPLACED WITH: " + index); 
            return 1;
         }
      }  
      
      return 0;  

   }
   
   public boolean lookUp(String string)
   {
      int low = 0;
      int high = list.size() - 1;
      int mid = (low + high) / 2;
      
      while(low <= high)
      {
         
         mid = (low + high) / 2;
 
         
         if(string.compareTo(list.get(mid)) > 0)
         {
            low = mid + 1;
         }
         else if(string.compareTo(list.get(mid)) < 0)
         {
            high = mid - 1;
         }
         else
         {
            return true;
         }
      }
      
      return false;
   }
   
   public void write(String fileName)
   {
      
      java.io.FileOutputStream fileOutput; 
      java.io.DataOutputStream dataOutput;
      
      try 
      {
        FileOutputStream fileIO = new java.io.FileOutputStream(new File(fileName));
        
        for(int i = 0; i < list.size(); i++)
        {
           for(int j = 0; j < list.get(i).length(); j++)
           {
              fileIO.write(list.get(i).charAt(j));
           }
           
           if(i != list.size() - 1)
           {
              fileIO.write('\n');
           }
        }
       }
       catch(java.io.FileNotFoundException e)
       {
          
       }
       catch(java.io.IOException e1)
       {
          
       }
   }
   
   public java.util.Iterator<String> iterator()
   {
      java.util.Iterator<String> it = list.iterator();
      return it;
   }
   
}