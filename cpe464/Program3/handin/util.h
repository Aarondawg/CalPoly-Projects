typedef struct window_data{
   int sequence_num;
   unsigned char* data;
   int eof;
   int count;
}window;
int open_socket();
int select_call();
void bubbleSort(struct window_data* buffer, int window_size);
unsigned char* build_ack(char ack, int seq);

