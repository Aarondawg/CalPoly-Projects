/**
  Winter 2012 CPE365 Lab 10
// Lab 10 part 2
// ROSS MCKELVIE & GARRETT MILSTER
 **/

import java.io.*;
import java.util.*;
import java.sql.*;

class InsertZip{
   public static void main (String[] args ){
      Scanner keyboard = null, lineScan = null;
      String zip_ = null, city_ = null, line = null;
      Connection conn = null;
      ResultSet rset = null;
      PreparedStatement pstmt1 = null;
      PreparedStatement pstmt2 = null;
   /*
   <<< ADD other declarations as needed>>>
   */	  
      try {
         conn = login();    
         conn.setAutoCommit(false);
         conn. setTransactionIsolation(
               Connection.TRANSACTION_READ_COMMITTED);
         keyboard = new Scanner(System.in);
                
         String query =
                  "select zip, city " +
                  "from zipcodes " +
                  "order by zip";
         System.out.println("QUERY STRING is:\n" + query);
         pstmt1 = conn.prepareStatement(query);

        query = "insert into zipcodes (zip,city)" +
               " values (?, ?)";  
         pstmt2 = conn.prepareStatement(query);     
         line = " ";
         while (line.length( ) != 0){  
            System.out.println
               ("Enter ZIP then CITY, or press return to quit:");
            line = keyboard.nextLine( );
            if (line.length( ) == 0)
               continue;
            lineScan = new Scanner(line);
            try {
               zip_ = lineScan.next();
               city_ = lineScan.next();
               System.out.println("You entered: " + zip_ + 
                                   " for zip and " + city_ + " for city");
               pstmt2.setString(1, zip_);  
               pstmt2.setString(2, city_); 
               
               pstmt2.executeUpdate();
               System.out.println("Insertion made");
            }

            catch (Exception ex) {
                System.out.println("That didn't work. Please try again.");
                 
            }
         }

         // Exceute the query and obtain the result set
            rset = pstmt1.executeQuery();
            int i = 1;
            System.out.println("The zipcodes table now contains:");
            System.out.println("\tZIP    \tCITY");
            while (rset.next ()){
               System.out.print(i + "\t");
               System.out.print (rset.getString ("zip") + "\t");
               System.out.print (rset.getString ("city"));
               System.out.println( );
               i++;
            } //end while
            rset.close( );
         System.out.println
            ("Want to make the changes made permanent?" +
             " Enter Y or N.");
         line = keyboard.next( ).toUpperCase( ); 
         System.out.println("You entered " + line.charAt(0));
         if (line.charAt(0)== 'Y')
         {
            conn.commit();
         } else {
            conn.rollback();
         }
            
         
      } //end try
      catch (Exception ex){
  	     ex.printStackTrace( );
      }
      finally {
      	 try {
             keyboard.close( );
            pstmt1.close();
            pstmt2.close();
            rset.close();
            conn.close(); 
         }
         catch (Exception ex) {  
   	   }    	
      } // end finally   	
  
   } //end main
   private static Connection login() throws Exception{
    Console console = System.console();
    String id = console.readLine("Enter your Oracle userID: ");
    char[] pword = console.readPassword("Enter Oracle password: ");
    Class.forName ("oracle.jdbc.OracleDriver");
    Connection conn = DriverManager.getConnection(
      "jdbc:oracle:thin:@hercules.csc.calpoly.edu:1522:ora10g",
      id, new String(pword));
    return conn;
}
   
} //end class

/*
gmilster@301x20:~/sql/Lab10 $ java InsertZip
Enter your Oracle userID: gmilster
Enter Oracle password: 
QUERY STRING is:
select zip, city from zipcodes order by zip
Enter ZIP then CITY, or press return to quit:
90210   Beverly Hills
You entered: 90210 for zip and Beverly for city
Insertion made
Enter ZIP then CITY, or press return to quit:
93444    Nipomo
You entered: 93444 for zip and Nipomo for city
Insertion made
Enter ZIP then CITY, or press return to quit:
67226    Wichita
You entered: 67226 for zip and Wichita for city
That didn't work. Please try again.
Enter ZIP then CITY, or press return to quit:
95224    Avery
You entered: 95224 for zip and Avery for city
Insertion made
Enter ZIP then CITY, or press return to quit:
Boron  93516
You entered: Boron for zip and 93516 for city
That didn't work. Please try again.
Enter ZIP then CITY, or press return to quit:
93407
That didn't work. Please try again.
Enter ZIP then CITY, or press return to quit:
999      Nowhere
You entered: 999 for zip and Nowhere for city
Insertion made
Enter ZIP then CITY, or press return to quit:
123456  Somewhere
You entered: 123456 for zip and Somewhere for city
That didn't work. Please try again.
Enter ZIP then CITY, or press return to quit:

The zipcodes table now contains:
	ZIP    	CITY
1	999	Nowhere
2	11111	paris
3	22222	rome
4	50302	Kansas City
5	54444	Columbia
6	60606	Fort Dodge
7	61111	Fort Hays
8	66002	Liberal
9	67226	Wichita
10	90210	Beverly
11	93444	Nipomo
12	95224	Avery
Want to make the changes made permanent? Enter Y or N.
n
You entered N
*/
