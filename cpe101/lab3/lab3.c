/* Garrett Milster */
/* October 7, 2009 */
/* This is a program used to determine if two spheres are colliding */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

double Spheredist(float x1, float x2, float y1, float y2, float z1, float z2, double distance)

	{ 
 
	distance = sqrt( (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) + (z2-z1)*(z2-z1) );
	return(distance);
	
	}

int Collisionpoint(double collision, double distance,float rad1, float 
rad2)

{    
  collision = rad1 + rad2;
    
 	if (distance <= collision)
    {
	return(1);
    }else{
	return(0);
    } 
 }   

int main(void) 
{ 
float  x1, x2, y1, y2, z1, z2;
float  rad1, rad2;
double distance=0.0, collision=0.0;
 

  printf("This program will test if two objects are colliding\n");
  printf("Please enter the center of one object \n");
  printf("Object 1 x: ");
  scanf("%f", &x1);
  printf("Object 1 y: ");
  scanf("%f", &y1);
  printf("Object 1 z: ");
  scanf("%f", &z1);
  printf("Object 1 radius: ");
  scanf("%f", &rad1);
  printf("Please enter the center of the second object\n");
  printf("Object 2 x: ");
  scanf("%f", &x2);
  printf("Object 2 y: ");
  scanf("%f", &y2);
  printf("Object 2 z: ");
  scanf("%f", &z2);
  printf("Object 2 radius: ");
  scanf("%f", &rad2);

 distance = Spheredist(x1,x2,y1,y2,z1,z2,distance);
 
	if(Collisionpoint(collision, distance, rad1, rad2)) {
		printf("Objects are colliding!\n");
	}else{
		printf("Objects are not colliding.\n");
	}

  exit(0);
}
