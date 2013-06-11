public class Test
{
   public static void main(String[] args)
   {

      
      
      String test2 = "fullOrderedDict7113.bin";
      
      Dictionary dict2 = new Dictionary(test2, true, 7113);
      

      dict2.write("TestDict.txt");
    
      
      /* String test3 = "partialRandomDict.txt";
       Dictionary dict3 = new Dictionary(test3, false);

       for(int i = 0; i < 100; i++)
       {
          System.out.println(dict3.get(i));
       }*/
   }
   
}