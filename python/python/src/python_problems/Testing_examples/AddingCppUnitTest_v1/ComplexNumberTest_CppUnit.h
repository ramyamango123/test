#ifndef COMPLEXNUMBERTEST_H
#define COMPLEXNUMBERTEST_H

#include<cppunit/extensions/HelperMacros.h>
#include"Complex.h"

/// Unit tests for ComplexNumberTest
class ComplexNumberTest : public CppUnit::TestFixture 
{
 CPPUNIT_TEST_SUITE( ComplexNumberTest );
 CPPUNIT_TEST( testEquality );
 CPPUNIT_TEST( testAddition );
 CPPUNIT_TEST_SUITE_END();
 
 private:
   Complex *m_10_1, *m_1_1, *m_11_2;
 public:
   void setUp();
   void tearDown();

   void testEquality();
   void testAddition();
};
#endif  // COMPLEXNUMBERTEST_H

