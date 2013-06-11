/**
 * A Canvas object which holds an array of shapes that can be accessed by the user.
 *
 * @author Garrett Milster   
 * @version Program 4           
 */

import java.util.ArrayList;
import java.awt.Point;
import java.awt.Color;

public class Canvas
{
      
   private ArrayList<Shape> canvas = new ArrayList();
   /**
   * Creates a blank canvas object.
   *
   *
   *
   */
   public Canvas()
   {
      
   }
   
   /**
   * Adds a shape to the canvas.
   *
   *@param shape The new shape to add to the end of the canvas.
   *
   */
   public void add(Shape shape)
   {
      canvas.add(shape);
   }   
   /**
   * Removes a specific shape from the list.
   *
   *@param index The specific shape in the arraylist to remove.
   *@return Returns a reference or null.
   */
   
   public Shape remove(int index)
   { 
      return canvas.remove(index);
   }
   
   /**
   * Returns a specified shape from the list.
   *
   *@param index The index number of the specific shape wanted.
   *@return Returns a reference to the specifed shape.
   */
   public Shape get(int index)
   {
      return canvas.get(index);
   }
   
   /**
   * Gets the current size of the arraylist.
   *
   *@return Returns the size of the arraylist.
   *
   */
   public int size()
   {
      return canvas.size();
   }
   
   /**
   * Gets all circles from the arraylist and puts them in their own arraylist.
   *
   *@return An arraylist of circles.
   *
   */
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
   
   /**
   * Gets all Rectangles from the arraylist and puts them in their own arraylist.
   *
   *@return An arraylist of rectangles.
   *
   */
   public ArrayList<Rectangle> getRectangles()
   {
      ArrayList<Rectangle> rectangle = new ArrayList();
           
      for(int i = 0; i < canvas.size(); i++)
      {
         if(canvas.get(i).getClass() == Rectangle.class)
         {
            rectangle.add((Rectangle)canvas.get(i));
         }
      }
      return rectangle;
   }
   
   /**
   * Gets all triangles from the arraylist and puts them in their own arraylist.
   *
   *@return An arraylist of triangles.
   *
   */
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
   
   /**
   * Gets all convex polygons from the arraylist and puts them in their own arraylist.
   *
   *@return An arraylist of convex polygons.
   *
   */
   public ArrayList<ConvexPolygon> getConvexPolygons()
   {
      ArrayList<ConvexPolygon> polygon = new ArrayList();
      
      for(int i = 0; i < canvas.size(); i++)
      {
         if(canvas.get(i).getClass() == ConvexPolygon.class)
         {
            polygon.add((ConvexPolygon)canvas.get(i));
         }
      }
      return polygon;
   }
   
   /* public ArrayList<Square> getSquares()
   {
      ArrayList<Square> square = new ArrayList();
      
      for(int i = 0; i < canvas.size(); i++)
      {
         if(canvas.get(i) instanceof Square)
         {
            square.add((Square)canvas.get(i));
         }
      }
      return square;
   } */
   
      /**
      * Gets all shapes of the same color and puts them in their own arraylist.
      *
      *@return An arraylist of shapes of the same color.
      *
      */
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
   
   /**
   * Gets the total area of all shapes on the canvas.
   *
   *@return The combined area of all shapes on the canvas.
   *
   */
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