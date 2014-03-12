#ifndef EXAMPLETESTCASE_H
#define EXAMPLETESTCASE_H

#include <cppunit/extensions/HelperMacros.h>
#include <vector>

#include <iostream>
#include <exception>
#include <stdexcept>

using namespace std;

/* 
 * A test case that is designed to produce
 * example errors and failures
 *
 */

class ExampleTestCase : public CppUnit::TestFixture
{
  CPPUNIT_TEST_SUITE( ExampleTestCase );
  CPPUNIT_TEST( example );
  CPPUNIT_TEST( anotherExample );
  CPPUNIT_TEST( testAdd );
  CPPUNIT_TEST( testEquals );
  CPPUNIT_TEST_EXCEPTION(testVectorAtThrow, std::invalid_argument);
  CPPUNIT_TEST_EXCEPTION(testexceptionthrows, int);
  CPPUNIT_TEST_FAIL( testAssertFalseFail );
  CPPUNIT_TEST_SUITE_END();

private:
  double m_value1;
  double m_value2;

public:
  void setUp();
  void tearDown();
  void example();
  void anotherExample();
  void anotherExample1();
  void testAdd();
  void testEquals();
  void testVectorAtThrow();
  void testexceptionthrows();
  void testAssertFalseFail();
};

#endif //EXAMPLETESTCASE_H

