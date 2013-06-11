public class Test
{
   public static void main(String[] args)
   {
      LinkedList list = new LinkedList();
      
      list.add(4.0);
      list.add(2.0);
      list.add(3.0);
      list.add(4.0);
      list.add(5.0);
      list.add(6.0);
      
      for(int i = 0; i < list.size(); i++)
      {
         System.out.println("Node" + i + ": " + list.get(i));
      }
      
         System.out.println("");
         
      list.set(2, "HELLO");
      
      for(int i = 0; i < list.size(); i++)
      {
         System.out.println("Node" + i + ": " + list.get(i));
      }

      System.out.println("");
      
      System.out.println("IndexOF: " + list.indexOf("HELLO"));
      System.out.println("");
      list.add(2, "TEST");
      System.out.println("Size: " + list.size());
      
      for(int i = 0; i < list.size(); i++)
      {
         System.out.println("Node" + i + ": " + list.get(i));
      }
      
      list.remove(0);

      
      System.out.println("");

      for(int i = 0; i < list.size(); i++)
      {
         System.out.println("Node" + i + ": " + list.get(i));
      }
      
      System.out.println("");
      
     list.add(0,1);
     list.add(0,3);
     for(int i = 0; i < list.size(); i++)
     {
        System.out.println("Node" + i + ": " + list.get(i));
     }
     
     System.out.println("");
     
     ListIterator i = list.listIterator();
     
     System.out.println("index: " + i.next());
     System.out.println("index: " + i.next());
     System.out.println("index: " + i.next());       
     System.out.println("index: " + i.next());
     System.out.println("index: " + i.next());     
     System.out.println("index: " + i.next());   
     System.out.println("index: " + i.next());     
     System.out.println("index: " + i.next());    
     
          System.out.println("");
    
     System.out.println("VALID: " + i.hasPrevious());
     System.out.println("prev: " + i.previous());
     System.out.println("VALID: " + i.hasPrevious());
     System.out.println("prev: " + i.previous());
     System.out.println("VALID: " + i.hasPrevious());
     System.out.println("prev: " + i.previous());
     System.out.println("VALID: " + i.hasPrevious());
     System.out.println("prev: " + i.previous());
     System.out.println("VALID: " + i.hasPrevious());
     System.out.println("prev: " + i.previous());
     System.out.println("VALID: " + i.hasPrevious());
     System.out.println("prev: " + i.previous());
     System.out.println("VALID: " + i.hasPrevious());
     System.out.println("prev: " + i.previous());
     System.out.println("VALID: " + i.hasPrevious());
      System.out.println("prev: " + i.previous());
        
   }
}