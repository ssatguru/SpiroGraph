package com.sps.canvas.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.LineCap;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;

public class Spiro extends Component {
	double radius;
	int thickness = 10;
	//CssColor circleColor = CssColor.make( "rgba(255,187,187,0.75)");
	CssColor circleColor = CssColor.make("rgba(187,187,255,0.50)");
	public final double TWO_PI = Math.PI * 2;
	private final double DELTA = Math.PI / 32;
	boolean stop = false;
		
	public void paint() {
		
	}

	public void build() {
		context.clearRect(0,0,RootComponent.coordinateSpaceWidth,RootComponent.coordinateSpaceHeight);
		width = 2 * radius + thickness;
		height = 2 * radius + thickness;
		setReferencePoint(new Point(width / 2, height / 2));
		context.beginPath();
		context.arc(getReferencePoint().x, getReferencePoint().y, radius, 0, 2 * Math.PI);
		context.moveTo(getReferencePoint().x+2, getReferencePoint().y);
		context.arc(getReferencePoint().x, getReferencePoint().y, 2, 0, 2 * Math.PI);
		context.setLineWidth(thickness);
		context.setStrokeStyle(circleColor);
		context.stroke();

	}
	public void onDoubleClick() {
		stop = !stop;
	}
	
	public Spiro(int radius) {
		this.radius = radius;
		this.setDoubleClickHandler(true);
		this.setMouseDownHandler(true);
	}
	
	

	public boolean containsPoint(Point p) {
		double pointRadius = Point.distance(this.getReferencePoint(), p);
		if (Math.abs(pointRadius - radius) <= thickness) {
			return true;
		} else {
			return false;
		}
	}



}
