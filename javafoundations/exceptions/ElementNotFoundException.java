/**
 * ElementNotFoundException.java 
 * To be used when a target element is not present in the collection.
 */
package javafoundations.exceptions;

public class ElementNotFoundException extends RuntimeException
{
  /**
   * Constructor. Creates an exception with the provided message
   * @param The message to be presented when the exception is thrown
   **/
  public ElementNotFoundException (String message)
  {
    super (message);
  }
}
