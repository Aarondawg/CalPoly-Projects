/* Garrett Milster and DJ Mitchell
 **
 ** pipeit forks a piped command
 **/
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <errno.h>

#define READ_END 0
#define WRITE_END 1

int main(int argc, char *argv[])
{

int fd;
int my_pipe[2];
if(pipe(my_pipe) < 0)
{
   perror("pipe");
   exit(-1);
}

pid_t childOne, childTwo;

// Child
if((childOne = fork()) == 0)
{
	//set up standard out to the pipe 	
	if(dup2(my_pipe[WRITE_END], STDOUT_FILENO) == -1)
        {
               perror("dup2");
               exit(1);
        }
	//close extra uneeded fd's
	close(my_pipe[WRITE_END]);
	close(my_pipe[READ_END]);
	execlp("ls", "ls", NULL);
	perror("exec");
	exit(EXIT_FAILURE);

}

// Second child process
if((childTwo = fork()) == 0)
{
	//set up standard in to come from pipe
        if(dup2(my_pipe[READ_END], STDIN_FILENO) == -1)
        {
            perror("dup2");
            exit(1);
        }
	//set up standard out to go to outfile
        if((fd = open("outfile", O_RDWR|O_CREAT|O_TRUNC,S_IRUSR|S_IWUSR)) < 0)
        {
		perror("open");
		exit(1);
 	}
        if(dup2(fd, STDOUT_FILENO) == -1)
        {
             perror("dup2");
             exit(1);
        }   
	//close extra uneeded fd's
	close(my_pipe[READ_END]);
	close(my_pipe[WRITE_END]);
	close(fd);
	execlp("sort", "sort", "-r", NULL);
	perror("exec");
	exit(EXIT_FAILURE);
}

    // Parent
    // close uneeded fd's
    int i, j, status;
    close(my_pipe[READ_END]);
    close(my_pipe[WRITE_END]);
    
    //wait for 2 child processes
    for (i = 0; i < 2; i++)
    {
        if((j = wait(&status)) == -1)
        {
            if(errno != EINTR)
            {
               perror("wait");
               return -1;
            }
	    i--;
        }
    }
    fflush(stdout);

    return 0;

}
