/* Garrett Milster
 * DJ Mitchell
 *
 * This program reads can read many files, put all
 * the words into a hash table, then make a list of the
 * top n words, N defaults at 10 but can be specified by
 * the -n switch */
 
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "fw.h"

#define BASE_LENGTH 20
#define HASHSIZE 500

char *read_long_word(FILE *file);
void add_string(Node *array[], char* string, int* size);
unsigned hash(char *s);
void lookup(char *s, Node *table[]);


int main(int argc, char *argv[])
{
   int i = 0; 
   int j = 0;
   int boolean = 0;
   int size = 0;
   char *str;
   typedef struct linkedlist Node;
   int numWords = 10;
   FILE *file;
   char *temp, **dp = &temp;
   static Node *hashtab[HASHSIZE]; /* Hash Table */
   Node *current;
   Node *previous;
   Node *table;
   Node *ptr;
   
   /* initializes every node to Null */
   for(j = 0; j < HASHSIZE; j++)
   {
      hashtab[j] = NULL;
   }
   
   
   if(argc == 1)
   {
      boolean = 1;
      /*if boolean == 1 then they are using stdin */
   }
   else
   {
      /* only contains fw and file1 */
      if(argv[1][0] != '-') 
      {
          i = 1;        
      }
      else if(argc >= 3 && argv[1][0] == '-' && argv[1][1] == 'n') 
      {
          /* will be true if argv[2] is not a number */
          if(!strtol(argv[2], dp, 10) || dp != &temp) 
          {
              printf("usage: fw [-n num] [ file1 [ file 2 ...] ]\n");
              exit(EXIT_FAILURE);
          }
          /* for when -n is invoked and number is valid */
          else 
          {
              numWords = (int)strtol(argv[2], NULL, 10);
              if(argc == 3)
              {
                 boolean = 1;
                 /* if there are only three arguments then
                  * they are using stdin */
              }
              else
              {
                 i = 3;
              }
          }
      }
      /* for the case of invalid commands such as: fw -z */
      else 
      {
          printf("usage: fw [-n num] [ file1 [ file 2 ...] ]\n");
          exit(EXIT_FAILURE);
      }
   }
   
   /* if they are using stdin, use this while loop */
   if(boolean == 1)
   {
      while((str = read_long_word(stdin)) != NULL)
      {         
         add_string(hashtab, str, &size);
      }
      
   }
   else
   {
      for(; i<argc; i++) 
      {
          /* this loop can be used to read from multiple files */
          file = fopen(argv[i], "r");
          if(!file)
          {
             perror(argv[i]);
          }
          else if(!boolean)
          {
             while((str = read_long_word(file)) != NULL)
		          add_string(hashtab, str, &size);
            
                
	     fclose(file);
          }
      }
   }
   
   i = 0; j = 0;
   /* table holds the top n words specified by
    * numWords, defaults at 10 or modified by -n switch */
   table = malloc(sizeof(Node) * numWords);
   
   for(i = 0; i < numWords; i++)
   {
      table[i].count = 0;
      table[i].word ="a";
   }
   
   /* Pulls the top N words out of the Hashtable by
    * by comparing each node against the smallest of the
    * the top N words, and iterating up the list, pushing
    * everything it is greater than down the list until it
    * finds the spot it belongs */
   for(j = 0; j < HASHSIZE; j++)
   {
      if(hashtab[j] != NULL)
      {
         /* for each node */
         ptr = hashtab[j];
         while(ptr != NULL)
         {
            previous = table + numWords - 1;
            
            for(i = numWords - 1; i >= 0; --i)
            {           
                 
               current = table + i;
               /* compare the count, if the node's count is 
                * higher, push the current node down on the 
                * list and keep iterating up */
               if(ptr->count > current->count)
               {	
                  previous->word = current->word;
                  previous->count = current->count;
                  if(i == 0)
                  {
                     current->word = ptr->word;
                     current->count = ptr->count;
                  }                  
                  previous = current;
               }
               /* otherwise, if they are equal, compare their words and
                * sort according */
               else if(ptr->count <= current->count && i != numWords - 1)
               {
                  if(ptr->count == current->count)
                  {
		               if(strcmp(ptr->word,current->word) > 0)
   		            {
		                  previous->word = current->word;
                         previous->count = current->count;
                         previous = current;
                     /* if it is equal to the biggest value,
                      * replace that value with ptr */
	                      if(!i)
	                      {
			                   current->word = ptr->word;
                            current->count = ptr->count;
			     
	                         break;
	                      }
                     }
                     else
   		            {
      		            previous->word = ptr->word;
                       previous->count = ptr->count;
                       break;
  		              }
                  }
                  else
                  {
		               previous->word = ptr->word;
      		         previous->count = ptr->count;
	 	               break; 
                  }  
		  
               }
	       else if(i == numWords - 1 && ptr->count == current->count)
	       {	
		     if(strcmp(ptr->word,current->word) > 0)
   		     {
		         previous->word = current->word;
                         previous->count = current->count;
                         previous = current;
	 	         }	   
		         else
		       break;
	         }
	         else
		       break;
            }
	   
            ptr = ptr->next;
         }
      }
   }
   
   printf("The top %d words (out of %d) are:\n", numWords, size);
   if(size == 0)
   {
      
   }
   else
   {
      for(i = 0; i < numWords; i++)
      {
         if(table[i].count != 0)
            printf("%9d %s\n", table[i].count, table[i].word);
      }
   }
   
   /* iterates through and frees all data allocated */
   for(i=0; i<HASHSIZE; i++)
   {
       ptr = hashtab[i];
       while(ptr!=NULL)
       {
           current=ptr;
           ptr=ptr->next;
           free(current->word);
           free(current);
       }
   }
   free(table);
   return 0;
}


char *read_long_word(FILE *file)
{
  int temp = 0;
  char *string = malloc(BASE_LENGTH);
  int i, mult = 1;
  /* reads through input and creates individual words */
  /* also makes them lower case if capital */   
  for(i=0; (temp=fgetc(file)) != EOF; i++)
  {
     if(i == (mult*BASE_LENGTH - 1))
       string = realloc(string, (++mult)*BASE_LENGTH);
     if(!isalpha(temp) && i) {
       string[i] = '\0';
       break;
     }
     else if(!i && !isalpha(temp))
       i--;
     
     else
       string[i] = tolower(temp);
     
  }

  if(temp!=EOF)
    return string;
  
  else
    return NULL;
   
}

void add_string(Node *array[], char* string, int* size)
{
   int value = hash(string);
   Node *new;
   Node *current;
   Node *previous;
   /* if there is nothing in the hash table
    * at the hash value, make a first Node */
   if(array[value] == NULL)
   {
      new = malloc(sizeof(Node));
      array[value] = new;
      new->count = 1;
      new->word = string;
      new->next = NULL;
      *size = *size + 1;
      
   }
   else
   {
      /* otherwise, if the word is in the linked list
       * add 1 to its count, if it is not found, make 
       * a new node for it. */
      current = array[value];
      previous = array[value];
      while(current != NULL)
      {
         if(strcmp(string, current->word) == 0)
         {
            current->count++;
            break;
         }
         previous = current;
         current = current->next;
      }
      
      if(current == NULL)
      {
         new = malloc(sizeof(Node));
         previous->next = new;
         new->count = 1;
         new->word = string;
         new->next = NULL;
         *size = *size + 1;
         
      }
   }
}

/*Hash function from K&R textbook */
unsigned hash(char *s)
{
   unsigned hashval = 0;
   for(hashval =0; *s != '\0'; s++)
   {
      hashval = *s + 31 * hashval;
   }
   
   return hashval% HASHSIZE;
}

/* this is a function purely for testing, it will print the
 * searched word and its count if found, taken from
 * K&R textbook */
void lookup(char *s, Node *table[])
{
   Node *np;
   for(np = table[hash(s)]; np != NULL; np = np->next)
   {
      if(strcmp(s,np->word) == 0)
      {
         printf("%s---%d\n", np->word, np->count);
      }
   }
}
