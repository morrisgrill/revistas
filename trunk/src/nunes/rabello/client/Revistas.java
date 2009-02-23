package nunes.rabello.client;

import nunes.rabello.client.composite.TabsComposite;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Revistas implements EntryPoint {

	protected TabsComposite tabs;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		tabs = new TabsComposite();
		
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setWidth("100%");
		hPanel.setHeight("100%");
		hPanel.add(tabs);
		
		HorizontalPanel titlePanel = new HorizontalPanel();
		titlePanel.setWidth("100%");
		titlePanel.setHeight("100%");
		Label title = new Label("Personal Comics DataBase");
		title.addStyleName("title");
		Image img = new Image("images/eu.jpg");
		img.setPixelSize(90, 99);
		titlePanel.add(img);
		titlePanel.add(title);
		titlePanel.setCellHorizontalAlignment(title, HorizontalPanel.ALIGN_CENTER);
		titlePanel.setCellVerticalAlignment(title, VerticalPanel.ALIGN_MIDDLE);
		
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setWidth("100%");
		vPanel.add(titlePanel);
		vPanel.add(hPanel);
		

		// Add image and button to the RootPanel
		RootPanel.get().add(vPanel);

	}
}
