package com.sps.canvas.client;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.Context2d.LineJoin;

public class RoundRectangle extends Component{

	@Override
	public boolean containsPoint(Point pt) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void paint() {
		// TODO Auto-generated method stub
		
	}
	
	public void build(double width, double height, double border, CssColor strokeColor, CssColor fillColor){
		this.setReferencePoint(new Point(width / 2+border, height / 2+border));
		context.setLineWidth(border);
		context.setLineJoin(LineJoin.ROUND);
		context.setStrokeStyle(strokeColor);
		context.beginPath();
		context.moveTo(border/2,border/2);
		context.lineTo(width+1.5*border,border/2);
		context.lineTo(width+1.5*border,height+1.5*border);
		context.lineTo(border/2,height+1.5*border);
		context.lineTo(border/2,border/2);
		context.closePath();
		if (fillColor != null){
			context.setFillStyle(fillColor);
			context.fill();
		}
		context.stroke();
	}
	
	
}
