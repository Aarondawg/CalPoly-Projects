import java.awt.Point;

/**
 * A Rectangle object with a width, height, specified color, and fill state.
 *
 * @author Garrett Milster   
 * @version Program 3              
 */

public class Rectangle implements Shape
{
   private int width, height;
   private Point position;
   private java.awt.Color color;
   private boolean filled;
   
   public Rectangle(int width, int height, Point position, java.awt.Color color, boolean filled)
   {
      this.width = width;
      this.height = height;
      this.position = position;
      this.color = color;
      this.filled = filled;
   }
   
   public double getArea()
   {
      double area;
      
      area = width * height;
      
      return(area);
   }

   public java.awt.Color getColor()
   {
      return(this.color);
   }
   
   public void setColor(java.awt.Color color)
   {
      this.color = color;   
   }
   
   public boolean getFilled()
   {
      return(filled);
   }
   
   public void setFilled(boolean filled)
   {
      this.filled = filled;
   }
   
   public Point getPosition()
   {
      return(position);
   }
   
   public void setPosition(Point position)
   {
      this.position = position;
   }
   
   public void move(Point delta)
   {
      position.x = position.x + delta.x;
      position.y = position.y + delta.y;
   }
   
   public int getWidth()
   {
      return(this.width);
   }
   
   public void setWidth(int width)
   {
      this.width = width;
   }
   
   public int getHeight()
   {
      return(this.height);
   }
   
   public void setHeight(int height)
   {
      this.height = height;
   }
   
   public boolean equals(Object obj)
   {
      return obj != null
       && (obj instanceof Rectangle)
       && this.width == ((Rectangle)obj).width
       && this.height == ((Rectangle)obj).height
       && this.position.equals(((Rectangle)obj).position)
       && this.color.equals(((Rectangle)obj).color)
       && this.filled == ((Rectangle)obj).filled;
   }
   
}