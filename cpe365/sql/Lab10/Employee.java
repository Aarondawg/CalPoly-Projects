// Sample JDBC code illustrating the execution and processing of a 
// query that does not modify  the database. 
//  Professor M. Liu, Winter 2012
import java.io.*;
import java.util.*;
import java.sql.*;

class Employee{	
   public static void main (String args []){
      // The following declarations are put outside the "try" block so that
      // they are accessible everywhere in this main method.
      Connection conn = null;
      PreparedStatement  stmt = null;
      ResultSet rset = null;  	  
      try {
         conn = login();
         String query =          	
                  "select * " +
         	  "from   EMPLOYEES e, ZIPCODES z " +
         	  "where  e.zip = z.zip " +
                  "order by eno"; 
         System.out.println("QUERY STRING is:\n" + query);
         stmt = conn.prepareStatement(query);
         	
         // Exceute the query and obtain the result set 
         rset = stmt.executeQuery();
         	
         // Iterate through the result set and print the attribute values
         int i = 1; 
         System.out.println("\tENO    \tENAME\tCITY");
         while (rset.next ()){
            System.out.print(i + "\t");
            System.out.print (rset.getString ("eno") + "\t");
            System.out.print (rset.getString ("ename") + "\t");
            System.out.print (rset.getString ("city"));
            System.out.println( );
            i++;
         } //end while
         rset.close( );
      } //end try
      catch (Exception ex){
  	     ex.printStackTrace( );
      }
      finally {
      	 try {
             stmt.close( );
             conn.close( ); 
         }
         catch (Exception ex) {
   	        ex.printStackTrace( );    
   	 }    	
      }    	
  
   } //end main
   /**
    * A helper method for making an Oracle connection.
    * It will prompt for userID and password.
    */
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
