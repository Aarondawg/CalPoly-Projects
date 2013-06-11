import java.awt.geom.Point2D;

/**
*@author Garrett Milster
*@version Lab Quiz 2
*/
public class MyPoint2D extends Point2D implements java.lang.Comparable<Point2D>
{
   private double x;
   private double y;
   
   public MyPoint2D()
   {
      x = 0;
      y = 0;
   }
   
   public MyPoint2D(double x, double y)
   {
      this.x = x;
      this.y = y;
   }
   
   public double getX()
   {
      return x;
   }
   
   public double getY()
   {
      return y;
   }
   
   public boolean equals(Object obj)
   {
      if(obj != null && obj instanceof Point2D)
      {
         obj = (Point2D)obj;
      }else{
         return false;
      }
      
      if((double)((Point2D)obj).getX() == this.x
      && (double)((Point2D)obj).getY() == this.y)
      {
         return true;
      }else{
         return false;
      }
   }
   
   public String toString()
   {
      String s;
      s = ("(" + x + ", " + y + ")");
      
      return s;
   }
   
   public void setLocation(double x, double y)
   {
      this.x = x;
      this.y = y;
   }
   
   public int compareTo(Point2D point)
   {
      if(x == (double)point.getX())
      {
         return (int)(y - (double)point.getY());
      }else{
         return (int)(x - (double)point.getX());
      }
   }
}           