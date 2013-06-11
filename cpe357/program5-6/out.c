#include <stdlib.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

#define READ_END 0
#define WRITE_END 1
#define MAX 256

int main()
{
   char c;
   while((c = getchar()) != EOF)
   {
      putchar(c);
   }
   return 0;
}