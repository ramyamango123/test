# To count the number of re-occuring words

p = ['horse', 'cat', 'rabbit', 'rat', 'cat', 'horse', 'snake', 'horse']

dict1 = {}
#count = 0
for i in range(len(p)):
        key = p[i]
        if key in dict1.keys():
                dict1[key] += 1
        else:
                dict1[key] = 1
print dict1
       
    
        
