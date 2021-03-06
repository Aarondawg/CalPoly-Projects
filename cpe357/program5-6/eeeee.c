/* Garrett Milster and DJ Mitchel
 *
 * Parseline takes a command line entry and
 * parses it into its various stages.
 */ 
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#define MAX_COMMANDS 10
#define MAX_ARGUMENTS 10
#define MAX_NAME_LENGTH 50 /*?*/
#define MAX_LINE_LENGTH 512
#define ASCII_NL 10
#define ASCII_CR 13
#define ARR_SIZE 150

typedef struct process {
   int argc;
   char argv[MAX_ARGUMENTS][MAX_NAME_LENGTH]; 
   char input[MAX_NAME_LENGTH]; /* or name of input for parseline */ 
   char output[MAX_NAME_LENGTH]; /* same as above */
} Process;

void handler(int num)
{
}


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
   int i = 0, numStages = 0, numInputs = 0, numOutputs = 0, lastStage = 0;
   char *temp, *delim = " ";
   char *line;
   int num = 0, flag = 0, j, index = 0, infile = STDIN_FILENO;
   Process stage[MAX_COMMANDS];
   
   if(argc > 1)
   {
      infile = open(argv[1], O_RDONLY);
   }

   while(1)
   {
      /* reads in the command */
      if(infile == STDIN_FILENO)
         printf("8-P ");
      if((line = readLongLine(infile)) == NULL)
      {
         break; /* happens if it encounters EOF */         
      }
      
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
            /* if the previous character was a < or > then it must be a file*/
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
   }

   


   free(line);
   return 0;
