package nunes.rabello.server.implementations;

import java.sql.SQLException;
import java.util.ArrayList;

import nunes.rabello.client.entities.Title;
import nunes.rabello.client.rpc.ListService;
import nunes.rabello.server.mysql.MySQLManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ListServiceImpl extends RemoteServiceServlet implements
		ListService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6797302471103747378L;

	@Override
	public ArrayList<Title> getTitleList() {
		ArrayList<Title> result = null;
		try {
			MySQLManager manager = new MySQLManager();
			result = manager.getList();
		} catch (SQLException e) {
			System.err.println("Deu pau pra pegar a lista");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<Title> getLista(String busca, int opcao) {
		ArrayList<Title> result = null;
		try {
			System.err.println("VAI INICIAR A BUSCA");
			MySQLManager manager = new MySQLManager();
			result = manager.getSearchList(busca, opcao);
		} catch (SQLException e) {
			System.err.println("Deu pau pra pegar a lista");
			e.printStackTrace();
		}
		System.err.println(result);
		return result;
	}

	@Override
	public Boolean getDesigner(String item) {
		try {
			MySQLManager manager = new MySQLManager();
			return manager.hasDesigners(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean getLabel(String item) {
		try {
			MySQLManager manager = new MySQLManager();
			return manager.hasLabel(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean getWriter(String item) {
		try {
			MySQLManager manager = new MySQLManager();
			return manager.hasWriters(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
