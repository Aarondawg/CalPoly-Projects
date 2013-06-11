public class SafeTest
{
  public static void main(String[] args)
  {
      SafeMessage safe = new SafeMessage("hello",1,2,3,4);
      SafeMessageCracker cracker = new SafeMessageCracker();
      String msg;
      int[] combo = new int[4];
      
      safe.open(1,2,3,4);
      msg = safe.toString();
      
      System.out.println(msg);
      
      combo = cracker.crack(safe);
      System.out.println(combo[0], combo[1], combo[2], combo[3]);
   }
}