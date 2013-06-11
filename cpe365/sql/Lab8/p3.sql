/**
p3.sql in PL/SQL
Ross McKelvie
Partner: Garrett Milster
*/
SET AUTOCOMMIT OFF;
COMMIT;
WHENEVER OSERROR CONTINUE ROLLBACK;
WHENEVER SQLERROR CONTINUE ROLLBACK;

DECLARE
	decreaseamt_ INTEGER := &decreaseamt;

BEGIN
	UPDATE parts SET QOH = qoh - decreaseamt_;
	COMMIT;
	
EXCEPTION
	WHEN OTHERS THEN
		dbms_output.put_line('Error occurred: '|| ': ' || SQLERRM );   
		ROLLBACK;
END;
/