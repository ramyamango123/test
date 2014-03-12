def this_fails():
    x = 1/0
    try:
        this_fails()
    except ZeroDivisionError, detail:
        print 'Handling run-time error:', detail

