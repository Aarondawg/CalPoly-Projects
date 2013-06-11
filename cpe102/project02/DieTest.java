public class DieTest
{
	public static void main(String[] args)
	{
		Die d1 = new Die();
		Die d2 = new Die(7);
		Die d3 = new Die(7987);
		
		int v1, v2, v3;
		int s1, s2, s3;
		
		v1 = d1.value();
		v2 = d2.value();
		v3 = d3.value();
		
		s1 = d1.sides();
		s2 = d2.sides();
		s3 = d3.sides();
		
		System.out.println(" the first three values are " + v1 +", " + v2 + ", " + v3);
		System.out.println(" the first three sides are " + s1 +", " + s2 + ", " + s3);

	
	
	}
}