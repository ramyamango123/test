package initiate.apd.util; //validate.siebel.runner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;


import initiate.apd.util.DatabaseUtil;

import com.intuit.taf.logging.Logger;
import com.intuit.taf.testing.Assert;
import com.intuit.taf.testing.TestResultStatus;
import com.intuit.taf.testing.TestRun;

/**  
* Queries the Siebel Database for data related to Orders.<br>
* 
* @author  Jeffrey Walker
* @version 1.0.0 
*/ 
public class SiebelDBQueryForOrder 
{
	private static Logger sLog;
	private Connection connection;
	private String separator = "\t\t\t\t\t";
	private DatabaseUtil siebelDatabaseUtil;
	
	
	/**
	 * Queries the Siebel Database for Status of a given Order. <br>
	 * 
	 * @param dbUserName Username for the Siebel Database
	 * @param dbPassword Password for the Siebel Database
	 * @param dbUrl Siebel Database Connection URL
	 * @param orderNumber Order Number
	 * @param expectedStatus Expected Order Header Status
	 * @return Return Map with values for the following keys ("TotalOrderAmount")
	 */
//	public HashMap<String, Object> queryOrderStatusByOrderNumber(Object dbUserName, Object dbPassword, Object dbUrl,Object orderNumber,Object expectedStatus, HashMap<String, Object> outputMap)
//	{
//		//Required conversion of parameters
//		String dbUserNameStr = (String)dbUserName;
//		String dbPasswordStr = (String)dbPassword;
//		String dbUrlStr = (String)dbUrl;
//		String orderNumberStr = (String)orderNumber;
//		String expectedStatusStr = (String)expectedStatus;
//		
//		String query = "";
//		sLog = Logger.getInstance();
//		//HashMap<String,String> outputMap = new HashMap<String, String>();
//		//outputMap.put("TotalOrderAmount", "0.00");
//		
//		TestRun.startSuite("Query Siebel By Order Number");
//		TestRun.startTest("Validating Order Sync to Siebel...");
//		
//		siebelDatabaseUtil = new DatabaseUtil();
//		connection = siebelDatabaseUtil.createDatabaseConnection(dbUserNameStr, dbPasswordStr, dbUrlStr);
//		
//		try 
//		{
//			String submittedStatus = "Submitted";
//			boolean resultIsPresent = false;
//			int count = 0;
//			ResultSet rset = null;
//			
//			while (resultIsPresent == false) {
//				count += 1;
//				if (count > 50) break;
//				System.out.println("running count :" + count);
//				Thread.sleep(5000);
//				query = "select * from siebel.s_order where ORDER_NUM='"+orderNumberStr+"'";
//
//				Statement mStatement;
//				mStatement = connection.createStatement();
//				rset = mStatement.executeQuery(query);
//				resultIsPresent = rset.next();
//				String db_status = "";
//
//				if (resultIsPresent) {
//					db_status = rset.getString("STATUS_CD");
//					outputMap.put("db_status", db_status);
//					//if (db_status == "BRM Hold") || (db_status == "Siebel ABCS Req Failed") || (db_status == "ERS Hold")
//					
//				}
//
//				if ((resultIsPresent == true) && ! (db_status.equals(expectedStatusStr)) && ! (db_status.equals(submittedStatus)))
//				{
//					resultIsPresent = false;
//					sLog.info("db status is " + db_status + ", Order status other than " + expectedStatusStr + " or " + submittedStatus + " is displayed in Siebel");
//				}
//			}
//                
//            if (resultIsPresent)
//            {
//            	sLog.info(separator + "QUERY: " + query);
//            	sLog.info(separator + "ORDER: \""+ orderNumberStr + "\" is successfully synched to Siebel.");
//            	sLog.info(separator + "STATUS: " + rset.getString("STATUS_CD"));
//            	outputMap.put("TotalOrderAmount", rset.getString("CRDT_CRD_TXN_AMT"));
//            	Assert.assertTrue(expectedStatusStr.equals(rset.getString("STATUS_CD")) || (submittedStatus.equals(rset.getString("STATUS_CD"))));
//            	
//            	//Assert.assertEquals("Order Status was different than expected.",expectedStatusStr, rset.getString("STATUS_CD"));
//            	TestRun.updateStatus(TestResultStatus.PASS, "queryOrderStatusByOrderNumber passed");
//            }
//            else
//            {
//            	TestRun.updateStatus(TestResultStatus.FAIL,"Order " + orderNumberStr + " did not sync to Siebel");
//            	sLog.info(separator + "QUERY: " + query);
//            	sLog.info(separator + "ORDER: "+orderNumberStr);
//            }
//            connection.close();
//		}
//		catch (Exception e) 
//		{
//			TestRun.updateStatus(TestResultStatus.INCOMPLETE);
//			sLog.info(separator + "QUERY: " + query);
//			System.out.println(e);
//			System.out.println(separator + "Exception occurred while trying to connect to the Siebel Database for validation:\r\n" + e.getStackTrace().toString());
//		}
//		finally
//		{
//			TestRun.endTest();
//			TestRun.endSuite();
//		}
//		return outputMap;
//	}
//}
	
	public HashMap<String, Object> queryOrderStatusByOrderNumber(Object dbUserName, Object dbPassword, Object dbUrl,Object orderNumber,Object expectedStatus, HashMap<String,Object> outputMap)
	{
		//Required conversion of parameters
		String dbUserNameStr = (String)dbUserName;
		String dbPasswordStr = (String)dbPassword;
		String dbUrlStr = (String)dbUrl;
		String orderNumberStr = (String)orderNumber;
		String expectedStatusStr = (String)expectedStatus;
	
		
		String query = "";
		sLog = Logger.getInstance();
		
		
		TestRun.startSuite("Query Siebel By Order Number");
		TestRun.startTest("Validating Order Sync to Siebel...");
		
		siebelDatabaseUtil = new DatabaseUtil();
		connection = siebelDatabaseUtil.createDatabaseConnection(dbUserNameStr, dbPasswordStr, dbUrlStr);
		
		try 
		{
			String submittedStatus = "Submitted";
			String openStatus = "Open";
			String recordedStatus = "Recorded";
			String queuedStatus = "Queued";
			boolean resultIsPresent = false;
			int count = 0;
			ResultSet rset = null;
			
		
			while (resultIsPresent == false) {
				count += 1;
				if (count > 50) break;
				System.out.println("running count :" + count);
				Thread.sleep(5000);
				query = "select * from siebel.s_order where ORDER_NUM='"+orderNumberStr+"'";

				Statement mStatement;
				mStatement = connection.createStatement();
				rset = mStatement.executeQuery(query);
				resultIsPresent = rset.next();
				String db_status = "";

				if (resultIsPresent) {
					//Object users = new HashMap();
					db_status = rset.getString("STATUS_CD");
					
					if (db_status.equals("Open")) {
						outputMap.put("siebel_order_status", "Open");
					} else if (db_status.equals("Queued")) {
						outputMap.put("siebel_order_status", "Queued");
					} else if (db_status.equals("Recorded")) {
						outputMap.put("siebel_order_status", "Recorded");
					} else if (db_status.equals("Submitted")) {
						outputMap.put("siebel_order_status", "Submitted");
					} else if (db_status.equals("BRM Hold")) {
						outputMap.put("siebel_order_status", "BRM Hold");
					} else if (db_status.equals("Siebel ABCS Req Failed")) {
						outputMap.put("siebel_order_status", "Siebel ABCS Req Failed");
					} else if (db_status.equals("ERS Hold")) {
						outputMap.put("siebel_order_status", "ERS Hold");
					} else if (db_status.equals("eBiz Hold")) {
						outputMap.put("siebel_order_status", "eBiz Hold");
					} else if (db_status.equals("Complete")) {
						outputMap.put("siebel_order_status", "Complete");
					} 
					outputMap.put("siebel_order_status", new String(db_status));
					//outputMap.put("siebel_order_status", new String(db_status));
					String db_result = (String) outputMap.get("siebel_order_status");
					System.out.println("siebel order status: " + db_result);
					sLog.info("siebel order status: " + db_result);
					
					//if (item_result.equals(itemString))

					//if ((db_result == "BRM Hold") || (db_result == "Siebel ABCS Req Failed") || (db_result == "ERS Hold") || (db_result == "eBiz Hold"))
					
					if ((db_result.equals("BRM Hold")) || (db_result.equals("Siebel ABCS Req Failed")) || (db_result.equals("ERS Hold")) || (db_result.equals("eBiz Hold")))
					{
						resultIsPresent = false;
						sLog.info("Order didn't go through successfully, some failed order status displayed");
						break;

					}
				}

				if ((resultIsPresent == true) && ((db_status.equals(submittedStatus))||(db_status.equals(recordedStatus))||(db_status.equals(openStatus)) || (db_status.equals(queuedStatus))))
				{
					resultIsPresent = false;
					System.out.println("The Order Status is one of them - Open, Submitted, Recorded");
					sLog.info("The Order Status is one of them - Open, Submitted, Recorded");
				}
			}
                
            if (resultIsPresent)
            {
            	sLog.info(separator + "QUERY: " + query);
            	sLog.info(separator + "ORDER: \""+ orderNumberStr + "\" is successfully synched to Siebel.");
            	sLog.info(separator + "STATUS: " + rset.getString("STATUS_CD"));
            	outputMap.put("TotalOrderAmount", rset.getString("CRDT_CRD_TXN_AMT"));
            	Assert.assertTrue(expectedStatusStr.equals(rset.getString("STATUS_CD")));
            	
            	//Assert.assertEquals("Order Status was different than expected.",expectedStatusStr, rset.getString("STATUS_CD"));
            	TestRun.updateStatus(TestResultStatus.PASS, "queryOrderStatusByOrderNumber passed");
            }
            else
            {
            	TestRun.updateStatus(TestResultStatus.FAIL,"Order " + orderNumberStr + " did not sync to Siebel");
            	sLog.info(separator + "QUERY: " + query);
            	sLog.info(separator + "ORDER: "+orderNumberStr);
            }
            connection.close();
		}
		catch (Exception e) 
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE);
			sLog.info(separator + "QUERY: " + query);
			System.out.println(e);
			System.out.println(separator + "Exception occurred while trying to connect to the Siebel Database for validation:\r\n" + e.getStackTrace().toString());
		}
		finally
		{
			TestRun.endTest();
			TestRun.endSuite();
		}
		return outputMap;
	}
}

