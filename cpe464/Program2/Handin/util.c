#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>          
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <unistd.h>
#include <netdb.h>
#include <string.h>
#include "cpe464.h"
#include "util.h"
#include <time.h>

int open_socket()
{
   int sk;
   // create the socket
   if ((sk = socket(AF_INET,SOCK_DGRAM,0)) < 0) 
   {
      perror("socket call");
      exit(-1);
   }
   return sk;
}

int select_call(int socket, int seconds, int useconds)
{
   static struct timeval timeout;
   fd_set fdvar;
   timeout.tv_sec = seconds;  // set timeout to 1 second
   timeout.tv_usec = useconds; // set timeout (in micro-second)
   FD_ZERO(&fdvar); // reset variables
   FD_SET(socket,&fdvar); // 
   return selectMod(socket+1,(fd_set *) &fdvar, (fd_set *) 0, (fd_set *) 0, &timeout);
  /* if (FD_ISSET(socket, &fdvar))
   {
   // socket is ready for recv, data is there
      return 1;
   } 
   else 
   {
      return 0;
   }*/
}