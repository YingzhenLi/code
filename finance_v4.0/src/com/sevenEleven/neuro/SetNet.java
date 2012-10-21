package com.sevenEleven.neuro;


public class SetNet
{
	// 网络层数和每层神经元个数
	int[] layers;
	// 参数设置存放以下三项
	double[] parameter = new double[4];

	public SetNet()
	{
		// 设置默认的网络
		initlayers();
	}

	// 设置默认的网络
	private void initlayers()
	{
		// 初始化网络设置
		layers = new int[4];
		layers[0] = 3;
		layers[1] = 20;
		layers[2] = 15;
		layers[3] = 1;
		// 初始化参数设置
		// 学习速率
		parameter[0] = 0.9;
		// 动量因子
		parameter[1] = 0.5;
		// 误差精度要求
		parameter[2] = 0.01;
		// 最大允许的循环次数
		parameter[3] = 2000;
	}
	
	// 获取网络层数及每层神经元个数
	public int[] getLayers()
	{
		return layers;
	}
	
	// 获取网络参数
	public double[] getParameter()
	{
		return parameter;
	}
}
