/**
* @author Garrett Milster
* @version Lab Quiz 1
*/

public class SafeMessageCracker
{
   public SafeMessageCracker()
   {
   }
   
   public int[] crack(SafeMessage safe)
   {
      int i;
      int[] combo = new int[4];
      boolean state1, state2;
      
      state1 = safe.isOpen();
      
      for(i = 0; i < 100; i++)
      {
         combo[0] = i;
         
            for(i = 0; i < 100; i++)
            
               combo[1] = i;
               
               for(i = 0; i < 100; i++)
               
                  combo[2] = i;
                  
                  for(i = 0; i < 100; i++)
                     combo[3] = i;
                     
         state2 = safe.open(combo[0], combo[1], combo[2], combo[3]);
                     
      }
              
      
     return(combo);
   }
}