
/* Garrett Milster */
/* November 10 2009 */
/* A Program that calculates planet data */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

typedef	struct{

char pName[80];
double diameter;
double Edist;
int numS;

} planetData;

void PrintData(planetData pList, int i){


	printf("Planet %d:\n", i);
	printf("         name: %s\n", pList.pName);
	printf("         diameter: %g km.\n",pList.diameter);
	printf("         distance to Earth:%g km.\n", pList.Edist);
	printf("         Number of moons: %d\n", pList.numS);

}

int Sort(int start, planetData pList[]){
  int i, index;

index = start;
  for(i = start; i < 8; i++){
    if((pList[i].Edist) < (pList[index].Edist)){
	index = i;
    }
  }
 return(index);
}


void Swap(int index1, int index2, planetData pList[]){
  planetData temp;

  temp = pList[index1];
  pList[index1] = pList[index2];
  pList[index2] = temp;
}	



int main(void){
  planetData pList[9];
  int i, count, index;
  FILE *fp;

  i = 0;
  count = 0;

  fp = fopen("planet.data", "r");
  if (fp == NULL) {
    printf("Invalid file exiting\n");
     exit(0);
  }

while ( fscanf(fp, "%s %lf %lf %d\n", pList[i].pName, &pList[i].diameter, &pList[i].Edist, &pList[i].numS) == 4){
i++;
  }
 printf("---UNSORTED---\n");
  for(i = 0; i < 8; i++){
	PrintData(pList[i], count);
    count = count + 1;
  }

  

 for(i = 0; i < 8; i++){
   index = Sort(i, pList);
   
   Swap(index, i, pList);
 }

 printf("\n");
 printf("---SORTED---\n");

 count = 0;
 for(i = 0; i < 8; i++){
	PrintData(pList[i], count);
    count = count + 1;
  }




exit(0);
}
