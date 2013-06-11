import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/****
 *
 * Class DataMenu is the "Data" pulldown menu in the scheduler tool.
 */
public class DataMenu extends JMenu {

    public DataeMenu() {

        super("Data");

        addAddCourseItem();
        addEditCourseItem();
		addDeleteCourseItem();
        add(new JSeparator());

        addAddRoomItem();
        addEditRoomItem();
		addDeleteRoomItem();
        add(new JSeparator());

        addAddInstructorItem();
        addEditInstructorItem();
		addDeleteInstructorItem();
    }

    protected void addAddCourseItem() {

        /*
         * Use the standard menu item pattern for the item.
         */
        add(new JMenuItem("Add Course")).addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Data->Add Course selected.");
                }
            }
        );
    }

    protected void addEditCourseItem() {

        /*
         * Use the standard menu item pattern for the item.
         */
        add(new JMenuItem("Edit Course")).addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Data->Edit Course selected.");
                }
            }
        );
    }

    protected void addDeleteCourseItem() {

        /*
         * Use the standard menu item pattern for the item.
         */
        add(new JMenuItem("Delete Course")).addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Data->Delete Course selected.");
                }
            }
        );
    }

    protected void addAddRoomItem() {

        /*
         * Use the standard menu item pattern for the item.
         */
        add(new JMenuItem("Add Room")).addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Data->Add Room selected.");
                }
            }
        );
    }

    protected void addEditRoomItem() {

        /*
         * Use the standard menu item pattern for the item.
         */
        add(new JMenuItem("Edit Room")).addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Data->Edit Room selected.");
                }
            }
        );
    }

    protected void addDeleteRoomItem() {

        /*
         * Use the standard menu item pattern for the item.
         */
        add(new JMenuItem("Delete Room")).addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Data->Delete Room selected.");
                }
            }
        );
    }
    protected void addAddInstructorItem() {

        /*
         * Use the standard menu item pattern for the item.
         */
        add(new JMenuItem("Add Instructor")).addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Data->Add Instructor selected.");
                }
            }
        );
    }

    protected void addEditInstructorItem() {

        /*
         * Use the standard menu item pattern for the item.
         */
        add(new JMenuItem("Edit Instructor")).addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Data->Edit Instructor selected.");
                }
            }
        );
    }

    protected void addDeleteInstructorItem() {

        /*
         * Use the standard menu item pattern for the item.
         */
        add(new JMenuItem("Delete Instructor")).addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Data->Delete Instructor selected.");
                }
            }
        );
    }
}