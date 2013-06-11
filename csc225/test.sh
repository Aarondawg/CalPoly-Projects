cp ~jworkman/225Final/P1/*.out .
cp ~jworkman/225Final/P1/*.txt .

echo -----------------------------------------------------
echo Evaluating test case 1: too few args...
./a.out > myt1.out
diff t1.out myt1.out

echo -----------------------------------------------------
echo Evaluating test case 2: story1.txt...
./a.out story1.txt > myt2.out
diff t2.out myt2.out

echo -----------------------------------------------------
echo Evaluating test case 3: story2.txt...
./a.out story2.txt > myt3.out
diff t3.out myt3.out

echo -----------------------------------------------------
echo Evaluating test case 4: story3.txt non-existent...
./a.out story3.txt > myt4.out
diff t4.out myt4.out

echo -----------------------------------------------------
echo Evaluating test case 5: too many args...
./a.out blah blah > myt5.out
diff t5.out myt5.out

echo -----------------------------------------------------
