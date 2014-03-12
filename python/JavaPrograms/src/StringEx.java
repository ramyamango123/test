class StringEx{
	//private ArrayList<Item> lineItemsList;
	
//	public StringEx()
//	{
//		lineItemsList = new ArrayList<Item>();
//	}
//
//	public static void main(String[] args){
//		
//		//String s = String.valueOf(4);
////		String a1 = "123";
////		int n1 = Integer.parseInt(a1);
////		String a2 = "456";
////		int n2 = Integer.parseInt(a2);
////		int a3;
////		a3 = n1 + n2;
////		System.out.println("The value of s:" + 123);
////		String s = "Ramya";
////		s.substring("am");
//		String itemString = "ProSeries Basic 1040 Federal | 1099935";
//		//Item item = new Item();
//		itemString.setName(itemString.substring(0,itemString.indexOf("|")).trim());
//		item.setNumber(itemString.substring(itemString.indexOf("|")+1).trim());
//		lineItemsList.add(item);
//		outputMap.put("ItemsList", lineItemsList);
	
	private String querySiebelDBforCANUsingAccountName(Object dbUserName, Object dbPassword, Object dbUrl,Object accountName, boolean startTest)
	{
		//Required conversion of parameters
		String dbUserNameStr = (String)dbUserName;
		String dbPasswordStr = (String)dbPassword;
		String dbUrlStr = (String)dbUrl;
		String accountNameStr = (String)accountName;

		String query = "";
		sLog = Logger.getInstance();
		String CAN = "";

		if (startTest) {
			TestRun.startSuite("Query Siebel DB to verify CAN By Account name");
			TestRun.startTest("Validating Account name Sync to Siebel DB");
		}

		try 
		{

			Thread.sleep(20000);
			siebelDatabaseUtil = new DatabaseUtil();
			connection = siebelDatabaseUtil.createDatabaseConnection(dbUserNameStr, dbPasswordStr, dbUrlStr);
			int count = 0;
            while (count < 100)
            {
			query = "select * from siebel.s_org_ext where NAME='"+accountNameStr+"'";

			ResultSet rset;
			Statement mStatement;
			mStatement = connection.createStatement();
			rset = mStatement.executeQuery(query);
			System.out.println("Result" + rset);
            count += 1;
			boolean resultIsPresent = rset.next();
			
			if(resultIsPresent)
			{
				sLog.info(separator + "QUERY: " + query);
				sLog.info(separator + "ACCOUNT: \""+accountNameStr+"\" is successfully synched to Siebel.");
				sLog.info(separator + "CAN: "+rset.getString("INTEGRATION_ID"));
				CAN = rset.getString("INTEGRATION_ID");
				TestRun.updateStatus(TestResultStatus.PASS, "querySiebelDBforCANUsingAccountName passed");
			}
			else
			{
				if (count < 100)
					{
					 Thread.sleep(5);
					 continue;
					}
				TestRun.updateStatus(TestResultStatus.FAIL);
				sLog.info(separator + "QUERY: " + query);
				sLog.info(separator + "ACCOUNT: \""+accountNameStr+"\" did not sync to Siebel");
			}
			connection.close();
			
		}
	}
		catch (Exception e) 
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE);
			sLog.info(separator + "QUERY: " + query);
			sLog.info(separator + "Exception occurred while trying to connect to the Siebel Database for validation:");
		}
		finally
		{
			if (startTest)
			{
				TestRun.endTest();
				TestRun.endSuite();
			}
		}

		return CAN;
	}

	if(selenium.getLocation().toLowerCase().contains(productName.toLowerCase() + ".intuit.com/" + accountPageName))

}