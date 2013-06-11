import java.awt.*;
import java.applet.Applet;
import javax.swing.*;
import java.awt.event.*;

public class SortDriver extends Applet {
   
   public int limit = 2000;  // size of array to sort - if your sort is too slow,
                             //    make this smaller
   public int array[]; 
   private int index = -1;  // flag to paint either a single location or whole array
                            //     if -1, paint whole array; otherwise, paint location index
   
   private JPanel sortPanel;
   private SortCanvas picture;
   private JLabel label; // a non-interactive text field
   private JRadioButton r1, r2, r3, r4, ordered, reverse, random;
   private ButtonGroup rButtons;
   private JTextField tField;
   private boolean bsort = true;
   private boolean rand = true;
   private boolean isort, msort, qsort, order, rev;
   private int size;
   private JButton sort;
   
   public void init() {
      System.out.println("Picture sort started");
      array = new int[limit];
      
      for (int i=0; i<limit; i++) {
          int val = (int) (Math.random()*1000.0);
          array[i] = val; 
      }
      
      // set up the window
      sortPanel = new JPanel();
      sortPanel.setLayout(new BoxLayout(sortPanel, BoxLayout.Y_AXIS));
      
      picture = new SortCanvas();
      sortPanel.add(picture);
      add(sortPanel);
      
      picture.paint(picture.getGraphics());

      
      // here's some code that sets up and initializes a text field and four radio buttons

   	// they have to be added to JPanels as usual
      
	   tField = new JTextField("", 10); // text field with room for 10 characters

	   r1 = new JRadioButton("Bubble Sort", true); // true sets this button by default
	   r2 = new JRadioButton("Quick Sort", false);
	   r3 = new JRadioButton("Merge Sort", false);
	   r4 = new JRadioButton("Insertion Sort", false);
	   ordered = new JRadioButton("In Order", false);
	   reverse = new JRadioButton("Reverse Order", false);
	   random = new JRadioButton("Random Order", true);

	   rButtons = new ButtonGroup(); // radio buttons have to be added to a ButtonGroup to work
      ButtonGroup sButtons = new ButtonGroup();
      ButtonActionListener baListener = new ButtonActionListener();
      
	   rButtons.add(r1);
	   rButtons.add(r2);
	   rButtons.add(r3);
	   rButtons.add(r4);
	   sButtons.add(ordered);
	   sButtons.add(reverse);
	   sButtons.add(random);


	   tField.addActionListener(baListener); // text and radio buttons add listener as usual
      
      //radio buttons
	   r1.addActionListener(baListener);
	   r2.addActionListener(baListener);
	   r3.addActionListener(baListener);
	   r4.addActionListener(baListener);
	   ordered.addActionListener(baListener);
	   reverse.addActionListener(baListener);
	   random.addActionListener(baListener);
	   
      JPanel buttonPanel = new JPanel();
 	   buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
      buttonPanel.add(r1);
      buttonPanel.add(r2);
      buttonPanel.add(r3);
      buttonPanel.add(r4);
      
      JPanel sortPanelC = new JPanel();
      sortPanelC.setLayout(new BoxLayout(sortPanelC, BoxLayout.X_AXIS));
      sortPanelC.add(ordered);
      sortPanelC.add(reverse);
      sortPanelC.add(random);
      
      //sorting buttons
      sortPanel.add(sortPanelC);
      sortPanel.add(tField); 
      sortPanel.add(buttonPanel);
      
      sort = new JButton("SORT");
      sort.addActionListener(baListener);
      sortPanel.add(sort);
      sortPanel.validate();      
   }
   
   private void doSort() {
      if(bsort == true)
      {
         bubbleSort();
      }
      else if(isort == true)
      {
         insertionSort(0, array.length -1);
      }
      else if(qsort == true)
      {
         quickSort(0, array.length -1);
      }
      else if(msort == true)
      {
         mergeSort(0, array.length -1);
      }
   }      
   private class ButtonActionListener implements ActionListener {

      public void actionPerformed(ActionEvent event) {

         Object source = event.getSource();

         if (source == tField) 
         { 
            // called when user hits return in text field
            String temp1 = tField.getText();
            
            int size = Integer.parseInt(temp1);
            int[] temp = new int[size];
            array = temp;
            // these three check which conditional is true and makes a new array of the newly entered size and orders it.
               if(order == true)
               {
                  for(int i = 0; i < array.length; i++)
                  {
                     array[i] = i;
                  }
                  index = -1;
                  picture.paint(picture.getGraphics());
               }
               else if(rand == true)
               {
                  for (int i=0; i<size; i++) {
                     int val = (int) (Math.random()*1000.0);
                     array[i] = val;
                  }
                  index = -1;
                  picture.paint(picture.getGraphics());              
                
               }
               else if(rev == true)
               {
                  int count = array.length;

                  for(int i = 0; i < array.length; i++)
                  {
                     array[i] = count;
                     count --;
                  }      
                  index = -1;   
                  picture.paint(picture.getGraphics());              
               }
            
         }
         // if the source is the bubble sort radio button, it switches the bubble sort boolean to true
         else if (source == r1)
         {
            bsort = true;
            qsort = false;
            msort = false;
            isort = false;
         }
         // the next 3 work the same as the previous comment but with respect to each button
         else if (source == r2)
         {
            bsort = false;
            qsort = true;
            msort = false;
            isort = false;
         }
         else if (source == r3)
         {
            bsort = false;
            qsort = false;
            msort = true;
            isort = false;
         }
         else if(source == r4)
         {
            bsort = false;
            qsort = false;
            msort = false;
            isort = true;
         }
         //if the source is the ordered radio button, it orders the array
         else if(source == ordered)
         {
            order = true;
            rand = false;
            rev = false;
            
            for(int i = 0; i < array.length; i++)
            {
               array[i] = i;
            }
            index = -1;  
            picture.paint(picture.getGraphics());
            
         }
         //the next two do the same thing but with respect to their ordering
         else if(source == random)
         {
            order = false;
            rand = true;
            rev = false;
            
            for (int i=0; i<array.length; i++) {
                int val = (int) (Math.random()*1000.0);
                array[i] = val; 
            }
            index = -1;
            picture.paint(picture.getGraphics());
            
         }
         else if(source == reverse)
         {
            order = false;
            rand = false;
            rev = true;
            
            int count = array.length;
            // copies the sorted array into the instance variable array.
               for(int i = 0; i < array.length; i++)
               {
                  array[i] = count;
                  count --;
               }    
               index = -1;       
               picture.paint(picture.getGraphics());
         }
         else if(source == sort)
         {
            System.out.println("SORTING");
            //if the source is the sort button, sort.
            doSort();
         }
      }

   }
   
   private void bubbleSort()
   {
      int temp;
      for (int i=0; i<array.length-1; i++) {
         for (int j=0; j<array.length-1-i; j++) {
            if (array[j]>array[j+1]) {
               temp = array[j]; array[j] = array[j+1]; array[j+1] = temp;
                  
               // redraw location j
               index = j; 
               picture.paint(picture.getGraphics());
               // redraw location j+1
               index = j+1; 
               picture.paint(picture.getGraphics());
            }
         }
      }
   }
    private void quickSort(int first,int last)  
    {
       int size = last-first+1;
       if (size <= 20)
          insertionSort(first,last);
       else
       {
          medianOfThree(first,last);
          int p = partition(first,last);
          quickSort(first,p-1); 
          quickSort(p+1,last);
       }
    }

   // The following method is provided ONLY for the purpose of isolating the 
    // counting of comparisons into a single location.  Any method below that
    // requires such counting can simply perform the comparison using this 
    // method and the counter will automatically be updated.  Of course this 
    // method would not be used in a production-quality implementation.
    // Although this method uses the same logic as compareTo, this concept
    // has nothing to do with the Comparable interface.

    private int compare(int left, int right) 
    {
       if (left < right)
          return -1;
       else if (left > right)
          return 1;
       else
          return 0;
    }

    // No action is taken if there are not at least three elements in the 
    // subarray. At the end of a call, the pivot value must be in array[first].  
    // You should swap, if necessary, to make this adjustment.  Otherwise, there 
    // should be no date movement in this method.  In order to easily count 
    // comparisons, all comparisons should be done using calls to "compare".

    private void medianOfThree(int first,int last)  
    {
       int temp,middle,median;

       if (last-first+1 < 3)
          return;
       middle = (first+last)/2;
       if (compare(array[first],array[middle]) <= 0)
          if (compare(array[middle],array[last]) <= 0)
             median = middle;
          else if (compare(array[last],array[first]) <= 0)
             median = first;
          else
             median = last;
       else
          if (compare(array[first],array[last]) <= 0)
             median = first;
          else if (compare(array[last],array[middle]) <= 0)
             median = middle;
          else
             median = last;
       temp = array[first];
       array[first] = array[median];
       array[median] = temp;
    }

    // The value returned by this method must be the index of the array
    // element that contains the pivot after the partition is complete.
    // Comparisons are counted by the calls to "compare".

    private int partition(int first,int last)
    {
       int left = first+1;
       int right = last;
       int temp;
       while(true)
       {
          while(left<=right && compare(array[left],array[first])<=0)
             ++left;
          while(right>=left && compare(array[first],array[right])<0)
             --right;
          if (left > right)
             break;
          temp = array[left];
          array[left] = array[right];
          array[right] = temp;
          index = left; 
          picture.paint(picture.getGraphics());
          // redraw location left and right
          index = right; 
          picture.paint(picture.getGraphics());
          ++left;
          --right;
       } 
       temp = array[first];
       array[first] = array[right];
       array[right] = temp;
       index = first; 
       picture.paint(picture.getGraphics());
       // redraw location right and first
       index = right; 
       picture.paint(picture.getGraphics());
       return right;
    }

    // This is the usual implementation of binary insertion sort that uses a call
    // to binarySearch to determine where the current item belongs in the array.

    private void insertionSort(int first,int last)
    {
       for(int i=first+1;i<=last;++i)
       {
          int target = binarySearch(array[i],first,i-1);
          if (target != i)
          {
             int temp = array[i];
             for(int j=i;j>target;--j)
             {
                array[j] = array[j-1];
                array[target] = temp;
                // redraw location j
                index = j; 
                picture.paint(picture.getGraphics());
                // redraw location target
                index = target; 
                picture.paint(picture.getGraphics());
             }
          }          
       }
    }

    // Use binary search to determine the index of the array after which x
    // should be inserted.

    private int binarySearch(int x, int first,int last)
    {
       int middle = 0;
       while(first<=last)
       {
          middle = (first+last)/2;
          if (compare(x,array[middle]) < 0)
             last = middle - 1;
          else if (compare(x,array[middle]) > 0)
             first = middle + 1;
          else
             return middle + 1;  
       }
       if (compare(x,array[middle]) > 0)
          return middle + 1;
       else
          return middle;
    }
    
    private void mergeSort(int first, int last)
    {
       if((last - first) <= 1)
       {
          insertionSort(first,last);
       }
       else
       {
          int mid = (first+last)/2;
          mergeSort(first, mid);
          mergeSort(mid +1, last);
          merge(first, mid, last);
          
      }
    }
    
    private void merge(int low, int mid, int high)
    {
      int index1 = low;
      int index2 = mid+1;
      int index3 = 0;
      int[] z = new int[high - low + 1];

      while(index1 <= mid && index2 <= high)
       {
          if(array[index1] < array[index2])
          {
             z[index3] = array[index1];
             index1++;
             index3++;
          }
          else 
          {
             z[index3] = array[index2];
             index2++;
             index3++;
          }
       }
       while(index1 <= mid)
       {
          z[index3] = array[index1];
          index1++;
          index3++;
       }

       while(index2 <= high)
       {
          z[index3] = array[index2];
          index2++;
          index3++;
       }
       
       int count = 0;
       for(int i = low; i <= high;i++)
       {          
          array[i] = z[count];
          index = i;
          picture.paint(picture.getGraphics());
          count++;
       }
       
       
    }

   class SortCanvas extends Canvas {
      // this class paints the window 
       
      SortCanvas() {
         resize(1001, 50);
         setBackground(Color.white);
      }
       
      public void paint(Graphics g) {
         
         if (index == -1) {
               System.out.println("x ");
            // paint whole array 
            setBackground(Color.white);
            g.setColor(Color.white);
            g.fillRect(0, 0, 1001, 50);
            
            for (int i=0; i<array.length; i++) {
               // the larger the number, the brighter blue it is
               // blue is between 0.0 and 1.0
               float blue = (float)(array[i]/1000.0);
               if (blue<0f) blue = 0f;
               if (blue>1f) blue = 1f;
               g.setColor(new Color(0f, blue, blue));
               // window is 1000 pixels wide; array location 0 is painted at left; 
               //   array location limit-1 is painted to right
               g.drawLine((int)(i*1000.0/array.length), 0, (int)(i*1000.0/array.length), 50);
            }
         }
         else {
            float blue = (float)(array[index]/1000.0);
            if (blue<0f) blue = 0f;
            if (blue>1f) blue = 1f;
            g.setColor(new Color(0f, blue, blue));
            g.drawLine((int)(index*1000.0/array.length), 0, (int)(index*1000.0/array.length), 50);
         }   
      }
   }
}