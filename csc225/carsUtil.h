typedef struct
{
   char model[20];
   char color[20];
   int numDoors;
   double mpg;
} Car;

void initCar(Car *c);
void printCar(Car c);
