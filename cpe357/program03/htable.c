/* Garrett Milster
 *
 * htable is a program that takes an input file, and creates
 * a binary tree according to the huffman encoding model
 * then prints out each character from the file in hexadecimal
 * and its binary path in said tree.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "htable.h"

#define CHAR_LEN 257

int read_file(int char_list[CHAR_LEN],int int_list[CHAR_LEN], char* filename);
void print_tree(Node *root);
Node* build_list(int char_list[CHAR_LEN], int int_list[CHAR_LEN], int index);
void build_table(Node *root, int index, int table[CHAR_LEN][CHAR_LEN]);
Node* build_tree(Node *head);
void free_nodes(Node* root);
void bubbleSort(int int_list[], int char_list[], int index);
void print_tree(Node *root);

int main(int argc, char *argv[])
{
   typedef struct linkedlist Node;
   int char_list[CHAR_LEN];
   int int_list[CHAR_LEN];
   int i, j,index;
   int table[CHAR_LEN][CHAR_LEN];
   Node *head;
   
   /* checks for improper argument entries */
   if(argc != 2)
   {
      printf("usage: htable [file]\n");
         return -1;
   }
   
   /* in order, reads the file in, sorts the array
    * builds the linked list, then builds the binary
    * tree from the linked list. */
    
   index = read_file(char_list, int_list, argv[1]);
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
   
   /* prints the table of characters with binary tree paths */
   for(i = 0; i < CHAR_LEN - 1; i++)
   {
      if(table[i][0] != -1)
      {
         printf("0x%02x: ", i);
         for(j = 0; table[i][j] >= 0; j++)
         {
            printf("%d", table[i][j]);
         }
         printf("\n");
      }
   }
   
   free_nodes(head); /* frees all allocated data */
   return 0;
   
}

/* read_file reads in all characters from a file and makes a histogram
 * table of said characters, it returns the index in the array where
 * the last unique character is entered */
int read_file(int char_list[],int int_list[], char* filename)
{
   int index = 0;
   int i, j, c;
   FILE *file = fopen(filename, "r");
   
   /* if there is a problem with the file
    * an appropriate error is thrown */
   if(file == NULL)
   {
      perror(filename);
      return -1;
   }
   char_list[0] = -1;
   
   /* takes each character read in, and compares
    * it to the current list of characters,
    * if it is found, it only adds to the count of that
    * character, if it is not found, it adds it to the list */
    
   for(i = 0; (c = fgetc(file)) != EOF; i++)
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
         index++;          
      }   
   }
   
   return index;
}

/* build_list builds a linked list from the histogram table
 * made from read_file. */
Node* build_list(int char_list[],int int_list[], int index)
{
   Node *head;
   Node *previous;
   Node *ptr;
   int i;
   
   previous = malloc(sizeof(Node));
   previous->next = NULL;
   previous->c = char_list[0];
   previous->count = int_list[0];
   head = previous;
   
   /* iterates through the array making nodes for each entry
    * until it gets to index. */
   for(i = 1; i < index; i++)
   {
      ptr = malloc(sizeof(Node)); 
      ptr->next = NULL;
      ptr->c = char_list[i];
      ptr->count = int_list[i];
      previous->next = ptr;
      previous = ptr;
   }
   
   return head;
}

/* build_tree makes a binary tree according to the huffman model for encoding.
 * it takes the first two nodes, and sums their counts. it then takes this sum,
 * and makes a new node, who's count is the sum, and who's children are the two 
 * nodes. finally, the newly created node is re-inserted in the proper order, 
 * into the linked list. */ 
Node* build_tree(Node *head)
{
   Node *ptr = head;
   Node *next;
   Node *temp;
   Node *previous;
   int count = 0;

   while(ptr->next != NULL)
   {
      next = ptr->next;
      count = ptr->count + next->count;
      temp = malloc(sizeof(Node));
      temp->count = count;
      temp->left = ptr;
      temp->right = next;
      temp->next = NULL;
      temp->c = -1;
      
      /* this checks to see if there are enough nodes left
       * to go through the loop again, if there are only two nodes
       * left in the list, then this will be the last run through */
      if(next->next == NULL)
      {
         head = temp;
         break;
      }
      else
      {
         head = next->next;
      }

      ptr->next = NULL;   
      next->next = NULL;

      ptr = head;
      previous = head;
      
      /* now that the new node has been created, this loop goes through
       * the linked list and inserts it in its proper place */
      while(ptr != NULL)
      {
         if(temp->count <= ptr->count)
         {
            if(ptr == head)
            {
               temp->next = head;
               head = temp;
               break;
            }
            previous->next = temp;
            temp->next = ptr;
            break;
         }
         previous = ptr;
         ptr = ptr->next;
      }
      if(ptr == NULL)
      {         
         previous->next = temp;
         temp->next = ptr;
      }
      ptr = head;
   }
   return head;
}

/* build_table makes a two dimensional array out of the binary tree.
 * it recursively visits each character in the tree and keeps track of the
 * path it took to get there, then records the path into the table at the
 * index related to the characters integer value. */
void build_table(Node *root, int index, int table[CHAR_LEN][CHAR_LEN])
{
   int i;
   
   /* if a character is enter the path into the table */
   if(root != NULL)
   {
      if(root->c != -1)
      {
         table[root->c][index] = -1;
         /* if there is only one character total in the file
          * this -2 assignment keeps it from being lost during 
          * the printing stage in main. */
         if(index == 0)
         {
            table[root->c][0] = -2;
         }

         for(i = 0; i < index; i++)
         {
            table[root->c][i] = table[CHAR_LEN - 1][i];
         }  
      }
   }
   
   i = index; /* this assignment keeps the function from losing 
               * the value of index when it enters left */
               
   /* this recursive code keeps track of the path taken and stores it 
    * in an extra row at the very bottom of the table. */
   if(root->left != NULL)
   {
      table[CHAR_LEN - 1][index] = 0;
      index ++;
      build_table(root->left, index, table);
   }
   if(root->right != NULL)
   {      
      table[CHAR_LEN - 1][i] = 1;
      i ++;
      build_table(root->right, i, table);
   }
}

/* free_nodes simply recursively travels through the list
 * and frees all the allocated memory. */
void free_nodes(Node *root)
{
   if(root->left != NULL)
   {
      free_nodes(root->left);
   }
   if(root->right != NULL)
   {
      free_nodes(root->right);
   }
   
   free(root);
}

/* a traditional bubble sort algorithm with an extra case added.
 * if the counts of two characters are the same, it then checks
 * which character's integer value is higher. */
void bubbleSort(int int_list[], int char_list[], int index)
{
  int i, j, temp;
  for (i = index; i >= 0; i--)
  {
    for (j = 1; j <= i; j++)
    {
      if (int_list[j-1] > int_list[j])
      {
        temp = int_list[j-1];
        int_list[j-1] = int_list[j];
        int_list[j] = temp;
        
        temp = char_list[j-1];
        char_list[j-1] = char_list[j];
        char_list[j] = temp;
      }
      else if(int_list[j-1] == int_list[j])
      {
         if(char_list[j-1] > char_list[j])
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
}