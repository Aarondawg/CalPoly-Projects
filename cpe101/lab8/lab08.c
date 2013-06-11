/*William Starr, Garrett Milster
 * Proffessor Wood
 * 12/4/09
 */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#define MAXS 80

typedef struct{
	char animal[MAXS];
	int number;
} zoo;

void printA(zoo animals[],int size){
	int i;
	for(i=0; i<size; i++){
		printf("animal %d:\n", i);
		printf("        type: %s\n", animals[i].animal);
		printf("        population: %d\n", animals[i].number);
	}
}

int cpop(zoo animals[], int nanimals){
	int i, num;
	int a;
	char inAnimal[MAXS];
	a = 0;

          printf("Enter the type of animal: ");
          scanf("%s", inAnimal);
	  printf("Enter the change in the population as");
	  printf(" a numeric value (e.g. 5 or -5): ");
	  scanf("%d", &num);
	  for(i=0; i<nanimals; i++){
		 if((strcmp(inAnimal, animals[i].animal))==0){
                      	animals[i].number+=num;
			a=i;
                 } 		
          }	

		if(nanimals == nn + 1){
                       printf("Inserating new animal %s\n", inAnimal);
		       animals[nanimals].number = num;
		       strcpy(animals[nanimals].animal, inAnimal);    
		if(a == 0){
                       printf("Inserting new animal %s\n", inAnimal);
		       animals[nanimals +1].number = num;
		       strcpy(animals[nanimals +1].animal, inAnimal); 
                nanimals = nanimals + 1;	
		}
		 
          if(animals[a].number<=0){
		  printf("All of the animals of type %s have died\n", animals[a].animal);
		  printf("Removing them from the zoo database\n");
	  	  for (i = a + 1; i < nanimals; i++){
			animals[a].number = animals[i].number;
		        strcpy(animals[a].animal, animals[i].animal);	
                        
                  }
            nanimals=nanimals-1; 
	  }

     return nanimals;

 }	  

int main(void){
    zoo animals[MAXS];
    int i=0;
    int choice,nn;
    int nanimals;
    FILE *fp;
    fp=fopen("animal.data", "r");
    if(fp==NULL){
    printf("Invalid File existing\n");
      exit(0);
    }
    
    while((fscanf(fp, "%s %d", animals[i].animal, &animals[i].number))==2){
	    i++;
    }
    nanimals = i;
    choice=0;
    while(choice==1 || choice==0){

	    printA(animals, nanimals);


    	printf("To update the zoo database, enter 1, otherwise to exit enter 0: ");
    	scanf("%d", &choice);
	  
	    if(choice==0){
		    exit(0);
             } else if(choice ==1){
		nn = nanimals;
              nanimals= cpop(animals, nanimals);
                    
	     } else {
		  choice=0;
		  printf("Invalid choice\n");
	     }
              	     
     }
    
	return(0);



}
