function [out,err,net]=NanoSystem(para,sheet,test)
%UNTITLED5 Summary of this function goes here
%   BP networks normalization
paratemp(:,1)=para(:,1)./10000;testtemp(:,1)=test(:,1)./10000;
paratemp(:,2)=para(:,2)./100;testtemp(:,2)=test(:,2)./100;
paratemp(:,3)=para(:,3)./2750;testtemp(:,3)=test(:,3)./2750;
paratemp(:,4)=para(:,4)./20;testtemp(:,4)=test(:,4)./20;
paratemp(:,5)=para(:,5)./20;testtemp(:,5)=test(:,5)./20;
paratemp(:,6)=para(:,6);testtemp(:,6)=test(:,6);
%RBF networks data
%paratemp=para;testtemp=test(:,1:6);
net=NanoNeuralNet(paratemp',sheet');
out=sim(net,testtemp')';
%PNN networks
%out=vec2ind(out);
err=out-test(:,7);
end