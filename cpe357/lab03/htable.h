/* contains:
 * a character
 * the number of times the character appears in the input file
 * a reference to a right node and a left node */
typedef struct linkedlist Node;
struct linkedlist {
	int c;
	int count;
   	Node *left;
	Node *right;
	Node *next;
}; 
