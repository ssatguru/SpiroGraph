package com.sps.canvas.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Style;

public class PenHolder extends Component {
	
	double thickness = 10;
	double length;
	
	double penHole;
	double penRadius;
	double oldX, oldY;
	
	Point penPoint;
	public CssColor color = CssColor.make("rgba(187,187,255,0.5)");
	CssColor ink = CssColor.make( "rgb(255,0,0)");
	Context2d paperContext;
	boolean stop = false;
	
	public PenHolder(double length){
		this.length = length;
		paperContext = RootComponent.shadowContext;
		paperContext.setStrokeStyle(ink);
		paperContext.setLineWidth(4);
		this.setDoubleClickHandler(true);
		this.setMouseDownHandler(true);
		penHole = length * 0.75;
		penRadius=3*thickness/8;
		penPoint = new Point(penHole,thickness/2);
	}
	
	public void build(){
		context.clearRect(0,0,RootComponent.coordinateSpaceWidth,RootComponent.coordinateSpaceHeight);
		width = length;
		height = thickness;
		this.setReferencePoint(new Point(width/2,height/2));
		context.beginPath();
		context.setLineWidth(thickness/2);
		context.setStrokeStyle(color);
		context.moveTo(thickness/2,thickness/2);
		context.lineTo(penPoint.x, penPoint.y);
		context.stroke();
		context.beginPath();
		context.setStrokeStyle(ink);
		context.setLineWidth(thickness/4);
		context.arc( penPoint.x,  penPoint.y, penRadius, 0, Math.PI * 2);
		context.stroke();
		
	}
	
	@Override
	public boolean containsPoint(Point p) {
		// TODO Auto-generated method stub
		return penPoint.inRange(p, penRadius);
	}


	public void Oldpaint() {
		if (!stop) {
			paperContext.beginPath();
			paperContext.arc(  penPoint.x,  penPoint.y, penRadius, 0, Math.PI * 2);
			//paperContext.setLineWidth(thickness/2);
			//paperContext.lineTo( penPoint.x,  penPoint.y);
			paperContext.stroke();
		}
	}
	
	public void paint() {
		if (!stop) {
			
			paperContext.lineTo( penPoint.x,  penPoint.y);
			paperContext.stroke();
			paperContext.beginPath();
			paperContext.moveTo( penPoint.x,  penPoint.y);
		}else{
				this.setCursor(Style.Cursor.MOVE);
		}
	}
	
	public void onDoubleClick() {
		stop = !stop;
	}
	
	public void onMouseDown(){
		if (stop) {
			if (containsPoint(this.relativeMousePos)){
				selected = !selected;
				this.setMouseMoveHandler(selected);
				this.setMouseUpHandler(selected);
			}
			
		}
	}
	
	public void onMouseUp(){
		this.setMouseMoveHandler(false);
		this.setMouseUpHandler(false);
		selected = false;
	}
	
	 public void onMouseMove(){
		 if (relativeMousePos.x < thickness/2) penPoint.x = thickness/2;
		 //else if (relativeMousePos.x > (length+thickness/2)) penPoint.x = (length+thickness/2);
		 else penPoint.x = relativeMousePos.x;
		 
		 build();
	 }

}
