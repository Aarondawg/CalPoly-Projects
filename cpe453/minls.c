/* minls.c
 * Garrett milster and DJ Mitchell
 *
 * minls lists a file or directory on the given filesystem image
*/


#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <stdint.h>
#include <sys/types.h>
#include <sys/stat.h>
#define KB 1024
#define SECTOR 512
 typedef struct part_entry {
   unsigned char bootind;        /* boot indicator 0/ACTIVE_FLAG  */
   unsigned char start_head;     /* head value for first sector   */
   unsigned char start_sec;      /* sector value + cyl bits for first sector */
   unsigned char start_cyl;      /* track value for first sector  */
   unsigned char sysind;         /* system indicator              */
   unsigned char last_head;      /* head value for last sector    */
   unsigned char last_sec;       /* sector value + cyl bits for last sector */
   unsigned char last_cyl;       /* track value for last sector   */
   uint32_t lowsec;         /* logical first sector          */
   uint32_t size;           /* size of partition in sectors  */
 }partitionEntry;

typedef struct super_block {
  uint32_t s_ninodes;       /* # usable inodes on the minor device */
  uint16_t s_nzones;        /* total device size, including bit maps etc */
  uint16_t s_imap_blocks;      /* # of blocks used by inode bit map */
  uint16_t s_zmap_blocks;      /* # of blocks used by zone bit map */
  uint16_t s_firstdatazone_old;  /* number of first data zone (small) */
  uint16_t s_log_zone_size;
  uint16_t s_flags;   /* FS state flags */
  uint32_t s_max_size;     /* maximum file size on this device */
  uint32_t s_zones;       /* number of zones (replaces s_nzones in V2) */
  uint16_t s_magic;        /* magic number to recognize super-blocks */

  /* The following items are valid on disk only for V3 and above */

  /* The block size in bytes. Minimum MIN_BLOCK SIZE. SECTOR_SIZE
   * multiple. If V1 or V2 filesystem, this should be
   * initialised to STATIC_BLOCK_SIZE.
   */
  uint16_t s_pad2;         /* try to avoid compiler-dependent padding */
  uint16_t s_block_size;  /* block size in bytes. */
  char s_disk_version;      /* filesystem format sub-version */
} superblock;

typedef struct inode {
    uint16_t i_mode;                /* file type, protection, etc. */
    uint16_t i_nlinks;             /* how many links to this file */
    uint16_t i_uid;                  /* user id of the file's owner */
    uint16_t i_gid;                  /* group number */
    uint32_t i_size;                 /* current file size in bytes */
    uint32_t i_atime;               /* time of last access (V2 only) */
    uint32_t i_mtime;               /* when was file data last changed */
    uint32_t i_ctime;               /* when was inode itself changed (V2 only)*/
    uint32_t i_zone[7]; /* zone numbers for direct, ind, and dbl ind */
    uint32_t i_indirect;
    uint32_t i_double;
    uint32_t padding;
}  inode;

void validatePartition(int num, uint32_t start, char* block);
int parseInode(int index, int pathNumber, char* buffer);
int parseZone(int index, int zoneOffset, int pathNumber, char* buffer);
void parseEnd(int zoneOffset, char* buffer);
void printInode(int index,char* buffer,char* name);
char* printMode(uint16_t mode);
// void readSuperBlock(int start, char* block,superblock* s);

uint32_t totalSize = 0;
int fileIndex = 0;
inode * curInode;
int verbose = 0;
partitionEntry *p;
struct stat filestats;
char *tokenizedPath[100];
char *tempPath;
int zoneSize;
int onlyroot = 0;
int topInode;
char* currentName;
int countOfDirectoriesInPath = 0;
char* path;
char* fullPath;

int main(int argc, char* argv[])
{
    int partition = -1;
    int subpartition = -1;
    char* filename;
    int fd;
    char *top;
    int pathNumber;
    int i;
    uint32_t nextInodeNumber;
    p = (partitionEntry*) malloc(sizeof(partitionEntry)); 
    superblock* s = (superblock*) malloc(sizeof(superblock));
    char *indirectZone;
    if(argc < 2 || argc > 8)
    {
        printf("usage: minls [ -v ] [ -p num [ -s num ] ] imagefile [ path ]" 
                "\nOptions:\n-p part --- select partition for filesystem "
                "(default: none)\n-s sub --- select partition for filesystem"
               " (default: none)\n-v verbose --- increase verbosity level\n");

        exit(-1);

    }

    // parses command line
    if(strcmp(argv[1],"-v") == 0)
    {
        verbose = 1;
        if(strcmp(argv[2],"-p") == 0)
        {
            partition = atoi(argv[3]);
            if(strcmp(argv[4],"-s") == 0)
            {
                subpartition = atoi(argv[5]);
                filename = argv[6];
                path = argv[7]; 
                fullPath = argv[7];
            }
            else
            {
                filename = argv[4];
                path = argv[5]; 
                fullPath = argv[5];
            }
        }
        else
        {
            filename = argv[3];
            path = argv[4]; 
            fullPath = argv[4];
        }
    }
    else if(strcmp(argv[1],"-p") == 0)
    {
        partition = atoi(argv[2]);
        if(strcmp(argv[3],"-s") == 0)
        {
            subpartition = atoi(argv[4]);
            filename = argv[5];
            path = argv[6]; 
            fullPath = argv[6];
        }
        else
        {
            filename = argv[3];
            path = argv[4]; 
            fullPath = argv[4];
        }
    }  
    else
    {
        filename = argv[1];
        path = argv[2]; 
        fullPath = argv[2]; 

    } 
    // Must print before we do strtok because it is destructive
    //  so path variable will change

    //printf("FULLPATH: %s\n",fullPath);
    if(path != NULL)
    {
        tempPath = malloc(strlen(path) + 1);
        strcpy(tempPath, path);

    
        if (path[strlen(path) - 1] == '/')
            path[strlen(path) - 1] = '\0';

        if (path[0] != '/')
            countOfDirectoriesInPath++;
        
        for (i = 0; i < strlen(path) ; i++)
            if (path[i] == '/')
                countOfDirectoriesInPath++;
        tokenizedPath[0] = strtok(path, "/");
        //printf("Path[0]: %s\n",tokenizedPath[0]); 
        for (i = 1; i < countOfDirectoriesInPath; i++)
        {
            tokenizedPath[i] = strtok(NULL, "/");
            //printf("Path[%d]: %s\n", i, tokenizedPath[i]); 
        }
    }
    else
    {
        printf("/:\n");
        countOfDirectoriesInPath++;
        onlyroot = 1;
    }

    if(verbose > 0)
    {
        //printf("Verbose flag turned on...\n");
    }
    if(partition != -1)
    {
        //printf("partition: %d\n",partition);
        if(subpartition != -1)
        {
            //printf("subpartition: %d\n",subpartition);
        }

    }

    int f;
    if((fd = open(filename, O_RDONLY, S_IRWXU)) < 0)
    {
         perror("open1");
         return -1;
    }

    fstat(fd, &filestats);
    top = (char*) malloc(sizeof(char) * filestats.st_size);

    if((f = read(fd, top, filestats.st_size)) != filestats.st_size)
    {
       perror("Read");
       exit(EXIT_FAILURE);
    }


    if(partition != -1)
    {
        if(partition > 3 || partition < 0)
        {
            perror("there are only 4 partition tables,"
                    " please specify a valid entry");
            exit(-1);
        }

        validatePartition(partition,0, top);
        
        
        if(subpartition != -1)
        {
            if(subpartition > 3 || subpartition < 0)
            {
                perror("there are only 4 subpartition tables per"
                        " partition table, please specify a valid entry");
                exit(-1);
            }
            validatePartition(subpartition, p->lowsec*SECTOR, top);
        }

        if(((unsigned char)top[510] != 0x55) 
                || ((unsigned char)top[511] != 0xAA))
        {
            perror("Invalid partition table");
            exit(-1);
        }
        fileIndex = p->lowsec*SECTOR;
        // Get it to the superblock
        
        s = (superblock*) &top[fileIndex+KB];
        //sets ptr to top of inode area
        topInode = fileIndex + (s->s_block_size * 2)
            + (s->s_imap_blocks * s->s_block_size)
            + (s->s_zmap_blocks * s->s_block_size);
    }
    else
    {
        fileIndex = 0;
        s = (superblock*) &top[KB];
        //Get it to the iNodes
        topInode = (s->s_block_size * 2)
            + (s->s_imap_blocks * s->s_block_size)
            + (s->s_zmap_blocks * s->s_block_size);
    }

    if(verbose > 0){    
        fprintf(stderr, "\n READING SUPERBLOCK \n");
    
    fprintf(stderr, "Number of inodes: %hu\n", (unsigned short)s->s_ninodes);
    fprintf(stderr, "i_blocks: %hd\n", s->s_imap_blocks);
    fprintf(stderr, "z_blocks: %hd\n", s->s_zmap_blocks);
    fprintf(stderr, "First Data: %hu\n", s->s_firstdatazone_old);
    fprintf(stderr, "s_log_zone_size: %hd\n", s->s_log_zone_size);
    fprintf(stderr, "max_file: %lu\n", (unsigned long)s->s_max_size);
    fprintf(stderr, "Magic number: %x\n",s->s_magic);
    fprintf(stderr, "zones: %u\n", s->s_zones);
    fprintf(stderr, "Block Size: %hu\n", s->s_block_size);
    fprintf(stderr, "sub-version: %d\n", (int)s->s_disk_version);}
    if(s->s_magic != 0x4d5a)
    {
        printf("Bad magic number. (%x)\n"
                "This doesn’t look like a MINIX filesystem.\n", s->s_magic);
        exit(EXIT_FAILURE);
    }
    //printf("FILE INDEX: %d\n",fileIndex);
    // This calculates the size of the zone and saves it
    zoneSize = s->s_block_size << s->s_log_zone_size;

    // Need a special case if root is the directory to list 
   
    // This loop will iterate through each dir in the provided path
    //  and get the next inode number, then grab the zone offset
    //  from the inode, and repeat 
    nextInodeNumber = -2;

    for (pathNumber = 0; pathNumber < countOfDirectoriesInPath; pathNumber++)
    {
        if(nextInodeNumber == -2)
        {
            nextInodeNumber = parseInode( (topInode), pathNumber, top);
        }
        else
        {
            nextInodeNumber = parseInode(topInode + (64*(nextInodeNumber-1)),
                    pathNumber, top);

        }
        if (nextInodeNumber == -1) {
            printf("DIRECTORY NOT FOUND\n");
            exit(EXIT_FAILURE);
        }

    }
    if(onlyroot == 1)
    {
        return 0;
    }
    int j;
    inode* t = (inode*) &top[topInode + (64*(nextInodeNumber-1))];
    if(S_ISDIR(t->i_mode))
    {
        fprintf(stdout, "%s:\n",tempPath);
        for(j = 0; j < 7; j++)
        {
            //printf("zone[%d]:    %u\n", j, t->i_zone[j]);
            if (t->i_zone[j] != 0 && onlyroot == 0)
            {
                parseEnd(t->i_zone[j], top);
            }
        }
        if (t->i_indirect != 0)
        {
            indirectZone = &top[fileIndex + (t->i_indirect * zoneSize)];
            for (j = 0; j < zoneSize/sizeof(uint32_t); j++)
            {
                if (indirectZone[j*sizeof(uint32_t)] != 0) {
                    parseEnd(indirectZone[j*sizeof(uint32_t)], top);
                }
            }
        }
    }
    else
    {
        char* mode = printMode(t->i_mode);
        if (verbose == 1)
        {
            fprintf(stderr, "\nParsing Inode: \nmode: %s\n",mode);
            fprintf(stderr, "num links: %hd\n",t->i_nlinks);
            fprintf(stderr, "uid: %hd\n",t->i_uid);
            fprintf(stderr, "gid: %hd\n",t->i_gid);
            fprintf(stderr, "File Size: %u\n", t->i_size);
            fprintf(stderr, "a-time: %u\n",t->i_atime);
            fprintf(stderr, "m-time: %u\n",t->i_mtime);
            fprintf(stderr, "c-time: %u\n\n",t->i_ctime);
        }
        printf("%s     %u %s\n",mode,t->i_size,tempPath);

        
    }
    return 0;
}

void validatePartition(int num, uint32_t start, char* block)
{
    int offset = 0x1BE;
    offset+= start;
    int  i;
    int increment = sizeof(partitionEntry);

    for(i = 0; i < 4; i++)
    {
        if(i == num)
        {
            p = (partitionEntry*) &block[offset];
        }

        offset+=increment;
    }
}
// breaks down mode variable and builds string for printing
char* printMode(uint16_t mode)
{
    char* string = (char*)malloc(11);
    if(S_ISDIR(mode))
    {
        string[0] = 'd';
    }
    else
    {
        string[0] = '-';
    }

    // owner permissions

    if((0000400 & mode) == 00000400)
    {
        string[1] = 'r';
    }
    else
    {
        string[1] = '-';
    }
    if((0000200 & mode) == 0000200)
    {
        string[2] = 'w';
    }
    else
    {
        string[2] = '-';
    }
    if((0000100 & mode) == 0000100)
    {
        string[3] = 'x';
    }
    else
    {
        string[3] = '-';
    }
    if((0000040 & mode) == 0000040)
    {
        string[4] = 'r';
    }
    else
    {
        string[4] = '-';
    }
    if((0000020 & mode) == 0000020)
    {
        string[5] = 'w';
    }
    else
    {
        string[5] = '-';
    }
    if((0000010 & mode) == 0000010)
    {
        string[6] = 'x';
    }
    else
    {
        string[6] = '-';
    }
    if((0000004 & mode) == 0000004)
    {
        string[7] = 'r';
    }
    else
    {
        string[7] = '-';
    }
    if((0000002 & mode) == 0000002)
    {
        string[8] = 'w';
    }
    else
    {
        string[8] = '-';
    }
    if((0000001 & mode) == 0000001)
    {
        string[9] = 'x';
    }
    else
    {
        string[9] = '-';
    }
    string[10] = '\0';
    return string;
}

// parses inode zones and indirect/double zones
int parseInode(int index, int pathNumber, char* buffer)
{
    int j, k, ret = -1;
    inode* i = (inode*) malloc(sizeof(inode));
    i = (inode*) &buffer[index];
    curInode = i;
    char *indirectZone;
    char *doubleIndirZone;
    //char* mode = printMode(i->i_mode);

    for(j = 0; j < 7; j++)
    {
        //printf("zone[%d]:    %u\n", j,i->i_zone[j]);
        if (i->i_zone[j] != 0 && onlyroot == 0)
        {
            ret = parseZone(index, i->i_zone[j], pathNumber, buffer); 
            if (ret != -1)
                break;
        }
        else if(i->i_zone[j] != 0 && onlyroot == 1) // special case for root
        {
            parseEnd(i->i_zone[j], buffer);
            ++ret; 
        }
    }

    if (i->i_indirect != 0)
    {
        indirectZone = &buffer[fileIndex + (i->i_indirect * zoneSize)];
        for (j = 0; j < zoneSize/sizeof(uint32_t); j++)
        {
            ret = parseZone(index, indirectZone[j*sizeof(uint32_t)],
                    pathNumber, buffer);
            if (ret != -1)
                break;
        }
    }
    if (i->i_double != 0)
    {
        indirectZone = &buffer[fileIndex + (i->i_double * zoneSize)];
        for (j = 0; j < zoneSize/sizeof(uint32_t); j++)
        {
            doubleIndirZone = &buffer[fileIndex +
                (indirectZone[j] * zoneSize)];
            for (k = 0; k < zoneSize/sizeof(uint32_t); k++)
            {
                ret = parseZone(index, doubleIndirZone[k*sizeof(uint32_t)],
                    pathNumber, buffer);
                if (ret != -1)
                    break;
            }
        }
    }

    return ret;
}

// If it does not find the dir it is looking for
//  it returns -1, otherwise it returns next inode number
int parseZone(int index, int zoneOffset, int pathNumber, char* buffer)
{
    char *zone;
    int i;
    uint32_t iNodeNumber = -1;
    // Calculate offest to this zone you need
    zone = &buffer[fileIndex + (zoneOffset * zoneSize)];
    char* name = (char*) malloc(61);
    name[60] = '\0';
    // Want to iterate through each of the dir files in this zone
    //  zonesize/64 assumes that each zone has only one dir file
    //  should this be blocksize/64? 
    for (i = 0; i < zoneSize/64; i++)
    {
        //printf("possible ENTRY: %s\n", &zone[i*64 + 4]);

        // If the string in dir file matches tokenized path, return node #
        if (strcmp(&zone[i * 64 + 4], tokenizedPath[pathNumber]) == 0)
        {
            //printf("CORRECT ENTRY: %s\n", &zone[i*64+4]);
            memcpy(&iNodeNumber, &zone[i*64], 4); 
            memcpy(&name[0], &zone[i*64+4], 60); 
            currentName = name;
            //printf("INODE: %u\n",iNodeNumber);
            // Convert first 4 bytes of dir entry into integer
            //iNodeNumber = atoi(correctEntry);
            if (iNodeNumber == 0)
                exit(EXIT_FAILURE);
            
            return iNodeNumber;
        }
    }

    return iNodeNumber;
}
void parseEnd(int zoneOffset, char* buffer)
{
    char *zone;
    int i, iNodeNumber = -1;
    // Calculate offest to this zone you need
    zone = &buffer[fileIndex + (zoneOffset * zoneSize)];
    char* name = (char*) malloc(61);
    name[60] = '\0';
    // Want to iterate through each of the dir files in this zone
    //  zonesize/64 assumes that each zone has only one dir file
    for (i = 0; i < zoneSize/64; i++)
    {

        //printf("CORRECT ENTRY: %s\n", &zone[i*64+4]);
        memcpy(&iNodeNumber, &zone[i*64], 4); 
        memcpy(&name[0], &zone[i*64+4], 60); 
        printInode(iNodeNumber,buffer,name);

    }
 
}

//prints the inode based on index
void printInode(int index,char* buffer,char* name)
{
    inode* i = (inode*) &buffer[topInode + (64*(index-1))];
    if (i->i_mode != 0 && (name != NULL || strlen(name) != 0)) {
        char* mode = printMode(i->i_mode);
        if(fullPath == NULL)
        {
             printf("%s     %u %s\n",mode,i->i_size,name);

        }
        else
        {
            if(path[strlen(path) - 1] != '/')
            {
                printf("%s     %u %s\n",mode,i->i_size,name);

            }
            else
            {
                printf("%s     %u %s%s\n",mode,i->i_size,tempPath,name);

            }


        }
    }
}
