#include <stdio.h>
#include <stdlib.h>
#include "carsUtil.h"

void printCar(Car c);

int main (void)
{
   int numCars, i;
   Car *cars;

   printf("How many cars do you own? ");
   scanf("%d", &numCars);

   cars = (Car*) malloc(sizeof(Car)*numCars);

   for (i=0; i<numCars; i++)
   {
      initCar(cars++);
   }

   for (i=0; i<numCars; i++)
   {
      printCar(cars[i]);
   }

   return 0;
}
