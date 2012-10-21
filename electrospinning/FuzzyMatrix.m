%13种求模糊相似矩阵法，设X为数据矩阵，C为模糊相似矩阵，c为参数
%海明距离法----------------------------
for i=1:n
    for j=1:n
        temp=sum(abs(X(i,:)-X(j,:)));
        C(i,j)=1-c*temp;%c为参数
    end
end
%欧式距离法----------------------------
for i=1:n
    for j=1:n
        temp=sum((X(i,:)-X(j,:)).^2);
        C(i,j)=1-c*temp^0.5;%c为参数
    end
end
%切比雪夫距离法-------------------------
for i=1:n
    for j=1:n
        temp=max(abs(X(i,:)-X(j,:)));
        C(i,j)=1-c*temp;%c为参数
    end
end
%闵可夫斯基距离法-----------------------
for i=1:n
    for j=1:n
        temp=sum((abs(X(i,:)-X(j,:))).^c);
        C(i,j)=temp^(1/c);%c为参数
    end
end
%绝对值倒数法---------------------------
for i=1:n
    for j=1:n        
        if i==j
            C(i,j)=1;
        else
            temp=sum(abs(X(i,:)-X(j,:)));
            C(i,j)=c/temp;%c为参数
        end
    end
end
%绝对值指数法----------------------------
for i=1:n
    for j=1:n
        temp=sum(abs(X(i,:)-X(j,:)));
        C(i,j)=exp(-c*temp);%c为参数
    end
end
%指数相似系数法--------------------------
Sk=std(X(:,:),1);
for i=1:n
    for j=1:n
        temp=sum(exp(-3/4*(X(i,:)-X(j,:)).^2./Sk.^2));
        C(i,j)=temp/n;%c为参数
    end
end
%兰式距离法------------------------------
for i=1:n
    for j=1:n
        temp=sum(abs(X(i,:)-X(j,:))./abs(X(i,:)+X(j,:)));
        C(i,j)=1-c*temp;%c为参数
    end
end
%数量积法--------------------------------
for i=1:n
    for j=1:n     
        if i==j
            C(i,j)=c;
        else
            C(i,j)=sum(X(i,:).*X(j,:));
        end
    end
end
%夹角余弦法------------------------------
for i=1:n
    for j=1:n
        temp=sum(X(i,:).*X(j,:));
        tempI=sum(X(i,:).^2);
        tempJ=sum(X(j,:).^2);
        C(i,j)=temp/(tempI*tempJ)^0.5;
    end
end
%相关系数法------------------------------
meanX=mean(X,2);
for i=1:n
    for j=1:n
        temp=sum((X(i,:)-meanX(i)).*(X(j,:)-meanX(j)));
        tempI=sum((X(i,:)-meanX(i)).^2);
        tempJ=sum((X(j,:)-meanX(j)).^2);
        C(i,j)=temp/(tempI*tempJ)^0.5;
    end
end
%最大最小法------------------------------
for i=1:n
    for j=1:n
        tempMin=sum(min(X(i,:),X(j,:)));
        tempMax=sum(max(X(i,:),X(j,:)));
        C(i,j)=tempMin/tempMax;
    end
end
%算术平均最小法--------------------------
for i=1:n
    for j=1:n
        tempMin=sum(min(X(i,:),X(j,:)));
        temp=sum(X(i,:)+X(j,:));
        C(i,j)=2*tempMin/temp;
    end
end
%几何平均最小法--------------------------
for i=1:n
    for j=1:n
        tempMin=sum(min(X(i,:),X(j,:)));
        temp=sum((X(i,:).*X(j,:)).^0.5);
        C(i,j)=tempMin/temp;
    end
end