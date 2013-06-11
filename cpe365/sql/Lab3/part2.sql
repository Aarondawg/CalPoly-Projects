-- Script for lab3, part 2 
-- Run this to generate part2.lst for your hand-in
-- M. Liu, Winter 2012
--------------------------------------------
SET PAUSE OFF
SET AUTOCOMMIT OFF
@dropAirline
@createAirline
@showConstraints
SET ECHO OFF
SET FEEDBACK OFF
@insertFlight
@insertAircraft
@insertEmployee
@insertCertified
SET ECHO ON
SET FEEDBACK ON
@listTables
@insertBad
@listTables
SET PAUSE ON
-----------------------------------------
