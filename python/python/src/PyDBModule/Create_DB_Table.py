import MySQLdb
import pprint
from DBConnection import run


p = pprint.pprint 


def db_connection():        
    global cursor
    conn = MySQLdb.connect(host = "localhost", user = "root", passwd = "", db = "test")
    cursor = conn.cursor()
    cursor.execute("SELECT VERSION()")
    cursor.execute("use test")
  
run("drop table employee") 
sql = """create table employee 
      (
       p_Id int,
       FirstName char(20) not null,
       LastName varchar(20),
       Age int,
       sex char(1),
       income float)"""

result = run(sql)
p(result)
result1 = run("show tables")
employee_table = result1[0][0]
p(employee_table)
result3 = run("select * from " + employee_table)
p(result3)

       


    
    
        
        
