package nunes.rabello.client.composite;

import java.util.ArrayList;

import nunes.rabello.client.entities.Title;
import nunes.rabello.client.rpc.ListService;
import nunes.rabello.client.rpc.ListServiceAsync;

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
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AddTitle extends Composite {

	protected VerticalPanel addTitlePanel;
	protected HorizontalPanel titlePanel;
	protected HorizontalPanel mainPanel;
	protected Label titleLabel;
	protected TextBox titleBox;
	protected HorizontalPanel editoraPanel;
	protected Label editoraLabel;
	protected TextBox editoraBox;
	protected HorizontalPanel okPanel;
	protected Button okButton;
	protected FormPanel formPanel;
	protected Tree titleTree;
	protected ListServiceAsync listService;

	public AddTitle() {
		addTitlePanel = new VerticalPanel();
		addTitlePanel.setWidth("700px");
		addTitlePanel.setSpacing(10);

		mainPanel = new HorizontalPanel();
		
		formPanel = new FormPanel();
		formPanel.setAction(GWT.getModuleBaseURL() + "addTitleServlet");
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_GET);
		formPanel.setWidget(mainPanel);
		formPanel.addFormHandler(new FormHandler() {

			public void onSubmit(FormSubmitEvent event) {
				if (titleBox.getText().length() == 0) {
					Window.alert("Campo Título está vazio");
					event.setCancelled(true);
					return;
				}

				if (editoraBox.getText().length() == 0) {
					Window.alert("Campo Editor está vazio");
					event.setCancelled(true);
					return;
				}
			}

			public void onSubmitComplete(FormSubmitCompleteEvent arg0) {
				Window.alert("Novo Título cadastrado com sucesso");
				titleBox.setText("");
				editoraBox.setText("");
				
				titleTree.clear();
				
				if (listService == null) {
					listService = GWT.create(ListService.class);
				}

				AsyncCallback<ArrayList<Title>> callback = new AsyncCallback<ArrayList<Title>>() {

					public void onFailure(Throwable caught) {

						Window.alert("DEU PAU!!!");
					}

					public void onSuccess(ArrayList<Title> result) {
						for (int i = 0; i < result.size(); i++) {
							Title title = result.get(i);
							titleTree.addItem(title.getTitle() + " - " + title.getEditora());
						}
					}

				};

				
				listService.getTitleList(callback);
			}

		});

		/* T�tulo */
		titlePanel = new HorizontalPanel();
		titlePanel.setSpacing(10);
		titleLabel = new Label("Título: ");
		titlePanel.add(titleLabel);
		titlePanel.setCellHorizontalAlignment(titleLabel, HorizontalPanel.ALIGN_LEFT);
		titlePanel.setCellVerticalAlignment(titleLabel, VerticalPanel.ALIGN_MIDDLE);
		titleBox = new TextBox();
		titleBox.setName("title");
		titlePanel.add(titleBox);
		titlePanel.setCellHorizontalAlignment(titleBox, HorizontalPanel.ALIGN_LEFT);
		titlePanel.setCellVerticalAlignment(titleBox, VerticalPanel.ALIGN_MIDDLE);

		/* Editora */
		editoraPanel = new HorizontalPanel();
		editoraPanel.setSpacing(5);
		editoraLabel = new Label("Editora: ");
		editoraPanel.add(editoraLabel);
		editoraPanel.setCellHorizontalAlignment(editoraLabel, HorizontalPanel.ALIGN_LEFT);
		editoraPanel.setCellVerticalAlignment(editoraLabel, VerticalPanel.ALIGN_MIDDLE);
		editoraBox = new TextBox();
		editoraBox.setName("editora");
		editoraPanel.add(editoraBox);
		editoraPanel.setCellHorizontalAlignment(editoraBox, HorizontalPanel.ALIGN_LEFT);
		editoraPanel.setCellVerticalAlignment(editoraBox, VerticalPanel.ALIGN_MIDDLE);

		/* OK */
		okPanel = new HorizontalPanel();
		okPanel.setWidth("500px");
		okPanel.setSpacing(10);
		okButton = new Button();
		okButton.setText("Cadastra");
		okButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				formPanel.submit();
			}
		});

		okPanel.add(okButton);
		okPanel.setCellHorizontalAlignment(okButton, HorizontalPanel.ALIGN_LEFT);
		okPanel.setCellVerticalAlignment(okButton, VerticalPanel.ALIGN_MIDDLE);
		
		titleTree = new Tree();
		titleTree.setAnimationEnabled(true);
		
		if (listService == null) {
			listService = GWT.create(ListService.class);
		}

		AsyncCallback<ArrayList<Title>> callback = new AsyncCallback<ArrayList<Title>>() {

			public void onFailure(Throwable caught) {

				Window.alert("DEU PAU!!!");
			}

			public void onSuccess(ArrayList<Title> result) {
				for (int i = 0; i < result.size(); i++) {
					Title title = result.get(i);
					titleTree.addItem(title.getTitle() + " - " + title.getEditora());
				}
			}

		};

		
		listService.getTitleList(callback);

		addTitlePanel.add(titlePanel);
		addTitlePanel.add(editoraPanel);
		addTitlePanel.add(okPanel);
		mainPanel.add(addTitlePanel);
		mainPanel.add(titleTree);

		initWidget(formPanel);
	}
}
