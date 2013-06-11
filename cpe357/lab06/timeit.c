/* Garrett Milster
 * Lab06
 *
 * this program counts down the number of seconds
 * the user inputs printing tick and tock eveyr .5 seconds
 */
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/time.h>
#include <unistd.h>

void handler(int num);
int counter;

int main(int argc, char *argv[])
{
   struct sigaction sa;
   struct itimerval val;

   char *endptr;

   if(argc !=2)
   {
      fprintf(stderr,"usage: timeit <seconds>\n");
      return -1;
   }

   counter = (int)strtod(argv[1], &endptr);
   if(*endptr != '\0')
   {
      fprintf(stderr,"%s: malformed time\n", argv[1]);
      fprintf(stderr,"usage: timeit <seconds>\n");
      return -1;
   }
   
   sa.sa_handler = handler;
   sa.sa_flags = 0;
   sigaction(SIGALRM, &sa, NULL);
   
   val.it_interval.tv_sec = 0;
   val.it_interval.tv_usec = 500000;
   val.it_value.tv_sec = 0;
   val.it_value.tv_usec = 500000;

   setitimer(ITIMER_REAL, &val, NULL);
   for(;;)
      pause();
}

void handler(int num)
{
   static int which = 1;
   static int count = 0;
   if(which)
   {
      write(STDOUT_FILENO,"Tick...", 7);
   }
   else
   {
      write(STDOUT_FILENO,"Tock\n", 5);
      count++;
      if(count == counter)
      {
	printf("Time's up!\n");
        exit(0);
      }
   }
   which = !which;
}

