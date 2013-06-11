/* simpmath.c */
/* This is a very simple example of a C program that reads in two numbers (integers)  */
/* adds them together and then prints the result.  by Garrett Milster 
9/25/09                 */

/*standard includes */
#include <stdlib.h>
#include <stdio.h>

int main(void) {
  int firstNum, secondNum;
  int sum, product, remainder;

  printf("This program does some simple math\n");
  printf("Please input one number:");
  scanf("%d", &firstNum);
  printf("Please input another number:");
  scanf("%d", &secondNum);

  sum = firstNum + secondNum;
  product = firstNum * secondNum;
  remainder = firstNum - secondNum;
 
  printf("The sum of %d and %d is: %d\n", firstNum, secondNum, sum);
  printf("The product of %d and %d is %d\n", firstNum, secondNum, 
  product);
  printf("the remainder of %d  and %d is %d\n", firstNum, secondNum, 
  remainder);

  exit(0);
}
  
