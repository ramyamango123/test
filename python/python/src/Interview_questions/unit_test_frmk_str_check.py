
import unittest
import re
import os

class StringCheck:
    def __init__(self, file_name):
        self.fp = None
        self.text_data = []
        #self.file_name = []
        try:
            self.fp = open(file_name, 'r')
            self.text_data = self.fp.readlines()
        except:
            print "Invalid file name"
        
    def __del__(self):
        if self.fp != None:
            self.fp.close()
            
            
    def str_check_from_file(self):
        list_data = []
        self.list_str = []
        self.list_num = []
        print self.text_data
        all_are_strings = True
        for eachline in self.text_data:
            words_list = eachline.split()
            print "words_list", words_list
            
            for word in words_list:
                #list_data.append(word)
                print "word", word
                #print "j", j
                try:
                    int(word)
                    self.list_num.append(word)
                    print "word", word
                    all_are_strings = False
                except:
                    self.list_str.append(word)
        return all_are_strings
        #return list_data#
    def count(self):
        print self
        return len(self.text_data)
    
#### Unit test class ##### 
class StringCheckUnitTest(unittest.TestCase):
    def createFile(self, file_content):
        fw = open(self.valid_file_name, 'w')
        fw.write(file_content)
        fw.close()
        
    @classmethod
    def setUpClass(self):
        print "in setUpClass"
        # mysql = open connection
    
    @classmethod
    def tearDownClass(self):
        print "in tearDownClass"
        os.remove("strnums.txt")
        
    def setUp(self):
        print "in setUp"
        s = '''4556 hjgjgj 7677 uiu344 89989 12
099 tr5 98
23 764'''
        print s
        fw = open("strnums.txt", 'w')
        fw.write(s)
        fw.close()
    
    def test1_valid_file(self):
        self.table_name = "valid_names"
        # mysql.runCmd("create table " + self.table_name)
        self.file_name = "strnums.txt"
        self.obj1 = StringCheck(self.file_name)
        data = self.obj1.str_check_from_file()
        print data
        print "list_num", self.obj1.list_num
        print "list_str", self.obj1.list_str
        
#     def test2_invalid_file(self):
#         self.table_name = "invalid_names"
#         self.file_name = "strnums2.txt"
#         self.createFile(
#         '''dfjd fdlkfjds
#         fdjdsf
#         fdfjdf
#         s''')
#         obj1 = StringCheck("strnums2.txt")
#         self.assertEqual(4, obj1.count()) # count(obj1)

        pass
        
    def tearDown(self):
        print "in TearDown"
        # mysql.runCmd("drop table " + self.table_name)
        #os.remove(self.file_name)
        
    
if __name__ == "__main__":
    unittest.main()
        
        
        
            
        