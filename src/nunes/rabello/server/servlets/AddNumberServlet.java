package nunes.rabello.server.servlets;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nunes.rabello.server.mysql.MySQLManager;
import nunes.rabello.client.entities.Designer;
import nunes.rabello.client.entities.History;
import nunes.rabello.client.entities.Labels;
import nunes.rabello.client.entities.Writer;

public class AddNumberServlet extends HttpServlet {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1674825754025910873L;

	private static final String destiny = "C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 5.5\\webapps\\nunes.rabello.Revistas\\images\\capas\\";
	private static final String origem = "C:\\Users\\Gabriel\\Pictures\\capas\\";

	protected String image = null;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String title = null;
		String number = null;

		title = request.getParameter("title");
		number = request.getParameter("number");

		if ((request.getParameter("image") == null) || (request.getParameter("image").indexOf(".jpg") == -1)) image = null;
		else {
			image = origem + request.getParameter("image");
			saveImage(image);
		}

		ArrayList<History> history = null;
		int iCount = 0;

		while (request.getParameter("historytitle" + iCount) != null) {
			if (history == null) history = new ArrayList<History>();
			History newHistory = new History();
			newHistory.setName(request.getParameter("historytitle" + iCount));
			newHistory.setHistoy(request.getParameter("historyhistory" + iCount));
			newHistory.setNota(request.getParameter("historynota" + iCount));

			String parameter = "history" + (iCount + 1);

			ArrayList<Writer> alWriter = null;
			int iWriteCount = 0;
			while (request.getParameter(parameter + "writer" + iWriteCount) != null) {
				if (alWriter == null) {
					alWriter = new ArrayList<Writer>();
				}
				Writer writer = new Writer();
				writer.setId(Integer.valueOf(request.getParameter(parameter + "writer" + iWriteCount)));
				alWriter.add(writer);
				iWriteCount++;
			}
			if (alWriter != null) newHistory.setWriter(alWriter);

			ArrayList<Designer> alDesigner = null;
			int iDesignerCount = 0;
			while (request.getParameter(parameter + "designer" + iDesignerCount) != null) {
				if (alDesigner == null) {
					alDesigner = new ArrayList<Designer>();
				}
				Designer writer = new Designer();
				writer.setId(Integer.valueOf(request.getParameter(parameter + "designer" + iDesignerCount)));
				alDesigner.add(writer);
				iDesignerCount++;
			}
			if (alDesigner != null) newHistory.setDesigner(alDesigner);

			ArrayList<Labels> alLabel = null;
			int iLabelCount = 0;
			while (request.getParameter(parameter + "label" + iLabelCount) != null) {

				if (alLabel == null) {
					alLabel = new ArrayList<Labels>();
				}
				Labels writer = new Labels();
				writer.setId(Integer.valueOf(request.getParameter(parameter + "label" + iLabelCount)));
				alLabel.add(writer);
				iLabelCount++;
			}
			if (alLabel != null) newHistory.setLabel(alLabel);

			history.add(newHistory);
			iCount++;
		}

		try {
			MySQLManager manager = new MySQLManager();
			manager.insertNumbers(title, number, image, history);
		} catch (SQLException e) {
			System.err.println("Error connecting to database.");
			System.err.println(title + number + image);
			e.printStackTrace();
			return;
		} catch (Exception e) {
			System.err.println("Um dos três é nullo");
			e.printStackTrace();
		}

	}

	private void saveImage(String path) {
		File file = new File(path);
		OutputStream out = null;
		String destination = destiny + file.getName();

		FileInputStream in = null;
		try {
			try {
				in = new FileInputStream(file);
			} catch (java.io.FileNotFoundException e) {
				System.out.println("Não tem o arquivo " + path);
				return;
			}
			out = new BufferedOutputStream(new FileOutputStream(destination));
			byte[] buffer = new byte[1024];
			int numRead;
			long numWritten = 0;
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
				numWritten += numRead;
			}
			image = "images/capas/" + file.getName();
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				if (in != null) in.close();

				if (out != null) out.close();

			} catch (IOException ioe) {
			}
		}
	}

}
