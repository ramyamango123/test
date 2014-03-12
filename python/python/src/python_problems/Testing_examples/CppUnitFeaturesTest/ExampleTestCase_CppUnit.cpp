#include "ExampleTestCase_CppUnit.h"

CPPUNIT_TEST_SUITE_REGISTRATION(ExampleTestCase);

void ExampleTestCase::setUp()
{
  m_value1 = 2.0;
  m_value2 = 3.0;
}

void ExampleTestCase::tearDown() 
{
}

void ExampleTestCase::example()
{
  CPPUNIT_ASSERT_DOUBLES_EQUAL( 1.0, 1.1, 0.15 );
  CPPUNIT_ASSERT( 1 == 1 );
  CPPUNIT_ASSERT( 1 == 0 );
}

void ExampleTestCase::anotherExample()
{
  CPPUNIT_ASSERT (1 == 2);
}
void ExampleTestCase::testAdd()
{
  double result = m_value1 + m_value2;
  CPPUNIT_ASSERT( result == 6.0 );
}


void ExampleTestCase::testEquals()
{
  std::auto_ptr<long> l1( new long (12) );
  std::auto_ptr<long> l2( new long (12) );

  CPPUNIT_ASSERT_EQUAL( 12, 12 );
  CPPUNIT_ASSERT_EQUAL( 12L, 12L );
  CPPUNIT_ASSERT_EQUAL( *l1, *l2 );
  
  CPPUNIT_ASSERT( 12L == 12L );
  CPPUNIT_ASSERT_EQUAL( 12, 13 );
  CPPUNIT_ASSERT_DOUBLES_EQUAL( 12.0, 11.99, 0.5 );
}
// How to deal with exceptions
 void ExampleTestCase::testVectorAtThrow()
{
        std::vector<int> v(2);
       v.at(1);     // must throw exception std::invalid_argument
}

void ExampleTestCase::testexceptionthrows()
{ 
//     This test if the expected exception is thrown or not
    throw  100;
}

// How to test for fail 
void ExampleTestCase::testAssertFalseFail()
{
  CPPUNIT_ASSERT( 1 == 0 );
}	
