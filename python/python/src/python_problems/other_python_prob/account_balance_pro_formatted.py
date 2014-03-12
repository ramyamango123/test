

'''We are testing an application that accesses two tables: account and balance.
The application under test processes users' accounts to ensure that any
overdue accounts are flagged appropriately. When the application under
test runs, it updates the overdue flag in the account table according to the
following rules:
Any account with a balance greater than zero that was due before Oct 23,
2007 has an overdue value of 1
Any account without an overdue balance has a NULL overdue value
(empty in the csv files) or an overdue value of 0

The application under test exports the two attached csv files for use by
another system.

Your job is to write an automated test script to verify that the data in the files
is correct. Solutions in Ruby, Perl, Java or Python will be accepted.

Your script should take in 2 command line arguments - the name of the
account file and the name of the balance file. The output should indicate any
problem accounts and nothing for accounts that have their overdue value
properly set.

Include in your response:
1. The script.
2. The output from running the script.
3. Any instructions needed to run the script.

Your script will be run against other data sets to ensure it will properly flag
situations not included in the sample files provided.'''

import datetime
import sys
#import pprint
 
dict1_key_acct_val_overdue = {}
dict2_key_act_val_balance_amt_due= {}

list1 =[]
list2 = []

def overdue_acct_check(acct_file, balance_file):
    
    #Reading the data from excel file - "Account.CSV" 
    
    excel1_result_acct_file = acct_file.readlines()
    
    #Reading the data from excel file - "Balance.CSV" 
    
    excel2_result_balance_file = balance_file.readlines()
    
   
   
   # Looping through each line from account file, stripping "\\n" and splitting based on ","
   
    for eachline in excel1_result_acct_file:
        
        result = eachline.rstrip("\n").split(',')
        
      #Here key is account number and value is overdue value
      
        if (result[3] != 'account_number' and result[4] != 'overdue'):
            
            acct_key = result[3]
            acct_value = result[4]
           # print key, value
         
            if acct_value == "":
                acct_value = 0
                
                
            dict1_key_acct_val_overdue[acct_key] = acct_value
    print "dict1_key_acct_val_overdue :", dict1_key_acct_val_overdue
    
    # Looping through each line from balance file, stripping "\n" and splitting based on ","
    
    for eachline1 in excel2_result_balance_file:
        
        result1 = eachline1.rstrip('\n').split(',')
        
         # Here key is account number and values are balance_due and balance_amt
         
        if (result1[1] != 'account_number' and result1[2] != 'balance'):
            balance_key_acc_name = result1[1]
            balance_value1_balance_due = result1[3]
            balance_value2_balance_amt = result1[2]
            dict2_key_act_val_balance_amt_due[balance_key_acc_name]= [balance_value1_balance_due, balance_value2_balance_amt]
           
           # print " dict2_key_act_val_balance_amt_due[balance_key_acc_name][0]", dict2_key_act_val_balance_amt_due[balance_key_acc_name][0]
           # print " dict2_key_act_val_balance_amt_due[balance_key_acc_name][1]", dict2_key_act_val_balance_amt_due[balance_key_acc_name][1]
    print "dict2_key_act_val_balance_amt_due :", dict2_key_act_val_balance_amt_due
   
   
   # Looping through each key from dictionary - dict1_key_acct_val_overdue and checking for the condition - "Any account with a balance 
   #greater than zero that was due before Oct 23, 2007 has an overdue value of 1"
    for eachkey in dict1_key_acct_val_overdue.keys():
        
        if dict2_key_act_val_balance_amt_due.has_key(eachkey):
            
            print "dict2_key_act_val_balance_amt_due[eachkey][0]", dict2_key_act_val_balance_amt_due[eachkey][0]
            
            actual_due_date = datetime.datetime.strptime(dict2_key_act_val_balance_amt_due[eachkey][0], "%m/%d/%Y %M:%S")
            
            expected_due_date = datetime.datetime.strptime("10/23/2007 00:00", "%m/%d/%Y %M:%S")
            
            print actual_due_date
            
            print dict2_key_act_val_balance_amt_due[eachkey][1]
            
            if (float(dict2_key_act_val_balance_amt_due[eachkey][1]) > 0.00) and (actual_due_date < expected_due_date):
                #overdue = 1
                if dict1_key_acct_val_overdue[eachkey] == 0:
                    list1.append(eachkey)
                    print "overdue value was not set correctly for acct", eachkey
                    dict1_key_acct_val_overdue[eachkey] = 1         
            else:
                #overdue = 0
                if dict1_key_acct_val_overdue[eachkey] == 1:
                    list2.append(eachkey)
                    print "overdue value was not set correctly for acct", eachkey
                    dict1_key_acct_val_overdue[eachkey] = 0
               
            #if (dict1_key_acct_val_overdue[eachkey] == overdue):
                   # print "overdue value is set correctly for acct", eachkey
           # else:
               # dict1_key_acct_val_overdue[eachkey] = overdue
                #print "overdue value was not set correctly for acct", eachkey
             
    acct_file.close()
    balance_file.close()
    print "afterwards", dict1_key_acct_val_overdue 
    problem_accts = list1 + list2
    print  "problem_accts", problem_accts
   
  

      
def main():
  # While running from cmd, pass these command line arguments - account.csv, balance.csv
  # C:\Users\Ramya\QAdata_as_of_Feb172011\python_problems\other_python_prob>C:\Python26\python.exe acct_balance_pro.py account.csv balance.csv
 
    acct_file = open(sys.argv[1], 'r')
    balance_file = open(sys.argv[2], 'r')
   
    #acct_file = open("account.csv", 'r')
    #balance_file = open("balance.csv", 'r')
  
    overdue_acct_check(acct_file, balance_file)


    
    
#
# Call the main routine of this script
   
main()

