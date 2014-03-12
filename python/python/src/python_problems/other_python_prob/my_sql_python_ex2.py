from selenium import selenium
import MySQLdb
import itertools

   
def pprint (result):
    import pprint
    pprint.PrettyPrinter().pprint(result)
 

class TestSql:
      
    def __init__ (self, dbname = "test"):
        self.dbConnection = MySQLdb.connect(host='localhost', user='root', 
                                            passwd='mtsassay', db=dbname)
        self.cursor = self.dbConnection.cursor() 
    
    def cleanup (self):
        self.dbConnection.commit()
        self.cursor.close()
        self.dbConnection.close()
        exit()
        
    def execute (self, cmd):
        pprint(cmd)
        self.cursor.execute(cmd)
        return self.cursor.fetchall()
    
    #@classmethod    
    def print_test (self):
        print "hello\n"
        
    def orderSort (self):
        command = "select OrderNo from orders"
        result = self.execute(command)
        l1 = list(itertools.chain(*result))
        l2 = l1[:]
        l2.sort()
        print l2
       # or
        #print sorted(list(itertools.chain(*result)))
        
        
        return result
        
testSql = TestSql()
#testSql2 = TestSql("newdb")
result = testSql.orderSort()
pprint(result)
TestSql.print_test()
    
testSql.cleanup()
    
        
       