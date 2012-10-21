[ZData,mu,std]=zscore(data);
pdistData=pdist(ZData(:,4:7));
L=linkage(pdistData,'single');
f1=dendrogram(L);
C=cophenet(L,pdistData);
T=cluster(L,'cutoff',1);
find(T==2);
f2=scatter3(L(:,1),L(:,2),L(:,3),100,'filled');