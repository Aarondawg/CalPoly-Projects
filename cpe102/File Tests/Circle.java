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

   public boolean equals(Object circle)
   {
      if(circle == null)
      {
         return false;
      }

      if(this.getClass() != circle.getClass())
      {
         return false;
      }

      if(this.radius != ((Circle)circle).radius)
      {
         return false;
      }

      if(this.position != ((Circle)circle).position)
      {
         return false;
      }

      if(this.color != ((Circle)circle).color)
      {
         return false;
      }

      if(this.filled != ((Circle)circle).filled)
      {
         return false;
      }

      return true;
   }

}