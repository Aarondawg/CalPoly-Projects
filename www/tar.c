#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <dirent.h>

#define MAX_PATH 256
#define BLOCK 512
int build_file(int fd, char *dname);

int main(int argc, char* argv[])
{
   int c, t, x, v, f, S  = 0;
   int len, i,fd;
   char *string = malloc(MAX_PATH); 
   if(argc < 2)
   {
      printf("Usage: mytar [ctxvS]f tarfile [ path [ ... ] ]\n");
      return -1;
   }
   len = strlen(argv[1]);
   /* used for keeping track of what switches were used */
   for(i = 0; i < len; i++)
   {
      if(argv[1][i] == 'c')
      {
         c = 1;
      }
      else if(argv[1][i] == 't')
      {
         t = 1;
      }
      else if(argv[1][i] == 'x')
      {
         x = 1;
      }
      else if(argv[1][i] == 'v')
      {
         v = 1;
      }
      else if(argv[1][i] == 'f')
      {
         f = 1;
      }
      else if(argv[1][i] == 'S')
      {
         S = 1;
      }
      else
      {
         printf("name of archive file is required\n");
         return -1;
      }
   }
   
   if(f == 0)
   {
      printf("name of archive file is required\n");
      return -1;
   }
   
   if(c == 1)
   {
      fd = open(argv[2], O_RDWR|O_TRUNC|O_CREAT, S_IRWXU);
   }
   else
   {
      fd = open(argv[2], O_RDWR|O_TRUNC);;
   }
   
   string = getcwd(string, MAX_PATH);
   
   printf("%s\n",string);
   build_file(fd, string);
   return 0;
}

int build_file(int fd, char *dname)
{
   DIR *current_location;
   struct dirent *current;
   struct stat buff;
   int status, index,total = 0;
   int len, i,count;
   char *temp;
   char * dot = ".";
   char * dot2 = "..";
   char block[BLOCK];
   memset(block, '\0', BLOCK);
   current_location = opendir(dname);
   while((current = readdir(current_location)) != NULL)
   {
      if(strcmp(current->d_name, dot) != 0 && strcmp(current->d_name, dot2) != 0)
      {
         lstat(current->d_name, &buff);
         if(S_ISREG(buff.st_mode) == 1)
         {
            status = 0;
         }
         else if(S_ISDIR(buff.st_mode) == 1)
         {
            status = 1;
         }
         else if(S_ISLNK(buff.st_mode) == 1)
         {
            status = 2;
         }
         printf("%s\n", current->d_name);
   
         /*puts name in header block */
         len = strlen(current->d_name);
         if(len <= 100)
         {            
            for(i = 0; i < len; i++)
            {
               block[i] = current->d_name[i];
            }      
         }
         else
         {
            if(len > 255)
            {
               printf("Filename too large\n");
               return -1;
            }
            for(i = 0; i < 100; i++)
            {
               block[i] = current->d_name[i];
            }
            index = i;
            i = 345;
            while(current->d_name[index]) {
               block[i] = current->d_name[index];
               index++;
               i++;
            }
         }

         /* mode */
         index = 100;
         
         
      }

      
   }

   closedir(current_location);
   return 0;
}
