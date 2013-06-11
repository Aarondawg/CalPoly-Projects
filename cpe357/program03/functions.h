/* Garrett Milster
 *
 * this file contains the Node struct used by both hencode and hdecode
 * as well as the functions they share.
 */
#define CHAR_LEN 257

typedef struct linkedlist Node;
struct linkedlist {
	int c;
	int count;
   	Node *left;
	Node *right;
	Node *next;
}; 

Node* build_list(int char_list[CHAR_LEN], int int_list[CHAR_LEN], int index);
Node* build_tree(Node *head);
void free_nodes(Node* root);
void bubbleSort(int int_list[], int char_list[], int index);
int open_file(char *filename, int status);
Node* safe_malloc(int size);
void build_table(Node *root, int index, int table[CHAR_LEN][CHAR_LEN]);


