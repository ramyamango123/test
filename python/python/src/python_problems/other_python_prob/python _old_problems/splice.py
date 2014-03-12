file = open('Data.txt', 'r')
total = 0
count = 0





for everyline in file:
    count = 0
    #total = 0
    word_list = everyline.split()
    print word_list
    for eachword in word_list:
        count += 1
    print "count value is:",count
    total = total + count
print "Total value", total
    
    #L = len(words)
    #tota = total + L
    #count += 1
#print count
#print total
file.close()
    

    
    

