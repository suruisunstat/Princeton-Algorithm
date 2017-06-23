import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.*;
import edu.princeton.cs.algs4.Stopwatch;

/*
 * Author: Surui Sun
 * Date: 06/22/2017
 * Compilation: javac BruteCollinearPoints.java
 * Execution: java BruteCollinearPoints < input.txt
 * Dependencies: StdOut.java, StdDraw.java, In.java, java.util.*, Stopwatch.java
 * Purpose: This program examines 4 points at a time and checks whether they all lie on 
 * the same line segment, returning all such line segments.
 * 
 * 
 
 
 */


public class BruteCollinearPoints {
    // For simplicity, we will not supply any input to BruteCollinearPoints that has 5 or more collinear points.
    
    private int numberOfSegments;
    private ArrayList<LineSegment> segments;
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        numberOfSegments = 0;
        if (points == null) throw new java.lang.IllegalArgumentException("cannot create instance"); 
        for (int i=0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.IllegalArgumentException("cannot contain null points");
            for (int j=i+1; j < points.length; j++) {
                if (points[i].equals(points[j])) throw new java.lang.IllegalArgumentException("cannot contain duplicate points");
            }
        }

        segments = new ArrayList<LineSegment>();
        
        Point[] pointsCopy = Arrays.copyOf(points,points.length);
        
        Arrays.sort(pointsCopy);
        
        for (int p=0; p < pointsCopy.length-3; p++) {
            for (int q=p+1; q < pointsCopy.length-2; q++) {
                for (int r=q+1; r < pointsCopy.length-1; r++) {
                    double slopePQ = pointsCopy[p].slopeTo(pointsCopy[q]);
                    double slopePR = pointsCopy[p].slopeTo(pointsCopy[r]);
                    if (Double.compare(slopePQ,slopePR) != 0) continue; 
                    for(int s=r+1; s<pointsCopy.length; s++) {
                        double slopePS = pointsCopy[p].slopeTo(pointsCopy[s]);
                        if (Double.compare(slopePQ,slopePS) == 0){
                            segments.add(new LineSegment(pointsCopy[p],pointsCopy[s]));
                            numberOfSegments++;
                        }  
                    }
                }
            }
        }
    }  
    
    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }
    
    
    // the line segments
    public LineSegment[] segments() {
        LineSegment[] bruteSegments = segments.toArray(new LineSegment[segments.size()]);
        return bruteSegments; 
    }    
    
    
    public static void main(String[] args) {
        // read txt input
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x,y);
        }
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(StdDraw.BLUE);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        Stopwatch sw = new Stopwatch();
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        for (LineSegment segment : bcp.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        StdOut.println(bcp.segments().length);
        StdOut.println(bcp.numberOfSegments);
        StdOut.println("time= " + sw.elapsedTime() + " seconds.");
        //StdOut.println(bcp.segments().length);
    }
}
