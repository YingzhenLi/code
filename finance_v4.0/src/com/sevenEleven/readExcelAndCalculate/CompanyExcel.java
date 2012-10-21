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
	
	//20��Ҫ�������
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
	
	//Ҫ�������
	private double ratingScore;
	private String ratingLevel;
	
	//���㹫ʽ�ұߵı���
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
			//��˾������ҵ�ӹ�˾�����л��, 0-23 �ֱ����ũҵ����ҵ��
			if(DBConn.getCustomerDataById(companyId)[5]==null)
				return "�ÿͻ�������";
			
	    	int kindOfCompany = Integer.parseInt(DBConn.getCustomerDataById(companyId)[5]);
	    	
			is = new FileInputStream(file);
			//��������������Workbook����  
			Workbook wb = WorkbookFactory.create(is); 

			//get��Sheet����  
			Sheet sheet0 = wb.getSheetAt(0);
			Sheet sheet1 = wb.getSheetAt(1);
			Sheet sheet2 = wb.getSheetAt(2);
        
			CompanyExcel companyExcel = new CompanyExcel();
        
			//company_name and year
			//companyExcel.company_name = filePath.toString();
			companyExcel.company_name = companyId;
			int cellNum = 5 ; //sheet0.getRow(0).getLastCellNum();
			companyExcel.year = sheet0.getRow(0).getCell(cellNum-1).getDateCellValue().getYear()+1900;
        
			//����ʽ����Ҫ�õ��ı�����ֵ
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
        
    	//����Ҫ�����ֵ
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
    		return "����ָ��ʧ�ܣ��ʲ��ϼ� ����Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.currentLiability == 0){
    		return "����ָ��ʧ�ܣ�������ծ ����Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.netProfit+companyExcel.incomeTax+companyExcel.depreciationOfFixedAssets+companyExcel.longTermDeferredExpensesAmoritize+companyExcel.financialExpenses == 0){
    		return "����ָ��ʧ�ܣ�(������+����˰+�̶��ʲ��۾ɡ������ʲ��ۺġ������������ʲ��۾�+���ڴ�̯����̯��+�������) ����Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.shortTermBorrowing+companyExcel.aMaturityOfNoncurrentLiabilities+companyExcel.bondsPayable+companyExcel.totalOwnersRightsAndInterests == 0){
    		return "����ָ��ʧ�ܣ�(���ڽ��+һ���ڵ��ڵķ�������ծ+���ڽ��+Ӧ��ծȯ+������Ȩ�棨��ɶ�Ȩ�棩�ϼ�) ����Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.financialExpenses == 0){
    		return "����ָ��ʧ�ܣ�������� ����Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.currentLiabilities == 0){
    		return "����ָ��ʧ�ܣ�������ծ ����Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.totalLiability == 0){
    		return "����ָ��ʧ�ܣ���ծ�ϼ� ����Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.ownersEquityAtTheBeginning+companyExcel.ownersEquityInTheEndOfTheYear== 0){
    		return "����ָ��ʧ�ܣ�(������Ȩ�������+������Ȩ����ĩ��������Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.operatingIncome == 0){
    		return "����ָ��ʧ�ܣ�Ӫҵ���� ����Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.totalAssetsEarly+companyExcel.totalAssetsAtTheEndOfThePeriod == 0){
    		return "����ָ��ʧ�ܣ����ʲ��ܼ������+��ĩ�ʲ��ܼ���ĩ���� ����Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.businessCost+companyExcel.salesCharge+companyExcel.managementCost+companyExcel.financialExpenses == 0){
    		return "����ָ��ʧ�ܣ���Ӫҵ�ɱ�+���۷���+�������+������ã�����Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.totalCurrentAssetsAtTheBeginning+companyExcel.totalCurrentAssetsAtTheEndOfthePeriod== 0){
    		return "����ָ��ʧ�ܣ�(�����ʲ��ϼ������+�����ʲ��ϼ���ĩ��������Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.beginningInventory+companyExcel.yearendInventory == 0){
    		return "����ָ��ʧ�ܣ�(��������+�����ĩ��������Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.earlyAccountsReceivable+companyExcel.accountsReceivableAtTheEndOfThePeriod == 0){
    		return "����ָ��ʧ�ܣ���Ӧ���˿������+Ӧ���˿���ĩ��������Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.earlyOperatingIncome == 0){
    		return "����ָ��ʧ�ܣ��ڳ�Ӫҵ���� ����Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.ownersEquityForEarly == 0){
    		return "����ָ��ʧ�ܣ�������Ȩ������� ����Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else if(companyExcel.ownersEquityAtTheBeginningOfTheYear == 0){
    		return "����ָ��ʧ�ܣ�������Ȩ������� ����Ϊ�㣬������񱨱��ж�Ӧ��ֵ";
    	}else{
    		
    		if( DBConn.getRatingDataByKindOfCompanyAndRatingName(kindOfCompany) == null){
    			return "����¼�����ֹ���";
    		}
    		
    		//�õ�����
        	companyExcel.ratingScore = companyExcel.getRatingScore(kindOfCompany,companyExcel.allCapitalizationRatio, companyExcel.hasBeenInterestMultiples, companyExcel.quickRatio, companyExcel.netCashFlowsFromOperatingOrTotalDebt, companyExcel.assetLiabilityRatio, companyExcel.liquidityRatio, companyExcel.theTotalDebtOrEBITDA, companyExcel.ROA, companyExcel.cashInflowsFromOperatingActivitiesOrSalesRevenue, companyExcel.theRatioOfProfitsToCost, companyExcel.ROE, companyExcel.salesProfitMargin, companyExcel.inventoryTurnover, companyExcel.accountsReceivableTrunover, companyExcel.totalAssetTurnover, companyExcel.currentAssetsTurnover, companyExcel.totalAssetsGrowthRate, companyExcel.threeyearAverageProfitGrowthRate, companyExcel.salesGrowthRate, companyExcel.capitalAccumulationRate);
        	
        	
        	
        	//�õ��ȼ�
        	companyExcel.ratingLevel = companyExcel.getRatingLevel(companyExcel.ratingScore);

        	//��������
        	boolean result = DBConn.insertData(companyExcel.company_name, companyExcel.year, companyExcel.allCapitalizationRatio, companyExcel.hasBeenInterestMultiples, companyExcel.quickRatio, companyExcel.netCashFlowsFromOperatingOrTotalDebt, companyExcel.assetLiabilityRatio, companyExcel.liquidityRatio, companyExcel.theTotalDebtOrEBITDA, companyExcel.ROA, companyExcel.cashInflowsFromOperatingActivitiesOrSalesRevenue, companyExcel.theRatioOfProfitsToCost, companyExcel.ROE, companyExcel.salesProfitMargin, companyExcel.inventoryTurnover, companyExcel.accountsReceivableTrunover, companyExcel.totalAssetTurnover, companyExcel.currentAssetsTurnover, companyExcel.totalAssetsGrowthRate, companyExcel.threeyearAverageProfitGrowthRate, companyExcel.salesGrowthRate, companyExcel.capitalAccumulationRate,companyExcel.ratingScore,companyExcel.ratingLevel);
    		if(result == false)
    			return "����ָ��ʧ�ܣ����ݿ��쳣";
    	}
    	
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "����ָ��ʧ�ܣ�Excel�ļ�δ���ҵ�";
	} catch (InvalidFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "����ָ��ʧ�ܣ���ȷ��Excel��ʽ��ȷ";
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "����ָ��ʧ�ܣ�Excel�ļ�δ���ҵ�";
	}  
	return "����ָ��ɹ�";	
	}  
    
    public double getCellNumValue(Sheet sheet,int rowNum,int cellNum){
    	double cellNumValue=0;
    	if(sheet.getRow(rowNum).getCell(cellNum).getCellType() == Cell.CELL_TYPE_NUMERIC){
    		cellNumValue = sheet.getRow(rowNum).getCell(cellNum).getNumericCellValue();
    		return cellNumValue;
    	}
    	else return 0;//���������ʾ�ô���ֵ����Ϊ����
    }
    
    public double getRatingScore(int kindOfCompany,double allCapitalizationRatio, double hasBeenInterestMultiples,double quickRatio, double netCashFlowsFromOperatingOrTotalDebt, double assetLiabilityRatio, double liquidityRatio, double theTotalDebtOrEBITDA, double ROA, double cashInflowsFromOperatingActivitiesOrSalesRevenue, double theRatioOfProfitsToCost,double ROE, double salesProfitMargin, double inventoryTurnover, double accountsReceivableTrunover, double totalAssetTurnover,double currentAssetsTurnover, double totalAssetsGrowthRate,double threeyearAverageProfitGrowthRate, double salesGrowthRate,double capitalAccumulationRate){
    	int ratingscore = 0;
    	//��ʼ��һ�����飬����ǣ�ֵԽ�ߣ��÷�Խ�͵�ָ��
    //	double[] increasedTarget = {assetLiabilityRatio,theTotalDebtOrEBITDA,allCapitalizationRatio};
    	//��ʼ��һ�����飬����ǣ�ֵԽ�ߣ��÷�Խ�ߵ�ָ��
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
