# print odd and even numbers from 1 to 100
print "Even numbers","\t", " Odd numbers:"
for num in range(1,101):
    if (num%2)== 0:
        print num
    else:       
        print "odd num",num
    if (num % 2) != 0:
        print num

