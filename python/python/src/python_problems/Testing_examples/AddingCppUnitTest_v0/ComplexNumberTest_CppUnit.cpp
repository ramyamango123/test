#include"ComplexNumberTest_CppUnit.h"

 CPPUNIT_TEST_SUITE_REGISTRATION(ComplexNumberTest);

   void ComplexNumberTest::setUp()
   {
     m_10_1 = new Complex( 10, 1 );
     m_1_1 = new Complex( 1, 1 );
     m_11_2 = new Complex( 11, 2 );  
   }

   void ComplexNumberTest::tearDown() 
   {
     delete m_10_1;
     delete m_1_1;
     delete m_11_2;
   }

   void ComplexNumberTest::testEquality()
   {
     CPPUNIT_ASSERT( *m_10_1 == *m_10_1 );
   }

   void ComplexNumberTest::testAddition()
   {
     CPPUNIT_ASSERT( *m_10_1 + *m_1_1 == *m_11_2 );
     CPPUNIT_ASSERT( *m_10_1 + *m_1_1 == *m_1_1 );
   }
