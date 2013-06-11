---------------------------------------------------------------
-- NBA 2010 final series Database; Create Tables Script
--  M. Liu, Winter 2011
---------------------------------------------------------------
-- First, drop all existing tables, if any.
start drop_nba;

create table TEAM (
  tname      varchar2(10),
  conference char(1) NOT NULL,
  city    varchar2(15),
  primary key (tname)
);


create table TEAM_COLOR (
  tname,
  color   varchar2(12),
  primary key (tname, color),
  foreign key (tname) references TEAM(tname)
);

create table PLAYER (
  tname,
  pid      numeric(3) check (pid > 0 and pid <= 100),
  pname    varchar2(20),
  position varchar2(3),
  bdate    date,
  height   numeric(3) check (height > 0), 
  weight   numeric(3) check (weight > 0),
  college  varchar2(20),
  salary   numeric(12,2) check (salary > 0),
  primary key (tname, pid),
  foreign key (tname) references TEAM(tname)
);


create table CAPTAIN (
  tname,
  pid,
  foreign key (tname, pid) references PLAYER(tname, pid),
  -- foreign key (tname) references TEAM(tname),
  primary key (tname, pid) 
);

create table GAME(
  gnum      numeric(1) check (gnum > 0),
  dateTime  date,
  place varchar2(15),
  primary key (gnum)
);

create table PLAYS_IN(
  gnum,
  tname,
  pid, 
  minutes  numeric(3) check (minutes >= 0),
  points   numeric(3) check (points >= 0),
  starter char(1), check(starter='Y' or starter = 'N'),
  primary key (gnum, tname, pid),
  foreign key (gnum) references GAME,
  foreign key (tname, pid) references PLAYER (tname, pid)
);

 
