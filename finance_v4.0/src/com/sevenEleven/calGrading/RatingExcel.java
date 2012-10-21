package com.sevenEleven.calGrading;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.sevenEleven.dbConn.DBConn;

public class RatingExcel {
	
	public static boolean calRatingExcel(String filePath) {
   
    	File file=new File(filePath);
        InputStream is;
		try {
			is = new FileInputStream(file);
			//根据输入流创建Workbook对象
			Workbook wb = WorkbookFactory.create(is); 
			
			//get到25个行业的Sheet对象并计算每个行业的二十个指标
			//所属行业根据excel分别从0-24对应 农业、渔业、等
			//所属指标根据excel分别从上往下对应
			Sheet[] sheet = new Sheet[25];
			for(int kindOfCompany = 0;kindOfCompany<sheet.length;kindOfCompany++){
				sheet[kindOfCompany] = wb.getSheetAt(kindOfCompany);
            	for(int ratingName = 1;ratingName<=20;ratingName++){
            		double ratingA,ratingB,ratingC,ratingD;
            		double valueA=sheet[kindOfCompany].getRow(ratingName).getCell(3).getNumericCellValue();
            		double valueB=sheet[kindOfCompany].getRow(ratingName).getCell(4).getNumericCellValue();
            		double valueC=sheet[kindOfCompany].getRow(ratingName).getCell(5).getNumericCellValue();
            		double valueD=sheet[kindOfCompany].getRow(ratingName).getCell(6).getNumericCellValue();
            		double valueE=sheet[kindOfCompany].getRow(ratingName).getCell(7).getNumericCellValue();
            		ratingA=valueA+(valueB-valueA)/2;
            		ratingB=valueB+(valueC-valueB)/2;
            		ratingC=valueC+(valueD-valueC)/2;
            		ratingD=valueD+(valueE-valueD)/2;
            		
            		if(ratingName == 1 || ratingName == 3 || ratingName == 5|| ratingName == 6 || ratingName == 8|| ratingName == 10
            				|| ratingName == 11|| ratingName == 12|| ratingName == 17|| ratingName == 18|| ratingName == 19|| ratingName == 20){
            			ratingA=ratingA/100;
                		ratingB=ratingB/100;
                		ratingC=ratingC/100;
                		ratingD=ratingD/100;
            		}
            		DBConn.insertRatingData(kindOfCompany, ratingName, ratingA, ratingB, ratingC, ratingD);
            	}
            }
		}catch(Exception e){
            	e.printStackTrace();
            	return false;
            } 
		return true;
        }
	
	}

