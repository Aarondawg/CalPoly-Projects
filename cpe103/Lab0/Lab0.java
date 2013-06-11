import mycs1.*;

public class Lab0
{
   public static void main(String[] args)
   {
      boolean again = true;
      
      while(again != false)
      {
         System.out.print("Please enter the value: ");
         int value = Keyboard.readInt();
         System.out.print("Please enter the base: ");
         int base = Keyboard.readInt();
         System.out.println("");
         if(base < 2 || base > 16)
         {
            throw new RuntimeException("Base must be between 2 and 16");
         }
      
         if(value < 0)
         {
            int temp1 = value;
            value = value * -1;
            changeBase(value, base);
            String temp = changeBase(value, base);
            System.out.println("Initial Value: " + temp1);
            System.out.println("Initial Base: " + base);
            System.out.println("New Value: -" + temp);
         }
         else
         {
            changeBase(value, base);
            String temp = changeBase(value, base);
            System.out.println("Initial Value: " + value);
            System.out.println("Initial Base: " + base);
            System.out.println("New Value: " + temp);
         }
         System.out.println("");
         System.out.print("Would you like to go again? (y/n) : ");
         char c = Keyboard.readChar();
         
         if(c == 'n')
         {
            again = false;
         }
      }
   }

   private static final char[] digits = {'0','1','2','3','4','5','6','7','8',
                                         '9','A','B','C','D','E','F'};

   // value must be >= 0 and base must be in 2..16

   private static String changeBase(long value,int base)
   {
      if (value < base)
         return "" + digits[(int)value];
      else
         return changeBase(value/base,base) + digits[(int)(value%base)];
   }
}
