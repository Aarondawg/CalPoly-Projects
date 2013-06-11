/* Garrett Milster
*  
*  CPE 453: Program 1: Malloc.c
*
* Malloc replaces the libary functions malloc, calloc, realloc, free. 
*/


#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#define BLOCK 64000

// Basic header struct for linked list structure
typedef struct head header;
struct head
{
   int size;
   header* next;
   int free;
};

static int first = 0;
static header* base;
static int print = 0;

void* malloc(size_t size);
void *calloc(size_t count, size_t size);
char* getMemory();
char* checkLine(header* hptr, int size);
void buildHeader(header* hptr, int size, header* next, int free);
int checkSize(size_t size);
void free(void* ptr);
void* realloc(void* ptr, size_t size);
void mergeFree();
header* findHeader(char* ptr);

// malloc dynamically allocates the requested size of memory
void* malloc(size_t size)
{
   char* brk;
   int flag = 0;	   
   header* hptr;

   // checks size argument and makes it divisible by 16
   int trimmed_size = checkSize(size);  

   // checks if debug print statements should be activated
   if(getenv("DEBUG_MALLOC") != NULL)
   {
	  flag = 1;
   }	  

   // if size is zero just return null
   if(size == 0)
   {
      return NULL;
   }   

   // checks global variable to see if inital malloc call
   if(first == 0)
   {
      base = (header*) sbrk(0);
      brk = getMemory();
      hptr = base;
      if(brk == NULL)
      {
	      return NULL;
      }	      

      brk = checkLine(hptr, trimmed_size + sizeof(header));  
      if(brk == NULL)
      {
         return NULL;
      }  
      first++; 	     
      
      //builds header
      buildHeader(hptr, trimmed_size, NULL, 0);   
      
      //prints malloc debug statement if flags are set
      if(flag == 1 && print == 0)
      {		      
	        printf("MALLOC: malloc(%d)       =>   (ptr=%p, size=%d)\n", (int) size, (hptr + sizeof(header)), trimmed_size);
      }
	   
      return hptr + sizeof(header);
   }
   else
   {
      hptr = base;
      

      while(1)
      {
         // if the header is free and has enough size, use this chunk and set flag != to free
         if(hptr->free == 1)
         {
            if(trimmed_size <= hptr->size)
            {
	              if(flag == 1 && print == 0)
                 {		       
                     printf("MALLOC: USED malloc(%d)       =>   (ptr=%p, size=%d)\n", (int) size, (hptr + sizeof(header)), trimmed_size);
	              }
	              hptr->free = 0;
	              return hptr + sizeof(header);
            }
         }

         // iterates through pointers until the end of the list is found
         if(hptr->next != NULL)
         {
            hptr = hptr->next;
         }
         else
         {
            hptr->next = hptr + sizeof(header) + hptr->size;
            hptr = hptr->next;
            break;
         }
      }	
   }
      // checks to see if top of breakline will be hit by new data chunk
      brk = checkLine(hptr, trimmed_size + sizeof(header));  
      if(brk == NULL)
      {
         return NULL;
      }  
      buildHeader(hptr, trimmed_size, NULL, 0);  
      if(flag == 1 && print == 0)
      {
         printf("MALLOC: malloc(%d)       =>   (ptr=%p, size=%d)\n", (int) size, (hptr + sizeof(header)), trimmed_size);
      }
      return hptr + sizeof(header);
}

// calloc mallocs the result of count * size and sets the string to zero
void *calloc(size_t count, size_t size)
{
   int flag = 0;
   
   if(getenv("DEBUG_MALLOC") != NULL)
   {
      flag = 1;
   }

   print = 1;	
   char* ptr = (char*) malloc(count * size);
   print = 0; 
   int i;
   if(ptr == NULL)
   {
      return NULL;
   }	   
   if(flag == 1)
   {
	    printf("MALLOC: calloc(%d,%d)       =>   (ptr=%p, size=%d)\n", (int)count, (int) size, ptr, checkSize(count*size));
   }

   // initialize all allocated memory to 0
   for(i = 0; i < (count*size); i++)
   {
      ptr[i] = 0;
   }
   return ptr;
}

// get memory moves the program break line up BLOCK size
char* getMemory()
{
   char* ptr;
   if((ptr = (char*) sbrk(BLOCK)) < 0)
   {
      errno = ENOMEM; 	
      return NULL;
   }
   return ptr;
}

// check line checks the difference between the break line and the current head pointer
// and gets more memory if needed.
char* checkLine(header* hptr, int size)
{
   char* top = (char*) sbrk(0);
   char* temp;
   while((top - (char*) hptr) < size)
   {
      temp = getMemory();
      if(temp == NULL)
      {
         return NULL;
      }
      top = (char*) sbrk(0);	
   }
   return top;

}

// sets header data in memory
void buildHeader(header* hptr, int size, header* next, int free)
{
   hptr->size = size;
   hptr->next = next;
   hptr->free = free;
}

// takes size argument and raises up to the next multiple of 16
int checkSize(size_t size)
{
	int temp_size = (int) size;
	int i = 16;
	while(temp_size > i)
	{
		i+=16;
	}
	return i;	
}	

// frees the allocated memory pointed to by ptr
void free(void* ptr)
{
   int flag = 0; 	
   if(getenv("DEBUG_MALLOC") != NULL)
   {
       flag = 1;
   }
   header* hptr;
   
   if(ptr != NULL)
   {
      // finds the header for ptr's data
      hptr = findHeader((char*)ptr);
      if(hptr != NULL)
      {
	      if(flag == 1)     
	      {         
	         printf("MALLOC: free(%p)\n", (hptr + sizeof(header)));
	      }   
         hptr->free = 1;
      }

      // finds sequential free chunks of memory to free
      mergeFree();
   }

}

// finds the header for a ptr to data
header* findHeader(char* ptr)
{
   header* hptr = base;

   // checks for case of one header in list
   if(hptr->next == NULL)
   {
      return hptr;
   }	   
   
   // iterates through until header found
   while(hptr->next != NULL)
   {
      if((char*)hptr->next > ptr)
      {
         return hptr;
      }
      hptr = hptr->next;
   }
   
   // checks  final header
   if((char*)(hptr + sizeof(header) +  hptr->size) > ptr)
   {
      return hptr;	   
   }	   

   return NULL;
}

// iterates through headers, if two concurrent headers are free, merges them together into one
void mergeFree()
{
   header* hptr = base;
   header* next = hptr->next;
      if((hptr->free == 1) && (next != NULL) && (next->free == 1))
      {
         hptr->size += next->size + sizeof(header);
         hptr->next = next->next;
      }
}

// realloc resizes allocated memory ptr, by size
void* realloc(void* ptr, size_t size)
{
   int flag = 0;
   if(getenv("DEBUG_MALLOC") != NULL)
   {
       flag = 1;
   }    
   
   // if ptr is null, just call malloc with size
   if(ptr == NULL)
   {
      return malloc(size);
   }

   // if size is full, free ptr 
   if((ptr != NULL) && (size == 0))
   {
      free(ptr);
   }

   header* hptr = findHeader((char*) ptr);
   header* next = hptr->next;
   char* temp;
   int trimmed_size = checkSize(size);


   if(hptr->free == 1)
   {
      return NULL;
   }

   // if the new size is smaller than the current size, do nothing
   if(hptr->size > trimmed_size)
   {
      return hptr + sizeof(header);
   }
   else if((next != NULL) && (next->free == 1)) // if there is a next node and its free, check if you can merge them together for the requested size
   {
      if((next->size + hptr->size + sizeof(header)) > trimmed_size)
      {
         // if the size is big enough, merge them together and return the new ptr
         hptr->size = next->size + hptr->size + sizeof(header);
         hptr->next = next->next;
         if(flag == 1)
         {    	 
           printf("MALLOC: realloc(%p,%d)       =>   (ptr=%p, size=%d)\n", ptr, (int) size, (hptr + sizeof(header)), trimmed_size);
         }
         return hptr + sizeof(header);
      }
         // if the size isn't big enough, call malloc
          print = 1;
	       temp = malloc(trimmed_size);
          if(temp != NULL)
          {
            // if malloc was successful free the old ptr
            if(flag == 1)
	         {
	            printf("MALLOC: realloc(%p,%d)       =>   (ptr=%p, size=%d)\n", ptr, (int) size, temp, trimmed_size);
	         }
	         free(ptr);
         }  
	  print = 0;
	  return temp;

   }
   else
   {
	      print = 1;
         temp = malloc(trimmed_size);
         // if no existing header chunk could be found, call malloc and free old ptr if successful 
	      if(temp != NULL)
         {
	          if(flag == 1)		 
	          {	
		          printf("MALLOC: realloc(%p,%d)       =>   (ptr=%p, size=%d)\n", ptr, (int) size, temp, trimmed_size);
	          }
	          free(ptr);
         }
	          print = 0;
         return temp;
   }
   return NULL;

}

