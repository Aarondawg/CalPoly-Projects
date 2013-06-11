import mycs1.*;

public class Lab0
{
   public static void main(String[] args)
   {
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
