package javafoundations;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

import javafoundations.exceptions.*;

public class CircularArrayQueue<T> implements Queue<T> {
   private final int DEFAULT_CAPACITY = 10;
   private int front, rear, count;
   private T[] queue;

   //-----------------------------------------------------------------
   //  Creates an empty queue using the default capacity.
   //-----------------------------------------------------------------
   public CircularArrayQueue() {
      front = rear = count = 0;
      queue = (T[]) (new Object[DEFAULT_CAPACITY]);
   }

   //-----------------------------------------------------------------
   //  Adds the specified element to the rear of this queue, expanding
   //  the capacity of the queue array if necessary.
   //-----------------------------------------------------------------
   public void enqueue (T element) {
      if (count == queue.length)   expandCapacity();

      queue[rear] = element;
      rear = (rear+1) % queue.length;
      count++;
   }

   //-----------------------------------------------------------------
   //  Creates a new array to store the contents of this queue with
   //  twice the capacity of the old one.
   //-----------------------------------------------------------------
   public void expandCapacity() {
      T[] larger = (T[])(new Object[queue.length*2]);

      for (int index=0; index < count; index++)
         larger[index] = queue[(front+index) % queue.length];

      front = 0;
      rear = count;
      queue = larger;
   }
   
   public T dequeue() throws EmptyCollectionException {
      if (isEmpty()) 
         throw new EmptyCollectionException("queue");
   
      T result = queue[front];
      queue[front] = null;
      front = (front + 1) % queue.length;
      count--;
   
      return result;
   }

   public T first() throws EmptyCollectionException {
      if (isEmpty())
         throw new EmptyCollectionException("queue");
   
      return queue[front];
   }
   /**
    * Finds the last
    * @return
    * @throws EmptyCollectionException
    */
   public T last() throws EmptyCollectionException {
      if (isEmpty())
         throw new EmptyCollectionException("queue");
   
      return queue[rear];
   }

   public int size() {
      return count;
   }

   public boolean isEmpty() {
      return count == 0;
   }

   @Override
   public boolean contains(Object o) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'contains'");
   }

   @Override
   public Iterator<T> iterator() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'iterator'");
   }

   @Override
   public Object[] toArray() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toArray'");
   }

   @Override
   public <T> T[] toArray(T[] a) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toArray'");
   }

   @Override
   public boolean remove(Object o) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'remove'");
   }

   @Override
   public boolean containsAll(Collection<?> c) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
   }

   @Override
   public boolean addAll(Collection<? extends T> c) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addAll'");
   }

   @Override
   public boolean removeAll(Collection<?> c) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
   }

   @Override
   public boolean retainAll(Collection<?> c) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
   }

   @Override
   public void clear() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'clear'");
   }

   @Override
   public boolean add(T e) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'add'");
   }

   @Override
   public boolean offer(T e) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'offer'");
   }

   @Override
   public T remove() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'remove'");
   }

   @Override
   public T poll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'poll'");
   }

   @Override
   public T element() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'element'");
   }

   @Override
   public T peek() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'peek'");
   }
}

   