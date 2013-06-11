#include <stdio.h>
#include <stdlib.h>

int main ( int argc, char *argv[])
{

    if ( argc != 3) 
    {
        printf("Incorrect number of parameters\n");
        printf("Input should be in the form of \"%s filename.txt search-word\n", argv[0]); 
    }
    else 
    {   
        FILE *file = fopen(argv[1], "r");
        FILE *file2 = fopen(argv[1], "r");
        int maxcharcount = 0;
        int linecounter = 0;
        char string[100];
        
        char* maxstring;
        if(file == 0 )
        {
            printf( "Could not open file\n" );
        }
        else 
        {
            
            while((fgets(string, 101, file)) != NULL)
            {
                int index = 0;
                while (string[index] != '\0')
                   index++;

                if(index > maxcharcount)
                {
                     maxcharcount = index;
                     maxstring = string;
                     
                }
                printf("STRING: %s \n", maxstring);
                linecounter++;
                
            }
          /*  
            charptr = (char**)malloc(sizeof(char*) * linecounter);

            while((fgets(charptr[i], 101, file2)) != NULL)
            {
                //charptr[i] = string;
                i++;
            }
            // TO DO: FIGURE OUT STRING POINTER TO CHARPTR
            // MALLOC CHARPTR[i]*

            int count = 0;
            while(count < linecounter) 
            {
               printf("%s", charptr[count]);
               count++;
            }
*/
         fclose(file);
        }
                      
      printf("NUMBER OF CHARACTERS: %d\n", maxcharcount);
      printf("NUMBER OF LINES: %d\n", linecounter);
      printf("LONGEST STRING: %s\n", maxstring);
       
    }
}

