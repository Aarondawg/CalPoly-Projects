CC = gcc

CFLAGS = -Wall -g -fpic
DFLAGS = -Wall -g
LD = gcc

LDFLAGS =

malloc: malloc.c
	$(CC) $(CFLAGS) -c -o malloc.o malloc.c
	$(CC) $(CFLAGS) -shared -o libmalloc.so malloc.o
	ar r libmalloc.a malloc.o
	ranlib libmalloc.a

tryme: tryme.c
	$(CC) $(DFLAGS) -c -o tryme.o tryme.c
	$(CC) -L . -lmalloc -o tryme tryme.o

clean: 
	rm -f malloc.o *~ TAGS
