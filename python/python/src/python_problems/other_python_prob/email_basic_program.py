from selenium import selenium
import unittest, time, re

name = "ramya"
sel = self.selenium
print (sel.get_eval("'" + name + "'. toUpperCase()"))