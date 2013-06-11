/* contains:
 * a pointer to a string
 * the number of times the word appears in all the files
 * a reference to the next node */
typedef struct linkedlist Node;
struct linkedlist {
	char *word;
	int count;
   Node *next;
}; 