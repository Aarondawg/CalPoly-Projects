-- Lab 9 | Part 2
-- Name: Garrett Milster
-- Partner: Ross McKelvie
CREATE OR REPLACE
PROCEDURE listSales(eno_ employees.eno%type, pno_ parts.pno%type) AS
	-- DELCARE
   ono_ orders.ono%TYPE;
   received_ orders.received%TYPE;
   price_ parts.price%TYPE;

   checkRows NUMBER;
   
   CURSOR order_cursor IS
      SELECT o.ono, o.received, d.qty * p.price PRICE INTO ono_, received_, price_
   	FROM orders o
   	INNER JOIN odetails d ON o.ono = d.ono
   	INNER JOIN parts p ON d.pno = p.pno
   	WHERE d.pno = pno_ AND o.eno = eno_
   	ORDER BY o.ono;
   
   order_record order_cursor%ROWTYPE;

BEGIN
	dbms_output.put_line('The orders for part ' || pno_ || ' by ' || eno_ || ' are:');
	
	FOR order_record in order_cursor LOOP
	   checkRows := order_cursor%ROWCOUNT;
	   IF checkRows IS NULL THEN
	      dbms_output.put_line('No data found');
	   END IF;
		--EXIT WHEN order_cursor%NOTFOUND;
	    dbms_output.put_line(order_cursor%ROWCOUNT || '. ' || order_record.ono || ', ' || order_record.received || ', ' || order_record.PRICE);
	END LOOP;
	
	IF checkRows IS NULL THEN
      dbms_output.put_line('No Data Found');
   END IF;

EXCEPTION
	WHEN OTHERS THEN
	    dbms_output.put_line('Error occurred: ' || ':' || SQLERRM);
END;
/
show errors