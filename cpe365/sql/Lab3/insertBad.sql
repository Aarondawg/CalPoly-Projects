---------------------------------------------------------------
-- Airline Database: Insert tuples that will cause integrity 
-- constraint violations.
-- created by M. Liu,  Fall 2011
---------------------------------------------------------------
insert into flight values(99,'LAX','DCA',2308,
to_date( '2005/04/12 09:30am','YY/MM/DD hh:miam'),
to_date('2005/04/12 09:40pm','YY/MM/DD hh:miam'),235.98);



insert into flight values(-1,'LAX','DCA',2308,
to_date( '2005/04/12 09:30am','YY/MM/DD hh:miam'),
to_date('2005/04/12 09:40pm','YY/MM/DD hh:miam'),235.98);

insert into flight values(9001,'LAX','DCA',2308,
to_date( '2005/04/12 09:30am','YY/MM/DD hh:miam'),
to_date('2005/04/12 09:40pm','YY/MM/DD hh:miam'),235.98);

insert into flight values(1234,'LAXX','DCA',2308,
to_date( '2005/04/12 09:30am','YY/MM/DD hh:miam'),
to_date('2005/04/12 09:40pm','YY/MM/DD hh:miam'),235.98);

insert into flight values(1234,'LAX','DCAX',2308,
to_date( '2005/04/12 09:30am','YY/MM/DD hh:miam'),
to_date('2005/04/12 09:40pm','YY/MM/DD hh:miam'),235.98);

insert into flight values(1234, null,'DCA' ,2308,
to_date( '2005/04/12 09:30am','YY/MM/DD hh:miam'),
to_date('2005/04/12 09:40pm','YY/MM/DD hh:miam'),235.98);

insert into flight values(1234, 'LAX', null ,2308,
to_date( '2005/04/12 09:30am','YY/MM/DD hh:miam'),
to_date('2005/04/12 09:40pm','YY/MM/DD hh:miam'),235.98);

insert into flight values(1234,'LAX','DCA',-1000,
to_date( '2005/04/12 09:30am','YY/MM/DD hh:miam'),
to_date('2005/04/12 09:40pm','YY/MM/DD hh:miam'),235.98);

insert into flight values(1234,'LAX','DCA',2308,
null,
to_date('2005/04/12 09:40pm','YY/MM/DD hh:miam'),235.98);

insert into flight values(1234,'LAX','DCA',2308,
to_date( '2005/04/12 09:30am','YY/MM/DD hh:miam'),
null,235.98);

insert into flight values(1234,'LAX','DCA',2308,
to_date( '2005/04/12 09:30am','YY/MM/DD hh:miam'),
to_date('2005/04/12 09:40pm','YY/MM/DD hh:miam'),-2000);

insert into flight values(1234,'LAX','DCA',2308,
to_date( '2005/04/12 09:30am','YY/MM/DD hh:miam'),
to_date('2005/04/12 09:40pm','YY/MM/DD hh:miam'), 12345);

insert into aircraft values(1,'Boeing 747-400',8430);

insert into aircraft values(-1,'Boeing 747-400',8430);

insert into aircraft values(101,'Boeing 747-400',8430);

insert into aircraft values(100,'0123456789012345678901234567890',8430);

insert into aircraft values(100, null,8430);

insert into aircraft values(100,'Boeing 747-400',-1);

insert into aircraft values(100,'Boeing 747-400',1234567);

insert into employee values(242518965,'James Wong',1204330, null);

insert into employee values(-1,'James Wong',1204330, null);

insert into employee values(1234567890,'James Wong',1204330, null);

insert into employee values(123456789,'012345678901234567890',1204330, null);

insert into employee values(123456789,null, 1204330, null);

insert into employee values(123456789,'James Wong',-1, null);

insert into employee values(123456789,'James Wong',123456789.00, null);

insert into employee values(123456789,'James Wong',1204330, 987654321);

insert into certified values(567354612,1);

insert into certified values(123456789, 1);

insert into certified values(567354612,123);

