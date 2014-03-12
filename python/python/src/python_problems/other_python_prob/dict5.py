D = {'a': 1, 'b': 2, 'c': 3}

D.has_key('b')

if not D.has_key('a'):
    print 'missing'
else:
    print "value is", D['a']
