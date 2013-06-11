/**
 * A Canvas object which holds an array of shapes that can be accessed by the user.
 *
 * @author Garrett Milster   
 * @version Program 3              
 */

import java.util.ArrayList;

public class Canvas
{
      
   private ArrayList<Shape> canvas = new ArrayList();
      
   public Canvas()
   {
      
   }
   
   public void add(Shape shape)
   {
      canvas.add(shape);
   }
   
   public Shape remove(int index)
   {
      return canvas.remove(index);
   }
   
   public Shape get(int index)
   {
      return canvas.get(index);
   }
   
   public int size()
   {
      return canvas.size();
   }
   
   public ArrayList<Circle> getCircles()
   {
      ArrayList<Circle> circle = new ArrayList();
      
      for(int i = 0; i < canvas.size(); i++)
      {
         if(canvas.get(i) instanceof Circle)
         {   
            
            circle.add((Circle)canvas.get(i));
         }
      }
      return circle;
   } 
   
   public ArrayList<Rectangle> getRectangles()
   {
      ArrayList<Rectangle> rectangle = new ArrayList();
      
      for(int i = 0; i < canvas.size(); i++)
      {
         if(canvas.get(i) instanceof Rectangle)
         {
            rectangle.add((Rectangle)canvas.get(i));
            
            
         }
      }
      return rectangle;
   }
   
   public ArrayList<Triangle> getTriangles()
   {
      ArrayList<Triangle> triangle = new ArrayList();
      
      for(int i = 0; i < canvas.size(); i++)
      {
         if(canvas.get(i) instanceof Triangle)
         {
            triangle.add((Triangle)canvas.get(i));
         }
      }
      return triangle;
   }
   
   public ArrayList<ConvexPolygon> getConvexPolygons()
   {
      ArrayList<ConvexPolygon> polygon = new ArrayList();
      
      for(int i = 0; i < canvas.size(); i++)
      {
         if(canvas.get(i) instanceof ConvexPolygon)
         {
            polygon.add((ConvexPolygon)canvas.get(i));
         }
      }
      return polygon;
   }
   
   public ArrayList<Shape> getShapesByColor(java.awt.Color color)
   {
      ArrayList<Shape> c = new ArrayList();
      for(int i = 0; i < canvas.size(); i++)
      {
            if(canvas.get(i).getColor() ==  color)
            {
               c.add(canvas.get(i));
            }
      }
      return c;
   }
   
   public double getAreaOfAllShapes()
   {
      double total = 0;
      for(int i = 0; i < canvas.size(); i++)
      {
         total = total + canvas.get(i).getArea();
      }   
      
      return total;
   }
   
}