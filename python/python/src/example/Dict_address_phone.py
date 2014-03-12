import datetime

file_open = open("C:\\Users\\Ramya\\QAdata_as_of_Feb172011\\python_problems\\other_python_prob\\address_phove.csv", 'r')
dictcustomerdata = {}

for eachcustomer in file_open:
   # print eachcustomer
    result = eachcustomer.rstrip("\n").split(",");
   # print "result", result
    if result[0] == "Name":
        continue
    name_key = result[0];
    ph_no_value = result[1];
    dob_value = result[2];
    dictcustomerdata[name_key] = [ph_no_value, dob_value ]
   # Actual_customer_DOB = datetime.datetime.strptime(dictcustomerdata[name_key][1], "%m/%d/%Y")
    targted_year = datetime.datetime.strptime("1970", "%Y")
for eachcustomer in dictcustomerdata.keys():
    actual_customer_DOB = datetime.datetime.strptime(dictcustomerdata[eachcustomer][1], "%m/%d/%Y")
    if (actual_customer_DOB < targted_year):
        print eachcustomer, "DOB :", actual_customer_DOB, "is below 1970 and Ph no is", dictcustomerdata[eachcustomer][0]
    else:
        print eachcustomer , "DOB " , actual_customer_DOB , " is not below 1970" 
        
#print dictcustomerdata
            
        
    
     





    
    