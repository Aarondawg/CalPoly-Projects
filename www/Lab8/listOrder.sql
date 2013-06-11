/**********************************************************************
 listOrder.sql
   A PL/SQL block that uses run-time input by string substitution.
 M. Liu, fall 2009
***********************************************************************/         
  DECLARE
    ono_   orders.ono%type;
    orders_rec orders%rowtype;

  BEGIN
    -- Get input for the order number.
    ono_ := &ono_;
    SELECT * INTO orders_rec FROM orders WHERE ono = ono_;
    DBMS_OUTPUT.PUT_LINE('ono: ' || ono_);
    DBMS_OUTPUT.PUT_LINE('cno: ' || orders_rec.cno);
    DBMS_OUTPUT.PUT_LINE('eno: ' || orders_rec.eno);
    DBMS_OUTPUT.PUT_LINE('received: ' || orders_rec.received);
    DBMS_OUTPUT.PUT_LINE('shipped: ' || orders_rec.shipped);
EXCEPTION
   WHEN OTHERS THEN
      dbms_output.put_line('Error occurred: '|| ': ' || SQLERRM );
  END;
/
show errors
