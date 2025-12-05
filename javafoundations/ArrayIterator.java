package javafoundations;

import java.util.*;

/**
 * Represents an iterator over the elements of a collection.
 * 
 */
public class ArrayIterator<T> implements Iterator<T>
{
    private int DEFAULT_CAPACITY = 10;
    private int count;    // the number of elements in the iterator
    private int current;  // the current position in the iteration
    private T[] items;    // the iterator's storage for elements

    /**
     * Sets up an empty iterator.
     * 
     */

    public ArrayIterator()
    {
        items = (T[]) (new Object[DEFAULT_CAPACITY]);
        count = 0;
        current = 0;
    }

    /**
     * Adds the specified item to this iterator.
     * 
     * @param the item to be added to the iterator
     */

    public void add (T item)
    {
        if (count == items.length)
            expandCapacity();

        items[count] = item;
        count++;
    }

    /**
     * Returns true if the iterator has at least one more element to
     * deliver.
     * 
     * @return true if there are more elements in this iterator,
     * false otherwise
     */
    public boolean hasNext()
    {
        return (current < count);
    }

    /**
     * Returns the next element in the iteration.
     * If there are no more elements in this iteration,
     * a NoSuchElementException is thrown.
     * 
     * @return the next element in the iteration.
     * 
     * @throws NoSuchElementException if there are no more 
     * elements in this iteration
     */
    public T next()
    {
        if (! hasNext())
            throw new NoSuchElementException();

        current++;

        return items[current - 1];
    }

    /**
     * The remove operation is not supported in this collection.
     * 
     * @throws UnsupportedOperationException always!
     */
    public void remove() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Expands the capacity of the underlying array
     * 
     */
    private void expandCapacity()
    {
        T[] larger = (T []) (new Object[items.length*2]);

        int location = 0;
        for (T element : items)
            larger[location++] = element;

        items = larger;
    }
}
