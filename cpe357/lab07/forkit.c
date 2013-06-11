/* Garrett Milster
 *
 * forks current program and prints pid
 * of parent and child 
*/
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>

int main()
{
   pid_t child,parent;
   int status;
   printf("Hello World\n");
   if((child = fork()))
   {
      parent = getpid();
      if(-1 == wait(&status))
      {
         perror("wait");
      }
      printf("This is the parent, pid %d.\n", parent);
      printf("This is the parent, pid %d, signing off.\n", parent);
   }
   else
   {
      child = getpid();
      printf("This is the child, pid %d.\n",child);
   }
   return 0;
}
