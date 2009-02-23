package nunes.rabello.client.composite;

import java.util.ArrayList;

import nunes.rabello.client.entities.Labels;
import nunes.rabello.client.rpc.TitleService;
import nunes.rabello.client.rpc.TitleServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CreateLabel extends Composite {
	protected VerticalPanel addLabelPanel;
	protected HorizontalPanel labelPanel;
	protected Label labelLabel;
	protected TextBox labelBox;
	protected HorizontalPanel okPanel;
	protected Button okButton;
	protected FormPanel formPanel;
	protected TitleServiceAsync titleService;
	protected HorizontalPanel mainPanel;
	protected Tree titleTree;
	
	public CreateLabel(){
		addLabelPanel = new VerticalPanel();
		addLabelPanel.setSpacing(10);
		addLabelPanel.setWidth("700px");
		mainPanel = new HorizontalPanel();
		
		formPanel = new FormPanel();
		formPanel.setAction(GWT.getModuleBaseURL() + "addStuffServlet");
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_GET);
		formPanel.setWidget(mainPanel);
		formPanel.addFormHandler(new FormHandler() {
		
			public void onSubmit(FormSubmitEvent event) {
				if (labelBox.getText().length() == 0) {
					Window.alert("Campo Label está vazio");
					event.setCancelled(true);
					return;
				}

			}

			public void onSubmitComplete(FormSubmitCompleteEvent arg0) {
				Window.alert("Nova label criada com sucesso");
				labelBox.setText("");
				titleTree.clear();
				
				AsyncCallback<ArrayList<Labels>> callback = new AsyncCallback<ArrayList<Labels>>() {

					public void onFailure(Throwable caught) {

						Window.alert("DEU PAU!!!");
					}

					public void onSuccess(ArrayList<Labels> result) {

						for (int i = 0; i < result.size(); i++) {
							Labels title = result.get(i);
							titleTree.addItem(title.getLabel());
						}

					}
				};

				titleService.getLabelList(callback);
			}

		});
		
        /*Escritor*/		
		labelPanel = new HorizontalPanel();
		labelPanel.setSpacing(5);
		labelLabel = new Label("Label: ");
		labelPanel.add(labelLabel);
		labelPanel.setCellHorizontalAlignment(labelLabel, HorizontalPanel.ALIGN_LEFT);
		labelPanel.setCellVerticalAlignment(labelLabel, VerticalPanel.ALIGN_MIDDLE);
		labelBox = new TextBox();
		labelBox.setName("label");
		labelPanel.add(labelBox);
		labelPanel.setCellHorizontalAlignment(labelBox, HorizontalPanel.ALIGN_LEFT);
		labelPanel.setCellVerticalAlignment(labelBox, VerticalPanel.ALIGN_MIDDLE);
		
				
		/*OK*/		
		okPanel = new HorizontalPanel();
		okPanel.setSpacing(10);
		okButton = new Button();
		okButton.setText("Cadastra");
		okButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				formPanel.submit();
			}
		});		

		okPanel.add(okButton);
		okPanel.setCellHorizontalAlignment(okButton, HorizontalPanel.ALIGN_CENTER);
		okPanel.setCellVerticalAlignment(okButton, VerticalPanel.ALIGN_MIDDLE);
		
		if (titleService == null) {
			titleService = GWT.create(TitleService.class);
		}

		titleTree = new Tree();
		titleTree.setAnimationEnabled(true);
		
		AsyncCallback<ArrayList<Labels>> callback = new AsyncCallback<ArrayList<Labels>>() {

			public void onFailure(Throwable caught) {

				Window.alert("DEU PAU!!!");
			}

			public void onSuccess(ArrayList<Labels> result) {

				for (int i = 0; i < result.size(); i++) {
					Labels title = result.get(i);
					titleTree.addItem(title.getLabel());
				}

			}
		};

		titleService.getLabelList(callback);
		
		addLabelPanel.add(labelPanel);
		addLabelPanel.add(okPanel);
		mainPanel.add(addLabelPanel);
		mainPanel.add(titleTree);
		
		initWidget(formPanel);
	}
}
