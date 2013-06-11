/*Garrett Milster*/
/*October 7, 2009*/
/*A program for	determining which Firetruck is closest to the emergency	point*/

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

float EuclidDistance2D(float x1, float y1, float x2, float y2) 
{   
  return(sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)));
}

float ManDistance(float x1, float y1, float x2, float y2) {
        return(fabs(x1-x2) + fabs(y1-y2));

        }

/* compare two numbers – necessary to compare floating point numbers */
void compareNumber(float a, float b){
	 float EPSILON;
	 EPSILON=1e-8;
	 if (fabs(a-b)< EPSILON) {
                printf("comparison succeeded\n");
        } else {
                printf("comparison failed!\n");
 	}
}

int main(void) {
	

	/*compareNumber(EuclidDistance2D(0, 0, 3, 4), 5.0 ); 
	*compareNumber(EuclidDistance2D (1, 2, 4, 6), 5.0 );
	*compareNumber(EuclidDistance2D (3,4,4, 5), sqrt(2.0) ); 
	
	*compareNumber(ManDistance(3,6,1,7), 3);
	*compareNumber(ManDistance(1,7,3,6), 3);
	*compareNumber(ManDistance(1,1,2,3), 3);*/

exit(0);
}
