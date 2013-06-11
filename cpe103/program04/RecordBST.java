// Garrett Milster
// Program 04
// CSC103

public class RecordBST
{

    private class Node
    {
        ItemRecord item;
        Node left;
        Node right;
    }
   
    //root of tree
    private Node root;

    public RecordBST()
    {
        root = null;
    }
   
    public void insert(ItemRecord item)
    {
        root = insert(item, root);
    }
   
    private Node insert(ItemRecord item, Node treeroot)
    {
        if(treeroot == null)
        {
            treeroot = new Node();
            treeroot.item = item;
        }
        else
        {
            // If the string is less than the tree's root
            if(item.token.compareToIgnoreCase(treeroot.item.token) < 0)
            {
               // if it less than the tree current root variable, check if the left node is null, 
               // if it is null, recursively add it to the tree
               // otherwise, make the left node the current root and try again.
                if(treeroot.left == null)
                    treeroot.left = insert(item, treeroot.left);
                else
                    insert(item, treeroot.left);
            }
            // If the string is greater than the three's root
            else
            {
               // if it less than the tree current root variable, check if the right node is null, 
               // if it is null, recursively add it to the tree
               // otherwise, make the right node the current root and try again.
                if(treeroot.right == null)
                    treeroot.right = insert(item, treeroot.right);
                else
                    insert(item, treeroot.right);
            }
        }
        return treeroot;
    }

   
    public int find(ItemRecord item)
    {
        return find(item, root);
    }

    private int find(ItemRecord item, Node treeroot)
    {
        if(isEmpty())
            return 0;
        else
        {
           // if the current node is equal to the ItemRecord's string, add to the tally.
            if(item.token.compareToIgnoreCase(treeroot.item.token) == 0)
            {
                treeroot.item.tally++;
            }
            else
            {
               // Otherwise, check if the item records string is less than or greater than the current node,
               // and keep searching if the left or right is not null.
                if(item.token.compareToIgnoreCase(treeroot.item.token) < 0)
                {
                    if(treeroot.left != null)
                        return find(item, treeroot.left);
                }
                else
                {
                    if(treeroot.right != null)
                       return find(item, treeroot.right);
                }
            }
        }
        
        return treeroot.item.tally;
    }
   
    public boolean isEmpty()
    {
        return root == null;
    }

}
