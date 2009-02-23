package nunes.rabello.client.composite;

import java.util.ArrayList;

import nunes.rabello.client.entities.Designer;
import nunes.rabello.client.entities.History;
import nunes.rabello.client.entities.Labels;
import nunes.rabello.client.entities.Title;
import nunes.rabello.client.entities.Number;
import nunes.rabello.client.entities.Writer;
import nunes.rabello.client.rpc.ListService;
import nunes.rabello.client.rpc.ListServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ListComics extends Composite {

	protected VerticalPanel listComicsPanel;

	protected HorizontalPanel searchPanel;
	protected VerticalPanel searchStylePanel;
	protected Label searchLabel;
	protected TextBox searchBox;
	protected ListBox searchList;
	protected Button searchButton;

	protected ListServiceAsync listService;
	protected Tree titleTree;

	public ListComics() {
		listComicsPanel = new VerticalPanel();

		/* Search */

		searchStylePanel = new VerticalPanel();
		searchStylePanel.setWidth("1265px");
		searchStylePanel.addStyleName("search");
		searchPanel = new HorizontalPanel();
		searchPanel.setWidth("100px");
		searchPanel.setSpacing(1);
		searchLabel = new Label("Busca");
		searchBox = new TextBox();
		searchBox.setName("search");
		searchList = new ListBox();
		searchList.setTitle("searchType");
		searchList.addItem("Título", "1");
		searchList.addItem("Label", "2");
		searchList.addItem("Roteirista", "3");
		searchList.addItem("Desenhista", "4");
		searchButton = new Button("Busca");
		searchButton.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				if (searchBox.getText().equals("")) {
					Window.alert("O campo de busca está vazio");
					return;
				}

				if (listService == null) {
					listService = GWT.create(ListService.class);
				}

				AsyncCallback<ArrayList<Title>> callback = new AsyncCallback<ArrayList<Title>>() {

					public void onFailure(Throwable caught) {
						Window.alert("Não deu certo a busca");

					}

					public void onSuccess(ArrayList<Title> result) {
						listComicsPanel.clear();
						listComicsPanel.add(searchStylePanel);
						listComicsPanel.add(doTree(result));

					}

				};

				listService.getLista(searchBox.getText(), Integer.valueOf(searchList.getValue(searchList.getSelectedIndex())), callback);

			}

		});

		searchPanel.add(searchLabel);
		searchPanel.setCellHorizontalAlignment(searchLabel, HorizontalPanel.ALIGN_LEFT);
		searchPanel.setCellVerticalAlignment(searchLabel, VerticalPanel.ALIGN_MIDDLE);
		searchPanel.add(searchBox);
		searchPanel.setCellHorizontalAlignment(searchBox, HorizontalPanel.ALIGN_LEFT);
		searchPanel.setCellVerticalAlignment(searchBox, VerticalPanel.ALIGN_MIDDLE);
		searchPanel.add(searchList);
		searchPanel.setCellHorizontalAlignment(searchList, HorizontalPanel.ALIGN_LEFT);
		searchPanel.setCellVerticalAlignment(searchList, VerticalPanel.ALIGN_MIDDLE);
		searchPanel.add(searchButton);
		searchPanel.setCellHorizontalAlignment(searchButton, HorizontalPanel.ALIGN_LEFT);
		searchPanel.setCellVerticalAlignment(searchButton, VerticalPanel.ALIGN_MIDDLE);

		searchStylePanel.add(searchPanel);
		listComicsPanel.add(searchStylePanel);
		listComicsPanel.add(doTree(null));

		initWidget(listComicsPanel);
	}

	private VerticalPanel doTree(ArrayList<Title> lista) {

		VerticalPanel treePanel = new VerticalPanel();
		treePanel.setWidth("1150px");
		titleTree = new Tree();
		titleTree.setWidth("500px");
		titleTree.setAnimationEnabled(true);

		if (lista == null) {
			// RPC call
			if (listService == null) {
				listService = GWT.create(ListService.class);
			}

			AsyncCallback<ArrayList<Title>> callback = new AsyncCallback<ArrayList<Title>>() {

				public void onFailure(Throwable caught) {

					Window.alert("DEU PAU!!!");
				}

				public void onSuccess(ArrayList<Title> result) {
					tree(result);
				}

			};

			listService.getTitleList(callback);
		} else {
			tree(lista);
		}

		treePanel.add(titleTree);
		return treePanel;

	}

	public void tree(ArrayList<Title> result) {
		for (int i = 0; i < result.size(); i++) {
			Title title = result.get(i);
			TreeItem titleTreeItem = titleTree.addItem(title.getTitle() + " - " + title.getEditora());
			ArrayList<Number> number = title.getNumber();
			if (number != null) {
				for (int j = 0; j < number.size(); j++) {
					Number num = number.get(j);
					TreeItem numberTreeItem = titleTreeItem.addItem(String.valueOf(num.getNumber()));
					numberTreeItem.addStyleName("item");
					VerticalPanel vTreeItem = new VerticalPanel();
					vTreeItem.setWidth("500px");
					HorizontalPanel hTreeItem = new HorizontalPanel();
					hTreeItem.setWidth("500px");
					hTreeItem.setSpacing(5);
					Image iTreeItem = new Image(num.getImage());
					hTreeItem.add(iTreeItem);
					vTreeItem.add(hTreeItem);

					if (num.getHistory() != null) {
						hTreeItem = new HorizontalPanel();
						ArrayList<History> hist = num.getHistory();
						Tree historyTree = new Tree();
						for (int k = 0; k < hist.size(); k++) {
							History history = hist.get(k);
							TreeItem histTreeItem = historyTree.addItem(history.getName());
							histTreeItem.setWidth("500px");
							VerticalPanel historyPanel = new VerticalPanel();
							historyPanel.setWidth("500px");
							historyPanel.setSpacing(7);
							historyPanel.add(new Label("História:  " + history.getHistoy()));
							Tree arvore = new Tree();
							historyPanel.add(arvore);
							if (history.getWriter() != null) {
								ArrayList<Writer> writer = history.getWriter();
								if (writer.size() != 0) {
									TreeItem stuff = arvore.addItem("Roteirista(s)");
									for (int i1 = 0; i1 < writer.size(); i1++) {
										stuff.addItem(new Label(writer.get(i1).getWriter()));
									}
								}
							}
							if (history.getDesigner() != null) {
								ArrayList<Designer> writer = history.getDesigner();
								if (writer.size() != 0) {
									TreeItem stuff = arvore.addItem("Desenhista(s)");
									for (int i1 = 0; i1 < writer.size(); i1++) {
										stuff.addItem(new Label(writer.get(i1).getDesigner()));
									}
								}
							}
							if (history.getLabel() != null) {
								ArrayList<Labels> writer = history.getLabel();
								if (writer.size() != 0) {
									TreeItem stuff = arvore.addItem("Label(s)");
									for (int i1 = 0; i1 < writer.size(); i1++) {
										stuff.addItem(new Label(writer.get(i1).getLabel()));
									}
								}
							}
							historyPanel.add(new Label("Nota:  " + history.getNota()));
							histTreeItem.addItem(historyPanel);
						}
						hTreeItem.add(historyTree);
						vTreeItem.add(hTreeItem);
					}

					numberTreeItem.addItem(vTreeItem);

				}
			}
		}
	}

}
