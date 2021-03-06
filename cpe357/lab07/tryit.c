/* garrett milster 
 *
 * tryit executes the program specified */
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char* argv[])
{
   pid_t child;
   int status;
   if(argc < 2)
   {
      fprintf(stderr,"usage: tryit command\n");
      return -1;
   }
   if(!(child = fork()))
   {
      execl(argv[1],argv[1], NULL);
      perror(argv[1]);
      exit(1);
   }
   if(-1 == wait(&status))
   {
      perror("wait");
      exit(2);
   }
   if(WIFEXITED(status) && !WEXITSTATUS(status))
   {
      printf("Process %d succeeded.\n", (int)getpid());
      exit(EXIT_SUCCESS);
   }  
   else
   {
      printf("Process %d exited with an error value.\n", (int)getpid());
      exit(EXIT_FAILURE);
   }
   return 0;
}
