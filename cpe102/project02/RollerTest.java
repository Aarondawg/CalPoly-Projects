public class RollerTest
{
	public static void main(String[] args)
	{
		int i;
		int[] valueTest = new int[2];
		
		Roller r1 = new Roller(2);
		valueTest = r1.roll();
		
	/*	for(i = 0; i < valueTest.length; i++)
		{
			
			System.out.println(valueTest[i]);
			System.out.println("Hello");
		}
	 */
	   for(int a: valueTest)
			System.out.println(a);
			
	}
}