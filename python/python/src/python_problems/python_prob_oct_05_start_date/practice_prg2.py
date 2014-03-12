
dict1_username = {'Nitin': 15.00, 'Pedro': 11.02, 'Susie': 70.150000000000006}
for eachcustomer in dict1_username:
    #print dict1_username[eachcustomer]
    print eachcustomer + "- $" + str(dict1_username[eachcustomer])+ \
                        "," + " Tax - $" + str(0.0925 * dict1_username[eachcustomer])
    
