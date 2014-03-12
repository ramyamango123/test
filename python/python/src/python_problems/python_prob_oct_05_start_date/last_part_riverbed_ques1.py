'''Purchases by Pedro:
groceries - $1.42
fuel - $9.60

Purchases by Nitin:
tobacco - $15.00

Purchases by Susie:
groceries - $10.25
fuel - $44.90
tobacco - $15.00'''

dict_new = {'Susie_tobacco': 15.0, 'Susie_fuel': 44.899999999999999, 'Nitin_tobacco': 15.0, 'Pedro_fuel': 9.5999999999999996, 'Pedro_groceries': 1.4199999999999999, 'Susie_groceries': 10.25}
lastname = ''
for eachitem in sorted(dict_new.keys()):
    split_item = eachitem.split("_")
    if split_item[0] != lastname:
        print "purchases by :" + split_item[0]
        lastname = split_item[0]
    print split_item[1] + "- " + str(dict_new[eachitem]) + " Tax -" + str(0.0925*dict_new[eachitem])
    
    