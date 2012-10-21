function R = inputPortion(M,X,Y)
%UNTITLED3 Summary of this function goes here
%   Detailed explanation goes here
if(X+Y~=1)
    Y=1-X;
end
n=size(M,2)/2;
R=X*M(:,1)+Y*M(:,2);
for i=2:n
    TEMP=X*M(:,2*i-1)+M(:,2*i);
    R=[R';TEMP']';
end
end

