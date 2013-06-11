import java.util.Scanner; 

public class Driver
{

// This method must be inside the Driver class you are writing.
public static void main(String[] args)
 {
   // Declare and construct a Scanner object to read from the command-line

   Scanner scanner = new Scanner(System.in);
   // Prompt for a name
   System.out.print("What is your name? ");
   // Read the name using the Scanner
   String name = scanner.nextLine();
   // Construct a Greeter object
   Greeter greeter = new Greeter(name);
   Greeter greeter2 = new Greeter("President Obama");
   // Get the greeting and save it to a String
   String greeting = greeter.greet();
   // Display the greeting to the command-line
   System.out.println(greeting);
    System.out.println(greeter2.greet());

 }
}