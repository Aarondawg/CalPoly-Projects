import java.awt.Color;
/**
 * An abstract class that contains methods used by all shapes.
 *
 * @author Garrett Milster   
 * @version Program 4       
 */

public abstract class Shape implements java.lang.Comparable<Shape>
{  
   private Color color;
   private boolean filled;
   
    /**
   * Constructs a new shape object and initializes a color and fill state.
   *
   *@param color The specified color of the shape.
   *@param filled The specified fill state of the shape.
   */
   public Shape(Color color, boolean filled)
   {
      this.color = color;
      this.filled = filled;
   }
   
   /**
   * Returns the area of the shape.
   *
   *@return The area of the shape.
   *
   */
   public abstract double getArea();
   /**
   * Returns the current position of the shape.
   *
   *@return Returns a Point object containing the x,y coordinates of the shape.
   *
   */
   public abstract java.awt.Point getPosition();

    /**
   * Changes the current position of the shape.
   *
   *@param position The new position for the shape.
   *
   */
   public abstract void setPosition(java.awt.Point position); 
   
   /**
   * Moves the shape a certain x and y distance.
   *
   *@param delta The variable containing the x and y amounts the shape needs to be moved.
   *
   */
   public abstract void move(java.awt.Point delta);
   
   /**
   * Returns the color of the shape.
   *
   *@return The color of the shape.
   *
   */
   public Color getColor()
   {
      return color;
   }

   /**
   * Changes the color of the shape.
   *
   *@param color The new color of the shape.
   *
   */
   public void setColor(Color color)
   {
      this.color = color;
   }
   
   /**
   * Returns whether or not the shape is filled.
   *
   *@return Whether or not the shape is filled.
   *
   */
   public boolean getFilled()
   {
      return filled;
   }
   
   /**
   * Sets the current state of the shape to either filled or empty.
   *
   *@param filled The new fill state of the shape.
   *
   */
   public void setFilled(boolean filled)
   {
      this.filled = filled;
   } 
   
   /**
   * Compares the names of two shapes, if they are equal then it compares their areas.
   *
   *@param shape The explicit variable shape to compare with.
   *@return Returns a positive number if the implicit is greater than the explicit,
   * a negative number it's the opposite, or 0 if they are the same.
   */
   public int compareTo(Shape shape)
   {
      Shape shape1 = this;
      String name1 = shape1.getClass().getName();
      String name2 = shape.getClass().getName();
      int index = 0;
      index = name1.compareTo(name2);
      if(index != 0)
      {
         return index;
      }else{
         if(shape1.getArea() > shape.getArea())
         {
            return 1;
         }else if(shape1.getArea() < shape.getArea()){
            return -1;
         }else{
            return 0;
         }
      }
   }
   /**
   * Sets the current state of the shape to either filled or empty.
   *
   *@param obj An object to compare to shape to see if they are of the same class.
   *@return Returns true or false depending if they are equal or not.
   */
   public boolean equals(Object obj)
   {
      if(obj != null && obj.getClass() == this.getClass()
      && this.color.equals(((Shape)obj).color) && this.filled == ((Shape)obj).filled){
         return true;
      }else{
         return false;
      }
   }
}
   