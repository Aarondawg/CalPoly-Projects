/* Garrett Milster */
/* October 15, 2009 */
/* A program that determines the perimeter and area of a rectangle given it's width and length */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

double Area(double width,double length)
 {
  return(length * width); 
 }

double Perimeter(double width, double length)
 {
  return((2*length) + (2*width));
 }


void compareNumber(double a,double b)
 {
      double EPSILON;
      EPSILON=1e-8;
        
	 if (fabs(a-b)< EPSILON)
        {
            printf("comparison succeeded\n");
        } else {
            printf("comparison failed!\n");
        }
 }


int main (void) {

double width, length;
double area, perimeter;

 /* compareNumber(Area(2,2), 4);
    compareNumber(Area(3,3), 9);
    compareNumber(Area(4,4), 16);
    compareNumber(Perimeter(2,2), 8);
    compareNumber(Perimeter(3,3), 12);
    compareNumber(Perimeter(4,4), 16);
 */
  
  printf("Please enter the width of the rectangle: ");
  scanf("%lf", &width);
  printf("Please enter the length of the rectangle: ");
  scanf("%lf", &length);
 

	area = Area(width, length);
	perimeter = Perimeter(width, length);

  printf("Area of rectangle: %.2f\n", area);
  printf("Perimeter of rectangle: %.2f\n", perimeter); 

exit(0);

}
