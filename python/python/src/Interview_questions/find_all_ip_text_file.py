import re
import unittest

#
# This is an example of how to write simple Python code in a class and also unit test the same using Unit test PYUNIT framework
#
# Q: How do you write tests (typically using a frame work) ?
#
# Test case functionality required are broken into smaller pieces. Each piece is written in a separate class
# PYUnit Unit-test framework is used to write test cases for each of the class functionality implemented.
#
# There would be a master test class too, which would typically test the end user browser actions. This test class would use helper
# class routines. Master test class is also typically implemented using PYUnit framework

# 
# e.g. Goal: Write tests to test a web page that contains numerous IP address like strings
#
# 1. Write helper class IPAddress that does basic IP address related testing as shown in this file
#        This typically involves taking necessary input information in the constructor (__init__())
#        Also provide a cleanup routine as appropriate
#
# 2. Write IPAddressTest unit test class to test the functionality provided by IPAddress helper class using Unit test framework
# 3. Finally write the master test class which using IPAddress helper class, tests the web page. This class shall also use PYUnit
#    test frame work to easily write and run various test cases
#

class IPAddress:
    def __init__(self, name):
        self.text_data = [ ] 
        self.fp = None
        
        try:
            self.fp = open(name, 'r')
            self.text_data = self.fp.readlines()
        except:
            print "File not found"
            pass
        
#       print self.text_data
        
    def __del__(self):
        if self.fp != None:
            self.fp.close()
        
    def find_ip_addresses(self):
        l1 = []
        for lines in self.text_data:
            res = lines.split()
            print "res", res
            for eachword in res:
#               print eachword
                r1 = re.search("(^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$)", eachword)
                #print r1
                if r1 != None:
                    l1.append(eachword)
#       print l1
        str = " "
        for i in l1:
            str = str + ", " + i
        print "IP addresses are :", str
        return l1
    
    def find_unique_ip_addresses(self):
        return [ ]
    
        
class IPAddressTest(unittest.TestCase):
    def setUp(self):
        self.j = 10
        pass
    
    def test1(self):
        print self.j
        input = '''
        1.2.3.4
        bal sdfdsfds
        1.2.3.5
        '''
        fp = open("ip_addresses_text_file", "w")
        fp.write(input)
        fp.close()
                
        self.ip_address1 = IPAddress("ip_addresses_text_file")
        list = self.ip_address1.find_ip_addresses()
        print "list", list
        self.assertEqual(2, len(list))
        
    def test_find_ip_addresses_with_no_file(self):
        self.ip_address1 = IPAddress("ip_addresses_text_junk_file")
        list = self.ip_address1.find_ip_addresses()
        self.assertEqual(0, len(list))
        
    def test2(self):
        input = '''
        1.2.3.4
        bal sdfdsfds
        1.2.3.5
        1.2.3.4
        2.3.4.5
        '''
        fp = open("ip_addresses_text_file", "w")
        fp.write(input)
        fp.close()
        
        self.ip_address1 = IPAddress("ip_addresses_text_file")
        list = self.ip_address1.find_ip_addresses()
        self.assertEqual(4, len(list))
 
     
    def test3(self):
        input = '''
        1.2.3.4
        bal sdfdsfds
        1.2.3.5
        1.2.3.4
        2.3.4.5
        1..2.3.4
        300.4.5.6
        '''
        fp = open("ip_addresses_text_file", "w")
        fp.write(input)
        fp.close()
        
        self.ip_address1 = IPAddress("ip_addresses_text_file")
        list = self.ip_address1.find_unique_ip_addresses()
        self.assertEqual(0, len(list))
    
    
    def tearDown(self):
        print self.j
        pass
        #self.ip_address1.cleanup()
        #self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()