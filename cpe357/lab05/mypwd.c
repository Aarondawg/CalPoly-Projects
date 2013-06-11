/* Garrett Milster
 * Lab 5
 * mypwd prints out your current directory location.
 */

#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <dirent.h>
#include <unistd.h>
#include <sys/stat.h>
#include <string.h>

#define PATH MAX 2048
int main()
{
   struct stat buff;
   int device, inode;
   char string[2048];
   int len,i,j, index = 0;
   char *ptr;
   DIR *parent;
   struct dirent *child;

   lstat("/", &buff);
   /*stores the roots device/inode info */
   int root_d = (int)buff.st_dev;
   int root_i = (int)buff.st_ino;

   lstat(".", &buff);
   /*stores the first locations device/inode info */
   device = (int)buff.st_dev;
   inode = (int)buff.st_ino;

   chdir("..");
   parent = opendir(".");

   /*when these variables are equal, you're at the root */
   while(device != root_d && inode != root_i)
   {
      /*goes through each file in directory, stats them,
       * and compares them to the info you stored from your
       * previous unknown location */
      while((child = readdir(parent)) != NULL)
      {
         if(lstat(child->d_name, &buff) < 0)
         {
            perror("mypwd");
            return -1;
         }         
         if(buff.st_dev == device && buff.st_ino == inode)
         {
            /* stores the pathnames in string, separating 
             * each one by slashes*/
            len = strlen(child->d_name);
            string[index] = '/';
            j = 0;
            index++;
            if(index == PATH_MAX)
            {
               printf("path to long\n");
               return -1;
            }
            for(i = index; i < index + len; i++)
            {
               string[i] = child->d_name[j];
               j++;
            }
            index = index +len;
         }
      }

      lstat(".", &buff);
      device = (int)buff.st_dev;
      inode = (int)buff.st_ino;
      chdir("..");
      parent = opendir(".");

      /* So I tried error checking with this, the man page says it returns 
       * NULL on error but it always  enters the conditional and I don't know 
       * why, so I had to leave it out, I did the same with lstat, 
       * but it always entered the conditional for that as well =(
       * I just left this here so you know I tried. */

      /*if(parent == NULL);
      {
         printf("cannot get current directory.\n");
         return -1;
      }*/

   }

   string[index] = '\0'; 
   /* ends each string with null so it can be printed */

   for(i = index-1; i >= 0; i--)
   {
      /* when it encounters the / separator, it assigns the
       * string ptr to that location and prints it */
      if(string[i] == '/')
      {
         ptr = string+i;
         printf("%s", ptr);
         string[i] = '\0';
      }
   }
   printf("\n");
   return 0;
}
