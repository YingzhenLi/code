
;��������
EXITKEY = 1BH                                                     ;�˳���ESC��ASCII��
ENTERKEY= 0DH                                                     ;�س���ENTER��ASCII��
ARROW   = 18H                                                     ;��ͷ��ASCII��
RED     = 0CH                                                     ;��ɫ
WHITE   = 0FH                                                     ;��ɫ
ROWPLATE= 11H                                                     ;��������
ROW     = 13H                                                     ;������
COLA    = 19H                                                     ;A����������
COLB    = 23H                                                     ;B����������
COLC    = 35H                                                     ;C����������
dCOL    = 0EH                                                     ;�������
TOP     =4                                                        ;���Ͻ�������
BUTTOM  =19                                                       ;���½�������
LEFT    =16                                                       ;���Ͻ�������
RIGHT   =64                                                       ;���½�������
MAXN    =5                                                        ;���Ѻ�ŵ������


PLATE STRUC                                                       ;�������ӽ��ṹ
      nextY    DB  0                                              ;������
      nextX    DB  0                                              ;������
      color    DB  0AH                                            ;��ɫ
      siz      DB  1                                              ;��С
PLATE ENDS

;���ݶ�
DATA SEGMENT
     pillarA   DW   0,25,ROWPLATE,'A',0,5 dup(0)                 ;�������ҷֱ�Ϊ���Ӹ���������x���꣬������ӵ�y���꣬���ӱ�ǣ���������
     pillarB   DW   0,39,ROWPLATE,'B',0,5 dup(0)
     pillarC   DW   0,53,ROWPLATE,'C',0,5 dup(0)
     QHard     DB   'Please input the difficulty coeffient:$'   ;�޸�   Please input the difficulty coeffient:
     Con       DB   'Congratuations!$'
     hard      DB   0                                           ;�Ѷ�ϵ��
     colorBox  DB   5CH,3CH,6CH,7CH,8CH
     From      DB   0                                           ;��������
     To        DB   0                                           ;�ִ�����
     flag      DW   0
     SIZE1     DB   0
     CArrow    DB   COLA
     
DATA ENDS

;�����
CODE SEGMENT        'code'
     ASSUME CS:CODE, DS:DATA
     
START:
      MOV        AX,DATA
      MOV        DS,AX
     
      MOV        CH,3							  ; ��һ����ɫ����
	MOV        CL,15							  ; ���߿�
	MOV        DH,20
	MOV        DL,65
	MOV        BH,4CH
	MOV        AL,0
	MOV        AH,6
	INT        10H
 
      MOV        CH,TOP							 ; ��һ����ɫ����
	MOV        CL,LEFT					       ; ��Ϊ����
	MOV        DH,BUTTOM
	MOV        DL,RIGHT
	MOV        BH,07H
	MOV        AL,0
	MOV        AH,6
	INT        10H

      MOV        CH,5						      ; ����1�ϲ���
	MOV        CL,25
	MOV        DH,18
	MOV        DL,26
	MOV        BH,4CH                                    ;����Ϊ��ɫ
	MOV        AL,0
	MOV        AH,6
	INT        10H

      MOV        CH,17						     ; ����1�²���
	MOV        CL,20
	MOV        DH,18
	MOV        DL,31
	MOV        BH,4CH
	MOV        AL,0
	MOV        AH,6
	INT        10H

      MOV        CH,5						    ; ����2�ϲ���
	MOV        CL,39
	MOV        DH,18
	MOV        DL,40
	MOV        BH,4CH
	MOV        AL,0
	MOV        AH,6
	INT        10H

      MOV        CH,17						    ; ����2�²���
	MOV        CL,34
	MOV        DH,18
	MOV        DL,45
	MOV        BH,4CH
	MOV        AL,0
	MOV        AH,6
	INT        10H

      MOV        CH,5						    ; ����3�ϲ���
	MOV        CL,53
	MOV        DH,18
	MOV        DL,54
	MOV        BH,4CH
	MOV        AL,0
	MOV        AH,6
	INT        10H

      MOV        CH,17						    ; ����3�²���
	MOV        CL,48
	MOV        DH,18
	MOV        DL,60
	MOV        BH,4CH
	MOV        AL,0
	MOV        AH,6
	INT        10H
 
      CALL       setHard                                 ;�����Ѷ�ϵ��
 moving:
      CALL       drawArrow                               ;ѡ���ƶ������� ���ƶ�����
      CALL       checkIFWin                              ;�ж��Ƿ�Ӯ
      JMP        moving
      
 ;===================================================================
 ;�ӳ�������setHard
 ;���ܣ�������Ϸ��ʼ����Ļ
 ;��ڲ����� ��
 ;���ڲ����� ��
 
setHard PROC
      PUSH       SI					        ; �����Ĵ���
	PUSH       AX
	PUSH       BX
	PUSH       CX
	PUSH       DX

      XOR        DX, DX                                 ;����
      XOR        AX, AX
      
CHAR: MOV        DX, OFFSET QHard                       ;������ʾ��䣺�������Ѷ�ϵ��
      MOV        AH, 9
      INT        21H
      MOV        AH, 1                                  ;�����ַ����Ѷ�ϵ������AL
      INT        21H
      SUB        AL, '0'                                ;�ַ�ת��������
      CMP        AL, 1
      JL         CHAR                                   ;����<0���˳�����������Ҫ����������ʾ������
      CMP        AL, 5
      JG         CHAR                                   ;����>6���˳�
      
      MOV        hard, AL                               ;���Ѷ�ϵ��¼�����
      PUSH       AX                                     ;�����Ĵ���
      
enter0:
      MOV        AH, 1                                  ; ����
	INT        16H
      MOV        AH, 0
	INT        16H
      CMP        AL, ENTERKEY			              ; ����ENTER��,�˳�
	JNZ        enter0
 
      POP        AX                                     ;��AX�д���hard

                                                        ;���ܣ���Ϸ��������ʼ��
      XOR        SI, SI                                 ;����
      XOR        BX, BX

      MOV        SI, OFFSET pillarA                    ;SI ָ��pillarA
      MOV        SI[0], AL                             ;�ı�pillarA�ĳ�ʼֵ
      MOV        AL, ROWPLATE
      SUB        AL, SI[0]
      MOV        SI[4], AL
      ADD        SI, 8H                                ;��SIָ��pillarA���������
                                                       ;ѭ��������ʼ���Ӳ���һ����ʼ��pillarA�е�������Ϣ
      XOR        AX, AX
      XOR        BX, BX
      XOR        CX, CX
      XOR        DX, DX

      MOV        CL, hard
loop1:
      PUSH       CX                                    ;�����Ĵ���
                                                       ;�ı�pillarA������Ϣ����ʹSI��ǰ��һ��
      ADD        SI, 2H
      MOV        SI[0], CX

                                                       ;������
      MOV        CH, ROWPLATE                          ;���Ͻ�
      SUB        CH, hard
      SUB        CH, 1H
      ADD        CH, SI[0]
      MOV        CL, COLA
      SUB        CL, SI[0]

	MOV        DH, CH                                ;���½�
	MOV        DL, COLA
      ADD        DL, 1H
      ADD        DL, SI[0]

      MOV        DI, OFFSET colorBox                    ;���ò�ͬ��ɫ
      ADD        DI, SI[0]
      SUB        DI, 1H
      XOR        AX, AX
      MOV        AH, [DI]
      MOV        BH, AH

	MOV        AL,0
	MOV        AH,6
	INT        10H

      POP        CX
      LOOP       loop1
      
      POP        DX                                      ;��ԭ�Ĵ���
      POP        CX
      POP        BX
      POP        AX
      POP        SI

	RET
setHard ENDP
 
;===================================================================
;�ӳ�������checkIFWin
;���ܣ�ˢ�¼����Ƿ��Ѿ��ɹ�
;��ڲ�����
;���ڲ�����

checkIFWin PROC
      PUSH        SI					        ; �����Ĵ���
	PUSH        AX
	PUSH        BX
	PUSH        CX
	PUSH        DX

      XOR         AX, AX                                ;��ʼ
      XOR         SI, SI
      XOR         DX, DX
      XOR         BX, BX
      
      MOV         SI, OFFSET pillarC                    ;�ж��Ƿ�Ӯ
      MOV         BL, hard
      CMP         SI[0],BL
      JE          win
      JMP         nowin
win:  MOV         DX, OFFSET Con                        ;�ж�
      MOV         AH, 9
      INT         21H
      CALL        delay
      MOV         AH, 4CH
      INT         21H
nowin:
      POP         DX					        ; �ָ��Ĵ���
	POP         CX
	POP         BX
	POP         AX
	POP         SI
      RET
 checkIFWin ENDP
 
 ;===================================================================
;�ӳ�������drawArrow
;���ܣ�ˢ�¼����Ƿ��Ѿ��ɹ�
;��ڲ�����   ��
;���ڲ�����   ��
 drawArrow PROC
      PUSH        DI                                    ; �����Ĵ���
      PUSH        AX
      PUSH        BX
	PUSH        CX
	PUSH        DX

      MOV         flag, 1                               ; ȷ����ǰ��ͷģʽ��0��ʾѡ�����ӣ�1��ʾѡ�������ƶ�Ŀ��

      MOV         BH, 0				              ; ���ù��λ��
	MOV         DH, ROW
	MOV         DL, CArrow
	MOV         AH, 2
	INT         10H

      MOV         AL, 18H                               ; �ڹ��λ�û�����Ӧ�ڵ�
	MOV         BL, WHITE
      XOR         CX, CX                                ; ����
	MOV         CX, 1
	MOV         AH, 9
	INT         10H

again0:
      MOV         AH, 1                                 ; ����
	INT         16H
      MOV         AH, 0
	INT         16H

      CMP         AH, 4DH				        ; �Ҽ�ͷ(С���� 6),ת
      JZ          rightmove
      
      CMP         AH, 4BH                               ;���ͷ
      JZ          leftmove

      CMP         AL, ENTERKEY			        ; ����ENTER��,�˳�
	JZ          changemode

      CMP         AL, EXITKEY			              ; ����EXIT��,�˳�
      MOV         AH, 4CH
      INT         21H
           
      JMP         again0				        ; ����,���¶�ȡ
       
rightmove: call drawright                               ;ִ�м�ͷ��ת
           JMP arrowdraw
leftmove:  call drawleft                                ;ִ�м�ͷ��ת
           JMP  arrowdraw

arrowdraw:
	MOV         AL, ARROW                             ; �ڹ��λ�û�����Ӧ�ڵ�
      XOR         CX, CX                                ; ����
	MOV         CX, 1
	MOV         AH, 9
	INT         10H
      JMP         again0

changemode:
      CMP         flag, 1                               ; ȷ����ǰ��ͷ�ƶ�ģʽ
      JE          movestep                              ; ��Ϊȷ������ģʽ���ͷ���ɫ���죩
      MOV         BL, WHITE                             ; ��Ϊȷ������ģʽ���ͷ���ԭ����ɫ���ף�
      MOV         flag, 1
      MOV         To, DL                                ; ��DI��Ϊָ�겢�޸ģ���DIΪ1ʱENTER�����������ƶ�
      JMP         arrowdraw2                            ; �ɿ��ǽ������Ӻ���Ƕ����drawArray,Ҳ�ɽ�CX���ظ��ⲿ�����Ӻ���
                                                        ; ����ѡ�е�pillar(��ַΪ[SI].nextX֮���)�м�¼������ӵı��
                                                        ; ����һ��ȷ������ģʽ�ｫ�ñ�������Ƶ�ѡ�е�pillar���Ѹ��£�

movestep:                                               ; ȷ������ģʽ����ͷ��ɫ���죩
      MOV         BL, RED
      MOV         flag,0                                ;�޸ĵ�ǰģʽָ��
      MOV         From, DL
arrowdraw2:
	MOV         AL, 00H                               ; ԭλ�ü�ͷĨ��
      XOR         CX, CX                                ; ����
	MOV         CX, 1
	MOV         AH, 9
	INT         10H

	MOV         AL, ARROW                             ; �ڹ��λ�û�����ͷ�����ڼ�ͷλ�ò��䲻��Ҫ�������ù��λ��
      XOR         CX, CX
	MOV         CX, 1
	MOV         AH, 9
	INT         10H
      CMP         flag, 1
      JZ          drawpl2
      JMP         again0

drawpl2:
      CALL        FindPillar                             ;�жϲ����Ƿ�Ϸ�
      CMP         AH, 1H
      JB          again0
      CALL        drawPlate                              ;�Ϸ�������
exit:
      MOV         CArrow, DL
      POP         CX                                    ; �Ĵ������ݻ�ԭ�������浱ǰ���λ��
      POP         AX
      POP         DI
	POP         BX
	POP         DX
      RET
 drawArrow ENDP
 ;===================================================================
;�ӳ�������drawright
;���ܣ�ˢ�¼����Ƿ��Ѿ��ɹ�
;��ڲ�����    ��
;���ڲ�����    ��

drawright PROC

	MOV         AL, 00H                                ; ԭλ�ü�ͷĨ��
	MOV         AH, 9
	INT         10H

      CMP         DL, COLC                               ; �жϹ���Ƿ���C��
      JE          right1
      ADD         DL, dCOL
      JMP         setright
right1:
      MOV         DL, COLA                               ; �������C�����Ƶ�A��
setright:
	MOV         AH, 2                                  ; ���ù��λ�ã��ң�
	INT         10H

      RET
 drawright ENDP
;===================================================================
;�ӳ�������drawleft
;���ܣ�ˢ�¼����Ƿ��Ѿ��ɹ�
;��ڲ�������
;���ڲ�������
 
 drawleft PROC
 
	MOV          AL, 00H                               ; ԭλ�ü�ͷĨ��
      XOR          CX, CX                                ; ����
	MOV          CX, 1
	MOV          AH, 9
	INT          10H

      CMP          DL, COLA                              ; �жϹ���Ƿ���A��
      JE           left1
      SUB          DL, dCOL
      JMP          setleft
left1:
      MOV          DL, COLC                              ; �������A�����Ƶ�C��
setleft:
	MOV          AH, 2                                 ; ���ù��λ�ã���
	INT          10H
 
      RET
 drawleft ENDP

;===================================================================
;�ӳ�������drawPlate
;���ܣ�ˢ�¼����Ƿ��Ѿ��ɹ�
;��ڲ�����  ��
;���ڲ�����  ��
drawPlate PROC
      PUSH         SI					          ;�����Ĵ���
	PUSH         AX
	PUSH         BX
	PUSH         CX
	PUSH         DX
      PUSH         DI
      PUSH         BP


drawplate1:

      MOV          CH, SI[4]						     ; ��һ����ɫ����
	MOV          CL, SI[2]						     ; ��Ϊ����
      SUB          CL, 5
	MOV          DH, SI[4]
	MOV          DL, SI[2]
      ADD          DL,6
	MOV          BH,07H
	MOV          AL,0
	MOV          AH,6
	INT          10H

      MOV          CH,SI[4]						     ;��Ϊ��ɫ����
	MOV          CL,SI[2]						     ; ��Ϊ����
	MOV          DH,SI[4]
	MOV          DL,SI[2]
      ADD          DL,1
	MOV          BH,4CH
	MOV          AL,0
	MOV          AH,6
	INT          10H

      MOV          AH,SI[4]                                      ;�޸����ϲ����ӵ�������
      INC          AH
      MOV          SI[4],AH

      XOR          BP,BP                                         ;�޸����Ӹ���
      MOV          BP,SI[0]
      DEC          BP
      MOV          SI[0],BP
      
      ADD          BP,BP                                         ;�޸����ϲ����ӵ�size
      MOV          AL,SI[BP+10]
      MOV          BL,0
      MOV          SI[BP+10],BL
      
drawplate2:

      MOV          SI, DI
      XOR          DI, DI
      XOR          BL,BL                                         ;�޸�To�����ӵĸ���
      MOV          BL,SI[0]
      INC          BL
      MOV          SI[0],BL

      XOR          BH,BH                                         ;�޸�To������ӵ���ָ��
      MOV          BH,SI[4]
      DEC          BH
      MOV          SI[4],BH


      MOV          DI,SI[0]
      ADD          DI,DI
      ADD          SI,DI
      MOV          [SI+8],AL
      SUB          SI,DI

      XOR          CX,CX
      XOR          DX,DX
      XOR          BX,BX
      XOR          DI,DI

      MOV          CH,SI[4]                                      ;�涨��������
      MOV          CL,SI[2]

      SUB          CL,AL
      MOV          DH,SI[4]
      MOV          DL,SI[2]
      ADD          DL,AL
      INC          DL
      
      AND          AX, 00FFH                                     ;�涨������ɫ
      MOV          DI, AX
      SUB          DI, 1H
     	MOV          BH, colorBox[DI]
	MOV          AL,0
	MOV          AH,6
	INT          10H

      POP          BP
      POP          DI                                          ; �ָ��Ĵ���
      POP          DX
	POP          CX
	POP          BX
	POP          AX
	POP          SI

	RET
drawPlate ENDP
;=========================================================
;�ӳ�������FindPillar
;���ܣ���From��To��Ӧ������
;��ڲ�����  ��
;���ڲ�����  AH Ϊ1Ϊ�Ϸ� Ϊ0Ϊ�Ƿ�

FindPillar PROC				                            ;�����Ĵ���
      PUSH         BX
	PUSH         CX
	PUSH         DX
      PUSH         BP
      
      CMP          From, 25                                     ;ȷ��From��Ӧ������
      JZ           FromA
      CMP          From, 39
      JZ           FromB
      CMP          From, 53
      JZ           FromC

FromA:MOV          SI,OFFSET pillarA                            ;�ҵ������������������ �������SI��
      JMP          toj
FromB:MOV          SI,OFFSET pillarB
      JMP          toj
FromC:MOV          SI,OFFSET pillarC
      JMP          toj

TOj:
      XOR          BX, BX                                       ;��Ŀ��������û������
      XOR          BP, BP
      MOV          BP, SI[0]
      CMP          BP, BX
      JE           exit0
      XOR          CX, CX
      ADD          BP, BP
      MOV          CX, SI[BP+8H]
      CMP          To, 25                                       ;ȷ��From�����Ӵ�С
      JZ           ToAj
      CMP          To, 39
      JZ           ToBj
      CMP          To, 53
      JZ           ToCj

ToAj: MOV          DI,OFFSET pillarA                            ;�ҵ������������������ �������DI��
      JMP          Toend
ToBj: MOV          DI,OFFSET pillarB
      JMP          Toend
ToCj: MOV          DI,OFFSET pillarC
      JMP          Toend
Toend:
      XOR          BP, BP                                       ;ȷ��To�����Ӵ�С
      MOV          BP, DI[0]
      ADD          BP, BP
      XOR          BX, BX
      MOV          BX, DI[BP+8H]
      CMP          BX, 0
      JA           disregard
      MOV          BX, 6H
disregard:
      CMP          CX, BX
      JA           exit0
      MOV          AH, 1H
      JMP          final
exit0:
      MOV          AH, 0H
      JMP          final
      
final:
      POP          BP                                               ; �ָ��Ĵ���
      POP          DX
	POP          CX
	POP          BX
      RET
FindPillar ENDP
;=======================================================
; �ӳ�����: Delay
; ����: ��ʱ(����AX * CX��ѭ��)
; ��ڲ���: ��
; ���ڲ���: ��

delay PROC

	PUSH         CX                                               ;���õȴ���ʱ��
	PUSH         AX
	MOV          AX,0111111111111111B

MoreTick:
	MOV          CX,1111111111111111B
OneTick:
	LOOP         OneTick                                          ;ѭ��AX*CX�ε�λʱ��
	DEC          AX
	JNZ          MoreTick

	POP          AX
	POP          CX
	RET
delay ENDP
CODE ENDS
END  START


