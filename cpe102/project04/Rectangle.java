import java.awt.Point;

/**
 * A Rectangle object with a width, height, specified color, and fill state.
 *
 * @author Garrett Milster   
 * @version Program 4           
 */

public class Rectangle extends ConvexPolygon
{
   
   public Rectangle(int width, int height, Point position, java.awt.Color color, boolean filled)
   {
      super(new Point[] {new Point(position), new Point(position.x + width, position.y),
      new Point(position.x + width, position.y + height),
      new Point(position.x, position.y + height)}, color, filled);
      
      //System.out.println("Initial Width: " + width);
      //System.out.println("Initial Height: " + height);
   }   
   public int getWidth()
   {
      //System.out.println("Width1: " + getVertex(1).x);
      //System.out.println("Width2: " + getVertex(0).x);
      return(getVertex(1).x - getVertex(0).x);
   }
   
   public void setWidth(int width)
   {
      int width1 = this.getWidth();
      int diff = width1 - width;
      Point p = new Point(getVertex(1));
      Point p2 = new Point(getVertex(2));
      
         p.x = p.x - diff;
         p2.x = p2.x - diff;
         setVertex(1, p);
         setVertex(2, p2);
      
      
   }
   
   public int getHeight()
   {
      return(getVertex(3).y - getVertex(0).y);
   }
   
   public void setHeight(int height)
   {
      int height1 = this.getHeight();
      int diff = height1 - height;
      Point p = new Point(getVertex(2));
      Point p2 = new Point(getVertex(3));
      
         p.y = p.y - diff;
         p2.y = p2.y - diff;
         setVertex(2, p);
         setVertex(3, p2);
   }
   
   /*public boolean equals(Object obj)
   {
      boolean state1 = false;
      boolean state2 = true;
      
      if(obj != null && obj instanceof Rectangle 
      && this.getColor().equals(((Rectangle)obj).getColor()) 
      && this.getFilled() == ((Rectangle)obj).getFilled())
      {
         state1 = true;
      }else{
         return false;
      }
      
      for(int i = 0; i < 4; i++)
      {
         if(!(this.getVertex(i).equals(((Rectangle)obj).getVertex(i))))
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