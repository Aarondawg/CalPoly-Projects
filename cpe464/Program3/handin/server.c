/* Garrett Milster 
 * 
 * CPE 464 Program 3
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
#include <sys/wait.h>


 void init_server(int sk, struct sockaddr_in *local, socklen_t len, int argc);
 int wait_for_filename(int sk, struct sockaddr_in *remote, 
                       socklen_t rlen, int *readFD,unsigned short *window_size);
 int extract_filename(unsigned char data[], int *readFD, unsigned short *buffer_size, 
                      int sk, struct sockaddr_in *remote, unsigned short* window_size);
 int send_data(int sk, int fd, unsigned short buffer_size, unsigned short window_size,
                struct sockaddr_in *remote,socklen_t rlen);
 unsigned char* build_packet(unsigned char* data, int sequence_num, int buffer_size, int flag);
 int process_ack(int buffer_size, int high,int sk, struct sockaddr_in* remote,socklen_t rlen);
 struct window_data* init_buffer(int window_size, int fd, int buffer_size);
 void update_buffer(struct window_data* buffer, int window_size, int diff, int fd, int buffer_size);
 int send_eof(int sk, struct sockaddr_in *remote, socklen_t rlen);

int main(int argc, char *argv[])
{
   int sk,flag; 
   unsigned short buffer_size,window_size;
   struct sockaddr_in remote ; // socket address for remote
   struct sockaddr_in local ; // socket address for us
   socklen_t rlen = sizeof(remote) ; // length of remote address
   socklen_t len   = sizeof(local); //// length of local address length of local address
   int readFD;
   int status = 0;
   pid_t pid = 0;
	sk = open_socket(); // set up the socket
   
   local.sin_family = AF_INET ;  // internet family
   local.sin_addr.s_addr = htonl(INADDR_ANY); // wild card machine address
   local.sin_port = htons(0);
   
   double error_percent;
   
   init_server(sk, &local, len, argc);
   error_percent = strtod(argv[1],NULL);
   sendtoErr_init(error_percent,1,1,1,1);
   while(1)
   {
      buffer_size = wait_for_filename(sk, &remote, rlen, &readFD, &window_size);  
      
      if((pid = fork()) < 0)
      {
         perror("fork");
         exit(-1);
      }
      
      //for child
      if(pid == 0)
      {
         int sk2 = open_socket(); // set up the socket
         len = sizeof(local);

         local.sin_family = AF_INET ;  // internet family
         local.sin_addr.s_addr = htonl(INADDR_ANY); // wild card machine address
         local.sin_port = htons(0);
         if(bindMod(sk2,(struct sockaddr *)&local, len) < 0) 
         {
            perror("bind call\n");
            exit(-1);
         }
         if(getsockname(sk2,(struct sockaddr *)&local, &len) < 0)
         {
            perror("getsockname call\n");
            exit(-1);
         }
         printf("\nChild has socket has port %d \n\n", ntohs(local.sin_port));
         
         flag = send_data(sk2, readFD, buffer_size, window_size,&remote,rlen);
         if(flag >=0)
         {
            flag = send_eof(sk2,&remote, rlen);
            if(flag < 0)
            {
               printf("EOF ACK never received, disconnecting from client\n");
            }
         }
         close(sk);
         exit(0);
      }    
      while(waitpid(-1,&status,WNOHANG) >0)
      {
         printf("processed wiat\n");
      }
   }
   close(sk); 
   return 0;
}

void init_server(int sk, struct sockaddr_in *local, socklen_t len, int argc)
{
   
   if(argc !=2)
   {
      printf("Usage: server [error-percent]\n");
      exit(-1);
   }      
   if(bindMod(sk,(struct sockaddr *)local, len) < 0) 
   {
      perror("bind call\n");
      exit(-1);
   }
   
   if(getsockname(sk,(struct sockaddr *)local, &len) < 0)
   {
      perror("getsockname call\n");
      exit(-1);
   }
   struct sockaddr_in temp = *local;
   printf("socket has port %d \n\n", ntohs(temp.sin_port));
   
}

int wait_for_filename(int sk, struct sockaddr_in *remote, socklen_t rlen, 
                      int *readFD, unsigned short *window_size)
{
   int mesglen, len; 
   unsigned short buffer_size = 0;
   unsigned char ack;
   unsigned char data[115];
   memset(data, '\0', 115);
   
   while(1)
   {
      if ((mesglen = recvfrom(sk,data,115,0,(struct sockaddr *)remote,&rlen)) < 0)
	   {
	      perror("recvfrom call");
	      exit(-1);
	   }
      if(in_cksum((u_short*)data, 115) == 0)
      {
         ack = extract_filename(data, readFD, &buffer_size, sk, remote, window_size);
      }
      else
      {
         ack = 0;
         len = sendtoErr(sk,build_ack(ack,0),7,0,(struct sockaddr*)remote, sizeof(*remote));
         
      }

      if(select_call(sk, 3, 0) == 0 && ack == 7)
      {
         break;
      }
   }
   
   return buffer_size;
}

int extract_filename(unsigned char data[], int *readFD, 
                     unsigned short *buffer_size, int sk, 
                     struct sockaddr_in *remote, unsigned short *window_size)
{
   int strlength,fd, ack;
   unsigned short size;
   memcpy(&strlength, data+9,4);
   strlength = ntohl(strlength);
   char *filename = malloc(strlength);
   memcpy(filename, data+15, strlength);
   ack = 7;
   printf("\nFILE TO TRANSFER: %s\n\n",filename);
   if((fd = open(filename, O_RDONLY)) < 0)
   {
      ack = 8;
   }
   
   *readFD = fd;
   memcpy(&size, data+7,2);
   size = ntohs(size);
   *buffer_size = size; 
   memcpy(&size, data+13,2);
   size = ntohs(size);
   *window_size = size;
     
   //printf("Filename response sent\n");
   sendtoErr(sk,build_ack(ack,0),7,0,(struct sockaddr*)remote, sizeof(*remote));
    
   return ack;
}


int send_data(int sk, int fd, unsigned short buffer_size, unsigned short window_size,
               struct sockaddr_in *remote, socklen_t rlen)
{
   int count=0,ack, diff, low = 0,index = 0,eof_flag = 0;
   int high = window_size - 1;
   int window_state = 1;
   unsigned char *packet;
   struct window_data* buffer = init_buffer(window_size, fd, buffer_size);
   
   while(eof_flag == 0)
   {
      while(window_state == 1)
      {
         packet = build_packet(buffer[index].data, buffer[index].sequence_num, buffer[index].count, 1);
         sendtoErr(sk,packet,buffer[index].count+7,0,(struct sockaddr *)remote, sizeof(*remote));
         if(select_call(sk, 0, 0) == 1)
         {
            ack = process_ack(buffer_size, high,sk,remote,rlen);
            if(ack >= 0 && ack >= low)
            {
               if(buffer[window_size-1].eof == 1)
               {                  
                  if(ack == buffer[window_size-1].sequence_num+1)
                  {                     
                     eof_flag = 1;
                     break;
                  }
               }
               else
               {
                  low = ack;
                  diff = high;
                  high = low + window_size - 1;
                  diff = high - diff;
                  update_buffer(buffer,window_size, diff, fd, buffer_size);
                  index = -1;
               }
            }
            else if(ack == -1)
            {
               printf("SREJ received\n");
               index = -1;
            }
         }

         index++;
         if(index == window_size)
         {            
            window_state = 0;
         }
         
      }
      while(window_state == 0 && count < 10)
      {         
         if(select_call(sk, 1, 0) == 1)
         {            
            ack = process_ack(buffer_size, high,sk,remote,rlen);   
            if(ack >= 0 && ack >= low)
            {                              
               if(buffer[window_size-1].eof == 1)
               {                  
                  if(ack == (buffer[window_size-1].sequence_num+1))
                  {
                     
                     eof_flag = 1;
                     break;
                  }
               }
               else
               {
                  low = ack;
                  diff = high;
                  high = low + window_size - 1;
                  diff = high - diff;
                  update_buffer(buffer,window_size, diff, fd, buffer_size);
                  window_state = 1;
                  index = 0;
               }
            }
            else if(ack == -1)
            {
               printf("SREJ received (window closed)\n");
               index = 0;
               window_state = 1;
            }
            else
            {
               index--;
               window_state = 1;             
            }
            count = 0;
         }
         else
         {
            count++;
            index = 0;
            window_state = 1;
         }
      }
      if(count == 10)
      {
         printf("Data resent 10 times, quitting.\n");
         return -1;
       }
      
   }
   return 0;
}

unsigned char* build_packet(unsigned char* data, int sequence_num, int buffer_size, int flag)
{
   unsigned char *buffer = malloc(buffer_size + 7);
   memset(buffer, '\0', 7 + buffer_size);
   sequence_num = htonl(sequence_num);
   memcpy(buffer, &sequence_num, 4);
   memcpy(buffer + 6, &flag, 1);
   memcpy(buffer+7, data, buffer_size);   

   unsigned short check = 0;
   check = in_cksum((u_short*)buffer, buffer_size+7);
   memcpy(buffer+4, &check,2);

   return buffer;
}

int process_ack(int buffer_size, int high,int sk, struct sockaddr_in* remote, socklen_t rlen)
{
   int len,num;
   unsigned char ack[7];
   if ((len = recvfrom(sk,ack,7,0,(struct sockaddr *)remote,&rlen)) < 0)
   {
      perror("recvfrom call");
      exit(-1);
   }
   memcpy(&num, ack,4);
   num = ntohs(num);

  if(in_cksum((u_short*)ack, 7) == 0)
  {
      if(ack[6] == 3)
      {
         memcpy(&num, ack,4);
         num = ntohs(num);
         //printf("ACK %d received\n", num);
         
         if(num <= high+1)
         {
            return num;
         }
         
      }
      else if(ack[6] == 4)
      {
        // printf("SREJ packet received\n");
         return -1;
      }
  }
  return 0;

}

struct window_data* init_buffer(int window_size, int fd, int buffer_size)
{
   struct window_data *buffer = malloc(sizeof(struct window_data)*window_size);
   int i,count;
   for(i = 0; i < window_size; i++)
   {
      buffer[i].data = malloc(buffer_size);
      if((count = read(fd, buffer[i].data, buffer_size)) > 0)
      {
        buffer[i].count = count;
        if(count != buffer_size)
        {
           buffer[i].eof = 1;
           buffer[i].sequence_num = i;
           break;
        }
        buffer[i].eof = 0;
        buffer[i].sequence_num = i;
      }
      else
      {
        perror("reading file\n");
        exit(-1);
      }
   }

   return buffer;
}

void update_buffer(struct window_data* buffer, int window_size, int diff, int fd, int buffer_size)
{
   int i, count;
   int seq = buffer[window_size-1].sequence_num + 1;
   for(i = 0; i < diff; i++)
   {
      if((count = read(fd, buffer[i].data, buffer_size)) > 0)
      {
        buffer[i].count = count; 
        if(count != buffer_size)
        {
           buffer[i].eof = 1;
           buffer[i].sequence_num = seq;
           break;
        }
        buffer[i].eof = 0;
        buffer[i].sequence_num = seq;        
        seq++;
      }
      else
      {
        perror("update buffer: reading file\n");
        exit(-1);
      }

   }
   bubbleSort(buffer, window_size);
   
}

int send_eof(int sk, struct sockaddr_in *remote, socklen_t rlen)
{
   int len, count = 0;
   unsigned char ack[7];
   while(count < 10)
   {
      sendtoErr(sk,build_ack(9,0),7,0,(struct sockaddr *)remote, sizeof(*remote));
      if(select_call(sk, 3, 0) == 1)
      {
         if ((len = recvfrom(sk,ack,7,0,(struct sockaddr *)remote,&rlen)) < 0)
         {
            perror("recvfrom call");
            exit(-1);
         }
         
         if(ack[6] == 10)
         {
            printf("EOF ACK RECEIVED\n");
            break;
         }
         else
         {
            count++;
         }
      }
      else
      {
         count++;
      }

   } 
   
   if(count == 10)
   {
      return -1;
   }
   return 0;
}