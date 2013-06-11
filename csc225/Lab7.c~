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
        FILE *file = fopen( argv[1], "r" );
        FILE *file2 = fopen( argv[1], "r" );
        int charcount = 0;
        char *charptr;
        int i = 0;
        char *lineptr, *currentlineptr;
        int linecounter = 0, linecharcounter = 0, maxline = 0;
        if ( file == 0 )
        {
            printf( "Could not open file\n" );
        }
        else 
        {
            int x;
            
            while((x = fgetc( file ) ) != EOF )
            {
                charcount++;
            }
            charptr = (char*)malloc(sizeof(char) * charcount);
            lineptr = charptr;
            currentlineptr = charptr;

            while((x = fgetc( file2 ) ) != EOF )
            {
                charptr[i] = x;
                if( x = '\n')
                { 
                  linecounter++;
                  if( linecharcounter > maxline )
                     lineptr = currentlineptr;
                  currentlineptr = &charptr[i+1];
                  maxline = linecharcounter;
                  linecharcounter = 0;
                  
                } 
                else
                { 
                  linecharcounter++;
                }
                printf("%c-", charptr[i++]);
            }
            printf("Maxline: %d\n", maxline);
            printf("linecounter: %d\n", linecounter);
          
         fclose( file );



      
        }
       
    }
}

