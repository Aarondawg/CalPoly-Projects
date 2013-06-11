/* Garrett Milster */
/* October 26 2009 */
/* A program for printing out student's grades */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define max 350

void printGrades(char n1[], int g1[]){
  int i;	
    printf("%s's grades: ", n1);
  
    for(i = 0; i < 3; i++){
	printf("%d ", g1[i]);
    }
	printf("\n");
	printf("\n");
}

int main(void){

  char name1[max];
  char name2[max];
  char name3[max];

  char Filename[max];
  FILE *fp;
  char search[max];

  int grades1[3];
  int grades2[3];
  int grades3[3];
  int repeat;

    printf("Enter the Filename: ");
    scanf("%s", Filename);
    printf("\n");
    printf("Opening a file named %s\n", Filename);
    printf("\n");

    fp = fopen(Filename, "r");

fscanf(fp,"%s %d %d %d\n", name1, &grades1[0], &grades1[1], &grades1[2]);
fscanf(fp,"%s %d %d %d\n", name2, &grades2[0], &grades2[1], &grades2[2]);
fscanf(fp,"%s %d %d %d\n", name3, &grades3[0], &grades3[1], &grades3[2]);

repeat = 1;

while(repeat == 1){
  printf("Search for which student?: ");
  scanf("%s", search);
  printf("\n");

	if(strcmp(search, name1) == 0){
		printGrades(name1, grades1);
	}else if(strcmp(search, name2) == 0){
		printGrades(name2, grades2);
	}else if(strcmp(search, name3) == 0){
		printGrades(name3, grades3);
	}else{
		printf("Name not found!\n");
		printf("\n");
	}

  printf("Would you like to print another grade? 1=yes, 0=no: ");
  scanf("%d", &repeat);
  printf("\n");
}

exit(0);
}









