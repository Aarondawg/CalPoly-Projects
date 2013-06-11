/**
 * A Convex Polygon object with an array of points, a specific color, and fill state.
 *
 * @author Garrett Milster   
 * @version Program 3              
 */

public class ConvexPolygon implements Shape
{
   private java.awt.Point[] vertices;
   private java.awt.Color color;
   private boolean filled;
   
   public ConvexPolygon(java.awt.Point[] vertices, java.awt.Color color, boolean filled)
   {
      assert vertices[0] != vertices[vertices.length];
      
   
      this.vertices = vertices;
      this.color = color;
      this.filled = filled;
   }
   
   public double getArea()
   {
      double a1, a2, area;
      int i;
      a1 = 0;
      a2 = 0;
      
      //System.out.println("LENGTH: " + vertices.length);
      for(i = 0; i < vertices.length; i++)
      {  
         //System.out.println("A" + i + ": " + a1);
         //System.out.println("B" + i + ": " + a2);
         
         if(i == (vertices.length - 1))
         {
            a1 = a1 + vertices[i].x * vertices[0].x;
            a2 = a2 + vertices[i].y * vertices[0].y;
         }else{
            a1 = a1 + vertices[i].x * vertices[i + 1].y;
            a2 = a2 + vertices[i].y * vertices[i + 1].x;
         }
         
         //System.out.println("C" + i + ": " + a1);
         //System.out.println("D" + i + ": " + a2);
      }
      
      area = (a1 - a2) / 2;
      
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
      return(vertices[0]);
   }
   
   public void setPosition(java.awt.Point position)
   {
      int x,y;
      
      x = position.x - vertices[0].x;
      y = position.y - vertices[0].y;
      
      vertices[0] = position;
      for(int i = 1; i < vertices.length; i++)
      {
         vertices[i].x = vertices[i].x + x;
         vertices[i].y = vertices[i].y + y;
      }
   }
   
   public void move(java.awt.Point delta)
   {
      int i;
      
      for(i = 0; i < vertices.length; i++)
      {
         vertices[i].x = vertices[i].x + delta.x;
         vertices[i].y = vertices[i].y + delta.y;
      }
   }

   public java.awt.Point getVertex(int index)
   {
      return(vertices[index]);
   }
   
   public void setVertex(int index, java.awt.Point vertex)
   {
      vertices[index] = vertex;
   }

   public boolean equals(Object obj)
   {
      boolean state1 = false;
      boolean state2 = true;
      
      if(obj != null && obj instanceof ConvexPolygon 
      && this.color.equals(((ConvexPolygon)obj).color) 
      && this.filled == ((ConvexPolygon)obj).filled)
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