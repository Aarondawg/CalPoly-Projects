public class FractionTest
{

 public static void main(String[] args)
  {
  int num, denom;
  double decimal;
  String name;
  Fraction F1 = new Fraction(2, 4);
  Fraction F2 = new Fraction(4, 6);
 // Fraction F4 = new Fraction();
  
 num = F1.getNumerator();
 denom = F1.getDenominator();
 
  System.out.print("Fraction: " + num); 
  System.out.println("/" + denom);
 
 decimal = F1.value();
  System.out.println("Value: " + decimal);
  
 name = F1.toString();
  System.out.println("Name: " + name);
	
 F1.add(F2);
 F1.sub(F2);
 F1.mul(F2);
 F1.div(F2);
 
 F1.equals(F2);
  }
}