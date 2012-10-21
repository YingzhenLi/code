function [ fig,sta ] = clusterplot( set,data,t )
%--------------------------------------------------------------------------
%简单聚类图
%绘制统计图
m=size(set,2)-1;
mean=zeros(m,4);max=mean;min=ones(m,4).*Inf;
for i=1:m
    p=size(set{i},2);
    for j=1:p
        mean(i,:)=mean(i,:)+data(set{i}(j),2:5)./p;
        for k=2:5
            if data(set{i}(j),k)>max(i,k-1)
                max(i,k-1)=data(set{i}(j),k);
            end
            if data(set{i}(j),k)<min(i,k-1)
                min(i,k-1)=data(set{i}(j),k);
            end
        end
    end
end
for i=1:m
    sta{i}=[max(i,:);min(i,:);mean(i,:)];
end
char{1}='concentration vs. diameter';
char{2}='distance vs. diameter';
char{3}='voltage vs. diameter';
char{4}='velocity vs. diameter';
style=['-r*';'-b*';'-g*';'-k*'];
for i=1:3
    temp=sortrows([mean(:,[i,4])';max(:,i)';min(:,i)';max(:,4)';min(:,4)']');
    [fit,S]=polyfit(temp(:,1),temp(:,2),1);
    fig=subplot(2,2,i); 
    plot(temp(:,1),temp(:,2),style(i,2:3));
    hold on
    X=temp(1,4)*0.9:(temp(size(temp,1),3)-temp(1,4))/10:temp(size(temp,1),3)*1.1;
    Y=polyval(fit,X);
    plot(X,Y,['-',style(i,2)],'LineWidth',2);
    for j=1:size(temp,1)
        %plot(temp(j,3:4),[temp(j,2),temp(j,2)],style(i,1:2),'LineWidth',0.5);
        plot([temp(j,1),temp(j,1)],temp(j,5:6),style(i,1:2),'LineWidth',0.5);
    end
    title(char{i});
    str=['t=',num2str(t),',normr=',num2str(S.normr)];
    text(X(1),Y(1),str);
end
end

