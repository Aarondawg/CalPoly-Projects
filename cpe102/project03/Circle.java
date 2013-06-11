/**
 * A Circle object with a radius, specified color, and fill state.
 *
 * @author Garrett Milster   
 * @version Program 3              
 */

public class Circle implements Shape
{
   private double radius;
   private java.awt.Point position;
   private java.awt.Color color;
   private boolean filled;
 
   public Circle(double radius, java.awt.Point position, java.awt.Color color, boolean filled)
   {
      this.radius = radius;
      this.position = position;
      this.color = color;
      this.filled = filled;

   }
 
   public double getArea()
   {
      double area;

      area = this.radius * this.radius * Math.PI;
 
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

   public java.awt.Point getPosition()
   {
      return(position);
   }

   public void setPosition(java.awt.Point position)
   {
      this.position = position;
   }

   public void move(java.awt.Point delta)
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
       && this.color.equals(((Circle)obj).color)
       && this.filled == ((Circle)obj).filled;
   }

   /*public String toString()
   {
      return "Radius: " + radius +
       "\nPosition: " + position + 
       "\nColor: " + color +
       "\nFilled: " + filled + "\n";
   }*/
}