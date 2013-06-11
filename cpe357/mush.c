/* Garrett Milster and DJ Mitchel
 *
 * Parseline takes a command line entry and
 * parses it into its various stages.
 */ 
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#define MAX_COMMANDS 10
#define MAX_ARGUMENTS 10
#define MAX_NAME_LENGTH 50 /*?*/
#define MAX_LINE_LENGTH 512
#define ASCII_NL 10
#define ASCII_CR 13
#define ARR_SIZE 150
#define READ_END 0
#define WRITE_END 1

typedef struct process {
   int argc;
   char argv[MAX_ARGUMENTS][MAX_NAME_LENGTH]; 
   char input[MAX_NAME_LENGTH]; /* or name of input for parseline */ 
   char output[MAX_NAME_LENGTH]; /* same as above */
} Process;



/* Returns NULL if it reaches EOF */
char *readLongLine(FILE *file)
{
    int temp = 0;
    char *line = malloc(sizeof(char)*MAX_LINE_LENGTH);
    int i, mult =1;
    /* Reads in an arbitrary length line from file to line,
     character by character */
    
        for(i=0;(temp=fgetc(file)) != EOF;i++) {
            if(i == (mult*MAX_LINE_LENGTH -1))
        	line = realloc(line, (++mult)*MAX_LINE_LENGTH);	
            if(temp == ASCII_NL || temp == ASCII_CR) {
                line[i] = '\0';
                break;
            }
            else
                line[i] = temp;
        }
	
    if(temp!=EOF)
        return line;
    else
        return NULL;    
            
}

int main(int argc, char *argv[])
{
   char arr[ARR_SIZE][MAX_NAME_LENGTH];
   int pipes[MAX_COMMANDS][2];
   int i = 0, numStages = 0, numInputs = 0, numOutputs = 0, lastStage = 0;
   char *temp, *delim = " ";
   char *line;
   int num = 0, flag = 0, j, index = 0,fd,fd2;
   pid_t child;
   Process stage[MAX_COMMANDS];
   
   /* reads in the command */
   printf("line: ");
   line = readLongLine(stdin);
   if((temp = strtok(line, delim)) == NULL)
   {
      fprintf(stderr, "Must enter a line.\n");
      return -1;
   }
   strcpy(arr[i], temp);
   i++;
   num++;
   lastStage = 1;
   /* This loop checks for errors before we allocate memory */
   while((temp = strtok(NULL, delim)) != NULL)
   {      
      strcpy(arr[i], temp);
      /* for output redirects */
      if(arr[i][0] == '>')
      {
         /* flag is on if the previous character was a file symbol */
         if(flag == 1)
         {
            fprintf(stderr, "invalid null command\n");
            return -1;
         }
         flag = 1;
         numOutputs++;
         if(numOutputs > 1)
         {
            fprintf(stderr, "%s: bad output redirection\n", arr[0]);
            return -1;
         }
      }
      /* for input redirects */
      else if(arr[i][0] == '<')
      {
         if(flag == 1)
         {
            fprintf(stderr, "invalid null command\n");
            return -1;
         }
         flag = 1;
         numInputs++;
         if(numInputs > 1)
         {
            fprintf(stderr, "%s: bad input redirection\n", arr[lastStage]);
            return -1;
         }
         if(numStages > 0)
         {
            fprintf(stderr, "%s: ambiguous input\n", arr[i-1]);
            return -1;
         }
      }
      /* for pipes */
      else if(arr[i][0] == '|')
      {
         num = 0;
         if(flag == 1)
         {
            fprintf(stderr, "invalid null command\n");
            return -1;
         }
         flag = 1;
         if(i == 0 || arr[i-1][0] == '|')
         {
            fprintf(stderr, "%s: invalid null command\n", arr[lastStage]);
            return -1;
         }
         if(numOutputs > 0)
         {
            fprintf(stderr, "%s: ambiguous output\n", arr[lastStage]);
         }
         numStages++;
         lastStage = i-1;
      }
      /* for filename and arguments*/
      else
      {
         flag = 0;
         /* if the previous character was a < or > then it must be a file */
         if(arr[i-1][0] != '>' && arr[i-1][0] != '<')
         {
            num++;
         }
         if(num == MAX_ARGUMENTS)
         {
            fprintf(stderr, "%s: too many arguments\n", arr[lastStage]);
            return -1;
         }
      }
      if(numStages == MAX_COMMANDS)
      {
         fprintf(stderr, "pipeline too deep\n");
         return -1;
      }
      i++;
   }


   /* initialize all input and output strings to null character
      if it is a null char that means it is original stdin/stdout */
   for(j=0; j<MAX_COMMANDS; j++)
   {
      stage[j].input[0] = '\0';
      stage[j].output[0] = '\0';
   }
   numStages =0;
   index = 0;
   num = i;
   
   strcpy(stage[index].argv[0], arr[0]);
   printf("HERE\n");
   
   stage[index].argc++;
   /* checks previous string, if < or >, sets the appropriate input/output
    * if it's a pipe, it moves to the next stage, otherwise, it's an argument */
    /* flag is used to prevent double counting of arguments */
    
   for(j=2; j<=num; j++)
   {
      if(arr[j-1][0] == '>')
      {
         flag = 1;
         strcpy(stage[index].output, arr[j]);
      }
      else if(arr[j-1][0] == '<')
      {
         flag = 1;
         strcpy(stage[index].input, arr[j]);
      }
      else if(arr[j-1][0] == '|')
      {
         flag = 1;
         strcpy(stage[index].output, arr[j]);
         index++;
         numStages++;
         strcpy(stage[index].argv[0], arr[j]);
         stage[index].argc++;
         strcpy(stage[index].input, stage[index-1].argv[0]);         
      }
      else
      {
         if(flag != 1)
         {
            strcpy(stage[index].argv[stage[index].argc++], arr[j-1]);
         }
         flag = 0;
      }
   }

   /* prints the stages*/
   j = 0;
   index = 0;
   printf("\n");
   for(i=0; i<=numStages; i++)
   {
      printf("--------\nStage %d: \"%s", i, arr[index]);
      if(index+1 == num)
      {
         printf("\"");
      }
      for(j=index+1; j < num; j++)
      {
         if(strcmp(arr[j],"|") == 0 || j == num-1)
         {
            if(j == num -1)
            {
               printf(" %s", arr[j]);
            }
            printf("\"");
            index = j + 1;
            break;
         }
         printf(" %s", arr[j]);
         
      }
      printf("\n--------\n");
      /*input*/
      printf("input: ");
      if(strlen(stage[i].input) == 0)
      {
         printf("original stdin\n");
      }
      else if(i > 0)
      {
         if(strcmp(stage[i].input,stage[i-1].argv[0]) == 0)
         {
            printf("pipe from stage %d\n", i-1);
         }
         else
         {
            printf("%s\n", stage[i].input);
         }
      }
      else
      {
         printf("%s\n", stage[i].input);
      }
      /*output*/
      printf("output: ");
      if(strlen(stage[i].output) == 0)
      {
         printf("original stdout\n");
      }
      else if(i != numStages)
      {
         if(strcmp(stage[i].output,stage[i+1].argv[0]) == 0)
         {
            printf("pipe to stage %d\n", i+1);
         }
         else
         {
            printf("%s\n", stage[i].output);
         }
      }
      else
      {
         printf("%s\n", stage[i].output);
      }
      /*argc and argv*/
      printf("argc: %d\n",stage[i].argc);
      printf("argv: ");
      for(flag = 0; flag < stage[i].argc; flag++)
      {
         printf("\"%s\"",stage[i].argv[flag]);
         if(flag == stage[i].argc-1)
         {
            printf("\n");
         }
         else
         {
            printf(",");
         }
      }
      printf("\n");
   }
   free(line);
   
   for(i = 0;i < numStages; i++)
   {
      if(pipe(pipes[i]) < 0)
      {
         perror("pipe");
         exit(-1);
      }
   }

   for(i=0; i<=numStages; i++)
   {
      close(pipes[i][WRITE_END]); 
      /*input*/
      if((child = fork()) == 0)
      {
         if(strlen(stage[i].input) == 0)
         {
            /*stdin is set */
         }
         else if(i > 0)
         {
            if(strcmp(stage[i].input,stage[i-1].argv[0]) == 0)
            {
            
               if(dup2(pipes[i-1][READ_END], STDIN_FILENO) == -1)
               {
                  perror("dup2");
               }
               /*do i close read_end here? */
               close(pipes[i-1][WRITE_END]);
               close(pipes[i-1][READ_END]);
            }
            else
            {
               if((fd = open(stage[i].input, O_RDONLY)) < 0)
               {
                  perror(stage[i].input);
               }
               if(dup2(fd, STDIN_FILENO) == -1)
               {
                  perror("dup2");
               }
               close(fd);
            }
         }
         else
         {
            if((fd = open(stage[i].input, O_RDONLY)) < 0)
            {
               perror(stage[i].input);
            }
            if(dup2(fd, STDIN_FILENO) == -1)
            {
               perror("dup2");
            }      
            close(fd);
         }
         /*output*/
         if(strlen(stage[i].output) == 0)
         {
         }
         else if(i != numStages)
         {
            if(strcmp(stage[i].output,stage[i+1].argv[0]) == 0)
            {
                if(dup2(pipes[i][WRITE_END], STDOUT_FILENO) == -1)
                {                   
                   perror("dup2");
                }
               close(pipes[i][READ_END]);   
               close(pipes[i][WRITE_END]);
            }
            else
            {
               if((fd2 = open(stage[i].output, O_RDWR|O_CREAT|O_TRUNC,S_IRWXU)) < 0)
               {
                  perror(stage[i].output);
                  return -1;
               }
               if(dup2(fd2, STDOUT_FILENO) == -1)
               {
                  perror("dup2");
               }    
               close(fd2);
            }     
         }
         else
         {
            if((fd2 = open(stage[i].output, O_RDWR|O_CREAT|O_TRUNC,S_IRWXU)) < 0)
            {
               perror(stage[i].output);
               return -1;
            }
            if(dup2(fd2, STDOUT_FILENO) == -1)
            {
               perror("dup2");
            }   
            close(fd2);      
         }
         execve(stage[i].argv[0],stage[i].argv, NULL);
         perror("exec");
         exit (-1);
      }
   }
   for(i=0; i<=numStages; i++)
   {
      wait(NULL);
   }
   
   return 0;
}
