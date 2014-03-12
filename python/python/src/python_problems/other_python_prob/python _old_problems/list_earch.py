l = {'ram':3 , 'sow':5, 'priya':7, 'jera':8, 'ramu':3, 'eric':2, 'nag':1}
l1 = sorted(l)

for name in l1:
    print name, l[name]
    s = raw_input("enter a name:")
    if name == s:
        print "hello good morning:"
        break
    else:
        continue
    