# Customer_name|product_category|item_description|cost
file_open = open("customer_logfile.txt", 'r')

dict_name_categories ={'Susie_tobacco': 15.0, 'Susie_fuel': 44.899999999999999, 'Nitin_tobacco': 15.0, 'Pedro_fuel': 9.5999999999999996, 'Pedro_groceries': 1.4199999999999999, 'Susie_groceries': 10.25}
category_list = []
lastname = ""
for eachline in file_open:
    #print "eachline", eachline
    x = eachline.rstrip('\n')
    parsed_line = x.split('|')
    #print parsed_line
    key = parsed_line[0]
    value = parsed_line[3]
    
for each_name_category in sorted(dict_name_categories.keys()):
    split_name_category = each_name_category.split("_")
    print " split_name_category",  split_name_category
    if lastname != split_name_category[0]:
        print "purchases by " + split_name_category[0]
        lastname = split_name_category[0]
     
    print split_name_category[1] + " : " + str(dict_name_categories[each_name_category])+ " " + "&" + "Tax - " + str((0.0925* dict_name_categories[each_name_category]))
    
'''Purchases by Susie:
fuel - 44.9, Tax - $4.15325
groceries - 10.25, Tax - $0.948125
tobacco - 15.0, Tax - $1.3875'''
