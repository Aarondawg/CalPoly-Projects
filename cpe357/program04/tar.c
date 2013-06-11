/* Garrett Milster
 * DJ Mitchel
 *
 * tar does lots of stuff
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <time.h>
#include <fcntl.h>
#include <unistd.h>
#include <dirent.h>
#include <grp.h>
#include <pwd.h>
#include "extract.h"

#define MAX_PATH 256
#define BLOCK 512
int build_file(int fd, char *dname, char* tarfile, int flag);
int write_file(char *name, int fd, char* tarfile, int flag);


int main(int argc, char* argv[])
{
   int c = 0, t = 0, x = 0, v=0, f=0, S = 0;
   int len, i,fd, val, index,status, type;
   int  permissions, size;
   char block[BLOCK];
   char name[MAX_PATH];
   char dir[MAX_PATH];
   char temp[12], uname[32], gname[32];
   time_t *mtime = malloc(sizeof(time_t));

   if(argc < 2)
   {
      fprintf(stderr,"Usage: mytar [ctxS[f tarfile]] [ path [ ... ] ]\n");
      return -1;
   }
   len = strlen(argv[1]);
   /* used for keeping track of what switches were used */
   for(i = 0; i < len; i++)
   {
      if(argv[1][i] == 'c')
      {
         c++;
      }
      else if(argv[1][i] == 't')
      {
         t++;
      }
      else if(argv[1][i] == 'x')
      {
         x++;
      }
      else if(argv[1][i] == 'v')
      {
         v++;
      }
      else if(argv[1][i] == 'f')
      {
         f++;
      }
      else if(argv[1][i] == 'S')
      {
         S++;
      }
      else
      {
         fprintf(stderr,"Incorrect tar flag used\n");
         return -1;
      }
   }
   if((c + t + x) > 1 || (c + t + x) == 0)
   {
      fprintf(stderr,
      "Usage: %s: you may only choose one of the 'ctx' options.\n"
      , argv[2]);
      fprintf(stderr,"Usage: mytar [ctxS[f tarfile]] [ path [ ... ] ]\n");
      return -1;  
   }
   
   if(c > 1 || t > 1 || x > 1 || S > 1 || f > 1)
   {
      fprintf(stderr,"Usage: mytar [ctxS[f tarfile]] [ path [ ... ] ]\n");
      return -1; 
   }
   if(f == 0)
   {
      fprintf(stderr,"Name of archive file is required\n");
      return -1;
   }
   if(v == 1 && S == 1)
   {
      f = 2;
   }
   else if(v == 1)
   {
      f = 1;
   }
   else if(S == 1)
   {
      f = 0;
   }
   else
   {
      f = -1;
   }
   if(c == 1)
   {

      if((fd = open(argv[2], O_RDWR|O_TRUNC|O_CREAT, S_IRWXU)) < 0)
      {
         perror("open1");
         return -1;
      }
      if(argc > 3)
      {
         for(i = 3; i < argc; i++)
         {
            build_file(fd,argv[i],argv[2], f);
         }
      }
      /* writes final two null blocks */
      memset(block, '\0', BLOCK);
      if(write(fd, block, BLOCK) == -1) {
         perror("Write Error\n");
         return -1;
      } 
      if(write(fd, block, BLOCK) == -1) {
         perror("Write Error\n");
         return -1;
      }
      close(fd);
      return 0;
   }
   else
   {
      /* changed the permissions to read only*/
      if((fd = open(argv[2], O_RDONLY)) < 0)
      {
         perror("open2");
         return -1;
      }
   }
   if(t == 1)
   {
      status = 1;
      memset(block, '\0', BLOCK);
      while((f = read(fd, block, 512)) > 0)
      {
         i = 0;
         while(block[i++] == '\0' && i<BLOCK)
         {
         /*nothing*/
         }

         if(i == BLOCK)
         {
            if((f = read(fd, block, BLOCK)) != BLOCK)
            {
               perror("Read");
               exit(EXIT_FAILURE);
            }
            i = 0;
            while(block[i++] == '\0' && i<BLOCK)
	         {}   /*nothing*/
         
            /*indicates 2 null blocks, which means end of archive*/
            if(i == BLOCK)
               break;
         }

         f = 0;

         /* checksum */
         for(i = 0; i < BLOCK; i++)
         {
            f = f + (unsigned char)block[i];
         }
         index = 148;
         for(i=0; i<8; i++)
         {
            temp[i] = block[index++];
            f -= temp[i];            
         }
         temp[i] = '\0';
         f += ASCII_SPACE*8; 

         val = (int)strtol(temp, NULL, 8);

    
         if(f != val)
         {
            if(val == 0 && f == 0)
            {
               return 0;
            }
            fprintf(stderr,
            "bad header in archive...quiting.\ncalculated:%d\nreal:%d",f,val);
            return -1;
         }


         index = 100;   
         /* mode */
         permissions = (int)strtol((block+index), NULL, 8);

         index = 265;
         i = 0;
         /* uname */
         while(block[index] != '\0')
         {
            uname[i] = block[index++];
            i++;
         }
         uname[i] = '\0';
         index = 297;
         i = 0;
         /* gname */
         while(block[index] != '\0')
         {
            gname[i] = block[index++];
            i++;
         }
         gname[i] = '\0';
   
         index = 124;
         /* size */
         size = (int)strtol(block+index, NULL, 8);

         index = 136;
         /* mtime */
         *mtime = (int)strtol(block+index, NULL, 8);

         /* file type */
         index = 156;
         type = (int)strtol(block+index, NULL, 10);
         
         index = 345;
         i = 0;
         /* name */
         if((len = strlen(block+index)))
         {
            while(i < len)
            {
               name[i++] = block[index++];
            }
            name[i++] = '/';
            
         }
         index = 0;
         while(index < 100 && block[index] != '\0')
            name[i++] = block[index++];
         name[i] = '\0';
         
          /*should only enter this loop if there are multiple arguments
          * will only change status if header is not any of files, and so
          * not printing it. */
         for(i = 3; i < argc; i++)
         {
            f = strcmp(argv[i], name);
            if(f == 0)
            {
               status = 1;
               index = 156;
               if(block[index] == '5')
               {
                  c = 1;
                  strcpy(dir, name);
                  t = strlen(name);
               }
               break;
            }
            status = 0;
         }
         /* need code for extracting name */
         /* need code for extracting octal strings into ints */
         if(c == 1)
         {  
            if(strncmp(name, dir,t) == 0)
            {
               status = 1;
            }
            else
            {
               status = 0;
               c = 0;
            }
         }
         if(status == 1)
         {
            if(v > 0)
            {
               x = 0;
               if(type == 0)
               {
                  printf("-");
               }
               else if(type == 5)
               {
                  printf("d");
               }
               else if(type == 2)
               {      
                  printf("l");
               }
               for(i = 8; i >= 0; i--)
               {
                  if(((1 << i) & permissions) > 0)
                  {
                     if(x == 0)
                     {
                        printf("r");
                     }
                     else if(x == 1)
                     {                     
                        printf("w");
                     }                     
                     else if(x == 2)
                     {                    
                        printf("x");
                     }
                     x++;
                     if(x == 3)
                     {
                        x = 0;
                     }
                  }
                  else
                  {
                     x++;
                     printf("-");
                     if(x == 3)
                        x = 0;
                  }
               }
               printf(" ");
               printf("%8s", uname);
               printf("/");
               printf("%8s", gname);
               /*size */
               printf(" %8d ", size);
               /* mtime*/
               strftime(uname,17,"%Y-%m-%d %H:%M", localtime(mtime));
               printf("%s", uname);
               printf(" %s\n", name);
            }
            else
            {
               printf("%s\n", name);
            }
         }
         
         index = 124;
         x = (int)strtol(block+index, NULL, 8);
         f = x / BLOCK;
         val = x % BLOCK;
         if(val > 0)
         {
            f++;
         }
         lseek(fd, BLOCK * f, SEEK_CUR);
         
         memset(block, '\0', BLOCK);
      }
   }
   else
   {
      len = argc - 3;
      if(len == 0)
      {
         extract(NULL,0, f, fd);
      }
      else
      {
         extract(argv, argc, f, fd);
      }
   }
   

   
   return 0;
}

int build_file(int fd, char *dname, char* tarfile, int flag)
{
   DIR *current_location = NULL;
   struct dirent *current;
   char * dot = ".";
   char * dot2 = "..";
   struct stat buff;
   char temp[MAX_PATH];
   int i, len,j, index =0;

   if(lstat(dname, &buff) == -1)
   {
      printf("%s: Incorrect file path\n", dname);
      return -1;
   }
   /* what do we do for links? */
   if(S_ISDIR(buff.st_mode))
   {
      current_location = opendir(dname);
      write_file(dname, fd, tarfile, flag);
      while((current = readdir(current_location)) != NULL)
      {
         index = 0;
         if(strcmp(current->d_name, dot) != 0 
            && strcmp(current->d_name, dot2) != 0)
         {  
            len = strlen(dname);
            for(i = 0; i < len; i++)
            {
               temp[i] = dname[i];
            }
                 
            if(temp[len -1 ] == '/')
            {
               j = 0;
            }
            else
            {
               temp[len] = '/';
               j = 1;
            }  
            for(i = len +j; i < len + strlen(current->d_name) + 1; i++)
            {
               temp[i] = current->d_name[index];
               index++;
            }       
            temp[i] = '\0';    
            if(build_file(fd, temp, tarfile,flag) == -1)
            {
               return -1;
            }
         }
         /* call function for file */
      }
      if(closedir(current_location) < 0)
      {
         perror("closedir");
         return -1;
      }
   }
   else
   {
      if(write_file(dname, fd, tarfile, flag) < 0)
      {
         return -1;
      }
   }
   return 0;
}


int write_file(char *name, int fd, char* tarfile, int flag)
{
   struct stat buff;
   struct passwd * pwp;
   struct group * grp;
   int status, index = 0;
   int len, i, readFD;
   int count = 0;
   char *temp = malloc(32);
   char *ptr;
   char checksum[8];
   unsigned char block[BLOCK];
   char typeflag;
   char linkname[100];

   memset(block, '\0', BLOCK);

   if(lstat(name, &buff))
   {
      printf("%s: file could not be found\n", name);
      return -1;
   }
   if(S_ISREG(buff.st_mode) == 1)
   {
      status = 0;
      typeflag = '0';
      if(strcmp(tarfile,name) == 0)
      {
         status = -1;
      }
   }
   else if(S_ISDIR(buff.st_mode) == 1)
   {
      status = 1;
      typeflag = '5';
   }
   else if(S_ISLNK(buff.st_mode) == 1)
   {      
      status = 2;
      typeflag = '2';
   }
   else
   {      
      printf("incorrect file type\n");
      status = -1;
   }
   /* prints if verbose is on */
   if(flag > 0)
   {
      printf("%s\n", name);
   }
   /* SLASH PROBLEM FOR DIRECTORIES */
   if(status >= 0)
   {
      len = strlen(name);
      /*puts name in header block */
      if(len > 256)
      {
         printf("filename to long\n");
         return -1;
      }
      if(status == 1)
      {
         if(name[len-1] != '/')
         {
            ptr = malloc(len + 2);
            strcpy(ptr, name);
            name = ptr;
            name[len] = '/';
            name[len + 1] = '\0';
         }
         len = strlen(name);          
      }      
      if(len > 100)
      {
         count = len - 100;
         for(i = count; i < len; i++)
         {
            if(name[i] == '/' && (i != len-1))
            {
               break;
            }
         }
         if(i == len)
         {
            if(len < 156)
            {
               index = 345;
               for(i = 0; i < len; i++)
               {
                  block[index] = name[i];
                  index++;
               }
            }
            else
            {
               printf("cannot separate filename\n");
               return -1;
            }
         }
         else
         {
            ptr = name + i + 1;
            index = 345;
            for(count = 0; count < i; count++)
            {
               block[index] = name[count];
               index++;
            }
            for(index = 0; ptr[index] != '\0';index++)
            {
               block[index] = ptr[index];
            }
         }
      }
      else
      {
         for(i =0; i < len; i++)
         {
            block[i] = name[i];
         }
      }

      /* mode */
      memset(temp, '\0', 32);
      index = 100;
      buff.st_mode = buff.st_mode & 0x1FF;
      i = sprintf(temp,"%08o", buff.st_mode);
      for(i = 1; i <= 8; i++)
      {
         block[index] = temp[i];
         index++;
      }
     
      /* uid */
      memset(temp, '\0', 32);
      i = sprintf(temp,"%08o", buff.st_uid);
      /* if strict is on and uid is too big, quit */
      if((temp[0] == '1') && ((flag == 0) || (flag == 2)))
      {
         printf("%s: uid to large\n", name);
         return -1;
      }
      for(i = 1; i <= 8; i++)
      {
         if(temp[0] == '1')
         {
            block[index] = '7';
         }
         else
         {
            block[index] = temp[i];
         }

         index++;
      }
      block[index -1] = '\0';
      /* gid */
      memset(temp, '\0', 32);      
      i = sprintf(temp,"%08o", buff.st_gid);      
      for(i = 1; i <= 8; i++)
      {
         block[index] = temp[i];
         index++;
      }
      /* if it's a regular file, put size in, otherwise 0 */
      memset(temp, '\0', 32);
      if(status == 0)
      {
         i = sprintf(temp,"%012o", (unsigned int)buff.st_size);
         for(i = 1; i <= 12; i++)
         {
            block[index] = temp[i];
            index++;
         }
      }
      else
      {
         for(i = 1; i <= 12; i++)
         {
            block[index] = 0;
            index++;
         }           
      }
      /* mtime */
      memset(temp, '\0', 32);
      i = sprintf(temp,"%012o", (unsigned int)buff.st_mtime);
      for(i = 1; i <= 12; i++)
      {
         block[index] = temp[i];
         index++;
      }
      /* space for checksum */
      for(i = 0; i < 8; i++)
      {
         block[index] = ' ';
         index++;
      }
      block[index] = typeflag;
      index++;
      /* if symlink, get name and insert it*/
      if(status == 2)
      {
         if((len = readlink(name, linkname, 100)) == -1)
         {
            perror("symlink");
            return -1;
         }
         for(i = 0; i < len; i++)
         {
            block[index] = linkname[i];
            index++;
         }         
      }
      index = 257;
      memset(temp, '\0', 32);
      temp = "ustar";
      /* ustar */
      for(i = 0; i < 6; i++)
      {
         block[index] = temp[i];
         index++;
      }
      /* inserts 00 for version */
      block[index] = '0';
      index++;
      block[index] = '0';
      index++;
      /* uname */
      if((pwp = getpwuid(buff.st_uid)) == NULL)
      {
         printf("getpwuid error\n");
         return -1;
      }
      i=0;
      while(pwp->pw_name[i] && i < 31)
      {
         block[index] = pwp->pw_name[i];
         i++;
         index++;
      }
      /* gname */
      
      if((grp = getgrgid(buff.st_gid)) == NULL)
      {
         printf("getgrgid error\n");
         return -1;
      }
      index = 297;
      i = 0;      
      while(grp->gr_name[i] && i < 31)
      {
         block[index] = grp->gr_name[i];
         i++;
         index++;
      }
      index = 148;
      count = 0;
      for(i = 0; i < BLOCK; i++)
      {
         count = count + block[i];
      }


      i = sprintf(checksum,"%08o", count);
      
      for(i = 1; i <= 8; i++)
      {
         block[index] = checksum[i];
         index++;
      }
      
      /* write header */
      if(write(fd, block, BLOCK) != BLOCK) {
         printf("Write Error\n");
         return -1;
      }
      
      if(status == 0)
      {
         readFD = open(name, O_RDONLY);         
         /* write regular file */
         memset(block, '\0', BLOCK);
         while((count = read(readFD, block, 512)) > 0)
         {
            if(write(fd, block, BLOCK) == -1) {
               perror("Write Header Error\n");
               return -1;
            } 
            memset(block, '\0', BLOCK);
         }
         if(close(readFD))
         {
            return -1;
         }
      }
   }

   return 0;     
}

