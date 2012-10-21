function [ Cluster,B,set,ratio ] = Cutting( dataCluster,t )
%���ǵ����ܽ��ж�μ�֦��������м�֦�Ľ��
N=size(t,2);
n=size(dataCluster,1)+1;
Ratio1=[std(dataCluster(:,3)),mean(dataCluster(:,3))];%�ȸ����������ֱ��ʺ;�ֵ
for I=1:N
    if I==1
        Cluster{I}=dataCluster;
    else
        Cluster{I}=Cluster{I-1};
    end
%--------------------------------------------------------------------------
%STEP1��֦
%��ֱ̬�Ӿ��෨��֦--------
    i=1;tempmax=0;
    while i<=size(Cluster{I},1)
        if Cluster{I}(i,3)<t(I)
            if tempmax<Cluster{I}(i,3)
                tempmax=Cluster{I}(i,3);%��¼����֦�ߵ����Ȩ��
            end
            Cluster{I}(i,:)=[];
            i=i-1;
        end
        i=i+1;
    end
%--------------------------------------------------------------------------
%STEP2���༯��
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
                set{I}{i}=union(set{I}{i},temp(j,:));%����뼯��
                temp(j,:)=[];j=1;%ɾȥ����ӽڵ㲢��������
            else
                j=j+1;
            end
        end
        scatter=setdiff(scatter,set{I}{i});
        if size(temp,1)>0%��ʣ��ڵ�        
            i=i+1;
            set{I}{i}=temp(1,:);temp(1,:)=[];%�����¼���
            scatter=setdiff(scatter,set{I}{i});
        end
    end
    if ~isempty(scatter)
        set{I}{i+1}=scatter;
    end
%--------------------------------------------------------------------------
%STEP3�������ֱ��ʷ���
    if tempmax~=0
        tempmin=min(Cluster{I}(:,3));%����ͼ����С��Ȩ��
        ratio(I)=tempmin/tempmax;
    else
        Cluster(I)=[];set(I)=[];%clean
        return;
    end
%--------------------------------------------------------------------------
%STEP4����ͨͼ
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
ratio=[ratio,Ratio1];%�������������ֱ��ʺ;�ֵ
end
