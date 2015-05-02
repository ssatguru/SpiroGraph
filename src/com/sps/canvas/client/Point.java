package com.sps.canvas.client;

public class Point {
	public double x;
	public double y;
	public Point(double x, double y){
		this.x=x;
		this.y=y;
	}
	
	public static double distance(Point p1, Point p2){
		double dist =  Math.sqrt((p1.x - p2.x) * (p1.x - p2.x)
				+ (p1.y - p2.y) * (p1.y - p2.y));
		return dist;
	}
	
	public static Point mid(Point p1, Point p2){
		return new Point((p2.x+p1.x)/2 , (p2.y+p1.y)/2);
	}
	
	public static double slope(Point p1, Point p2){
		return ((double)(p2.y-p1.y))/((double) (p2.x-p1.x));
	}
	
	public static double angle(Point p1, Point p2){
		double a = Math.atan(slope(p1,p2));
		if (p1.x > p2.x) a= Math.PI + a;
		if (p1.x < p2.x) a= Math.PI *2 + a; 
		return a;
	}
	public void print(){
		System.out.println(x +"," + y);
	}
	
	public boolean inRange(Point P, double r){
		if ((Math.abs(x-P.x)<=r) && (Math.abs(y-P.y)<=r)) return true;
		else return false;
	}
	
	public boolean inRect(double rx, double ry, double w, double h){
		if ((x>=rx) && (x<=(rx+w)) && (y>=ry) && (y<=(ry+h))) return true;
		else return false;
	}
	public void set(int x2, int y2) {
		this.x=x2;
		this.y=y2;
	}
	public static Point add(Point p1, Point p2){
		return new Point(p1.x+p2.x, p1.y+p2.y);
	}
	public static Point diff(Point p1, Point p2){
		return new Point(p1.x-p2.x, p1.y-p2.y);
	}
}
