# If we import class then all methods are automatically imported. Howvever while using it in methods u have to create an instance of tha class and then required method
from file_validity import File_test
# We cannot directly import class method. Only if that method is outside class u can do this. So firt import starement is valid.
#from file_validity import file_validity_check

# Static
import unittest


class File_validity_check(unittest.TestCase):
        
    # pass valid filename
    def test1(self):
        valid_filename = "filedata.txt"
        f1 = File_test("A", valid_filename)
        f2 = File_test("B", valid_filename)
        print f1.name
        print f2.name
        
        print File_test.name
        
        result = f1.file_validity_check()
        File_test.static_test()
        print result
        assert result == 1
    
    def test2a(self):
        expected_data = "Error:can\'t find file or read data"
        invalid_filename = "filedata1111.txt"
        # If I don't pass anything to constructor, then its default value will be stored
        f2 = File_test()
        # To change the existing instance value call this setFilename method and pass the reqd parameter
        f2.setFilename(invalid_filename)
        result = f2.file_validity_check()
        print result
        assert result == expected_data
        
   # pass non-existing filename
    def test2(self):
        expected_data = "Error:can\'t find file or read data"
        invalid_filename = "filedata1111.txt"
        f2 = File_test(invalid_filename)
        result = f2.file_validity_check()
        assert result == expected_data
        
    # pass empty filename
    def test3(self):
        empty_filename = ""
        f3 = File_test(empty_filename)
        result = f3.file_validity_check()
        print "test3 result:", result
        
    
    # pass none filename
    def test4(self):
        none_filename = None
        f4 = File_test(none_filename)
        result = f4.file_validity_check()
        print "test4 result:", result
        
    # pass integer  
    def test5(self):
        dirname = 66778
        f5 = File_test(dirname)
        result = f5.file_validity_check()
        print "test5 result:", result
        
  # To run individual testcase from command line, use python filename classname.methodname   

if __name__ == "__main__":
    
    unittest.main()

        