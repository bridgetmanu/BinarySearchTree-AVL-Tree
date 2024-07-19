package BSM180003_;


public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    static class Entry<T> extends BinarySearchTree.Entry<T> {
        int height;
        Entry <T> parent;

        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            this.height = 0;
            this.parent = parent;
        }
    }

    AVLTree() {
        super();
    }

    // TO DO
    /**
     * TO DO
     * @param x
     * if adding was false, return false
     * else call constructor
     * call balance
     * @return true
     */

    @Override
   public boolean add(T x) {

        boolean adding = super.add(x);
        if (!adding){
            return false;
        } else{
            Entry<T> t = new Entry<>(x, null, null);
            balance(t);
        }
        return true;
    }




    //Optional. Complete for extra credit

    @Override
    public T remove(T x) {

        return super.remove(x);
    }


    /**
     * height method
     * @param w
     * if the node is empty, assign -1
     * else add +1 to the current height
     * @return height
     */

    public int height(Entry <T> x) { // returns height of the tree
        if (x == null){
            return -1;
        } else{
            return Math.max(height((Entry<T>)x.left), height((Entry<T>) x.right)) + 1;
        }
    }
    /**
     * left rotation method
     * @param w
     * if tree is right heavy, it rotates left
     * it updates the height and pointers of the parent nodes
     */

    public boolean rotateleft(Entry<T> w) {
       if (w != null && w.right != null) { // left rotation
           Entry<T> q = (Entry<T>) w.right; //right child of w is assigned as q
           w.right = q.left; // if right child has two children, assign the left child of w.right as the root node
           q.left = w; //w becomes left child of q
           q.parent = w.parent;
           w.parent = q;
           return true;
       }
       return false;
    }
    /**
     * right rotation function
     * @param w
     * if tree is left heavy, it rotates right
     * it updates the height and pointers of the parent nodes
     */
    public boolean rotateright(Entry <T> w) {
        if (w != null && w.left != null){ // right rotation
            Entry <T> q = (Entry<T>) w.left; //The left child of w is assigned as q
            w.left = q.right; // left child of w is the right child of q
            q.right = w; // right child of q is w
            q.parent = w.parent;
            w.parent = q;
            return true;
        }
        return false;
    }

    /**
     * Helper function for the add method
     * @param w
     * calculates the height of the tree
     * if tree is right heavy it rotates left
     * if tree is right heavy it rotates right
     * it updates the height and pointers of the parent nodes
     */

    public void balance(Entry <T> w){
        int lheight = height((Entry <T>) w.left); // stores height of left node
        int rheight = height((Entry <T>) w.right); // stores height of right node
        int tree = lheight - rheight;
        if (tree > 1){
            rotateleft(w);
            w.height = Math.max(lheight, height((Entry<T>) w.right));
        } else if (tree < -1){
            rotateright(w);
            w.height = Math.max(height((Entry<T>) w.left), rheight);
        }
        while(w != null){
            w.height = Math.max(height((Entry<T>)w.left), height((Entry<T>) w.right)) + 1;
            int htree = height((Entry<T>) w.left) - height((Entry<T>) w.right);
            if (Math.abs(htree) <= 1) {
                break;
            }
            w = w.parent;
        }
    }


    /**
     * TO DO
     * verify if the tree is a valid AVL tree, that satisfies
     * all conditions of BST, and the balancing conditions of AVL trees.
     * In addition, do not trust the height value stored at the nodes, and
     * heights of nodes have to be verified to be correct.  Make your code
     * as efficient as possible. HINT: Look at the bottom-up solution to verify BST
     */
    boolean verify() {
        if (size == 0){
            return true;
        } else {
            Entry<T> t = new Entry<>(root.element, null, null);
            return helper(t); // recursively check for
        }

    }
    /**
     * Helper function for the verify method
     * @param x
     * calculates the height of the tree
     * returns false if left or right element is >= 0
     * @return true if the absolute difference in height is <= 1
     */
    boolean helper(Entry<T> x){
        int lheight = 0; // stores height of left node
        int rheight = 0; // stores height of right node
        if(x.left != null) { //if left child exist,
            if(!helper((Entry<T>) x.left) || x.left.element.compareTo(x.element) >= 0)
            {
                return false;
            }
            lheight = height((Entry<T>) x.left);
        } else if(x.right != null) { //if right child exist,
            if(!helper((Entry<T>) x.right) || x.right.element.compareTo(x.element) <= 0){
                return false;
            }
            rheight = height((Entry<T>) x.right);

        }
        return Math.abs(lheight - rheight) <= 1;
    }

}