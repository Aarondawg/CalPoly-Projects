#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char *argv[])
{
   int good = 0;
   int words = 0;

   if(argc != 2)
   {
      printf("Usage: a.out <filename>\n");
      return 0;
   }

FILE *fp;

fp = fopen(argv[1], "r");

   if(fp == NULL)
   {
      printf("Unable to open file.\n");
      return 0;
   }

char string[100];

while(fgets(string, 100, fp) != NULL)
{
   char *tokptr = strtok(string, "\n,.? ");
   
   while(tokptr)
   {
      if(strlen(tokptr) <= 4)
      {
         good++;
         printf("%s\n", tokptr);
      }
      words++;
      tokptr = strtok(NULL, "\n,.? ");
   }
//printf("GOOD: %d\n", good);
//printf("WORDS: %d\n", words);
}
fclose(fp);
if(words == 0)
{
   printf("** Story is blank! **\n");
}
else if (good >= words/2 +1)
{
   printf("** Alex would like this story. **\n");
}
else
{
   printf("** This story is too hard. **\n");
}

return 0;






}
