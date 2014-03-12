INCLUDES = -I  $(CPPUNIT_INCLUDES)

# Rules for the test code (use `make check` to execute)
TESTS =AddingCppUnitTest_TDCppUnit
check_PROGRAMS = $(TESTS)

AddingCppUnitTest_TDCppUnit_SOURCES= AddingCppUnitTest_TDCppUnit.cpp \
                       ComplexNumberTest_CppUnit.h \
                       ComplexNumberTest_CppUnit.cpp \
                       Complex.h \
                       MoneyTest_CppUnit.h MoneyTest_CppUnit.cpp Money.h
AddingCppUnitTest_TDCppUnit_LDFLAGS = $(CPPUNIT_LDFLAGS) 
