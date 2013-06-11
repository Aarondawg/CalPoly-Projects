import java.awt.Point;
/**
 * A Triangle object with 3 vertices, specifed color, and fill state.
 *
 * @author Garrett Milster   
 * @version Program 3              
 */

public class Triangle implements Shape
{
   private Point a, b, c;
   private java.awt.Color color;
   private boolean filled;
   
   public Triangle(Point a, Point b, Point c, java.awt.Color color, boolean filled)
   {
      this.a = a;
      this.b = b;
      this.c = c;
      this.color = color;
      this.filled = filled;
   }
   
   public double getArea()
   {
      double area;
      double ab, ac, bc;
      double s;
      
      ab = Math.pow((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y), .5);
      ac = Math.pow((a.x - c.x) * (a.x - c.x) + (a.y - c.y) * (a.y - c.y), .5);
      bc = Math.pow((b.x - c.x) * (b.x - c.x) + (b.y - c.y) * (b.y - c.y), .5);
      s = (ab + ac + bc) / 2;
      
      area = Math.pow(s * (s - ab) * (s - ac) * (s - bc), .5);
      
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
      return(a);
   }
   
   public void setPosition(Point position)
   {
      int x, y;
      
      x = position.x - a.x;
      y = position.y - a.y;
      
      this.a = position;
      this.b = new Point(b.x + x, b.y + y);
      this.c = new Point(c.x + x, c.y + y);
   }
   
   public void move(Point delta)
   {
      a.x = a.x + delta.x;
      a.y = a.y + delta.y;
      b.x = b.x + delta.x;
      b.y = b.y + delta.y;
      c.x = c.x + delta.x;
      c.y = c.y + delta.y;
   }

   public Point getVertexA()
   {
      //System.out.println("point a: " + a);
      return(a);
   }
    public void setVertexA(Point point)
   {
      this.a = point;
   }

   public Point getVertexB()
   {
      //ystem.out.println("point b:  " + b);
      return(b);
   }

   public void setVertexB(Point point)
   {
      
      this.b = point;
   }
   
   public Point getVertexC() 
   {
      //System.out.println("point c: " + c);
      return(c);
   }

   public void setVertexC(Point point)
   {
      this.c = point;
   }
   
   public boolean equals(Object obj)
   {
      return obj != null
       && (obj instanceof Triangle)
       && this.a.equals(((Triangle)obj).a)
       && this.b.equals(((Triangle)obj).b)
       && this.c.equals(((Triangle)obj).c)
       && this.color.equals(((Triangle)obj).color)
       && this.filled == ((Triangle)obj).filled;
   }
}