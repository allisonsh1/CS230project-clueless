package javafoundations;

import javafoundations.exceptions.*;

/**
*  LinkedStack.java       Java Foundations
*
*  Represents a linked implementation of a stack.
*/

public class LinkedStack<T> implements Stack<T>
{
   private int count;
   private LinearNode<T> top;

   /**
   *  Creates an empty stack using the default capacity.
   */
   public LinkedStack()
   {
      count = 0;
      top = null;
   }

   /**
   *  Removes the element at the top of this stack and returns a
   *  reference to it. Throws an EmptyCollectionException if the
   *  stack contains no elements.
   * 
   * @return the element at the top of the stack
   */
   public T pop() throws EmptyCollectionException
   {
      if (count == 0)
         throw new EmptyCollectionException ("Pop operation failed. "
            + "The stack is empty.");

      T result = top.getElement();
      top = top.getNext();
      count--;

      return result;
   }

   /**
   *  Returns a string representation of this stack.
   * 
   * @return the elements in the stack
   */
   public String toString()
   {
      String result = "<top of stack>\n";
      LinearNode current = top;

      while (current != null)
      {
         result += current.getElement() + "\n";
         current = current.getNext();
      }

      return result + "<bottom of stack>";
   }


   /**  Implement the following methods.
    * NOTE: We have included minimal (incorrect) code so that
    * the compiler will not complain. 
    */
   public void push (T element) { }
   public T peek () throws EmptyCollectionException { return null; }
   public boolean isEmpty() { return false; }
   public int size() { return 0;}
}
