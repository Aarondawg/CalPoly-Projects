/* Garrett Milster */

import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.util.Iterator;
import javax.swing.*;

public class ColorTetrisDriver extends Applet {
   
   //number of different colors allowed in the game -
   //if changed, must also change paint method
   public int numColors = 4;  
   
   //number of rows displayed (minus 1 - don't worry about it)
   public int numRows = 10;
   
   //number of stacks
   public int numCols = 6;
   
   //stacks that store the color blocks
   public Stack stacks[] = new Stack[numCols];
   
   //color and location of the next block
   private int nextColor, nextColumn;
   
   //column to be swapped from
   private int colMarker = 0;

   //inner class that does the display
   ColorTetrisCanvas grid;
   
   //buttons that swap this stack's top with left or right tops
   private JButton left, right;
   private JPanel dirPanel;
   
   //buttons that move the stack marker
   private JButton leftS, rightS;
   private JPanel dirPanelS;
   
   //buttons that move the incoming cube
   private JButton leftC, rightC;
   private JPanel dirPanelC;

   private JPanel gamePanel;
   
   //this listener object responds to timer events
   private TimerActionListener timerListener;
   
   //this listener object responds to button events
   private ButtonActionListener buttonListener;
   
   public void init() {
      System.out.println("ColorTetris started");
      timerListener = new TimerActionListener();
      buttonListener = new ButtonActionListener();
       
      //initialize the stacks with random colors
      for (int i=0; i<numCols; i++) {
         stacks[i] = new Stack();
         int color = (int) (Math.random()*1000.0)%(numColors);
         stacks[i].push(new Integer(color));
      }
        
      // set up the window
      gamePanel = new JPanel();
      gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
      
      // first place the playing grid
      grid = new ColorTetrisCanvas(numRows, numCols, numColors);
      gamePanel.add(grid);
       
      // place the incoming cube control buttons 
      leftC = new JButton("<-- Column ");
      leftC.addActionListener(buttonListener);
      rightC = new JButton("Column -->");
      rightC.addActionListener(buttonListener);
      dirPanelC = new JPanel();
      dirPanelC.setLayout(new BoxLayout(dirPanelC, BoxLayout.X_AXIS));
      dirPanelC.add(leftC);
      dirPanelC.add(rightC);
      gamePanel.add(dirPanelC);

      // places the stack top swap buttons
      left = new JButton("<-- Stack");
      left.addActionListener(buttonListener);
      right = new JButton("Stack -->");
      right.addActionListener(buttonListener);
      dirPanel = new JPanel();
      dirPanel.setLayout(new BoxLayout(dirPanel, BoxLayout.X_AXIS));
      dirPanel.add(left);
      dirPanel.add(right);
      gamePanel.add(dirPanel);

      // places the stack marker control buttons
      leftS = new JButton("<<");
      leftS.addActionListener(buttonListener);
      rightS = new JButton(">>");
      rightS.addActionListener(buttonListener);
      dirPanelS = new JPanel();
      dirPanelS.setLayout(new BoxLayout(dirPanelS, BoxLayout.X_AXIS));
      dirPanelS.add(leftS);
      dirPanelS.add(rightS);
      gamePanel.add(dirPanelS);

      add(gamePanel);
      
      // start the timer to go off in 3 seconds
      javax.swing.Timer timer = new javax.swing.Timer(5000, timerListener);  // goes off in 2 seconds
      timer.start();
   }
   
   private class ButtonActionListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         Object source = event.getSource();
         if (source == leftS) {
            // shift column marker left
            shiftColumnMarkerLeft();
         }
         else if (source == rightS) {
            // shift column marker right
            shiftColumnMarkerRight();
         }
         else if (source == left) {
            if(colMarker > 0)
            {
            // swap tops of this and left stack
               if(stacks[colMarker].height() == 0 && stacks[colMarker - 1].height() == 0)
               {
                  // If both stacks are of length 0, do nothing
               }
               else if(stacks[colMarker].height() ==0)
               {
                  // If the marked stack is empty, but the left stack is not, pop the left stack and push it onto the marked stack
                  stacks[colMarker].push(stacks[colMarker - 1].pop());
                  
               }
               else if(stacks[colMarker - 1].height() == 0)
               {
                  // If the left stack is empty, but the marked stack is not, pop the marked stack, and push it onto the left stack
                  stacks[colMarker - 1].push(stacks[colMarker].pop());
               }
               else     
               {     
                  // Otherwise, switch the top of the marked and left stacks 
                  int temp = (Integer)stacks[colMarker - 1].pop();
                  int temp2 = (Integer)stacks[colMarker].pop();
                  stacks[colMarker].push(temp);
                  stacks[colMarker - 1].push(temp2);
               }
            }
         }
         else if (source == right) {
            // swap tops of this and right stack
            if(colMarker < numCols-1)
            {
               if(stacks[colMarker].height() ==0 && stacks[colMarker + 1].height() == 0)
               {
                  // If the length of both stacks are 0, do nothing
               }
               else if(stacks[colMarker].height() ==0)
               {
                  // If the marked stack is empty, but not the right stack, pop the right stack's TOS and push it onto the marked one
                  stacks[colMarker].push(stacks[colMarker + 1].pop());
               }
               else if(stacks[colMarker + 1].height() == 0)
               {
                  // If the right stack is empty, but not the marked stack, pop the marked stack and push it onto the right stack
                  stacks[colMarker + 1].push(stacks[colMarker].pop());
               }
               else
               {
                  // Otherwise, switch the top of the marked and right stacks
                  int temp = (Integer)stacks[colMarker + 1].pop();
                  int temp2 = (Integer)stacks[colMarker].pop();
                  stacks[colMarker].push(temp);
                  stacks[colMarker + 1].push(temp2);  
               }
            }
         }
         else if (source == leftC) {
            // move incoming cube left
            if (nextColumn > 0) {
               nextColumn--;
            }
         }
         else if (source == rightC) {
            // move incoming cube right
            if (nextColumn < numCols-1) {
               nextColumn++;
            }
         }
         removeCombos();
         grid.paint(grid.getGraphics());
      } 
      
      public void shiftColumnMarkerLeft() {
         if (colMarker > 0) colMarker--;
         else colMarker = 0;
      }
        
      public void shiftColumnMarkerRight() {
         if (colMarker < numCols-1) colMarker++;
         else colMarker = numCols-1;
      }
   }
   
   private class TimerActionListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         boolean check = true;
         // new cube onto a column 
         stacks[nextColumn].push(new Integer(nextColor));
          
         // generate randomly the next column and color
         nextColor = (int) (Math.random()*1000.0)%(numColors);
         nextColumn = (int) (Math.random()*1000.0)%(numCols);
         
         while(check == true)
         {         
            check = removeCombos();
         }
         
         // update screen
         grid.paint(grid.getGraphics());
      }
   }        
    
   public boolean removeCombos() {
      
      for(int i = 0; i < 4; i ++)
      {
         if(stacks[i].height() == 0  || stacks[i+1].height() == 0 || stacks[i+2].height() == 0)
         {
            // If any of the three stacks have length 0, do nothing
         }
         else
         {
            // Otherwise, if the three equal each other, pop them off and break the loop
            if(stacks[i].peek().equals(stacks[i+1].peek()) && stacks[i].peek().equals(stacks[i+2].peek()))
            {
               stacks[i].pop();
               stacks[i+1].pop();
               stacks[i+2].pop();  
               return true;        
            }
         }    
      } 
      return false;
   }
    
   class ColorTetrisCanvas extends Canvas {
      // this class paints the window 
      int rows, cols;
      int numColors;
       
      ColorTetrisCanvas(int r, int c, int nc) {
         rows = r;
         cols = c;
         numColors = nc;
         resize(cols*50+1, rows*50);
         setBackground(Color.white);
      }
       
      public void paint(Graphics g) {
         
         Integer stackColor;
         setBackground(Color.black);
         g.setColor(Color.black);
         g.fillRect(0, 0, cols*50+1, 550);
           
         boolean done = false;
         for (int j=0; j<cols; j++) {
            if (stacks[j].height() >= rows-1) {
               // a stack has exceeded the limit
               done = true;
            }   
         }
         if (done == false) {
            // draw the next block to fall   
            switch (nextColor) {
               case 0: g.setColor(Color.blue); break;
               case 1: g.setColor(Color.red); break;
               case 2: g.setColor(Color.green); break;
               case 3: g.setColor(Color.cyan); break;
            }
            g.fillRect(nextColumn*50, 0, 50, 50);
              
            // now draw the stacks
            for (int j=0; j<cols; j++) {
               if (stacks[j].height()==0) {}
               else {
                  /* Rather than use the iterator,
                   * I cloned each stack, and popped
                   * each block off the clone one by one
                   * until the stack length was 0
                   */
                  Stack stack = (Stack)stacks[j].clone();
                  int i = 0;
                  
                     while(stack.height() > 0)
                     {
                     stackColor = (Integer) stack.pop();
                     switch (stackColor.intValue()) {
                        case 0: g.setColor(Color.blue); break;
                        case 1: g.setColor(Color.red); break;
                        case 2: g.setColor(Color.green); break;
                        case 3: g.setColor(Color.cyan); break;
                     }
                    
                     
                     g.fillRect(j*50, i*50 + 100, 50, 50);
                     i++;
                     }
               }
            }
         }
         g.setColor(Color.white);
         g.drawRect(colMarker*50, 100, 50, 549);
      }
   }
}