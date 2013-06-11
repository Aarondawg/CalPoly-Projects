#include <stdio.h>
#include <stdlib.h>
double Fahren2Cel(double F) {
  return ((F-32.0)*(5.0/9.0));
}
void PrintF2C(int end) {
  int i;
  for(i=0; i <= end; i++) {
    printf("%f Fahrenheit is ", (double)i);
    printf("%f Celsius\n", Fahren2Cel((double)i));
  }
}
int main(void) {
  int choice;
  int end;
  double F;
  printf("To convert one value, enter 1. “);
  printf(“To print a list of conversions, enter 2: ");
  scanf("%d", &choice);
  if (choice == 1) {
    printf("Enter temperature in Fahrenheit: ");
    scanf("%lf", &F);
    printf("%f Fahrenheit is %f Celsius\n", F, Fahren2Cel(F));
  } else if (choice == 2) {
    printf("Enter the maximum value of conversion list: ");
    scanf("%d", &end);
    PrintF2C(end);
  } else {
    printf("Invalid entry.\n");
  }
  return (0);
}

