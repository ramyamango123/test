from file_validity_check_practice import File_test
import unittest

class File_validity_check(unittest.TestCase):
    
#    def test1(self):
#        valid_filename = "file1.txt"
#        f1 = File_test(valid_filename)
#        result = f1.file_validity_check()
#        print result
#        assert result == 1
    
#    def test2(self):
#        invalid_filename = "file2.txt"
#        f2 = File_test()
#        f2.setFilename(invalid_filename)
#        result = f2.file_validity_check()
#        print result
#        assert result == 1
#        
    def test3(self):
        
        f3 = File_test()
        result = f3.file_validity_check()
        print result
        assert result == 1
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
if __name__ == "__main__":
    unittest.main()