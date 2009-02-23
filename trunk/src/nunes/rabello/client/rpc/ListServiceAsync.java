package nunes.rabello.client.rpc;

import java.util.ArrayList;

import nunes.rabello.client.entities.Title;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ListServiceAsync {
	
	void getTitleList(AsyncCallback<ArrayList<Title>> callback);
	void getLista(String busca, int opcao ,AsyncCallback<ArrayList<Title>> callback);
	void getDesigner(String item,AsyncCallback<Boolean> callback);
	void getWriter(String item,AsyncCallback<Boolean> callback);
	void getLabel(String item,AsyncCallback<Boolean> callback);

}
