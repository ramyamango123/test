import MySQLdb
import pprint
from DBConnection import run

def db_connection():        
    global cursor
    conn = MySQLdb.connect(host = "localhost", user = "odbc", passwd = "", db = "test")
    cursor = conn.cursor()
    cursor.execute("SELECT VERSION()")
    cursor.execute("use test")
   
result = run("show tables")
pprint.pprint(result)
table_name = result[0][0]
pprint.pprint(table_name)

r = run("select * from " + table_name)
pprint.pprint(r)


'''for i in result: 
   # print i
    data = run("select * from " + i)
    p(data)'''
    


    
    
        
        
