package com.sps.canvas.client;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Style;

public class Button extends RoundRectangle{
	
	double thickness = 4;
	double w = 40;
	double h = 10;
	CssColor fillColor =CssColor.make("rgba(198,198,198,0.5)");
	CssColor strokeColor =CssColor.make("rgb(198,198,198)");
	public Button(){
		width = w + thickness;
		height = h + thickness;
		build();
		this.setClickHandler(true);
	}

	@Override
	public boolean containsPoint(Point pt) {
		return  pt.inRect(0,0, w+thickness*2, h+thickness*2) ;
	}

	
	@Override
	public void paint() {
		this.setCursor(Style.Cursor.POINTER);
	}
	
	public void build() {
		super.build(w,h,thickness,strokeColor,fillColor);
		context.setFillStyle("Black");
		context.fillText("CLEAR",1.25*thickness, 0.75*h+thickness);
		context.stroke();
	}

	public void onClick(){
		if (containsPoint(this.relativeMousePos)){
			RootComponent.clearShadow= true;
		}
	}
}
