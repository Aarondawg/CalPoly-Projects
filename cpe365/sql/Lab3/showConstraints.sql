-- These commands list the constraints created for the Airline database
-- M. Liu, Fall 2011

select constraint_name, constraint_type, search_condition 
from user_constraints
where table_name = 'FLIGHT';

select constraint_name, constraint_type, search_condition 
from user_constraints
where table_name = 'AIRCRAFT';

select constraint_name, constraint_type, search_condition 
from user_constraints
where table_name = 'EMPLOYEE';

select constraint_name,  constraint_type, search_condition 
from user_constraints
where table_name = 'CERTIFIED';




