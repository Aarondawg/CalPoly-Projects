/* Garrett Milster
 *
 * hdecode is a program that decodes a file or input compressed
 * with huffman encoding. it then writes it to a specified
 * file or stdout.
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

int main(int argc, char *argv[])
{
   int fd, fd2, count, c;
   int total = 0;
   int num, index = 0;;
   int char_list[CHAR_LEN];
   int int_list[CHAR_LEN];
   int mask = 0x80, shift = 7;
   Node *head;
   Node *ptr;
   
   /*checks the arguments */
   if(argc == 1)
   {
      fd = STDIN_FILENO;
      fd2 = STDOUT_FILENO;
   }
   else if(argc == 2)
   {
      fd = open_file(argv[1], 0);
      fd2 = STDOUT_FILENO;
   }
   else if(argc == 3)
   {
      if(argv[1][0] == '-')
      {
         fd = STDIN_FILENO;
         fd2 = open_file(argv[2],1);
      }
      else
      {
         fd = open_file(argv[1], 0);
         fd2 = open_file(argv[2], 1);
      }
   }
   
   count = read(fd, &c, sizeof(int));
   if(count < 0)
   {
      perror("read");
   }
   num = c;
   /* reads in header info and stores it in arrays. 
    * breaks when total number of characts has been reached. */
      while(count > 0)
      {
         count = read(fd, &c, sizeof(char));
         if(count <= 0)
         {
            break;
         }         
         char_list[index] = (unsigned char)c;
         count = read(fd, &c, sizeof(int)) ;
         int_list[index] = c;
         
         index++;
         total++;
         if(total == num)
            break;   
      }
   /* sorts the lists and then builds a linked list out of them.
    * after this a binary tree is built using the linked list */
   bubbleSort(int_list,char_list, index - 1);
   head = build_list(char_list, int_list, index);
   head = build_tree(head);
   
   /* if there is only one character in the file, it is written
    * the rest of the file will be skipped over */
   if(num == 1)
   {
      for(count = 0; count < int_list[0]; count++)
      {
         write(fd2, &char_list[0],sizeof(char)); 
      }
   }
   
   total = 0;
   num = 0;
   ptr = head;
   
   /* sums up total number of characters */
   for(count = 0; count < index; count++)
   {
      num = num + int_list[count];
   }
   
   while((count = read(fd, &c, sizeof(char))) > 0)
   {
      /*every byte is read in, and then a mask scans through
       * the byte, using each 1 and 0 to traverse the tree.
       * when the loop hits a node with a character, it writes it
       * and resets everything, starting back at the root of the tree */
      while(shift >= 0)
      {
         mask = (1 << shift);
         
         if(ptr->c != -1)
         {         
               write(fd2, &(ptr->c),sizeof(char)); 
               ptr = head;
               total++;
               if(total == num)
               {
                  break;
               }
         }
         else
         {
            if((mask & c) > 0)
            {
               ptr = ptr->right;
            }
            else
            {
               ptr = ptr->left;               
            }           
            shift--;
         }
      }      
      if(total == num)
      {
         break;
      }
      shift = 7;
   } 
   /* the loop quits before writing the last character due to my logic,
    * so this conditional is used for writing the last one. */
   if(total == (num-1))
   {
      write(fd2, &(ptr->c),sizeof(char));       
   }
   
   free_nodes(head);
   /*closes files and error checks */
   if(close(fd) < 0|| close(fd2) < 0)
   {
      perror("close");
      return -1;
   }
   return 0;
}
