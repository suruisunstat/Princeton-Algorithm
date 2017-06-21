import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

/*
 * Author: Surui Sun
 * Date: 06/20/2017
 * Compilation: javac RandomizedQueue.java
 * Execution: java RandomizedQueue
 * Dependencies: StdOut.java, StdRandom.java, java.util.Iterator
 * Purpose: This program implements a randomized queue, which is similar to a 
 * stack or queue, except that the item removed is chosen uniformly at random 
 * from items in the data structure.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private int n; // number of items in the randomized queue
    private Item[] a; // an array to store the items, can be resized
    
    // resize the array a whenever we need more space or we don't want to waste
    // space.
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i=0; i<n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
    
    
    
    // construct an empty randomized queue
    public RandomizedQueue(){
        a = (Item[]) new Object[2];
        n = 0;
    }
    
    // is the queue empty?
    public boolean isEmpty(){
        return n == 0;
    } 
    
    // return the number of items on the queue
    public int size(){
        return n;
    }  
    
    // add the item
    public void enqueue(Item item){ 
        if (item == null)
            throw new java.lang.NullPointerException("Cannot add a null item"); 
        if (a.length == n) {
            resize(2*n);
        }
        a[n++] = item;
    }          
    
    // remove and return a random item
    public Item dequeue(){
        if (isEmpty()) 
            throw new java.util.NoSuchElementException("cannot remove item from an empty queue");
        int r = StdRandom.uniform(0,n);
        Item item = a[r];
        
        // swap a[n-1] with a[r], and make a[n-1] = null
        a[r] = a[n-1];
        a[n-1] = null;      
        n--;
        if (a.length/4 == n & n > 0) {
            resize(a.length/2);
        }
        return item;
    }  
    
    // return (but do not remove) a random item
    public Item sample(){
        if (n == 0) 
            throw new java.util.NoSuchElementException("cannot sample from an empty randomized queue");
        int r = StdRandom.uniform(0,n);
        return a[r];
    } 
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomizedArrayIterator();
    } 
    
    private class RandomizedArrayIterator implements Iterator<Item> {
        private int i;
        private Item[] shuffle;
        
        // exchange two items a[k] and a[r] in an array a
        private void exch(int k,int r, Item[] a) {
            Item temp = a[k];
            a[k] = a[r];
            a[r] = temp;
        }
        
        // each iterator will shuffle the randomized queue array independently
        public RandomizedArrayIterator() {
            i = n;
            shuffle = (Item[]) new Object[n];
            for(int k=0;k<n;k++) {
                shuffle[k] = a[k];
            }
            
            for(int k=0;k<n;k++) {
                int r = StdRandom.uniform(0,k+1);
                exch(k,r,shuffle);
            }
        }
        
        public boolean hasNext() {
            return i>0;
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException("Remove method is prohibited");
        }
        
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("No next element in the randomizedQueue");
            i--;
            return shuffle[i];
            
        }
        
    }
    
    // unit testing (optional)
    public static void main(String[] args){
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        String[] sList = new String[]{"Take","me","to","your","heart","please"};
        for(int i=0; i<sList.length; i++) {
            rq.enqueue(sList[i]);
        }
        StdOut.println("The size of the randomizedQueue is: " + rq.size());
        StdOut.println("Is the randomizedQueue empty? " + rq.isEmpty());
        StdOut.println("Remove item: " + rq.dequeue());
        StdOut.println("After removing the size of the randomizedQueue is: " + rq.size());
        StdOut.println("Remove item: " + rq.dequeue());
        
        /*for(String s: rq) {
            StdOut.println(s);
        }*/
        
        // Iterator one
        Iterator<String> iFirst = rq.iterator();
        while(iFirst.hasNext()){
            String s = iFirst.next();
            StdOut.println(s);
        }
        
        // Iterator two
        Iterator<String> iSecond = rq.iterator();
        while(iSecond.hasNext()){
            String s = iSecond.next();
            StdOut.println(s);
        }
    }   
}

