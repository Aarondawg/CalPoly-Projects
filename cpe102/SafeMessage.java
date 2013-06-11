/**
* @author Garrett Milster
* @version Lab Quiz 1
*/

public class SafeMessage
{
   private int n1,n2,n3,n4;
   private String message;
   private boolean state;
   
   public SafeMessage(String message, int n1, int n2, int n3, int n4)
   {
      this.message = message;
      this.n1 = n1;
      this.n2 = n2;
      this.n3 = n3;
      this.n4 = n4;
      
      state = false;
   }
   
   public boolean isOpen()
   {
      return(state);
   }
   
   public boolean open(int p1, int p2, int p3, int p4)
   {
      if(n1 == p1 && n2 == p2 && n3 == p3 && n4 == p4)
      {
         state = true;
      }
      
      return(state);
   }
   
   public void close()
   {
      state = false;
   }
   
   public String toString()
   {
      if(state = false)
      {
         return("Sorry, the SafeMessage is locked!");
      }else{
         return(message);
      }
   }
}
