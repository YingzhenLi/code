function Cluster = FuzzyCluster( X,c )
%Fuzzy Cluster
%--------------------------------------------------------------------------
%STEP1计算模糊相似矩阵R，方法参见FuzzyMatrix.m
X=zscore(X);%标准化
X=mapminmax(X,0,1);%归一化
n=size(X,1);
%欧式距离法----------------------------
for i=1:n
    for j=1:n
        temp=sum((X(i,:)-X(j,:)).^2);
        C(i,j)=1-c*temp^0.5;%c为参数
    end
end
temp=[];
%warning
%if ~isempty(find(C<0))
%    return
%end
%--------------------------------------------------------------------------
%STEP2聚类
%动态直接聚类法--------------------------
B=C;m=size(B,1);
for i=1:size(B,1)
    B(i,i)=-Inf;
end
%寻找主元
[R1,R2]=max(B,[],1);S=[];R=[];
for i=1:m
    for j=i+1:m
        if R1(i)==R1(j)&&R2(i)==j&&R2(j)==i
            S=[S;i,j,R1(i)];%双重主元
            R2(i)=0;R2(j)=0;R1(i)=0;R1(j)=0;%清零
            B(i,j)=-inf;B(j,i)=-Inf;%划去主元
        end
    end    
end
for i=1:m
    if R1(i)~=0
        R=[R;i,R2(i),R1(i)];%单重主元
        B(i,R2(i))=-Inf;%划去主元
    end
end
%行标分解
m=size(R,1);l=size(S,1);
for i=1:l
    H{i}=S(i,1:2);
end
k=1;
for i=1:m
    for j=1:l
        if ~isempty(intersect(R(i,1:2),H{j}))
            H{j}=union(H{j},R(i,1:2));%若交集非空则并入
            break;
        else
            if j==l
                temp(k)=i;
                k=k+1;%可能因顺序问题有些集合需要改换并入顺序
            end
        end
    end
end
i=1;
while ~isempty(temp) %补充并入
    for j=1:l
        if ~isempty(intersect(R(temp(i),1:2),H{j}))
            H{j}=union(H{j},R(temp(i),1:2));%若交集非空则并入
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
%划去连通元
for i=1:size(H,2)
    for j=1:size(H{i},2)
        for k=1:size(H{i},2)
            B(H{i}(j),H{i}(k))=-Inf;%划去连通元(主元早已划去)
        end
    end
end
%寻找连接元
m=size(H,2);
for i=1:m-1
    [temp,tempJ]=max(B(:,:),[],2);
    [temp,tempI]=max(temp);
    tempJ=tempJ(tempI);%找最大元作为连接元
    L(i,:)=[tempI,tempJ,temp];
    B(tempI,tempJ)=-Inf;B(tempJ,tempI)=-Inf;%划去连接元
    %合并变换
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
    H{j}=union(H{j},H{k});%合并H{j}与H{k}
    H(k)=[];%删除原来的H{k}
    %清理变换
    for a=1:size(H{j},2)
        for b=1:size(H{j},2)
            B(H{j}(a),H{j}(b))=-Inf;%划去剩余元
        end
    end
end
%求得基元
Cluster=[S;R;L];
end


