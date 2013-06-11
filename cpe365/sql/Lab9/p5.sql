-- Lab 9 | Part 5
-- Name: Garrett Milster
-- Partner: Ross McKelvie
CREATE OR REPLACE TRIGGER reorderWatcher
BEFORE INSERT OR UPDATE ON parts
FOR EACH ROW
BEGIN
   IF (:new.qoh <= :new.olevel) THEN
      dbms_output.put_line('!!!!!' || to_char(sysdate, 'MON-DD-YY, HH:MI:SS') || '-- ALERT: part ' || :new.pno || ' needs to be reordered');
   END IF;
END;
/
show errors;