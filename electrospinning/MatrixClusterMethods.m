%传递闭包法-----------------------------
flag=0;B=C;
while flag==0
    for i=1:n
        for j=1:n
            for k=1:n
                B(i,j)=max(min(C(i,k),C(k,j)),B(i,j));%计算传递闭包
            end
        end
    end
    if B==C
        flag=1;
    else
        C=B;
    end
end
%最大树法---------------------------------
%Prim算法--------------
x=1;B=C;
for i=1:size(B,1)
    B(i,i)=0;
end
B(:,1)=0;%除去第一个节点
Cluster=[1,1,1];nest=0;nestI=0;%默认先将第一个节点添入Cluster
for i=1:n
    m=size(Cluster,1);
    for j=1:m
        [temp,tempI]=max(B(x,:));%对现有节点找最大权边
        if temp>nest
            nest=temp;
            nestI=tempI;
            link=m;
        end
    end
    Cluster=[Cluster;link,nestI,nest];%将最大权边及对应节点添加入Cluster
    B(:,link)=0;B(link,:)=0;%在B中除去该节点
end
%Kruskal算法------------
B=C;
for i=1:size(B,1)
    B(i,i)=0;
end
i=0;Cluster=[0,0,0];
while i<n %找到n个节点则结束
    [temp,tempJ]=max(B(:,:),[],2);
    [temp,tempI]=max(temp);
    tempJ=tempJ(tempI);%找权重最大边
    for j=1:size(Cluster,1)
        if(Cluster(j,1)==tempI||Cluster(j,1)==tempJ||Cluster(j,2)==tempI...
                ||Cluster(j,2)==tempJ)
            i=i-1;%如果添加边其中某节点已添加入Cluster则计数-1
            break;
        end
    end
    i=i+2;%计数+2,若if成立则实际计数+1
    Cluster=[Cluster;tempI,tempJ,temp];%将该边添加入Cluster
    B(tempI,tempJ)=0;B(tempJ,tempI)=0;%将该边从B中删除
end
Cluster(1,:)=[];
%动态直接聚类法--------------------------
B=C;m=size(B,1);
for i=1:size(B,1)
    B(i,i)=0;
end
%寻找主元
[R1,R2]=max(B,[],1);S=[];R=[];
for i=1:m
    for j=i+1:m
        if R1(i)==R1(j)&&R2(i)==j&&R2(j)==i
            S=[S;i,j,R1(i)];%双重主元
            R2(i)=0;R2(j)=0;R1(i)=0;R1(j)=0;%清零
            B(i,j)=0;B(j,i)=0;%划去主元
        end
    end    
end
for i=1:m
    if R1(i)~=0
        R=[R;i,R2(i),R1(i)];%单重主元
        B(i,R2(i))=0;%划去主元
    end
end
%行标分解
m=size(R,1);l=size(S,1);
for i=1:l
    H{i}=S(i,1:2);
end
for i=1:m
    for j=1:l
        if ~isempty(intersect(R(i,1:2),H{j}))
            H{j}=union(H{j},R(i,1:2));%若交集非空则并入
        end
    end
end
%划去连通元
for i=1:size(H,2)
    for j=1:size(H{i},2)
        for k=1:size(H{i},2)
            B(H{i}(j),H{i}(k))=0;%划去连通元(主元早已划去)
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
    B(tempI,tempJ)=0;B(tempJ,tempI)=0;%划去连接元
    %合并变换
    temp2=[tempI,tempJ];
    j=1;
    while isempty(intersect(H{j},temp2))&&j<=size(H,2)
        j=j+1;
    end
    temp2=setdiff(temp2,H{j});
    k=j+1;
    while isempty(intersect(H{k},temp2))&&k<=size(H,2)
        k=k+1;
    end
    H{j}=union(H{j},H{k});%合并H{j}与H{k}
    H(k)=[];%删除原来的H{k}
    %清理变换
    for a=1:size(H{j},2)
        for b=1:size(H{j},2)
            B(H{j}(a),H{j}(b))=0;%划去剩余元
        end
    end
end
%求得基元
Cluster=[S;R;L];
%剪枝方法--------------------------------
%传递闭包法剪枝------------
Cluster=B-t>=0;
%最大树法剪枝--------------
temp=find(Cluster(:,3)<t);
Cluster=Cluster(1:size(Cluster,1)-size(temp,1),:);
%动态直接聚类法剪枝--------
for i=1:size(Cluster,1)
    if Cluster(i,3)<t
        Cluster(i,:)=[];
        i=i-1;
    end
end