
import MySQLdb
import unittest
import pprint


    
def run(cmd):
    global cursor
    conn = MySQLdb.connect(host = "localhost", user = "odbc", passwd = "", db = "test")
    cursor = conn.cursor()
    print 'Running SQL Command: "' + cmd + '"' 
    cursor.execute(cmd)
    output = cursor.fetchall()
    # print output.__class__.__name__
    #pprint.pprint(output)
    return output

#run("show tables")



   
    

    

