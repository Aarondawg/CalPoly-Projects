/* Garrett Milster */
/* November 29 2009 */
/* A program for sending messages between various users using the network */



#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <time.h>
#include "net.h"
#include "p6.h"

#define MAX_MESSAGES 10
#define MAX_USERS 10

struct message
{
   char from[STR_LEN];
   char date[STR_LEN];
   char message[MSG_LEN];
};

struct user
{
   char name[STR_LEN];
   int num_msgs;
   struct message messages[MAX_MESSAGES] ;
};

/*
   get_user_file

   Prompt the user for the name of a 'users' file.  This file contains
   the names of the users of the messaging system.

   Open this file and return the file stream (FILE *).
*/
FILE * get_user_file(){
   FILE *fp;
   char filename[200];
  printf("Please enter the name of the user file: ");
  scanf("%s", filename);
  fp = fopen(filename, "r");
  if (fp == NULL) {
    printf("Invalid file exiting\n");
  }
     return(fp);

}

/*
   get_users

   Parameters:
      users: array of struct user elements representing the users of the
         system -- this array is to be initialized by this function
      file: the file stream from which to read the user list

   Read the set of users from the given file.  Initialize the users
   array based on this data.  Return the number of users.
*/
int get_users(struct user users[], FILE *file){
  int i, j;

 while(fscanf(file, "%s", users[i].name) == 1 && i < 10){
 i++;
}
  for(j = 0; j < i; j++){
	users[j].num_msgs = 0;
  }

 return(i);
}

/*
   handle_send

   Parameters:
      connection: the network connection used to communicate with the client
      users: array of struct user elements representing the users of the
         system
      num_users: the number of users -- entries 0 to num_users - 1 in the
         users array represent the users of the system

   Handle a SEND request from the client.
*/
void handle_send(CONNECTION_ID connection, struct user users[], int num_users)
{

 char sender[STR_LEN];
 char recipient[STR_LEN];
 char message[MSG_LEN];
 int  i, compare, index;

  int len;
  time_t rawtime;
  char time_string[80];




  compare = 0;

  for(i = 0; i < num_users; i++){
     if(strcmp(users[i].name, recipient) == 0){
	compare = 1;
	index = i;
	}
    }

  if(compare == 0){
     write_string(connection, USER_NOT_FOUND_MSG);
   }



 if(compare == 1){

  read_string(connection, sender, STR_LEN);
  read_string(connection, recipient, STR_LEN);
  read_string(connection, message, MSG_LEN);

  }


  if(users[index].num_msgs > 10){
	write_string(connection, TOO_MANY_MESSAGES_MSG);
  }

	
  /*get the raw time */
  time( &rawtime);

  /* put it into a string and convert to 'normal' time */
  strcpy(time_string, asctime(localtime(&rawtime)));

  /* now get rid of the newline */
  len = strlen(time_string);
  if (time_string[len-1] == '\n') {
    time_string[len-1] = '\0';
    printf("removing newline from time_string\n");
  }
  printf("time string: %s\n", time_string);

      i = users[index].num_msgs;
	users[index].num_msgs = users[index].num_msgs + 1;

	 strcpy(users[index].messages[i].message, message);
	 strcpy(users[index].messages[i].from, sender);
	 strcpy(users[index].messages[i].date, time_string); 




}

/*
   handle_list

   Parameters:
      connection: the network connection used to communicate with the client
      users: array of struct user elements representing the users of the
         system
      num_users: the number of users -- entries 0 to num_users - 1 in the
         users array represent the users of the system

   Handle a LIST request from the client.
*/
void handle_list(CONNECTION_ID connection, struct user users[], int num_users)
{
}

/*
   handle_delete

   Parameters:
      connection: the network connection used to communicate with the client
      users: array of struct user elements representing the users of the
         system
      num_users: the number of users -- entries 0 to num_users - 1 in the
         users array represent the users of the system

   Handle a DELETE request from the client.
*/
void handle_delete(CONNECTION_ID connection, struct user users[], int num_users)
{
}

/*
   handle_connection

   Parameters:
      connection: the network connection used to communicate with the client
      users: array of struct user elements representing the users of the
         system
      num_users: the number of users -- entries 0 to num_users - 1 in the
         users array represent the users of the system

   Handle a client connection by dispatching to a request-specific handler
   function.
*/
void handle_connection(CONNECTION_ID connection, struct user users[],
   int num_users)
{
   char string[STR_LEN];
   int choice;

   if (read_string(connection, string, STR_LEN) == NULL)
   {
      fprintf(stderr, "empty request\n");
   }
   sscanf(string, "%d", &choice);

   switch (choice)
   {
      case SEND:
         handle_send(connection, users, num_users);
         break;
      case LIST:
         handle_list(connection, users, num_users);
         break;
      case DELETE:
         handle_delete(connection, users, num_users);
         break;
      default:
         fprintf(stderr, "invalid request\n");
   }
}

int get_port()
{
   int port;

   printf("Please enter the port on which to listen: ");
   scanf("%d", &port);

   return port;
}

SERVICE_ID initialize_service(int port)
{
   SERVICE_ID service_id = create_service(port);

   if (service_id == SERVICE_FAILURE)
   {
      perror("unable to initialize service");
      exit(-1);
   }

   return service_id;
}

void run_service(int port, struct user users[], int num_users)
{
   SERVICE_ID service_id = initialize_service(port);
   CONNECTION_ID connection;

   printf("Listening on port %d ...\n", port);

   while ((connection = accept_connection(service_id)) != CONNECTION_FAILURE)
   {
      handle_connection(connection, users, num_users);
      close_connection(connection);
   }
}

int main(void)
{
   struct user users[MAX_USERS];
   int num_users;
   FILE *user_file = get_user_file();

   num_users = get_users(users, user_file);

   fclose(user_file);

   run_service(get_port(), users, num_users);

   return 0;
}

