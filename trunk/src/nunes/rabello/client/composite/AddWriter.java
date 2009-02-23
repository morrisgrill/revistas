package nunes.rabello.client.composite;

import java.util.ArrayList;

import nunes.rabello.client.entities.Writer;
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

public class AddWriter extends Composite {

	protected VerticalPanel addWriterPanel;
	protected HorizontalPanel writerPanel;
	protected Label writerLabel;
	protected TextBox writerBox;
	protected HorizontalPanel okPanel;
	protected Button okButton;
	protected FormPanel formPanel;
	protected TitleServiceAsync titleService;
	protected HorizontalPanel mainPanel;
	protected Tree titleTree;
	
	public AddWriter(){
		mainPanel = new HorizontalPanel();
		addWriterPanel = new VerticalPanel();
		addWriterPanel.setSpacing(10);
		addWriterPanel.setWidth("700px");
		
		formPanel = new FormPanel();
		System.err.println(GWT.getModuleBaseURL());
		formPanel.setAction(GWT.getModuleBaseURL() + "addStuffServlet");
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_GET);
		formPanel.setWidget(mainPanel);
		formPanel.addFormHandler(new FormHandler() {
		
			public void onSubmit(FormSubmitEvent event) {
				if (writerBox.getText().length() == 0) {
					Window.alert("Campo Roteirista está vazio");
					event.setCancelled(true);
					return;
				}

			}

			public void onSubmitComplete(FormSubmitCompleteEvent arg0) {
				Window.alert("Novo Escritor cadastrado com sucesso");
				writerBox.setText("");
				
				titleTree.clear();
				
				if (titleService == null) {
					titleService = GWT.create(TitleService.class);
				}
				
				AsyncCallback<ArrayList<Writer>> callback = new AsyncCallback<ArrayList<Writer>>() {

					public void onFailure(Throwable caught) {

						Window.alert("DEU PAU!!!");
					}

					public void onSuccess(ArrayList<Writer> result) {

						for (int i = 0; i < result.size(); i++) {
							Writer title = result.get(i);
							titleTree.addItem(title.getWriter());
						}

					}
				};

				titleService.getWriterList(callback);
			}

		});
		
        /*Escritor*/		
		writerPanel = new HorizontalPanel();
		writerPanel.setSpacing(5);
		writerLabel = new Label("Roteirista: ");
		writerPanel.add(writerLabel);
		writerPanel.setCellHorizontalAlignment(writerLabel, HorizontalPanel.ALIGN_LEFT);
		writerPanel.setCellVerticalAlignment(writerLabel, VerticalPanel.ALIGN_MIDDLE);
		writerBox = new TextBox();
		writerBox.setName("writer");
		writerPanel.add(writerBox);
		writerPanel.setCellHorizontalAlignment(writerBox, HorizontalPanel.ALIGN_LEFT);
		writerPanel.setCellVerticalAlignment(writerBox, VerticalPanel.ALIGN_MIDDLE);
		
				
		/*OK*/		
		okPanel = new HorizontalPanel();
		okPanel.setWidth("100%");
		okPanel.setSpacing(5);
		okButton = new Button();
		okButton.setText("Cadastra");
		okButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				formPanel.submit();
			}
		});		

		okPanel.add(okButton);
		okPanel.setSpacing(10);
		okPanel.setCellHorizontalAlignment(okButton, HorizontalPanel.ALIGN_LEFT);
		okPanel.setCellVerticalAlignment(okButton, VerticalPanel.ALIGN_MIDDLE);
		
		if (titleService == null) {
			titleService = GWT.create(TitleService.class);
		}

		titleTree = new Tree();
		titleTree.setAnimationEnabled(true);
		
		AsyncCallback<ArrayList<Writer>> callback = new AsyncCallback<ArrayList<Writer>>() {

			public void onFailure(Throwable caught) {

				Window.alert("DEU PAU!!!");
			}

			public void onSuccess(ArrayList<Writer> result) {

				for (int i = 0; i < result.size(); i++) {
					Writer title = result.get(i);
					titleTree.addItem(title.getWriter());
				}

			}
		};

		titleService.getWriterList(callback);
		
		addWriterPanel.add(writerPanel);
		addWriterPanel.add(okPanel);
		
		mainPanel.add(addWriterPanel);
		mainPanel.add(titleTree);
		
		initWidget(formPanel);
	}

}
