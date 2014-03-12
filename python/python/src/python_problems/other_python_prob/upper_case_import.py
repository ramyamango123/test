#import upper_case
from upper_case import upper_case_convert
import unittest

class Test_uppercase_convert(unittest.TestCase):
    
    
    def test1(self):
        data = 2
        assert upper_case_convert(data) == 2
    
    def test2(self):
        data = "MYSPACE"
        assert upper_case_convert(data) == "MYSPACE"
        
    def test3(self):
        data = None
        assert upper_case_convert(data) == None


if __name__ == "__main__":
    
    unittest.main()

