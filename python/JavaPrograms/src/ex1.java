private static void runLoginWithExistingProSeriesProdAcct(String apdProseriesProdURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)

	{
		
		String username = "psc_unl_not_renewed";
		String password = "test123";
		String existingfirmCAN = "237074551";
		String firmname = "APD Test psc_unl_not_renewed";
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test"; 
		String itemName = "ProSeries Basic 1040 Federal";
		String page = "autoRenewalPageWithSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "ProSeries";
		String accountPageName = "myproseries";
		String segmentName = "ProSeriesBasicOptedInAutorenewalSignUp";
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";
		


		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.loginWithExistingProdAccount(username, password, existingfirmCAN, firmname, apdProseriesProdURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl,
				estoreDbPassword, MDMDbUserName, profileDbUserName,maxPageLoadWaitTimeInMinutes,newUserEmail, userFirstName, userLastName, 
				creditCardNumber, creditCardMonth, creditCardYear, paymentType, lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, 
				itemName, page, productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL);

		
		private static void runProseriesNewUserSignupProd(String apdProseriesProdURL,String estoreDbUrl, String mdmDBCacheURL, 
				String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)

		{
			String username = "psc_unl_not_renewed";
			String password = "test123";
			String existingfirmCAN = "237074551";
			String firmname = "APD Test psc_unl_not_renewed";
			String zipcode = "94086";
			String creditCardNumber="4444444444444448"; 
			String creditCardMonth = "1"; 
			String creditCardYear = "2021";
			String maxPageLoadWaitTimeInMinutes = "40";
			String newUserEmail = "ramya_nagendra@apdqa.com";
			String userFirstName = "Ramya"; 
			String userLastName = "Nagendra";
			String paymentType = "Checking";
			String routingNumber = "011302616";
			String accountNumber = "1234";
			String lastThreeDigitsAccNo = "234";
			String lastFourDigitsRoutingNo = "2616";
			String bankAccName = "test"; 
			String itemName = "ProSeries Basic 1040 Federal";
			String page = "autoRenewalPageWithSignup";
//			String siebelDbUserName = "diaguser";
//			String siebelDbPassword = "diaguser";
//			String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
			String productName = "ProSeries";
			String accountPageName = "myproseries";
			String segmentName = "ProSeriesBasicOptedInAutorenewalSignUp";
			String estoreDbPassword = "atg";
			String MDMDbUserName = "mdmuser";
			String profileDbUserName = "profiledbuser";


			NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
			newCustomerNewOrder.newAcctCreationProdAPD(username, password, existingfirmCAN, firmname, zipcode, apdProseriesProdURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl,
					estoreDbPassword, MDMDbUserName, profileDbUserName,maxPageLoadWaitTimeInMinutes,newUserEmail, userFirstName, userLastName, 
					creditCardNumber, creditCardMonth, creditCardYear, paymentType, lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, 
					itemName, page, productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, null);


		}