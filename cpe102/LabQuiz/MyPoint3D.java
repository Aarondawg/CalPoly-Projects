import java.awt.geom.Point2D;

/**
*@author Garrett Milster
*@version Lab Quiz 2
*/

public class MyPoint3D implements Comparable<MyPoint3D>
{
   private MyPoint2D point = new MyPoint2D();
   private double z;
   
   public MyPoint3D()
   {
      z = 0;
   }
   
   public MyPoint3D(double x, double y, double z)
   {
      this.z = z;
      point.setLocation(x,y);
   }
   
   public double getX()
   {
      return point.getX();
   }
   
   public double getY()
   {
      return point.getY();
   }
   
   public double getZ()
   {
      return z;
   }
   
   public void setLocation(double x, double y, double z)
   {
      this.z = z;
      point.setLocation(x,y);
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
      && (double)((Point2D)obj).getY() == this.y
      && (double)((Point2D)obj).getz() == this.z)
      {
         return true;
      }else{
         return false;
      }
   }
   
   public String toString()
   {
      String s;
      s = ("(" + x + ", " + y + ", " + z + ")");
      
      return s;
   }
   
   public int compareTo(Point2D point)
   {
      if(x == (double)point.getX())
      {
         return ((int)y - point.getY());
      }else{
         return ((int)x - point.getX());
      }
   }
}