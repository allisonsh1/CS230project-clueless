package javafoundations;

import javafoundations.exceptions.*;

/*
 * A linked implementation of the Queue Interface.
 * */
public class LinkedQueue<T> implements Queue<T>
{
    private int count;
    private LinearNode<T> front, rear;

    /**
     * Constructor
     * Creates an empty queue.
     */
    public LinkedQueue()
    {
        count = 0;
        front = rear = null;
    }

    /**
     * Adds the specified element to the rear of the queue.
     * 
     * @param The element to be enqueued into the queue
     */ 
    public void enqueue (T element)
    {
        LinearNode<T> node = new LinearNode<T>(element);

        if (count == 0)
            front = node;
        else
            rear.setNext(node);

        rear = node;
        count++;
    }

    /**
     * Removes and returns the element at the front of the queue.
     * 
     * @return the element removed from the front of the queue.
     * @throws EmptyCollectionException if this queue is empty
     */
    public T dequeue () throws EmptyCollectionException 
    {
        if (count == 0)
            throw new EmptyCollectionException ("Dequeue failed. Empty q.");

        T temp = front.getElement();
        front = front.getNext();
        count--;
        return temp;
    }

    /**
     * Returns the element at the front of the queue
     * without removing it.
     * @return the element at the front of the queue
     * @throws EmptyCollectionException if this queue is empty
     */
    public T first () throws EmptyCollectionException 
    {
        if (count == 0)
            throw new EmptyCollectionException ("First failed. Empty q.");
            
        return front.getElement();
    }

    /**
     * Returns true if the queue contains no elements,
     *  false otherwise.
     * 
     * @return true if the queue is empty, false otherwise.
     */
    public boolean isEmpty() 
    {
        return count == 0;
    }

    /**
     * Returns the number of elements in the queue.
     * 
     * the number of elements in the queue
     */
    public int size() 
    {
        return count;
    }

    /**
     * Returns a string representation of the queue.
     * 
     * a string representation of the queue
     */
    public String toString() 
    {
        String result = "<front of queue>\n";
        LinearNode<T> current = front;
        while(current != null)
        {
            result += current.getElement() + "\n";
            current = current.getNext();
        }

        return result + "<rear of queue>";
    }
}
