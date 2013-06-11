import java.awt.Point;
import java.awt.Color;

/**
 * A Circle object with a radius, specified color, and fill state.
 *
 * @author Garrett Milster   
 * @version Program 4           
 */

public class Circle extends Shape
{
   private double radius;
   private Point position;

   public Circle(double radius, Point position, Color color, boolean filled)
   {
      super(color, filled);
      this.radius = radius;
      Point p = new Point(position);
      this.position = p;
   }
 
   public double getArea()
   {
      double area;

      area = this.radius * this.radius * Math.PI;
 
      return(area);
   }

   public Point getPosition()
   {
      Point p = new Point(position);
      return(p);
   }

   public void setPosition(Point position)
   {
      Point p = new Point(position);
      this.position = p;
   }

   public void move(Point delta)
   {
      position.x = position.x + delta.x;
      position.y = position.y + delta.y;
   }

   public double getRadius()
   {
      return(this.radius);
   }

   public void setRadius(double radius)
   {
      this.radius = radius;
   }

   public boolean equals(Object obj)
   {
      return obj != null
       && (obj instanceof Circle)
       && this.radius == ((Circle)obj).radius
       && this.position.equals(((Circle)obj).position)
       && this.getColor().equals(((Circle)obj).getColor())
       && this.getFilled() == ((Circle)obj).getFilled();
   }

   /*public String toString()
   {
      return "Radius: " + radius +
       "\nPosition: " + position + 
       "\nColor: " + color +
       "\nFilled: " + filled + "\n";
   }*/
}