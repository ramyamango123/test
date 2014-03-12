
# To find no of repetitions of each word in a list 
z = ['I', 'am', 'arranging', 'this', 'again', 'carry', 'this', 'this']
count = 0
dict1 = {}

for i, j in enumerate(z):
    key_name = j
    # or key_name = z[i]
    value_count = 0
        
    if dict1.has_key(key_name):
        dict1[key_name] += 1
    else:
        dict1[key_name] = 1
print "dict1", dict1




    
        
    
    
    
    
    