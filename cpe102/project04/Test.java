import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Test
{
   public static void main(String[] args)
   {
     Color c = Color.black;
     Point[] points = new Point[4];
     boolean filled = false;
     int i = 0;
     points[0]=new Point(0,0);
     points[1]=new Point(4,0);
     points[2]=new Point(4,4);
     points[3]=new Point(0,4);
     ConvexPolygon cp = new ConvexPolygon(points,c.black, filled);
     ConvexPolygon cp2 = new ConvexPolygon(points,c.white, filled);
     
     System.out.println("CP Area: " + cp.getArea());
     System.out.println("CP Color: " + cp.getColor());
     System.out.println("CP Filled: " + cp.getFilled()); 
     System.out.println("CP Equals: " + cp.equals(cp2)); 
     System.out.println("");
     
     Rectangle r1 = new Rectangle(2,2,points[0], c.black, filled);
     Rectangle r2 = new Rectangle(2,2,points[0], c.black, filled);
     
     System.out.println("Is a rectangle greater than a polygon? " + cp.compareTo(r1));
     System.out.println("");
     
     
     System.out.println("Rect Area: " + r1.getArea());
     System.out.println("Rect Color: " + r1.getColor());
     System.out.println("Rect Width: " + r1.getWidth());
     System.out.println("Rect Filled: " + r1.getFilled()); 
     System.out.println("Rect Equals: " + r1.equals(r2));
     
     Triangle t1 = new Triangle(points[0], points[1], points[3], c.black, filled);
     Triangle t2 = new Triangle(points[0], points[1], points[3], c.black, filled);
     
     System.out.println("Tri Area: " + t1.getArea());
     System.out.println("Tri Color: " + t1.getColor());
     System.out.println("Tri Filled: " + t1.getFilled()); 
     System.out.println("Tri Equals: " + t1.equals(t2));
     
     Circle c1 = new Circle(1, points[0], c.black, filled);
     Circle c2 = new Circle(1, points[0], c.black, filled);
    
     System.out.println("");
     System.out.println("Is a Circle greater than a polygon? " + cp.compareTo(c1));
     System.out.println("");
     
     System.out.println("Circle Area: " + c1.getArea());
     System.out.println("Cicle Color: " + c1.getColor());
     System.out.println("Circle Filled: " + c1.getFilled()); 
     System.out.println("Circle Equals: " + c1.equals(c2));
     
     System.out.println("");
     System.out.println("Is a Circle greater than a Circle? " + c1.compareTo(c2));
     System.out.println("");
     
     Square s1 = new Square(4, points[0], c.white, filled);
     Square s2 = new Square(4, points[0], c.black, filled);
     
     System.out.println("Square Area: " + s1.getArea());
     System.out.println("Square Color: " + s1.getColor());
     System.out.println("Square Filled: " + s1.getFilled()); 
     System.out.println("Square Equals: " + s1.equals(s2));
     
     Canvas canvas = new Canvas();
     canvas.add(cp);
     canvas.add(cp2);
     canvas.add(r1);
     canvas.add(r2);
     canvas.add(r2);
     canvas.add(t1);
     canvas.add(t2);
     canvas.add(t1);
     canvas.add(t2);
     canvas.add(s1);
     canvas.add(s2);
     canvas.add(s2);
     canvas.add(c1);
     canvas.add(c2);
     
     
      ArrayList<ConvexPolygon> polygon = new ArrayList();
        polygon = canvas.getConvexPolygons();
        System.out.println("Number of ConvexPolygons: " + polygon.size());
      
       ArrayList<Rectangle> rectangle = new ArrayList();
         rectangle = canvas.getRectangles();
         System.out.println("Number of Rectangles: " + rectangle.size());
         
          ArrayList<Triangle> triangle = new ArrayList();
            triangle = canvas.getTriangles();
            System.out.println("Number of Triangles: " + triangle.size());
            
             ArrayList<Circle> circle = new ArrayList();
               circle = canvas.getCircles();
               System.out.println("Number of Circles: " + circle.size());
               
               // ArrayList<Square> square = new ArrayList();
                 // square = canvas.getSquares();
                  //System.out.println("Number of Squares: " + square.size());
     }
}