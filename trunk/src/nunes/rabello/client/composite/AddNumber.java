package nunes.rabello.client.composite;

import java.util.ArrayList;

import nunes.rabello.client.entities.Designer;
import nunes.rabello.client.entities.Title;
import nunes.rabello.client.entities.Writer;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AddNumber extends Composite {
	protected VerticalPanel addNumberPanel;
	protected HorizontalPanel titlePanel;
	protected Label titleLabel;
	protected ListBox titleBox;

	protected HorizontalPanel numberPanel;
	protected Label numberLabel;
	protected TextBox numberBox;

	protected HorizontalPanel imagePanel;
	protected Label imageLabel;
	protected TextBox imageBox;

	protected HorizontalPanel historyButtonPanel;
	protected VerticalPanel historyPanel;
	protected Label historyButtonLabel;
	protected Image historyButton;
	protected int iCount;
	protected int iWriterCount;
	protected int iDesignerCount;
	protected int iLabelCount;

	protected VerticalPanel tempPanel;
	protected VerticalPanel tempDesignerPanel;
	protected VerticalPanel tempLabelPanel;

	protected HorizontalPanel okPanel;
	protected Button okButton;
	protected FormPanel formPanel;

	// RPC
	protected TitleServiceAsync titleService;

	public AddNumber() {
		addNumberPanel = new VerticalPanel();
		addNumberPanel.setSpacing(10);
		addNumberPanel.setWidth("1150px");
		addNumberPanel.addStyleName("bordas");

		formPanel = new FormPanel();
		System.err.println(GWT.getModuleBaseURL());
		formPanel.setAction(GWT.getModuleBaseURL() + "addNumberServlet");
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_GET);
		formPanel.setWidget(addNumberPanel);

		formPanel.addFormHandler(new FormHandler() {

			public void onSubmit(FormSubmitEvent event) {

				if (numberBox.getText().length() == 0) {
					Window.alert("Campo número está vazio");
					event.setCancelled(true);
					return;
				}

				try {
					@SuppressWarnings("unused")
					int number = Integer.valueOf(numberBox.getText());
				} catch (Exception e) {
					Window.alert("O campo número deve ser um número");
					event.setCancelled(true);
					return;
				}
			}

			public void onSubmitComplete(FormSubmitCompleteEvent arg0) {
				Window.alert("Novo Número cadastrado com sucesso");
				addNumberPanel.clear();
				formPanel.clear();
				new AddNumber();
			}

		});

		/* Título */
		titlePanel = new HorizontalPanel();
		titlePanel.setWidth("1150px");
		titlePanel.setSpacing(10);
		titleLabel = new Label("Título: ");
		titleLabel.addStyleName("item");
		titlePanel.add(titleLabel);
		titlePanel.setCellHorizontalAlignment(titleLabel, HorizontalPanel.ALIGN_LEFT);
		titlePanel.setCellVerticalAlignment(titleLabel, VerticalPanel.ALIGN_MIDDLE);
		titleBox = new ListBox(false);
		titleBox.setWidth("500px");
		titleBox.setName("title");

		// RPC call
		if (titleService == null) {
			titleService = GWT.create(TitleService.class);
		}

		AsyncCallback<ArrayList<Title>> callback = new AsyncCallback<ArrayList<Title>>() {

			public void onFailure(Throwable caught) {

				Window.alert("DEU PAU!!!");
			}

			public void onSuccess(ArrayList<Title> result) {

				for (int i = 0; i < result.size(); i++) {
					Title title = result.get(i);
					titleBox.addItem(title.getTitle() + " - " + title.getEditora(), String.valueOf(title.getId()));
				}

			}
		};

		titleService.getTitleList(callback);

		titlePanel.add(titleBox);
		titlePanel.setCellHorizontalAlignment(titleBox, HorizontalPanel.ALIGN_RIGHT);
		titlePanel.setCellVerticalAlignment(titleBox, VerticalPanel.ALIGN_MIDDLE);

		/* Número */
		numberPanel = new HorizontalPanel();
		numberPanel.setWidth("1150px");
		numberPanel.setSpacing(5);
		numberLabel = new Label("Número: ");
		numberLabel.addStyleName("item");
		numberPanel.add(numberLabel);
		numberPanel.setCellHorizontalAlignment(numberLabel, HorizontalPanel.ALIGN_LEFT);
		numberPanel.setCellVerticalAlignment(numberLabel, VerticalPanel.ALIGN_MIDDLE);
		numberBox = new TextBox();
		numberBox.setName("number");
		numberBox.setWidth("500px");
		numberPanel.add(numberBox);
		numberPanel.setCellHorizontalAlignment(numberBox, HorizontalPanel.ALIGN_RIGHT);
		numberPanel.setCellVerticalAlignment(numberBox, VerticalPanel.ALIGN_MIDDLE);

		/* Imagem */
		imagePanel = new HorizontalPanel();
		imagePanel.setWidth("1150px");
		imagePanel.setSpacing(5);
		imageLabel = new Label("Capa: ");
		imageLabel.addStyleName("item");
		imagePanel.add(imageLabel);
		imagePanel.setCellHorizontalAlignment(imageLabel, HorizontalPanel.ALIGN_LEFT);
		imagePanel.setCellVerticalAlignment(imageLabel, VerticalPanel.ALIGN_MIDDLE);
		imageBox = new TextBox();
		imageBox.setName("image");
		imageBox.setWidth("500px");
		imagePanel.add(imageBox);
		imagePanel.setCellHorizontalAlignment(imageBox, HorizontalPanel.ALIGN_RIGHT);
		imagePanel.setCellVerticalAlignment(imageBox, VerticalPanel.ALIGN_MIDDLE);

		/* Histórias */
		historyPanel = new VerticalPanel();
		historyPanel.setSpacing(10);
		historyPanel.setWidth("1150px");
		HorizontalPanel tempPanel = new HorizontalPanel();
		tempPanel.setSpacing(10);
		historyButtonLabel = new Label("Adiciona nova história");
		historyButtonLabel.addStyleName("item");
		historyButton = new Image("images/button_add.gif");
		tempPanel.add(historyButtonLabel);
		tempPanel.setCellHorizontalAlignment(historyButtonLabel, HorizontalPanel.ALIGN_LEFT);
		tempPanel.setCellVerticalAlignment(historyButtonLabel, VerticalPanel.ALIGN_MIDDLE);
		tempPanel.add(historyButton);
		tempPanel.setCellHorizontalAlignment(historyButton, HorizontalPanel.ALIGN_RIGHT);
		tempPanel.setCellVerticalAlignment(historyButton, VerticalPanel.ALIGN_MIDDLE);
		iCount = 0;
		historyButton.addClickListener(addHistory());
		historyPanel.add(tempPanel);

		/* OK */
		okPanel = new HorizontalPanel();
		okPanel.setWidth("1150px");
		okPanel.setSpacing(5);
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

		addNumberPanel.add(titlePanel);
		addNumberPanel.add(numberPanel);
		addNumberPanel.add(imagePanel);
		addNumberPanel.add(historyPanel);
		addNumberPanel.add(okPanel);

		initWidget(formPanel);
	}

	private ClickListener addHistory() {

		return new ClickListener() {

			public void onClick(Widget sender) {
				VerticalPanel vHistoryPanel = new VerticalPanel();
				vHistoryPanel.addStyleName("search");
				vHistoryPanel.setSpacing(5);

				HorizontalPanel historyTitlePanel = new HorizontalPanel();
				historyTitlePanel.setWidth("1150px");
				historyTitlePanel.setSpacing(10);
				Label historyTitleLabel = new Label("Nome da História: ");
				historyTitleLabel.addStyleName("item");
				historyTitlePanel.add(historyTitleLabel);
				historyTitlePanel.setCellHorizontalAlignment(historyTitleLabel, HorizontalPanel.ALIGN_LEFT);
				historyTitlePanel.setCellVerticalAlignment(historyTitleLabel, VerticalPanel.ALIGN_MIDDLE);
				TextBox historyTitleBox = new TextBox();
				historyTitleBox.setWidth("500px");
				historyTitleBox.setName("historytitle" + iCount);
				historyTitlePanel.add(historyTitleBox);
				historyTitlePanel.setCellHorizontalAlignment(historyTitleBox, HorizontalPanel.ALIGN_RIGHT);
				historyTitlePanel.setCellVerticalAlignment(historyTitleBox, VerticalPanel.ALIGN_MIDDLE);

				/* Originalmente publicado em */
				HorizontalPanel historyOriginalPanel;
				Label historyOriginalLabel;
				TextBox historyOriginalBox;

				historyOriginalPanel = new HorizontalPanel();
				historyOriginalPanel.setWidth("1150px");
				historyOriginalPanel.setSpacing(5);
				historyOriginalLabel = new Label("Originalmente publicado em: ");
				historyOriginalLabel.addStyleName("item");
				historyOriginalPanel.add(historyOriginalLabel);
				historyOriginalPanel.setCellHorizontalAlignment(historyOriginalLabel, HorizontalPanel.ALIGN_LEFT);
				historyOriginalPanel.setCellVerticalAlignment(historyOriginalLabel, VerticalPanel.ALIGN_MIDDLE);
				historyOriginalBox = new TextBox();
				historyOriginalBox.setWidth("500px");
				historyOriginalBox.setName("historyoriginal");
				historyOriginalPanel.add(historyOriginalBox);
				historyOriginalPanel.setCellHorizontalAlignment(historyOriginalBox, HorizontalPanel.ALIGN_RIGHT);
				historyOriginalPanel.setCellVerticalAlignment(historyOriginalBox, VerticalPanel.ALIGN_MIDDLE);

				HorizontalPanel historyHistoryPanel = new HorizontalPanel();
				historyHistoryPanel.setWidth("1150px");
				historyHistoryPanel.setSpacing(10);
				Label historyHistoryLabel = new Label("História: ");
				historyHistoryLabel.addStyleName("item");
				historyHistoryPanel.add(historyHistoryLabel);
				historyHistoryPanel.setCellHorizontalAlignment(historyHistoryLabel, HorizontalPanel.ALIGN_LEFT);
				historyHistoryPanel.setCellVerticalAlignment(historyHistoryLabel, VerticalPanel.ALIGN_MIDDLE);
				TextArea historyHistoryBox = new TextArea();
				historyHistoryBox.setWidth("500px");
				historyHistoryBox.setHeight("150px");
				historyHistoryBox.setName("historyhistory" + iCount);
				historyHistoryPanel.add(historyHistoryBox);
				historyHistoryPanel.setCellHorizontalAlignment(historyHistoryBox, HorizontalPanel.ALIGN_RIGHT);
				historyHistoryPanel.setCellVerticalAlignment(historyHistoryBox, VerticalPanel.ALIGN_MIDDLE);

				HorizontalPanel historyNotaPanel = new HorizontalPanel();
				historyNotaPanel.setWidth("1150px");
				historyNotaPanel.setSpacing(10);
				Label historyNotaLabel = new Label("Nota pra História: ");
				historyNotaLabel.addStyleName("item");
				historyNotaPanel.add(historyNotaLabel);
				historyNotaPanel.setCellHorizontalAlignment(historyNotaLabel, HorizontalPanel.ALIGN_LEFT);
				historyNotaPanel.setCellVerticalAlignment(historyNotaLabel, VerticalPanel.ALIGN_MIDDLE);
				TextBox historyNotaBox = new TextBox();
				historyNotaBox.setWidth("500px");
				historyNotaBox.setName("historynota" + iCount);
				historyNotaPanel.add(historyNotaBox);
				historyNotaPanel.setCellHorizontalAlignment(historyNotaBox, HorizontalPanel.ALIGN_RIGHT);
				historyNotaPanel.setCellVerticalAlignment(historyNotaBox, VerticalPanel.ALIGN_MIDDLE);

				tempPanel = new VerticalPanel();
				tempPanel.setSpacing(10);
				HorizontalPanel tempRoteirista = new HorizontalPanel();
				tempPanel.setWidth("1150px");
				Label writerButtonLabel = new Label("Adicionar roteirista: ");
				writerButtonLabel.addStyleName("item");
				Image writerButton = new Image("images/button_add.gif");
				tempRoteirista.add(writerButtonLabel);
				tempRoteirista.setCellHorizontalAlignment(writerButtonLabel, HorizontalPanel.ALIGN_LEFT);
				tempRoteirista.setCellVerticalAlignment(writerButtonLabel, VerticalPanel.ALIGN_MIDDLE);
				tempRoteirista.add(writerButton);
				tempRoteirista.setCellHorizontalAlignment(writerButton, HorizontalPanel.ALIGN_RIGHT);
				tempRoteirista.setCellVerticalAlignment(writerButton, VerticalPanel.ALIGN_MIDDLE);
				tempPanel.add(tempRoteirista);
				iWriterCount = 0;
				writerButton.addClickListener(addWriter());

				tempDesignerPanel = new VerticalPanel();
				tempDesignerPanel.setSpacing(10);
				tempDesignerPanel.setWidth("1150px");
				HorizontalPanel tempDesign = new HorizontalPanel();
				Label designerButtonLabel = new Label("Adicionar desenhista: ");
				designerButtonLabel.addStyleName("item");
				Image designerButton = new Image("images/button_add.gif");
				tempDesign.add(designerButtonLabel);
				tempDesign.add(designerButton);
				tempDesign.setCellHorizontalAlignment(designerButtonLabel, HorizontalPanel.ALIGN_LEFT);
				tempDesign.setCellVerticalAlignment(designerButtonLabel, VerticalPanel.ALIGN_MIDDLE);
				tempDesign.setCellHorizontalAlignment(designerButton, HorizontalPanel.ALIGN_RIGHT);
				tempDesign.setCellVerticalAlignment(designerButton, VerticalPanel.ALIGN_MIDDLE);
				tempDesignerPanel.add(tempDesign);

				iDesignerCount = 0;
				designerButton.addClickListener(addDesigner());

				tempLabelPanel = new VerticalPanel();
				tempLabelPanel.setSpacing(10);
				tempLabelPanel.setWidth("1150px");
				HorizontalPanel tempLabel = new HorizontalPanel();
				Label labelButtonLabel = new Label("Adicionar Label: ");
				labelButtonLabel.addStyleName("item");
				Image labelButton = new Image("images/button_add.gif");
				tempLabel.add(labelButtonLabel);
				tempLabel.add(labelButton);
				tempLabel.setCellHorizontalAlignment(labelButtonLabel, HorizontalPanel.ALIGN_LEFT);
				tempLabel.setCellVerticalAlignment(labelButtonLabel, VerticalPanel.ALIGN_MIDDLE);
				tempLabel.setCellHorizontalAlignment(labelButton, HorizontalPanel.ALIGN_RIGHT);
				tempLabel.setCellVerticalAlignment(labelButton, VerticalPanel.ALIGN_MIDDLE);
				tempLabelPanel.add(tempLabel);

				iLabelCount = 0;
				labelButton.addClickListener(addLabel());

				vHistoryPanel.add(historyTitlePanel);
				vHistoryPanel.add(historyOriginalPanel);
				vHistoryPanel.add(historyHistoryPanel);
				vHistoryPanel.add(historyNotaPanel);
				vHistoryPanel.add(tempPanel);
				vHistoryPanel.add(tempDesignerPanel);
				vHistoryPanel.add(tempLabelPanel);

				historyPanel.add(vHistoryPanel);
				iCount++;
			}

		};

	}

	private ClickListener addWriter() {
		return new ClickListener() {

			public void onClick(Widget sender) {

				final ListBox historyTitleBox = new ListBox();

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
							historyTitleBox.addItem(title.getWriter(), String.valueOf(title.getId()));
						}

					}
				};

				titleService.getWriterList(callback);

				VerticalPanel vHistoryPanel = new VerticalPanel();
				vHistoryPanel.addStyleName("darksearch");
				vHistoryPanel.setSpacing(5);

				HorizontalPanel historyTitlePanel = new HorizontalPanel();
				historyTitlePanel.setWidth("500px");
				historyTitlePanel.setSpacing(10);
				Label historyTitleLabel = new Label("Roteirista: ");
				historyTitleLabel.addStyleName("item");
				historyTitleLabel.addStyleName("darksearch");
				historyTitlePanel.add(historyTitleLabel);
				historyTitlePanel.setCellHorizontalAlignment(historyTitleLabel, HorizontalPanel.ALIGN_LEFT);
				historyTitlePanel.setCellVerticalAlignment(historyTitleLabel, VerticalPanel.ALIGN_MIDDLE);
				historyTitleBox.setName("history" + iCount + "writer" + iWriterCount);
				historyTitlePanel.add(historyTitleBox);
				historyTitlePanel.setCellHorizontalAlignment(historyTitleBox, HorizontalPanel.ALIGN_RIGHT);
				historyTitlePanel.setCellVerticalAlignment(historyTitleBox, VerticalPanel.ALIGN_MIDDLE);
				vHistoryPanel.add(historyTitlePanel);
				tempPanel.add(vHistoryPanel);
				iWriterCount++;
			}

		};
	}

	private ClickListener addDesigner() {
		return new ClickListener() {

			public void onClick(Widget sender) {

				final ListBox historyTitleBox = new ListBox();

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
							historyTitleBox.addItem(title.getDesigner(), String.valueOf(title.getId()));
						}

					}
				};

				titleService.getDesignerList(callback);

				VerticalPanel vHistoryPanel = new VerticalPanel();
				vHistoryPanel.addStyleName("darksearch");
				vHistoryPanel.setSpacing(5);

				HorizontalPanel historyTitlePanel = new HorizontalPanel();
				historyTitlePanel.setWidth("500px");
				historyTitlePanel.setSpacing(10);
				Label historyTitleLabel = new Label("Desenhista: ");
				historyTitleLabel.addStyleName("item");
				historyTitleLabel.addStyleName("darksearch");
				historyTitlePanel.add(historyTitleLabel);
				historyTitlePanel.setCellHorizontalAlignment(historyTitleLabel, HorizontalPanel.ALIGN_LEFT);
				historyTitlePanel.setCellVerticalAlignment(historyTitleLabel, VerticalPanel.ALIGN_MIDDLE);
				historyTitleBox.setName("history" + iCount + "designer" + iDesignerCount);
				historyTitlePanel.add(historyTitleBox);
				historyTitlePanel.setCellHorizontalAlignment(historyTitleBox, HorizontalPanel.ALIGN_RIGHT);
				historyTitlePanel.setCellVerticalAlignment(historyTitleBox, VerticalPanel.ALIGN_MIDDLE);
				vHistoryPanel.add(historyTitlePanel);
				tempDesignerPanel.add(vHistoryPanel);
				iDesignerCount++;
			}

		};
	}

	private ClickListener addLabel() {
		return new ClickListener() {

			public void onClick(Widget sender) {

				final ListBox historyTitleBox = new ListBox();

				if (titleService == null) {
					titleService = GWT.create(TitleService.class);
				}

				AsyncCallback<ArrayList<Labels>> callback = new AsyncCallback<ArrayList<Labels>>() {

					public void onFailure(Throwable caught) {

						Window.alert("DEU PAU!!!");
					}

					public void onSuccess(ArrayList<Labels> result) {

						for (int i = 0; i < result.size(); i++) {
							Labels title = result.get(i);
							historyTitleBox.addItem(title.getLabel(), String.valueOf(title.getId()));
						}

					}
				};

				titleService.getLabelList(callback);

				VerticalPanel vHistoryPanel = new VerticalPanel();
				vHistoryPanel.addStyleName("darksearch");
				vHistoryPanel.setSpacing(5);

				HorizontalPanel historyTitlePanel = new HorizontalPanel();
				historyTitlePanel.setWidth("500px");
				historyTitlePanel.setSpacing(10);
				Label historyTitleLabel = new Label("Label: ");
				historyTitleLabel.addStyleName("item");
				historyTitleLabel.addStyleName("darksearch");
				historyTitlePanel.add(historyTitleLabel);
				historyTitlePanel.setCellHorizontalAlignment(historyTitleLabel, HorizontalPanel.ALIGN_LEFT);
				historyTitlePanel.setCellVerticalAlignment(historyTitleLabel, VerticalPanel.ALIGN_MIDDLE);
				historyTitleBox.setName("history" + iCount + "label" + iLabelCount);
				System.err.println("history" + iCount + "label" + iLabelCount);
				historyTitlePanel.add(historyTitleBox);
				historyTitlePanel.setCellHorizontalAlignment(historyTitleBox, HorizontalPanel.ALIGN_RIGHT);
				historyTitlePanel.setCellVerticalAlignment(historyTitleBox, VerticalPanel.ALIGN_MIDDLE);
				vHistoryPanel.add(historyTitlePanel);
				tempLabelPanel.add(vHistoryPanel);
				iLabelCount++;
			}

		};
	}

}
