%13����ģ�����ƾ��󷨣���XΪ���ݾ���CΪģ�����ƾ���cΪ����
%�������뷨----------------------------
for i=1:n
    for j=1:n
        temp=sum(abs(X(i,:)-X(j,:)));
        C(i,j)=1-c*temp;%cΪ����
    end
end
%ŷʽ���뷨----------------------------
for i=1:n
    for j=1:n
        temp=sum((X(i,:)-X(j,:)).^2);
        C(i,j)=1-c*temp^0.5;%cΪ����
    end
end
%�б�ѩ����뷨-------------------------
for i=1:n
    for j=1:n
        temp=max(abs(X(i,:)-X(j,:)));
        C(i,j)=1-c*temp;%cΪ����
    end
end
%�ɿɷ�˹�����뷨-----------------------
for i=1:n
    for j=1:n
        temp=sum((abs(X(i,:)-X(j,:))).^c);
        C(i,j)=temp^(1/c);%cΪ����
    end
end
%����ֵ������---------------------------
for i=1:n
    for j=1:n        
        if i==j
            C(i,j)=1;
        else
            temp=sum(abs(X(i,:)-X(j,:)));
            C(i,j)=c/temp;%cΪ����
        end
    end
end
%����ֵָ����----------------------------
for i=1:n
    for j=1:n
        temp=sum(abs(X(i,:)-X(j,:)));
        C(i,j)=exp(-c*temp);%cΪ����
    end
end
%ָ������ϵ����--------------------------
Sk=std(X(:,:),1);
for i=1:n
    for j=1:n
        temp=sum(exp(-3/4*(X(i,:)-X(j,:)).^2./Sk.^2));
        C(i,j)=temp/n;%cΪ����
    end
end
%��ʽ���뷨------------------------------
for i=1:n
    for j=1:n
        temp=sum(abs(X(i,:)-X(j,:))./abs(X(i,:)+X(j,:)));
        C(i,j)=1-c*temp;%cΪ����
    end
end
%��������--------------------------------
for i=1:n
    for j=1:n     
        if i==j
            C(i,j)=c;
        else
            C(i,j)=sum(X(i,:).*X(j,:));
        end
    end
end
%�н����ҷ�------------------------------
for i=1:n
    for j=1:n
        temp=sum(X(i,:).*X(j,:));
        tempI=sum(X(i,:).^2);
        tempJ=sum(X(j,:).^2);
        C(i,j)=temp/(tempI*tempJ)^0.5;
    end
end
%���ϵ����------------------------------
meanX=mean(X,2);
for i=1:n
    for j=1:n
        temp=sum((X(i,:)-meanX(i)).*(X(j,:)-meanX(j)));
        tempI=sum((X(i,:)-meanX(i)).^2);
        tempJ=sum((X(j,:)-meanX(j)).^2);
        C(i,j)=temp/(tempI*tempJ)^0.5;
    end
end
%�����С��------------------------------
for i=1:n
    for j=1:n
        tempMin=sum(min(X(i,:),X(j,:)));
        tempMax=sum(max(X(i,:),X(j,:)));
        C(i,j)=tempMin/tempMax;
    end
end
%����ƽ����С��--------------------------
for i=1:n
    for j=1:n
        tempMin=sum(min(X(i,:),X(j,:)));
        temp=sum(X(i,:)+X(j,:));
        C(i,j)=2*tempMin/temp;
    end
end
%����ƽ����С��--------------------------
for i=1:n
    for j=1:n
        tempMin=sum(min(X(i,:),X(j,:)));
        temp=sum((X(i,:).*X(j,:)).^0.5);
        C(i,j)=tempMin/temp;
    end
end