/**
 * A Shape interface which contains methods to set or get multiple states of a shape.
 *
 * @author Garrett Milster   
 * @version Program 3              
 */

public interface Shape
{   
   /**
   * Returns the area of the shape.
   *
   *@return The area of the shape.
   *
   */
   public double getArea(); 

   /**
   * Returns the color of the shape.
   *
   *@return The color of the shape.
   *
   */
   public java.awt.Color getColor(); 

   /**
   * Changes the color of the shape.
   *
   *@param java.awt.Color The new color of the shape.
   *
   */
   public void setColor(java.awt.Color color); 
   
   /**
   * Returns whether or not the shape is filled.
   *
   *@return Whether or not the shape is filled.
   *
   */
   public boolean getFilled(); 
   
   /**
   * Sets the current state of the shape to either filled or empty.
   *
   *@param boolean The new fill state of the shape.
   *
   */
   public void setFilled(boolean filled); 

   /**
   * Returns the current position of the shape.
   *
   *@return Returns a Point object containing the x,y coordinates of the shape.
   *
   */
   public java.awt.Point getPosition();

    /**
   * Changes the current position of the shape.
   *
   *@param java.awt.Point The new position for the shape.
   *
   */
   public void setPosition(java.awt.Point position); 
   
   /**
   * Moves the shape a certain x and y distance.
   *
   *@param java.awt.Point The variable containing the x and y amounts the shape needs to be moved.
   *
   */
   public void move(java.awt.Point delta); 
}
   