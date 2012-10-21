package com.sevenEleven.fuzzy;

import java.util.HashMap;

public class Fuzzy {
	
	//one input, one output
    final int left1 = -1, right1 = 11;
	
	final double inputmf1fst = -4;
	final double inputmf1mid = 0;
	final double inputmf1lst = 4;
	final double inputmf2fst = 1;
	final double inputmf2mid = 5;
	final double inputmf2lst = 9;
	final double inputmf3fst = 6;
	final double inputmf3mid = 10;
	final double inputmf3lst = 14;
	
	final double outputmf1fst = -2.5;
	final double outputmf1mid = -1;
	final double outputmf1lst = 2.5;
	final double outputmf2fst = 0.5;
	final double outputmf2mid = 5;
	final double outputmf2lst = 9.5;
	final double outputmf3fst = 7.5;
	final double outputmf3mid = 11;
	final double outputmf3lst = 14.5;
	
	//two inputs, one output
	final int left2 = -3, right2 = 13;
	
	final double input1mf1fst = -3;
	final double input1mf1mid = 0;
	final double input1mf1lst = 3;
	final double input1mf2fst = 2;
	final double input1mf2mid = 5;
	final double input1mf2lst = 8;
	final double input1mf3fst = 7;
	final double input1mf3mid = 10;
	final double input1mf3lst = 13;
	
	final double input2mf1fst = -3;
	final double input2mf1mid = 0;
	final double input2mf1lst = 3;
	final double input2mf2fst = 7;
	final double input2mf2mid = 10;
	final double input2mf2lst = 13;

	final double output1mf1fst = -3.2;
	final double output1mf1mid = 0;
	final double output1mf1lst = 3.2;
	final double output1mf2fst = 2;
	final double output1mf2mid = 5;
	final double output1mf2lst = 8;
	final double output1mf3fst = 6.8;
	final double output1mf3mid = 10;
	final double output1mf3lst = 13.2;
	
	
	//one input main function.
	public double oneInputFuzzy(double input1){
		double input1mf1 = getFuzzyDegree(input1, inputmf1fst, inputmf1mid, inputmf1lst);
		double input1mf2 = getFuzzyDegree(input1, inputmf2fst, inputmf2mid, inputmf2lst);
		double input1mf3 = getFuzzyDegree(input1, inputmf3fst, inputmf3mid, inputmf3lst);
				
		HashMap<Double,Double>[] ImplicationOutputs = new HashMap[3];
		ImplicationOutputs[0] = getImplicationOutputs(left1, right1, input1mf1, outputmf1fst, outputmf1mid, outputmf1lst); 
		ImplicationOutputs[1] = getImplicationOutputs(left1, right1, input1mf2, outputmf2fst, outputmf2mid, outputmf2lst); 
		ImplicationOutputs[2] = getImplicationOutputs(left1, right1, input1mf3, outputmf3fst, outputmf3mid, outputmf3lst); 
		
		HashMap<Double,Double> aggregateOutputs = getAggregateOutputs(left1, right1, ImplicationOutputs[0], ImplicationOutputs[1], ImplicationOutputs[2]);
		
		double fuzzyOutput = defuzzify(left1, right1, aggregateOutputs);
		//取小数后三位
		return (double)Math.round(fuzzyOutput*1000)/1000;		
	}
	
	//two input main function.
	public double twoInputFuzzy(double input1, double input2){
		double input1mf1 = getFuzzyDegree(input1, input1mf1fst, input1mf1mid, input1mf1lst);
		double input1mf2 = getFuzzyDegree(input1, input1mf2fst, input1mf2mid, input1mf2lst);
		double input1mf3 = getFuzzyDegree(input1, input1mf3fst, input1mf3mid, input1mf3lst);
		double input2mf1 = getFuzzyDegree(input2, input2mf1fst, input2mf1mid, input2mf1lst);
		double input2mf2 = getFuzzyDegree(input2, input2mf2fst, input2mf2mid, input2mf2lst);
		
		double degree[] = new double[3];
		degree[0] = fuzzyOrOperator(input1mf1, input2mf1);
		degree[1] = input1mf2;
		degree[2] = fuzzyOrOperator(input1mf3, input2mf2);
		
		HashMap<Double,Double>[] ImplicationOutputs = new HashMap[3];
		ImplicationOutputs[0] = getImplicationOutputs(left2, right2, degree[0], output1mf1fst, output1mf1mid, output1mf1lst); 
		ImplicationOutputs[1] = getImplicationOutputs(left2, right2, degree[1], output1mf2fst, output1mf2mid, output1mf2lst); 
		ImplicationOutputs[2] = getImplicationOutputs(left2, right2, degree[2], output1mf3fst, output1mf3mid, output1mf3lst); 
		
		HashMap<Double,Double> aggregateOutputs = getAggregateOutputs(left2, right2, ImplicationOutputs[0], ImplicationOutputs[1], ImplicationOutputs[2]);
		
		double fuzzyOutput = defuzzify(left2, right2, aggregateOutputs);
		//取小数后三位
		return (double)Math.round(fuzzyOutput*1000)/1000;		
	}
	
	//step 1
	public double getFuzzyDegree(double input, double first, double middle, double last){
		
		if(input>=first && input<middle){
			return (input - first)/(middle - first);
		}
		else if(input>=middle && input<last){
			return (last - input)/(last - middle);
		}
		else
			return 0;
	}
	
	//step 2
	public double fuzzyOrOperator(double degree1, double degree2){
		return degree1 > degree2 ? degree1 : degree2;
	}
	
	//step 3
	//每隔0.5取一个样本点
	public HashMap<Double,Double> getImplicationOutputs(int left, int right, double degree, double first, double middle, double last){
		HashMap<Double,Double> map = new HashMap<Double,Double>();
		for(double i=left;i<=right;i+=0.5){
			double temp = getFuzzyDegree(i, first, middle, last);
			if(temp<degree){
				map.put(i, temp);
			}
			else{
				map.put(i, degree);
			}
		}
		
		return map;
	}
	
	//step 4
	//max
	public HashMap<Double,Double> getAggregateOutputs(int left, int right, HashMap<Double,Double> mf1, HashMap<Double,Double> mf2, HashMap<Double,Double> mf3){
		HashMap<Double,Double> aggMap = new HashMap<Double,Double>();
		for(double i=left;i<=right;i+=0.5){
			double degree1 = mf1.get(i);
			double degree2 = mf2.get(i);
			double degree3 = mf3.get(i);			
			double temp = degree1 > degree2 ? degree1 : degree2;
			temp = temp > degree3 ? temp : degree3;
			aggMap.put(i, temp);
		}
		return aggMap;
	}

	//step 5
	//centroid
	public double defuzzify(int left, int right, HashMap<Double,Double> aggMap){
		double keyValue=0;
		double value=0;
		for(double i=left;i<=right;i+=0.5){
			keyValue += i * aggMap.get(i);
			value += aggMap.get(i);
		}
		return (keyValue/value);
	}
}
