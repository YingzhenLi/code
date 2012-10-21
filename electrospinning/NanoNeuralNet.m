function NanoNet = NanoNeuralNet(trdata,trSet,trResult)
%analyze the data using neural networks
%translate clustering data
n=size(trResult,2);
for i=1:size(trSet,2)
    for j=1:size(trSet{i},2)
        trResult(trSet{i}(j),n+1)=i;
    end
end
%BP networks
NanoNet=newcf(trdata',trResult',[4,2],{'tansig','poslin'},'traingdm','learngdm');
NanoNet.trainParam.goal=0.005;
NanoNet.trainParam.epochs=10000;
NanoNet.divideFcn ='';
[NanoNet,tr]=train(NanoNet,trdata',trResult');

%RBF networks
%NanoNet=newrb(trdata',trResult',0,1.1);

%PNN networks, patten recognition
%trResultC=ind2vec(trResult);
%NanoNet=newpnn(trdata,trResultC);

%GRNN networks
%NanoNet=nwegrnn(trdata,trResult);
end

