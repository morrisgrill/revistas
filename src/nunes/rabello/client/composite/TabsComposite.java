package nunes.rabello.client.composite;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;

public class TabsComposite extends Composite{
	
protected DecoratedTabPanel tabsPanel;
	
	protected SimplePanel addTitlePanel;
	protected SimplePanel addNumberPanel;
	protected SimplePanel listComicsPanel;
	protected SimplePanel addDesignerPanel;
	protected SimplePanel addWriterPanel;
	protected SimplePanel createLabelPanel;
	
	public TabsComposite() {
		// Create tab panel
		tabsPanel = new DecoratedTabPanel();
		tabsPanel.setAnimationEnabled(true);
		tabsPanel.setWidth("100%");
		tabsPanel.setHeight("100%");
		tabsPanel.addTabListener(new TabListener(){

			public boolean onBeforeTabSelected(SourcesTabEvents sender,
					int tabIndex) {
				
				if (tabIndex == 0){
					addTitlePanel.clear();
					addTitlePanel.add(new AddTitle());
				}
				
				if (tabIndex == 1){
					addNumberPanel.clear();
					addNumberPanel.add(new AddNumber());
				}
				
				if (tabIndex == 2){
					listComicsPanel.clear();
					listComicsPanel.add(new ListComics());
				}
				
				if (tabIndex == 3){
					addWriterPanel.clear();
					addWriterPanel.add(new AddWriter());
				}
				
				if (tabIndex == 4){
					addDesignerPanel.clear();
					addDesignerPanel.add(new AddDesigner());
				}
				
				if (tabIndex == 5){
					createLabelPanel.clear();
					createLabelPanel.add(new CreateLabel());
				}
				return true;
			}

			public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
				
			}
			
		});
	
		// Populate tab panel
		
		System.err.println("Vai chamar o addTitle");
		addTitlePanel = new SimplePanel();
		addTitlePanel.setWidth("1260px");
		addTitlePanel.add(new AddTitle());
		/*addTitlePanel = new SimplePanel();
		addTitlePanel.setWidth("100%");
		addTitlePanel.setHeight("100%");
		label = new Label("Under Construction 1");
		label.addStyleName("under-construction");
		addTitlePanel.add(label);*/
		tabsPanel.add(addTitlePanel, "Add New Title");
		
		addNumberPanel = new SimplePanel();
		addNumberPanel.setWidth("1260px");
		addNumberPanel.add(new AddNumber());
		/*addNumberPanel.setWidth("100%");
		addNumberPanel.setHeight("100%");
		label = new Label("Under Construction 2");
		label.addStyleName("under-construction");
		addNumberPanel.add(label);*/
		tabsPanel.add(addNumberPanel, "Add New Number");
		
		
		listComicsPanel = new SimplePanel();
		listComicsPanel.setWidth("1260px");
		listComicsPanel.add(new ListComics());
		/*listComicsPanel.setWidth("100%");
		listComicsPanel.setHeight("100%");
		label = new Label("Under Construction");
		label.addStyleName("under-construction");
		listComicsPanel.add(label);*/
		tabsPanel.add(listComicsPanel, "List all the Comics");
		
		
		addWriterPanel = new SimplePanel();
		addWriterPanel.setWidth("1260px");
		addWriterPanel.add(new AddWriter());
		tabsPanel.add(addWriterPanel, "Add a new writer");
		
		
		addDesignerPanel = new SimplePanel();
		addDesignerPanel.setWidth("1260px");
		addDesignerPanel.add(new AddDesigner());
		tabsPanel.add(addDesignerPanel, "Add a new designer");
		
		
		createLabelPanel = new SimplePanel();
		createLabelPanel.setWidth("1260px");
		createLabelPanel.add(new CreateLabel());
		tabsPanel.add(createLabelPanel, "Create a new label");
		

		tabsPanel.selectTab(0);
		initWidget(tabsPanel);
	}

}
