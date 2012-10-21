function Cluster = FuzzyCluster( X,c )
%Fuzzy Cluster
%--------------------------------------------------------------------------
%STEP1����ģ�����ƾ���R�������μ�FuzzyMatrix.m
X=zscore(X);%��׼��
X=mapminmax(X,0,1);%��һ��
n=size(X,1);
%ŷʽ���뷨----------------------------
for i=1:n
    for j=1:n
        temp=sum((X(i,:)-X(j,:)).^2);
        C(i,j)=1-c*temp^0.5;%cΪ����
    end
end
temp=[];
%warning
%if ~isempty(find(C<0))
%    return
%end
%--------------------------------------------------------------------------
%STEP2����
%��ֱ̬�Ӿ��෨--------------------------
B=C;m=size(B,1);
for i=1:size(B,1)
    B(i,i)=-Inf;
end
%Ѱ����Ԫ
[R1,R2]=max(B,[],1);S=[];R=[];
for i=1:m
    for j=i+1:m
        if R1(i)==R1(j)&&R2(i)==j&&R2(j)==i
            S=[S;i,j,R1(i)];%˫����Ԫ
            R2(i)=0;R2(j)=0;R1(i)=0;R1(j)=0;%����
            B(i,j)=-inf;B(j,i)=-Inf;%��ȥ��Ԫ
        end
    end    
end
for i=1:m
    if R1(i)~=0
        R=[R;i,R2(i),R1(i)];%������Ԫ
        B(i,R2(i))=-Inf;%��ȥ��Ԫ
    end
end
%�б�ֽ�
m=size(R,1);l=size(S,1);
for i=1:l
    H{i}=S(i,1:2);
end
k=1;
for i=1:m
    for j=1:l
        if ~isempty(intersect(R(i,1:2),H{j}))
            H{j}=union(H{j},R(i,1:2));%�������ǿ�����
            break;
        else
            if j==l
                temp(k)=i;
                k=k+1;%������˳��������Щ������Ҫ�Ļ�����˳��
            end
        end
    end
end
i=1;
while ~isempty(temp) %���䲢��
    for j=1:l
        if ~isempty(intersect(R(temp(i),1:2),H{j}))
            H{j}=union(H{j},R(temp(i),1:2));%�������ǿ�����
            temp(i)=[];
            break;
        end
    end
    if j>=l
        i=i+1;
    end
    if i>size(temp)
        i=1;
    end    
end
%��ȥ��ͨԪ
for i=1:size(H,2)
    for j=1:size(H{i},2)
        for k=1:size(H{i},2)
            B(H{i}(j),H{i}(k))=-Inf;%��ȥ��ͨԪ(��Ԫ���ѻ�ȥ)
        end
    end
end
%Ѱ������Ԫ
m=size(H,2);
for i=1:m-1
    [temp,tempJ]=max(B(:,:),[],2);
    [temp,tempI]=max(temp);
    tempJ=tempJ(tempI);%�����Ԫ��Ϊ����Ԫ
    L(i,:)=[tempI,tempJ,temp];
    B(tempI,tempJ)=-Inf;B(tempJ,tempI)=-Inf;%��ȥ����Ԫ
    %�ϲ��任
    temp2=[tempI,tempJ];
    j=1;
    while isempty(intersect(H{j},temp2))&&j<size(H,2)
        j=j+1;
    end
    temp2=setdiff(temp2,H{j});
    k=j+1;
    while k<size(H,2)&&isempty(intersect(H{k},temp2))
        k=k+1;
    end
    H{j}=union(H{j},H{k});%�ϲ�H{j}��H{k}
    H(k)=[];%ɾ��ԭ����H{k}
    %����任
    for a=1:size(H{j},2)
        for b=1:size(H{j},2)
            B(H{j}(a),H{j}(b))=-Inf;%��ȥʣ��Ԫ
        end
    end
end
%��û�Ԫ
Cluster=[S;R;L];
end


