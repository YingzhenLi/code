/*
 * 作者：张珩   信研0802 2008000757
 * 内容：BP神经网络算法
 */
package com.sevenEleven.neuro;



//BP算法类
public class NetAlgo 
{
	// 第一个数记录有几层网络，后面记录每一层有多少个神经元（直接默认为2层）
	private int[] netLayers;

	// 二维数组存放神经元，动态分配，每一维不一定等长（默认为1层14项，2层？项）
	private Neuron[][] neuroArray;

	// 用三维数组存放网络的权重，每一维不一定等长Delta
	private double[][][] netWeight;

	// 用于存储前一次的变化权重,用作动量影子（写函数让用户可选是否使用动量影子）
	private double[][][] deltaWeight;

	// 原始数据学习集
	private double[][] origLearnData;

	// 归一化后的学习数据集（改为标准化（以输入值除以标准值））
	private double[][] learnData;

	// 原始泛化数据集
	private double[][] origGenerData;

	// 归一化后的泛化数据集
	private double[][] generData;

	// 存放最大最小值，用于归一化,0行存最小值，1行存最大值
	private double[][] minMax;

	// 均方差数组
	private double[] meanErrSum;

	// 相对误差和数组
	private double[] relaErrSum;

	// 学习速率
	private double eta = 0.8;

	// 动量项因子
	private double alpha = 0.7;

	// 学习要达到的误差精度
	private double precise = 0.0005;

	// 最多允许学习次数
	private int maxCount = 2000;

	// 是否利用原有网络
	private boolean isHaveNet;

	// 算法执行类型,1表示学习，3表示泛化
	private int execType = 1;
	
	private double err=0;
	
	private double realNewOut;

	public NetAlgo()
	{
		
	}

	// 设置网络结构---层数和每层节点个数
	public void setLayers(int[] layers)
	{
		netLayers = layers;
	}

	// 将网络参数写入算法类
	public void setParameter(double[] parameter)
	{
		// 学习速率
		eta = parameter[0];
		// 动量项因子
		alpha = parameter[1];
		// 学习要达到的误差精度
		precise = parameter[2];
		// 最多允许学习次数
		maxCount = (int) parameter[3];
	}

	// 设置网络结构---初始化网络
	public void setNeuros(Neuron[][] neurons)
	{
		neuroArray = neurons;
	}

	// 设置网络的连接权值
	public void setWeight(double[][][] weights)
	{
		netWeight = weights;
	}

	// 初始化deltaWeight
	public void initDeltaWeight()
	{
		deltaWeight = new double[neuroArray.length -1][][];
		for (int i = 0; i <neuroArray.length -1; i++)
		{
			deltaWeight[i] = new double[neuroArray[i].length][];
			for (int j = 0; j <neuroArray[i].length; j++)
			{
				deltaWeight[i][j] = new double[neuroArray[i +1].length];
				for (int k = 0; k <neuroArray[i +1].length; k++)
				{
					deltaWeight[i][j][k] = Math.random();
				}
			}
		}
	}

	// 设置网络权值
	public void setweights()
	{
		// 初始化神经网络连接权值 neuroArray
		netWeight = new double[neuroArray.length -1][][];
		for (int i = 0; i <neuroArray.length -1; i++)
		{
			netWeight[i] = new double[neuroArray[i].length][];
			for (int j = 0; j <neuroArray[i].length; j++)
			{
				netWeight[i][j] = new double[neuroArray[i +1].length];
				for (int k = 0; k <neuroArray[i +1].length; k++)
				{
					netWeight[i][j][k] = Math.random();
				}
			}
		}
	}

	// 获得权值表
	public double[][][] getWeight()
	{
		return netWeight;
	}

	// 载入学习数据集
	public void setLearnData(double[][] data)
	{
		// 导入学习数据集
		origLearnData = data;
	}

	// 载入测试数据
	public void setGenarData(double[][] data)
	{
		// 导入泛化数据集
		origGenerData = data;
	}



	// 设置使用新的网络学习
	public void setNoNet()
	{
		isHaveNet = false;
	}

	// 算法执行类型,1表示学习，2表示泛化
	public void setExecType(int type)
	{
		execType = type;
	}

	// 初始化最大最小值表,将用于存放归一化后的数据
	private void initMaxMin()
	{
		int col;
		if (origLearnData !=null)
		{
			col = origLearnData[0].length;
		} else
		{
			col = origGenerData[0].length;
		}
		minMax = new double[2][col];
		for (int i = 0; i <col; i++)
		{
			minMax[0][i] = minMax[1][i] = origLearnData[0][i];
		}
	}

	// 找出数据集中最大最小值为归一化做准备
	private void findMaxMin(boolean flag)
	{
		double[][] tempData;
		if (flag)
		{
			// 在学习数据中找最大最小值
			tempData = origLearnData;
		} else
		{
			// 在泛化数据中找最大最小值
			tempData = origGenerData;
		}
		if (tempData !=null)
		{
			for (int i = 0; i <tempData.length; i++)
			{
				for (int j = 0; j <tempData[i].length; j++)
				{
					if (tempData[i][j] <minMax[0][j])
					{
						minMax[0][j] = tempData[i][j];
					}
					if (tempData[i][j] >minMax[1][j])
					{
						minMax[1][j] = tempData[i][j];
					}
				}
				
			}

		}
	}

	// 对数据进行归一化
	private void unitaryData(boolean flag)
	{
		double[][] tempData;
		if (flag)
		{
			// 对学习数据进行归一化
			tempData = origLearnData;
		} else
		{
			// 对泛化数据进行归一化
			tempData = origGenerData;
			
		}
		int row, col;
		if (tempData !=null)
		{
			row = tempData.length;
			col = tempData[0].length;
		} else
		{
			row = col = 0;
		}
		double[][] data = new double[row][col];
		// 对泛化数据进行归一化
		for (int i = 0; i <row; i++)
		{
			for (int j = 0; j <col; j++)
			{
				data[i][j] = (tempData[i][j] -minMax[0][j])
						/(minMax[1][j] -minMax[0][j]);
			}
			
			
		}
		if (flag)
		{
			// 返回学习数据
			learnData = data;
		} else
		{
			// 返回泛化数据
			generData = data;
		}
	}

	// 数据(包括学习数据和泛化数据)归一化
	public void normal()
	{
		// 初始化最大最小值表
		initMaxMin();
		// 先在学习数据集中找到最大最小值
		findMaxMin(true);
		// 在泛化数据集中找到最大最小值
		findMaxMin(false);
		// 对学习数据进行归一化
		unitaryData(true);
		// 对泛化数据进行归一化
		unitaryData(false);
	}

	// 初始化神经网络
	public void initNet()
	{
		// 初始化神经网络各层节点
		neuroArray = new Neuron[netLayers[0]][];
		for (int i = 0; i <netLayers[0]; i++)
		{
			neuroArray[i] = new Neuron[netLayers[i +1]];
			for (int j = 0; j <neuroArray[i].length; j++)
			{
				neuroArray[i][j] = new Neuron();
			}
		}
		// 初始化神经网络连接权值
		setweights();
	}

	// 从输入到输出层进行前向计算
	public void forwardCacu(double[] sample)
	{
		int layerCount = netLayers[0];
		int inCount = netLayers[1];
		int outCount = netLayers[netLayers.length -1];

		// 将学习数据输入输入层
		for (int j = 0; j <inCount; j++)
		{
			neuroArray[0][j].setInput(sample[j]);
		
		}
		// 将期望输出存于输出层
		for (int j = 0; j <outCount; j++)
		{
			neuroArray[layerCount -1][j].setDesireOut(sample[inCount +j]);
		}

		// 向前计算每一层网络
		// 输入层特殊处理
		for (int j = 0; j <neuroArray[0].length; j++)
		{
			neuroArray[0][j].straight();
		}
		// 计算其它层
		for (int j = 1; j <layerCount; j++)
		{
			// 对于每一层的每一个神经元
			for (int k = 0; k <neuroArray[j].length; k++)
			{
				// 将前一层的每一个神经元的输出与权重的乘积加入
				double inValue = 0;
				for (int l = 0; l <neuroArray[j -1].length; l++)
				{
					// 要乘的权值
					double weight = netWeight[j -1][l][k];
					inValue += neuroArray[j -1][l].getOutput() *weight;
				}
				neuroArray[j][k].setInput(inValue);
				// 该神经进行函数传递
				neuroArray[j][k].transfer();
			}
		}
	}

	// 误差类和数组清空
	public void clsErrArray(int outCount)
	{
		for (int i = 0; i <outCount; i++)
		{
			meanErrSum[i] = 0;
			relaErrSum[i] = 0;
		}
	}
	int i=0;
	// 误差计算
	public void errorCacu()
	{
		int layerCount = netLayers[0];
		int inCount = netLayers[1];
		int outCount = netLayers[netLayers.length -1];
		
		for (int j = 0; j <outCount; j++)
		{
			// 求均方差
			double newOut = neuroArray[layerCount -1][j].getOutput();
			double desireOut = neuroArray[layerCount -1][j].getDesireOut();
			meanErrSum[j] += 0.5 *(desireOut -newOut) *(desireOut -newOut);
			// 反归一化，求相对误差
			// 实际输出值
			double realNewOut = (minMax[1][inCount +j] -minMax[0][inCount +j])
					*newOut +minMax[0][inCount +j];
			double realDesireOut = (minMax[1][inCount +j] -minMax[0][inCount +j])
					*desireOut +minMax[0][inCount +j];
			relaErrSum[j] += Math.abs((realNewOut -realDesireOut) /realNewOut);
			
			
		}
	}

	// 计算平均相对误差是否达到足够精度,满足精度要求则返回true
	public boolean isOk(int sum)
	{
		boolean ok = true;
		int outCount = netLayers[netLayers.length -1];
		for (int i = 0; i <outCount; i++)
		{
			double meanErrRate = meanErrSum[i] /learnData.length;
			double relaErrRate = relaErrSum[i] /learnData.length;


		}
		for (int i = 0; i <outCount; i++)
		{
			double meanErrRate = meanErrSum[i] /learnData.length;
			if (meanErrRate >precise)
			{
				ok = false;
				break;
			}
		}
		// 不满足条件则返回true
		return ok;
	}

	// 反向计算下降因子
	public void deltaCacu()
	{
		int layerCount = netLayers[0];
		int outCount = netLayers[netLayers.length -1];

		// 误差反传计算；
		// 对于最后一层的delta
		for (int j = 0; j <outCount; j++)
		{
			double newOut = neuroArray[layerCount -1][j].getOutput();
			double desireOut = neuroArray[layerCount -1][j].getDesireOut();
			double temDelta = newOut *(1 -newOut) *(desireOut -newOut);
			neuroArray[layerCount -1][j].setDelta(temDelta);
		}

		// 对于前面各层的delta的更新
		for (int j = layerCount -2; j >=0; j--)
		{
			for (int k = 0; k <neuroArray[j].length; k++)
			{
				double newOut = neuroArray[j][k].getOutput();
				double desireOut = 0;
				for (int l = 0; l <neuroArray[j +1].length; l++)
				{
					double tempWeight = netWeight[j][k][l];
					desireOut += neuroArray[j +1][l].getDelta() *tempWeight;
				}
				double temDelta = newOut *(1 -newOut) *desireOut;
				neuroArray[j][k].setDelta(temDelta);
			}
		}
	}

	// 调节权重
	public void renewWeight()
	{
		int layerCount = netLayers[0];
		for (int j = 0; j <layerCount -1; j++)
		{
			for (int k = 0; k <neuroArray[j].length; k++)
			{
				for (int l = 0; l <neuroArray[j +1].length; l++)
				{
					double deWeight = eta *neuroArray[j +1][l].getDelta()
							*neuroArray[j][k].getOutput();
					netWeight[j][k][l] = netWeight[j][k][l] +deWeight +alpha
							*deltaWeight[j][k][l];
					deltaWeight[j][k][l] = deWeight;
				}
			}
		}
	}


	// 神经网络学习算法
	private void BpLearn()
	{
		// 输出值个数，初始化存放类和误差的数组
		int outCount = netLayers[netLayers.length -1];
		// 均方差数组初始化
		meanErrSum = new double[outCount];
		// 相对误差数组初始化
		relaErrSum = new double[outCount];
		// 初始化曲线类数组

		if (!isHaveNet)
		{
			initNet();
		}
		// 初始化DeltaWeight
		initDeltaWeight();
		// 归一化
		normal();
		int sum = 0;
		System.out.print("训练开始\n");
		do
		{
			// 误差类和数组清空（这里learnData.length表示训练样本的大小）
			clsErrArray(outCount);
			for (int i = 0; i <learnData.length; i++)
			{
				// 前向计算
				forwardCacu(learnData[i]);
				// 反向计算下降因子
				deltaCacu();
				// 调整权值；
				renewWeight();
				// 误差计算
				errorCacu();
			}
			sum++;
			

		} while (!isOk(sum) &&sum <maxCount);
		System.out.print("训练结束！\n\n");
	}


	// 泛化误差计算
	public void generErr(int i)
	{
		int layerCount = netLayers[0];
		int inCount = netLayers[1];
		int outCount = netLayers[netLayers.length -1];

		for (int j = 0; j <outCount; j++)
		{
			// 期望输出
			double desireOut = neuroArray[layerCount -1][j].getDesireOut();
			
			// 实际输出
			double newOut = neuroArray[layerCount -1][j].getOutput();
			
			// 反归一化，求相对误差
			// 实际输出值
			realNewOut = (minMax[1][inCount +j] -minMax[0][inCount +j])
					*newOut +minMax[0][inCount +j];
			double realDesireOut = (minMax[1][inCount +j] -minMax[0][inCount +j])
					*desireOut +minMax[0][inCount +j];
			double relaErr = Math.abs((realNewOut -realDesireOut) /realNewOut);

			err+=relaErr;
			//System.out.print("期望输出"+realDesireOut+"   "/*+"实际输出"+realNewOut+"   "+"误差"+relaErr+"\n");
			
			System.out.print("评分："+realNewOut+"\n");
		}
	
		
	}
	
	public double getNeuroResult(){
		return realNewOut;
	}

	
	

	// Bp神经网络泛化（预测）
	private void BpGeneral()
	{
		

		// 初始化，期望值、误差值，先对误差表
		for (int i = 0; i <generData.length; i++)
		{
			// 前向计算
			forwardCacu(generData[i]);
			// 误差计算
			generErr(i);
			
		}
		System.out.print("平均误差："+err/generData.length);

	}



	public void exe() {
		// TODO Auto-generated method stub
		switch (execType)
		{
			case 1://训练
				BpLearn();
				break;
			case 2://泛化（预测）
				BpGeneral();
				break;
			default:
				break;
		}
	}
}