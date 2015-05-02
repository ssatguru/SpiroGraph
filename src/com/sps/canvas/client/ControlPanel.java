package com.sps.canvas.client;

import com.google.gwt.canvas.dom.client.CssColor;

public class ControlPanel extends RoundRectangle {

	double border = 10;
	double delta = 10;
	double w = 60 ;
	double h = 300;
	CssColor strokeColor = CssColor.make("rgb(238,238,238)");
	CssColor fillColor =CssColor.make("rgba(198,198,198,0.5)");
	boolean moveIn = false;
	boolean in = false;

	@Override
	public boolean containsPoint(Point pt) {
		return false;
	}
	
	public ControlPanel() {
		build(w,h,border,strokeColor,fillColor);

		Button button = new Button();
		this.addComponent(button);
		button.setPosition(0,-100);
		this.setDoubleClickHandler(true);
	}
	@Override
	public void paint() {

		if ((moveIn) && (!in)) {
			if (getPosition().x >= w/2) {
				in = true;
				return;
			}
			this.setPosition(this.getPosition().x + delta, this.getPosition().y);
		}else if ((!moveIn) && (in)){
			if (getPosition().x <= -w/2) {
				in = false;
				return;
			}
			this.setPosition(this.getPosition().x-delta, this.getPosition().y );
		}
	}

	

	public void onDoubleClick() {
		moveIn = !moveIn;

	}

}
