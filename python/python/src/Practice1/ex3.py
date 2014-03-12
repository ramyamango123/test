# To find no of chars present in each word of a list

z = ['I', 'am', 'arranging', 'this', 'again', 'carry', 'this', 'this']

for eachword in z:
    count = 0
    for eachchar in eachword:
        count += 1
    print "Total no of letters in" , eachword , "is :" , count
    