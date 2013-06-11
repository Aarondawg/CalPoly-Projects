/**
 * An iterator interface creating O(1) peformance for certain methods in LinkedList
 * 
 *
 * @author Garrett Milster
 * @version 6
 */

import java.util.Iterator;

public interface ListIterator<E> extends Iterator
{   
   public boolean hasPrevious();
   public E previous();
   public boolean hasNext();
   public E next();
   public void remove();
}