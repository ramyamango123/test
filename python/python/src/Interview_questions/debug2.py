
f1 ='''
        dfjd fdlkfjds
        fdjdsf
        fdfjdf
        s'''
        
fw = open("text1.txt", 'w')
res = fw.write(f1)
fw.close()

fp = open("text1.txt", 'r')
file1 = fp.readlines()
print file1
fp.close()