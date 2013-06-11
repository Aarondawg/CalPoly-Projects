 import java.awt.Color;
import java.awt.Point;

/**
 * A Convex Polygon object with an array of points, a specific color, and fill state.
 *
 * @author Garrett Milster   
 * @version Program 4            
 */

public class ConvexPolygon extends Shape
{
   private Point[] vertices;

   
   public ConvexPolygon(Point[] vertices, Color color, boolean filled)
   {
      super(color, filled);
      assert vertices[0] != vertices[vertices.length];
      
      Point[] p1 = new Point[vertices.length];

      for(int i = 0; i < p1.length; i++)
      {
         p1[i] = new Point(vertices[i]);
      }
      
      this.vertices = p1;
      //System.out.println("Length: " + this.vertices.length);
   }
   
   public double getArea()
   {
      double a1, a2, area;
      int i;
      a1 = 0;
      a2 = 0;
   
      for(i = 0; i < vertices.length; i++)
      {  
         //System.out.println("A" + i + ": " + a1);
         //System.out.println("B" + i + ": " + a2);
         
         if(i == (vertices.length - 1))
         {
            a1 = a1 + vertices[i].x * vertices[0].y;
            a2 = a2 + vertices[i].y * vertices[0].x;
            
            //System.out.println("a1: " + a1);
            //System.out.println("a2: " + a2);
            
         }else{
            a1 = a1 + vertices[i].x * vertices[i + 1].y;
            a2 = a2 + vertices[i].y * vertices[i + 1].x;
         }
         
         //System.out.println("C" + i + ": " + a1);
         //System.out.println("D" + i + ": " + a2);

      }
      //System.out.println("A1: " + a1);
      //System.out.println("A2: " + a2);
      
      area = (a1 - a2) / 2;
      
      if(area < 0)
      {
         area = area * -1;
      }
      //System.out.println("Area: " + area);
      return(area);
   }

   public Point getPosition()
   {
      Point p = new Point(vertices[0]);
      return(p);
   }
   
   public void setPosition(Point position)
   {
      int x,y;
      Point p = new Point(position);
      
      x = p.x - vertices[0].x;
      y = p.y - vertices[0].y;
      
      vertices[0] = p;
      for(int i = 1; i < vertices.length; i++)
      {
         vertices[i].x = vertices[i].x + x;
         vertices[i].y = vertices[i].y + y;
      }
   }
   
   public void move(Point delta)
   {
      int i;
      
      for(i = 0; i < vertices.length; i++)
      {
         vertices[i].x = vertices[i].x + delta.x;
         vertices[i].y = vertices[i].y + delta.y;
      }
   }

   public Point getVertex(int index)
   {
      Point p = new Point(vertices[index]);
      return(p);
   }
   
   public void setVertex(int index, Point vertex)
   {
      Point p = new Point(vertex);
      vertices[index] = p;
   }

   public boolean equals(Object obj)
   {
      boolean state1 = false;
      boolean state2 = true;
      
      if(obj != null && obj.getClass() == this.getClass() 
      && this.getColor().equals(((ConvexPolygon)obj).getColor()) 
      && this.getFilled() == ((ConvexPolygon)obj).getFilled())
      {
         state1 = true;
      }else{
         return false;
      }
      
      for(int i = 0; i < vertices.length; i++)
      {
         if(!(this.vertices[i].equals(((ConvexPolygon)obj).vertices[i])))
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
   }
}