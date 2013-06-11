import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/****
 *
 * Class FileMenu is the "File" pulldown menu for the scheduler tool
 *
 */
public class FileMenu extends JMenu {

    public FileMenu() {

        super("File");

        addNewItem();
        addSaveItem();
        addExitItem();
    }

    protected void addNewItem() {
        add(new JMenuItem("New Schedule")).addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {     
                    System.out.println("File->New Schedule selected.");
                }
            }
        );
    }

    protected void addSaveItem() {

        add(new JMenuItem("Save Schedule")).addActionListener(
        	new ActionListener() {
            	public void actionPerformed(ActionEvent e) {         
                	System.out.println("File->Save Schedule selected.");
            	}
            }
        );
    }

    protected void addExitItem() {
        add(new JMenuItem("Exit")).addActionListener(
            new ActionListener() {
        		System.out.println("File->Exit selected.");
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            }
        );
    }

}