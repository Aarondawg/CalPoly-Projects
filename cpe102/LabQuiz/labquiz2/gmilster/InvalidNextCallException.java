/**
 * @author Garrett Milster
 * @version Lab Quiz 3
 */

public class InvalidNextCallException extends RuntimeException
{
   public InvalidNextCallException()
   {
   }
   
   public InvalidNextCallException(String string)
   {
      super(string);
   }
}