from selenium import selenium
import unittest, time, re


class Adv_agencies(unittest.TestCase):
    selenium = None
    
   
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*firefox", "http://web.stg.auditude.com")
        self.selenium.start()
        
        
       # try to get to known state (campaign manager)
        sel = self.selenium
        sel.open("/campaign_manager.php")
        sel.wait_for_page_to_load("30000")

        # if we're at login page, then login
        if re.search(r"^[\s\S]*/login.php$", sel.get_location()):
            sel.open("/login.php")
            sel.type("u", "myspacesales")
            sel.type("p", "myspacesales")
            sel.click("submitForm")
            sel.wait_for_page_to_load("30000")
            
 # Test cases on Advertisers and agenices page
 
 # * Some routine methods for adding, editing, deleting on adv & agencies page
 
 # a) To find all names of the advertisers list      
    
    def findAllNames (self):
        sel = self.selenium
        
        # Gets the count of all the advertisers present in list
        
        row_count = sel.get_xpath_count("//div[@id='content_wrapper']/div[@id='content']/div[2]/div[@id='advertisers']/div/table/tbody/tr") 
        print "Total number of entries in the table is " + row_count
        print "Now looking for all entries "
        
        # creating an empty list to add all advertisers in the list
        
        entries_list = [ ] 
        for i in range (1, int(row_count)):
            cmd = "//div[@id='content_wrapper']/div[@id='content']/div[2]/div[@id='advertisers']/div/table/tbody/tr[" + str(i) + "]/td[1]"
  
            item = str(sel.get_text(cmd))
            
         # Appending advertisers to the list by converting to lower case (to match the adv font case in both front & back end)
         
            entries_list.append(item.lower()) 
        
        print entries_list
        return entries_list
    
 # b) To find the advertisers row number using get_xpath_count method
        
    def findXpathRowByName (self, name):
        sel = self.selenium
        
      # Gets the count of all the advertisers present in list  
      
        row_count = sel.get_xpath_count("//div[@id='content_wrapper']/div[@id='content']/div[2]/div[@id='advertisers']/div/table/tbody/tr")
        print "Total number of entries in the table is " + row_count
        print "Now looking for entry " + name
        
        # for loop to traverse through each advertiser in the list
        
        for i in range (1, int(row_count)):
            cmd = "//div[@id='content_wrapper']/div[@id='content']/div[2]/div[@id='advertisers']/div/table/tbody/tr[" + str(i) + "]/td[1]"
            
            # Gets the advertisers name from get_text method
  
            item = str(sel.get_text(cmd))
            
            # Checking if the advertiser names derived from xpath pattern & user entry names are same 
            
            if item == name:
                print "Found entry for " + name + " in row " + str(i)
                return i
        
        print "Oops, entry " + name + " NOT found !!"
        return 0
    
  # c)  To find the xpath pattern for edit or delete action type
  
         # action_type = edit or delete
         # name        = Entry entered by user
         
    def findXpathByName(self, name, action_type):
        print(name)
        row = self.findXpathRowByName(name)
        # If the advertiser searched is not present returns null
        if row == 0:
            return ""
        action = ""
        # If the action_type passed from the user is edit, its appropriate column number is appended  to xpath pattern
        if action_type == "edit":
            action = "/td[1]/a[1]"
            # If the action_type passed from the user is delete, its appropriate column number is appended to xpath pattern
        elif action_type == "delete":
            action = "/td[2]/a[1]"
            
        xpath = "//div[@id='content_wrapper']/div[@id='content']/div[2]/div[@id='advertisers']/div/table/tbody/tr[" + str(row) + "]"
        xpath += action
        # Complete xpath pattern for the selected action_type is returned to user
        return xpath
    
# d) Routine steps to be followed to be on Adv & agencies page

    def advInit (self):
        sel = self.selenium
        sel.open("/campaign_manager.php?nav=campaign_manager")
        sel.click("link=Administration")
        sel.wait_for_page_to_load("30000")
        sel.click("link=Advertisers and agencies")
        sel.wait_for_page_to_load("30000")
        time.sleep(3)    
        return sel
    
    
    
  # e) Use this method to modify an existing advertier name in the table.
  
     #  existing_entry: An existing entry in the table to edit
     
     #  new_entry     : New name to change to.
   
    def editAdvEntry (self, existing_entry, new_entry):
        sel = self.selenium
         
        # Find xpath url for existing_entry in the table for editing.
        xpath = self.findXpathByName(existing_entry, "edit")
 
        # If the entry does not exist, then we cannot edit it!
        
        if xpath == "":
            print "Entry not found" + exsiting_entry
            return
        
        try:
            
            # Now click the edit action.
            
            sel.click(xpath)
            
            # Now modify the name to new_entry.
            
            sel.type("name", new_entry)
            
            # Click the save to button so that new_entry gets into the table.
            
            sel.click("save")
            
         # Some times add/edit also gives delete-confirmation exception !So take care of it here.
        
        except Exception:
            print "Caught an exception !"
            
        print "Sleeping for 5 secs after editing"
        time.sleep(3) 
    
  # f) Use this method to delete an existing advertiser name in the table.
    
     #  existing_entry: An existing entry in the table to delete
     
     #  *entered_names_list: It accepts any number of advertisers to delete bcz of (*)
    
    def deleteAdvEntry (self, *entered_names_list):
        sel = self.selenium
        
        for entered_name in entered_names_list:
            
            # Here method findXpathByName is called to find the complete xpath delete pattern
            
            xpath = self.findXpathByName(entered_name, "delete")
            
             # If the entry does not exist, then we cannot delete!
             
            if xpath == "":
                print "Entry " + entered_name + " is not present to delete"
                print "Sleeping for 5 secs after deletion"
                time.sleep(3)
                
                # If the entry is not present, moves to next adv name in the user entered list
                continue
            
            try:
                # Click on "ok" button when the pop-up window appears before deleting
                
                sel.choose_ok_on_next_confirmation()  
                
                # Click on the "x"/delete button when adv name delete xpath pattern is obtained from findXpathByName method
                
                sel.click(xpath)
                
                # Catch an exception here for delete-confirmation message
            except Exception:
                print "Caught an exception !"
            
            print "Sleeping for 5 secs after deletion"
            time.sleep(3)             
               
 
  # a) Test cases on Advertisers
 
    def test1(self):# Checking if Advertisers and agencies tab is present when clicked on "Administration"
        sel = self.selenium
        sel.open("/campaign_manager.php")
        sel.click("link=Administration")
        sel.wait_for_page_to_load("30000")
        try: self.assertEqual("Advertisers and agencies", sel.get_text("link=Advertisers and agencies"))
        except AssertionError, e: self.verificationErrors.append(str(e))
 
    def test2(self): # Checking if Advertisers and agencies page opens up when clicked on "Advertisers and agencies"
        sel = self.selenium
        sel.open("/campaign_manager.php?nav=campaign_manager")
        sel.click("link=Administration")
        sel.wait_for_page_to_load("30000")
        sel.click("link=Advertisers and agencies")
        sel.wait_for_page_to_load("30000")
        try: self.assertEqual("Advertisers and agencies", sel.get_text("link=Advertisers and agencies"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        try: self.failUnless(sel.is_text_present("*New Advertiser*"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        #try: self.assertEqual("New Advertiser", sel.get_text("//form[@id='advertiser_agency']/div[1]/ol/li[1]"))
        #except AssertionError, e: self.verificationErrors.append(str(e))
        
        
    
    def test3(self): # Checking if Advertisers tab is selected when user first visits this page
        sel = self.selenium
        sel.open("/campaign_manager.php?nav=campaign_manager")
        sel.click("link=Administration")
        sel.wait_for_page_to_load("30000")
        sel.click("link=Advertisers and agencies")
        sel.wait_for_page_to_load("30000")
        try: self.assertEqual("Advertisers and agencies", sel.get_text("link=Advertisers and agencies"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        try: self.assertEqual("New Advertiser", sel.get_text("//form[@id='advertiser_agency']/div[1]/ol/li[1]"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
             
        
    def test4(self): # Checking if user can create a new advertiser (with valid input) 
        sel = self.selenium
        sel.open("/campaign_manager.php?nav=campaign_manager")
        sel.click("link=Administration")
        sel.wait_for_page_to_load("30000")
        sel.click("link=Advertisers and agencies")
        sel.wait_for_page_to_load("30000")
        time.sleep(3)
        sel.type("name", "Geico123")
        sel.click("save")
        time.sleep(3)
        sel.refresh()
        time.sleep(3)
        try: self.failUnless(sel.is_text_present("exact:Geico123"))
        except AssertionError, e: self.verificationErrors.append(str(e))

        
    def test5(self): # Checking if user can create a new advertiser (with long name) 
        sel = self.selenium
        sel.open("/campaign_manager.php?nav=campaign_manager")
        sel.click("link=Administration")
        sel.wait_for_page_to_load("30000")
        sel.click("link=Advertisers and agencies")
        sel.wait_for_page_to_load("30000")
        time.sleep(3)
        sel.type("name", "web.stg.auditude.com,advertisers_and_agencies.php?")
        sel.click("save")
        time.sleep(3)
        sel.refresh()
        time.sleep(3)
        try: self.failUnless(sel.is_text_present("exact:web.stg.auditude.com,advertisers_and_agencies.php?"))
        except AssertionError, e: self.verificationErrors.append(str(e))

        
    def test6(self): # Checking if user gets an error message when the advertiser name field is left empty 
        sel = self.selenium
        sel.open("/campaign_manager.php?nav=campaign_manager")
        sel.click("link=Administration")
        sel.wait_for_page_to_load("30000")
        sel.click("link=Advertisers and agencies")
        sel.wait_for_page_to_load("30000")
        sel.click("save")
        time.sleep(3)
        try: self.failUnless(sel.is_text_present("Unable to create advertiser"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        #try: self.assertEqual("Unable to create advertiser", sel.get_text("//div[@id='ip_error_list_container']/div"))
        #except AssertionError, e: self.verificationErrors.append(str(e))
        
                
    def test7(self):# Checking if user gets an error message when the advertiser names are duplicate 
        self.test4()
        sel = self.selenium
        sel.type("name", "Geico123")
        sel.click("save")
        time.sleep(3)
        try: self.failUnless(sel.is_text_present("Advertiser 'Geico123' already exists"))
        except AssertionError, e: self.verificationErrors.append(str(e))
             
        
    def test8(self): # Checking if user can create an advertiser using numbers and special characters 
        sel = self.selenium
        sel.open("/campaign_manager.php?nav=campaign_manager")
        sel.click("link=Administration")
        sel.wait_for_page_to_load("30000")
        sel.click("link=Advertisers and agencies")
        sel.wait_for_page_to_load("30000")
        time.sleep(3)
        sel.type("name", "Test_123%^$!#*")
        sel.click("save")
        time.sleep(3)
        sel.refresh()
        time.sleep(3)
        try: self.failUnless(sel.is_text_present("exact:Test_123%^$!#*"))
        except AssertionError, e: self.verificationErrors.append(str(e))
  
        
    def test9(self): # Checking if newly created advertisers get listed in alphabetic order 
        sel = self.selenium      
        sel.open("/advertisers_and_agencies.php?nav=account_manager&nav_nested=advertisers_and_agencies")
        sel.click("link=Campaign manager")
        sel.wait_for_page_to_load("30000")
        sel.click("link=Administration")
        sel.wait_for_page_to_load("30000")
        sel.click("link=Advertisers and agencies")
        sel.wait_for_page_to_load("30000")
        time.sleep(3)
        sel.type("name", "!!!apple")
        sel.click("save")
        time.sleep(3)
        sel.type("name", "!!!Myspace")
        sel.click("save")
        time.sleep(3)
        sel.type("name", "!!!Yahoo")
        sel.click("save")
        time.sleep(3)
        sel.refresh()
        time.sleep(3)
        r1 = self.findXpathRowByName("!!!apple")
        r2 = self.findXpathRowByName("!!!Myspace")
        r3 = self.findXpathRowByName("!!!Yahoo")
        
        print r1
        print r2
        print r3
        
        if r1 < r2 and r2 < r3:
            print "In sorted order !"
        else:
            print "NOT sorted !!"
            self.assertEqual(1, 0)
        
            
           
    def test10(self): # Checking if clickable Edit button appears when you mouseover any of the saved advertisers 
        sel = self.selenium
        sel.type("name", "!!!Starbucks")
        sel.click("save")
        time.sleep(3)
        # Calling "findXpathByName" method in-order to find out the existing_entry row no by means of xpath
        #name = "!!!Starbucks"
        #action_type = "edit"
       
        self.findXpathByName("!!!Starbucks","edit")
        
        try:
            
            #
            # Now click the edit action.
            #
            sel.click(xpath)
            
        except Exception:
            print "Caught an exception !"
            
        print "Sleeping for 5 secs after deletion"
        time.sleep(3)
        try: self.failUnless(sel.is_text_present("exact:!!!Starbucks"))
        except AssertionError, e: self.verificationErrors.append(str(e))

             
    def test11(self): # Checking if user can edit the saved advertiser successfully 
        self.test10()
        sel = self.selenium
        sel.type("name", "!!!iphone")
        sel.click("save")
        sel.refresh()
        time.sleep(3)
        try: self.failUnless(sel.is_text_present("exact:!!!iphone"))
        except AssertionError, e: self.verificationErrors.append(str(e))
   
        
    def test12(self):
        sel = self.selenium
        sel.open("/advertisers_and_agencies.php?nav=account_manager&nav_nested=advertisers_and_agencies")
        self.assertEqual("Auditude", sel.get_title())
        sel.click("link=Advertisers and agencies")
        sel.wait_for_page_to_load("30000")
        self.assertEqual("Auditude", sel.get_title())
        sel.click("edit_367")
        try: self.assertEqual("!!!apple", sel.get_value("name"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        sel.click("save")
        try: self.failIf(sel.is_text_present("Unable to update advertiser"))
        except AssertionError, e: self.verificationErrors.append(str(e))
       
        
        
        
        
        
        
        
             
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()


        
                                        