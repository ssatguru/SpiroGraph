package com.sps.canvas.client;

import java.util.HashMap;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.LineJoin;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Timer;

public class RootComponent extends Component implements MouseMoveHandler, MouseUpHandler, MouseDownHandler, ClickHandler, DoubleClickHandler {

	public static HashMap componentRegistry = new HashMap();
	public static boolean mouseMoveEvent = false;
	public static boolean mouseDownEvent = false;
	public static boolean mouseUpEvent = false;
	public static boolean clickEvent = false;
	public static boolean doubleClickEvent = false;
	// public static Point mousePoint = new Point(0,0);
	public static double mouseX = 0;
	public static double mouseY = 0;

	Canvas rootCanvas, shadowCanvas;
	public static boolean clearShadow = false;
	public static Context2d rootContext, shadowContext;

	public static double coordinateSpaceHeight = 480;
	public static String heightPx = "480px";
	public static double coordinateSpaceWidth = 640;
	public static String widthPx = "640px";

	private static RootComponent rootComponent;

	private RootComponent() {
		rootCanvas = Canvas.createIfSupported();
		rootCanvas.setSize( (coordinateSpaceWidth+2*border)+"px",(coordinateSpaceHeight+2*border) +"px");
		rootCanvas.setCoordinateSpaceHeight( (int) (coordinateSpaceHeight+2*border));
		rootCanvas.setCoordinateSpaceWidth( (int) (coordinateSpaceWidth+2*border));
		rootContext = rootCanvas.getContext2d();
		build();
		rootCanvas.addMouseDownHandler(this);
		rootCanvas.addMouseUpHandler(this);
		rootCanvas.addMouseMoveHandler(this);
		rootCanvas.addClickHandler(this);
		rootCanvas.addDoubleClickHandler(this);

		shadowCanvas = Canvas.createIfSupported();
		shadowCanvas.setSize(widthPx, heightPx);
		shadowCanvas.setCoordinateSpaceHeight((int) coordinateSpaceHeight);
		shadowCanvas.setCoordinateSpaceWidth((int) coordinateSpaceWidth);
		shadowContext = shadowCanvas.getContext2d();

		parent = this;

	}
	int border = 20;
	public void build(){
		
		rootContext.setLineWidth(border);
		rootContext.setLineJoin(LineJoin.ROUND);
		rootContext.setStrokeStyle(CssColor.make(238,238,238));
		rootContext.beginPath();
		rootContext.moveTo(border/2,border/2);
		rootContext.lineTo(coordinateSpaceWidth+1.5*border,border/2);
		rootContext.lineTo(coordinateSpaceWidth+1.5*border,coordinateSpaceHeight+1.5*border);
		rootContext.lineTo(border/2,coordinateSpaceHeight+1.5*border);
		rootContext.lineTo(border/2,border/2);
		rootContext.closePath();
		rootContext.stroke();
	}

	public Context2d getContext() {
		return context;
	}

	public static RootComponent getRootComponent() {
		if (!Canvas.isSupported())
			return null;
		if (rootComponent == null) {
			rootComponent = new RootComponent();
		}
		return rootComponent;
	}

	public Canvas getCanvas() {
		return rootCanvas;
	}
	
	

	public void display() {
		Timer timer = new Timer() {
			@Override
			public void run() {
				process();
				rootContext.clearRect(border, border, coordinateSpaceWidth, coordinateSpaceHeight);
				if (clearShadow) {
					shadowContext.beginPath();
					shadowContext.clearRect(0, 0, coordinateSpaceWidth, coordinateSpaceHeight);
					clearShadow=false;
				} else
					rootContext.drawImage(shadowContext.getCanvas(), border, border);
				rootContext.drawImage(context.getCanvas(), border, border);
				initEvents();
			}
		};
		timer.scheduleRepeating(25);
	}

	@Override
	public void paint() {
		context.clearRect(0, 0, coordinateSpaceWidth, coordinateSpaceHeight);

	}

	private void initEvents() {
		mouseMoveEvent = false;
		mouseDownEvent = false;
		mouseUpEvent = false;
		clickEvent = false;
		doubleClickEvent = false;
	}

	@Override
	public boolean containsPoint(Point pt) {
		return true;
	}

	@Override
	public void onDoubleClick(DoubleClickEvent event) {
		initEvents();
		relativeMousePos.set(event.getX()-border, event.getY()-border);
		doubleClickEvent = true;
		//double click selects the canvas . This prevents the mousedown from working.
		//On mousedown the the browser assumes you are trying to move the canvas
		//to prevent this loose the focus on canvas
		rootCanvas.setFocus(false);

	}

	@Override
	public void onClick(ClickEvent event) {
		// initEvents();
		relativeMousePos.set(event.getX()-border, event.getY()-border);
		clickEvent = true;
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		//by default mouse down results in cursor changing to text cursor.
		//to prevent this use preventDefault()
		event.preventDefault();
		relativeMousePos.set(event.getX()-border, event.getY()-border);
		mouseDownEvent = true;
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		relativeMousePos.set(event.getX()-border, event.getY()-border);
		mouseUpEvent = true;
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		relativeMousePos.set(event.getX()-border, event.getY()-border);
		mouseMoveEvent = true;
	}

	private void setRelativeMousePos() {
	}

}
