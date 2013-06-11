-- Name: Garrett Milster
-- Partner: Ross McKelvie

-- 12 What is the percentage of the players on the team whose name is "Lakers" that went to college over all players on that team?
SELECT
(
   SELECT count(*)
   FROM player
   WHERE tname = 'Lakers' AND college IS NOT NULL
) /
(
   SELECT count(*)
   FROM player
   WHERE tname = 'Lakers'
) * 100
AS PERCENT
FROM dual;

-- 13 What is the average of points scored by the Lakers team, computed as <total points scored by its players>
-- divided by <the number of games played>
-- Output the number under a column heading of average
SELECT
(
   SELECT sum(points)
   FROM plays_in
   WHERE pid IN (
      SELECT pid
      FROM player
      WHERE tname='Lakers'
   )
) /
(
   SELECT count(count(*))
   FROM plays_in
   WHERE tname = 'Lakers'
   GROUP BY gnum
)
AS AVERAGE
FROM dual;

-- 14 How many playesr play more than 10 minutes in at least one game?
SELECT count(*) HOW_MANY
FROM
(
   SELECT pid
   FROM plays_in
   WHERE minutes > 10
   GROUP BY pid
   ORDER BY pid
);

-- 15 List the pname and height of each tallest player(s), in increasing order of name.
-- You must not use any Cartesian product or table join
SELECT pname, height
FROM player
WHERE height = (
   SELECT max(height)
   FROM player
)
ORDER BY pname;

-- 16 List the tname and pname of the highest paid player(s),
-- in increasing order of tname then pname.
-- You may not use any Cartesian product or table join.
SELECT tname, pname
FROM player
WHERE salary =
(
   SELECT max(salary)
   FROM player
)
ORDER BY tname, pname;

-- 17 List the tname and pname of each player who played one or more minutes in every one of the game in the series.
SELECT tname TEAM_NAME, pname PLAYER_NAME
FROM player p
WHERE p.pid IN
(
   SELECT pid
   FROM plays_in
   WHERE MINUTES > 0
   GROUP BY pid
)
ORDER BY tname, pname;

-- 18 Which City hosted the most number of games?
SELECT place
FROM game
GROUP BY place
HAVING count(place) =
(
    SELECT max(count(place))
    FROM game
    GROUP BY place
);

-- 19 List the name of the team that won the series
-- NOTE: the series winner is the team that won the last game played, which should not assumed to be game 7,
-- but should be the game with the higest game number
SELECT tname
FROM plays_in p
WHERE gnum =
(
   SELECT max(gnum) GNUM
   FROM plays_in
)
GROUP BY tname
HAVING count(points) = (
   SELECT max(count(points))
   FROM plays_in
   WHERE gnum = (
      SELECT max(gnum) GNUM
      FROM plays_in
   )
   GROUP BY tname
);