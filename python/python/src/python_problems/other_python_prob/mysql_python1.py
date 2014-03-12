
from selenium import selenium

def pprint (result):
    import pprint
    pprint.PrettyPrinter().pprint(result)
  
#x = "red"

dbConnection = MySQLdb.connect(host='localhost', user='root', passwd='mtsassay', db='test')
cursor = dbConnection.cursor()
#print '''SELECT name FROM fruits WHERE color = %s'''%(x)


def cleanup ():
    dbConnection.commit()
    cursor.close()
    dbConnection.close()
    exit()    

def execute (cmd):
    pprint(cmd)
    cursor.execute(cmd)
    return cursor.fetchall()
   
  
#found = cursor.execute('''SELECT * FROM fruits WHERE color = "%s"'''%(x))
result = execute("SELECT * FROM fruits")
pprint(list(result))
#print result
print len(result)
#cleanup()

for i in range(9002, 10000):
    cmd = '''DELETE from fruits where Id = "%s" ''' %(i)
    #execute(cmd)

#cleanup(cursor, dbconnection)
for i in range(9002, 10000):
    cmd = '''INSERT into fruits VALUES ('%s', 'grape', 'black', 100) ''' %(i)
    #execute(cmd)

cleanup()

    #cursor.execute('''UPDATE play_session
                          #SET expires = '2010-01-01 00:00:00'
                         # WHERE account_title_id = %s'''%(gameAcctId))