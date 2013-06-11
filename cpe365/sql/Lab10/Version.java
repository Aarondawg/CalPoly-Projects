/*
 * This sample can be used to check the JDBC driver version installed
 * with your Oracle 10g Client.  It will prompt for your oracle account
 * ID and password (masked).  Be sure to enter your ID without "@ora10g".
 *
 * To compile and run, you need to set your CLASSPATH to include:
 *   /app/oracle/product/10.2.0/client_1/jdbc/lib/classes12.jar
 * (as of Fall 2009)
 * You may do so in your Linux bash shell account by adding this
 * line to your ~/.mybashrc file:
 *  export CLASSPATH=$CLASSPATH:/app/oracle/product/10.2.0/client_1/jdbc/lib/classes12.jar:.
 *
 *   Professor M. Liu, Computer Science Dept., Cal Poly San Luis Obispo
 *   Winter 2012
 */

import java.sql.*;
import java.io.Console;

class Version {
  public static void main (String args []){
    String id = null;
    char[ ] pword = null;   	  
    Connection conn = null;
    try {
       Console console = System.console();
       id = console.readLine("Enter your Oracle userID: "); 
       pword = console.readPassword("Enter your Oracle password: ");
       System.out.println ("Connecting...");
       // Load the Oracle JDBC driver
       Class.forName ("oracle.jdbc.OracleDriver"); 
       System.out.println ("Driver class found and loaded.");   
       conn = DriverManager.getConnection(
         "jdbc:oracle:thin:@hercules.csc.calpoly.edu:1522:ora10g",
         id, new String(pword));
       System.out.println ("connected.");

       // Create Oracle DatabaseMetaData object
       DatabaseMetaData meta = conn.getMetaData( );
       System.out.println ("JDBC driver version is " +
          meta.getDriverVersion());
       // Close the connection
       conn.close();
    } //end try
    catch (Exception ex){
       try {
         conn.close();
       }
       catch (Exception ex1){}
       ex.printStackTrace( ); // for debugging
    }

  } //end main
} //end class
