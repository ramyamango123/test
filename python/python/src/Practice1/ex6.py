import unittest

from ex7 import File_validity

class FilePythonTest(unittest.TestCase):
    
    def test1(self):
        valid_file_name = "C:\\Users\\Ramya\\eclipseWorkspace\\Practice1\\validfile.txt"
        f1 = File_validity(valid_file_name)
        result = f1.fileTest()
        print result
        
    def test2(self):
        valid_file_name1 = "C:\\Users\\Ramya\\eclipseWorkspace\\Practice1\\validfile.txt"
        f1 = File_validity()
        f1.set_name("C:\\Users\\Ramya\\QAdata_as_of_Feb172011\\python_problems\\other_python_prob\\repeatwords.txt")
        result1 = f1.fileTest()
        print "set_name result", result1
        
        
        
if __name__ == "__main__":
    
    unittest.main()
