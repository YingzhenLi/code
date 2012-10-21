/*
 * ���ߣ�����   ����0802 2008000757
 * ���ݣ�BP�������㷨
 */
package com.sevenEleven.neuro;



//BP�㷨��
public class NetAlgo 
{
	// ��һ������¼�м������磬�����¼ÿһ���ж��ٸ���Ԫ��ֱ��Ĭ��Ϊ2�㣩
	private int[] netLayers;

	// ��ά��������Ԫ����̬���䣬ÿһά��һ���ȳ���Ĭ��Ϊ1��14�2�㣿�
	private Neuron[][] neuroArray;

	// ����ά�����������Ȩ�أ�ÿһά��һ���ȳ�Delta
	private double[][][] netWeight;

	// ���ڴ洢ǰһ�εı仯Ȩ��,��������Ӱ�ӣ�д�������û���ѡ�Ƿ�ʹ�ö���Ӱ�ӣ�
	private double[][][] deltaWeight;

	// ԭʼ����ѧϰ��
	private double[][] origLearnData;

	// ��һ�����ѧϰ���ݼ�����Ϊ��׼����������ֵ���Ա�׼ֵ����
	private double[][] learnData;

	// ԭʼ�������ݼ�
	private double[][] origGenerData;

	// ��һ����ķ������ݼ�
	private double[][] generData;

	// ��������Сֵ�����ڹ�һ��,0�д���Сֵ��1�д����ֵ
	private double[][] minMax;

	// ����������
	private double[] meanErrSum;

	// �����������
	private double[] relaErrSum;

	// ѧϰ����
	private double eta = 0.8;

	// ����������
	private double alpha = 0.7;

	// ѧϰҪ�ﵽ������
	private double precise = 0.0005;

	// �������ѧϰ����
	private int maxCount = 2000;

	// �Ƿ�����ԭ������
	private boolean isHaveNet;

	// �㷨ִ������,1��ʾѧϰ��3��ʾ����
	private int execType = 1;
	
	private double err=0;
	
	private double realNewOut;

	public NetAlgo()
	{
		
	}

	// ��������ṹ---������ÿ��ڵ����
	public void setLayers(int[] layers)
	{
		netLayers = layers;
	}

	// ���������д���㷨��
	public void setParameter(double[] parameter)
	{
		// ѧϰ����
		eta = parameter[0];
		// ����������
		alpha = parameter[1];
		// ѧϰҪ�ﵽ������
		precise = parameter[2];
		// �������ѧϰ����
		maxCount = (int) parameter[3];
	}

	// ��������ṹ---��ʼ������
	public void setNeuros(Neuron[][] neurons)
	{
		neuroArray = neurons;
	}

	// �������������Ȩֵ
	public void setWeight(double[][][] weights)
	{
		netWeight = weights;
	}

	// ��ʼ��deltaWeight
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

	// ��������Ȩֵ
	public void setweights()
	{
		// ��ʼ������������Ȩֵ neuroArray
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

	// ���Ȩֵ��
	public double[][][] getWeight()
	{
		return netWeight;
	}

	// ����ѧϰ���ݼ�
	public void setLearnData(double[][] data)
	{
		// ����ѧϰ���ݼ�
		origLearnData = data;
	}

	// �����������
	public void setGenarData(double[][] data)
	{
		// ���뷺�����ݼ�
		origGenerData = data;
	}



	// ����ʹ���µ�����ѧϰ
	public void setNoNet()
	{
		isHaveNet = false;
	}

	// �㷨ִ������,1��ʾѧϰ��2��ʾ����
	public void setExecType(int type)
	{
		execType = type;
	}

	// ��ʼ�������Сֵ��,�����ڴ�Ź�һ���������
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

	// �ҳ����ݼ��������СֵΪ��һ����׼��
	private void findMaxMin(boolean flag)
	{
		double[][] tempData;
		if (flag)
		{
			// ��ѧϰ�������������Сֵ
			tempData = origLearnData;
		} else
		{
			// �ڷ����������������Сֵ
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

	// �����ݽ��й�һ��
	private void unitaryData(boolean flag)
	{
		double[][] tempData;
		if (flag)
		{
			// ��ѧϰ���ݽ��й�һ��
			tempData = origLearnData;
		} else
		{
			// �Է������ݽ��й�һ��
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
		// �Է������ݽ��й�һ��
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
			// ����ѧϰ����
			learnData = data;
		} else
		{
			// ���ط�������
			generData = data;
		}
	}

	// ����(����ѧϰ���ݺͷ�������)��һ��
	public void normal()
	{
		// ��ʼ�������Сֵ��
		initMaxMin();
		// ����ѧϰ���ݼ����ҵ������Сֵ
		findMaxMin(true);
		// �ڷ������ݼ����ҵ������Сֵ
		findMaxMin(false);
		// ��ѧϰ���ݽ��й�һ��
		unitaryData(true);
		// �Է������ݽ��й�һ��
		unitaryData(false);
	}

	// ��ʼ��������
	public void initNet()
	{
		// ��ʼ�����������ڵ�
		neuroArray = new Neuron[netLayers[0]][];
		for (int i = 0; i <netLayers[0]; i++)
		{
			neuroArray[i] = new Neuron[netLayers[i +1]];
			for (int j = 0; j <neuroArray[i].length; j++)
			{
				neuroArray[i][j] = new Neuron();
			}
		}
		// ��ʼ������������Ȩֵ
		setweights();
	}

	// �����뵽��������ǰ�����
	public void forwardCacu(double[] sample)
	{
		int layerCount = netLayers[0];
		int inCount = netLayers[1];
		int outCount = netLayers[netLayers.length -1];

		// ��ѧϰ�������������
		for (int j = 0; j <inCount; j++)
		{
			neuroArray[0][j].setInput(sample[j]);
		
		}
		// ������������������
		for (int j = 0; j <outCount; j++)
		{
			neuroArray[layerCount -1][j].setDesireOut(sample[inCount +j]);
		}

		// ��ǰ����ÿһ������
		// ��������⴦��
		for (int j = 0; j <neuroArray[0].length; j++)
		{
			neuroArray[0][j].straight();
		}
		// ����������
		for (int j = 1; j <layerCount; j++)
		{
			// ����ÿһ���ÿһ����Ԫ
			for (int k = 0; k <neuroArray[j].length; k++)
			{
				// ��ǰһ���ÿһ����Ԫ�������Ȩ�صĳ˻�����
				double inValue = 0;
				for (int l = 0; l <neuroArray[j -1].length; l++)
				{
					// Ҫ�˵�Ȩֵ
					double weight = netWeight[j -1][l][k];
					inValue += neuroArray[j -1][l].getOutput() *weight;
				}
				neuroArray[j][k].setInput(inValue);
				// ���񾭽��к�������
				neuroArray[j][k].transfer();
			}
		}
	}

	// �������������
	public void clsErrArray(int outCount)
	{
		for (int i = 0; i <outCount; i++)
		{
			meanErrSum[i] = 0;
			relaErrSum[i] = 0;
		}
	}
	int i=0;
	// ������
	public void errorCacu()
	{
		int layerCount = netLayers[0];
		int inCount = netLayers[1];
		int outCount = netLayers[netLayers.length -1];
		
		for (int j = 0; j <outCount; j++)
		{
			// �������
			double newOut = neuroArray[layerCount -1][j].getOutput();
			double desireOut = neuroArray[layerCount -1][j].getDesireOut();
			meanErrSum[j] += 0.5 *(desireOut -newOut) *(desireOut -newOut);
			// ����һ������������
			// ʵ�����ֵ
			double realNewOut = (minMax[1][inCount +j] -minMax[0][inCount +j])
					*newOut +minMax[0][inCount +j];
			double realDesireOut = (minMax[1][inCount +j] -minMax[0][inCount +j])
					*desireOut +minMax[0][inCount +j];
			relaErrSum[j] += Math.abs((realNewOut -realDesireOut) /realNewOut);
			
			
		}
	}

	// ����ƽ���������Ƿ�ﵽ�㹻����,���㾫��Ҫ���򷵻�true
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
		// �����������򷵻�true
		return ok;
	}

	// ��������½�����
	public void deltaCacu()
	{
		int layerCount = netLayers[0];
		int outCount = netLayers[netLayers.length -1];

		// �������㣻
		// �������һ���delta
		for (int j = 0; j <outCount; j++)
		{
			double newOut = neuroArray[layerCount -1][j].getOutput();
			double desireOut = neuroArray[layerCount -1][j].getDesireOut();
			double temDelta = newOut *(1 -newOut) *(desireOut -newOut);
			neuroArray[layerCount -1][j].setDelta(temDelta);
		}

		// ����ǰ������delta�ĸ���
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

	// ����Ȩ��
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


	// ������ѧϰ�㷨
	private void BpLearn()
	{
		// ���ֵ��������ʼ����������������
		int outCount = netLayers[netLayers.length -1];
		// �����������ʼ��
		meanErrSum = new double[outCount];
		// �����������ʼ��
		relaErrSum = new double[outCount];
		// ��ʼ������������

		if (!isHaveNet)
		{
			initNet();
		}
		// ��ʼ��DeltaWeight
		initDeltaWeight();
		// ��һ��
		normal();
		int sum = 0;
		System.out.print("ѵ����ʼ\n");
		do
		{
			// ������������գ�����learnData.length��ʾѵ�������Ĵ�С��
			clsErrArray(outCount);
			for (int i = 0; i <learnData.length; i++)
			{
				// ǰ�����
				forwardCacu(learnData[i]);
				// ��������½�����
				deltaCacu();
				// ����Ȩֵ��
				renewWeight();
				// ������
				errorCacu();
			}
			sum++;
			

		} while (!isOk(sum) &&sum <maxCount);
		System.out.print("ѵ��������\n\n");
	}


	// ����������
	public void generErr(int i)
	{
		int layerCount = netLayers[0];
		int inCount = netLayers[1];
		int outCount = netLayers[netLayers.length -1];

		for (int j = 0; j <outCount; j++)
		{
			// �������
			double desireOut = neuroArray[layerCount -1][j].getDesireOut();
			
			// ʵ�����
			double newOut = neuroArray[layerCount -1][j].getOutput();
			
			// ����һ������������
			// ʵ�����ֵ
			realNewOut = (minMax[1][inCount +j] -minMax[0][inCount +j])
					*newOut +minMax[0][inCount +j];
			double realDesireOut = (minMax[1][inCount +j] -minMax[0][inCount +j])
					*desireOut +minMax[0][inCount +j];
			double relaErr = Math.abs((realNewOut -realDesireOut) /realNewOut);

			err+=relaErr;
			//System.out.print("�������"+realDesireOut+"   "/*+"ʵ�����"+realNewOut+"   "+"���"+relaErr+"\n");
			
			System.out.print("���֣�"+realNewOut+"\n");
		}
	
		
	}
	
	public double getNeuroResult(){
		return realNewOut;
	}

	
	

	// Bp�����緺����Ԥ�⣩
	private void BpGeneral()
	{
		

		// ��ʼ��������ֵ�����ֵ���ȶ�����
		for (int i = 0; i <generData.length; i++)
		{
			// ǰ�����
			forwardCacu(generData[i]);
			// ������
			generErr(i);
			
		}
		System.out.print("ƽ����"+err/generData.length);

	}



	public void exe() {
		// TODO Auto-generated method stub
		switch (execType)
		{
			case 1://ѵ��
				BpLearn();
				break;
			case 2://������Ԥ�⣩
				BpGeneral();
				break;
			default:
				break;
		}
	}
}