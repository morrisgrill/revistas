package nunes.rabello.client.composite;

import java.util.ArrayList;

import nunes.rabello.client.entities.Designer;
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

public class AddDesigner extends Composite {
	protected VerticalPanel addDesignerPanel;
	protected HorizontalPanel designerPanel;
	protected Label designerLabel;
	protected TextBox designerBox;
	protected HorizontalPanel okPanel;
	protected Button okButton;
	protected FormPanel formPanel;
	protected TitleServiceAsync titleService;
	protected HorizontalPanel mainPanel;
	protected Tree titleTree;

	public AddDesigner() {
		
		mainPanel = new HorizontalPanel();  
		addDesignerPanel = new VerticalPanel();
		addDesignerPanel.setSpacing(10);
		addDesignerPanel.setWidth("700px");

		formPanel = new FormPanel();
		System.err.println(GWT.getModuleBaseURL());
		formPanel.setAction(GWT.getModuleBaseURL() + "addStuffServlet");
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_GET);
		formPanel.setWidget(mainPanel);
		formPanel.addFormHandler(new FormHandler() {

			public void onSubmit(final FormSubmitEvent event) {
				if (designerBox.getText().length() == 0) {
					Window.alert("Campo Desenhista está vazio");
					event.setCancelled(true);
					return;
				}

			}

			public void onSubmitComplete(FormSubmitCompleteEvent arg0) {
				Window.alert("Novo Desenhista cadastrado com sucesso");
				designerBox.setText("");
				titleTree.clear();
				
				if (titleService == null) {
					titleService = GWT.create(TitleService.class);
				}
				
				AsyncCallback<ArrayList<Designer>> callback = new AsyncCallback<ArrayList<Designer>>() {

					public void onFailure(Throwable caught) {

						Window.alert("DEU PAU!!!");
					}

					public void onSuccess(ArrayList<Designer> result) {

						for (int i = 0; i < result.size(); i++) {
							Designer title = result.get(i);
							titleTree.addItem(title.getDesigner());
						}

					}
				};

				titleService.getDesignerList(callback);
				
			}

		});

		/* Escritor */
		designerPanel = new HorizontalPanel();
		designerPanel.setSpacing(5);
		designerLabel = new Label("Desenhista: ");
		designerPanel.add(designerLabel);
		designerPanel.setCellHorizontalAlignment(designerLabel, HorizontalPanel.ALIGN_LEFT);
		designerPanel.setCellVerticalAlignment(designerLabel, VerticalPanel.ALIGN_MIDDLE);
		designerBox = new TextBox();
		designerBox.setName("designer");
		designerPanel.add(designerBox);
		designerPanel.setCellHorizontalAlignment(designerBox, HorizontalPanel.ALIGN_LEFT);
		designerPanel.setCellVerticalAlignment(designerBox, VerticalPanel.ALIGN_MIDDLE);

		/* OK */
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
		
		AsyncCallback<ArrayList<Designer>> callback = new AsyncCallback<ArrayList<Designer>>() {

			public void onFailure(Throwable caught) {

				Window.alert("DEU PAU!!!");
			}

			public void onSuccess(ArrayList<Designer> result) {

				for (int i = 0; i < result.size(); i++) {
					Designer title = result.get(i);
					titleTree.addItem(title.getDesigner());
				}

			}
		};

		titleService.getDesignerList(callback);

		addDesignerPanel.add(designerPanel);
		addDesignerPanel.add(okPanel);
		mainPanel.add(addDesignerPanel);
		mainPanel.add(titleTree);

		initWidget(formPanel);
	}
}
