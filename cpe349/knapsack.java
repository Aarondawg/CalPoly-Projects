// Garrett Milster
// CPE 349
// Knapsack assignment 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.lang.*;

class knapsack{
	public static int numItems = 0;
	public static int capacity = 0;
	public static int values[];
	public static int weights[];
  public static int bfMax = 0;
  public static int bfMaxCap = 0;
  public static int bfLength = 0;
  public static int bfIndices[];


   // This function calls the brute force recursive function
   // and then prints the solution
   static void bruteForce()
   {
      bfIndices = new int[numItems];
      bruteForceRecurse(values, new boolean[numItems], 0);
      System.out.println("Best feasible solution found: total value - " + bfMax + " total weight - " + bfMaxCap);
      for(int i = 0; i < bfLength; i++)
      {
         System.out.println("Item " + bfIndices[i] + ".  value: " + values[bfIndices[i]] + " weight: " + weights[bfIndices[i]]);
      }
   }

   // recursive code found at http://anh.cs.luc.edu/314-315/ListRecursion.java
   // I added code to adjust the algorithm for the knapsack problem
   private static void bruteForceRecurse(int[] e, boolean[] isIn, int iStart) {
    
    // e is the array of possible characters.
    // e[j] is in the set if isIn[j] is true, for each j < iStart.
    // isIn[j] is false for each j >= iStart.
    // isIn ends in the same state it starts the method.
    // The method provides all choices of true and false values for every
    // index >= iStart, and prints the result if iStart == e.length.
    

    int sum = 0;
    int total = 0;
    int count = 0;

    //checks if iStart has passed through every value in e[]
    if (iStart == e.length) {
      for (int j = 0; j < e.length; j++)
      {
        // isIn[] is a boolean which takes certain value out of the combination
        // it's changed for every recursive call to find every possible combination
        if(isIn[j]) 
        {
          count++;
          sum += weights[j]; //calculates weight of this combination
        }
      }
      
      int indices[] = new int[count];
      if(sum <= capacity) //if the weight is within the capacity, checks if this combination is better than the max
      {
      
         int i = 0;
         //saves indeces of this combination
         for (int j = 0; j < e.length; j++)
         { 
            if(isIn[j]) 
            {
               indices[i++] = j;

               total += e[j];
            }
         }

         // if this combination has a better value than the previous max, it overwrites it;
         if(total > bfMax)
         {
           bfMax = total;
           bfMaxCap = sum;
           bfLength = indices.length;
           for(int j = 0; j < indices.length; j++)
           {
              bfIndices[j] = indices[j];
           }
           
         }
      }
    }
    else {

      //recursively calls itself with the next value in the array in or out of the combination
      isIn[iStart] = true;
      bruteForceRecurse(e, isIn, iStart+1);  // do include e[iStart]
      isIn[iStart] = false; // placing isIn back in its original state
      bruteForceRecurse(e, isIn, iStart+1);  // not include e[iStart]
    }
    
  }
    
    
  // Node Structure for branch and bound
  static class Node implements Comparable<Node>{

      int profit;
      int weight;
      double bound;
      int level;
      int list[];
      // compare function for the priority queue
      public int compareTo(Node o) {
         if(o.bound > this.bound)
         {
          return 1;
         }
         else if(o.bound < this.bound)
         {
            return -1;
         }

         return 0;

    }
  }

   //finds the upper bound for branch and bound as calculated in the textbook
   static float findBound(Node n, int index, int list[])
   {
      float weight = capacity - n.weight;
      // if the weight of the node is above the capacity, no bound is calculated
      if(weight < 0)
      {
          return 0;

      }
      // if the node is on the lest level of the tree it just returns the profit value
      if(index == (numItems-1))
      {
         return n.profit;
      }
      float density = ((float)values[index+1]/(float)weights[index+1]);

      return n.profit + (density*weight);


   }
   static void printNode(Node n)
   {
      System.out.println("Node Index: " + n.level);
      System.out.println("Weight: " + n.weight + " Value: " + n.profit);
      System.out.println("Bound: " + n.bound);
      System.out.println();

   }

   //found code http://stackoverflow.com/questions/7474975/memory-choke-on-branch-and-bound-knapsack-implementation
   // edited code to keep track of best solution's path down the tree, original code only found max value and weight
   static void branchbound()
   {
    int maxProfit=0;
    int usedWeight=0;
    Node u;
    Node v = new Node();
    Node maxNode = new Node();
    maxNode.profit = 0;
    maxNode.weight = 0;

    // list keeps track of the indeces to get to each node
      v.list = new int[numItems];
      for(int i = 0; i < v.list.length;i++)
      {
        v.list[i] = -1;
      }
    PriorityQueue<Node> PQ = new PriorityQueue<Node>(numItems);
    
    v.level=-1;
    v.profit=0;
    v.weight=0; // v initialized to -1, dummy root
    v.bound = findBound(v,v.level,v.list);
    
    PQ.add(v);
    try{
       while(!PQ.isEmpty()){ //runs until every node has been removed

       v=PQ.poll();
       //printNode(v);
       u = new Node();
       if(v.bound>0){ // check if node is still promising
           if(v.level +1 < numItems)
           {
              u.level = v.level+1; // set u to the child that includes the next item

             u.weight = v.weight + weights[u.level]; // new value and weight to include new item
             u.profit = v.profit + values[u.level];
             u.list = Arrays.copyOf(v.list,v.list.length);

             u.list[u.level] = 1;
             if (u.weight <=capacity && u.profit >= maxNode.profit){ //if this child exceeds the previous max profit, overwrite it with this child
                 maxNode.profit = u.profit;
                 maxNode.weight = u.weight;
                 maxNode.list  =  Arrays.copyOf(u.list,u.list.length);
             }

             u.bound = findBound(u,u.level,u.list);

             if(u.bound > maxProfit){ //prunes children by making sure their bounds are greater than the max profit
                 PQ.add(u); 
             }
              
              // exactly the same as above but does not changed value and weight for new item
             u = new Node();
             u.level = v.level+1;
             u.weight = v.weight; 
             u.profit = v.profit;
             u.list = Arrays.copyOf(v.list,v.list.length);
             u.bound = findBound(u,u.level,u.list);
             if (u.weight <=capacity && u.profit >= maxNode.profit){
                 maxNode.profit = u.profit;
                 maxNode.weight = u.weight;
                 maxNode.list  =  Arrays.copyOf(u.list,u.list.length);
             }
             if(u.bound>maxProfit)
                 PQ.add(u);

             }
           

       }
    }
        System.out.println("Best feasible solution found: total value - " + maxNode.profit + " total weight - " + maxNode.weight);
        for(int i = 0; i < maxNode.list.length; i++)
        {
          if(maxNode.list[i] != -1)
           System.out.println("Item " + i + ".  value: " + values[i] + " weight: " + weights[i]);
        }

    } catch (OutOfMemoryError e) { //branch and bound can run out of memory so this will catch it and print the solution at this point
        System.out.println("Best feasible solution found: total value - " + maxNode.profit + " total weight - " + maxNode.weight);
        for(int i = 0; i < maxNode.list.length; i++)
        {
          if(maxNode.list[i] != -1)
           System.out.println("Item " + i + ".  value: " + values[i] + " weight: " + weights[i]);
        }    
    }
   
   
}
    //http://www.leepoint.net/notes-java/data/arrays/31arrayselectionsort.html
    // edited to work with profit density
    public static void selectionSort(int[] v, int[] w, int[] indices) {
        for (int i=0; i<v.length-1; i++) {
            for (int j=i+1; j<v.length; j++) {

                float d1 = v[i]/w[i];
                float d2 = v[j]/w[j];
                if(d1 < d2) {
                    //... Exchange elements
                    int temp = v[i];
                    v[i] = v[j];
                    v[j] = temp;

                    temp = w[i];
                    w[i] = w[j];
                    w[j] = temp;

                    temp = indices[i];
                    indices[i] = indices[j];
                    indices[j] = temp;
                }
            }
        }
    }

   // this function calculates the greedy solution to knapsack
   static void greedy()
   {
      int indices[] = new int[numItems];
      for(int i = 0; i < indices.length; i++)
      {
        indices[i] = i;
      }
      int v[] = Arrays.copyOf(values,values.length); // makes copies of values and weights before sorting
      int w[] = Arrays.copyOf(weights,weights.length);

      
      selectionSort(v,w,indices);
      int cap = 0;
      int val = 0;
      int index = 0;

      //after sorting, this loop adds items to the list in order of highest profit density until capacity is filled
      for(int i = 0; i < v.length; i++)
      {
          if((cap + w[i]) <= capacity)
          {
            cap+=w[i];
            val+=v[i];
          }
          else
          {
            index = i;
            break;
          }
      }

      
        System.out.println("Greedy solution (not necessarily optimal): total value - " + val + " total weight - " + cap);
        for(int j = 0; j < index; j++)
        {
           System.out.println("Item " + indices[j] + ".  value: " + v[indices[j]] + " weight: " + w[indices[j]]);
        }
        System.out.println();
   }


   static void readfile(String filename) throws FileNotFoundException     
   {
       FileInputStream in = new FileInputStream(new File(filename));
       Scanner sc = new Scanner(in);
       numItems = sc.nextInt();	
       //System.out.println("NUM: " + numItems);	
       values = new int[numItems];
       weights = new int[numItems];
       for(int i = 0; i < numItems; i++)
       {
       		sc.nextInt();
       		values[i] = sc.nextInt();
       		weights[i] = sc.nextInt();
          //System.out.println(i + ". " + values[i] + " " + weights[i]);
       }
       capacity = sc.nextInt();
       //System.out.println("Cap: " + capacity);
	}
	static public void main(String[] args) throws FileNotFoundException	
	{
        String filename = args[0];
        readfile(filename);
        System.out.println("Trying Brute Force... ");
        System.out.println();
        bruteForce();
        System.out.println();

        System.out.println("Trying Greedy Approach... ");
        System.out.println();
        greedy();

        System.out.println("Trying Branch and Bound... ");
        System.out.println();
        branchbound();
     }
        
}
