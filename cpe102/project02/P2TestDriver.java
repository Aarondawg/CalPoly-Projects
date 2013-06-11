import java.util.Scanner;
import java.io.*;
import java.lang.reflect.*;

public class P2TestDriver
{
   private static final String RESULTS_FOR = "Results for Program 2";
   
   public static void main(String[] args) throws FileNotFoundException
   {
      boolean pass = true;
      printHeader(args);
      
      pass &= testDieArchitecture();
      pass &= testRollerArchitecture();
            
      if (pass)
      {
         // Die tests...
         //
         // NOTE: The value(), sides(), and roll() methods are used to verify the behaviors
         // of the constructors.  Any errors reported for a constructor may be because of
         // error(s) in the constructor being tested or because of error(s) in value(), 
         // sides(), or roll().       
         pass &= testDieDefaultConstructor();
         pass &= testDieConstructor2();
         pass &= testDieConstructor3();
         pass &= testDieConstructor4();   

      }
      
      //if (pass)
      {
         // RollerTests...
         //
         // NOTE: The roll() and roll(int) methods are used to verify the behaviors of the
         // constructors.  Any errors reported for a constructor may be because
         // of and error(s) in the constructor being tested or because of error(s) in roll()
         // or roll(int).         
         pass &= testRollerConstructor1();
         pass &= testRollerConstructor2();
         pass &= testRollerConstructor3();
         pass &= testRollerConstructor4();  
      }
      
      printResults(pass);
      
       // Added for to support robust script checking
      if (!pass)
      {
         System.exit(-1);
      }
   }

   private static boolean testDieArchitecture() throws FileNotFoundException
   {
      boolean pass = true;
      String msg = "      FAILED: ";
      Class cl;
      Class[] temp;
      
      System.out.println("   Testing Die architecture...");
       
      cl = Die.class;
      
      pass &= test(Die.DEFAULT_NUMBER_OF_SIDES == 6, " DEFAULT_NUMBER_OF_SIDES is not 6");

      int cnt = cl.getConstructors().length;     
      pass &= test(cnt == 4, msg + cnt + " public constructors found, expected 4");
      
      cnt = countModifiers(cl.getDeclaredMethods(), Modifier.PUBLIC);     
      pass &= test(cnt == 3, msg + cnt + " public non-constructor methods found, expected 3");
      
      cnt = cl.getFields().length;
      pass &= test(cnt == 1, msg + cnt + " public fields found, expected 1");
      
      cnt = countModifiers(cl.getDeclaredFields(), Modifier.PROTECTED);
      pass &= test(cnt == 0, msg + cnt + " protected fields found, expected 0");
      
      cnt = countModifiers(cl.getDeclaredFields(), Modifier.PRIVATE);
      pass &= test(cnt == 3, msg + cnt + " private fields found, expected 3");
         
      cnt = cl.getDeclaredFields().length
          - countModifiers(cl.getDeclaredFields(), Modifier.PRIVATE)
          - countModifiers(cl.getDeclaredFields(), Modifier.PROTECTED)
          - countModifiers(cl.getDeclaredFields(), Modifier.PUBLIC);
      pass &= test(cnt == 1, msg + cnt + " package fields found, expected 1");

      pass &= test(useOf("Math", "Die.java"), msg
            + "The word \"Math\" appears in your source code - please remove it");
 
      return pass;
   }

   private static boolean testRollerArchitecture() throws FileNotFoundException
   {
      boolean pass = true;
      String msg = "      FAILED: ";
      Class cl;
      Class[] temp;
      
      System.out.println("   Testing Roller architecture...");
       
      cl = Roller.class;

      int cnt = cl.getConstructors().length;     
      pass &= test(cnt == 4, msg + cnt + " public constructors found, expected 4");
      
      cnt = countModifiers(cl.getDeclaredMethods(), Modifier.PUBLIC);     
      pass &= test(cnt == 2, msg + cnt + " public non-constructor methods found, expected 2");
      
      cnt = cl.getFields().length;
      pass &= test(cnt == 0, msg + cnt + " public fields found, expected 0");
      
      cnt = countModifiers(cl.getDeclaredFields(), Modifier.PROTECTED);
      pass &= test(cnt == 0, msg + cnt + " protected fields found, expected 0");
      
      cnt = countModifiers(cl.getDeclaredFields(), Modifier.PRIVATE);
      pass &= test(cnt == 1, msg + cnt + " private fields found, expected 1");
         
      cnt = cl.getDeclaredFields().length
          - countModifiers(cl.getDeclaredFields(), Modifier.PRIVATE)
          - countModifiers(cl.getDeclaredFields(), Modifier.PROTECTED)
          - countModifiers(cl.getDeclaredFields(), Modifier.PUBLIC);
      pass &= test(cnt == 0, msg + cnt + " package fields found, expected 0");
 
      pass &= test(useOf("java.util", "Roller.java"), msg
            + "It appears you are importing or using something in java.util - remove it");
            
      pass &= test(useOf("Math", "Roller.java"), msg
            + "The word \"Math\" appears in your source code - please remove it");
            
      return pass;
   }
     
   // Test default Die constructor
   private static boolean testDieDefaultConstructor()
   {
      boolean pass = true;
      System.out.println("   Testing default constructor - Die()...");
     
      // Test sides
      String msg = "      FAILED sides(): ";
      Die die = new Die();
      
      pass &= test(die.sides() == Die.DEFAULT_NUMBER_OF_SIDES, msg + die.sides() 
            + " sides found, expected " + Die.DEFAULT_NUMBER_OF_SIDES);

      // Test value()
      msg = "      FAILED value(): ";
      die = new Die();
      int firstValue = die.value();
      int invalidValue = 0;
      boolean validValues = true;
      boolean differentValues = false;
      boolean[] valuesConstructed = new boolean[Die.DEFAULT_NUMBER_OF_SIDES];
      
      for (int i = 0; i < 1000; i++)
      {
         int value = new Die().value();
         
         if (value < 1 || value > Die.DEFAULT_NUMBER_OF_SIDES)
         {
            validValues = false;
            invalidValue = value;
         }
         else
         {
            valuesConstructed[value-1] = true;
         }
         
         if (value != firstValue)
         {
            differentValues = true;
         }
      }
      
      pass &= test(validValues, msg
            + "Constructor produced a die with a value of " + invalidValue 
            + ", expect values from 1 to " + Die.DEFAULT_NUMBER_OF_SIDES + ", inclusive");

      pass &= test(differentValues, msg
            + "Constructor always results in the same value - not very random!");
      
      boolean allValuesConstructed = true;
      for (int i = 0; i < valuesConstructed.length; i++)
      {
         allValuesConstructed &= valuesConstructed[i];
      }
      
      pass &= test(allValuesConstructed, msg
            + "Not all die values were produced during construction");
            
      // Test roll()
      msg = "      FAILED roll(): ";
      die = new Die();
      int invalidRoll = 0;
      boolean validRolls = true;
      boolean differentRolls = false;
      boolean differentRollValueResults = false;
      int[] rolls = new int[1000];
      boolean[] valuesRolled = new boolean[Die.DEFAULT_NUMBER_OF_SIDES];
           
      for (int i = 0; i < 1000; i++)
      {
         rolls[i] = die.roll();
         
         if (rolls[i] < 1 || rolls[i] > Die.DEFAULT_NUMBER_OF_SIDES)
         {
            validRolls = false;
            invalidRoll = rolls[i];
         }
         else
         {
            valuesRolled[rolls[i] - 1] = true;
         }
         
         if (rolls[i] != rolls[0])
         {
            differentRolls = true;
         }
         
         if (rolls[i] != die.value())
         {
            differentRollValueResults = true;
         }
      }

      pass &= test(validRolls, msg
            + "roll() produced a value of " + invalidRoll 
            + ", expect values from 1 to " + Die.DEFAULT_NUMBER_OF_SIDES + ", inclusive");

      pass &= test(differentRolls, msg
            + "roll() always has the same result - not very random!");

      pass &= test(!differentRollValueResults, msg
            + "value() does not always return the same value as the last roll()");

      boolean allValuesRolled = true;
      for (int i = 0; i < valuesRolled.length; i++)
      {
         allValuesRolled &= valuesRolled[i];
      }
      
      pass &= test(allValuesRolled, msg
            + "Not all die values were produced by roll()");
            
      Die die2 = new Die();
      int differentCount = 0;
      
      for (int i = 0; i < 1000; i++)
      {
         if (die2.roll() != rolls[i])
         {
            differentCount++;
         }
      }

      pass &= test(differentCount > 1000 - (2 * 1000/Die.DEFAULT_NUMBER_OF_SIDES),
                   msg + "Roll results for two different Die not as different as expected"
                   + " - try running test again");

      return pass;
   }
   

   // Test constructor Die(int sides)
   private static boolean testDieConstructor2()
   {
      boolean pass = true;
      System.out.println("   Testing constructor - Die(int sides)...");
     
      // Test sides
      String msg = "      FAILED sides(): ";
      final int SIDES = 31;
      Die die = new Die(SIDES);
      
      pass &= test(die.sides() == SIDES, msg + die.sides() 
            + " sides found, expected " + SIDES);

      // Test value()
      msg = "      FAILED value(): ";
      die = new Die(SIDES);
      int firstValue = die.value();
      int invalidValue = 0;
      boolean validValues = true;
      boolean differentValues = false;
      boolean[] valuesConstructed = new boolean[SIDES];
      
      for (int i = 0; i < 1000; i++)
      {
         int value = new Die(SIDES).value();
         
         if (value < 1 || value > SIDES)
         {
            validValues = false;
            invalidValue = value;
         }
         else
         {
            valuesConstructed[value-1] = true;
         }
                  
         if (value != firstValue)
         {
            differentValues = true;
         }
      }
      
      pass &= test(validValues, msg
            + "Constructor produced a die with a value of " + invalidValue 
            + ", expect values from 1 to " + SIDES + ", inclusive");

      pass &= test(differentValues, msg
            + "Constructor always results in the same value - not very random!");

      boolean allValuesConstructed = true;
      for (int i = 0; i < valuesConstructed.length; i++)
      {
         allValuesConstructed &= valuesConstructed[i];
      }
      
      pass &= test(allValuesConstructed, msg
            + "Not all die values were produced during construction");
            
      
      // Test roll()
      msg = "      FAILED roll(): ";
      die = new Die(SIDES);
      int invalidRoll = 0;
      boolean validRolls = true;
      boolean differentRolls = false;
      boolean differentRollValueResults = false;
      int[] rolls = new int[1000];
      boolean[] valuesRolled = new boolean[SIDES];
            
      for (int i = 0; i < 1000; i++)
      {
         rolls[i] = die.roll();
         
         if (rolls[i] < 1 || rolls[i] > SIDES)
         {
            validRolls = false;
            invalidRoll = rolls[i];
         }
         else
         {
            valuesRolled[rolls[i] - 1] = true;
         }

         if (rolls[i] != rolls[0])
         {
            differentRolls = true;
         }
         
         if (rolls[i] != die.value())
         {
            differentRollValueResults = true;
         }
      }

      pass &= test(validRolls, msg
            + "roll() produced a value of " + invalidRoll 
            + ", expect values from 1 to " + SIDES + ", inclusive");

      pass &= test(differentRolls, msg
            + "roll() always has the same result - not very random!");

      pass &= test(!differentRollValueResults, msg
            + "value() does not always return the same value as the last roll()");

      boolean allValuesRolled = true;
      for (int i = 0; i < valuesRolled.length; i++)
      {
         allValuesRolled &= valuesRolled[i];
      }
      
      pass &= test(allValuesRolled, msg
            + "Not all die values were produced by roll()");
            
      Die die2 = new Die(SIDES);
      int differentCount = 0;
      
      for (int i = 0; i < 1000; i++)
      {
         if (die2.roll() != rolls[i])
         {
            differentCount++;
         }
      }

      pass &= test(differentCount > 1000 - (2 * 1000/SIDES),
                   msg + "Roll results for two different Die not as different as expected"
                   + " - try running test again");

      return pass;
   }
   
   // Test constructor Die(int sides, long seed)
   private static boolean testDieConstructor3()
   {
      boolean pass = true;
      System.out.println("   Testing constructor - Die(int sides, long seed)...");
     
      // Test sides
      String msg = "      FAILED sides(): ";
      final int SIDES = 19;
      final long SEED = 3891233;
      Die die = new Die(SIDES, SEED);
      
      pass &= test(die.sides() == SIDES, msg + die.sides() 
            + " sides found, expected " + SIDES);

      // Test value()
      msg = "      FAILED value(): ";
      die = new Die(SIDES, SEED);
      int firstValue = die.value();
      int invalidValue = 0;
      boolean validValues = true;
      boolean differentValues = false;
      boolean[] valuesConstructed = new boolean[SIDES];
      
      for (int i = 0; i < 1000; i++)
      {
         int value = new Die(SIDES, SEED).value();
         
         if (value < 1 || value > SIDES)
         {
            validValues = false;
            invalidValue = value;
         }
         else
         {
            valuesConstructed[value-1] = true;
         }
                  
         if (value != firstValue)
         {
            differentValues = true;
         }
      }
      
      pass &= test(validValues, msg
            + "Constructor produced a die with a value of " + invalidValue 
            + ", expect values from 1 to " + SIDES + ", inclusive");

      pass &= test(!differentValues, msg
            + "Constructor does not always results in the same value as expected when seeded");

      boolean allValuesConstructed = true;
      for (int i = 0; i < valuesConstructed.length; i++)
      {
         allValuesConstructed &= valuesConstructed[i];
      }
      
      pass &= test(!allValuesConstructed, msg
            + "All possible die values were produced during construction - not correct "
            + "for a seed Die");
            
      
      // Test roll()
      msg = "      FAILED roll(): ";
      die = new Die(SIDES, SEED);
      int invalidRoll = 0;
      boolean validRolls = true;
      boolean differentRolls = false;
      boolean differentRollValueResults = false;
      int[] rolls = new int[1000];
      boolean[] valuesRolled = new boolean[SIDES];
            
      for (int i = 0; i < 1000; i++)
      {
         rolls[i] = die.roll();
         
         if (rolls[i] < 1 || rolls[i] > SIDES)
         {
            validRolls = false;
            invalidRoll = rolls[i];
         }
         else
         {
            valuesRolled[rolls[i] - 1] = true;
         }

         if (rolls[i] != rolls[0])
         {
            differentRolls = true;
         }
         
         if (rolls[i] != die.value())
         {
            differentRollValueResults = true;
         }
      }

      pass &= test(validRolls, msg
            + "roll() produced a value of " + invalidRoll 
            + ", expect values from 1 to " + SIDES + ", inclusive");

      pass &= test(differentRolls, msg
            + "roll() always has the same result - not very random!");

      pass &= test(!differentRollValueResults, msg
            + "value() does not always return the same value as the last roll()");

      boolean allValuesRolled = true;
      for (int i = 0; i < valuesRolled.length; i++)
      {
         allValuesRolled &= valuesRolled[i];
      }
      
      pass &= test(allValuesRolled, msg
            + "Not all die values were produced by roll()");
            
      Die die2 = new Die(SIDES, SEED);
      int differentCount = 0;
      
      for (int i = 0; i < 1000; i++)
      {
         if (die2.roll() != rolls[i])
         {
            differentCount++;
         }
      }

      pass &= test(differentCount == 0,
                   msg + "Roll results for two different seeded Die are not the same "
                   + "values in the same order as expected");

      return pass;
   }
 
   // Test constructor Die(long seed)
   private static boolean testDieConstructor4()
   {
      boolean pass = true;
      System.out.println("   Testing constructor - Die(long seed)...");
     
      // Test sides
      String msg = "      FAILED sides(): ";
      final int SIDES = Die.DEFAULT_NUMBER_OF_SIDES;
      final long SEED = -78534;
      Die die = new Die(SEED);
      
      pass &= test(die.sides() == SIDES, msg + die.sides() 
            + " sides found, expected " + SIDES);

      // Test value()
      msg = "      FAILED value(): ";
      die = new Die(SEED);
      int firstValue = die.value();
      int invalidValue = 0;
      boolean validValues = true;
      boolean differentValues = false;
      boolean[] valuesConstructed = new boolean[SIDES];
      
      for (int i = 0; i < 1000; i++)
      {
         int value = new Die(SEED).value();
         
         if (value < 1 || value > SIDES)
         {
            validValues = false;
            invalidValue = value;
         }
         else
         {
            valuesConstructed[value-1] = true;
         }
                  
         if (value != firstValue)
         {
            differentValues = true;
         }
      }
      
      pass &= test(validValues, msg
            + "Constructor produced a die with a value of " + invalidValue 
            + ", expect values from 1 to " + SIDES + ", inclusive");

      pass &= test(!differentValues, msg
            + "Constructor does not always results in the same value as expected when seeded");

      boolean allValuesConstructed = true;
      for (int i = 0; i < valuesConstructed.length; i++)
      {
         allValuesConstructed &= valuesConstructed[i];
      }
      
      pass &= test(!allValuesConstructed, msg
            + "All possible die values were produced during construction - not correct "
            + "for a seed Die");
            
      
      // Test roll()
      msg = "      FAILED roll(): ";
      die = new Die(SEED);
      int invalidRoll = 0;
      boolean validRolls = true;
      boolean differentRolls = false;
      boolean differentRollValueResults = false;
      int[] rolls = new int[1000];
      boolean[] valuesRolled = new boolean[SIDES];
            
      for (int i = 0; i < 1000; i++)
      {
         rolls[i] = die.roll();
         
         if (rolls[i] < 1 || rolls[i] > SIDES)
         {
            validRolls = false;
            invalidRoll = rolls[i];
         }
         else
         {
            valuesRolled[rolls[i] - 1] = true;
         }

         if (rolls[i] != rolls[0])
         {
            differentRolls = true;
         }
         
         if (rolls[i] != die.value())
         {
            differentRollValueResults = true;
         }
      }

      pass &= test(validRolls, msg
            + "roll() produced a value of " + invalidRoll 
            + ", expect values from 1 to " + SIDES + ", inclusive");

      pass &= test(differentRolls, msg
            + "roll() always has the same result - not very random!");

      pass &= test(!differentRollValueResults, msg
            + "value() does not always return the same value as the last roll()");

      boolean allValuesRolled = true;
      for (int i = 0; i < valuesRolled.length; i++)
      {
         allValuesRolled &= valuesRolled[i];
      }
      
      pass &= test(allValuesRolled, msg
            + "Not all die values were produced by roll()");
            
      Die die2 = new Die(SEED);
      int differentCount = 0;
      
      for (int i = 0; i < 1000; i++)
      {
         if (die2.roll() != rolls[i])
         {
            differentCount++;
         }
      }

      pass &= test(differentCount == 0,
                   msg + "Roll results for two different seeded Die are not the same "
                   + "values in the same order as expected");

      return pass;
   }

   // Test constructor Roller(int numberOfDice)
   private static boolean testRollerConstructor1()
   {
      boolean pass = true;
      System.out.println("   Testing constructor - Roller(int numberOfDice)...");
      final int DICE = 29;
      final int SIDES = Die.DEFAULT_NUMBER_OF_SIDES;
      
      // Test roll()
      String msg = "      FAILED roll(): ";
      
      Roller roller = new Roller(9679);
      int[] rolls = roller.roll();
      pass &= test(rolls.length == 9679, msg + "Expected " + 9679 + " roll values, "
            + "found " + rolls.length);
      
      roller = new Roller(DICE); 
      boolean[] history = new boolean[SIDES];
           
      for (int i = 0; i < 100; i++)
      {
         rolls = roller.roll();
         pass &= test(rolls.length == DICE, msg + "Expected " + DICE + " roll values, "
            + "found " + rolls.length);
            
         for (int j = 0; j < rolls.length; j++)
         {
            history[rolls[j]-1] = true;
         }
      }
      
      boolean rolledAllValues = true;
      
      for (int i = 0; i < history.length; i++)
      {
         rolledAllValues &= history[i];
      }
      
      pass &= test(rolledAllValues, msg + "All possible die values were not produced as expected");
             
      // Test roll(int numberOfDice)
      msg = "      FAILED roll(int numberOfDice): ";
      history = new boolean[SIDES];
      
      for (int i = 1; i < DICE; i++)
      {
         rolls = roller.roll(i);
         
         pass &= test(rolls.length == i, msg + "Expected " + i + " roll values, "
            + "found " + rolls.length);
            
         for (int j = 0; j < rolls.length; j++)
         {
            history[rolls[j]-1] = true;
         }
      }

      rolledAllValues = true;
      
      for (int i = 0; i < history.length; i++)
      {
         rolledAllValues &= history[i];
      }
      
      pass &= test(rolledAllValues, msg + "All possible die values were not produced as expected");

      return pass;
   }

   // Test constructor Roller(int numberOfDice, int numberOfSides)
   private static boolean testRollerConstructor2()
   {
      boolean pass = true;
      System.out.println("   Testing constructor - Roller(int numberOfDice,"
                       + " int numberOfSides)...");
      final int DICE = 32;
      final int SIDES = 11;
      
      // Test roll()
      String msg = "      FAILED roll(): ";
      
      Roller roller = new Roller(17979, SIDES);
      int[] rolls = roller.roll();
      pass &= test(rolls.length == 17979, msg + "Expected " + 17979 + " roll values, "
            + "found " + rolls.length);
      
      roller = new Roller(DICE, SIDES); 
      boolean[] history = new boolean[SIDES];
           
      for (int i = 0; i < 100; i++)
      {
         rolls = roller.roll();
         pass &= test(rolls.length == DICE, msg + "Expected " + DICE + " roll values, "
            + "found " + rolls.length);
            
         for (int j = 0; j < rolls.length; j++)
         {
            history[rolls[j]-1] = true;
         }
      }
      
      boolean rolledAllValues = true;
      
      for (int i = 0; i < history.length; i++)
      {
         rolledAllValues &= history[i];
      }
      
      pass &= test(rolledAllValues, msg + "All possible die values were not produced as expected");
             
      // Test roll(int numberOfDice)
      msg = "      FAILED roll(int numberOfDice): ";
      history = new boolean[SIDES];
      
      for (int i = 1; i < DICE; i++)
      {
         rolls = roller.roll(i);
         
         pass &= test(rolls.length == i, msg + "Expected " + i + " roll values, "
            + "found " + rolls.length);
            
         for (int j = 0; j < rolls.length; j++)
         {
            history[rolls[j]-1] = true;
         }
      }

      rolledAllValues = true;
      
      for (int i = 0; i < history.length; i++)
      {
         rolledAllValues &= history[i];
      }
      
      pass &= test(rolledAllValues, msg + "All possible die values were not produced as expected");

      return pass;
   }

   // Test constructor Roller(int numberOfDice, int numberOfSides, long[] seeds)
   private static boolean testRollerConstructor3()
   {
      boolean pass = true;
      System.out.println("   Testing constructor - Roller(int numberOfDice,"
                       + " int numberOfSides, long[] seeds)...");
      final int DICE = 9;
      final int SIDES = 7;
      long[] seeds = new long[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
      
      // Test roll()
      String msg = "      FAILED roll(): ";

      Roller roller = new Roller(DICE, SIDES, seeds);
      int[] rolls = null;
      boolean[] history = new boolean[SIDES];
      boolean different = false;
           
      for (int i = 0; i < 100; i++)
      {
         rolls = roller.roll();
         
         pass &= test(rolls.length == DICE, msg + "Expected " + DICE + " roll values, "
            + "found " + rolls.length);
            
         for (int j = 0; j < rolls.length; j++)
         {
            history[rolls[j]-1] = true;
         }
         
         // make sure die have different values each roll
         for (int j = 1; j < rolls.length; j++)
         {
            different |= rolls[j-1] == rolls[j];
         }
      }
      
      boolean rolledAllValues = true;
      
      for (int i = 0; i < history.length; i++)
      {
         rolledAllValues &= history[i];
      }
      
      pass &= test(rolledAllValues, msg + "All possible die values were not produced as expected");
         
      pass &= test(different, msg + "All die for a particular roll are the same - not very random!");
        
      // Test roll(int numberOfDice)
      msg = "      FAILED roll(int numberOfDice): ";
      history = new boolean[SIDES];
      
      for (int i = 1; i < DICE; i++)
      {
         rolls = roller.roll(i);
         
         pass &= test(rolls.length == i, msg + "Expected " + i + " roll values, "
            + "found " + rolls.length);
            
         for (int j = 0; j < rolls.length; j++)
         {
            history[rolls[j]-1] = true;
         }
      }

      rolledAllValues = true;
      
      for (int i = 0; i < history.length; i++)
      {
         rolledAllValues &= history[i];
      }
      
      pass &= test(rolledAllValues, msg + "All possible die values were not produced as expected");

      return pass;
   }

   // Test constructor Roller(int numberOfDice, long[] seeds)
   private static boolean testRollerConstructor4()
   {
      boolean pass = true;
      System.out.println("   Testing constructor - Roller(int numberOfDice,"
                       + " int numberOfSides, long[] seeds)...");
      final int DICE = 19;
      final int SIDES = 6;
      long[] seeds = new long[] {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};
      
      // Test roll()
      String msg = "      FAILED roll(): ";

      Roller roller = new Roller(DICE, seeds);
      int[] rolls = null;
      boolean[] history = new boolean[SIDES];
      boolean same = true;
           
      for (int i = 0; i < 100; i++)
      {
         rolls = roller.roll();
         
         pass &= test(rolls.length == DICE, msg + "Expected " + DICE + " roll values, "
            + "found " + rolls.length);
            
         for (int j = 0; j < rolls.length; j++)
         {
            history[rolls[j]-1] = true;
         }
         
         // make sure die have SAME values each roll
         for (int j = 1; j < rolls.length; j++)
         {
            same &= rolls[j-1] == rolls[j];
         }
      }
      
      boolean rolledAllValues = true;
      
      for (int i = 0; i < history.length; i++)
      {
         rolledAllValues &= history[i];
      }
      
      pass &= test(rolledAllValues, msg + "All possible die values were not produced as expected");
         
      pass &= test(same, msg + "All die for a particular roll are NOT the same as "
            + "expected with same seed");
        
      // Test roll(int numberOfDice)
      msg = "      FAILED roll(int numberOfDice): ";
      history = new boolean[SIDES];
      
      for (int i = 1; i < DICE; i++)
      {
         rolls = roller.roll(i);
         
         pass &= test(rolls.length == i, msg + "Expected " + i + " roll values, "
            + "found " + rolls.length);
            
         for (int j = 0; j < rolls.length; j++)
         {
            history[rolls[j]-1] = true;
         }
      }

      rolledAllValues = true;
      
      for (int i = 0; i < history.length; i++)
      {
         rolledAllValues &= history[i];
      }
      
      pass &= test(rolledAllValues, msg + "qqqAll possible die values were not produced as expected");

      return pass;
   }

   private static void printHeader(String[] args)
   {
      if (args.length == 1)
      {
         System.out.println(args[0]);
      }
      
      System.out.println(RESULTS_FOR + "\n");
   }
   
   private static void printResults(boolean pass)
   {
      String msg;
      
      if(pass)
      {
         msg = "\nCongratulations, you passed all the tests!\n\n"
            + "Your grade will be based on when you turn in your functionally\n"
            + "correct solution and any deductions for the quality of your\n"
            + "implementation.  Quality is based on, but not limited to,\n"
            + "coding style, documentation requirements, compiler warnings,\n"
            + "and the efficiency and elegance of your code.\n";
      }
      else
      {
         msg = "\nNot done yet - you failed one or more tests!\n";
      }
      
      System.out.print(msg);       
   }
   
   private static boolean test(boolean pass, String msg)
   {
      if(!pass)
      {
         System.out.println(msg);
      }
      
      return pass;
   }

   private static int countModifiers(Field[] fields, int modifier)
   {
      int count = 0;
      
      for (Field f : fields)
      {
         if (f.getModifiers() == modifier)
         {
            count++;
         }
      }
      
      return count;
   }
   
   private static int countModifiers(Method[] methods, int modifier)
   {
      int count = 0;
      
      for (Method m : methods)
      {
         if (m.getModifiers() == modifier)
         {
            count++;
         }
      }
      
      return count;
   }

   private static boolean useOf(String match, String fname) throws FileNotFoundException
   {
      Scanner scanner = new Scanner(new File(fname));
         
      while (scanner.hasNextLine())
      {
         String line = scanner.nextLine();
         
         if (line.contains(match))
         {
            // Using, importing, or referring to something you should not be
            scanner.close();
            return false;
         }
      }
      
      scanner.close();
      
      // Pass - not found...
      return true;
   }      
}