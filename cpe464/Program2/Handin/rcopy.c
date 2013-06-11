/* Garrett Milster 
 * 
 * CPE 464 Program 2
 *  Client side
 */


#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>   
#include <sys/stat.h>       
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <unistd.h>
#include <string.h>
#include <netdb.h>
#include <string.h>
#include "cpe464.h"
#include <fcntl.h>
#include "util.h"

void ack_waiting(char *from_remote_file, int sk, unsigned char *temp_buf, struct sockaddr_in *remote, socklen_t rlen);
void receive_data(int sk, unsigned char* block, int buffer_size,struct sockaddr_in remote, socklen_t rlen, int fd);

int main ( int argc, char *argv[] )
{
   int sk ;        // socket descriptor
   struct sockaddr_in remote ;    // socket address for remote sid
   struct hostent *hp ;       // address of remote host
   int len; /* len of data actually sent  */
   socklen_t rlen = sizeof(remote) ; // length of remote address
   
   if(argc != 7)
   {
      perror("Usage: rcopy [from-remote-file] [to-local-file] [buffer-size] [error-percent] [remote-machine] [remote-port]\n");
      exit(-1);
   }
   char *from_remote_file = argv[1];
   char *to_local_file = argv[2];
   int buffer_size = htons(atoi(argv[3]));
   double error_percent = strtod(argv[4],NULL); 
   char *remote_machine = argv[5];
   int remote_port = htons(atoi(argv[6]));  
	sk = open_socket();
   // designate the addressing family
   remote.sin_family = AF_INET; 
   
   //Initializing sendtoErr
   sendtoErr_init(error_percent, 1,1,1,1);
  
   // get the address of the remote host and store
   hp = gethostbyname(remote_machine);
   memcpy(&remote.sin_addr,hp->h_addr,hp->h_length);
   
   // get the port used on the remote side and store
   remote.sin_port = remote_port;
   unsigned char* temp_buf;
   unsigned short check = 0;
   int strlength = strlen(from_remote_file) + 1;
   temp_buf = malloc(2 + 104 + 2);
   
   //Building the data packet to send to the server
   memset(temp_buf, '\0', 108);
   memcpy(temp_buf, &buffer_size,2);
   strlength = htonl(strlength);   
   memcpy(temp_buf+2, &strlength, 4);   
   strlength = ntohl(strlength);
   memcpy(temp_buf+6, from_remote_file, strlength);  
   check = in_cksum((u_short*)temp_buf, 108);
   memcpy(temp_buf+106, &check,2);  
   len = sendtoErr(sk,temp_buf,108,0,(struct sockaddr *)&remote, sizeof(remote));
   
   //waits for the ack from the server saying it has the correct data.
   ack_waiting(from_remote_file, sk, temp_buf, &remote, rlen);
   printf("passed ack waiting\n");
   unsigned char* block = malloc(buffer_size+6);
   memset(block, '\0', buffer_size+6);
   
   
   int fd;
   if((fd = open(to_local_file, O_RDWR|O_CREAT|O_TRUNC,S_IRWXU)) < 0)
   {
      perror(to_local_file);
      return -1;
   }
   
   //waits for file data
   receive_data(sk, block, buffer_size,remote, rlen,fd);
  
   close(fd);
   close(sk);
   return 0;
}
void receive_data(int sk, unsigned char* block, int buffer_size,struct sockaddr_in remote, socklen_t rlen, int fd)
{
     int sequence;
     int prev = -1;
     int acknum;
     int count,len;
     while (1)
     {  
        if(select_call(sk, 10, 0) == 1)
        {	              
           if ((count = recvfrom(sk,block,buffer_size+6,0,(struct sockaddr *)&remote,&rlen)) < 0)
     	   {
     	      perror("recvfrom call");
     	      exit(-1);
     	   }

     	   memcpy(&sequence, block, 4); 

           sequence = ntohl(sequence);
           if(sequence == (prev +1))
           {      	   
              prev++;
              if(in_cksum((u_short*)block, buffer_size+6) == 0)
              {
                  acknum = 1;
                  len = sendtoErr(sk,&acknum,1,0,(struct sockaddr *)&remote, sizeof(remote));
                  if(write(fd, block+6, count-6) == -1) {
                     perror("Write Error\n");
                     exit(-1);
                  }
              }
              else
              {                                             
                 acknum = 0;
                 len = sendtoErr(sk,&acknum,1,0,(struct sockaddr *)&remote, sizeof(remote));
              }
           }
           else if(sequence == prev)
           {
              acknum = 1;
              len = sendtoErr(sk,&acknum,1,0,(struct sockaddr *)&remote, sizeof(remote));
           }



     	   memset(block, '\0', buffer_size+6);

           if(count != (ntohs(buffer_size) + 6))
           {            
              break;
           }
         }
         else
         {
           printf("Timer expired waiting for server response\n");
           exit(-1);
         }  
  	}
}
void ack_waiting(char *from_remote_file, int sk, unsigned char *temp_buf, struct sockaddr_in* remote, socklen_t rlen)
{
   int count = 5;
   int len;
   char ACK;
   while(count > 0)
   {   
      if(select_call(sk, 1, 0) == 1)
      {         
         if ((len = recvfrom(sk,&ACK,1,0,(struct sockaddr *)remote,&rlen)) < 0)
   	   {
   	      perror("recvfrom call");
   	      exit(-1);
   	   }
   	   if(ACK == 1)
   	   {
            printf("Valid ACK received\n");
            break;
   	   }
   	   else if(ACK == 2)
   	   {
            printf("Server could not open file: %s\n", from_remote_file);
            exit(-1);
         }
         else
         {   	  
            printf("Invalid ACK received\n");    
   	      len = sendtoErr(sk,temp_buf,108,0,(struct sockaddr *)remote, sizeof(*remote));
            count--;
         }
	   }
	   else
	   {         
	      count--;
	      len = sendtoErr(sk,temp_buf,108,0,(struct sockaddr *)remote, sizeof(*remote));
	   }
   }
   if(count == 0)
   {
      printf("Data buffer/Filename ACK not received correctly within 5 tries\n");
      exit(-1);
   }
   if (len < 0) {
      printf("Warning data not sent!\n");
      perror("sendtoErr call");
      exit(-1);
   }
}
