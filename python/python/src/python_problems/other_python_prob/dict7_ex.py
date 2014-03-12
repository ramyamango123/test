d = {'a':5, 'b':8, 'c':['ti', 'one', 'four']}
print d
new = d.copy()
#print new
new['d'] = 'test'
print new
new['c'].remove('one')
print new
