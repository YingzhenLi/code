function b = Nanofit( data )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
X=[data(:,1) data(:,2) data(:,3) data(:,4) data(:,5)];
Y=data(:,6);
[b,bint,r,rint,stats]=regress(Y,X);
end

