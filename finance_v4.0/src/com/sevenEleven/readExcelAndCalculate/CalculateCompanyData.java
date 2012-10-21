package com.sevenEleven.readExcelAndCalculate;

public class CalculateCompanyData {

	//1
	public static double calAssetLiabilityRatio(double totalLiability,double totalAssets){
		if(totalAssets!=0)
			return totalLiability/totalAssets;
		else return 0; //弹出警示框提示输入不应为零
	}
	
	//2
	public static double calLiquidityRatio(double currentAssets,double currentLiability){
		if(currentLiability!=0)
			return currentAssets/currentLiability;
		else return 0; //弹出警示框提示输入不应为零
	}
	
	//3
	public static double calTheTotalDebtOrEBITDA(double totalLiability,double netProfit,double incomeTax,double depreciationOfFixedAssets,double longTermDeferredExpensesAmoritize,double financialExpenses){
		if(netProfit+incomeTax+depreciationOfFixedAssets+longTermDeferredExpensesAmoritize+financialExpenses!=0)
			return totalLiability/(netProfit+incomeTax+depreciationOfFixedAssets+longTermDeferredExpensesAmoritize+financialExpenses);
		else return 0; //弹出警示框提示输入不应为零
	}
	
	//4
	public static double calAllCapitalizationRatio(double shortTermBorrowing,double aMaturityOfNoncurrentLiabilities,double bondsPayable,
			double totalOwnersRightsAndInterests){
		if(shortTermBorrowing+aMaturityOfNoncurrentLiabilities+bondsPayable+totalOwnersRightsAndInterests!=0)
			return (shortTermBorrowing+aMaturityOfNoncurrentLiabilities+bondsPayable)/(shortTermBorrowing+aMaturityOfNoncurrentLiabilities+bondsPayable+totalOwnersRightsAndInterests);
		else return 0; //弹出警示框提示输入不应为零
	}
	
	//5
	public static double calHasBeenInterestMultiples(double netProfit,double incomeTax,double depreciationOfFixedAssets,double longTermDeferredExpensesAmoritize,double financialExpenses){
		if(financialExpenses!=0)
			return (netProfit+incomeTax+depreciationOfFixedAssets+longTermDeferredExpensesAmoritize+financialExpenses)/financialExpenses;
		else return 0; //弹出警示框提示输入不应为零
	}	
	
	//6
	public static double calQuickRatio(double currentAssets,double inventory,double currentLiabilities){
		if(currentLiabilities!=0)
			return (currentAssets-inventory)/currentLiabilities;
		else return 0; //弹出警示框提示输入不应为零
	}	
	
	//7
	public static double calNetCashFlowsFromOperatingOrTotalDebt(double netCashFlowsFromOperating,double totalLiability){
		if(totalLiability!=0)
			return netCashFlowsFromOperating/totalLiability;
		else return 0; //弹出警示框提示输入不应为零
	}	
	
	//8
	public static double calROE(double netProfit,double ownersEquityAtTheBeginning,double ownersEquityInTheEndOfTheYear){
		if(ownersEquityAtTheBeginning+ownersEquityInTheEndOfTheYear!=0)
			return netProfit/((ownersEquityAtTheBeginning+ownersEquityInTheEndOfTheYear)/2);
		else return 0; //弹出警示框提示输入不应为零
	}	
	
	//9
	public static double calSalesProfitMargin(double operatingProfit,double operatingIncome){
		if(operatingIncome!=0)
			return operatingProfit/operatingIncome;
		else return 0; //弹出警示框提示输入不应为零
	}

	//10
	public static double calROA(double totalProfit,double financialExpenses,double totalAssetsEarly,double totalAssetsAtTheEndOfThePeriod){
		if(totalAssetsEarly+totalAssetsAtTheEndOfThePeriod!=0)
			return (totalProfit+financialExpenses)/((totalAssetsEarly+totalAssetsAtTheEndOfThePeriod)/2);
		else return 0; //弹出警示框提示输入不应为零
	}
	
	//11
	public static double calCashInflowsFromOperatingActivitiesOrSalesRevenue(double netCashFlowFromOperatingActivities,double operatingIncome){
		if(operatingIncome!=0)
			return netCashFlowFromOperatingActivities/operatingIncome;
		else return 0; //弹出警示框提示输入不应为零
	}	
	
	//12
	public static double calTheRatioOfProfitsToCost(double totalProfit,double businessCost,double salesCharge,double managementCost,double financialExpenses){
		if(businessCost+salesCharge+managementCost+financialExpenses!=0)
			return totalProfit/(businessCost+salesCharge+managementCost+financialExpenses);
		else return 0; //弹出警示框提示输入不应为零
	}
	
	//13
	public static double calTotalAssetTurnover(double operatingIncome,double totalAssetsEarly,double totalAssetsAtTheEndOfThePeriod){
		if(totalAssetsEarly+totalAssetsAtTheEndOfThePeriod!=0)
			return operatingIncome/((totalAssetsEarly+totalAssetsAtTheEndOfThePeriod)/2);
		else return 0; //弹出警示框提示输入不应为零
	}
	
	//14
	public static double calCurrentAssetsTurnover(double operatingIncome,double totalCurrentAssetsAtTheBeginning,double totalCurrentAssetsAtTheEndOfthePeriod){
		if(totalCurrentAssetsAtTheBeginning+totalCurrentAssetsAtTheEndOfthePeriod!=0)
			return operatingIncome/((totalCurrentAssetsAtTheBeginning+totalCurrentAssetsAtTheEndOfthePeriod)/2);
		else return 0; //弹出警示框提示输入不应为零
	}
	
	//15
	public static double calInventoryTurnover(double businessCost,double beginningInventory,double yearendInventory){
		if(beginningInventory+yearendInventory!=0)
			return businessCost/((beginningInventory+yearendInventory)/2);
		else return 0; //弹出警示框提示输入不应为零
	}
	
	//16
	public static double calAccountsReceivableTrunover(double operatingIncome,double earlyAccountsReceivable,double accountsReceivableAtTheEndOfThePeriod){
		if(earlyAccountsReceivable+accountsReceivableAtTheEndOfThePeriod!=0)
			return operatingIncome/((earlyAccountsReceivable+accountsReceivableAtTheEndOfThePeriod)/2);
		else return 0; //弹出警示框提示输入不应为零
	}
	
	//17
	public static double calSalesGrowthRate(double operatingIncomeThisYear,double operatingIncomePriorYear,double earlyOperatingIncome){
		if(earlyOperatingIncome!=0)
			return (operatingIncomeThisYear-operatingIncomePriorYear)/earlyOperatingIncome;
		else return 0; //弹出警示框提示输入不应为零
	}
	
	//18
	public static double calCapitalAccumulationRate(double theOwnersEquityAtTheEndOfThePeriod,double ownersEquityForEarly){
		if(ownersEquityForEarly!=0)
			return (theOwnersEquityAtTheEndOfThePeriod-ownersEquityForEarly)/ownersEquityForEarly;
		else return 0; //弹出警示框提示输入不应为零
	}	
	
	//19
	public static double calTotalAssetsGrowthRate(double numberOfYearendTotalAssets,double totalAssetsAtBeginningOfTheYear,double ownersEquityAtTheBeginningOfTheYear){
		if(ownersEquityAtTheBeginningOfTheYear!=0)
			return (numberOfYearendTotalAssets-totalAssetsAtBeginningOfTheYear)/ownersEquityAtTheBeginningOfTheYear;
		else return 0; //弹出警示框提示输入不应为零
	}	
	
	//20
	public static double calThreeyearAverageProfitGrowthRate(double totalProfitsOfTheEnd,double theEndOfThreeYearsAgo){
		return Math.cbrt((totalProfitsOfTheEnd-theEndOfThreeYearsAgo)) - 1;
	}	
	
}
