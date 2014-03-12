
# print in reverse order
#a = "abcde"
#
##for i, j in enumerate(range(len(a))):
##    print i, a[j]
#
#for i, j in enumerate(range(len(a))):
#    print len(a)-1-i, a[(len(a)-1) -i]


# Put all the list items to dict and count how many
# people are between 1-10, 10-20, 20-30, 30-40

age_check = ['ramya', 10, 'sow', 25, 'priyush', 35, 'soumya', 8, 'rrr', 37]
dict = {}
list = [0, 0, 0, 0]
prev1 = ""
for i in range(0, len(age_check)-1, 2):
    #print age_check[i]
    key = age_check[i]
    value = age_check[i+1]
   # print key, value
    dict[key] = value
for key, value in dict.iteritems():
    #print key, value
    if (value >0 and value <=10):
        #prev1  = prev1 + " " +  key
        #print prev1 + " : are in the age group 1-10"
        list[0] += 1
        if list[0] > 1:
            prev1  = prev1 + " and " +  key
        else:
            prev1  = prev1 + key
#    
#    if (value >10 and value <=20):
#        print key + " is in the age group 10-20"
#        list[1] += 1
#        
#    if (value >20 and value <=30):
#        print key + " is in the age group 20-30"
#        list[2] += 1
#        
#    if (value >30 and value <=40):
#        print key + " is in the age group 30-40"
#        list[3] += 1
#print list
#print prev1.split()

print prev1 + " : are in the age group 1-10: ", list[0]
#        
        

# 
