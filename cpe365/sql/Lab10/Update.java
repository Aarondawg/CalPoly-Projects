// Sample JDBC code illustrating updating a table using 
//  a PreparedStatement object. 
// Professor M. Liu, Spring 2008
// WARNING: There is no validation of user-input data; not a good idea.
 
import java.io.*;
import java.util.*;
import java.sql.*;

class Update{
	
   public static void main (String args []){
      Connection conn = null;
      Statement  stmt = null;
      ResultSet rset = null;
      PreparedStatement pstmt = null;
      String query = null, line = null; 	  
      int i = 0; 	  
      try {
         conn = login(); 
         conn.setAutoCommit(false);
         conn. setTransactionIsolation (Connection.TRANSACTION_SERIALIZABLE);
         // For keyboard input
         Scanner sc = new Scanner(System.in);
         System.out.println("Enter <ONO> and <PNO> to update:");
         String ono = sc.next( ), pno = sc.next();
         
        // List current QTY -- WARNING: NO input data validation
        query = "select * from odetails where ONO = " + ono +
                 " and PNO = " + pno;
        pstmt = conn.prepareStatement(query);
        rset = pstmt.executeQuery(query);
        rset.next();
        System.out.println("The current QTY is " + rset.getString("qty"));
        System.out.println("Enter the new QTY:");
        query = "update odetails set QTY = ?  " +
                 " where ONO = " + ono +
                 " and PNO = " + pno;
        pstmt = conn.prepareStatement(query);       
        pstmt.setString(1, sc.next( ));         
        pstmt.executeUpdate(); // NO PARAMETERs!!
        System.out.println("Update made.");
        pstmt.close( );
         // List the resulting table     
         query = "select * from Odetails";
         pstmt = conn.prepareStatement(query);
         rset = pstmt.executeQuery(query);
  
         System.out.println("Odetails now contains:");	
         System.out.println("\tONO    \tPNO\tQTY");
         while (rset.next ()){
            System.out.print(i + "\t");
            System.out.print (rset.getString ("ono") + "\t");
            System.out.print (rset.getString ("pno") + "\t");
            System.out.print (rset.getString ("qty") + "\t");
            System.out.println( );
            i++;
         } //end while
         System.out.println
            ("Do you want to make the change permanent? Enter Y or N.");
         line = sc.next( ).trim().toUpperCase( ); 
         if (line.charAt(0)== 'Y')
            conn.commit( );
         else
           conn.rollback( );
      } //end try
      catch (Exception ex){
  	     ex.printStackTrace( );
      }
      finally {
      	 try { 
             //strictly speaking, each close should be in its own try-catch.
             stmt.close( );
             pstmt.close( );
             rset.close( );
             conn.close( ); 
             keyboard.close( );
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
