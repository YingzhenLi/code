%change the methodology in FuzzyCluster.m & Cutting.m:
    %FuzzyCluster.m: solve fuzzy matrix
    %FCMCluster.m: FCM cluster methodology
    %MatrixClusterMethods.m: clustering and cutting methods
    %clusterplot.m: plot diagrams
    %NanoNeuralNet.m: basic BP or RBF networks
    %to be edited:fuzzy neural networks(anfis%gitfis1)
%Commands goes here--------------------------------------------------------
%fitting
%clustering
ClusterTestShort=FuzzyCluster(TestShort(:,1:5),0.7 );
[Cluster,BGtestShort,setTestShort,ratioTestShort]...
    =Cutting(ClusterTestShort,[0.6,0.7]);
%view biograph
%for i=1:size(BGtest2,2)
%    view(BGtest2{i});
%    [figTest2{i},staTest2{i}]=clusterplot(setTest2{i},test2,...
%        min(Cluster{i}(:,3)));
%end
%training
for i=1:size(setTestShort,2)
    NanoNet{i}=NanoNeuralNet(TestShort(:,1:4),setTestShort{i},TestShort(:,5));
end
%predicting
distance=[12*ones(1,3),13*ones(1,3),14*ones(1,3)];
voltage=[9:2:13,9:2:13,9:2:13];
concentration=12*ones(1,9);velocity=0.5*ones(1,9);dcdmf=9*ones(1,9);
electric=fitpara*[1;distance;voltage;distance.*voltage;voltage.^2];
velocity=velocity.*1000./pi./0.18^2./3600;
for i=1:size(setTestShort,2)
    TestSim{i}=sim(NanoNet{i},[concentration;dcdmf;electric;velocity]');
end