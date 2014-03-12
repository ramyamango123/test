p = input("Enter a day no between 1-7:")
print p
Days = ['Mon','Tue','Wed','Thu','Fri','Sat','Sun']
if p <= 7:
    print "The day is", Days[p-1]