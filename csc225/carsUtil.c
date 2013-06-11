#include "carsUtil.h"
#include <stdio.h>

void initCar(Car *c)
{
   printf("Model: ");
   scanf("%s", c->model);
   printf("Color: ");
   scanf("%s", c->color);
   printf("Num Doors: ");
   scanf("%d", &c->numDoors);
   printf("MPG: ");
   scanf("%lf", &c->mpg);
}

void printCar(Car c)
{
   printf("Model: %s\n", c.model);
   printf("Color: %s\n", c.color);
   printf("Doors: %d\n", c.numDoors);
   printf("MPG:   %.2f\n", c.mpg);
}
