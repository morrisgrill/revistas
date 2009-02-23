package nunes.rabello.client.rpc;

import java.util.ArrayList;

import nunes.rabello.client.entities.Designer;
import nunes.rabello.client.entities.Labels;
import nunes.rabello.client.entities.Title;
import nunes.rabello.client.entities.Writer;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TitleServiceAsync {

	void getTitleList(AsyncCallback<ArrayList<Title>> callback);
	void getWriterList(AsyncCallback<ArrayList<Writer>> callback);
	void getLabelList(AsyncCallback<ArrayList<Labels>> callback);
	void getDesignerList(AsyncCallback<ArrayList<Designer>> callback);
	
}
