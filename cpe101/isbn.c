/* Garrett Milster */
/* September 30, 2009 */
/* This is a program designed to compute the the Checksum of an ISBN number */



#include <stdlib.h>
#include <stdio.h>

int main(void)
{

int num1, num2, num3, num4, num5, num6, num7, num8, num9;
int sum;
int remainder;

  printf("ISBN checksum computation program\n");
  printf("Please enter the first digit of the ISBN number:");
  scanf("%d", &num1);
  printf("Please enter the second digit of the ISBN number:");
  scanf("%d", &num2); 
  printf("Please enter the third digit of the ISBN number:");
  scanf("%d", &num3);
  printf("Please enter the fourth digit of the ISBN number:");
  scanf("%d", &num4);
  printf("Please enter the five digit of the ISBN number:");
  scanf("%d", &num5);
  printf("Please enter the six digit of the ISBN number:");
  scanf("%d", &num6);
  printf("Please enter the seven digit of the ISBN number:");
  scanf("%d", &num7);
  printf("Please enter the eight digit of the ISBN number:");
  scanf("%d", &num8);
  printf("Please enter the nine digit of the ISBN number:");
  scanf("%d", &num9);  

sum = (num1 + 2*num2 + 3*num3 + 4*num4 + 5*num5 + 6*num6 + 7*num7 + 8*num8 + 9*num9);
remainder = sum % 11;

/*If remainder > 10 then num10 = X */
/*If remainder < 10 then num10 = remainder */
	
	if(remainder < 10)
	{
		  printf("Your ISBN number is: %d-%d%d%d-%d%d%d%d%d-%d\n", num1, num2, num3, num4, num5, num6, num7, num8, num9,remainder);
	}else{
		  printf("The ISBN number is: %d-%d%d%d-%d%d%d%d%d-x\n", num1, num2, num3, num4, num5, num6, num7, num8, num9);
		
	}


  exit(0);

}
