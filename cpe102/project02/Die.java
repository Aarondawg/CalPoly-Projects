/**
 * A Die class whose number of sides can be specified and a random 
 * number genenerator creates a value each time it's rolled.
 * 
 * @author Garrett Milster
 * @version Program 2
 */

import java.util.Random;

public class Die
{
   private int sides;
   private int currentValue;
   public static final int DEFAULT_NUMBER_OF_SIDES = 6;
   private Random random;


   public Die()
   {
      this.sides = DEFAULT_NUMBER_OF_SIDES;
      random = new Random();

      currentValue = random.nextInt(DEFAULT_NUMBER_OF_SIDES) + 1;
   }

   public Die(int sides)
   {
      this.sides = sides;
      random = new Random();

      currentValue = random.nextInt(sides) + 1;
   }

   public Die(int sides, long seed)
   {
      this.sides = sides;
      random = new Random(seed);

      currentValue = random.nextInt(sides) + 1;
   }

   public Die(long seed)
   {
      this.sides = DEFAULT_NUMBER_OF_SIDES;
      random = new Random(seed);

      currentValue = random.nextInt(sides) + 1;
   }

   public int roll()
   {
      currentValue = random.nextInt(sides) + 1;

      return(currentValue);
   }

   public int sides()
   {
      int num = this.sides;

      return(num);
   }

   public int value()
   {
      return(currentValue);
   }

}