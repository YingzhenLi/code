package com.sevenEleven.neuro;

//��Ԫ��
public class Neuron
{
	//����ֵ
	private double input;
	//���ֵ
	private double output;
	//�������ֵ
	private double desirOut;
	// ��������
	private double delta;

	public Neuron()
	{
		input = 0;
	}

	// ����logsig���������ݺ���
	public void transfer()
	{
		double temp;
		temp = 1 +Math.exp(-(input-0.5));//0.5 Ϊ��ֵ
		output = 1 /temp;
	}
	//����tansig���������ݺ�������Ч������
//	public void transfer()
//	{
//		double temp;
//		temp = 1 +Math.exp(-2*input);
//		output = 2 /temp - 1;
//	}
	public void setInput(double inValue)
	{
		input=inValue;
	}
	
	public void straight()
	{
		output = input;
	}



	public double getOutput()
	{
		return output;
	}

	public void setDesireOut(double out)
	{
		desirOut = out;
	}

	public double getDesireOut()
	{
		return desirOut;
	}

	public double getDelta()
	{
		return delta;
	}

	public void setDelta(double newDelt)
	{
		delta = newDelt;
	}
}
