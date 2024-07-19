package BSM180003_;

import java.io.File;
import java.util.Comparator;
import java.io.FileNotFoundException;
import java.util.*;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T>
    {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
    }

    Entry<T> root;
    int size;
    // define stack
    Deque <Entry <T>> press;


    public BinarySearchTree() {
        root = null;
        size = 0;
    }



    /**
     * @param x
     * @param t
     * helper method to find a node
     * @return t when element is found or if elemen
     */

    public Entry <T> find(Entry <T> t,T x) {
        press = new ArrayDeque<>();
        if (t == null || t.element == null) {
            return null;
        } else{
            while(true) {
                int dess = x.compareTo(t.element);
                if (dess < 0) {
                    if (t.left == null) {
                        break;
                    }
                    press.push(t);
                    t = t.left;
                }
                else if (dess == 0) {
                    break;
                }
                else if (dess > 0) {
                    if (t.right == null){
                        break;
                    }
                    press.push(t);
                    t = t.right;
                }
            }
            return t;
        }
    }

    /**
     * TO DO: Is x contained in tree?
     * @param x
     * when t is not empty and element is equal to x, return true
     * @return  false if t is null or t.element is not equal to x
     */

    public boolean contains(T x) {
        Entry <T> t = find(root, x);
        if (t == null){
            return false;
        } else{
            if (t.element.compareTo(x) == 0){
                return true;
            }
            return false;
        }


    }


    /** TO DO: Add x to tree.
     * @param x
     *  create a new root node if tree is empty then increase size
     *  If tree contains a node with same key, replace element by x.
     *  If x < node, create a new left child
     *  If x > node, create a new right child
     *  Then increase size
     *  Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
        if (size == 0) {
             root = new Entry(x, null, null);
             size ++;
             return true;
        } else {
            Entry <T> t = find(root, x);
            if (t!= null && x.compareTo(t.element) == 0) {
                t.element = x;
                return false;
            } else {
                if (t != null){
                    int fess = x.compareTo(t.element); //changing from x -> t to t -> x
                    if (fess < 0) {
                        t.left = new Entry(x, null, null);
                    } else {
                        if (fess > 0){
                            t.right = new Entry(x, null, null);
                        }
                    }
                    size ++;
                }

            }
            return true;
        }
    }

    /** TO DO: Remove x from tree.
     * @param x
     * @return null if the tree is empty
     * call the find method to locate x
     * if x has a child or children, splice it
     *  Return x if found, otherwise return null
     */
    public T remove(T x) {

        if (size == 0) {
            return null;
        }
        Entry <T> t = find(root, x);
        if (t != null) {
            if (x.compareTo(t.element) != 0){
                return null;
            }
            else {
                if (t.left == null || t.right == null){ // if left or right child is empty, splice
                    splice(t);
                } else { // if both children exist
                    press.push(t);
                    Entry <T> minR = find(t.right, x); // find the minimum right node
                    t.element = minR.element;
                    splice(minR);
                }
                size --;
            }
        }
        return x;
    }


    /** TO DO: splice x from tree.
     * peek at the parent of x
     *
     * if parent is null, make child the root
     *
     */

    public void splice(Entry t){
        Entry <T> p = press.peek();
        Entry <T> c = (t.left == null ? t.right : t.left);
        if(p == null){
            root = c;
        } else if (p.left == t){
            p.left = c;
        } else{
            p.right = c;
        }
    }




// Start of Optional problems

    /** Optional problem : Iterate elements in sorted order of keys
     Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator()
    {
        return null;
    }

    // Optional problem





    public static void main(String[] args) throws FileNotFoundException {
        BinarySearchTree<Long> bst = new BinarySearchTree<>();
        Scanner sc;
        if (args.length > 0) {
            File file = new File(args[0]);
            sc = new Scanner(file);
        } else {
            sc = new Scanner(System.in);
        }
        String operation = "";
        long operand = 0;
        int modValue = 999983;
        long result = 0;
        // Initialize the timer
        Timer timer = new Timer();

        while (!((operation = sc.next()).equals("End"))) {
            switch (operation) {
                case "Add": {
                    operand = sc.nextInt();
                    if (bst.add(operand)) {
                        result = (result + 1) % modValue;
                        //System.out.println(operand);
                    }
                    break;
                }
                case "Remove": {
                    operand = sc.nextInt();
                    if (bst.remove(operand) != null) {
                        //System.out.println(operand);
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Contains":
                {
                    operand = sc.nextInt();
                    if (bst.contains(operand)) {
                        //System.out.println(operand);
                        result = (result + 1) % modValue;
                    }
                    break;
                }
            }
        }

        // End Time
        timer.end();

        System.out.println(result);
        System.out.println(timer);
    }


    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }


}




