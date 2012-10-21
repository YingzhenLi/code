%polyfit--------------------------------
%plot(long(1:3,1),long(1:3,4),'ko');
%hold on
%fit1=polyfit(long(1:3,1),long(1:3,4),1);
%fit2=polyfit(long(1:3,1),long(1:3,4),2);
%y1=polyval(fit1,7:1:18);
%y2=polyval(fit2,7:1:18);
%plot(7:1:18,y1,'c-',7:1:18,y2,'p-');
%griddata------------------------
t1=10:0.1:16;t2=7:0.1:16;
[XI,YI]=meshgrid(t1,t2);
ZI=griddata(short(:,1),short(:,2),short(:,3),XI,YI,'linear');
mesh(XI,YI,ZI);hold on; plot3(short(:,1),short(:,2),short(:,3),'o');
xlabel('distance(cm)');ylabel('voltage(V)');
zlabel('electric field intensity(V/m)');
shortpredit=[];
for i=1:size(XI,1)
    for j=1:size(XI,2)
        if ~isnan(XI(i,j))&&~isnan(YI(i,j))&&~isnan(ZI(i,j))
            shortpredit=[shortpredit;XI(i,j),YI(i,j),ZI(i,j)];
        end
    end
end
shortpredit=sortrows(shortpredit,[1,2]);
i=1;fit=[];
while i<size(shortpredit,1)
    j=i+1;
    while shortpredit(j,1)==shortpredit(i,1)&&j<=size(shortpredit,1)
        j=j+1;
    end
    fit=[fit;shortpredit(i,1),...
        polyfit(shortpredit(i:j-1,2),shortpredit(i:j-1,3),1)];
    i=j;
end
