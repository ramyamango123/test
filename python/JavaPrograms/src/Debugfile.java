	public void verifyOLRQuotePage(Selenium selenium,String maxPageLoadInMs, String firmName, String CAN, String itemName)
	{
		try
		{
			TestRun.startTest("Verify OLR Quote page");
			// Clicks Renew Now button to launch OLR Quote page
			selenium.click("link=Renew Now");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			// Checks that the OLR Quote page was loaded successfully
			// https://sboqa.proseries.intuit.com/olr/index.jsp?s_ev1=myproseries_header_olr-button 
			if(selenium.getLocation().contains("/olr/index.jsp?s_ev1=MyProSeries_Header_OLR-Button"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The OLR Quote page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The OLR Quote page did not succesfully load." + 
						selenium.getLocation());
			}

			Assert.assertEquals("OLR Quote", selenium.getTitle());
			Assert.assertEquals("Add to Cart", selenium.getText("link=Add to Cart"));
			Assert.assertEquals(itemName , selenium.getText("css=div.item-name"));

			// Captures the entire text of the page
			String currentPageBodyText = selenium.getBodyText();

			//			System.out.println("----------------------------------------------");
			//			System.out.println(currentPageBodyText);
			//			System.out.println("----------------------------------------------");

			// Calling validatePrice utility method to validate item's price
			validatePrice(selenium, currentPageBodyText, "Regular:");
			validatePrice(selenium, currentPageBodyText, "Your Price:");
			validatePrice(selenium, currentPageBodyText, "Subtotal:");
			validatePrice(selenium, currentPageBodyText, "Total\\*:");

		}
		catch(Exception e)
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
	}

	// Utility method which helps to validate item's price
	public void validatePrice(Selenium selenium, String bodyText, String priceType)
	{
		String patternStr = priceType + "\\s+\\$(\\d+(\\.\\d{2})?)";
		// Compile and use regular expression
		Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(bodyText);
		boolean matchFound = matcher.find();

		if (matchFound)
		{
			String groupStr = matcher.group(1);
			float itemValue = (Float.valueOf(groupStr)).floatValue();
			System.out.println(priceType + " " + itemValue);

			// Checks if the item price is a non-zero value
			if  (itemValue > 0) 
			{
				TestRun.updateStatus(TestResultStatus.PASS,"Non-zero" + priceType + "item price is displayed on the account management page.");
				System.out.println(priceType + "good");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, priceType + "Item price displayed is <= zero on the account manapage page.");
				System.out.println(priceType + "bad");

			}
		}
		else 
		{
			System.out.println("No match");
			TestRun.updateStatus(TestResultStatus.FAIL, priceType + "Item price is not present on the account manapage page.");
		}
	}	
