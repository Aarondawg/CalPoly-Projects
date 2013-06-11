/* Garrett Milster
 *
 * program that takes stdin, and prevents duplicate lines from being entered.
 * Input can also be redirected from a file.
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/* this base length will serve as a base for the rest of the
 * program */
#define base_length 50
char *read_long_line(FILE *file);

int main(int argc, char *argv[])
{
   char *previous;
   char *current;
   char *temp;   
   if(stdin == 0)
   {
      printf("File could not be opened\n");
         return -1;
   }
   else
   {
      previous = read_long_line(stdin);
   }
   printf("%s", previous);
   
   /* Loops through comparing the previous line with
    * the current line, printing accordingly */
   while((current = read_long_line(stdin)) != NULL)
   {
      if(strcmp(current, previous) == 0)
      {
      }
      else
      {
         printf("%s", current);

      }
      /* makes current into previous and frees the allocated memory */
      temp = previous;
      previous = current;
      free(temp);
      
   }
   free(previous);
   return 0;
}

char *read_long_line(FILE *file)
{
   char string[base_length];
   char *final_string = calloc(base_length,sizeof(char) * base_length);
   int current = base_length;
   /* reads in 50 characters, checks for null ending, if there is,
    * concats it to final string and returns, otherwise it allocates
    * more memory for final string and goes back into the loop */
   while((fgets(string,base_length, file)) != NULL)
   {
      int i = 0;
      for(i = 0; i < base_length - 1; i++)
      {
         if(string[i] == '\0')
         {
            final_string = strcat(final_string, string);
            return final_string;
         }
      } 
      final_string = strcat(final_string, string);
      current = current + base_length;
      final_string = realloc(final_string, base_length);
   }
   
   return NULL;
   
}