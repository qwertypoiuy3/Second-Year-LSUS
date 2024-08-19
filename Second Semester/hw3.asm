; Karin Galicia Hernandez

.586
.MODEL FLAT

INCLUDE io.h; header file for input / output

.STACK 4096

.DATA
number1 DWORD	?
number2 DWORD   1
number3 DWORD	0
prompt1 BYTE    "Enter the value of n: ", 0
string  BYTE    40 DUP(? )
resultLbl BYTE  "The sum of the number series is: ", 0
sum     BYTE    11 DUP(? ), 0

.CODE
_MainProc PROC
		input   prompt1, string, 40; read ASCII characters
		atod    string; convert to integer
		mov     number1, eax; store in memory

		mov		ecx, number1

		myLoop:
		mov		ebx, number2
		imul	ebx, number2
		add		number2, 1
		add		number3, ebx

		loop myLoop

		mov     eax, number3


		dtoa	sum, eax
		output	resultLbl, sum		


		mov     eax, 0; exit with return code 0
		ret
_MainProc ENDP
END; end of source code

