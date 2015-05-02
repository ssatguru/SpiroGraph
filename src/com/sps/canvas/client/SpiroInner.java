package com.sps.canvas.client;

import com.sps.canvas.client.Line;
import com.sps.canvas.client.Point;
import com.sps.canvas.client.RootComponent;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Style;

public class SpiroInner extends Spiro {
	double penHole = radius * 0.75;
	Context2d paperContext;
	double socAngleStore, socAngle;
	PenHolder penHolder = null;
	double saveRotation;
	
	public SpiroInner(int radius) {
		super(radius);

		build();
	}

	public void paint() {
		if (stop){
			this.setCursor(Style.Cursor.MOVE);
		}
	}

	public void build() {
		super.build();
		if (penHolder == null){
			penHolder = new PenHolder(radius);
			this.addComponent(penHolder);
		}
		penHolder.length = radius;
		penHolder.build();
		penHolder.setRotation(0);
		penHolder.setPosition(radius/2,0);
	}

	public void onMouseDown() {
		if (stop) {
			if (containsPoint(this.relativeMousePos)) {
				selected = true;
				this.setMouseMoveHandler(true);
				this.setMouseUpHandler(true);
				saveRotation=rotation;
			}
		}

	}
	public void onMouseUp(){
		this.setMouseMoveHandler(false);
		this.setMouseUpHandler(false);
		selected=false;
	}

	public void onMouseMove() {

		// three points
		// pointA - center of SOC
		// pointB - mouse point on sic circumference (this is the one dragging the sic circumference )
		// pointC - point on soc circumference
		// pointD - mid of BC
		// pointE - sic center
		//
		// every thing is in parents frame of reference

		SpiroOuter soc = (SpiroOuter) parent;
		socAngle =soc.socAngle;
		//System.out.println("angle = " + socAngle);
		Point pA = soc.getReferencePoint();
		Point pB = soc.relativeMousePos;
		double r = soc.radius -  (thickness + soc.thickness) / 2;
		Point pC = new Point((pA.x + r* Math.cos(socAngle)), (pA.y + r * Math.sin(socAngle)));
		Point pD = Point.mid(pB, pC);
		//System.out.println("a b c d ");
		//pA.print();
		//pB.print();
		//pC.print();
		//pD.print();
		double sDE = -1 / Point.slope(pB, pC);// perpendicular to BC
		double sAC = Point.slope(pA, pC);
		Line lAC = new Line(pA, sAC);
		Line lDE = new Line(pD, sDE);
		Point newCenter = Line.intersection(lAC, lDE);
		//newCenter.print();
		radius = Point.distance(newCenter, pB);
		width = 2 * radius + thickness;
		height = 2 * radius + thickness;
		setReferencePoint(new Point(width / 2, height / 2));
		setPosition(Point.diff(newCenter,pA));
		setRotation(saveRotation);
		//System.out.println("rad " + radius);
		build();

	}

}
