/* Garrett Milster */
/* October 21 2009 */
/* A  Moonlander Program where the objective is to land somewhat safely on the moon*/

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int FuelValue(int Fuel){

  printf("Enter the initial amount of fuel on board the LM (in liters): ");
  scanf("%d", &Fuel);

	while (Fuel <= 0) {
	    printf("Retry: ");
 	    scanf("%d", &Fuel);
	}

  return(Fuel);
}

double Altitude(double altitude){
  
 printf("Enter the initial altitude of the LM (in meters): ");
  scanf("%lf", &altitude);

        while (altitude < 1 || altitude > 9999) {
            printf("Retry: ");
            scanf("%lf", &altitude);
        }

  return(altitude);
}

void DisplayLM(int Time, double altitude, double velocity, int FuelLevel, int thrust){

  printf("Elapsed Time:     %d s\n", Time);
  printf("        Fuel:     %d l\n", FuelLevel);
  printf("        Rate:     %d l/s\n", thrust);
  printf("    Altitude:     %2g m\n", altitude);
  printf("    Velocity:     %2g m/s\n", velocity);
 
  printf("\n");
}   

int Thrust(int thrust, int fuel){

 printf("Enter fuel rate(0-9, 0=freefall, 5=constant velocity, 9=max thrust): ");
 scanf("%d", &thrust);

        while (thrust < 0 || thrust > 9) {
            printf("Error: Fuel rate must be between 0 and 9, inclusive\n ");
 	    printf("\n");
	    printf("Enter fuel rate(0-9, 0=freefall, 5=constant velocity, 9=max thrust): ");
            scanf("%d", &thrust);

        }

    if (thrust < fuel){
	return(thrust);
    }else{
	return(fuel);
    }
}

double Accel(double thrust){
  double acceleration;

  	 /* printf("thrust: %f\n", thrust);*/

   acceleration =  1.62 * ((thrust/5) - 1);

   	/* printf("Accel = %f\n", acceleration); */

	return(acceleration);
}

double CurrentAlt(double altitude, double velocity,double acceleration){
  double CurrentAltitude;

	   /*printf("Altitude: %f\n", altitude);
	     printf("Velocity: %f\n", velocity );
	     printf("Acceleration: %f\n", acceleration);
	   */

    CurrentAltitude = altitude + velocity + (acceleration/2.0);

	   /* printf("Current Altitude:  %f\n", CurrentAltitude); */

	return(CurrentAltitude);
}

double CurrentFuel(int Fuel, int thrust){
  double CF;

   CF = Fuel - thrust;
	
	return(CF);
}

double Velocity(double velocity1, double acceleration){
  double velocity;  

 	/*  printf("Velocity: %f\n", velocity1);
  	    printf("Accel: %f\n", acceleration); */

  velocity = velocity1 + acceleration;

	/*   printf("Final Velocity: %f\n", velocity); */

	return(velocity);
}

int main(void){
   double altitude, velocity, accel;
   int fuel, thrust, CF, time;

	altitude = 0;
	fuel = 0;	
	

  printf("\nWelcome aboard the Lunar Module Flight Simulator\n\n");
  printf("  To begin you must specify the LM's initial altitude\n");  
  printf("  and fuel level.  To simulate the actual LM use\n"); 
  printf("  values of 1300 meters and 500 liters, respectively.\n");
  printf("\n"); 

  printf("Good luck and may the force be with you!\n");
  printf("\n");

	altitude = Altitude(altitude);
	fuel = FuelValue(fuel);
        thrust = 0;
        velocity = 0;
 	CF = fuel;
	time = 0;

	printf("LM state at retrorocket cutoff\n");

	DisplayLM(time, altitude, velocity, CF, thrust);
	
		while(altitude > 0){
		
		    if(CF > 0){
			
			thrust = Thrust(thrust, CF);
			accel = Accel(thrust);
			altitude = CurrentAlt(altitude, velocity, accel);
			velocity = Velocity(velocity, accel);
			CF = CF - thrust;
			time = time + 1;

				 if(altitude <= 0){
                                        DisplayLM(time, 0, velocity, CF, thrust);
                                }else{
                                      	 DisplayLM(time, altitude, velocity, CF, thrust);
                                }
  
		    }else{
			
			thrust = 0;
                        accel = Accel(thrust);
                        altitude = CurrentAlt(altitude, velocity, accel);
                        velocity = Velocity(velocity, accel);
                        CF = CF - thrust;
                        time = time + 1;
		    
				if(altitude <= 0){
					DisplayLM(time, 0, velocity, CF, thrust);
				}else{
					DisplayLM(time, altitude, velocity, CF, thrust); 				
				}
		   }

		}


	if(velocity < 0 && velocity > -1){

		printf("Status at landing - The eagle has landed!\n");

	}else if(velocity > -10 && velocity < -1){

		printf("Status at landing - Enjoy your oxygen while it lasts!\n");
	}else{
		printf("Status at landing - Ouch - that hurt!\n");
	}




 exit(0);
}

