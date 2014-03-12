# Customer_name|product_category|item_description|cost
file_open = open("customer_logfile.txt", 'r')

dict1_username = {}
dict1_each_category = {}
dict1_just_category_names = {}
category_list = []
for eachline in file_open:
    #print "eachline", eachline
    x = eachline.rstrip('\n')
    parsed_line = x.split('|')
    print parsed_line
    key = parsed_line[0]
    value = parsed_line[3]
    #print key, value 
    if key not in dict1_username:
        dict1_username[key] = float(value)
    else:
        dict1_username[key] = float(dict1_username[key])+ float(value)
    key_category = key + "_" + parsed_line[1]
    print "key_category", key_category
    if key_category not in dict1_each_category:
        dict1_each_category[key_category] = float(value)
    else:
        dict1_each_category[key_category] = float(dict1_each_category[key_category]) + float(value)
    just_category_names = parsed_line[1]
    dict1_just_category_names[just_category_names] = True
print "dict1_username", dict1_username
print dict1_each_category
print dict1_just_category_names.keys()
for eachcustomer in dict1_username:
    print eachcustomer + "- $" + str(dict1_username[eachcustomer]) + " Tax - $" + str((0.0925 * dict1_username[eachcustomer]))

    
for eachcustomername in dict1_username.keys():
    
    print "purchases by " + eachcustomername + ":"
    for eachcategory in dict1_just_category_names.keys():
        key_category_name = eachcustomername + "_" + eachcategory
        if key_category_name in dict1_each_category:
            print eachcategory + "- $ " + "$" + str(dict1_each_category[key_category_name]) + ", Tax " + "$" + str((0.0925 * dict1_each_category[key_category_name]))
        

    
    