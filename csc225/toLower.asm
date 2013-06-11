.orig x3000

	input getc
	AND R1, R1, #0
	ADD R1, R1, #-15
	ADD R1, R1, #-15
	ADD R1, R1, #-15
	ADD R1, R1, #-15
	ADD R1, R1, #-15
	ADD R1, R1, #-15
	ADD R1, R1, R0
	BRp output
	ADD R0, R0, #15
	ADD R0, R0, #15
	ADD R0, R0, #2
	OUT
	BRnzp input
	
	output ADD R0, R0, #0

	HALT
.END

count .FILL xFFA6