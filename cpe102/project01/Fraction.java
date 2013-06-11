/*
 * A program that takes in two fractions and is capable of completing multiple arithmetic computations with them.
 *
 *
 * @author Garrett Milster     
 * @version Program 1    
*/

public class Fraction
{
  private int n;
  private int d;

   public Fraction()
    {
      n = 0;
      d = 1;
    }

	public Fraction(int numerator)
    {
      n = numerator;
      d = 1;
    }

   public Fraction(int numerator, int denominator)
    {
	  String warning;
	  warning = "Zero is not an acceptable denominator";
      
      Fraction reduce =  simplify(numerator, denominator);
      
		 if(denominator <= 0)
		  {
		   throw new IllegalArgumentException(warning);
		  }else{
        
	     n = reduce.n;
        d = reduce.d;
         
		  }
    } 
	public int getNumerator()
    {
     return(n);
    }

	public int getDenominator()
    {
     return(d);
    }

	private Fraction simplify(int num, int denom)
	 {
	  int n1, n2;
	  int point1, point2;
	  int rem;
	  int gcf;
	  
	  rem = 1;
	  n1 = 0;
	  n2 = 0;
	  gcf = 0;
	  
	  if(denom > num)
	   {
		 n2 = denom;
		 n1 = num;
	   }else{
	    n1 = denom;
		 n2 = num;
		}
		
	  while(rem > 0)
	   {
		 rem = n2 - n1;
		 
		 // System.out.println("remainder: " + rem);
		 
		  if(rem < n2 && rem > n1)
		   {
			 n2 = rem;
			}else if(rem < 0){
			 n2 = 1;
			}else{
			 n2 = n1;
			 n1 = rem;
			}
		}
	 // System.out.println("GCF: " + n2);
	  
	    gcf = n2;
	  
		 point1 = num / gcf;
		 point2 = denom / gcf;
       
		 Fraction simplified = new Fraction(point1, point2);
	   
	  return(simplified);
	 }
	 
	public double value()
    {
     double decimal;

     decimal = (double)n/d;
      
     return(decimal);
    }
    
   public java.lang.String toString()
    {
     String str;
	  
	  if(d == 1)
	   {
		 str= (n + "");
		}else{
	    str = (n + "/" + d);
      }
		
     return(str);

    
    }
 
   public Fraction add(Fraction other)
	 {
	  int den1 = this.d;
	  int num1 = this.n;
	  int den2 = other.getDenominator();
	  int num2 = other.getNumerator();
	  int den3;
	  int num3; 
	 
	  if(den1 == den2)
	   {
	    num3 = num1 + num2;
		 den3 = den1;
		
	   }else{
	    num3 = (den2 * num1) +(den1 * num2);
		 den3 = den1 * den2;  
	   }
	  
	  Fraction F3 = new Fraction();
	  F3 = simplify(num3, den3);
	  
	  // System.out.print("Sum: " + F3.n);
	  // System.out.println("/" + F3.d);
	  return(F3);
    }
	 
   public Fraction sub(Fraction other)
    {
     int den1 = this.d;
	  int num1 = this.n;
	  int den2 = other.getDenominator();
	  int num2 = other.getNumerator();
	  int den3;
	  int num3; 
	 
	  if(den1 == den2)
	   {
	    num3 = num1 - num2;
		 den3 = den1;
		
	   }else{
	    num3 = (den2 * num1) - (den1 * num2);
		 den3 = den1 * den2;  
	   }
	   
		Fraction F4 = new Fraction();
	   F4 = simplify(num3, den3);
	  
     // System.out.print("Remainder: " + F4.n);
	  // System.out.println("/" + F4.d);
	   return(F4);
	 }
	 
   public Fraction mul(Fraction other)
    {
	  int den1 = this.d;
	  int num1 = this.n;
	  int den2 = other.getDenominator();
	  int num2 = other.getNumerator();
	  int den3;
	  int num3; 

     num3 = num1 * num2;
	  den3 = den1 * den2;
	  
	  Fraction F5 = new Fraction();
	  F5 = simplify(num3, den3);
	  
    // System.out.print("Product: " + F5.n);
	 // System.out.println("/" + F5.d);
	  return(F5);
	 }
	 
	public Fraction div(Fraction other)
	 {
	 int den1 = this.d;
	 int num1 = this.n;
	 int den2 = other.getDenominator();
	 int num2 = other.getNumerator();
	 int den3;
	 int num3; 

     num3 = num1 * den2;
	  den3 = den1 * num2;
	  
	  Fraction F6 = new Fraction();
	  F6 = simplify(num3, den3);
	  
	 // System.out.print("Quotient: " + F6.n);
	 // System.out.println("/" + F6.d);
	  return(F6);
	 }
   public boolean equals(Fraction other)
    {
	  int den1 = this.d;
	  int num1 = this.n;
	  int den2 = other.getDenominator();
	  int num2 = other.getNumerator();
	  int index;
	  boolean test;
	 
	  if(num1 == num2 && den1 == den2)
	   {
	    test = true;
		 index = 1;
	   }else{
	    test = false;
		 index = 0;
	   } 
	 
	 // System.out.println("Equals Test: " + index);
	 
	  return(test);
	 } 
}