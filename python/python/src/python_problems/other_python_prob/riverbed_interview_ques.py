'''Pedro|groceries|apple|1.42
Nitin|tobacco|cigarettes|15.00
Susie|groceries|cereal|5.50
Susie|groceries|milk|4.75
Susie|tobacco|cigarettes|15.00
Susie|fuel|gasoline|44.90
Pedro|fuel|propane|9.60'''

# Open input file for reading
#
try:
    file_open = open("log_file.txt", 'r')
    #print file_open.readlines()
    
except IOError:
    print "File does not exists"
    exit(0)

#
# Initialize empty dictionaries of names, name_categories and categories.
#
dict_names = {}
dict_name_categories = {}
dict_categories = {}

#
# Iterate through each line and parse it
#
for eachline in file_open:
    x = eachline.rstrip('\n')
    parsed_line = x.split('|')
    if len(parsed_line) != 4:
        print "Bad format"dict_name_category
        continue
    1
    key_name = parsed_line[0]
    value_price = float(parsed_line[3])

    #
    # Store the price of each name found, in the dictionary of names.
    # If entry already exists, add the subsequent prices to get total price per
    # name.
    if dict_names.has_key(key_name):
        dict_names[key_name] = dict_names[key_name] + value_price
        print "dict_names[key_name]", dict_names[key_name]
    else:
        dict_names[key_name] = value_price
    
    #
    # In order to get total price of each name per category, maintain a new
    # dictionary with name_category as the key.
    #
    key_name_category = parsed_line[0] + "_" + parsed_line[1]
    value_name_category_price = float(parsed_line[3])
    
    if dict_name_categories.has_key(key_name_category):
        dict_name_categories[key_name_category] = \
            dict_name_categories[key_name_category] + value_name_category_price
        print "dict_name_categories[key_name_category]",dict_name_categories[key_name_category]
    else:
        dict_name_categories[key_name_category] = value_name_category_price
    
    #
    # In order to track the list of categories, maintain yet another dictionary
    # of just the categories.
    #
    key_category = parsed_line[1]
    dict_categories[key_category] = True
     
#
# Report1
#
# Iterate through the dictionary of names and print the total price of each name
# along with sales tax.
#
for name in sorted(dict_names.keys()):
    print name + " - $" + str(dict_names[name]) + \
                 ", Tax - $" + str((0.0925 * dict_names[name]))
    
print ""

#
# Report 2
#
# Iterate through names dictionary and for each name found, iterate again through
# the list of all categories. This gives the price of each name, on a per category
# basis
#
print "dict_names", dict_names
print "dict_categories.keys()", dict_categories.keys()
print dict_name_categories
print "dict_name_categories.keys()", dict_name_categories.keys()
for name in sorted(dict_names.keys()):
    print "Purchases by " + name + ":"
    for category in dict_categories.keys():
        name_category = name + "_" + category
        if dict_name_categories.has_key(name_category):
            
            #
            # There is an entry for this name_category cobination. Print the value
            # along with the sales tax.
            #
            print category + " - " + str(dict_name_categories[name_category]) + \
               ", Tax - $" + str((0.0925 * dict_name_categories[name_category]))
    print ""

#
# Close the input file.
#
file_open.close()

