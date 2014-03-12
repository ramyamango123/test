z = ['I', 'am', 'arranging', 'this', 'again', 'carry', 'this']
#countchar = 0
output_string = ""
dict1 = {}
list1 = []
#count = 0
## To find total no of chars in each word
#for eachword in z:
#    countchar = 0
#    for eachchar in eachword:
#        countchar += 1
#    print countchar
#    output_string = output_string + " " + str(countchar)
#print output_string
''' Result -  1 2 9 4 5 5 4'''

# To find total no of unique words
#for i in z:
#    if dict1.has_key(i):
#        dict1[i] =dict1[i] + 1
#    else:
#        dict1[i] = 1
#print dict1
''''Result : {'again': 1, 'I': 1, 'am': 1, 'carry': 1, 'this': 2, 'arranging': 1}'''

# To remove duplicate entry
#for i in z:
#    if i not in list1:
#        list1.append(i)
#print list1
'''Result: ['I', 'am', 'arranging', 'this', 'again', 'carry']'''

# To find unique words

#for i in z:
#    count = 0
#    for j in i:
#        #print j
#        count = count + 1
#    print count
    
'''Result -
1
2
9
4
5
5
4'''

#for i in z:
#    count = 0
#    if i not in list1:
#        print i
#        list1.append(i)
#        
            
        
ram = {'again': 1, 'I': 1, 'am': 1, 'carry': 1, 'this': 2, 'arranging': 1}

for i in ram.keys():
    if ram[i] == 1:
        print i
        
    



        