package javafoundations;

/**
*  LinearNode.java       Java Foundations
*
*  Represents a node in a linked list.
*/
public class LinearNode<T>
{
   private LinearNode<T> next;
   private T element;

   /**
   *  Creates an empty node.
   */
   public LinearNode()
   {
      next = null;
      element = null;
   }

   /**
   *  Creates a node storing the specified element.
   */
   public LinearNode (T elem)
   {
      next = null;
      element = elem;
   }

   /**
   *  Returns the node that follows this one.
   * 
   * @return the next node
   */
   public LinearNode<T> getNext()
   {
      return next;
   }

   /**
   *  Sets the node that follows this one.
   * @param node the new next node
   */
   public void setNext (LinearNode<T> node)
   {
      next = node;
   }

   /**
   *  Returns the element stored in this node.
   * 
   * @return the element stored in the node
   */
   public T getElement()
   {
      return element;
   }

   /**
   *  Sets the element stored in this node.
   * @param elem the element to be stored in the node
   */
   public void setElement (T elem)
   {
      element = elem;
   }
}
