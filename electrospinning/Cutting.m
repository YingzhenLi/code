function [ Cluster,B,set,ratio ] = Cutting( dataCluster,t )
%考虑到可能进行多次剪枝，输出所有剪枝的结果
N=size(t,2);
n=size(dataCluster,1)+1;
Ratio1=[std(dataCluster(:,3)),mean(dataCluster(:,3))];%先给出整体聚类分辨率和均值
for I=1:N
    if I==1
        Cluster{I}=dataCluster;
    else
        Cluster{I}=Cluster{I-1};
    end
%--------------------------------------------------------------------------
%STEP1剪枝
%动态直接聚类法剪枝--------
    i=1;tempmax=0;
    while i<=size(Cluster{I},1)
        if Cluster{I}(i,3)<t(I)
            if tempmax<Cluster{I}(i,3)
                tempmax=Cluster{I}(i,3);%记录被剪枝边的最大权重
            end
            Cluster{I}(i,:)=[];
            i=i-1;
        end
        i=i+1;
    end
%--------------------------------------------------------------------------
%STEP2聚类集合
    temp=Cluster{I}(:,1:2);
    set{I}{1}=temp(1,:);temp(1,:)=[];i=1;
    if I==1
        scatter=1:n;
    else
        scatter=setdiff(1:n,scatter);
    end
    while size(temp,1)>0
        j=1;
        while j<=size(temp,1)
            if ~isempty(intersect(set{I}{i},temp(j,:)))
                set{I}{i}=union(set{I}{i},temp(j,:));%添加入集合
                temp(j,:)=[];j=1;%删去已添加节点并重新搜索
            else
                j=j+1;
            end
        end
        scatter=setdiff(scatter,set{I}{i});
        if size(temp,1)>0%有剩余节点        
            i=i+1;
            set{I}{i}=temp(1,:);temp(1,:)=[];%建立新集合
            scatter=setdiff(scatter,set{I}{i});
        end
    end
    if ~isempty(scatter)
        set{I}{i+1}=scatter;
    end
%--------------------------------------------------------------------------
%STEP3分类聚类分辨率分析
    if tempmax~=0
        tempmin=min(Cluster{I}(:,3));%聚类图中最小边权重
        ratio(I)=tempmin/tempmax;
    else
        Cluster(I)=[];set(I)=[];%clean
        return;
    end
%--------------------------------------------------------------------------
%STEP4绘连通图
    B{I}=zeros(n);
    row=size(Cluster{I},1);
    for i=1:row
        if(Cluster{I}(i,:)>0)
            B{I}(Cluster{I}(i,1),Cluster{I}(i,2))=Cluster{I}(i,3);
        end
    end
    ids=cell(1,n);
    for i=1:n
        ids{i}=int2str(i);
    end
    B{I}=biograph(B{I},ids,'ShowArrows', 'off','ShowWeights', 'on',...
        'LayoutType','radial');
end
ratio=[ratio,Ratio1];%最后两项是整体分辨率和均值
end
