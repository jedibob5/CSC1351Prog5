package bstreedemo;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * A binary search tree <br>
 * Requires JDK 1.8 for Function
 *
 *
 * @author Duncan, Robert Anderson
 * @param <E> the tree data type
 * @since December 2, 2015
 * @see BSTreeAPI
 */
public class BSTree<E extends Comparable<E>> implements BSTreeAPI<E>
{

    /**
     * the root of this tree
     */
    private Node root;
    
    /**
     * the number of nodes in this tree
     */
    private int size;

    /**
     * A node of a tree stores a data item and references to the child nodes to
     * the left and to the right.
     */
    private class Node {

        /**
         * the data in this node
         */
        public E data;
        /**
         * A reference to the left subtree rooted at this node.
         */
        public Node left;
        /**
         * A reference to the right subtree rooted at this node
         */
        public Node right;
    }

    /**
     * Constructs an empty tree
     */
    public BSTree() {
        root = null;
        size = 0;
    }
    
    /**
     * This method creates a binary search tree with the same
     * structure and contents as the specified binary search tree.
     * @param tree a binary search tree
     */
    public BSTree(BSTree<E> tree)
    {
    	copyTree(tree.root);
    }

    /**
    * An auxiliary method of the copy constructor that inserts the
    * data from the specified node into this tree and recursively
    * inserts the data of the left and right subtrees of this node
    * as the tree is traversed pre-order.
    * @param originalSubtreeRoot a root of a subtree in the
    * original tree.
    */
    private void copyTree(Node originalSubtreeRoot)
    {
    	insert(originalSubtreeRoot.data);
    	if(originalSubtreeRoot.left != null)
    		copyTree(originalSubtreeRoot.left);
    	if(originalSubtreeRoot.right != null)
    		copyTree(originalSubtreeRoot.right);
    }
    
    @Override
    public void trim()
    {
    	if(root != null)
    		trim(root);
    }
    
    /**
    * A recursive auxiliary method for the trim method.
    * @param subtreeRoot a node at which a subtree is rooted
    */
    private void trim(Node subtreeRoot)
    {
    	if(subtreeRoot.left == null && subtreeRoot.right == null)
    		remove(subtreeRoot.data);
    	if(subtreeRoot.left != null)
    		trim(subtreeRoot.left);
    	if(subtreeRoot.right != null)
    		trim(subtreeRoot.right);
    }
    
    @Override
    public ArrayList<String> getPaths() throws BSTreeException
    {
    	if(root == null)
    		throw new BSTreeException("getPaths() called on empty tree.");
    	ArrayList<String> paths = new ArrayList<String>();
    	String pStr = new String();
    	getPaths(root, pStr, paths);
    	return paths;
    }
    
    /**
    * A recursive auxiliary method for the getPath method.
    * @param node a node along the root-to-leaf path
    * @param pStr a string representing a path
    * @param paths an array list whose elements are the
    * root-to-leaf paths in the tree in the format
    ^ n1->n2->n3...nk, where n1 is the root and nk a leaf.
    */
    private void getPaths(Node node, String pStr, ArrayList<String> paths)
    {
    	pStr = pStr + node.data;
    	if(node.left != null || node.right != null)
    		pStr = pStr + "->";
    	if(node.left != null)
    	{
    		getPaths(node.left, pStr, paths);
    	}
    	if(node.right != null)
    	{
    		getPaths(node.right, pStr, paths);
    	}
    	if(node.left == null && node.right == null)
    		paths.add(pStr);
    }
    
    @Override
    public int diameter()
    {
    	if(root == null)
    		return 0;
    	else
    		return diameter(root);
    }

    /**
    * A recursive auxiliary method of the diameter method that
    * gives the diameter of the subtree rooted at the specified node.
    * @param node in the tree
    * @return the diameter of the subtree rooted at the specified node
    */
    private int diameter(Node node)
    {
    	if(node.left == null && node.right == null)
    		return 1;
    	else if(node.left != null && node.right != null)
    	{
    		/*
    		 * Compares the diameter of the current node (1 + maxPath(node.left) + maxPath(node.right)) with the 
    		 * diameters of both its left and right subtrees, returning the maximum of all three. This ensures the
    		 * method will find the longest diameter in the entire tree, instead of just the diameter through the root.
    		 */
    		return Math.max(Math.max(1 + maxPath(node.left) + maxPath(node.right), diameter(node.left)), diameter(node.right));
    	}
    	else if(node.left != null)
    		return 1 + diameter(node.left);
    	else
    		return 1 + diameter(node.right);
    }

    /**
    * Computes the maximum root-to-leaf path of the subtree rooted at
    * the specified node.
    * @param node the root of a subtree
    * @return the number of nodes along the longest path of the subtree
    * rooted at the specified node.
    */
    private int maxPath(Node node)
    {
    	if(node.left != null && node.right != null)
    		return 1 + Math.max(maxPath(node.left), maxPath(node.right));
    	else if(node.left != null)
    		return 1 + maxPath(node.left);
    	else if(node.right != null)
    		return 1 + maxPath(node.right);
    	else
    		return 1;
    }
    
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public void insert(E item) {
        Node newNode = new Node();
        newNode.data = item;
        if (size == 0) {
            root = newNode;
            size++;
        } else {
            Node tmp = root;
            while (true) {
                int d = tmp.data.compareTo(item);
                if (d == 0) { /* Key already exists. (update) */

                    tmp.data = item;
                    return;
                } else if (d > 0) {
                    if (tmp.left == null) { /* If the key is less than tmp */

                        tmp.left = newNode;
                        size++;
                        return;
                    } else { /* continue searching for insertion pt. */

                        tmp = tmp.left;
                    }
                } else {
                    if (tmp.right == null) {/* If the key is greater than tmp */

                        tmp.right = newNode;
                        size++;
                        return;
                    } else { /* continue searching for insertion point*/

                        tmp = tmp.right;
                    }
                }
            }
        }
    }

    @Override
    public boolean inTree(E item) {
        Node tmp;
        if (size == 0) {
            return false;
        }
        /*find where it is */
        tmp = root;
        while (true) {
            int d = tmp.data.compareTo(item);
            if (d == 0) {
                return true;
            } else if (d > 0) {
                if (tmp.left == null) {
                    return false;
                } else /* continue searching */ {
                    tmp = tmp.left;
                }
            } else {
                if (tmp.right == null) {
                    return false;
                } else /* continue searching for insertion pt. */ {
                    tmp = tmp.right;
                }
            }
        }
    }

    @Override
    public void remove(E item) {
        Node nodeptr;
        nodeptr = search(item);
        if (nodeptr != null) {
            remove(nodeptr);
            size--;
        }
    }

    @Override
    public void inorderTraverse(Function func) {
        inorderTraverse(root, func);
    }

    @Override
    public E retrieve(E key) throws BSTreeException {
        Node nodeptr;
        if (size == 0) {
            throw new BSTreeException("Non-empty tree expected on retrieve().");
        }
        nodeptr = search(key);
        if (nodeptr == null) {
            throw new BSTreeException("Existent key expected on retrieve().");
        }
        return nodeptr.data;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * A recursive auxiliary method for the inorderTraver method that
     *
     * @param node a reference to a Node object
     * @param func a function that is applied to the data in each node as the
     * tree is traversed in order.
     */
    private void inorderTraverse(Node node, Function func) {
        if (node != null) {
            inorderTraverse(node.left, func);
            func.apply(node.data);
            inorderTraverse(node.right, func);
        }
    }

    /**
     * An auxiliary method that support the remove method
     *
     * @param node a reference to a Node object in this tree
     */
    private void remove(Node node) {
        E theData;
        Node parent, replacement;
        parent = findParent(node);
        if ((node.left != null) && (node.right != null)) {
            replacement = node.right;
            while (replacement.left != null) {
                replacement = replacement.left;
            }
            theData = replacement.data;
            remove(replacement);
            node.data = theData;
        } else {
            if ((node.left == null) && (node.right == null)) {
                replacement = null;
            } else if (node.left == null) {
                replacement = node.right;
            } else {
                replacement = node.left;
            }
            if (parent == null) {
                root = replacement;
            } else if (parent.left == node) {
                parent.left = replacement;
            } else {
                parent.right = replacement;
            }
        }
    }

    /**
     * An auxiliary method that supports the search method
     *
     * @param key a data key
     * @return a reference to the Node object whose data has the specified key.
     */
    private Node search(E key) {
        Node current = root;
        while (current != null) {
            int d = current.data.compareTo(key);
            if (d == 0) {
                return current;
            } else if (d > 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    /**
     * An auxiliary method that gives a Node reference to the parent node of the
     * specified node
     *
     * @param node a reference to a Node object
     * @return a reference to the parent node of the specified node
     */
    private Node findParent(Node node) {
        Node tmp = root;
        if (tmp == node) {
            return null;
        }
        while (true) {
            assert tmp.data.compareTo(node.data) != 0;
            if (tmp.data.compareTo(node.data) > 0) {
                /* this assert is not needed but just
                 in case there is a bug */
                assert tmp.left != null;
                if (tmp.left == node) {
                    return tmp;
                } else {
                    tmp = tmp.left;
                }
            } else {
                assert tmp.right != null;
                if (tmp.right == node) {
                    return tmp;
                } else {
                    tmp = tmp.right;
                }
            }
        }
    }
    
    @Override
    public void postorderTraverse(Function func)
    {
        postorderTraverse(root, func);
    }
    
    @Override
    public void preorderTraverse(Function func)
    {
        preorderTraverse(root, func);
    }
    
    /**
     * traverses the binary search tree in postorder
     * and apply the specify function to the data in each node visited
     * @param node node visited
     * @param func function applied
     */
    private void postorderTraverse(Node node, Function func)
    {
        if (node != null)
        {
            postorderTraverse(node.left, func);
            postorderTraverse(node.right, func);
            func.apply(node.data);
        }
    }
    
    /**
     * traverses the binary search tree in preorder
     * and apply the specify function to the data in each node visited
     * @param node node visited
     * @param func function applied
     */
    private void preorderTraverse(Node node, Function func)
    {
        if(node != null)
        {
            func.apply(node.data);
            preorderTraverse(node.left, func);
            preorderTraverse(node.right, func);
        }
    }
    
    /**
     * gives the number of edges in the tree
     * @return number of edges
     */
    public long countEdges()
    {
        return size-1;
    }
    
    /**
     * finds the maximum value of the list.
     * @return the maximum value in the list
     * @throws BSTreeException when the tree is empty
     */
    public E max() throws BSTreeException
    {
        if(size == 0)
            throw new BSTreeException("Binary search tree is empty");
        Node traverser = root;
        while(traverser.right != null)
        {
            traverser = traverser.right;
        }
        return traverser.data;
    }
    
    /**
     * finds the minimum value of the list.
     * @return the minimum value in the list
     * @throws BSTreeException when the tree is empty
     */
    public E min() throws BSTreeException
    {
        if(size == 0)
            throw new BSTreeException("Binary search tree is empty");
        Node traverser = root;
        while(traverser.left != null)
        {
            traverser = traverser.left;
        }
        return traverser.data;
    }
    
    /**
     * returns an array list containing the elements of the tree
     * in ascending order or an empty array list if the tree is empty
     * @return 
     */
    public ArrayList<E> sort()
    {
        ArrayList<E> list = new ArrayList<>();
        Function<E,Boolean> f = x -> list.add(x);
        inorderTraverse(f);
        return list;
    }
    
    /**
     * Wrapper method for determining whether binary search trees are equivalent
     * @param bstree the tree for which to compare equivalence
     * @return true if equivalent, false otherwise
     */
    public boolean equals(BSTree bstree)
    {
        if(this.size != bstree.size)
            return false;
        return equals(root, bstree.root);
    }
    
    /**
     * Determines whether two binary search trees are equivalent.
     * @param subtree1 the left subtree of the nodes being compared
     * @param subtree2 the right subtree of the nodes being compared
     * @return true if equivalent, false otherwise
     */
    private boolean equals(Node subtree1, Node subtree2)
    {
        if(subtree1 == null && subtree2 == null)
            return true;
        else if(subtree1.data != subtree2.data)
            return false;
        equals(subtree1.left, subtree2.left);
        equals(subtree1.right, subtree2.right);
        return false;
    }
}
