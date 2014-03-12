#ifndef COMPLEX_H
#define COMPLEX_H

class Complex { 
   friend bool operator ==(const Complex& a, const Complex& b);
   friend Complex operator +(const Complex& a, const Complex& b);
   double real, imaginary;
 public:
   Complex( double r, double i = 0 ) 
     : real(r)
         , imaginary(i) 
   {
   }
 };

 bool operator ==( const Complex &a, const Complex &b )
 { 
   return a.real == b.real  &&  a.imaginary == b.imaginary; 
 }
 Complex operator +( const Complex &a, const Complex &b )
 { 
   return Complex(a.real+b.real,a.imaginary+b.imaginary); 
 }
#endif // COMPLEX_H 

