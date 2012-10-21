function sheet = CalGrade( FileList )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
n=length(FileList);
sheet=0*ones(7,n);
for i=1:n
    tmp=1;
    while(FileList(i).name(tmp)<'A'||FileList(i).name(tmp)>'C')
        tmp=tmp+1;
    end
    j=tmp;
    while(FileList(i).name(j)>='A'&&FileList(i).name(j)<='C')
        j=j+1;
    end
    k=j-tmp;tmp=0;
    if(FileList(i).name(j)=='+')
        tmp=1;
    end
    if(FileList(i).name(j)=='-')
        tmp=-1;
    end
    if(k<=2)   
        j=3*k-1+tmp;
    end
    if(k==3)
        j=7;
    end
    sheet(j,i)=1;
end
end

