#include <stdio.h>
#include <math.h>

int main(void)
{
  void foo(int num);
  int num; 
  scanf("%d",&num);
  foo(num);
}

void foo(int bar)
{
   double dbar;
   if (bar < 0) {
      printf("negative\n");
   }
   else if (bar > 0) {
      printf("positive\n");
   }
   else {
   printf("zero\n");
   }

   if (bar % 2) {
     printf("odd\n");
   }
   else {
     printf("even\n");
   }
   
   printf("squared = %d\n",bar*bar);
   if (bar > 0) {
     dbar = bar;
     printf("square root = %g\n",sqrt(dbar));
   }

   exit(0);
}
