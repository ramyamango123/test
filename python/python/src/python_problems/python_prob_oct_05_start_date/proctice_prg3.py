# Customer_name|product_category|item_description|cost
file_open = open("customer_logfile.txt", 'r')

dict1_username = {}
for eachline in file_open:
    #print "eachline", eachline
    x = eachline.rstrip('\n')
    print "x", x
    parsed_line = x.split('|')
    print parsed_line
    key = parsed_line[0]
    value = (parsed_line[3])
    print key, value 
    if key not in dict1_username:
        dict1_username[key] = float(value)
    else:
        dict1_username[key] = float(dict1_username[key])+ float(value)
print dict1_username

