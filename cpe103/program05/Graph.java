import java.util.ArrayList;
// Garrett Milster
public class Graph
{

   private int[][] maze;
   private int[][] adj;
   private int numVertices;

   Vert[] vert;

   private int[] parent;
   
   private class Vert
   {
      int row;
      int col;
      int wall;
   }
   public Graph(int[][] maze, int r, int c)
   {
      this.maze = maze;
      int count = 0;
      numVertices = r*c;
      vert = new Vert[numVertices];

      for (int i=0; i<r; i++) 
      {
         for (int j=0; j<c; j++) 
         {
            vert[count] = new Vert();
            vert[count].row = i;
            vert[count].col = j;
            vert[count].wall = maze[i][j];
            count ++;
         }
      }
      adj = new int[numVertices][numVertices];
      for (int i=0; i< vert.length;i++)
      {
         if(vert[i].wall != 0)
         {
            for(int j = 0; j < numVertices; j++)
            {
               adj[i][j] = 0;
            }
         }
         else
         {
            if(vert[i +1].wall == 0)
               adj[i][i+1] = 1;
            if(vert[i -1].wall == 0)
               adj[i][i-1] = 1;
               
            int row = i/c;
            int col = i - row*c;
            int index = ((row -1) * c) + col;
            if(vert[index].wall ==0)
               adj[i][index] = 1;
            index = ((row +1) * c) + col;
            if(vert[index].wall ==0)
               adj[i][index] = 1;
         }
      }
      
      /* for (int j=0; j<numVertices; j++) 
      {
         System.out.print(j + " ");
      }
      for (int i=0; i<numVertices; i++) 
      {
         System.out.print(i + "---");
         for (int j=0; j<numVertices; j++) 
         {
            System.out.print(adj[i][j] + " ");
         }
         System.out.println("");
      } */
   }
   
   public int vertices()
   {
      return numVertices;
   }
   
   public Vert[] getVert()
   {
      return vert;
   }
   public static class Error extends RuntimeException
   {
      public Error(String message)
      {
         super(message);
      }
   }

   public int[][] shortestPath(int start, int stop)
   {
      parent = new int[numVertices];
      for(int i = 0; i < parent.length; i++)
         parent[i] = -2;
      ArrayList<Integer> list = new ArrayList<Integer>(1);
      list.add(start);
      parent[start] = -1;
      int root = start;

      while(!list.isEmpty())
      {
         root = list.remove(0);
         for (int j = 0; j < numVertices; j++)
         {         
            if(parent[j] == -2 && adj[root][j] == 1)
            {
               list.add(j);
               parent[j] = root;
            }
            
         }
      }
      
      if (parent[stop]== -2)
         return null;
         
      ArrayList<Integer> path = new ArrayList<Integer>();
      int k = stop;
      while(k != -1)
      {
         path.add(k);
         k = parent[k];
      }
      int[][] temp = new int[path.size()][2];
      for(int i = 0; i < path.size(); i++)
      {
         temp[i][0] = vert[path.get(i)].row;
         temp[i][1] = vert[path.get(i)].col;
      }
      return temp;
   }

}
