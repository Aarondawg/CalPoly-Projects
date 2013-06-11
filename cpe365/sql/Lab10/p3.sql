CREATE OR REPLACE  FUNCTION orderValue(ono_ orders.ono%type)
	RETURN NUMBER	-- NUMBER is an Oracle type equivalent to NUMERIC
AS
-- DECLARE
	   returnVal NUMBER;
 	   
  	 BEGIN
	    
 	    SELECT SUM (qty*price) INTO returnVal
 	    FROM   odetails INNER JOIN parts USING (pno)
 	    WHERE  ono = ono_;
  	    RETURN (returnVal);
 	 EXCEPTION
    	   WHEN OTHERS THEN
 	       	RETURN null;
  	 END;
/
show errors;
