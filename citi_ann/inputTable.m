function [para1,para2,save,FileList]=inputTable(path)
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here
if(path(length(path))~='\')
    path=[path,'\']
end
path1=[path,'*.xlsx'];
path2=[path,'*.xls'];
FileList=dir(path1);
FileList=[FileList;dir(path2)];%������ȡ�ļ���
n=length(FileList);
name=[path,FileList(1).name];
M1=xlsread(name,'�ʲ���ծ��');
M2=xlsread(name,'�����');
M3=xlsread(name,'�ֽ�������');
para1=NetParaCal(M1,M2,M3);
para2=NetParaCal2(M1,M2,M3);
save=ParaSave(M1,M2,M3);
for i=2:n
    name=[path,FileList(i).name];
    M1=xlsread(name,'�ʲ���ծ��');
    M2=xlsread(name,'�����');
    M3=[xlsread(name,'�ֽ�������')];
    para1=[para1,NetParaCal(M1,M2,M3)];
    para2=[para2,NetParaCal2(M1,M2,M3)];
    save=[save,ParaSave(M1,M2,M3)];
end
 end

