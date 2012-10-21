package com.sevenEleven.neuro;


public class SetNet
{
	// ���������ÿ����Ԫ����
	int[] layers;
	// �������ô����������
	double[] parameter = new double[4];

	public SetNet()
	{
		// ����Ĭ�ϵ�����
		initlayers();
	}

	// ����Ĭ�ϵ�����
	private void initlayers()
	{
		// ��ʼ����������
		layers = new int[4];
		layers[0] = 3;
		layers[1] = 20;
		layers[2] = 15;
		layers[3] = 1;
		// ��ʼ����������
		// ѧϰ����
		parameter[0] = 0.9;
		// ��������
		parameter[1] = 0.5;
		// ����Ҫ��
		parameter[2] = 0.01;
		// ��������ѭ������
		parameter[3] = 2000;
	}
	
	// ��ȡ���������ÿ����Ԫ����
	public int[] getLayers()
	{
		return layers;
	}
	
	// ��ȡ�������
	public double[] getParameter()
	{
		return parameter;
	}
}
