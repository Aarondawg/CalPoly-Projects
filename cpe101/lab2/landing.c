#include <stdlib.h>
#include <stdio.h>

int main(void) {

float distance;
float velocity;
int time;

  printf("Prototype Moon Landing Program\n");
  printf("Garrett Milster & Clark Duvall\n");

  printf("Enter distance to the Moon (miles):\n");
  scanf("%f", &distance);

    if (0.0 <= distance && distance <= 253560.00) {
    } else {
    printf("Input Error: Incorrect distance: %f", distance);
    printf("Enter a correct distance to the Moon (miles):\n");
    scanf("%f", &distance);
    }

    if (0.0 <= distance && distance <= 253560.00) {
    } else {
    distance = 253560.00;
    printf("Input Error: Incorrect distance, Valid value %f assigned!", distance);
    }

  printf("Enter currect velocity (miles/sec):\n");
  scanf("%f", &velocity);
  printf("Enter the time in flight (mins):\n");
  scanf("%d", &time);
  
  /* printf("Your distance, velocity, and time in flight are %f, %f, and %d\n", distance, velocity, and time); */

  exit(0);
}
