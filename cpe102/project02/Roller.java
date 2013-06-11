/**
 * A Roller class which holds dice and rolls them, generating a random value.
 * 
 * @author Garrett Milster
 * @version Program 2
 */

public class Roller
{
   private Die[] die;
   
   public Roller(int numberOfDie)
   {
      int i;
      die = new Die[numberOfDie];
      for(i = 0; i < numberOfDie; i++)
      {
         die[i] = new Die();
      }
   }
   
   public Roller(int numberOfDie, int numberOfSides) 
   {
      int i;
      die = new Die[numberOfDie];
      
      for(i = 0; i < numberOfDie; i++)
      {
         die[i] = new Die(numberOfSides);
      }
   }
   
   public Roller(int numberOfDie, int numberOfSides, long[] seeds) 
   {
      int i;
      die = new Die[numberOfDie];

      for(i = 0; i < numberOfDie; i++)
      {
         die[i] = new Die(numberOfSides, seeds[i]);
      }
   }

   public Roller(int numberOfDie, long[] seeds) 
   {
      int i;
      die = new Die[numberOfDie];

      for(i = 0; i < numberOfDie; i++)
      {
         die[i] = new Die(seeds[i]);
      }
   }

   public int[] roll()
   {
      int i, numberOfDie;
      int[] rollValue = new int[die.length];

      for(i = 0; i < die.length; i++)
      {
         rollValue[i] = die[i].roll();
      }

      return(rollValue);
   }

   public int[] roll(int numberOfDie)
   {
      int i;
      int[] rollValue = new int[numberOfDie];

      for(i = 0; i < numberOfDie; i++)
      {
         rollValue[i] = die[i].roll();
      }

      return(rollValue);
   }
} 