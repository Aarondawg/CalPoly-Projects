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
		
		 if(denominator <= 0)
		  {
		  
		  	throw new IllegalArgumentException(warning);
			
		  }else{
	      n = numerator;
         d = denominator;
			
			simplify();
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

	private void simplify()
	 {
	 	int n1 = 0, n2 = 0;
	 	int index = 0;
	 	int rem = 1;
	 	int gcf = 0;
	  
	 	if(n < 0)
	  	 {
			n = n * -1;
			index = 1;
		 }
	  
	   if(n == 0)
	    {
			d = 1;
		 }
	 
	   if(d > n )
	    {
			n2 = d;
		 	n1 = n;
	    }else{
	    	n1 = d;
		 	n2 = n;
		 }
		
	  	while(rem > 0)
		 {
			rem = n2 - n1;
		 
		  //System.out.println("remainder: " + rem);
		 
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
	  //System.out.println("GCF: " + n2);
	 
		if(index == 1)
	    {
			n = n * -1;
			index = 0;
		 }
	 
	   gcf = n2;
	  
		n = n / gcf;
	   d = d / gcf;
      
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
	   int temp1 = 0, temp2 = 0;
	  
	   temp1 = n;
	   temp2 = d;
	 
	   if(den1 == den2)
	    {
	      n = num1 + num2;
		   d = den1;
		
	    }else{
	      n = (den2 * num1) +(den1 * num2);
		   d = den1 * den2;  
	    }
		
	   simplify();
	   Fraction F3 = new Fraction(n , d);
	  
	   n = temp1;
		d = temp2;
	  
	   //System.out.print("Sum: " + F3.n);
	   //System.out.println("/" + F3.d);
	  return(F3);
    }
	 
   public Fraction sub(Fraction other)
    {
      int den1 = this.d;
	   int num1 = this.n;
	   int den2 = other.getDenominator();
	   int num2 = other.getNumerator();
	   int temp1 = 0, temp2 = 0;
	 
	   temp1 = n;
	   temp2 = d;
	  
	   if(den1 == den2)
	    {
	      n = num1 - num2;
		   d = den1;
		
	    }else{
	      n = (den2 * num1) - (den1 * num2);
		   d = den1 * den2;  
	    }
	   
		simplify();
		Fraction F4 = new Fraction(n,d);
	  
	   n = temp1;
		d = temp2;
	  	  
      //System.out.print("Remainder: " + F4.n);
	   //System.out.println("/" + F4.d);
	  
	   return(F4);
	 }
	 
   public Fraction mul(Fraction other)
    {
	   int den1 = this.d;
	   int num1 = this.n;
	   int den2 = other.getDenominator();
	   int num2 = other.getNumerator();
	   int temp1 = 0, temp2 = 0;
	  
	   temp1 = n;
	   temp2 = d;

      n = num1 * num2;
	   d = den1 * den2;
	  
	   simplify();
	   Fraction F5 = new Fraction(n,d);
	  
	   n = temp1;
	   d = temp2;
	  
      //System.out.print("Product: " + F5.n);
	   //System.out.println("/" + F5.d);
	 
	  return(F5);
	 }
	 
	public Fraction div(Fraction other)
	 {
	   int den1 = this.d;
	   int num1 = this.n;
	   int den2 = other.getDenominator();
	   int num2 = other.getNumerator();
	   int temp1 = 0, temp2 = 0;
	 
	   temp1 = n;
	   temp2 = d; 

      n = num1 * den2;
	   d = den1 * num2;
	  
	   simplify();
	   Fraction F6 = new Fraction(n,d);
	  
	   n = temp1;
	   d = temp2;
	  
	  //System.out.print("Quotient: " + F6.n);
	  //System.out.println("/" + F6.d);
	  
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
	 
	   //System.out.println("Equals Test: " + index);
	 
	   return(test);
	 } 
}