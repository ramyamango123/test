'''Pedro|groceries|apple|1.42
Nitin|tobacco|cigarettes|15.00
Susie|groceries|cereal|5.50
Susie|groceries|milk|4.75
Susie|tobacco|cigarettes|15.00
Susie|fuel|gasoline|44.90
Pedro|fuel|propane|9.60'''

in_text = open("C:\\Users\\Ramya\\QAdata_as_of_Feb172011\\python_problems\\other_python_prob\\log_file.txt", 'r')
dict1 = {}
dict_name_category = {}
dict_category= {}
for i in in_text:
    result = i.rstrip().split("|")
   # print result
    key = result[0]
    value = float(result[3])
    if dict1.has_key(key):
        dict1[key] = dict1[key] + value
    else:
        dict1[key] = value
        
        
    key_name_category = result[0] + "_" + result[1]
    value_name_category_price = float(result[3])
    if dict_name_category.has_key(key_name_category):
        dict_name_category[key_name_category] = dict_name_category[key_name_category] + value_name_category_price
    else:
        dict_name_category[key_name_category] = value_name_category_price
    
    
    key_category = result[1]
    dict_category[key_category] = True
    
    
    
    
print "dict1", dict1
print  "dict_name_category", dict_name_category
print "dict_category", dict_category

# Report 1

for eachname in dict1:
    print eachname + "-" + "$" + str(dict1[eachname]) + \
                    " , Tax - " + str(0.0925 * dict1[eachname])
    
 # Report 2
 
for eachname in dict1:
    print "purchases by " + eachname
    for eachcategory in dict_category:
         key_category = eachname + "_" + eachcategory 
         if dict_name_category.has_key(key_category):
             print eachcategory + " - " + \
             str(dict_name_category[key_category]) + " Tax - " \
             + str(0.0925 *  dict_name_category[key_category])
    print ""
    
             
         
    
    







in_text.close()

