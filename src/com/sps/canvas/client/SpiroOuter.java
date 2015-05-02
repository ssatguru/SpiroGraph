package com.sps.canvas.client;

import com.google.gwt.dom.client.Style;

public class SpiroOuter extends Spiro {
	double delta = Math.PI /90;
	
	double rotAngle = 0,saveRotation;
	// angle subtended by the inner spiro inside the outer spiro
	public double socAngle = 0;
	SpiroInner si;
	double siOffset;
	

	public SpiroOuter(int radius) {
		super(radius);
		si = new SpiroInner(75);
		this.addComponent(si);
		siOffset = radius - si.radius - (thickness + si.thickness) / 2;
		si.setPosition(siOffset, 0);
		build();
	}

	public void build() {
		super.build();
	}

	public void onMouseDown() {
		if (stop) {
			if (containsPoint(this.relativeMousePos)) {
				selected = true;
				this.setMouseMoveHandler(true);
				this.setMouseUpHandler(true);
				saveRotation = si.getRotation();
			}
		}
	}
	
	public void onMouseUp(){
		this.setMouseMoveHandler(false);
		this.setMouseUpHandler(false);
		selected=false;
	}

	public void onMouseMove() {
		radius = Point.distance(this.getReferencePoint(), this.relativeMousePos);
		build();
		siOffset = radius - si.radius - (thickness + si.thickness) / 2;
		si.setPosition(siOffset * Math.cos(socAngle), siOffset * Math.sin(socAngle));
		si.setRotation(saveRotation);
	}

	

	public void paint() {
		/*
		 * if (rotAngle >= TWO_PI){ rotAngle = delta; }else{ rotAngle =rotAngle+
		 * delta; }
		 */
		if (!stop) {
			socAngle = socAngle +delta;
			siOffset = radius - si.radius - (thickness + si.thickness) / 2;
			si.setPosition(siOffset * Math.cos(socAngle), siOffset * Math.sin(socAngle));
			si.setRotation(-(Math.abs(si.getRotation())+delta * radius/si.radius));
		}else{
			this.setCursor(Style.Cursor.MOVE);
		}

	}

}
