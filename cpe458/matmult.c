// Garrett Milster
// DJ Mitchell
// CPE 458 Lab1

#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>

// Type changes depending on whether or not the DOUBLE/SINGLE flag is specified
#ifdef DOUBLE
typedef double mytype_t;
#else
typedef float mytype_t;
#endif

mytype_t** parseFile (char *buffer);
mytype_t** multiply(mytype_t** m1, mytype_t** m2);
void writefile(mytype_t** matrix);

int row1 = 0,row2 = 0,cols1 = 0,cols2 = 0;

int main (int argc, char* argv[])
{
     int fd1, fd2;
     struct stat stats1, stats2;
     char *bufferOne;
     char *bufferTwo;
     mytype_t **result;
     mytype_t **m1;
     mytype_t **m2;

     int j = 0;

     if(argc != 3)
     {
          perror("incorrect number of arguments");
     }
    
     if ( (fd1 = open(argv[1], O_RDONLY)) < 0 ){
          printf("Can't open %s for reading", argv[1]);
          return -1;
     }
     if ( (fd2 = open(argv[2], O_RDONLY)) < 0 ){
          printf("Can't open %s for reading", argv[2]);
          return -1;
     }
     if (fstat(fd1, &stats1) < 0){
          printf("fstat failed on file 1");
          return -1;
     }
     if (fstat(fd2, &stats2) < 0){
          printf("fstat failed on file 2");
          return -1;
     }
     if ( (bufferOne = mmap(NULL, stats1.st_size, PROT_READ | PROT_WRITE, MAP_PRIVATE, fd1, 0)) == (void *)-1 ){
          printf("mmap error for first file");    
          return -1;
     }
     if ( (bufferTwo = mmap(NULL, stats2.st_size, PROT_READ | PROT_WRITE, MAP_PRIVATE, fd2, 0)) == (void *)-1 ){
          printf("mmap error for second file");
          return -1;
     }

     // Parse both files into 2-D arrays
     m1 = parseFile(bufferOne);
     m2 = parseFile(bufferTwo);
     
     if(cols1 != row2)
     {
          printf("Dimensions of matrices are not valid for matrix multiplication\n");
          return -1;
     }

     // Matrices are valid, multiply them
     result = multiply(m1, m2);

     // Write to file
     writefile(result);

     // Free allocated memory before we         fprintf(file, "%.2f ",matrix[i][j]); exit
     for (j = 0; j < row1; j++) 
          free(result[j]);   
     free(result);
     
     for (j = 0; j < row1; j++)
          free(m1[j]);
     free(m1);

     for (j = 0; j < row2; j++)
          free(m2[j]);
     free(m2);

     return 0;
}

mytype_t** parseFile (char *buffer)
{
     int cols = 0;
     int rows = 0;
     int i = 0, j = 0;
     char seps[3] = {' ', '\n', '\0'};
     char* token;
     mytype_t var;
     mytype_t **matrix;

     while(buffer[i] != '\n')
     {
          if(buffer[i] == ' ')
          {
               cols++;
          }
          i++;
     }
     i = 0;
     while(buffer[i] != '\0')
     {
          if(buffer[i] == '\n')
          {
               rows++;
          }
          i++;
     }
     
     if(cols1 == 0)
     {
         // printf("ROWS/COLS #1: %d/%d\n", rows, cols);
          cols1 = cols;
          row1 = rows;
     }
     else
     {
         // printf("ROWS/COLS #2: %d/%d\n", rows, cols);
          cols2 = cols;
          row2 = rows;
     }
     matrix = (mytype_t**) malloc(sizeof(mytype_t*) * rows);
     for (i = 0; i < rows; i++)
          matrix[i] = (mytype_t*) malloc(sizeof(mytype_t) * cols);

     token = strtok (buffer, seps);

     for (i = 0; i < rows; i++) 
     {
          for (j = 0; j < cols; j++)
          {
               // If we are using doubles scan in with %lf, else use %f
               #ifdef DOUBLE
               sscanf (token, "%lf ", &var);
               #else
               sscanf (token, "%f ", &var);
               #endif
               matrix[i][j] = var;
               token = strtok (NULL, seps);
          }
     }

     return matrix;
}

// Multiplies the first matrix by the second
mytype_t** multiply(mytype_t** m1, mytype_t** m2)
{
     mytype_t **tempMatrix;
     int i = 0, j = 0, k = 0;

     tempMatrix = (mytype_t**) malloc(sizeof(mytype_t*) * row1);

     for (i = 0; i < row1; i++)
     {
          tempMatrix[i] = malloc(sizeof(mytype_t) * cols2);
     }

     // Initialize to 0
     for (i = 0; i < row1; i++)
     {
          for (j = 0; j < cols2; j++)
          {
               tempMatrix[i][j] = 0.0;               
          }
     }
     
     for(i = 0; i < row1; i++)
     {
          for(j = 0; j < cols2; j++)
          {
               for (k = 0; k < row2; k++)
                    tempMatrix[i][j] += m1[i][k] * m2[k][j];
          }
     }  

     return tempMatrix;  
}

// Write the given matrix to a file called result.out
void writefile(mytype_t **matrix)
{
    int i,j;
    
    FILE* file = fopen("resultant.txt","w");
    for(i = 0; i < row1; i++)
    {
      for(j = 0; j < cols2; j++)
      {
         fprintf(file, "%.2f ",matrix[i][j]);
      }
         fprintf(file, " \n");
     // write(fd,  end, 2);

    }
    
    fclose(file);
}
