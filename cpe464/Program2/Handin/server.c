/* Garrett Milster 
 * 
 * CPE 464 Program 2
 *  Server side
 */


#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>          
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <unistd.h>
#include "util.h"
#include "cpe464.h"
#include <fcntl.h>
#include <string.h>
 int send_file_data(int sk, struct sockaddr_in remote, socklen_t rlen);
 int wait_for_ack(int sk, struct sockaddr_in remote, socklen_t rlen, unsigned char *block, int count);
 int wait_for_data_buffer(int sk, struct sockaddr_in *remote, socklen_t rlen, unsigned char data[]);


int main ( int argc, char *argv[]  )
{
   int sk ; // socket descriptor
   struct sockaddr_in remote ; // socket address for remote
   struct sockaddr_in local ; // socket address for us
   socklen_t rlen = sizeof(remote) ; // length of remote address
   socklen_t len   = sizeof(local); //// length of local address length of local address
   sk = open_socket();
   // set up the socket
   local.sin_family = AF_INET ;         // internet family
   local.sin_addr.s_addr = htonl(INADDR_ANY);// wild card machine address
   local.sin_port = htons(0);     
   if(argc != 2)
   {
      printf("incorrect number of arguments");
      exit(-1);
   }
   double error_percent = strtod(argv[1],NULL); 
   
   sendtoErr_init(error_percent, 1,1,1,1);
   
   // bind the name (address) to a port
   if (bindMod(sk,(struct sockaddr *)&local,sizeof(local)) < 0) {
      perror("bind call");
      exit(-1);
   }
   // get the port name and print it out
   if (getsockname(sk,(struct sockaddr*)&local,&len) < 0) {
      perror("getsockname call");
      exit(-1);
   }
   printf("socket has port %d \n\n", ntohs(local.sin_port));
   send_file_data(sk,remote, rlen);

   close(sk);	
   return 0;
}

int wait_for_data_buffer(int sk, struct sockaddr_in *remote, socklen_t rlen, unsigned char data[])
{
   int mesglen;
   int strlength = 0;
   unsigned char ack;
   int readFD;
   int len;
   while(1)
   {
      if ((mesglen = recvfrom(sk,data,108,0,(struct sockaddr *)remote,&rlen)) < 0)
	   {
	      perror("recvfrom call");
	      exit(-1);
	   }
      if(in_cksum((u_short*)data, 108) == 0)
      {
	      memcpy(&strlength, data+2,4);
	      strlength = ntohl(strlength);
	      char *filename = malloc(strlength);
	      memcpy(filename, data+6, strlength);
         ack = 1;

	      if((readFD = open(filename, O_RDONLY)) < 0)
		   {
            ack = 2;
		   }
		    //sends ack back to client saying it has correct data
          printf("server ack sent\n");
          len = sendtoErr(sk,&ack,1,0,(struct sockaddr *)remote, sizeof(*remote));
      }
      else
      {
         ack = 0;
         len = sendtoErr(sk,&ack,1,0,(struct sockaddr *)remote, sizeof(*remote));
      }
      
      //after the ack has been sent to client, it waits a few seconds to make sure
      // that the client received the ack correctly and didn't resend another one.
      if(select_call(sk, 3, 0) == 0 && ack == 1)
      {
         break;
      }
   }
   return readFD;
}

int wait_for_ack(int sk, struct sockaddr_in remote, socklen_t rlen, unsigned char *block, int buffer)
{
   int count = 5;
   char ACK;
   int len;
   while(count > 0)
   {
      if(select_call(sk, 1, 0) == 1)
      {  
         if ((len = recvfrom(sk,&ACK,1,0,(struct sockaddr *)&remote,&rlen)) < 0)
   	   {
   	      perror("recvfrom call");
   	      exit(-1);
   	   }
   	   if(ACK == 1)
   	   {
            printf("Valid ACK received\n");
            break;
   	   }
   	   else
   	   {
            printf("Invalid ACK: %d received\n", ACK);
   	      len = sendtoErr(sk,block,buffer+6,0,(struct sockaddr *)&remote, sizeof(remote));
   	      count--;
   	   }
	   }
	   else
	   {
	      len = sendtoErr(sk,block,buffer+6,0,(struct sockaddr *)&remote, sizeof(remote));
	      count--;
	   }
   }
   if (len < 0) {
      printf("Warning data not sent!\n");
      perror("sendtoErr call");
      exit(-1);
   }
   if(count == 0)
   {
      printf("ACK incorrect or not received 5 times, disconnecting client.\n");
      return(-1);
   }
   else
   {
      return 0;
   }
}

int send_file_data(int sk, struct sockaddr_in remote, socklen_t rlen)
{
   unsigned char data[108];   // buffer from remote
   
   while (1)
     {
        int readFD = wait_for_data_buffer(sk,&remote,rlen, data);
        int buf;
  	     memcpy(&buf, data,2);
	     buf = ntohs(buf);
        unsigned char *block = malloc(buf+6);
        int ack_response;
        int sequence_number = 0;
        unsigned short checksum = 0;
        int count;
        int len; 
       
        // clearing out the block for the checksum
        memset(block, '\0', buf+6);
        block[4] = 0;
        block[5] = 0;
        
        while((count = read(readFD, block+6, buf)) > 0)
        {   
           sequence_number = htonl(sequence_number);
           memcpy(block, &sequence_number, 4);
           checksum = in_cksum((u_short*)block, buf+6);
           memcpy(block+4, &checksum,2);
      
           sequence_number = ntohl(sequence_number);
           sequence_number++;
           
           //packet has been built and is now being sent.
           len = sendtoErr(sk,block,count+6,0,(struct sockaddr *)&remote, sizeof(remote));    
           printf("file data sent\n");
           if(len < 0) {
              perror("SEND ERROR: ");
              return -1;
           }
           //waits for ack before moving on.
           ack_response = wait_for_ack(sk, remote, rlen,block,count);
           if(ack_response < 0)
           {
              break;
           }
           memset(block, '\0', buf+6);
        }
        if(count < 0) {
           perror("Write Error\n");
           return -1;
        }

        if(close(readFD))
        {
           return -1;
        }
        printf("\n");
  	}
}