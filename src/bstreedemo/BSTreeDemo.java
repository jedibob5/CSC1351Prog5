package bstreedemo;

import java.util.function.Function;
import java.util.ArrayList;
import java.io.PrintStream;
/**
 * Demonstration of the binary search tree class implemented as BSTree. <br>
 * CSC 1351, Project #5
 * @author Robert Anderson
 * @since December 2, 2015
 * @see BSTree
 */
public class BSTreeDemo
{

    public static void main(String[] args) throws BSTreeException
    {
        BSTree<Integer> tree1 = new BSTree<>();
        tree1.insert(6);
        tree1.insert(2);
        tree1.insert(1);
        tree1.insert(4);
        tree1.insert(3);
        tree1.insert(5);
        tree1.insert(7);
        tree1.insert(8);
        tree1.insert(12);
        tree1.insert(13);
        tree1.insert(10);
        tree1.insert(9);
        tree1.insert(11);
        
        System.out.println("The root-to-leaf paths in tree1 are:");
        System.out.println(tree1.getPaths().toString());
        
        BSTree<Integer> tree2 = new BSTree<>();
        tree2.insert(13);
        tree2.insert(14);
        tree2.insert(15);
        tree2.insert(7);
        tree2.insert(2);
        tree2.insert(1);
        tree2.insert(6);
        tree2.insert(4);
        tree2.insert(3);
        tree2.insert(5);
        tree2.insert(8);
        tree2.insert(10);
        tree2.insert(9);
        tree2.insert(11);
        tree2.insert(12);
        
        System.out.println("The root-to-leaf paths in tree2 are:");
        System.out.println(tree2.getPaths().toString());
        
        System.out.printf("%nThe diameters of tree1 and tree2 are %d and %d, respectively.%n", tree1.diameter(), tree2.diameter());
        
        BSTree<Integer> tree3 = new BSTree<Integer>(tree2);
        
        System.out.println("The root-to-leaf paths in tree3 are:");
        System.out.println(tree3.getPaths().toString());
        System.out.printf("The diameter of tree3 is %d and its size is %d.%n%n", tree3.diameter(), tree3.size());
        
        tree3.trim();
        System.out.println("The root-to-leaf paths in tree3 are now:");
        System.out.println(tree3.getPaths().toString());
        System.out.printf("The diameter of tree3 is now %d and its size is now %d.%n", tree3.diameter(), tree3.size());
    }
    
}
