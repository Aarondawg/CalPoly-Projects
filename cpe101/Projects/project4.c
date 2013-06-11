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
  printf("    Altitude:     %.2f m\n", altitude);
  printf("    Velocity:     %.2f m/s\n", velocity);

}   

int Thrust(int thrust, int fuel){

 printf("Enter fuel rate(0-9, 0=freefall, 5=constant velocity, 9=max thrust): ");
 scanf("%d", &thrust);

        while (thrust < 0 || thrust > 9) {
            printf("Error: Fuel rate must be between 0 and 9, inclusive ");
            scanf("%d", &thrust);
 	    printf("\n\n");
        }

    if (thrust < fuel){
	return(thrust);
    }else{
	return(fuel);
    }
}

double Accel(int thrust){
  double acceleration;

   acceleration =  1.62 * ((thrust/5) - 1);

	return(acceleration);
}

double CurrentAlt(double altitude, double velocity,double acceleration){
  double CurrentAltitude;

    CurrentAltitude = altitude + velocity + (acceleration/2);

	return(CurrentAltitude);
}

double CurrentFuel(int Fuel, int thrust){
  double CF;

   CF = Fuel - thrust;
	
	return(CF);
}

double Velocity(double velocity1, double acceleration){
  double velocity;  
  
  velocity = velocity1 + acceleration;

	return(velocity);
}

int main(void){
   double altitude, velocity, accel;
   int fuel, thrust, CF, time;


  printf("Welcome aboard the Lunar Module Flight Simulator\n\n");
  printf("  To begin you must specify the LM's initial altitude\n");  
  printf("  and fuel level.  To simulate the actual LM use\n"); 
  printf("  values of 1300 meters and 500 liters,respectively.\n");
  printf("\n"); 

  printf("Good luck and may the force be with you!\n");
  printf("\n");

	altitude = Altitude(altitude);
	fuel = FuelValue(fuel);
        thrust = 0;
        velocity = 0;
 	CF = fuel;
	time = 0;

	DisplayLM(time, altitude, velocity, CF, thrust);
	

  printf("LM state at retrorocket cutoff\n");

		while(altitude > 0){
			thrust = Thrust(thrust, CF);
			accel = Accel(thrust);
			velocity = Velocity(velocity, accel);
			altitude = CurrentAlt(altitude, velocity, accel);
			CF = CF - thrust;
			time = time + 1;
	
				DisplayLM(time, altitude, velocity, CF, thrust);			
		}







 exit(0);
}

