typedef struct WordList Word;
struct WordList{
int linenumber;
int wordnumber;
char line[100];
Word *next;
};
