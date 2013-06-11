// Sample code illustrating insertion into a table using JDBC. 
// Professor M. Liu, Spring 2008
// WARNING: There is no validation of user-input data; not a good idea.
 
import java.io.*;
import java.util.*;
import java.sql.*;

class Insert{
	
   public static void main (String args []){
      Connection conn = null;
      ResultSet rset = null;
      PreparedStatement pstmt = null;
      Scanner sc = null;
      String query = null, line = null; 	  
      int i = 0;
   	  
      try {
         conn = login();    
         conn.setAutoCommit(false);
         conn. setTransactionIsolation(
               Connection.TRANSACTION_READ_COMMITTED);
         // for keyboard input
         sc = new Scanner(System.in);

         // Create a reusable parameterized prepared statement.  Once 
         // prepared, the query can be executed repeatedly with new 
         // values without preparation.
         query = "insert into employees (eno,ename,zip,hdate)" +
               " values (?, ?, ?, ?)";
         pstmt = conn.prepareStatement(query);
         String num = null, name = null, zip  = null, date = null;     
         // loop for as many tuple as to be inserted
         System.out.println("Enter <ENO> <name> <zip code> <DD-MON-YYYY>");
         num = sc.next( ).trim();  name = sc.next( ).trim();  
         zip = sc.next().trim();   date = sc.next().trim();
         System.out.println("num = " + num + "; name = " + name
                          + "; zip = " + zip + "; date = " +  date);
         pstmt.setString(1, num);  
         pstmt.setString(2, name);          
         pstmt.setString(3, zip);  
         pstmt.setString(4, date);         
         pstmt.executeUpdate();  //IMPORTANT: NO PARAMETER
         System.out.println("Tuple inserted into EMPLOYEES table"
                        + " using a prepared query statement.");
         // end loop
      } //end try
      catch (Exception ex){
  	  ex.printStackTrace( );    // for debugging
      }
      finally {
         try {
            // List the resulting table     
            query = "select * from EMPLOYEES";
            pstmt = conn.prepareStatement(query);
            rset = pstmt.executeQuery(query);
  
            System.out.println("EMPLOYEES now contains:");	
            System.out.println("\tENO    \tENAME\tZIP\tHDATE");
            while (rset.next ()){
               System.out.print(i + "\t");
               System.out.print (rset.getString ("eno") + "\t");
               System.out.print (rset.getString ("ename") + "\t");
               System.out.print (rset.getString ("zip") + "\t");
               System.out.print (rset.getDate ("hdate"));
               System.out.println( );
               i++;
            } //end while
            System.out.println
              ("Do you want to make the changes permanent? Enter Y or N.");
            line = sc.next( ).trim().toUpperCase( ); 
            if (line.charAt(0)== 'Y')
               conn.commit( );
            else
               conn.rollback( );
            pstmt.close( );
            rset.close( );
            conn.close( ); 
         }
         catch (Exception ex) {  
   	 }    	
      } // end finally   	
  
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
