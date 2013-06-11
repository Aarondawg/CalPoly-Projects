/*Garrett Milster*/
/*October 7, 2009*/
/*A program for	determining which Firetruck is closest to the emergency	point*/

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

double EuclidDistance2D(double x1,double y1,double x2, double y2) 
{   
  return(sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)));
}

double ManDistance(double x1, double y1,double x2,double y2) {
        return(fabs(x1-x2) + fabs(y1-y2));

        }

/* compare two numbers – necessary to compare floating point numbers */
void compareNumber(double a, double b){
	 double EPSILON;
	 EPSILON=1e-8;
	 if (fabs(a-b)< EPSILON) {
                printf("comparison succeeded\n");
        } else {
                printf("comparison failed!\n");
 	}
}

int main(void) {
double x1, x2, x3, y1, y2, y3;
double distance1, distance2, distanceA, distanceB;	

	/*compareNumber(EuclidDistance2D(0, 0, 3, 4), 5.0 ); 
	*compareNumber(EuclidDistance2D (1, 2, 4, 6), 5.0 );
	*compareNumber(EuclidDistance2D (3,4,4, 5), sqrt(2.0) ); 
	
	*compareNumber(ManDistance(3,6,1,7), 3);
	*compareNumber(ManDistance(1,7,3,6), 3);
	*compareNumber(ManDistance(1,1,2,3), 3);*/
  
  printf("Welcome to the emergency response assistance tool.\n");
  printf("Please enter the location of the emergency as a 2D coordinate.\n");
  printf("Enter the X value of the 2D coordinate: ");
  scanf("%lf", &x1);
  printf("Enter the Y value of the 2D coordinate: ");
  scanf("%lf", &y1); 
  printf("Please enter the location of the first firetruck as a 2D coordinate.\n");
  printf("Enter the X value of the 2D coordinate: ");
  scanf("%lf", &x2); 
  printf("Enter the Y value of the 2D coordinate: ");
  scanf("%lf", &y2); 
  printf("Please enter the location of the second firetruck as a 2D coordinate.\n");
  printf("Enter the X value of the 2D coordinate: ");
  scanf("%lf", &x3); 
  printf("Enter the Y value of the 2D coordinate: ");
  scanf("%lf", &y3); 

 distance1 = EuclidDistance2D(x1, y1, x2, y2);
 distance2 = EuclidDistance2D(x1, y1, x3, y3);
 distanceA = ManDistance(x1, y1, x2, y2);
 distanceB = ManDistance(x1, y1, x3, y3);


  printf("The first firetruck is %.0f blocks (or %f units) away from the emergency.\n", 
distanceA,distance1);
  printf("The second firetruck is %.0f blocks (or %f units) away from the emergency.\n", 
distanceB,distance2);



  if (distanceA < distanceB) {
	printf("Please contact the first truck.\n");
  }else if (distanceB < distanceA) {
	printf("Please contact the second truck.\n");
  }else{
	printf("Please contact the first truck.\n");
  }

exit(0);
}
