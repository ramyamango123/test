import re

file1 = open("file_count_remove_duplicate.txt", 'r')
x = file1.readlines()
print x
count = 0
countchar = 0
for eachline in x:
    #print eachline
    count += 1
print count
y = ''.join(x)
print "y", y
p = re.compile( '(#|\$|\.)')
res = p.sub( '', y)
print "res:-", res
z = res.split()
print "z:", z
print len(z)
for eachword in z:
    for eachchar in eachword:
        countchar += 1
print countchar
    

'''line_count = 0
word_count = 0
#letter_count = 0

for eachline in file1:
    print eachline
    #p = re.complie( 
    #if '#' in eachline:
        #eachline.sub('#', '')
    
    line_count += 1
    #splitwords = eachline.split() 
    #print "split", splitwords'''
    
    
    
    
        
     
        
    
#print line_count
file1.close()
    
