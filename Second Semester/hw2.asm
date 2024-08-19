; Karin Galicia Hernandez

.586
.MODEL FLAT

INCLUDE io.h; header file for input / output

.STACK 4096

.DATA
length1 DWORD ?
width1  DWORD ?
height DWORD ?
first  DWORD	0
second DWORD	0
third  DWORD 	0
prompt1 BYTE    "Length of the box?", 0
prompt2 BYTE    "Width of the box?", 0
prompt3 BYTE    "Height of the box?", 0
string  BYTE    40 DUP(? )
resultLbl BYTE  "The box surface area is: ", 0
sum     BYTE    11 DUP(? ), 0

.CODE
_MainProc PROC
		input   prompt1, string, 40; read ASCII characters
		atod    string; convert to integer
		mov     length1, eax; store in memory

		input   prompt2, string, 40; repeat for second number
		atod    string
		mov     width1, eax

		input   prompt3, string, 40
		atod    string
		mov     height, eax

		mov     eax, length1
		imul    eax, width1
		mov		first, eax

		mov     eax, length1
		imul	eax, height
		mov		second, eax

		mov		eax, width1
		imul	eax, height
		mov		third, eax

		mov		eax, first
		add		eax, second
		add		eax, third
		imul		eax, 2
		dtoa	sum, eax
		output	resultLbl, sum		


		mov     eax, 0; exit with return code 0
		ret
_MainProc ENDP
END; end of source code

