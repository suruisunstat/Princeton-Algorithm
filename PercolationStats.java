import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;


/*
 * Author: Surui Sun
 * Date: 06/18/2017
 * Compilation: javac PercolationStats.java
 * Execution: java PercolationStats < n < trials
 * Dependencies: StdOut.java, StdRandom.java, StdStats.java, Stopwatch.java
 * Purpose: This program use Monte Carlo method to compute and estimate the threshold when the grid system
 * percolates.
 */


public class PercolationStats{
    private double[] proportion; // an array to store the threshold in each trail
    private int n; // grid row length
    private int trails; // number of trails
    
    // perform trials independent experiments on an n-by-n grid
    /*
    * Constructor:
    * if size is non-positive then throw Exception Error
    */
    public PercolationStats(int n, int trails){
        if(n<=0 || trails <= 0){
            throw new IllegalArgumentException("size is illegal");
        }
        this.n = n;
        this.trails = trails;
        proportion = new double[trails];
        for(int i=0; i<trails; i++){
            Percolation p = new Percolation(n);
            //PercolationSlow p = new PercolationSlow(n);
            while(!p.percolates()){
                int row = StdRandom.uniform(1,n+1);
                int col = StdRandom.uniform(1,n+1);
                p.open(row,col);
            }
            double gridsize = (double) n*n;
            proportion[i] = p.numberOfOpenSites()/gridsize;
        }
           
    }    
    
    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(proportion);
    }      
    
    // sample standard deviation of percolation threshold
    public double stddev(){
        if (proportion.length == 1) return Double.NaN; // If number of trails is 1, then return Double.NaN
        else return StdStats.stddev(proportion);
    }                      
    
     // low  endpoint of 95% confidence interval
    public double confidenceLo(){
        double mu = mean();
        double sd = stddev();
        return mu - 1.96 * sd/Math.sqrt(trails);
    }      
    
     // high endpoint of 95% confidence interval
    public double confidenceHi(){
        double mu = mean();
        double sd = stddev();
        return mu + 1.96 * sd/Math.sqrt(trails);
    }   
    
    
     // test client (described below)
    public static void main(String[] args){
        //int n = StdIn.readInt();
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        //int t = StdIn.readInt();
        //StdOut.println("n is: " + n);
        //StdOut.println("t is: " + t);
        Stopwatch sw = new Stopwatch(); // time calculator
        PercolationStats p = new PercolationStats(n,t);
        StdOut.println("mean                    = " + p.mean());
        StdOut.println("stddev                  = " +  p.stddev());
        StdOut.println("95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
        StdOut.println("time                    = " + sw.elapsedTime() + " seconds.");
    }       
}