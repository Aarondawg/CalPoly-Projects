#include<string.h>
#include<stdlib.h>
#include<stdio.h>
int main(int argc, char *argv[]) {

	char *s;

	s = strdup("Tryme"); /* will call malloc() implicitly */

	puts(s);
	
	free(s);
	char* t = calloc(5,4);
	printf("CALLOC'ing 20\n"); 
	free(t); 
	return 0;

}
