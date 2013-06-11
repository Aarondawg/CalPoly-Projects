import java.awt.Point;
import java.awt.Color;
/**
 * A Triangle object with 3 vertices, specifed color, and fill state.
 *
 * @author Garrett Milster   
 * @version Program 4            
 */

public class Triangle extends ConvexPolygon
{
   public Triangle(Point a, Point b, Point c, Color color, boolean filled)
   {     
      super(new Point[] {new Point(a), new Point(b), new Point(c)}, color, filled);
   }
   
   public Point getVertexA()
   {
      //System.out.println("point a: " + a);
      Point p = new Point(getVertex(0));
      return(p);
   }
    public void setVertexA(Point point)
   {
      Point p = new Point(point);
      setVertex(0, p);
   }

   public Point getVertexB()
   {
      //ystem.out.println("point b:  " + b);
      Point p = new Point(getVertex(1));
      return(p);
   }

   public void setVertexB(Point point)
   {
      Point p = new Point(point);
      setVertex(1, p);

   }
   
   public Point getVertexC() 
   {
      //System.out.println("point c: " + c);
      Point p = new Point(getVertex(2));
      return(p);
   }

   public void setVertexC(Point point)
   {
      Point p = new Point(point);
      setVertex(2, p);

   }
   
   /* public boolean equals(Object obj)
   {
      
      return obj != null
       && (obj instanceof Triangle)
       && this.getVertex(0).equals(((Triangle)obj).getVertex(0))
       && this.getVertex(1).equals(((Triangle)obj).getVertex(1))
       && this.getVertex(2).equals(((Triangle)obj).getVertex(2))
       && this.getColor().equals(((Triangle)obj).getColor())
       && this.getFilled() == ((Triangle)obj).getFilled();
       
   } */
}