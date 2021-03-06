#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "lab3.h"

#define CHAR_LEN 257

void bubbleSort(int int_list[], char char_list[], int index);
void print_tree(Node *root);
void build_table(Node *root, int index, int table[CHAR_LEN][CHAR_LEN]);


int main(int argc, char *argv[])
{
   typedef struct linkedlist Node;
   char char_list[CHAR_LEN];
   int int_list[CHAR_LEN];
   int i, j, index = 0, count = 0;
   char c;
   int table[CHAR_LEN][CHAR_LEN];
   FILE *file;
   Node *ptr, *previous, *head, *temp, *next;
   
   if(argc != 2)
   {
      printf("usage: htable [file]\n");
         return -1;
   }
   
   file = fopen(argv[1], "r");
   if(file == NULL)
   {
      perror(argv[1]);
      return -1;
   }
   char_list[0] = -1;
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
   
   bubbleSort(int_list, char_list, index -1);
   
   previous = malloc(sizeof(Node)); /*ERROR CHECK */
   previous->next = NULL;
   previous->c = char_list[0];
   previous->count = int_list[0];
   head = previous;
   
   for(i = 1; i < index; i++)
   {
      ptr = malloc(sizeof(Node)); /*ERROR CHECK */
      ptr->next = NULL;
      ptr->c = char_list[i];
      ptr->count = int_list[i];
      previous->next = ptr;
      previous = ptr;
   }
 

   ptr = head;
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
   
   j = 0;
   for(i = 0; i < CHAR_LEN; i++)
   {
      table[i][0] = -1;
   }
   build_table(head, j, table);
   
   for(i = 1; i < CHAR_LEN; i++)
   {
      if(table[i][0] != -1)
      {
         printf("0x%x: ", i);
         for(j = 0; table[i][j] != -1; j++)
         {
            printf("%d", table[i][j]);
         }
         printf("\n");
      }
   }
   
   return 0;
   
}


void bubbleSort(int int_list[], char char_list[], int index)
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

void print_tree(Node *root)
{
   if(root != NULL)
   {
      printf("%d\n", root->count);
   }
   if(root->left != NULL)
   {
      print_tree(root->left);
   }
   if(root->right != NULL)
   {
      print_tree(root->right);
   } 
}

void build_table(Node *root, int index, int table[CHAR_LEN][CHAR_LEN])
{
   int i;
   if(root != NULL)
   {
      if(root->c != -1)
      {
         table[(int)root->c][index] = -1;
         for(i = 0; i < index; i++)
         {
            table[(int)root->c][i] = table[0][i];
         }  
      }
   }
   i = index;
   if(root->left != NULL)
   {
      table[0][index] = 0;
      index ++;
      build_table(root->left, index, table);
   }
   if(root->right != NULL)
   {      
      table[0][i] = 1;
      i ++;
      build_table(root->right, i, table);
   }
}

