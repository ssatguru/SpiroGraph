package com.sps.canvas.client;

import java.util.HashSet;
import java.util.Iterator;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;

public abstract class Component  {
	public Component parent;
	protected boolean selected = false;
	public boolean isSelected(){
		return selected;
	}
	private Point position = new Point(0, 0);
	public Point relativeMousePos = new Point(0, 0);;
	protected double rotation = 0;
	public Point scale;
	protected double width;
	protected double height;
	private Point referencePoint = new Point(0,0);

	public Context2d context;

	private boolean mouseDownHandler = false;
	private boolean mouseUpHandler = false;
	private boolean mouseMoveHandler = false;
	private boolean clickHandler = false;
	private boolean doubleClickHandler = false;
	private boolean handlesEvents = false;
	
	public void setMouseDownHandler(boolean mouseDownHandler) {
		this.mouseDownHandler = mouseDownHandler;
		handlesEvents=mouseDownHandler;
	}

	public void setMouseUpHandler(boolean mouseUpHandler) {
		this.mouseUpHandler = mouseUpHandler;
		handlesEvents=mouseUpHandler;
	}

	public void setMouseMoveHandler(boolean mouseMoveHandler) {
		this.mouseMoveHandler = mouseMoveHandler;
		handlesEvents=mouseMoveHandler;
	}

	public void setClickHandler(boolean clickHandler) {
		this.clickHandler = clickHandler;
		handlesEvents=clickHandler;
	}

	public void setDoubleClickHandler(boolean doubleClickHandler) {
		this.doubleClickHandler = doubleClickHandler;
		handlesEvents=doubleClickHandler;
	}

	

	HashSet components = new HashSet();

	public Component() {
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight((int) RootComponent.coordinateSpaceHeight);
		canvas.setCoordinateSpaceWidth((int) RootComponent.coordinateSpaceWidth);
		context = canvas.getContext2d();
	}

	private double alpha = 0;
	private double refLen = 0;

	public void setReferencePoint(Point p) {
		this.referencePoint = p;
		if ((p.y == 0) && (p.x == 0)) {
			alpha = 0;
		} else if (p.x == 0) {
			alpha = Math.PI / 2;
		} else {
			alpha = Math.atan(p.y / p.x);
		}
		refLen = Math.sqrt(p.x * p.x + p.y * p.y);
		setTranslationPoint();
	}

	public void setScale(Point p) {
		scale = p;
		refLen = Math.sqrt(referencePoint.x * referencePoint.x * scale.x * scale.x + referencePoint.y * referencePoint.y * scale.y * scale.y);
		setTranslationPoint();
	}

	public Point getScale() {
		return scale;
	}

	public Point getReferencePoint() {
		return referencePoint;
	}

	public Point translationPoint = new Point(0, 0);

	private void setTranslationPoint() {
		double a = alpha + rotation;
		if (a != 0) {
			translationPoint.x = position.x - refLen * Math.cos(alpha + rotation);
			translationPoint.y = position.y - refLen * Math.sin(alpha + rotation);
		} else {
			translationPoint.x = position.x - refLen;
			translationPoint.y = position.y;
		}
	}

	public void setRotation(double rot) {
		rotation = rot;
		setTranslationPoint();
	}

	public double getRotation() {
		return rotation;
	}

	public void setPosition(Point p) {
		position = Point.add(parent.referencePoint,p);
		setTranslationPoint();
	}
	//set position component reference point relative to to the parents reference point
	public void setPosition(double x, double y) {
		position.x =parent.referencePoint.x + x;
		position.y =parent.referencePoint.y + y;
		setTranslationPoint();
	}
	
	public Point getPosition(){
		return position;
	}
	public abstract boolean containsPoint(Point pt);

	public void addComponent(Component component) {
		components.add(component);
		component.parent = this;
	}

	public void removeComponent(Component component) {
		components.remove(component);
	}



	public final void process() {
		setRelativeMousePos();
		//if (handlesEvents)
			manageEvents();
			
		Context2d c2d, rContext = RootComponent.getRootComponent().getContext();
		Context2d shadowContext = RootComponent.shadowContext;
	
		rContext.save();
		shadowContext.save();
		rContext.translate(translationPoint.x, translationPoint.y);
		shadowContext.translate(translationPoint.x, translationPoint.y);
		if (rotation != 0) {
			rContext.rotate(rotation);
			shadowContext.rotate(rotation);
		}
		if (scale != null){
			rContext.scale(scale.x, scale.y);
			shadowContext.scale(scale.x, scale.y);
		}
		paint();
		rContext.drawImage(context.getCanvas(), 0, 0);
		
		
		Iterator i = components.iterator();
		Component cmp;
		while (i.hasNext()) {
			cmp = (Component) i.next();
			cmp.process();
		}
		
		rContext.restore();
		shadowContext.restore();

	}
	private void manageEvents() {

		if ((mouseDownHandler) && (RootComponent.mouseDownEvent )) {
			onMouseDown();
		}
		if ((mouseUpHandler) && (RootComponent.mouseUpEvent)) {
			onMouseUp();
		}
		if ((mouseMoveHandler ) && (RootComponent.mouseMoveEvent)) {
			onMouseMove();
		}
		if ((clickHandler ) && (RootComponent.clickEvent )) {
			onClick();
		}
		if ((doubleClickHandler ) && (RootComponent.doubleClickEvent )) {
				onDoubleClick();
		}

	}
	
	protected void onMouseDown(){};
	protected void onMouseUp(){};
	protected void onMouseMove(){};
	protected void onClick(){};
	protected void onDoubleClick(){System.out.println(" double clicked");};

	private void setRelativeMousePos() {
		if (this.parent.equals(this)) return;
		double a1 = Point.angle(this.translationPoint,parent.relativeMousePos );
		double a2 = rotation;
		double r = Point.distance(this.translationPoint, parent.relativeMousePos);
		double a = a1 - a2;
		relativeMousePos.x = r * Math.cos(a);
		relativeMousePos.y = r * Math.sin(a);
	}

	boolean cursorChanged = false;
	public  void setCursor(Style.Cursor c){
		if (containsPoint(relativeMousePos)){
			RootComponent.rootContext.getCanvas().getStyle().setCursor(c);
			 cursorChanged = true;
		}else{
			if (cursorChanged){
				RootComponent.rootContext.getCanvas().getStyle().setCursor(Style.Cursor.AUTO);
				cursorChanged = false;
			}
		}
	}

	public abstract void paint();

}
