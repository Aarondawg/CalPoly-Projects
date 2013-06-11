/****************************************************
 PL/SQL anonymous block illustrating basic UPDATE with
 error handling and transactional processing
 M. Liu, Winter 2011
 This block will prompt for 3 input values.
     start updateEmpZip 
 ****************************************************/
SET AUTOCOMMIT OFF;
-- start a transaction and make transaction processing settings
COMMIT;
WHENEVER OSERROR CONTINUE ROLLBACK;
WHENEVER SQLERROR CONTINUE ROLLBACK;
SET TRANSACTION ISOLATION LEVEL READ SERIALIZABLE;
-- error handling done in code
DECLARE
  choice boolean := &true_or_false_to_commit;
  no_update EXCEPTION;  -- used when no update can be made
  eno_ employees.eno%TYPE := &eno;
  zip_ employees.zip%TYPE := &zip;
BEGIN
   UPDATE employees SET zip = zip_  WHERE  eno = eno_;
   -- Check to see if the update was made to the specified employee
   IF (sql%rowcount = 0) THEN
      -- we could simply do ROLLBACK here
      RAISE no_update; 
   END IF;    
   IF (choice) THEN
      COMMIT;
      DBMS_OUTPUT.PUT_LINE('COMMIT made');
   ELSE
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('ROLLBACK made');
   END IF; 
EXCEPTION 
   -- for invalid ENO specified
   WHEN no_update THEN
      dbms_output.put_line
        ('No update was made - please check input values.');
      ROLLBACK;
   -- For errors such as foreign-key constraint violation
   WHEN OTHERS THEN
      dbms_output.put_line('Error occurred: '|| ': ' || SQLERRM );   
      ROLLBACK;                        
END;
/



