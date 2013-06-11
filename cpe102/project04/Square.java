import java.awt.Point;

/**
 * A Square object with a size, specified color, and fill state.
 *
 * @author Garrett Milster   
 * @version Program 4            
 */

public class Square extends Rectangle
{
   /**
   * Constructs a Square object with a size, initial position, color, and fill state.
   *
   *@param size The size of the shape.
   *@param position The initial point of the square.
   *@param color The color of the shape.
   *@param filled The fill state of the shape.
   */
   public Square(int size, Point position, java.awt.Color color, boolean filled)
   {
      super(size, size, position, color, filled);
   }   

   /**
   * Sets the width of the square, which in turn sets the height to the same amount.
   *
   *@param width The new width of the square.
   *
   */
   public void setWidth(int width)
   {
      int width1 = getVertex(1).x - getVertex(0).x;
      int diff = width1 - width;
      Point p = new Point(getVertex(1));
      Point p2 = new Point(getVertex(2));
      Point p3 = new Point(getVertex(3));
      
         p.x = p.x - diff;
         p2.x = p2.x - diff;
         p2.y = p2.y - diff;
         p3.y = p3.y - diff;
         setVertex(1, p);
         setVertex(2, p2);
         setVertex(3, p3);
   
   }
   
   /**
   * Sets the height of the square, which in turn sets the width to the same amount.
   *
   *@param height The new height of the square.
   *
   */
   public void setHeight(int height)
   {
      int height1 = getVertex(3).y - getVertex(0).y;
      int diff = height1 - height;
      Point p = new Point(getVertex(1));
      Point p2 = new Point(getVertex(2));
      Point p3 = new Point(getVertex(3));
      
         p.x = p.x - diff;
         p2.x = p2.x - diff;
         p2.y = p2.y - diff;
         p3.y = p3.y - diff;
         setVertex(1, p);
         setVertex(2, p2);
         setVertex(3, p3);

   }
   /**
   * Returns the size of the square.
   *
   *@return Returns the size of the square.
   *
   */
   public int getSize()
   {
      return this.getWidth();
   }
   
   /**
   * Sets the size of the square.
   *
   *@param size The new size of the square.
   *
   */
   public void setSize(int size)
   {
      int size1 = getVertex(3).y - getVertex(0).y;
      int diff = size1 - size;
      Point p = new Point(getVertex(1));
      Point p2 = new Point(getVertex(2));
      Point p3 = new Point(getVertex(3));
      
         p.x = p.x - diff;
         p2.x = p2.x - diff;
         p2.y = p2.y - diff;
         p3.y = p3.y - diff;
         setVertex(1, p);
         setVertex(2, p2);
         setVertex(3, p3);
 
   }
  
  /* public boolean equals(Object obj)
   {
      boolean state1 = false;
      boolean state2 = true;
      
      if(obj != null && obj instanceof Square 
      && this.getColor().equals(((Square)obj).getColor()) 
      && this.getFilled() == ((Square)obj).getFilled())
      {
         state1 = true;
      }else{
         return false;
      }
      
      for(int i = 0; i < 4; i++)
      {
         if(!(this.getVertex(i).equals(((Square)obj).getVertex(i))))
         {
            state2 = false;
         }
      }
      
      if(state1 == true && state2 == true)
      {
         return true;
      }else{
         return false;
      }
   } */
   
}