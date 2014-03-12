# To convert string that has & and = to dictionary with key and values
# Here first we split the string based on &. Then split the list  based on = , we get seperate lists in one big list
# Grab each item in list and then put into one new list

import re
s1 = "username=mexAPU8Cfz&titleCode=KFPW&referralUrl=http://www.gaziilion.com&service=register&referrer=&emailAddress=mexAPU8Cfz@brainquake.com&gameUserId=1091011206580855667102122&password=password"
# Creating an empty dictionary
dict = {}
# Creating an empty list
new_list = []

#Cannot split both on & and = together, so first splitting just based on &
p1 = s1.split("&")
#print "p1", p1 # Here we get list with till = in it (['username=mexAPU8Cfz', 'titleCode=KFPW' so on....])

# After splitting based on & we  get a list. Now we are splitting list based on =
Stuff = [i.split("=") for i in p1]
#print "Stuff", Stuff # Here after splitting list based on =, we get separate lists in big list ( ['username', 'mexAPU8Cfz'], ['titleCode', 'KFPW'] so on...]

for j in Stuff:
    #print "j", j # Here all lists that are present inside are displayed seperately 
    #(['username', 'mexAPU8Cfz']
    # ['titleCode', 'KFPW']
    #['referralUrl', 'http://www.gaziilion.com'] so on...
    
    for k in j:
        #print "k", k # here lists are split into strings
        #username
       # mexAPU8Cfz
        #titleCode
        #KFPW so on..
        # appending all the items to new list new_list
        new_list.append(k)
        
#print "new_list", new_list  # Here all items in single list  (['username', 'mexAPU8Cfz', 'titleCode', 'KFPW', 'referralUrl, so on..]
new_list_length = len(new_list) # length of list new_list
#print new_list_length

# for loop incrementing by 2
for item in range(0, int(new_list_length), 2):
    #print item
    key = new_list[item] 
    value = new_list[item + 1]
    #print "key and values are:", key, value
    # Assign value to the respective key and infally print the whole dictionary
    dict[key] = value
print dict


                         

                         
                         
 