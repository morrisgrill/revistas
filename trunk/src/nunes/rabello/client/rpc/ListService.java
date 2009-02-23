package nunes.rabello.client.rpc;

import java.util.ArrayList;

import nunes.rabello.client.entities.Title;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("getlist")
public interface ListService extends RemoteService {
	
	ArrayList<Title> getTitleList();
	ArrayList<Title> getLista(String busca, int opcao);
	Boolean getDesigner(String item);
	Boolean getWriter(String item);
	Boolean getLabel(String item);

}
