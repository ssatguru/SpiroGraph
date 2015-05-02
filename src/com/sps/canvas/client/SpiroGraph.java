package com.sps.canvas.client;



import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class SpiroGraph implements EntryPoint {
	
	RootComponent rootComponent;
	Spiro soc;

	public void onModuleLoad() {
		rootComponent = RootComponent.getRootComponent();
		if (rootComponent == null){
			Label lbl = new Label("Sorry, your browser does not support Canvas");
			RootPanel.get().add(lbl);
			return;
		}
		
	
		soc = new SpiroOuter(200);
		rootComponent.addComponent(soc);
		soc.setPosition(new Point(320,240));
		
		ControlPanel cp = new ControlPanel();
		rootComponent.addComponent(cp);
		cp.setPosition(-30,240);
		
		
		RootPanel.get().add(rootComponent.getCanvas());
		rootComponent.display();
	}
}