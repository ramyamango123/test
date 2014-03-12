import datetime
import sys
#import pprint
 # When running from cmd, pass these command line arguments - account.csv, balance.csv
 # C:\Users\Ramya\QAdata_as_of_Feb172011\python_problems\other_python_prob>C:\Python26\python.exe acct_balance_pro.py account.csv balance.csv
 
#fileread1 = open(sys.argv[1], 'r')

#fileread2 = open(sys.argv[2], 'r')

 
fileread1 = open("account.csv", 'r')

fileread2 = open("balance.csv", 'r')
dict1 = {}
dict2={}
excel1_result_acct_file = fileread1.readlines()
for eachline in excel1_result_acct_file:
   result = eachline.rstrip("\n").split(',')
   #print result
   # Here key is account number and value is overdue value
   if (result[3] != 'account_number' and result[4] != 'overdue'):
       acct_key = result[3]
       acct_value = result[4]
  # print key, value
      if acct_value == None:
         acct_value = 0
      dict1[acct_key] = acct_value
print "dict1 :", dict1


        
print "-------------------\n--------------------"
# here reading the balance excel data
excel2_result_balance_file = fileread2.readlines()
for eachline1 in excel2_result_balance_file:
    result1 = eachline1.rstrip('\n').split(',')
    #print "result1", result1
      # Here key is account number and values are balance_due and balance_amt
    if (result1[1] != 'account_number' and result1[2] != 'balance'):
         balance_key_acc_name = result1[1]
         balance_value1_balance_due = result1[3]
         balance_value2_balance_amt = result1[2]
         dict2[balance_key_acc_name]= [balance_value1_balance_due, balance_value2_balance_amt]
        # print " dict2[balance_key_acc_name][0]", dict2[balance_key_acc_name][0]
        # print " dict2[balance_key_acc_name][1]", dict2[balance_key_acc_name][1]
print "dict2 :", dict2

# Here looping through each key from dictionary 1 and checking the condition - Any account with a balance 
#greater than zero that was due before Oct 23, 2007 has an overdue value of 1

for eachkey in dict1.keys():
    if dict2.has_key(eachkey):
        print "dict2[eachkey][0]", dict2[eachkey][0]
        actual_due_date = datetime.datetime.strptime(dict2[eachkey][0], "%m/%d/%Y %M:%S")
        expected_due_date = datetime.datetime.strptime("10/23/2007 00:00", "%m/%d/%Y %M:%S")
        print actual_due_date
        print dict2[eachkey][1]
        if (float(dict2[eachkey][1]) > 0.00) and (actual_due_date < expected_due_date):
            #overdue = 1
            if dict1[eachkey] == 0:
                print "overdue value was not set correctly for acct", eachkey
                dict1[eachkey] = 1         
        else:
            #overdue = 0
            if dict1[eachkey] == 1:
                print "overdue value was not set correctly for acct", eachkey
                dict1[eachkey] = 0
            
#        if (dict1[eachkey] == overdue):
#            print "overdue value is set correctly for acct", eachkey
#                
#        else:
#            dict1[eachkey] = overdue
#            print "overdue value was not set correctly for acct", eachkey
#            

print "afterwards", dict1
  

      
 


#pprint.pprint (dict1)

#    
#     
    


fileread1.close()

fileread2.close()