#define BLOCK 512
#define MAX_PATH 256
#define ASCII_SPACE ' '
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <fcntl.h>
#include <grp.h>
#include <pwd.h>
#include <string.h>
#include <unistd.h>
#include <sys/time.h>
#include <time.h>
#include <utime.h>
#include <dirent.h>

int build_dir_tree(char *, mode_t, char *);
int extract(char *names[], int arrSize, int flag, int fd);
