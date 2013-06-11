// Garrett Milster
// CPE 349
// Sequence Alignment Assignment

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.*;

class alignment{
	static public int h = 0;
	static public int w = 0;
   static int distance = 0;
	// These are the two strings algorithm is aligning, I chose to hardcode them in rather than use a file
	static public char text1[] = new char[4000];
	static public char text2[] = new char[4000];
	static public char text3[] = new char[4000];
   static public char indices[] = new char[4000];
	// just a debugging function which prints the table of values
	static public void printTable(int list[][])
	{
		for(int i = 0; i < h; i++)
		{
			for(int j = 0; j < w; j++)
			{
				System.out.print(list[i][j] + "   ");
			}
			System.out.println("");
		}
	}

	// This function builds the table of values for the dynamic programming algorithm
	static public int[][] buildTable()
	{


		int list[][] = new int[h+1][w+1]; // these values are hardcoded in for the table size
		int penalty = 2; // the gap penalty laid out in the program specs.
		int k = 0;

		// this loop initializes the first row and column of the table to multiples of two
		// this helps to account for the gap penalty
		for(int i = 0; i < w; i++)
		{
			list[0][i] = k;
			if(i < h)
			{
				list[i][0] = k;
			}
			k+=2;
		}

		// this loop uses a dynamic programming approach to build the table.
		// starting at location (1,1), it takes the min of up + 2, left + 2, and Diagonal + 1 if ther's a mismatch and 0 if match
		// the addition of 2 to the up and left variables accounts for the gap penalty
		// adding 1 to the diagonal accounts for the penalty of a mismatch
		for(int i = 1; i <= h; i++)
		{
			for(int j = 1; j <= w; j++)
			{
				int left = list[i][j-1];
				int up = list[i-1][j];
				int diag = list[i-1][j-1];

				// checks for possible mismatch, adds 1 if found
				if(text1[j] != text2[i])
				{
					diag++;
				}

				//finds min between left and up
				if(up < left)
				{
					k = up;
				}
				else
				{
					k = left;
				}

				// finds min between diagonal and min(left,up) + 2 for gap penalty
				// we the min because we are looking for the shortest edit distance
				if(diag < (k+penalty))
				{
					list[i][j] = diag;
				}
				else
				{
					list[i][j] = k+penalty;
				}
			}
		}
		return list;
	}

	// This function backtraces through the table and builds an array of the aligned string
	// the backtrace basically does the opposite of the build, it checks if left or up is 2 less than the current position
	// this would indicate a gap, if there is no gap, the diagonal is checked. if the diagonal is 1 less than the current 
	// position, this means there was a mismatch and if they are equal it was a match.
	static public char[] backtrace(int list[][])
	{
		int i = h - 1;
		int j = w - 1;
		int diag,left, up;
		;
		int k = 0;
		// loops through entire table
		while(i != 0 || j!=0)
		{

         // accounts for certain cases where either i or j equals zero before the other one
			if(i == 0)
			{
           // left is the only way to go
			  diag = -1;
			  left = list[i][j-1];
			  up = -1;
			}
         else if(j == 0)
  			{
           // up is the only way to go
           diag = -1;
			  left = -1;
			  up = list[i-1][j];
			}
         else
         {	
			  diag = list[i-1][j-1];
			  left = list[i][j-1];
			  up = list[i-1][j];
			}
			//checks left and up for gaps, otherwise checks up
			if(left == (list[i][j] -2))
			{
				distance+=2; //gap penalty
				j = j -1; // stay on same row, move over a column
				indices[k] = 0;
				k++;
			}
         // checks up for gaps, otherwise checks diagonal
			else if(up == (list[i][j] -2))
			{
				distance+=2;
				i = i -1;
            text3[j] = 0;
            indices[k] = text2[i];
				k++;
			}
			else
			{
				if(list[i][j] != list[i-1][j-1]) //if the diagonal isn't equal, add mismatch penalty
				{
					distance++;
				}	
				indices[k] = text2[i];
				i = i -1;
				j = j -1;
				k++;
			}
         //System.out.println("i: " + i + " j: " + j + " value: " + indices[k-1]);
		}
		return indices;
	} 
   
   static void readfile(String filename) throws FileNotFoundException     
   {
       String temp1,temp2;
       FileInputStream in = new FileInputStream(new File(filename));
       Scanner sc = new Scanner(in);
    		temp1 = sc.nextLine();
         temp2 = sc.nextLine();
         if(temp1.length() < temp2.length())
         {
            h = temp1.length() + 1;
            w = temp2.length() + 1;
           // System.out.println("H: " + h + " W: " + w);
            for(int i = 0; i < temp1.length(); i++)
            {
               text2[i+1] = temp1.charAt(i);
              // System.out.println(text2[i+1]);
            }
            for(int i = 0; i < temp2.length(); i++)
            {
               text1[i+1] = temp2.charAt(i);
               text3[i+1] = temp2.charAt(i);
               //System.out.println(text1[i+1]);
            }
         }
         else
         {
            h = temp2.length() + 1;
            w = temp1.length() + 1;
           // System.out.println("H: " + h + " W: " + w);
            for(int i = 0; i < temp2.length(); i++)
            {
               text2[i+1] = temp2.charAt(i);
              // System.out.println(text2[i+1]);
            }
            for(int i = 0; i < temp1.length(); i++)
            {
               text1[i+1] = temp1.charAt(i);
               text3[i+1] = temp1.charAt(i);
               //System.out.println(text1[i+1]);
            }
         }
      
	 }
	static public void main(String[] args) throws FileNotFoundException	
	{
      String filename = args[0];
      readfile(filename);
		int list[][] = buildTable();
		char list2[] = backtrace(list);
		int dist = 0;
		int i = w - 2;
      int count = 0;
		//loops through result array, compares with original string to calculate edit distance.
		// a zero indicates a gap, 2 is added, two values not being equal means a mismatch, and 1 is added.
		System.out.println("Edit Distance: " + distance);

		int k = 1;

		// if command line argument given, print this
		if(args.length > 1)
		{
			//loops through both arrays and prints strings and distance values
			for(int j = w-2;j >=0; j--)
			{
            if(text3[k] == 0)
            {
               System.out.print("- ");
            }
            else
            {
				   System.out.print(text3[k] + " ");
            }


				if(list2[j] == 0)
				{
					System.out.println("- 2");
				}
				else
				{
					System.out.print(list2[j] + " ");
					if(list2[j] != text1[k])
					{
						System.out.print("1");
					}
					else
					{
						System.out.print("0");
					}
					System.out.println("");
				}

				k++;
			}
		}	

	}
}
