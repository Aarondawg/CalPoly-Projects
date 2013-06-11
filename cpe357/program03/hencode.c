/* Garrett Milster
 *
 * hencode is a program that, when given input, uses huffman 
 * encoding to compress the information and write it to a file,
 * or stdout if specified.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "functions.h"
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

#define CHAR_LEN 257

int read_file(int char_list[CHAR_LEN],
int int_list[CHAR_LEN], int fd, char* filename);
void print_tree(Node *root);
Node* build_list(int char_list[CHAR_LEN], int int_list[CHAR_LEN], int index);
void build_table(Node *root, int index, int table[CHAR_LEN][CHAR_LEN]);
Node* build_tree(Node *head);
void free_nodes(Node* root);
void bubbleSort(int int_list[], int char_list[], int index);
int open_file(char *filename, int status);
Node* safe_malloc(int size);
void alphaSort(int int_list[], int char_list[], int index);

int main(int argc, char *argv[])
{
   typedef struct linkedlist Node;
   int char_list[CHAR_LEN];
   int int_list[CHAR_LEN];
   int i, j, c, fd,fd2,num, index = 0;
   int total = 0;
   int table[CHAR_LEN][CHAR_LEN];
   int mask = 0x80, shift = 7;
   int buf = 0;
   int count, status = -1;
   unsigned char temp;
   Node *head;
   
   /* checks for improper argument entries */
   if(argc == 2 || argc == 3)
   {
      fd = open_file(argv[1], 0);
         if(fd < 0)
            return fd;

      if(argc == 3)
      {
         fd2 = open_file(argv[2], 1);
         if(fd2 < 0)
            return fd2;
      }
      else
      {
         fd2 = STDOUT_FILENO;
      }
   }
   
   /* in order, reads the file in, sorts the array
    * builds the linked list, then builds the binary
    * tree from the linked list. */

   index = read_file(char_list, int_list, fd, argv[1]);   
   num = int_list[index];
   bubbleSort(int_list, char_list, index -1);
   head = build_list(char_list, int_list, index);
   head = build_tree(head);
   
   /* fills the first column of the array with -1's
    * to signify whether or not that character is in the
    * file, if it is in the file, build_table will overwrite
    * the -1. */
   for(i = 0; i < CHAR_LEN; i++)
   {
      table[i][0] = -1;
   }
   
   
   build_table(head, 0, table);
   
   /* totals all character counts for later use*/
   for(i = 0; i < index; i++)
   {
      total = total + int_list[i];
   }
   
   free_nodes(head); /* frees all allocated data */
   alphaSort(int_list, char_list, index - 1); /*sorts alphabetically*/
   
   if(lseek(fd, 0, SEEK_SET) < 0)
   {
      perror("lseek");
   }
   
   if((count = write(fd2, &num,sizeof(int))) < 0)
   {
      perror("write");
      return -1;
   }   
   
   for(i = 0; i < index; i++)
   {
      if((count = write(fd2, &char_list[i],sizeof(char))) < 0)
      {
         perror("write");
         return -1;
      }
      if((count = write(fd2, &int_list[i],sizeof(int))) < 0)
      {
         perror("write");
         return -1;
      } 
   }
   num = 0;
   while((count = read(fd, &c, sizeof(char))) > 0)
   {  
      j = 0;
      temp = (unsigned char)c;
      for(j = 0; table[temp][j] >= 0; j++)
      {
       /*goes through each characters table entry and enters the path
         into buf, once buff is full, it's written tot he file and reset */
         if(table[temp][j] != 0)
         {
            buf = mask | buf;
         }
         shift --;
         if(shift < 0)
         {
            if((i = write(fd2, &buf,sizeof(char))) < 0)
            {
               perror("write");
               return -1;
            } 
            shift = 7;
            mask = (1 << shift);
            buf = 0;
            status = 1;
            
         }
         else
         {
            mask = (1 << shift);
            status = 0;
         }
      } 
      num++;    
      
   } 
   if(i == -1)
   {
      perror(argv[1]);
   } 
   /* if the last byte was not full, it is written padded with 0's */
   if((status == 0 && shift >= 0))
   {   
      if((i = write(fd2, &buf,sizeof(char))) < 0)
      {
         perror("write");
         return -1;
      } 
   }
   
   /*closes files and error checks */
   if(close(fd) < 0|| close(fd2) < 0)
   {
      perror("close");
      return -1;
   }
   return 0;  
}

/* read_file reads in all characters from a file and makes a histogram
 * table of said characters, it returns the index in the array where
 * the last unique character is entered */

int read_file(int char_list[],int int_list[], int fd, char* filename)
{
   int index = 0;
   int j, c;
   int count, num = 0;

   
   char_list[0] = -1;
   
   /* takes each character read in, and compares
    * it to the current list of characters,
    * if it is found, it only adds to the count of that
    * character, if it is not found, it adds it to the list */
   while((count = read(fd, &c, sizeof(char))) > 0)
   {
      for(j = 0; j < index; j++)
      {
         if(c == char_list[j])
         {
            int_list[j]++;   
            break;
         }
      }
      if(j == index)
      {
         char_list[index] = c;
         int_list[index] = 1;    
         num++;                
         index++;          
      }   
   }
   
   if(count == -1)
   {
      perror(filename);
   }   
   int_list[index] = num; /*saves the number of unique characters here */
   return index;
}

/* a simple bubble sort that sorts the arrays into alphabetical order */
void alphaSort(int int_list[], int char_list[], int index)
{
   
  int i, j, temp;

  for (i = index; i >= 0; i--)
  {     
    for (j = 1; j <= i; j++)
    {
      if (char_list[j-1] > char_list[j])
      {
        temp = int_list[j-1];
        int_list[j-1] = int_list[j];
        int_list[j] = temp;
        
        temp = char_list[j-1];
        char_list[j-1] = char_list[j];
        char_list[j] = temp;
      }

    }
  }
}
