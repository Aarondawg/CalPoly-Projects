	.ORIG   x3000
	; display question 1
	AND R2, R2, #0
	LD     R0,Q1 ; Question1
	PUTS
	LEA R6, #1
	JSR GETANS
	LD R1, A1
	LEA R6, #1
	JSR POINTS

	LD     R0,Q2 ; Question2
	PUTS
	LEA R6, #1
	JSR GETANS
	LD R1, A2
	LEA R6, #1
	JSR POINTS

	LD     R0,Q3 ; Question3
	PUTS
	LEA R6, #1
	JSR GETANS
	LD R1, A3
	LEA R6, #1
	JSR POINTS

	ADD R2, R2, #-3 ; chooses result
	BRnz Re1
	ADD R2, R2, #-3
	BRnz Re2
	ADD R2, R2, #-3
	BRnz Re3
	BRnzp Re4

Re1	LD R0, Res1   ; prints result
	PUTS
	HALT
Re2	LD R0, Res2
	PUTS
	HALT
Re3	LD R0, Res3
	PUTS
	HALT
Re4	LD R0, Res4
	PUTS
	HALT
	
Q1	.FILL   x3300 
A1	.FILL   x3364
Q2	.FILL 	x3368
A2	.FILL	x33C9
Q3	.FILL	x33CD
A3	.FILL	x3436
Res1	.FILL	x343A
Res2	.FILL	x3451
Res3	.FILL	x346F
Res4	.FILL	x3494
answer	.STRINGZ "Answer: "

GETANS	LEA R0,answer ; gets answer
	PUTS
	GETC
	OUT
	ADD R0, R0, #-16
	ADD R0, R0, #-16
	ADD R0, R0, #-16
	ADD R7, R6, #0
	RET

POINTS	ADD R1, R1, R0 ; calculates total points
	LDR R1, R1, #0
	ADD R2, R2, R1
	ADD R7, R6, #0
	RET
	
	.END