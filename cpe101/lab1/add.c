/* This is a very simple example of a C program that reads in two numbers (integers)  */
/* adds them together and then prints the result.  by Zoe Wood 1/6/08                 */

/*standard includes */
#include <stdlib.h>
#include <stdio.h>

int main(void) {
  int firstNum, secondNum;
  int sum;

  printf("Please input one number:");
  scanf("%d", &firstNum);
  printf("Please input another number:");
  scanf("%d", &secondNum);
  sum = firstNum + secondNum;
  printf("The sum of %d and %d is: %d\n", firstNum, secondNum, sum);

  exit(0);
}
  
