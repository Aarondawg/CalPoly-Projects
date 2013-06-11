// Garrett Milster
// Program 04
// CSC103

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
public class SpellCheck
{
   public static void main(String[] args)
   {
      ArrayList<String> list = new ArrayList<String>();
      File file = new File(args[0]);
      try
      {
         //Scans in the words from the file passed in as an argument in the command line.
         Scanner in = new Scanner(file);
         
         while (in.hasNext()) 
         {  
            //while the file has a next token, scan it in.
            list.add(in.next());
         }

            in.close();
         }
      catch(java.io.FileNotFoundException e)
      {

      }
      // repeating the same process, but for the words in a known file called 'unsortedWords.txt'
      ArrayList<String> list2 = new ArrayList<String>();
      File file2 = new File("unsortedWords.txt");
      try
      {
         Scanner in = new Scanner(file2);
         
         while (in.hasNext()) 
         {  
            list2.add(in.next());
         }

            in.close();
         }
      catch(java.io.FileNotFoundException e)
      {

      } 
          
      ItemRecord item;
      RecordBST record = new RecordBST();
      ArrayList<ItemRecord> recordList = new ArrayList<ItemRecord>();
      ArrayList<ItemRecord> foundList = new ArrayList<ItemRecord>();
      
      //Creates the binary search tree of itemRecords.
      for(int i = 0; i < list2.size(); i++)
      {
         item = new ItemRecord(list2.get(i));
         record.insert(item);
      }
      
      // loops through the words from the command line file, and checks if they are in the BST and how many times
      // if they are not found they are placed in a second ArrayList
      
      for(int i = 0; i < list.size(); i++)
      {
         item = new ItemRecord(list.get(i));
         int count = record.find(item);
         item.tally = count;
         
         if(count > 1)
         {
            for(int j = 0; j < foundList.size(); j++)
            {               
               if(item.token.equals(foundList.get(j).token))
               {
                  foundList.get(j).tally ++;
               }
            }
         }
         else if(count > 0)
         {
            foundList.add(item);
         }
         else 
         {
            recordList.add(item);
         }
      }
      
      bubbleSort(foundList);
      //Words that were found are sorted and now printed
      System.out.println("The dictionary file contained the following words: ");
      for(int i = 0; i < foundList.size(); i++)
         System.out.println(foundList.get(i).token + " " + foundList.get(i).tally);
      System.out.println("");
      
      //this large loop spell checks the unfound words
      ArrayList<String> spelledList;
      System.out.println("The following words were not found: ");
      for(int i = 0; i < recordList.size(); i++)
      {
         spelledList = new ArrayList<String>();
         int count = 0;
         String temp1 = recordList.get(i).token;
         String holder = recordList.get(i).token;
         //checks possible spellings by switching pairs of letters.
         for(int j = 0; j < temp1.length() - 1; j++)
         {
            char[] temp2 = holder.toCharArray();
            char temp3 = temp2[j];
            temp2[j] = temp2[j+1];
            temp2[j+1] = temp3;
            temp1 = "";
               for(int k = 0; k < temp2.length; k++)
               {
                  temp1 = temp1 + temp2[k];
               }
            item = new ItemRecord(temp1);
            //if the search method finds a word, it adds it to a list and moves on with the loop.
            if(record.find(item) > 0)
            {
               count = 1; 
               spelledList.add(temp1);
            }  
         }
         
         if(count == 0)
         {  
            // If no possible spellings were found, the counter will be zero and this statement will appear.       
            System.out.println((recordList.get(i)).token + "     possible spellings: none found...");
         }
         else
         {
            //if possible spellings were found, it prints the list of words.
            System.out.print((recordList.get(i)).token + "     possible spellings: ");
            for(int j = 0; j < spelledList.size(); j++)
            {
               if(j == spelledList.size() - 1)
                  System.out.println(spelledList.get(j));
               else
                  System.out.print(spelledList.get(j) + ", ");                  
            }
         }
      }
   }
   
   //simple sorting algorithm
   private static void bubbleSort(ArrayList<ItemRecord> array)
   {
      ItemRecord temp;
      for (int i=0; i<array.size() - 1; i++) {
         for (int j=0; j<array.size() - 1-i; j++) {
            if (array.get(j).token.compareToIgnoreCase(array.get(j+1).token) > 0) {
               temp = array.get(j); array.set(j,array.get(j+1)); array.set(j+1, temp);
            }
         }
      }
   }

}