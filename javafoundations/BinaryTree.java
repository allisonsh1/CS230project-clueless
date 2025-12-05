package javafoundations;

import java.util.Iterator;

/**
 * An Interface for a binary tree collection.
 * 
 */
public interface BinaryTree<T> extends Iterable<T>
{
    /**
     * Returns the element stored in the root of the tree.
     * 
     * @return the element stored in the root of the tree.
     */
    public T getRootElement();

    /**
     * Returns the left subtree of the tree.
     * 
     * @return the left subtree of the tree.
     */
    public BinaryTree<T> getLeft(); 

    /**
     * Returns the right subtree of the root.
     * 
     * @return the right subtree of the root.
     */
    public BinaryTree<T> getRight();

    /**
     * Returns true if the binary tree contains an element that
     * matches the specified element and false otherwise.
     * 
     * @param the element to look for in the tree
     * 
     * @return true if the binary tree contains the specified element,
     * false otherwise
     */
    public boolean contains (T target);

    /**
     * Returns the element in the tree matching
     * the specified target.
     * 
     * @param the element to look for in the tree
     * @return the element in the tree matching
     * the specified element, null if not such element exists 
     * in the tree
     */
    public T find (T target);

    /**
     * Returns true if the binary tree contains no elements, and
     * false otherwise.
     * 
     * @return true if the tree has no nodes, false otherwise
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in this binary tree.
     * 
     * @return the number of elements in this binary tree.
     */
    public int size();

    /**
     * Returns a string representation of the binary tree.
     * 
     * @return a string representation of the binary tree.
     */
    public String toString();

    /**
     * Returns a preorder traversal on the binary tree.
     * 
     * @return an iterator containing the elements 
     * of this tree visited during an preorder traversal
     */
    public Iterator<T> preorder();

    /**
     * Returns an inorder traversal on the binary tree.
     * 
     * @return an iterator containing the elements 
     * of this tree visited during an inorder traversal
     */
    public Iterator<T> inorder();

    /**
     * Returns an postorder traversal on the binary tree.
     *@return an iterator containing the elements 
     * of this tree visited during an postorder traversal
     */
    public Iterator<T> postorder();

    /**
     * Returns a level order traversal on the binary tree.
     * 
     * @return an iterator containing the elements 
     * of this tree visited during a level order traversal
     */
    public Iterator<T> levelorder();
}
