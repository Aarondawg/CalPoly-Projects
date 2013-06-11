/* Garrett Milster
*
* This program has two capabilities: translating and delete characters 
* from user input. The user can replace all characters in an inputted 
* set with another set of inputted characters. the user can also choose
* to delete a set of characters from the inputted string. The final 
* option available to the user is a complement delete which will delete
* all characters from the string except the inputted ones.
*
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>


int translate(char* str1, char* str2);
int delete(char* str1);
int complement(char* str1);

int main(int argc, char *argv[])
{
   /* if  user only input 1 or 2 arguments which is not enough
      for the xlat command, an error is printed */
   if(argc == 1 || argc == 2)
   {
      fprintf(stderr, "usage: xlat [ -d ] [ -c ] set1 [ set2 ]\n");
      return -1;
   }
   else if(argc == 3)
   {
      /* If the delete switch is used with no attempted extra switch after it,
         the delete function is called */
      if(argv[1][0] == '-' && argv[1][1] == 'd' && argv[2][0] != '-')
      {
	  delete(argv[2]);
      }
      /* If no switches are inputed and the two strings are the same length,
         check if they are the same string */
      else if(argv[1][0] != '-' && strlen(argv[1]) == strlen(argv[2]))
      {
         /* if set1 has repeat characters, print an error message
            otherwise call the translate function */
	 int i = 0;
 	 int j = 0;
	 
	 for(i = 0; i < strlen(argv[1]) - 1; i++)
	 {
	   for(j = i + 1; j < strlen(argv[1]); j++)
	   {
	     if(argv[1][i] == argv[1][j])
	     {
	       fprintf(stderr, "usage: Set1 must not have duplicates\n");
	       return -1;
	     }
	   }
	 }
         
         translate(argv[1], argv[2]);
      }
      /*if the two strings are not the same length, print error message*/
      else if(argv[1][0] != '-' && strlen(argv[1]) != strlen(argv[2]))
      {
         fprintf(stderr,"usage: Set1 and set2 must be the same length.\n");
	 return -1;
      }
      /* if none of the correct arguments are inputted, an error is printed*/
      else
      {
	 fprintf(stderr, "usage: xlat [ -d ] [ -c ] set1 [ set2 ]\n");
         return -1;
      }
   }
   /* 4 arguments can only mean that the user wants to complement delete
      which leaves only 2 correct syntax entries */
   else if(argc == 4)
   {
     /* if the d switch and c switch are called in either order
        the argument is syntactically correct */
     if(argv[1][0] == '-' && argv[1][1] == 'd' && argv[2][0] == '-' 
     && argv[2][1] == 'c')
     {
       complement(argv[3]);
     }
     else if(argv[1][0] == '-' && argv[1][1] == 'c' && argv[2][0] == '-' 
     && argv[2][1] == 'd')
     {
       complement(argv[3]);
     }
     /* otherwise, an error message is thrown */
     else
     {
       fprintf(stderr, "usage: xlat [ -d ] [ -c ] set1 [ set2 ]\n");
       return -1;
     }     
   }

   return 0;
   
}

int translate(char* str1, char* str2)
{
  int c;
  int i = 0;
  int size = strlen(str1);
  
  /* takes an inputted character, and compares it to all the characters
     of the set to be replaced, if it finds one, it goes to the corresponding
     location in the replacement set and prints that character */
  while((c = getchar()) != EOF)
  {
    for(i = 0; i < size; i++)
    {
      if(c == str1[i])
      {
	c = str2[i];
	break;
      }
    }
    putchar(c);
  }
  return 0;
}

int delete(char* str1)
{
  int c;
  int i = 0;
  int index = 0;
  int size = strlen(str1);
  
  /* takes an inputted character, and compares it to all the characters
     of the set to be deleted, if it finds one, then it skips printing
     it and moves on to the next character */
  while((c = getchar()) != EOF)
  {
    for(i = 0; i < size; i++)
    {
      if(c == str1[i])
      {
	index = 1;
	break;
      }
    }
   
    if((index == 0))
    {
      putchar(c);
    }

    index = 0;
  }
  return 0;
}

int complement(char* str1)
{
  int c;
  int i = 0;
  int size = strlen(str1);
  
  /* takes an inputted character, and compares it to all the characters
       of the set to be kept, if it finds one, prints it and moves on 
       to the next character */
  while((c = getchar()) != EOF)
  {
    for(i = 0; i < size; i++)
    {
      if(c == str1[i])
      {
	putchar(c);
	break;
      }
    }
  }
  return 0;
}
