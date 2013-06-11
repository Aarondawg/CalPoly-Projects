import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


// uses GraphStart class, simple dfs on graph to classify edges


class DfsClassEdge   {
	static final int MAXV = 100;
	static boolean processed[] = new boolean[MAXV];
	static boolean discovered[] = new boolean[MAXV];
	static int parent[] = new int[MAXV];
	static int entry_time[] = new int[MAXV];
	static int exit_time[] = new int[MAXV];
	
	static void process_vertex_early(int v)	{
		timer++;
		entry_time[v] = timer;
		System.out.printf("entered vertex %d at time %d\n",v, entry_time[v]);
	}
	
	static void process_vertex_late(int v)	{
		timer++;
		exit_time[v] = timer;
		System.out.printf("exit vertex %d at time %d\n",v, exit_time[v]);
	}
	
	static final int TREE = 1, BACK = 2, FORWARD = 3, CROSS = 4;
	static int timer = 0;
	
	static int edge_classification(int x, int y)	{
		if (parent[y] == x) return(TREE);
		if (discovered[y] && !processed[y]) return(BACK);
		if (processed[y] && (entry_time[y]>entry_time[x])) return(FORWARD);
		if (processed[y] && (entry_time[y]<entry_time[x])) return(CROSS);
		System.out.printf("Warning: self loop (%d,%d)\n",x,y);
		return -1;
	}
	
	static void dfs(GraphStart g, int v)	{
		int y;
		discovered[v] = true;
		process_vertex_early(v);
		for(int i=0;i<=g.degree[v]-1;i++)		{
			y = (Integer)g.edges[v].get(i);
			if(!discovered[y])  {		
				parent[y]=v;
				process_edge(v,y);
				if (!g.directed) g.remove_edge(y,v);  // removes the "mirror" edge so not mistakenly reprocessed
				dfs(g,y);
			}
			else		{
	   		process_edge(v,y);
            if (!g.directed) g.remove_edge(y,v);
			}
		}
		process_vertex_late(v);   
		processed[v]=true;
	}
	
	static void process_edge(int x,int y) 	{
		int c = edge_classification(x,y);
		if (c == BACK) System.out.printf("back edge (%d,%d)\n",x,y);
		else if (c == TREE) System.out.printf("tree edge (%d,%d)\n",x,y);
		else if (c == FORWARD) System.out.printf("forward edge (%d,%d)\n",x,y);
		else if (c == CROSS) System.out.printf("cross edge (%d,%d)\n",x,y);
		else System.out.printf("edge (%d,%d)\n not in valid class=%d",x,y,c);	
	}
	
	static void initialize_search(GraphStart g)	{
		for(int i=1;i<=g.nvertices;i++)		{
			processed[i] = discovered[i] = false;
			parent[i] = -1;
		}
	}
	
	static int stillVert(GraphStart g){	   
      for(int i=1;i<=g.nvertices;i++)  {
         if (! processed[i]) return i;
      }
   return -1;	
   }
	
	static public void main(String[] args) throws FileNotFoundException	{
		String filename = "test3.txt";
		GraphStart g = new GraphStart();
		g.readfile_graph(filename);
		g.print_graph();
		initialize_search(g);
		
		// Loop for connected components, this only does the expected on undirected graphs		
		int compRoot= 1;
		while (compRoot != -1) {
			System.out.printf("\nProcessing Component: \n");
         dfs(g,compRoot);
  			compRoot = stillVert(g);	
		}		
		
	}
}