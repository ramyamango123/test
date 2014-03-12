for i in range(4, 8):
    is_prime = 1
    for j in range(2, i):
        if i % j == 0:
            is_prime = 0
            break
        else:
            continue
    if is_prime == 1:
        print "the number is prime", i
    else:
        print "the number %d is not a prime:" % (i)

        
    
    
