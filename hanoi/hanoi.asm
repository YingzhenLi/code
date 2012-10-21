
;常量定义
EXITKEY = 1BH                                                     ;退出键ESC的ASCII码
ENTERKEY= 0DH                                                     ;回车键ENTER的ASCII码
ARROW   = 18H                                                     ;箭头的ASCII码
RED     = 0CH                                                     ;红色
WHITE   = 0FH                                                     ;白色
ROWPLATE= 11H                                                     ;柱子坐标
ROW     = 13H                                                     ;行坐标
COLA    = 19H                                                     ;A柱子列坐标
COLB    = 23H                                                     ;B柱子列坐标
COLC    = 35H                                                     ;C柱子列坐标
dCOL    = 0EH                                                     ;列坐标差
TOP     =4                                                        ;左上角行坐标
BUTTOM  =19                                                       ;右下角行坐标
LEFT    =16                                                       ;左上角列坐标
RIGHT   =64                                                       ;右下角列坐标
MAXN    =5                                                        ;最难汉诺塔层数


PLATE STRUC                                                       ;声明盘子结点结构
      nextY    DB  0                                              ;行坐标
      nextX    DB  0                                              ;列坐标
      color    DB  0AH                                            ;颜色
      siz      DB  1                                              ;大小
PLATE ENDS

;数据段
DATA SEGMENT
     pillarA   DW   0,25,ROWPLATE,'A',0,5 dup(0)                 ;从左至右分别为盘子个数，柱子x坐标，最高盘子的y坐标，柱子标记，和无意义
     pillarB   DW   0,39,ROWPLATE,'B',0,5 dup(0)
     pillarC   DW   0,53,ROWPLATE,'C',0,5 dup(0)
     QHard     DB   'Please input the difficulty coeffient:$'   ;修改   Please input the difficulty coeffient:
     Con       DB   'Congratuations!$'
     hard      DB   0                                           ;难度系数
     colorBox  DB   5CH,3CH,6CH,7CH,8CH
     From      DB   0                                           ;出发柱子
     To        DB   0                                           ;抵达柱子
     flag      DW   0
     SIZE1     DB   0
     CArrow    DB   COLA
     
DATA ENDS

;代码段
CODE SEGMENT        'code'
     ASSUME CS:CODE, DS:DATA
     
START:
      MOV        AX,DATA
      MOV        DS,AX
     
      MOV        CH,3							  ; 开一个红色窗口
	MOV        CL,15							  ; 作边框
	MOV        DH,20
	MOV        DL,65
	MOV        BH,4CH
	MOV        AL,0
	MOV        AH,6
	INT        10H
 
      MOV        CH,TOP							 ; 开一个黑色窗口
	MOV        CL,LEFT					       ; 作为界面
	MOV        DH,BUTTOM
	MOV        DL,RIGHT
	MOV        BH,07H
	MOV        AL,0
	MOV        AH,6
	INT        10H

      MOV        CH,5						      ; 柱子1上部分
	MOV        CL,25
	MOV        DH,18
	MOV        DL,26
	MOV        BH,4CH                                    ;其后改为棕色
	MOV        AL,0
	MOV        AH,6
	INT        10H

      MOV        CH,17						     ; 柱子1下部分
	MOV        CL,20
	MOV        DH,18
	MOV        DL,31
	MOV        BH,4CH
	MOV        AL,0
	MOV        AH,6
	INT        10H

      MOV        CH,5						    ; 柱子2上部分
	MOV        CL,39
	MOV        DH,18
	MOV        DL,40
	MOV        BH,4CH
	MOV        AL,0
	MOV        AH,6
	INT        10H

      MOV        CH,17						    ; 柱子2下部分
	MOV        CL,34
	MOV        DH,18
	MOV        DL,45
	MOV        BH,4CH
	MOV        AL,0
	MOV        AH,6
	INT        10H

      MOV        CH,5						    ; 柱子3上部分
	MOV        CL,53
	MOV        DH,18
	MOV        DL,54
	MOV        BH,4CH
	MOV        AL,0
	MOV        AH,6
	INT        10H

      MOV        CH,17						    ; 柱子3下部分
	MOV        CL,48
	MOV        DH,18
	MOV        DL,60
	MOV        BH,4CH
	MOV        AL,0
	MOV        AH,6
	INT        10H
 
      CALL       setHard                                 ;设置难度系数
 moving:
      CALL       drawArrow                               ;选择移动的柱子 并移动盘子
      CALL       checkIFWin                              ;判断是否赢
      JMP        moving
      
 ;===================================================================
 ;子程序名：setHard
 ;功能：布置游戏初始的屏幕
 ;入口参数： 无
 ;出口参数： 无
 
setHard PROC
      PUSH       SI					        ; 保护寄存器
	PUSH       AX
	PUSH       BX
	PUSH       CX
	PUSH       DX

      XOR        DX, DX                                 ;清零
      XOR        AX, AX
      
CHAR: MOV        DX, OFFSET QHard                       ;输入提示语句：请输入难度系数
      MOV        AH, 9
      INT        21H
      MOV        AH, 1                                  ;接收字符（难度系数）到AL
      INT        21H
      SUB        AL, '0'                                ;字符转换成数据
      CMP        AL, 1
      JL         CHAR                                   ;数据<0则退出；若不符合要求则重新提示并输入
      CMP        AL, 5
      JG         CHAR                                   ;数据>6则退出
      
      MOV        hard, AL                               ;将难度系数录入程序
      PUSH       AX                                     ;保护寄存器
      
enter0:
      MOV        AH, 1                                  ; 输入
	INT        16H
      MOV        AH, 0
	INT        16H
      CMP        AL, ENTERKEY			              ; 读到ENTER键,退出
	JNZ        enter0
 
      POP        AX                                     ;令AX中存入hard

                                                        ;功能：游戏各变量初始化
      XOR        SI, SI                                 ;清零
      XOR        BX, BX

      MOV        SI, OFFSET pillarA                    ;SI 指向pillarA
      MOV        SI[0], AL                             ;改变pillarA的初始值
      MOV        AL, ROWPLATE
      SUB        AL, SI[0]
      MOV        SI[4], AL
      ADD        SI, 8H                                ;令SI指向pillarA的无意义格
                                                       ;循环画出初始盘子并进一步初始化pillarA中的盘子信息
      XOR        AX, AX
      XOR        BX, BX
      XOR        CX, CX
      XOR        DX, DX

      MOV        CL, hard
loop1:
      PUSH       CX                                    ;保护寄存器
                                                       ;改变pillarA盘子信息，并使SI向前进一格
      ADD        SI, 2H
      MOV        SI[0], CX

                                                       ;画盘子
      MOV        CH, ROWPLATE                          ;左上角
      SUB        CH, hard
      SUB        CH, 1H
      ADD        CH, SI[0]
      MOV        CL, COLA
      SUB        CL, SI[0]

	MOV        DH, CH                                ;右下角
	MOV        DL, COLA
      ADD        DL, 1H
      ADD        DL, SI[0]

      MOV        DI, OFFSET colorBox                    ;设置不同颜色
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
      
      POP        DX                                      ;还原寄存器
      POP        CX
      POP        BX
      POP        AX
      POP        SI

	RET
setHard ENDP
 
;===================================================================
;子程序名：checkIFWin
;功能：刷新检验是否已经成功
;入口参数：
;出口参数：

checkIFWin PROC
      PUSH        SI					        ; 保护寄存器
	PUSH        AX
	PUSH        BX
	PUSH        CX
	PUSH        DX

      XOR         AX, AX                                ;初始
      XOR         SI, SI
      XOR         DX, DX
      XOR         BX, BX
      
      MOV         SI, OFFSET pillarC                    ;判断是否赢
      MOV         BL, hard
      CMP         SI[0],BL
      JE          win
      JMP         nowin
win:  MOV         DX, OFFSET Con                        ;判断
      MOV         AH, 9
      INT         21H
      CALL        delay
      MOV         AH, 4CH
      INT         21H
nowin:
      POP         DX					        ; 恢复寄存器
	POP         CX
	POP         BX
	POP         AX
	POP         SI
      RET
 checkIFWin ENDP
 
 ;===================================================================
;子程序名：drawArrow
;功能：刷新检验是否已经成功
;入口参数：   无
;出口参数：   无
 drawArrow PROC
      PUSH        DI                                    ; 保护寄存器
      PUSH        AX
      PUSH        BX
	PUSH        CX
	PUSH        DX

      MOV         flag, 1                               ; 确定当前箭头模式，0表示选择柱子，1表示选择盘子移动目标

      MOV         BH, 0				              ; 设置光标位置
	MOV         DH, ROW
	MOV         DL, CArrow
	MOV         AH, 2
	INT         10H

      MOV         AL, 18H                               ; 在光标位置画出相应节点
	MOV         BL, WHITE
      XOR         CX, CX                                ; 清零
	MOV         CX, 1
	MOV         AH, 9
	INT         10H

again0:
      MOV         AH, 1                                 ; 输入
	INT         16H
      MOV         AH, 0
	INT         16H

      CMP         AH, 4DH				        ; 右箭头(小键盘 6),转
      JZ          rightmove
      
      CMP         AH, 4BH                               ;左箭头
      JZ          leftmove

      CMP         AL, ENTERKEY			        ; 读到ENTER键,退出
	JZ          changemode

      CMP         AL, EXITKEY			              ; 读到EXIT键,退出
      MOV         AH, 4CH
      INT         21H
           
      JMP         again0				        ; 否则,重新读取
       
rightmove: call drawright                               ;执行箭头右转
           JMP arrowdraw
leftmove:  call drawleft                                ;执行箭头左转
           JMP  arrowdraw

arrowdraw:
	MOV         AL, ARROW                             ; 在光标位置画出相应节点
      XOR         CX, CX                                ; 清零
	MOV         CX, 1
	MOV         AH, 9
	INT         10H
      JMP         again0

changemode:
      CMP         flag, 1                               ; 确定当前箭头移动模式
      JE          movestep                              ; 若为确定柱子模式则箭头需变色（红）
      MOV         BL, WHITE                             ; 若为确定盘子模式则箭头变回原来颜色（白）
      MOV         flag, 1
      MOV         To, DL                                ; 将DI作为指标并修改，当DI为1时ENTER后运行盘子移动
      JMP         arrowdraw2                            ; 可考虑将画盘子函数嵌套入drawArray,也可将CX返回给外部画盘子函数
                                                        ; 查找选中的pillar(地址为[SI].nextX之类的)中记录的最顶盘子的编号
                                                        ; 在下一次确定盘子模式里将该编号盘子移到选中的pillar（已更新）

movestep:                                               ; 确定柱子模式，箭头变色（红）
      MOV         BL, RED
      MOV         flag,0                                ;修改当前模式指标
      MOV         From, DL
arrowdraw2:
	MOV         AL, 00H                               ; 原位置箭头抹除
      XOR         CX, CX                                ; 清零
	MOV         CX, 1
	MOV         AH, 9
	INT         10H

	MOV         AL, ARROW                             ; 在光标位置画出箭头，由于箭头位置不变不需要重新设置光标位置
      XOR         CX, CX
	MOV         CX, 1
	MOV         AH, 9
	INT         10H
      CMP         flag, 1
      JZ          drawpl2
      JMP         again0

drawpl2:
      CALL        FindPillar                             ;判断操作是否合法
      CMP         AH, 1H
      JB          again0
      CALL        drawPlate                              ;合法则画盘子
exit:
      MOV         CArrow, DL
      POP         CX                                    ; 寄存器数据还原，并保存当前光标位置
      POP         AX
      POP         DI
	POP         BX
	POP         DX
      RET
 drawArrow ENDP
 ;===================================================================
;子程序名：drawright
;功能：刷新检验是否已经成功
;入口参数：    无
;出口参数：    无

drawright PROC

	MOV         AL, 00H                                ; 原位置箭头抹除
	MOV         AH, 9
	INT         10H

      CMP         DL, COLC                               ; 判断光标是否在C处
      JE          right1
      ADD         DL, dCOL
      JMP         setright
right1:
      MOV         DL, COLA                               ; 若光标在C处则移到A处
setright:
	MOV         AH, 2                                  ; 设置光标位置（右）
	INT         10H

      RET
 drawright ENDP
;===================================================================
;子程序名：drawleft
;功能：刷新检验是否已经成功
;入口参数：无
;出口参数：无
 
 drawleft PROC
 
	MOV          AL, 00H                               ; 原位置箭头抹除
      XOR          CX, CX                                ; 清零
	MOV          CX, 1
	MOV          AH, 9
	INT          10H

      CMP          DL, COLA                              ; 判断光标是否在A处
      JE           left1
      SUB          DL, dCOL
      JMP          setleft
left1:
      MOV          DL, COLC                              ; 若光标在A处则移到C处
setleft:
	MOV          AH, 2                                 ; 设置光标位置（左）
	INT          10H
 
      RET
 drawleft ENDP

;===================================================================
;子程序名：drawPlate
;功能：刷新检验是否已经成功
;入口参数：  无
;出口参数：  无
drawPlate PROC
      PUSH         SI					          ;保护寄存器
	PUSH         AX
	PUSH         BX
	PUSH         CX
	PUSH         DX
      PUSH         DI
      PUSH         BP


drawplate1:

      MOV          CH, SI[4]						     ; 开一个黑色窗口
	MOV          CL, SI[2]						     ; 作为界面
      SUB          CL, 5
	MOV          DH, SI[4]
	MOV          DL, SI[2]
      ADD          DL,6
	MOV          BH,07H
	MOV          AL,0
	MOV          AH,6
	INT          10H

      MOV          CH,SI[4]						     ;改为红色柱子
	MOV          CL,SI[2]						     ; 作为界面
	MOV          DH,SI[4]
	MOV          DL,SI[2]
      ADD          DL,1
	MOV          BH,4CH
	MOV          AL,0
	MOV          AH,6
	INT          10H

      MOV          AH,SI[4]                                      ;修改最上层盘子的行坐标
      INC          AH
      MOV          SI[4],AH

      XOR          BP,BP                                         ;修改盘子个数
      MOV          BP,SI[0]
      DEC          BP
      MOV          SI[0],BP
      
      ADD          BP,BP                                         ;修改最上层盘子的size
      MOV          AL,SI[BP+10]
      MOV          BL,0
      MOV          SI[BP+10],BL
      
drawplate2:

      MOV          SI, DI
      XOR          DI, DI
      XOR          BL,BL                                         ;修改To的盘子的个数
      MOV          BL,SI[0]
      INC          BL
      MOV          SI[0],BL

      XOR          BH,BH                                         ;修改To最高盘子的行指标
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

      MOV          CH,SI[4]                                      ;规定盘子坐标
      MOV          CL,SI[2]

      SUB          CL,AL
      MOV          DH,SI[4]
      MOV          DL,SI[2]
      ADD          DL,AL
      INC          DL
      
      AND          AX, 00FFH                                     ;规定盘子颜色
      MOV          DI, AX
      SUB          DI, 1H
     	MOV          BH, colorBox[DI]
	MOV          AL,0
	MOV          AH,6
	INT          10H

      POP          BP
      POP          DI                                          ; 恢复寄存器
      POP          DX
	POP          CX
	POP          BX
	POP          AX
	POP          SI

	RET
drawPlate ENDP
;=========================================================
;子程序名：FindPillar
;功能：找From和To对应的盘子
;入口参数：  无
;出口参数：  AH 为1为合法 为0为非法

FindPillar PROC				                            ;保护寄存器
      PUSH         BX
	PUSH         CX
	PUSH         DX
      PUSH         BP
      
      CMP          From, 25                                     ;确定From对应的柱子
      JZ           FromA
      CMP          From, 39
      JZ           FromB
      CMP          From, 53
      JZ           FromC

FromA:MOV          SI,OFFSET pillarA                            ;找到该柱子最上面的盘子 并存放在SI中
      JMP          toj
FromB:MOV          SI,OFFSET pillarB
      JMP          toj
FromC:MOV          SI,OFFSET pillarC
      JMP          toj

TOj:
      XOR          BX, BX                                       ;若目标柱子上没有柱子
      XOR          BP, BP
      MOV          BP, SI[0]
      CMP          BP, BX
      JE           exit0
      XOR          CX, CX
      ADD          BP, BP
      MOV          CX, SI[BP+8H]
      CMP          To, 25                                       ;确定From的盘子大小
      JZ           ToAj
      CMP          To, 39
      JZ           ToBj
      CMP          To, 53
      JZ           ToCj

ToAj: MOV          DI,OFFSET pillarA                            ;找到该柱子最上面的盘子 并存放在DI中
      JMP          Toend
ToBj: MOV          DI,OFFSET pillarB
      JMP          Toend
ToCj: MOV          DI,OFFSET pillarC
      JMP          Toend
Toend:
      XOR          BP, BP                                       ;确定To的盘子大小
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
      POP          BP                                               ; 恢复寄存器
      POP          DX
	POP          CX
	POP          BX
      RET
FindPillar ENDP
;=======================================================
; 子程序名: Delay
; 功能: 延时(进行AX * CX次循环)
; 入口参数: 无
; 出口参数: 无

delay PROC

	PUSH         CX                                               ;设置等待的时间
	PUSH         AX
	MOV          AX,0111111111111111B

MoreTick:
	MOV          CX,1111111111111111B
OneTick:
	LOOP         OneTick                                          ;循环AX*CX次单位时间
	DEC          AX
	JNZ          MoreTick

	POP          AX
	POP          CX
	RET
delay ENDP
CODE ENDS
END  START


