

fp = open("strnums.txt", 'r')
text_data = fp.readlines()
print text_data
list1 = []
for i in text_data:
    print "i", i
    res = i.split()
    print res
    for j in res:
        list1.append(j)
        for k in list1:
            print "k", k
            try:
                int(k)
                print k
                return True
            except TypeError:
                return False
                
print list1
       

