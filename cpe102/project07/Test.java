import java.util.Comparator;

public class Test implements Comparator<E> 
{
   public static void main(String[] args)
   {
      LinkedList list = new LinkedList();
      
      list.add(4.0);
      list.add(2.0);
      list.add(3.0);
      list.add(4.0);
      list.add(5.0);
      list.add(6.0);
      
        
   }
	
	public double compare(E n1, E n2)
	{
		return n1 - n2;
	}
	
	public boolean equals(double obj)
	{
		if(obj == 1)
		{
			return true;
		}else{
			return false;
		}
	}
}