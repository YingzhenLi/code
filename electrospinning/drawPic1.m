[x,y]=meshgrid(8:0.25:15,0.3:0.02:0.5);
pre=[];z=[];Sdata2=Sdata(1:19,:);
for j=1:size(y,1)
    for i=1:size(x,2)
        pre=[pre;10000,12,2475,13.5,x(1,i),y(j,1)];
    end
end
%BP networks normalization
pre(:,1)=pre(:,1)./10000;
pre(:,2)=pre(:,2)./100;
pre(:,3)=pre(:,3)./2750;
pre(:,4)=pre(:,4)./20;
pre(:,5)=pre(:,5)./20;
outP=sim(Snet,pre')';
for k=1:size(x,2):size(outP,1)
    z=[z;outP(k:k+size(x,2)-1,1)'];
end
surf(x,y,z);
hold on
plot3(Sdata2(:,5),Sdata2(:,6),Sdata2(:,7),'r*');
%hold on
%plot(pre(:,5),outP2(:,1),'*g-');
%legend('d=12.5','d=13.5');