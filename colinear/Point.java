/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.*; // in order to call Arrays.sort


/*
 * Author: Surui Sun
 * Date: 06/22/2017
 * Purpose: This program implements a 2D Point class, where we can draw point and line segment
 * between points and calculate slope and compare the points based on axis or slope.
 */
public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point
    
    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (that.y == this.y && that.x == this.x) return Double.NEGATIVE_INFINITY;
        else if (that.y == this.y && that.x != this.x) return +0; // positive zero
        else if (that.y != this.y && that.x == this.x) return Double.POSITIVE_INFINITY;
        else return (that.y-this.y)/ (double)(that.x-this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;
        else {
            if (this.x < that.x) return -1;
            else if (this.x > that.x) return 1;
            else return 0;
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
   
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                double slope1 = slopeTo(p1); //Point.this.
                double slope2 = slopeTo(p2); //Point.this.
                int isBigger = Double.compare(slope1,slope2);
                if (isBigger > 0) {return 1;}
                else if (isBigger < 0) {return -1;}
                else return 0;
                // if 1 then slope1 > slope2; if -1 then slope1 < slope2, 
           //if 0 then slope1 == slope2
            }
        };
    }
    


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
    
        /* YOUR CODE HERE */
        // test for the Point constructor and toString method
        
        
        Point p1 = new Point(1,1);
        Point p2 = new Point(2,2);
        Point p3 = new Point(3,2);
        Point p4 = new Point(2,5);
        Point p5 = new Point(1,1);
        Point[] pointArr = new Point[] {p1,p2,p3,p4,p5};
        StdOut.println("Point 1: " + p1);
        StdOut.println("Point 2: " + p2);
        StdOut.println("Point 3: " + p3);
        StdOut.println("Point 4: " + p4);
        StdOut.println("Point 5: " + p5);
        // test for the slopeTo function
        StdOut.println("Testing for the slopeTo function: ");
        StdOut.println("Slope between p1 and p2 expected: 1, return: " + p1.slopeTo(p2));
        StdOut.println("Slope between p2 and p3 expected: +0, return: " + p2.slopeTo(p3));
        StdOut.println("Slope between p2 and p4 expected: positive_infinity, return: " + p2.slopeTo(p4));
        StdOut.println("Slope between p1 and p5 expected: negative_infinity, return: " + p1.slopeTo(p5));
        // test for the compareTo function
        StdOut.println("p1 compare to p2 expected: -1, return: " + p1.compareTo(p2));
        StdOut.println("p2 compare to p3 expected: -1, return: " + p2.compareTo(p3));
        StdOut.println("p4 compare to p2 expected: 1, return: " + p4.compareTo(p2));
        StdOut.println("p1 compare to p5 expected: 0, return: " + p1.compareTo(p5));
        
        // test for the draw method
        drawPlot(pointArr);
        
        // test for the slopeOrder
        Arrays.sort(pointArr);
        StdOut.println("Original Sort: ");
        for (int i=0;i<pointArr.length;i++) {
            StdOut.println(pointArr[i].toString());
        }
        Arrays.sort(pointArr,p2.slopeOrder());
        StdOut.println("Sort by slope: ");
        for (int i=0;i<pointArr.length;i++) {
            StdOut.println(pointArr[i].toString());
        }
        
    }
    
    // function to visualize the points and lines
    private static void drawPlot(Point[] pointArr) {
        StdDraw.setXscale(-1,6);
        StdDraw.setYscale(-1,6);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLUE);
        for (int i=0;i<pointArr.length;i++) {
            pointArr[i].draw();
        }
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        pointArr[0].drawTo(pointArr[1]);
        pointArr[1].drawTo(pointArr[2]);
        pointArr[1].drawTo(pointArr[3]);
        pointArr[0].drawTo(pointArr[4]);
    }
}


