import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
    
/*
 * Author: Surui Sun
 * Date: 06/18/2017
 * Compilation: javac Percolation.java
 * Execution: java Percolation
 * Dependencies: StdOut.java
 * Purpose: This program implements a n by n grid which are blocked at the beginning, and then
 * open some sites, we can check whether a site is open, is full (which means there is a path from top 
 * to this site), and whether the grid is percolated (which means there is one path from top to bottom).
 * 
 * 
 
 
 */

public class Percolation {
    
    private int[][] grid; // the 2D array to determine whether the sites are open, 0 means block and 1 means open
    private int sideSize; // the length of row 
    private WeightedQuickUnionUF uf; // the Weighted quick union object to determine percolation
    private int numberOfOpenSites; // number of open sites in the n*n grids
    private WeightedQuickUnionUF ufFull; // the Weighted quick union object to determine if the site is full
    
    /*convert row and column number into 1D index in UnionFind
     * 
     */
    private int xyTo1D(int row, int col){
        validate(row, col); // check if the row and col value is valid
        return (row-1)* sideSize + col-1; 
    }
    /*
     * Check if the row and column value is valid in the grid
     */
    private void validate(int p,int q){
        int n = sideSize;
        if(p<1 || p>n || q<1 || q>n){
            throw new IndexOutOfBoundsException("row or column index is not between 1 and " + n);
        }
    }
    
    /*
     * Constructor: create n-by-n grid, with all sites blocked
     */
    // 
    // blocked: 0
    // opened: 1
    public Percolation(int n){
        if (n <= 0) throw new IllegalArgumentException("size must be positive");
        grid = new int[n][n]; // by default, prefilled with zeros
        sideSize = n;
        uf = new WeightedQuickUnionUF(sideSize*sideSize+2);
        ufFull = new WeightedQuickUnionUF(sideSize*sideSize+2);
        numberOfOpenSites = 0;
    } 
    
    /* 
     * open site (row, col) if it is not open already
     * 
     */
    public void open(int row, int col){
        validate(row,col);
        int i = row - 1; 
        int j = col - 1;
        int index = xyTo1D(row,col);
        int n = sideSize;
        if (grid[i][j] == 1) return;
        else{
            grid[i][j] = 1;
            // check the neighboring
            if(j-1>=0 && grid[i][j-1]==1){
                uf.union(index,index-1); //left
                ufFull.union(index,index-1);
            } 
            if(j+1<=n-1 && grid[i][j+1]==1){
                uf.union(index,index+1); //right
                ufFull.union(index,index+1);
            } 
            if(i-1>=0 && grid[i-1][j]==1){
                uf.union(index,index-sideSize); // upper
                ufFull.union(index,index-sideSize);
            } 
            if(i+1<=n-1 && grid[i+1][j]==1){
                uf.union(index,index+sideSize); //lower
                ufFull.union(index,index+sideSize);
            } 
            if(i==0) {
                uf.union(index,sideSize*sideSize); // top row
                ufFull.union(index,sideSize*sideSize);
            } 
            if(i==sideSize-1) uf.union(index,sideSize*sideSize+1); // bottom row
            numberOfOpenSites++; // add 1 open sites
        }
    }
    
    // is site (row, col) open?
    public boolean isOpen(int row, int col){
        validate(row,col);
        return grid[row-1][col-1]==1;
    }
    
    // is site (row, col) full?
    public boolean isFull(int row, int col){
        validate(row,col);
        int index = xyTo1D(row,col);
        return ufFull.connected(sideSize*sideSize,index);
    }
    
     // number of open sites
    public int numberOfOpenSites(){
        return numberOfOpenSites;
    }
    
    // does the system percolate?
    public boolean percolates(){
        return uf.connected(sideSize*sideSize,sideSize*sideSize+1);
    }
    
    // testing
    public static void main(String[] args){
        Percolation p = new Percolation(5);
        p.open(1,2);
        p.open(2,3);
        p.open(4,5);
        p.open(5,3);
        StdOut.println("Is row 4 col 5 opened? " + p.isOpen(4,5));
        p.open(2,2);
        StdOut.println("Is row 2 col 2 full? " + p.isFull(2,2));
        p.open(3,3);
        StdOut.println("Percolates? " + p.percolates());
        p.open(4,3);
        StdOut.println("Percolates? " + p.percolates());
        StdOut.println("Number of open components: " + p.numberOfOpenSites());
        p.open(1,4);
        StdOut.println("Number of open components: " + p.numberOfOpenSites());
    }  
}
