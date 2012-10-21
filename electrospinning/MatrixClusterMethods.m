%���ݱհ���-----------------------------
flag=0;B=C;
while flag==0
    for i=1:n
        for j=1:n
            for k=1:n
                B(i,j)=max(min(C(i,k),C(k,j)),B(i,j));%���㴫�ݱհ�
            end
        end
    end
    if B==C
        flag=1;
    else
        C=B;
    end
end
%�������---------------------------------
%Prim�㷨--------------
x=1;B=C;
for i=1:size(B,1)
    B(i,i)=0;
end
B(:,1)=0;%��ȥ��һ���ڵ�
Cluster=[1,1,1];nest=0;nestI=0;%Ĭ���Ƚ���һ���ڵ�����Cluster
for i=1:n
    m=size(Cluster,1);
    for j=1:m
        [temp,tempI]=max(B(x,:));%�����нڵ������Ȩ��
        if temp>nest
            nest=temp;
            nestI=tempI;
            link=m;
        end
    end
    Cluster=[Cluster;link,nestI,nest];%�����Ȩ�߼���Ӧ�ڵ������Cluster
    B(:,link)=0;B(link,:)=0;%��B�г�ȥ�ýڵ�
end
%Kruskal�㷨------------
B=C;
for i=1:size(B,1)
    B(i,i)=0;
end
i=0;Cluster=[0,0,0];
while i<n %�ҵ�n���ڵ������
    [temp,tempJ]=max(B(:,:),[],2);
    [temp,tempI]=max(temp);
    tempJ=tempJ(tempI);%��Ȩ������
    for j=1:size(Cluster,1)
        if(Cluster(j,1)==tempI||Cluster(j,1)==tempJ||Cluster(j,2)==tempI...
                ||Cluster(j,2)==tempJ)
            i=i-1;%�����ӱ�����ĳ�ڵ��������Cluster�����-1
            break;
        end
    end
    i=i+2;%����+2,��if������ʵ�ʼ���+1
    Cluster=[Cluster;tempI,tempJ,temp];%���ñ������Cluster
    B(tempI,tempJ)=0;B(tempJ,tempI)=0;%���ñߴ�B��ɾ��
end
Cluster(1,:)=[];
%��ֱ̬�Ӿ��෨--------------------------
B=C;m=size(B,1);
for i=1:size(B,1)
    B(i,i)=0;
end
%Ѱ����Ԫ
[R1,R2]=max(B,[],1);S=[];R=[];
for i=1:m
    for j=i+1:m
        if R1(i)==R1(j)&&R2(i)==j&&R2(j)==i
            S=[S;i,j,R1(i)];%˫����Ԫ
            R2(i)=0;R2(j)=0;R1(i)=0;R1(j)=0;%����
            B(i,j)=0;B(j,i)=0;%��ȥ��Ԫ
        end
    end    
end
for i=1:m
    if R1(i)~=0
        R=[R;i,R2(i),R1(i)];%������Ԫ
        B(i,R2(i))=0;%��ȥ��Ԫ
    end
end
%�б�ֽ�
m=size(R,1);l=size(S,1);
for i=1:l
    H{i}=S(i,1:2);
end
for i=1:m
    for j=1:l
        if ~isempty(intersect(R(i,1:2),H{j}))
            H{j}=union(H{j},R(i,1:2));%�������ǿ�����
        end
    end
end
%��ȥ��ͨԪ
for i=1:size(H,2)
    for j=1:size(H{i},2)
        for k=1:size(H{i},2)
            B(H{i}(j),H{i}(k))=0;%��ȥ��ͨԪ(��Ԫ���ѻ�ȥ)
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
    B(tempI,tempJ)=0;B(tempJ,tempI)=0;%��ȥ����Ԫ
    %�ϲ��任
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
    H{j}=union(H{j},H{k});%�ϲ�H{j}��H{k}
    H(k)=[];%ɾ��ԭ����H{k}
    %����任
    for a=1:size(H{j},2)
        for b=1:size(H{j},2)
            B(H{j}(a),H{j}(b))=0;%��ȥʣ��Ԫ
        end
    end
end
%��û�Ԫ
Cluster=[S;R;L];
%��֦����--------------------------------
%���ݱհ�����֦------------
Cluster=B-t>=0;
%���������֦--------------
temp=find(Cluster(:,3)<t);
Cluster=Cluster(1:size(Cluster,1)-size(temp,1),:);
%��ֱ̬�Ӿ��෨��֦--------
for i=1:size(Cluster,1)
    if Cluster(i,3)<t
        Cluster(i,:)=[];
        i=i-1;
    end
end