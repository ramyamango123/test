import MySQLdb
import pprint

p = pprint.pprint

# Open database connection



def execute_script(cmd):
    global cursor
    cursor.execute(cmd)
    output = cursor.fetchall()
    return output
    


db = MySQLdb.connect("localhost","odbc","","test" )
# prepare a cursor object using cursor() method
cursor = db.cursor()
cursor.execute("SELECT VERSION()")
cursor.execute("use test")    
result = execute_script("show tables")
p(result)
for i in result:
    print i[0]
    r1 = execute_script("select * from " + i[0])
    p(list(r1))
    
cursor.close()
db.close()