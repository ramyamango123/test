in_text = open("addresses.txt", 'r')
#text = in_text.read()
l = []
for eachline in in_text:
    #print eachline
    p = eachline.split(" ")
    first_name = p[0]
    last_name = p[1]
    full_name = p[0] +" " + p[1]
    #print full_name
    l.append(full_name)
print l
l2 = sorted(l)
print l2
in_text.close()