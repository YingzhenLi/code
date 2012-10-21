package com.sevenEleven.dbConn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConn {
	private static Connection conn = null;
	private static String url = "jdbc:mysql://localhost:3306/finance";
	private static String driver = "com.mysql.jdbc.Driver";
	//插入公司数据
	public static  boolean insertData(int company_name,int year,
			double allCapitalizationRatio,double hasBeenInterestMultiples,double quickRatio,double netCashFlowsFromOperatingOrTotalDebt,
			double assetLiabilityRatio,double liquidityRatio,double theTotalDebtOrEBITDA,
			double ROA,double cashInflowsFromOperatingActivitiesOrSalesRevenue,double theRatioOfProfitsToCost,
			double ROE,double salesProfitMargin,
			double inventoryTurnover,double accountsReceivableTrunover,
			double totalAssetTurnover,double currentAssetsTurnover,
			double totalAssetsGrowthRate,double threeyearAverageProfitGrowthRate,
			double salesGrowthRate,double capitalAccumulationRate,
			double ratingScore,String ratingLevel){
		
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","root");
			Statement st = conn.createStatement();
			
			String firstsql = "select * from companydata where company_name="+company_name;
			ResultSet rs=st.executeQuery(firstsql);
			
			if (rs!=null && rs.next()){
				String sql = "update companydata set year = "+year+",allCapitalizationRatio="+allCapitalizationRatio+",hasBeenInterestMultiples="+hasBeenInterestMultiples+",quickRatio="+quickRatio+",netCashFlowsFromOperatingOrTotalDebt="+netCashFlowsFromOperatingOrTotalDebt+",assetLiabilityRatio="
					+assetLiabilityRatio+",liquidityRatio="+liquidityRatio+",theTotalDebtOrEBITDA="+theTotalDebtOrEBITDA+",ROA="
					+ROA+",cashInflowsFromOperatingActivitiesOrSalesRevenue="+cashInflowsFromOperatingActivitiesOrSalesRevenue+",theRatioOfProfitsToCost="+theRatioOfProfitsToCost+",ROE="
					+ROE+",salesProfitMargin="+salesProfitMargin+",inventoryTurnover="
					+inventoryTurnover+",accountsReceivableTrunover="+accountsReceivableTrunover+",totalAssetTurnover="
					+totalAssetTurnover+",currentAssetsTurnover="+currentAssetsTurnover+",totalAssetsGrowthRate="
					+totalAssetsGrowthRate+",threeyearAverageProfitGrowthRate="+threeyearAverageProfitGrowthRate+",salesGrowthRate="
					+salesGrowthRate+",capitalAccumulationRate="+capitalAccumulationRate+",ratingScore="
					+ratingScore+",ratingLevel='"+ratingLevel+"' where company_name="+company_name;
				System.out.print(sql);
				st.executeUpdate(sql);
			}else{
				String sql = "insert into companydata value(uuid(),"+company_name+","+year+","
						+allCapitalizationRatio+","+hasBeenInterestMultiples+","+quickRatio+","+netCashFlowsFromOperatingOrTotalDebt+","
						+assetLiabilityRatio+","+liquidityRatio+","+theTotalDebtOrEBITDA+","
						+ROA+","+cashInflowsFromOperatingActivitiesOrSalesRevenue+","+theRatioOfProfitsToCost+","
						+ROE+","+salesProfitMargin+","
						+inventoryTurnover+","+accountsReceivableTrunover+","
						+totalAssetTurnover+","+currentAssetsTurnover+","
						+totalAssetsGrowthRate+","+threeyearAverageProfitGrowthRate+","
						+salesGrowthRate+","+capitalAccumulationRate+","
						+ratingScore+",'"+ratingLevel+"')";
			
				
				st.execute(sql);
			}
			
			st.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
				return false;
			}
		return true;
	}
	
	public static Object[] getCompanyDataByID(int company_name){
		Object[] result = null;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","root");	
			
			String sql = "select * from companydata where company_name="+company_name;
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs!=null && rs.next()){
				result = new Object[23];
				result[0] = rs.getInt(3); // 从year开始
				result[1] = rs.getDouble(4);
				result[2] = rs.getDouble(5);
				result[3] = rs.getDouble(6);
				result[4] = rs.getDouble(7);
				result[5] = rs.getDouble(8);
				result[6] = rs.getDouble(9);
				result[7] = rs.getDouble(10);
				result[8] = rs.getDouble(11);
				result[9] = rs.getDouble(12);
				result[10] = rs.getDouble(13);
				result[11] = rs.getDouble(14);
				result[12] = rs.getDouble(15);
				result[13] = rs.getDouble(16);
				result[14] = rs.getDouble(17);
				result[15] = rs.getDouble(18);
				result[16] = rs.getDouble(19);
				result[17] = rs.getDouble(20);
				result[18] = rs.getDouble(21);
				result[19] = rs.getDouble(22);
				result[20] = rs.getDouble(23);
				result[21] = rs.getDouble(24);
				result[22] = rs.getString(25);
						 
						   
			}
			
			rs.close();
			st.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
			}
		return result;
	}
	
	//插入评分数据
	public static String insertRatingData(int kindOfCompany,int ratingName,double ratingA,double ratingB,double ratingC,double ratingD){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","root");
			Statement st = conn.createStatement();
			
			
			String firstsql = "select * from ratingData where kindOfCompany = "+kindOfCompany+" and ratingName = "+ratingName;
			System.out.println(firstsql);
			ResultSet rs = st.executeQuery(firstsql);
			
			if(rs != null && rs.next()){
				String sql = "update ratingData set ratingA="+ratingA+",ratingB="+ratingB+",ratingC="+ratingC+",ratingD="+ratingD+" where kindOfCompany="+kindOfCompany+" and ratingName="+ratingName;
				System.out.println(sql);
				
				st.executeUpdate(sql);
			}else{
				String sql = "insert into ratingData value(uuid(),"+kindOfCompany+","+ratingName+","+ratingA+","+ratingB+","+ratingC+","+ratingD+")";
				System.out.println(sql);
				
				st.execute(sql);
			}
			
			st.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
			return "录入评分失败，数据库异常";
			}
		return "录入评分成功";
	}
	
	public static double[][] getRatingDataByKindOfCompanyAndRatingName(int kindOfCompany){
		double[][] ratingData = null;
		int i = 0;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","root");	
			
			String sql = "select * from ratingData where kindOfCompany="+kindOfCompany;
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs!=null && rs.next()){
				System.out.println("nto null");
				ratingData = new double[20][4];
				rs.beforeFirst();
			}
				
			while(rs!=null && rs.next() && i<20){
				ratingData[i][0] = rs.getDouble(4);
				ratingData[i][1] = rs.getDouble(5);
				ratingData[i][2] = rs.getDouble(6);
				ratingData[i][3] = rs.getDouble(7);
				i++;
			}
			rs.close();
			st.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
			return null;
			}
		return ratingData;
	}
	
	//客户资料
	public static int insertCustomerData(String customerName,String createDate,String belongBank,String kindOfEconomy,String law,int kind,String customerKind,
			String num,String money,String area,String market,String belongrelation,String createXDDate,int IsOnMarket,
			String latestDate,String hold,String holdthis){
		int Maxid = 0;
		try{
			Maxid = getMaxIdFromCustomerData()+1;
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","root");
			Statement st = conn.createStatement();

			String sql = "insert into customerData value("+Maxid+",'"+customerName+"','"+createDate+"','"+belongBank+"','"+kindOfEconomy+"','"+law+"',"+kind+",'"+customerKind+"','"
			+num+"','"+money+"','"+area+"','"+market+"','"+belongrelation+"','"+createXDDate+"',"+IsOnMarket+",'"
			+latestDate+"','"+hold+"','"+holdthis+"')";
			System.out.println(sql);

			st.execute(sql);
			//ResultSet rs=st.executeQuery("select id from customerData");
//			while(rs.next()){
//				System.out.println(rs.getString(1));
//			}
			//rs.last();
		//	id=rs.getString(1);
//			System.out.print(id);
		//	rs.close();
			st.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
			}
	return Maxid;
	}
	
	public static String[][] findCustomerDataByIdOrName(int customerId,String customerName){
		
		int i = 0;
		String sql;
		String[][] customerData = null;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","root");	
			
			if(customerId !=-1 && customerName == ""){
				sql = "select id,customerName from customerData where id="+customerId;
			}else if(customerId == -1 && customerName != ""){
				sql = "select id,customerName from customerData where customerName like '%"+customerName+"%'";
			}else if(customerId != -1 && customerName != ""){
				sql = "select id,customerName from customerData where id="+customerId+" and customerName like '%"+customerName+"%'";

			}else{
				sql = "select * from customerData";
			}
			
			System.out.println(sql);
			Statement st = conn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			if(rs!=null){
				rs.last();
				System.out.println(rs.getRow());
				customerData = new String[rs.getRow()][2];
				rs.beforeFirst();

				while(rs != null && rs.next()){	
						customerData[i][0] = rs.getString(1);
						customerData[i][1] = rs.getString(2);
						i++;	
				}
			}
			rs.close();
			st.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
			}
		return customerData;
	}
	
	public static String[] getCustomerDataById(int customerId){
		String sql = null;
		String[] customerData = null;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","root");	
			
			if(customerId != -1){
				sql = "select customerName,createDate, belongBank, kindOfEconomy, law, kind, customerKind, num, money, area, market, belongrelation, createXDDate, IsOnMarket, latestDate, hold, holdthis from customerData where id="+customerId;
			}
			
			System.out.println(sql);
			Statement st = conn.createStatement();
			ResultSet rs=st.executeQuery(sql);
				while(rs!=null && rs.next()){
					customerData = new String[17];
					customerData[0] = rs.getString(1);
					customerData[1] = rs.getString(2);
					customerData[2] = rs.getString(3);
					customerData[3] = rs.getString(4);
					customerData[4] = rs.getString(5);

					customerData[5] = rs.getString(6);
					customerData[6] = rs.getString(7);
					customerData[7] = rs.getString(8);
					customerData[8] = rs.getString(9);
					customerData[9] = rs.getString(10);
					customerData[10] = rs.getString(11);
					customerData[11] = rs.getString(12);
					customerData[12] = rs.getString(13);
					customerData[13] = rs.getString(14);
					customerData[14] = rs.getString(15);
					customerData[15] = rs.getString(16);
					customerData[16] = rs.getString(17);
					
				}
			rs.close();
			st.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
			}
		return customerData;
	}
	
	public static boolean updateCustomerData(String id,String customerName,String createDate,String belongBank,String kindOfEconomy,String law,int kind,String customerKind,
			String num,String money,String area,String market,String belongrelation,String createXDDate,int IsOnMarket,
			String latestDate,String hold,String holdthis){
		
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","root");	
			
			String sql = "update customerData set customerName='"+customerName+"',createDate='"+createDate+"',belongBank='"+belongBank+"',kindOfEconomy='"+kindOfEconomy+"',law='"+law+"',kind="+kind+",customerKind='"+customerKind+"',num='"
			+num+"',money='"+money+"',area='"+area+"',market='"+market+"',belongrelation='"+belongrelation+"',createXDDate='"+createXDDate+"',IsOnMarket="+IsOnMarket+",latestDate='"
			+latestDate+"',hold='"+hold+"',holdthis='"+holdthis+"'where id = '"+id+"'";
			System.out.println(sql);
			Statement st = conn.createStatement();
			int resultcolm=st.executeUpdate(sql);
			
			st.close();
			conn.close();
			if(resultcolm==0){
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
			}
		return true;
	}
	
	public static int getMaxIdFromCustomerData(){
		int maxId=-1;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","root");	
			
			String sql = "select max(id) from customerdata";
			System.out.println(sql);
			Statement st = conn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			
			if(rs !=null && rs.next()){
				maxId=rs.getInt(1);
			}else{
				maxId=0;
			}
			
			st.close();
			conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
			}
		return maxId;
	}
	
	//插入专家系统的数据,专家系统
	public static  boolean insertExpertDataByExpert(int customerId,double managerCapacity ,double qualityManagement , double persolalCredit ,
											double staff ,double creditLevel ,double managementLevel  ,double potential,
											double innovation,double totalFuzzy){
			
			try{
				Class.forName(driver);
				conn = DriverManager.getConnection(url,"root","root");
				Statement st = conn.createStatement();
				
				String firstsql = "select * from expertSystem where customerId="+customerId;
				ResultSet rs=st.executeQuery(firstsql);
				
				if (rs!=null && rs.next()){
					String sql = "update expertSystem set managerCapacity="+managerCapacity+",qualityManagement="+qualityManagement+",persolalCredit="+persolalCredit+",staff="
						+staff+",creditLevel="+creditLevel+",managementLevel="+managementLevel+",potential="
						+potential+",innovation="+innovation+",totalFuzzy="+totalFuzzy+" where customerId="+customerId;
					System.out.print(sql);
					st.executeUpdate(sql);
				}else{
					String sql = "insert into expertSystem value("+customerId+","+managerCapacity+","+qualityManagement+","+persolalCredit+","
							+staff+","+creditLevel+","+managementLevel+","
							+potential+","+innovation+","+totalFuzzy+","+null+")";
					st.execute(sql);
				}
				
				st.close();
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
					return false;
				}
			return true;
		}
	
	//插入专家系统的数据,专家系统
		public static  boolean insertExpertDataByNeural(int customerId,double neuralResult){
				
				try{
					Class.forName(driver);
					conn = DriverManager.getConnection(url,"root","root");
					Statement st = conn.createStatement();
					
					String firstsql = "select * from expertSystem where customerId="+customerId;
					ResultSet rs=st.executeQuery(firstsql);
					
					if (rs!=null && rs.next()){
						String sql = "update expertSystem set neuralResult="+neuralResult+" where customerId="+customerId;
						System.out.print(sql);
						st.executeUpdate(sql);
					}else{
						String sql = "insert into expertSystem value("+customerId+","+null+","+null+","+null+","
								+null+","+null+","+null+","
								+null+","+null+","+null+","+neuralResult+")";
						st.execute(sql);
					}
					
					st.close();
					conn.close();
				}catch(Exception e){
					e.printStackTrace();
						return false;
					}
				return true;
			}	
	
	//查询专家系统数据
	public static double[] getExpertDataById(int customerId){
		double[] ExpertData = null;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","root");	
			
			String sql = "select * from expertSystem where customerId="+customerId;
				
			System.out.println(sql);
			Statement st = conn.createStatement();
			ResultSet rs=st.executeQuery(sql);
				while(rs!=null && rs.next()){
					ExpertData = new double[10];
					ExpertData[0] = rs.getDouble(2);
					ExpertData[1] = rs.getDouble(3);
					ExpertData[2] = rs.getDouble(4);
					ExpertData[3] = rs.getDouble(5);
					ExpertData[4] = rs.getDouble(6);

					ExpertData[5] = rs.getDouble(7);
					ExpertData[6] = rs.getDouble(8);
					ExpertData[7] = rs.getDouble(9);
					ExpertData[8] = rs.getDouble(10);
					ExpertData[9] = rs.getDouble(11);	
				}
			rs.close();
			st.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
			}
		return ExpertData;
	}
	
	//查询专家系统不为空数据
	public static Object[][] getNotNullExpertData(){
		Object[][] ExpertData = null;
		int i = 0;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","root");	
			
			String sql = "select customerId, totalFuzzy, neuralResult from expertSystem where totalFuzzy is not null and neuralResult is not null";
				
			System.out.println(sql);
			Statement st = conn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			if(rs != null){
				rs.last();
				ExpertData = new Object[rs.getRow()][3];
				rs.beforeFirst();
				while(rs!=null && rs.next()){
					
					ExpertData[i][0] = rs.getInt(1);
					ExpertData[i][1] = rs.getDouble(2);
					ExpertData[i][2] = rs.getDouble(3);
					i++;
				}
			}	
				
			rs.close();
			st.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
			}
		return ExpertData;
	}
	
	//插入评分最终结果
	public static  boolean insertRatingResult(int customerId,double ratingScore ,String ratingLevel){
			
			try{
				Class.forName(driver);
				conn = DriverManager.getConnection(url,"root","root");
				Statement st = conn.createStatement();
				
				String firstsql = "select * from ratingResult where customerId="+customerId;
				ResultSet rs=st.executeQuery(firstsql);
				
				if (rs!=null && rs.next()){
					String sql = "update ratingResult set ratingScore="+ratingScore+",ratingLevel='"+ratingLevel+"' where customerId="+customerId;
					System.out.print(sql);
					st.executeUpdate(sql);
				}else{
					String sql = "insert into ratingResult value("+customerId+","+ratingScore+",'"+ratingLevel+"')";
					st.execute(sql);
				}
				
				st.close();
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
					return false;
				}
			return true;
		}
	
	//查询专家系统数据
	public static Object[] getRatingResultById(int customerId){
		Object[] RatingResult = null;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","root");	
			
			String sql = "select * from ratingResult where customerId="+customerId;
				
			System.out.println(sql);
			Statement st = conn.createStatement();
			ResultSet rs=st.executeQuery(sql);
				while(rs!=null && rs.next()){
					RatingResult = new Object[2];
					RatingResult[0] = rs.getDouble(2);
					RatingResult[1] = rs.getString(3);
				}
			rs.close();
			st.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
			}
		return RatingResult;
	}
	
	//查询评级数据
	public static int[] getRatingResultCompanyByLevel(String level){
		int[] RatingResult = null;
		int i = 0;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","root");	
			
			String sql = "select customerId from ratingResult where ratingLevel='"+level+"'";
				
			System.out.println(sql);
			Statement st = conn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			if(rs != null){
				rs.last();
				RatingResult = new int[rs.getRow()];
				rs.beforeFirst();
				while(rs!=null && rs.next()){
					RatingResult[i] = rs.getInt(1);
					i++;
				}
			}
			rs.close();
			st.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
			}
		return RatingResult;
	}
}
