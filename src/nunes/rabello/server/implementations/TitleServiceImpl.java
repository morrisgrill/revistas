package nunes.rabello.server.implementations;

import java.sql.SQLException;
import java.util.ArrayList;

import nunes.rabello.client.entities.Designer;
import nunes.rabello.client.entities.Labels;
import nunes.rabello.client.entities.Title;
import nunes.rabello.client.entities.Writer;
import nunes.rabello.client.rpc.TitleService;
import nunes.rabello.server.mysql.MySQLManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TitleServiceImpl extends RemoteServiceServlet implements
		TitleService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4420773426200099599L;

	@Override
	public ArrayList<Title> getTitleList() {
		ArrayList<Title> result = null;
		
		try {
			MySQLManager manager = new MySQLManager();
			result = manager.getTitles();
		} catch (SQLException e) {
			System.err.println("Error connecting to database.");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<Designer> getDesignerList() {
		ArrayList<Designer> result = null;
		
		try {
			MySQLManager manager = new MySQLManager();
			result = manager.getDesigners();
		} catch (SQLException e) {
			System.err.println("Error connecting to database.");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<Labels> getLabelList() {
		ArrayList<Labels> result = null;
		try {
			MySQLManager manager = new MySQLManager();
			result = manager.getLabel();
		} catch (SQLException e) {
			System.err.println("Error connecting to database.");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<Writer> getWriterList() {
		ArrayList<Writer> result = null;
		try {
			MySQLManager manager = new MySQLManager();
			result = manager.getWriters();
		} catch (SQLException e) {
			System.err.println("Error connecting to database.");
			e.printStackTrace();
		}
		return result;
	}

}
