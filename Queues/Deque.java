import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

/*
 * Author: Surui Sun
 * Date: 06/20/2017
 * Compilation: javac Deque.java
 * Execution: java Deque
 * Dependencies: StdOut.java, java.util.Iterator
 * Purpose: This program implements a double-ended queue (or we can say deque), 
 * so we can add or remove items from either the front or the back.
 */


public class Deque<Item> implements Iterable<Item>{
    
    private int n; // the size of the deque
    private Node first; // the first node in the deque
    private Node last; // the last node in the deque
   
    // data structure to represent each node in the deque
    private class Node {
        private Item item; // value of the node 
        private Node next; // pointer to the next node
        private Node prev; // pointer to the previous node
    }
    
    // constructor an empty deque
    public Deque(){
        n = 0;
        first = null;
        last = null;
    }
    
    
    // is the deque empty?
    public boolean isEmpty(){
        return n==0;
    }
    
    // return the number of items on the deque
    public int size(){
        return n;
    }
    
    // add the item to the front
    public void addFirst(Item item){
        if (item == null) throw new java.lang.NullPointerException("Cannot add a null item"); 
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        if (n == 0) {     
            first.next = null;
            last = first;
        }
        else if (n == 1) {
            first.next = oldfirst;
            oldfirst.prev = first;
            last.prev = first;
        }
        else {
            first.next = oldfirst;
            oldfirst.prev = first;
        }    
        n++;
    }
    
    // add the item to the end
    public void addLast(Item item){
        if (item == null) throw new java.lang.NullPointerException("Cannot add a null item"); 
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (n == 0) {
            last.prev = null;
            first = last;
        }
        else if (n == 1) {
            last.prev = oldlast;
            oldlast.next = last;
            first.next = last;
        }
        else {
            oldlast.next = last;
            last.prev = oldlast;
        }
        n++;
    }
    
    //remove and return the item from the front
    public Item removeFirst(){
        if (isEmpty()) // cannot remove from an empty deque
            throw new java.util.NoSuchElementException("Cannot remove an item from an empty deque");
        
        Item item = first.item;
        if (n == 1){
            first = null;
            last = null;
        } // if there is only 1 item, aftering removeFirst, last should points to null.
        else {
            first = first.next;
            first.prev = null;
        } 
        n--;
        return item;
        
    }
    
    // remove and return the item from the end
    public Item removeLast(){
        if (isEmpty())
            throw new java.util.NoSuchElementException("Cannot remove an item from an empty deque");
        Item item = last.item;
        if (n==1) {
            last = null;
            first = null;
        }// if there is only 1 item, aftering removeLast, first should points to null.
        else {
            last = last.prev;
            last.next = null;
        }  
        n--;
        return item;
    }
    
    //return an iterator over items in order from front to end
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext(){
            return current!= null;
        }
        
        public void remove(){
            throw new java.lang.UnsupportedOperationException("Remove method is prohibited");
        }
        
        public Item next(){
            if (!hasNext()) throw new java.util.NoSuchElementException("No next element in the deque");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    
    //unit testing (optional)
    public static void main(String[] args){
        Deque<String> dq = new Deque<String>();
        dq.addFirst("Here");
        dq.addLast("we");
        StdOut.println("Deque Size: " + dq.size());
        StdOut.println("Removing first item: " + dq.removeFirst());
        dq.addFirst("are");
        //StdOut.println("Deque Size: " + dq.size());
        dq.addLast("riding");
        dq.addLast("the");
        StdOut.println("Deque Size: " + dq.size());
        StdOut.println("Is Deque empty? " + dq.isEmpty());
        StdOut.println("The sequence in the deque is:");
        
        Iterator<String> i = dq.iterator();
        while(i.hasNext()){
            String s = i.next();
            StdOut.println(s);
        }
        //i.remove(); // will throw java.lang.UnsupportedOperationException
    }
    
}
