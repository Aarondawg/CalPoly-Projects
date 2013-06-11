#include <stdlib.h>
#include <stdio.h>

int main(void)
{

float remaining;
float traveled;
float distance;
float velocity;
int time, timeMax;

  printf("Prototype Moon Landing Program\n");
  printf("Garrett Milster & Clark Duvall\n");

  printf("Enter distance to the Moon (miles):");
  scanf("%f", &distance);

    if (0.0 <= distance && distance <= 253560.00) {
    } else {
    printf("Input Error: Incorrect distance: %f\n", distance);
    printf("Enter the distance to the Moon (miles):");
    scanf("%f", &distance);
    }

    if (0.0 <= distance && distance <= 253560.00) {
    } else {
    distance = 253560.00;
    printf("Input Error: Incorrect distance, Valid value %f assigned!\n", distance);
    }

  printf("Enter currect velocity (miles/sec):");
  scanf("%f", &velocity);

    if (7.0 <= velocity && velocity <= 385.9375) {
    } else {
    printf("Input Error: Incorrect velocity: %.4f\n", velocity);
    printf("Enter the current velocity (miles/sec):");
    scanf("%f", &velocity);
    }

    if (0.0 <= velocity && velocity <= 385.9375) {
    } else {
    velocity = 385.9375;
    printf("Input Error: Incorrect velocity, Valid value %.4f assigned!\n", velocity);
    }

  printf("Enter the time in flight (mins):");
  scanf("%d", &time);

    timeMax = distance / velocity;

    if (0.0 <= time && time <= timeMax) {
    } else {
    printf("Input Error: Incorrect time in flight: %d\n", time);
    printf("Ente the time in flight (mins):");
    scanf("%d", &time);
    }

    if (0.0 <= time && time <= timeMax) {
    } else {
    time = 0;
    printf("Input Error: Incorrect time in flight, Valid value %d assigned!\n", time);

    }

  traveled = velocity * time *60;
  remaining = distance - traveled;
 
  printf("The spaceship has traveled: %.2f\n", traveled);
  printf("%.2f miles left to the Moon\n", remaining);
  printf("Good Luck\n");  
  

  exit(0);
}
