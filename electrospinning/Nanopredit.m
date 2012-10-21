function d = Nanopredit( b,x )
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here
syms x1 x2 x3 x4 x5;
f=b(1)*x1+b(2)*x2+b(3)*x3+b(4)*x4+b(5)*x5;
d=subs(f,{x1,x2,x3,x4,x5},{x(1),x(2),x(3),x(4),x(5)});
end

