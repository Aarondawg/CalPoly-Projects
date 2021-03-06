--------------------------------------------------------------------------------
-- This file contains sql*plus SET commands for setting various sql*plus
-- environment variables to their common settings.
-- Each line that begins with a dash is a comment line.
-------------------------------------------------------------------------------
SET ARRAYSIZE 100 
-- Fetch size (1 to 5000) =  the number of rows that will be retrieved in one go

SET AUTOCOMMIT OFF
--  Autocommit ON  commits after each SQL command or PL/SQL block

SET ECHO      ON 
-- ECHO = ON will Display the command on screen (+ spool)
-- ECHO = OFF will Display the command on screen but not in spool files.
-- Interactive commands are always echoed to screen/spool.

SET FEEDBACK  ON 
-- FEEDBACK = ON will count rows returned

SET HEADING   ON 
-- HEADING = OFF will hide column headings

SET LINESIZE  80 
-- LINESIZE = width of page (80 is typical)

SET NEWPAGE 0 
--   The number of blank lines between the top of each page and the top title.
--   0 = a formfeed between pages.

SET PAGESIZE  50   
-- PAGESIZE = height 54 is 11 inches (0 will suppress all headings and page brks)

SET PAUSE     ON 
-- PAUSE = ON .. press return at end of each page

SET SERVEROUTPUT ON
-- Disallow output from server - turn this ON when running PL/SQL

SET TIME ON 
--    ON = display timing statistics for each SQL command or PL/SQL block run.
--    OFF = suppress timing statistics

SET SQLPROMPT "&_user> "
-- Set the command prompt to display the user name     

SET TERM      ON 
-- TERM = ON will display on terminal screen (OFF = show in LOG only)

SET TRIMOUT   ON 
-- TRIMOUT = ON will remove trailing spaces from output

SET TRIMSPOOL ON 
-- TRIMSPOOL = ON will remove trailing spaces from spooled output
------------------------------------------------------------------------------------------------
