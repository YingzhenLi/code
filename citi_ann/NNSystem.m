function [out,r]=NNSystem(M1,M2,M3,sheet)
%UNTITLED5 Summary of this function goes here
%   Detailed explanation goes here
trdata=inputCal(M1,M2,M3);
y=input('请输入最后一年数据所占比重：')
x=1-y;
trdata=ParaCheck(trdata);
trdata=inputPortion(trdata,x,y);
out=NeuralNet(trdata,sheet,trdata);
r=mean(var(sheet-out));
end

