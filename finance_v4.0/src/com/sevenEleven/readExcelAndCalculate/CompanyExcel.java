package com.sevenEleven.readExcelAndCalculate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.sevenEleven.dbConn.DBConn;

public class CompanyExcel {
	
	//20个要存的数据
	private int company_name;
	private int year;
	private double assetLiabilityRatio;
	private double liquidityRatio;
	private double theTotalDebtOrEBITDA;
	private double allCapitalizationRatio;
	private double hasBeenInterestMultiples;
	private double quickRatio;
	private double netCashFlowsFromOperatingOrTotalDebt;
	private double ROE;
	private double salesProfitMargin;
	private double ROA;
	private double cashInflowsFromOperatingActivitiesOrSalesRevenue;
	private double theRatioOfProfitsToCost;
	private double totalAssetTurnover;
	private double currentAssetsTurnover;
	private double inventoryTurnover;
	private double accountsReceivableTrunover;
	private double salesGrowthRate;
	private double capitalAccumulationRate;
	private double totalAssetsGrowthRate;
	private double threeyearAverageProfitGrowthRate;
	
	//要存的评分
	private double ratingScore;
	private String ratingLevel;
	
	//计算公式右边的变量
	private double totalLiability;
	private double totalAssets;
	private double currentAssets;
	private double currentLiability ;
	private double netProfit;
	private double incomeTax;
	private double depreciationOfFixedAssets;
	private double longTermDeferredExpensesAmoritize;
	private double financialExpenses;
	private double shortTermBorrowing;
	private double aMaturityOfNoncurrentLiabilities;
	private double bondsPayable;
	private double totalOwnersRightsAndInterests;
	private double inventory;
	private double currentLiabilities;
	private double netCashFlowsFromOperating;
	private double ownersEquityAtTheBeginning;
	private double ownersEquityInTheEndOfTheYear;
	private double operatingProfit;
	private double operatingIncome;
	private double totalProfit;
	private double totalAssetsEarly;
	private double totalAssetsAtTheEndOfThePeriod;
	private double netCashFlowFromOperatingActivities;
	private double businessCost;
	private double salesCharge;
	private double managementCost;
	private double totalCurrentAssetsAtTheBeginning;
	private double totalCurrentAssetsAtTheEndOfthePeriod;
	private double beginningInventory;
	private double yearendInventory;
	private double earlyAccountsReceivable;
	private double accountsReceivableAtTheEndOfThePeriod;
	private double operatingIncomeThisYear;
	private double operatingIncomePriorYear;
	private double earlyOperatingIncome;
	private double theOwnersEquityAtTheEndOfThePeriod;
	private double ownersEquityForEarly;
	private double numberOfYearendTotalAssets;
	private double totalAssetsAtBeginningOfTheYear;
	private double ownersEquityAtTheBeginningOfTheYear;
	private double totalProfitsOfTheEnd;
	private double theEndOfThreeYearsAgo;

	
    @SuppressWarnings("deprecation")
	public static String calCompanyData(String filePath,int companyId) {

    	File file=new File(filePath);
        InputStream is;
		try {
			//公司所属行业从公司资料中获得, 0-23 分别代表农业、渔业等
			if(DBConn.getCustomerDataById(companyId)[5]==null)
				return "该客户不存在";
			
	    	int kindOfCompany = Integer.parseInt(DBConn.getCustomerDataById(companyId)[5]);
	    	
			is = new FileInputStream(file);
			//根据输入流创建Workbook对象  
			Workbook wb = WorkbookFactory.create(is); 

			//get到Sheet对象  
			Sheet sheet0 = wb.getSheetAt(0);
			Sheet sheet1 = wb.getSheetAt(1);
			Sheet sheet2 = wb.getSheetAt(2);
        
			CompanyExcel companyExcel = new CompanyExcel();
        
			//company_name and year
			//companyExcel.company_name = filePath.toString();
			companyExcel.company_name = companyId;
			int cellNum = 5 ; //sheet0.getRow(0).getLastCellNum();
			companyExcel.year = sheet0.getRow(0).getCell(cellNum-1).getDateCellValue().getYear()+1900;
        
			//给公式计算要用到的变量赋值
        companyExcel.totalLiability = companyExcel.getCellNumValue(sheet0, 56, cellNum-1);
        companyExcel.totalAssets = companyExcel.getCellNumValue(sheet0, 33, cellNum-1);
        companyExcel.currentAssets = companyExcel.getCellNumValue(sheet0, 14, cellNum-1);
        companyExcel.currentLiability = companyExcel.getCellNumValue(sheet0, 47, cellNum-1);
    	companyExcel.netProfit = companyExcel.getCellNumValue(sheet1, 22, cellNum-1);
    	companyExcel.incomeTax = companyExcel.getCellNumValue(sheet1, 20, cellNum-1);
    	companyExcel.depreciationOfFixedAssets = companyExcel.getCellNumValue(sheet2, 51, cellNum-1);
    	companyExcel.longTermDeferredExpensesAmoritize = companyExcel.getCellNumValue(sheet2, 53, cellNum-1);
    	companyExcel.financialExpenses = companyExcel.getCellNumValue(sheet1, 7, cellNum-1);
    	companyExcel.shortTermBorrowing = companyExcel.getCellNumValue(sheet0, 34, cellNum-1);
    	companyExcel.aMaturityOfNoncurrentLiabilities = companyExcel.getCellNumValue(sheet0, 45, cellNum-1);
    	companyExcel.bondsPayable = companyExcel.getCellNumValue(sheet0, 49, cellNum-1);
    	companyExcel.totalOwnersRightsAndInterests = companyExcel.getCellNumValue(sheet0, 66, cellNum-1);
    	companyExcel.inventory = companyExcel.getCellNumValue(sheet0, 10, cellNum-1);
    	companyExcel.currentLiabilities = companyExcel.getCellNumValue(sheet0, 47, cellNum-1);
    	companyExcel.netCashFlowsFromOperating = companyExcel.getCellNumValue(sheet2, 13, cellNum-1);
    	companyExcel.ownersEquityAtTheBeginning = companyExcel.getCellNumValue(sheet0, 66, cellNum-2);
    	companyExcel.ownersEquityInTheEndOfTheYear = companyExcel.getCellNumValue(sheet0, 66, cellNum-1);
    	companyExcel.operatingProfit = companyExcel.getCellNumValue(sheet1, 13, cellNum-1);
    	companyExcel.operatingIncome = companyExcel.getCellNumValue(sheet1, 1, cellNum-1);
    	companyExcel.totalProfit = companyExcel.getCellNumValue(sheet1, 19, cellNum-1);
    	companyExcel.totalAssetsEarly = companyExcel.getCellNumValue(sheet0, 33, cellNum-2);
    	companyExcel.totalAssetsAtTheEndOfThePeriod = companyExcel.getCellNumValue(sheet0, 33, cellNum-1);
    	companyExcel.netCashFlowFromOperatingActivities = companyExcel.getCellNumValue(sheet2, 13, cellNum-1);
    	companyExcel.businessCost = companyExcel.getCellNumValue(sheet1, 2, cellNum-1);
    	companyExcel.salesCharge = companyExcel.getCellNumValue(sheet1, 4, cellNum-1);
    	companyExcel.managementCost = companyExcel.getCellNumValue(sheet1, 5, cellNum-1);
    	companyExcel.totalCurrentAssetsAtTheBeginning = companyExcel.getCellNumValue(sheet0, 14, cellNum-2);
    	companyExcel.totalCurrentAssetsAtTheEndOfthePeriod = companyExcel.getCellNumValue(sheet0, 14, cellNum-1);
    	companyExcel.beginningInventory = companyExcel.getCellNumValue(sheet0, 10, cellNum-2);
    	companyExcel.yearendInventory = companyExcel.getCellNumValue(sheet0, 10, cellNum-1);
    	companyExcel.earlyAccountsReceivable = companyExcel.getCellNumValue(sheet0, 4, cellNum-2);
    	companyExcel.accountsReceivableAtTheEndOfThePeriod = companyExcel.getCellNumValue(sheet0, 4, cellNum-1);
    	companyExcel.operatingIncomeThisYear = companyExcel.getCellNumValue(sheet1, 1, cellNum-1);
    	companyExcel.operatingIncomePriorYear = companyExcel.getCellNumValue(sheet1, 1, cellNum-2);
    	companyExcel.earlyOperatingIncome = companyExcel.getCellNumValue(sheet1, 1, cellNum-2);
    	companyExcel.theOwnersEquityAtTheEndOfThePeriod = companyExcel.getCellNumValue(sheet0, 66, cellNum-1);
    	companyExcel.ownersEquityForEarly = companyExcel.getCellNumValue(sheet0, 66, cellNum-2);
    	companyExcel.numberOfYearendTotalAssets = companyExcel.getCellNumValue(sheet0, 33, cellNum-1);
    	companyExcel.totalAssetsAtBeginningOfTheYear = companyExcel.getCellNumValue(sheet0, 33, cellNum-2);
    	companyExcel.ownersEquityAtTheBeginningOfTheYear = companyExcel.getCellNumValue(sheet0, 66, cellNum-2);
    	companyExcel.totalProfitsOfTheEnd = companyExcel.getCellNumValue(sheet1, 19, cellNum-1);
    	companyExcel.theEndOfThreeYearsAgo = companyExcel.getCellNumValue(sheet1, 19, cellNum-4);
        
    	//计算要计算的值
    	companyExcel.assetLiabilityRatio = CalculateCompanyData.calAssetLiabilityRatio(companyExcel.totalLiability, companyExcel.totalAssets);
    	companyExcel.liquidityRatio = CalculateCompanyData.calLiquidityRatio(companyExcel.currentAssets, companyExcel.currentLiability);
    	companyExcel.theTotalDebtOrEBITDA = CalculateCompanyData.calTheTotalDebtOrEBITDA(companyExcel.totalLiability, companyExcel.netProfit, companyExcel.incomeTax, companyExcel.depreciationOfFixedAssets, companyExcel.longTermDeferredExpensesAmoritize, companyExcel.financialExpenses);
    	companyExcel.allCapitalizationRatio = CalculateCompanyData.calAllCapitalizationRatio(companyExcel.shortTermBorrowing, companyExcel.aMaturityOfNoncurrentLiabilities, companyExcel.bondsPayable, companyExcel.totalOwnersRightsAndInterests);
    	companyExcel.hasBeenInterestMultiples = CalculateCompanyData.calHasBeenInterestMultiples(companyExcel.netProfit, companyExcel.incomeTax, companyExcel.depreciationOfFixedAssets, companyExcel.longTermDeferredExpensesAmoritize, companyExcel.financialExpenses);
    	companyExcel.quickRatio = CalculateCompanyData.calQuickRatio(companyExcel.currentAssets, companyExcel.inventory, companyExcel.currentLiabilities);
    	companyExcel.netCashFlowsFromOperatingOrTotalDebt = CalculateCompanyData.calNetCashFlowsFromOperatingOrTotalDebt(companyExcel.netCashFlowsFromOperating, companyExcel.totalLiability);
    	companyExcel.ROE = CalculateCompanyData.calROE(companyExcel.netProfit, companyExcel.ownersEquityAtTheBeginning, companyExcel.ownersEquityInTheEndOfTheYear);
    	companyExcel.salesProfitMargin = CalculateCompanyData.calSalesProfitMargin(companyExcel.operatingProfit, companyExcel.operatingIncome);
    	companyExcel.ROA = CalculateCompanyData.calROA(companyExcel.totalProfit, companyExcel.financialExpenses, companyExcel.totalAssetsEarly, companyExcel.totalAssetsAtTheEndOfThePeriod);
    	companyExcel.cashInflowsFromOperatingActivitiesOrSalesRevenue = CalculateCompanyData.calCashInflowsFromOperatingActivitiesOrSalesRevenue(companyExcel.netCashFlowFromOperatingActivities, companyExcel.operatingIncome);
    	companyExcel.theRatioOfProfitsToCost = CalculateCompanyData.calTheRatioOfProfitsToCost(companyExcel.totalProfit, companyExcel.businessCost, companyExcel.salesCharge, companyExcel.managementCost, companyExcel.financialExpenses);
    	companyExcel.totalAssetTurnover = CalculateCompanyData.calTotalAssetTurnover(companyExcel.operatingIncome, companyExcel.totalAssetsEarly, companyExcel.totalAssetsAtTheEndOfThePeriod);
    	companyExcel.currentAssetsTurnover = CalculateCompanyData.calCurrentAssetsTurnover(companyExcel.operatingIncome, companyExcel.totalCurrentAssetsAtTheBeginning, companyExcel.totalCurrentAssetsAtTheEndOfthePeriod);
    	companyExcel.inventoryTurnover = CalculateCompanyData.calInventoryTurnover(companyExcel.businessCost, companyExcel.beginningInventory, companyExcel.yearendInventory);
    	companyExcel.accountsReceivableTrunover = CalculateCompanyData.calAccountsReceivableTrunover(companyExcel.operatingIncome, companyExcel.earlyAccountsReceivable, companyExcel.accountsReceivableAtTheEndOfThePeriod);
    	companyExcel.salesGrowthRate = CalculateCompanyData.calSalesGrowthRate(companyExcel.operatingIncomeThisYear, companyExcel.operatingIncomePriorYear, companyExcel.earlyOperatingIncome);
    	companyExcel.capitalAccumulationRate = CalculateCompanyData.calCapitalAccumulationRate(companyExcel.theOwnersEquityAtTheEndOfThePeriod, companyExcel.ownersEquityForEarly);
    	companyExcel.totalAssetsGrowthRate = CalculateCompanyData.calTotalAssetsGrowthRate(companyExcel.numberOfYearendTotalAssets, companyExcel.totalAssetsAtBeginningOfTheYear, companyExcel.ownersEquityAtTheBeginningOfTheYear);
    	companyExcel.threeyearAverageProfitGrowthRate = CalculateCompanyData.calThreeyearAverageProfitGrowthRate(companyExcel.totalProfitsOfTheEnd, companyExcel.theEndOfThreeYearsAgo);
    	
    	if(companyExcel.totalAssets == 0){
    		return "计算指标失败，资产合计 不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.currentLiability == 0){
    		return "计算指标失败，流动负债 不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.netProfit+companyExcel.incomeTax+companyExcel.depreciationOfFixedAssets+companyExcel.longTermDeferredExpensesAmoritize+companyExcel.financialExpenses == 0){
    		return "计算指标失败，(净利润+所得税+固定资产折旧、油气资产折耗、生产性生物资产折旧+长期待摊费用摊销+财务费用) 不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.shortTermBorrowing+companyExcel.aMaturityOfNoncurrentLiabilities+companyExcel.bondsPayable+companyExcel.totalOwnersRightsAndInterests == 0){
    		return "计算指标失败，(短期借款+一年内到期的非流动负债+长期借款+应付债券+所有者权益（或股东权益）合计) 不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.financialExpenses == 0){
    		return "计算指标失败，财务费用 不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.currentLiabilities == 0){
    		return "计算指标失败，流动负债 不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.totalLiability == 0){
    		return "计算指标失败，负债合计 不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.ownersEquityAtTheBeginning+companyExcel.ownersEquityInTheEndOfTheYear== 0){
    		return "计算指标失败，(所有者权益年初数+所有者权益年末数）不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.operatingIncome == 0){
    		return "计算指标失败，营业收入 不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.totalAssetsEarly+companyExcel.totalAssetsAtTheEndOfThePeriod == 0){
    		return "计算指标失败，（资产总计年初数+期末资产总计年末数） 不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.businessCost+companyExcel.salesCharge+companyExcel.managementCost+companyExcel.financialExpenses == 0){
    		return "计算指标失败，（营业成本+销售费用+管理费用+财务费用）不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.totalCurrentAssetsAtTheBeginning+companyExcel.totalCurrentAssetsAtTheEndOfthePeriod== 0){
    		return "计算指标失败，(流动资产合计年初数+流动资产合计年末数）不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.beginningInventory+companyExcel.yearendInventory == 0){
    		return "计算指标失败，(存货年初数+存货年末数）不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.earlyAccountsReceivable+companyExcel.accountsReceivableAtTheEndOfThePeriod == 0){
    		return "计算指标失败，（应收账款年初数+应收账款年末数）不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.earlyOperatingIncome == 0){
    		return "计算指标失败，期初营业收入 不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.ownersEquityForEarly == 0){
    		return "计算指标失败，所有者权益年初数 不可为零，请检查财务报表中对应的值";
    	}else if(companyExcel.ownersEquityAtTheBeginningOfTheYear == 0){
    		return "计算指标失败，所有者权益年初数 不可为零，请检查财务报表中对应的值";
    	}else{
    		
    		if( DBConn.getRatingDataByKindOfCompanyAndRatingName(kindOfCompany) == null){
    			return "请先录入评分规则";
    		}
    		
    		//得到评分
        	companyExcel.ratingScore = companyExcel.getRatingScore(kindOfCompany,companyExcel.allCapitalizationRatio, companyExcel.hasBeenInterestMultiples, companyExcel.quickRatio, companyExcel.netCashFlowsFromOperatingOrTotalDebt, companyExcel.assetLiabilityRatio, companyExcel.liquidityRatio, companyExcel.theTotalDebtOrEBITDA, companyExcel.ROA, companyExcel.cashInflowsFromOperatingActivitiesOrSalesRevenue, companyExcel.theRatioOfProfitsToCost, companyExcel.ROE, companyExcel.salesProfitMargin, companyExcel.inventoryTurnover, companyExcel.accountsReceivableTrunover, companyExcel.totalAssetTurnover, companyExcel.currentAssetsTurnover, companyExcel.totalAssetsGrowthRate, companyExcel.threeyearAverageProfitGrowthRate, companyExcel.salesGrowthRate, companyExcel.capitalAccumulationRate);
        	
        	
        	
        	//得到等级
        	companyExcel.ratingLevel = companyExcel.getRatingLevel(companyExcel.ratingScore);

        	//插入数据
        	boolean result = DBConn.insertData(companyExcel.company_name, companyExcel.year, companyExcel.allCapitalizationRatio, companyExcel.hasBeenInterestMultiples, companyExcel.quickRatio, companyExcel.netCashFlowsFromOperatingOrTotalDebt, companyExcel.assetLiabilityRatio, companyExcel.liquidityRatio, companyExcel.theTotalDebtOrEBITDA, companyExcel.ROA, companyExcel.cashInflowsFromOperatingActivitiesOrSalesRevenue, companyExcel.theRatioOfProfitsToCost, companyExcel.ROE, companyExcel.salesProfitMargin, companyExcel.inventoryTurnover, companyExcel.accountsReceivableTrunover, companyExcel.totalAssetTurnover, companyExcel.currentAssetsTurnover, companyExcel.totalAssetsGrowthRate, companyExcel.threeyearAverageProfitGrowthRate, companyExcel.salesGrowthRate, companyExcel.capitalAccumulationRate,companyExcel.ratingScore,companyExcel.ratingLevel);
    		if(result == false)
    			return "计算指标失败，数据库异常";
    	}
    	
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "计算指标失败，Excel文件未能找到";
	} catch (InvalidFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "计算指标失败，请确保Excel格式正确";
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "计算指标失败，Excel文件未能找到";
	}  
	return "计算指标成功";	
	}  
    
    public double getCellNumValue(Sheet sheet,int rowNum,int cellNum){
    	double cellNumValue=0;
    	if(sheet.getRow(rowNum).getCell(cellNum).getCellType() == Cell.CELL_TYPE_NUMERIC){
    		cellNumValue = sheet.getRow(rowNum).getCell(cellNum).getNumericCellValue();
    		return cellNumValue;
    	}
    	else return 0;//弹出框框，提示该处的值必须为数字
    }
    
    public double getRatingScore(int kindOfCompany,double allCapitalizationRatio, double hasBeenInterestMultiples,double quickRatio, double netCashFlowsFromOperatingOrTotalDebt, double assetLiabilityRatio, double liquidityRatio, double theTotalDebtOrEBITDA, double ROA, double cashInflowsFromOperatingActivitiesOrSalesRevenue, double theRatioOfProfitsToCost,double ROE, double salesProfitMargin, double inventoryTurnover, double accountsReceivableTrunover, double totalAssetTurnover,double currentAssetsTurnover, double totalAssetsGrowthRate,double threeyearAverageProfitGrowthRate, double salesGrowthRate,double capitalAccumulationRate){
    	int ratingscore = 0;
    	//初始化一个数组，存的是：值越高，得分越低的指标
    //	double[] increasedTarget = {assetLiabilityRatio,theTotalDebtOrEBITDA,allCapitalizationRatio};
    	//初始化一个数组，存的是：值越高，得分越高的指标
    	double[] Target = { allCapitalizationRatio, hasBeenInterestMultiples, quickRatio,  netCashFlowsFromOperatingOrTotalDebt,assetLiabilityRatio, liquidityRatio,  theTotalDebtOrEBITDA,ROA, cashInflowsFromOperatingActivitiesOrSalesRevenue,theRatioOfProfitsToCost, ROE,salesProfitMargin, inventoryTurnover, accountsReceivableTrunover, totalAssetTurnover,currentAssetsTurnover,totalAssetsGrowthRate,threeyearAverageProfitGrowthRate,salesGrowthRate,capitalAccumulationRate};
    	
    	
    	double[][] ratingData = DBConn.getRatingDataByKindOfCompanyAndRatingName(kindOfCompany);
    	
    	for(int m=0;m<20;m++){
    		if(m==0 || m==4 || m ==6 ){
    			if(Target[m]<ratingData[m][0]){
        			ratingscore=ratingscore+5;
        			}else if(ratingData[m][0]<=Target[m] && Target[m]<ratingData[m][1]){
        				ratingscore=ratingscore+4;
        				}else if(ratingData[m][1]<=Target[m] && Target[m]<ratingData[m][2]){
        					ratingscore=ratingscore+3;
        					}else if(ratingData[m][2]<=Target[m] && Target[m]<ratingData[m][3]){
            					ratingscore=ratingscore+2;
            					}else{
            						ratingscore=ratingscore+1;
            						}
    			System.out.print("A"+m+" ");
    			System.out.println(ratingscore+" ");
    		}
    		else{
        		if(Target[m]>ratingData[m][0]){
        			ratingscore=ratingscore+5;
        			}else if(ratingData[m][1]<=Target[m] && Target[m]<ratingData[m][0]){
        				ratingscore=ratingscore+4;
        				}else if(ratingData[m][2]<=Target[m] && Target[m]<ratingData[m][1]){
        					ratingscore=ratingscore+3;
        					}else if(ratingData[m][3]<=Target[m] && Target[m]<ratingData[m][2]){
            					ratingscore=ratingscore+2;
            					}else{
            						ratingscore=ratingscore+1;
            						}
        		System.out.print(m+" ");
    			System.out.println(ratingscore+" ");
        	}
    		
    	}
    	System.out.println(ratingscore);
    	return ratingscore;
    }
    
    public String getRatingLevel(double ratingScore){
    	String level=null;
    	if(ratingScore>=90)
    		level = "AAA";
    	else if(90>ratingScore && ratingScore>=80)
    		level = "AA";
    	else if(80>ratingScore && ratingScore>=70)
    		level = "A";
    	else if(70>ratingScore && ratingScore>=60)
    		level = "BBB";
    	else if(60>ratingScore && ratingScore>=50)
    		level = "BB";
    	else if(50>ratingScore && ratingScore>=40)
    		level = "B";
    	else
    		level = "C";
    	return level;
    }
}  
