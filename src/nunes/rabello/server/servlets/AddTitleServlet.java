package nunes.rabello.server.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nunes.rabello.server.mysql.MySQLManager;

public class AddTitleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3926441591437351936L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {


		String title = null;
		String editora = null;

		title = request.getParameter("title");
		editora = request.getParameter("editora");

		try {
			MySQLManager manager = new MySQLManager();
			manager.insertTitle(title, editora);
		} catch (SQLException e) {
			System.err.println("Error connecting to database.");
			e.printStackTrace();
			return;
		} catch (Exception e) {
			System.err.println("Um dos dois é nullo");
			e.printStackTrace();
		}

	}

}
