// Lab 10 part 1
// ROSS MCKELVIE & GARRETT MILSTER

import java.sql.*;
import java.util.*;
import java.io.*;
import java.io.Console;

class OrderValue{
   public static void main (String args []){
	Connection conn = null;
      PreparedStatement  stmt = null;
      ResultSet rset = null;
      try {
         conn = login();
         String query =
                  "select ono, orderValue(ono) " +
                  "from orders " +
                  "order by ono";
         System.out.println("QUERY STRING is:\n" + query);
         stmt = conn.prepareStatement(query);

         // Exceute the query and obtain the result set
         rset = stmt.executeQuery();
            int i = 1;
            System.out.println("\tONO    \tVALUE");
            while (rset.next ()){
               System.out.print(i + "\t");
               System.out.print (rset.getString ("ono") + "\t");
               System.out.print (rset.getString ("orderValue(ono)"));
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
         
  }
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
}


/*

gmilster@301x20:~/sql/Lab10 $ java OrderValue
Enter your Oracle userID: gmilster
Enter Oracle password: 
QUERY STRING is:
select ono, orderValue(ono) from orders order by ono
	ONO    	VALUE
1	1020	139.93
2	1021	99.96
3	1022	44.98
4	1023	39.98
5	1024	24.99
6	1025	99.96

*/




