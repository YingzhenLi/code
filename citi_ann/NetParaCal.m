function SingleCal=NetParaCal(M1,M2,M3)
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
FZL=M1(56,:)./M1(33,:);
QYB=M1(56,:)./M1(66,:);

MLL=1-M2(2,:)./M2(1,:);
LRL=M2(13,:)./M2(1,:);
BCL=2*[(M2(20,2)+M1(41,2) )/( M1(33,1)+M1(33,2)) (M2(20,3)+M1(41,3) )/( M1(33,2)+M1(33,3))];%UNDEFINED
SYL=[2*M2(22,2)/(M1(66,1)+M1(66,2)) 2*M2(22,3)/(M1(66,2)+M1(66,3))];%DEVELOPING

YSZZSD=[2*M2(1,2)/(M1(4,1)+M1(4,2)) 2*M2(1,3)/(M1(4,2)+M1(4,3))];%DEVELOPING
CHZZSD=[2*M2(1,2)/(M1(10,1)+M1(10,2)) 2*M2(1,3)/(M1(10,2)+M1(10,3))];%DEVELOPING
ZZCZZSD=[2*M2(1,2)/(M1(33,1)+M1(33,2)) 2*M2(1,3)/(M1(33,2)+M1(33,3))];%DEVELOPING

LDB=M1(14,:)./M1(47,:);
XJB=(M3(72,:)+M1(2,:))./M1(47,:);%UNDEFINED
EBI=(M2(20,:)+M1(41,:)+M3(48,:)+M3(49,:)+M3(50,:))./M2(7,:) ;%TO BE IMPROVED
YYFZB=(M1(14,:)+M1(47,:))./M1(55,:);

SRXJ=M3(1,:)./M2(1,:);
JYLDFZB=[2*M3(10,2)/(M1(47,1)+M1(47,2)) 2*M3(10,3)/(M1(47,2)+M1(47,3))];%DEVELOPING
JYZFZB=[2*M3(10,2)/(M1(56,1)+M1(56,2)) 2*M3(10,3)/(M1(56,2)+M1(56,3))];%DEVELOPING
FLDFZB=[2*(M3(10,2)+M3(29,2))/(M1(47,1)+M1(47,2)) 2*(M3(10,3)+M3(29,3))/(M1(47,2)+M1(47,3))];%DEVELOPING
FZFZB=[2*(M3(10,2)+M3(29,2))/(M1(56,1)+M1(56,2)) 2*(M3(10,3)+M3(29,3))/(M1(56,2)+M1(56,3))];%DEVELOPING

ZYW=[(M3(4,2)-M3(4,1))/M3(4,1) (M3(4,3)-M2(4,2))/M3(4,2)];
JLR=[M2(22,2)/M2(22,1)-1 M2(22,3)/M2(22,2)-1];

SingleCal=[FZL(2) FZL(3);QYB(2) QYB(3);MLL(2) MLL(3);LRL(2) LRL(3);BCL;SYL;YSZZSD;CHZZSD;ZZCZZSD; ...
    LDB(2) LDB(3);XJB(2) XJB(3);EBI(2) EBI(3);YYFZB(2) YYFZB(3);SRXJ(2) SRXJ(3);JYLDFZB;JYZFZB;FLDFZB;FZFZB;ZYW;JLR];
end

