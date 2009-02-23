package nunes.rabello.client.rpc;

import java.util.ArrayList;

import nunes.rabello.client.entities.Designer;
import nunes.rabello.client.entities.Labels;
import nunes.rabello.client.entities.Title;
import nunes.rabello.client.entities.Writer;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gettitle")
public interface TitleService extends RemoteService {
	
	ArrayList<Title> getTitleList();
	ArrayList<Labels> getLabelList();
	ArrayList<Writer> getWriterList();
	ArrayList<Designer> getDesignerList();

}
