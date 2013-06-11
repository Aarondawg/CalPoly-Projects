/* Garrett Milster */
/* December 4 2009 */
/* A program for computer the cost of a trip */

#include <stdlib.h>
#include <stdio.h>

double Fuel(double fuel, int distance){
  double consump;

  consump = distance / fuel;

    return(consump);
}

double Cost(double consump, double cost){
   double overall;

   overall = consump * cost;

     return(overall);
}


int main(void){
  double fuel, cost, consump, overall;
  int distance;

  printf("Enter the fuel efficiency of the car: ");
  scanf("%lf", &fuel);
  printf("Enter the distance to be traveled: ");
  scanf("%d", &distance);
  printf("Enter the cost of gasoline: ");
  scanf("%lf", &cost);

     consump = Fuel(fuel, distance);
   
     overall = Cost(consump, cost);

  printf("the trip will cost %g dollars for gasoline.\n", overall);
 
  return(0);

}  
