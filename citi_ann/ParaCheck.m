function para = ParaCheck( para )
%UNTITLED4 Summary of this function goes here
%   Detailed explanation goes here
x=size(para,1);y=size(para,2);
for i=1:x
    for j=1:y
        if(isnan(para(i,j)))
            para(i,j)=0;%to be checked
        end
    end
end
end

