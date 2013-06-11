CC = gcc

CFLAGS = -Wall -ansi -pedantic

LD = gcc

LDFLAGS =

uniq: 
	$(CC) $(CFLAGS) -o uniq uniq.c

test: uniq
	echo Testing uniq
	./uniq < test.txt
	echo done.
