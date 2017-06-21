import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.Stopwatch;
//import edu.princeton.cs.algs4.In;


/*
 * Author: Surui Sun
 * Date: 06/20/2017
 * Compilation: javac Permutation.java
 * Execution: java Permutation < k < input.txt
 * eg: java Permutation 3 queues/permutation8.txt
 * Dependencies: StdOut.java, In.java, StdIn.java, java.util.Iterator
 * Purpose: This program implements a random sampling of k items from a randomized queue
 */
/*
 * Takes a command-line integer k; reads in a sequence of strings from standard input
 * using StdIn.readString(); and prints exactly k of them, uniformly at random. 
 * Print each item from the sequence at most once.
 */

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]); 
        //In in = new In(args[2]);
        RandomizedQueue<String> rqs = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            rqs.enqueue(item);
        }
        if (k > rqs.size()) {
            StdOut.println("The number provided should be smaller than " + rqs.size() + 
                           " print at most " + rqs.size() + " values");
            k = rqs.size();
        }
        // Stopwatch is used for testing purpose
        // Stopwatch sw = new Stopwatch(); 
        // generate random k component from the queue
        for(int i=0; i<k; i++) {
            StdOut.println(rqs.dequeue());
        }
        //StdOut.println("time= " + sw.elapsedTime() + " seconds.");
    }
}
