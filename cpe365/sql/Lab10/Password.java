// Lab 10 part 2
// ROSS MCKELVIE & GARRETT MILSTER
// ASSUMPTION: username & password must be alpha numeric

import java.sql.*;
import java.util.*;
import java.io.*;

class Password {

    public static void main(String args[]) {
        boolean DEBUG = true, done = false;
        //boolean DEBUG = false, done = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String user_col_name = "x_";
        String pword_col_name = "y_";
        String query = null, id = null, pword = null;
        String input1 = null, input2 = null;
        Scanner keyboard = new Scanner(System.in);

        try {
            conn = login();

            // Set up the part of the query that's static
            String query1 = "Select * from PASSWORDS where " + user_col_name + " = ";

            while (!done) {
                System.out.println("Enter your login name for accessing your bank account");
                input1 = keyboard.nextLine().trim();

                if (!input1.matches("[a-zA-z0-9]*")) {
                    System.out.println("Invalid username characters");
                    break;
                }
                
                System.out.println("Enter your password");
                input2 = keyboard.nextLine().trim();

                if (!input2.matches("[a-zA-z0-9]*")) {
                    System.out.println("Invalid password characters");
                    break;
                }



                try {
                    // Make the query
                    query = query1 + "? and " + pword_col_name + " = ?";
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, input1);
                    stmt.setString(2, input2);

                    rset = stmt.executeQuery();

                    if (rset.next())  // id-password combo found
                        done = true; // user can now access his/her bank account
                } catch (SQLException ex) {
                    System.out.println("Execution of that query string failed.\n " + query);
                    if (DEBUG) ex.printStackTrace();
                }
            }//end while

            if (done) System.out.println("Welcome to your bank account!");
        } //end try
        catch (Exception ex) {
            if (DEBUG) ex.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {}
            try {
                conn.close();
            } catch (Exception ex) {}
        } // end finally

    } //end main

    /**
     * A helper method for making an Oracle connection.
     * It will prompt for userID and password.
     */
    private static Connection login() throws Exception {
        Console console = System.console();
        String id = console.readLine("Enter your Oracle userID: ");
        char[] pword = console.readPassword("Enter Oracle password: ");
        Class.forName("oracle.jdbc.OracleDriver");
        Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@hercules.csc.calpoly.edu:1522:ora10g",
                id, new String(pword));
        return conn;
    }

} //end class
