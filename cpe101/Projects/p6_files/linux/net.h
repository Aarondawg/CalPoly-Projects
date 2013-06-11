#include <stdio.h>

#ifndef NETH
#define NETH

#define SERVICE_FAILURE -1
#define CONNECTION_FAILURE -1

typedef int SERVICE_ID;
typedef int CONNECTION_ID;

SERVICE_ID create_service(short port);
CONNECTION_ID accept_connection(SERVICE_ID service_id);

char *read_string(CONNECTION_ID, char *, int);
void write_string(CONNECTION_ID, char *);

void verify_host(char *host);
CONNECTION_ID connect_to_server(char *host, int port);
void close_connection(CONNECTION_ID connection);
void initialize_network();

#endif
