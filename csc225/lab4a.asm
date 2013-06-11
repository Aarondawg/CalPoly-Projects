	.orig x3030
	
LOOP	LDI R2, DSR  ; prints first inital
	BRzp LOOP
	STI R0, DDR

LOOP2	LDI R2, DSR ; prints second initial
	BRzp LOOP2
	STI R1, DDR
	
	RET

DSR	.FILL xFE04 ; DSR memory address
DDR	.FILL xFE06 ; DDR memory address

	.end