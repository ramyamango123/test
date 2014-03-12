import MySQLdb
import pprint
import datetime

def run(cmd):
    global cursor
    
    print 'Running SQL Command: "' + cmd + '"' 
    cursor.execute(cmd)
    output = cursor.fetchall()
    # print output.__class__.__name__
    pprint.pprint(output)
    return output
    
# select user(); in mysql to see the user name    
conn = MySQLdb.connect(host = "localhost", user = "odbc", passwd = "", db = "test")
cursor = conn.cursor()
cursor.execute("SELECT VERSION()")
cursor.execute("use test")

for i in run("show tables"):
    run("select * from " + i[0])
     
cursor.close()
conn.close()
