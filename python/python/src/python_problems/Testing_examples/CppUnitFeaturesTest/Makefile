######################################################################
# LCG:Applications Area, Software Process Infrastructure             #
#     Testing component                                              #
# This is a simple makefile that allows to compile the               #
# CppUnit test example.                                              #
######################################################################

#CppUnit library and includes  
LDFLAGS=$(CPPUNIT_LDFLAGS)
#CppUnit includes 
INCLUDES=-I$(CPPUNIT_INCLUDES) 
 
#Executable that will be produced, choose yours 
EXEC_TEST_NAME=CppUnitFeaturesTest_TDCppUnit

OBJTESTSOURCE=$(EXEC_TEST_NAME).o \
              ExampleTestCase_CppUnit.o
                                 # Objects need, add those that will 
                                 # be needed. As many as test classes 
                                 # you have.  
 
# Default goal, can also be invoked by use of the command "make all"
all:	$(EXEC_TEST_NAME)


$(EXEC_TEST_NAME).o:
	$(CC) -c $(INCLUDES) $(EXEC_TEST_NAME).cpp
ExampleTestCase_CppUnit.o:
	$(CC) -c $(INCLUDES) ExampleTestCase_CppUnit.cpp

# First test collection, notice the tab before $(CC)
$(EXEC_TEST_NAME):	$(OBJTESTSOURCE) 
	$(CC)  $(CCOPTS) -o $(EXEC_TEST_NAME) $(LDFLAGS) $(OBJTESTSOURCE)  $(LDFLAGS)

# Runs the tests
check: 
	-./$(EXEC_TEST_NAME)

# Clean procedure
clean:
	rm *.o $(EXEC_TEST_NAME)























