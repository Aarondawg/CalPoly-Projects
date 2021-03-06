/* Garrett Milster 
 * 
 * CPE 464 Program 2
 * 
 */

 /* Simple socket program: client side    */

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

/* to run: a.out <IP address> <port #>
ex: a.out 192.168.1.1 4567 */

int main ( int argc, char *argv[] )
{
   int sk ;        // socket descriptor
   struct sockaddr_in remote ;    // socket address for remote sid
   struct hostent *hp ;       // address of remote host
   int mesglen ;   // actual length of the message
   int len; /* len of data actually sent  */
   char string[80];

   if ((sk = socket(AF_INET,SOCK_DGRAM,0)) < 0) {
      perror("socket call");
      exit(-1);
   }
   // designate the addressing family
   remote.sin_family = AF_INET; 
  
   // get the address of the remote host and store
   hp = gethostbyname(argv[1]);
   memcpy(&remote.sin_addr,hp->h_addr,hp->h_length);
   
   // get the port used on the remote side and store
   remote.sin_port = htons(atoi(argv[2]));

   printf("Enter the info to transmit: ");
   scanf("%s\n", string);
   mesglen = strlen(string)+ 1;
   printf("%s len: %d\n",  string, mesglen);
   len = sendto(sk,string,mesglen,0,(struct sockaddr *)&remote, sizeof(remote));

   if (len < 0) {
      printf("Warning data not sent!\n");
      perror("sendto call");
      exit(-1);
   }
   close(sk);
   return 0;
}

