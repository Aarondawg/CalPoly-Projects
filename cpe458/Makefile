CC   = gcc

CFLAGS  = -Wall -O2

matmult_double:
	$(CC) $(CFLAGS) -DDOUBLE -o matmult matmult.c

matmult_float:
	$(CC) $(CFLAGS) -DSINGLE -o matmult matmult.c
clean:
	rm matmult
