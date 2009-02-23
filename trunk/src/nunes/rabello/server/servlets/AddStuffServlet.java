package nunes.rabello.server.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nunes.rabello.server.mysql.MySQLManager;

public class AddStuffServlet extends HttpServlet {

	private String stuff;
	private MySQLManager manager;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			manager = new MySQLManager();
		} catch (SQLException e) {
			System.err.println("Não instanciou no AddStuffServlet");
			e.printStackTrace();
		}
		
		/*Roteirista*/
		stuff = request.getParameter("writer");	
		if (stuff != null){
			try {
				manager.insertWriter(stuff);
			} catch (SQLException e) {
				System.err.println("Não conseguiu inserir");
				e.printStackTrace();
			}
		}
		
		/*Desenhista*/
		stuff = request.getParameter("designer");	
		if (stuff != null){
			try {
				manager.insertDesigner(stuff);
			} catch (SQLException e) {
				System.err.println("Não conseguiu inserir");
				e.printStackTrace();
			}
		}
		
		/*Label*/
		stuff = request.getParameter("label");	
		if (stuff != null){
			try {
				manager.insertLabel(stuff);
			} catch (SQLException e) {
				System.err.println("Não conseguiu inserir");
				e.printStackTrace();
			}
		}
		
	}

}
