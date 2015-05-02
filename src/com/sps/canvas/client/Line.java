package com.sps.canvas.client;

public class Line {
	double slope;
	Point point;
	
	public Line(Point p, double slope){
		this.slope = slope;
		this.point = p;
	}
	
	public static Point intersection(Line l1,Line l2){
		double c1 = l1.point.y - l1.slope*l1.point.x;
		double  c2 = l2.point.y - l2.slope*l2.point.x;
		int x = (int) ((c2-c1)/(l1.slope-l2.slope));
		int y = (int) (l1.slope*x + c1);
		return new Point(x,y);
	}

}
