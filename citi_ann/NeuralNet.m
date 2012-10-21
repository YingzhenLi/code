function OUTPUT = NeuralNet(trdata,trResult,test)
%UNTITLED4 Summary of this function goes here
%   Detailed explanation goes here
net=newff(minmax(trdata),[14,7],{'tansig','logsig'},'trainrp','learngdm');
net.trainParam.goal=0.0005;
net.trainParam.epochs=10000;
[net,tr]=train(net,trdata,trResult);
OUTPUT=sim(net,test);
end

