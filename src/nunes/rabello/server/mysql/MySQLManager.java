package nunes.rabello.server.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import nunes.rabello.client.entities.Designer;
import nunes.rabello.client.entities.History;
import nunes.rabello.client.entities.Labels;
import nunes.rabello.client.entities.Title;
import nunes.rabello.client.entities.Number;
import nunes.rabello.client.entities.Writer;

;

public class MySQLManager {

	protected static final int SESSION_EXPIRE_TIME = 1000 * 60 * 60 * 24;

	// Database finals
	protected final static String DATABASE_DRIVER = "com.mysql.jdbc.Driver";

	// Database info
	protected String dbURL = "jdbc:mysql://localhost/comics";
	protected String dbUser = "root";
	protected String dbPass = "root";

	// Database objects
	protected Connection connection;

	public MySQLManager() throws SQLException {

		try {
			Class.forName(DATABASE_DRIVER);
			connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
		} catch (Exception e) {
			System.err.println("Error creating MySQL database connection.");
			e.printStackTrace();
		}
	}

	public boolean insertTitle(String title, String editora) throws Exception {
		Statement statement = connection.createStatement();

		if (title == null || editora == null) {
			throw new Exception("Title = " + title + " e editora = " + editora);
		}
		String sql = "INSERT INTO titles (title,editora) VALUES (\"" + title + "\",\"" + editora + "\");";
		if (statement.executeUpdate(sql) == 1) {
			return true;
		} else {
			return false;
		}
	}

	public void insertNumbers(String title, String number, String image, ArrayList<History> history) throws Exception {
		Statement statement = connection.createStatement();

		if (title == null || number == null) {
			throw new Exception("Title = " + title + " e number = " + number + " e image = " + image);
		}

		String sql = null;
		if (image == null || image.equals("")) sql = "INSERT INTO numbers (title_id,number) VALUES (" + title + "," + number + ");";
		else sql = "INSERT INTO numbers (title_id,number,image) VALUES (" + title + "," + number + ",\"" + image + "\");";

		System.err.println(sql);
		statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = statement.getGeneratedKeys();

		long number_id = -1;
		if (rs.next()) {
			number_id = rs.getLong(1);
		}

		if (history != null) {
			insertHistory(number_id, history);
		}
	}

	public void insertHistory(long number_id, ArrayList<History> history) throws SQLException {
		String sql = "INSERT INTO histories (number_id,name,history,nota) VALUES (" + number_id + ",\"$name$\",\"$history$\",\"$nota$\")";
		Statement statement = connection.createStatement();
		for (int i = 0; i < history.size(); i++) {
			History newHistory = history.get(i);
			String newSQL = sql.replace("$name$", newHistory.getName());
			newSQL = newSQL.replace("$history$", newHistory.getHistoy());
			newSQL = newSQL.replace("$nota$", newHistory.getNota());
			System.err.println(newSQL);
			ArrayList<Writer> writer = newHistory.getWriter();
			ArrayList<Designer> designer = newHistory.getDesigner();
			ArrayList<Labels> label = newHistory.getLabel();

			statement.executeUpdate(newSQL, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = statement.getGeneratedKeys();

			long history_id = -1;
			if (rs.next()) {
				history_id = rs.getLong(1);
			}

			if (writer != null) insertWriter(history_id, writer);

			if (designer != null) insertDesigner(history_id, designer);

			if (label != null) insertLabel(history_id, label);
		}
	}

	public void insertWriter(long history_id, ArrayList<Writer> writer) throws SQLException {
		String sql = "INSERT INTO history_writer (history_id,writer_id) VALUES (" + history_id + ",$writer$)";

		Statement statement = connection.createStatement();
		for (int i = 0; i < writer.size(); i++) {
			Writer newWriter = writer.get(i);
			String newSQL = sql.replace("$writer$", String.valueOf(newWriter.getId()));
			statement.executeUpdate(newSQL);
		}
	}

	public void insertDesigner(long history_id, ArrayList<Designer> designer) throws SQLException {
		String sql = "INSERT INTO history_designer (history_id,designer_id) VALUES (" + history_id + ",$designer$)";

		Statement statement = connection.createStatement();
		for (int i = 0; i < designer.size(); i++) {
			Designer newWriter = designer.get(i);
			String newSQL = sql.replace("$designer$", String.valueOf(newWriter.getId()));
			statement.executeUpdate(newSQL);
		}
	}

	public void insertLabel(long history_id, ArrayList<Labels> label) throws SQLException {
		String sql = "INSERT INTO history_label (history_id,label_id) VALUES (" + history_id + ",$label$)";

		Statement statement = connection.createStatement();
		for (int i = 0; i < label.size(); i++) {
			Labels newWriter = label.get(i);
			String newSQL = sql.replace("$label$", String.valueOf(newWriter.getId()));
			statement.executeUpdate(newSQL);
		}
	}

	public ArrayList<Title> getTitles() throws SQLException {
		ArrayList<Title> result = new ArrayList<Title>();

		String sql = "SELECT * FROM titles ORDER BY title";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Title title = new Title();
			title.setId(rs.getInt("id"));
			title.setTitle(rs.getString("title"));
			title.setEditora(rs.getString("editora"));
			result.add(title);
		}
		return result;
	}

	public ArrayList<Writer> getWriters() throws SQLException {
		ArrayList<Writer> result = new ArrayList<Writer>();

		String sql = "SELECT * FROM writers ORDER BY writer";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Writer title = new Writer();
			title.setId(rs.getInt("id"));
			title.setWriter(rs.getString("writer"));
			result.add(title);
		}
		return result;
	}

	public ArrayList<Designer> getDesigners() throws SQLException {
		ArrayList<Designer> result = new ArrayList<Designer>();

		String sql = "SELECT * FROM designers ORDER BY designer";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Designer title = new Designer();
			title.setId(rs.getInt("id"));
			title.setDesigner(rs.getString("designer"));
			result.add(title);
		}
		return result;
	}

	public ArrayList<Labels> getLabel() throws SQLException {
		ArrayList<Labels> result = new ArrayList<Labels>();

		String sql = "SELECT * FROM labels ORDER BY label";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Labels title = new Labels();
			title.setId(rs.getInt("id"));
			title.setLabel(rs.getString("label"));
			result.add(title);
		}
		return result;
	}

	public ArrayList<Title> getList() throws SQLException {
		ArrayList<Title> result = new ArrayList<Title>();

		String sql = "SELECT * FROM titles";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Title title = new Title();
			title.setId(rs.getInt("id"));
			title.setTitle(rs.getString("title"));
			title.setEditora(rs.getString("editora"));
			title.setNumber(getNumbers(rs.getInt("id")));
			result.add(title);
		}
		return result;
	}

	public ArrayList<Title> getSearchList(String search, int option) throws SQLException {
		ArrayList<Title> result = new ArrayList<Title>();
		String sql = "SELECT * FROM titles";
		if (option == 1) {
			sql = "SELECT * FROM titles WHERE title LIKE \"" + search + "\"";
		}
		Statement statement = connection.createStatement();
		System.err.println(sql);
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {

			if (option == 1) {
				Title title = new Title();
				title.setId(rs.getInt("id"));
				title.setTitle(rs.getString("title"));
				title.setEditora(rs.getString("editora"));
				title.setNumber(getNumbers(rs.getInt("id")));
				result.add(title);
			} else {
				ArrayList<Number> numero = getSearchNumbers(rs.getInt("id"), option, search);
				if (numero != null) {
					Title title = new Title();
					title.setNumber(numero);
					title.setId(rs.getInt("id"));
					title.setTitle(rs.getString("title"));
					title.setEditora(rs.getString("editora"));
					result.add(title);
				}
			}

		}
		return result;
	}

	public ArrayList<Number> getNumbers(int title_id) throws SQLException {
		ArrayList<Number> result = null;
		String sql = "SELECT * FROM numbers WHERE title_id=" + title_id + " ORDER BY number";
		System.err.println(sql);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			if (result == null) result = new ArrayList<Number>();
			Number num = new Number();
			num.setTitleId(title_id);
			num.setNumber(rs.getInt("number"));
			num.setImage(rs.getString("image"));
			num.setId(rs.getInt("id"));
			num.setHistory(getHistories(rs.getInt("id")));
			result.add(num);
		}

		System.err.println(result);
		return result;
	}

	public ArrayList<Number> getSearchNumbers(int title_id, int option, String search) throws SQLException {
		ArrayList<Number> result = null;
		String sql = "SELECT * FROM numbers WHERE title_id=" + title_id + " ORDER BY number";
		System.err.println(sql);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			ArrayList<History> alHist = getHistories(rs.getInt("id"), search, option);
			if (alHist != null) {
				if (result == null) result = new ArrayList<Number>();
				Number num = new Number();
				num.setTitleId(title_id);
				num.setNumber(rs.getInt("number"));
				num.setImage(rs.getString("image"));
				num.setId(rs.getInt("id"));
				num.setHistory(alHist);
				result.add(num);
			}
		}

		System.err.println(result);
		return result;
	}

	public ArrayList<History> getHistories(int number_id) throws SQLException {
		ArrayList<History> result = null;
		String sql = "SELECT * FROM histories WHERE number_id=" + number_id;
		System.err.println(sql);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			if (result == null) {
				result = new ArrayList<History>();
			}
			History hist = new History();
			hist.setId(rs.getInt("id"));
			hist.setName(rs.getString("name"));
			hist.setHistoy(rs.getString("history"));
			hist.setWriter(getWriters(rs.getInt("id")));
			hist.setDesigner(getDesigners(rs.getInt("id")));
			hist.setLabel(getLabel(rs.getInt("id")));
			hist.setNota(rs.getString("nota"));
			result.add(hist);
		}

		return result;
	}

	public ArrayList<History> getHistories(int number_id, String search, int option) throws SQLException {
		ArrayList<History> result = null;
		String sql = "SELECT * FROM histories WHERE number_id=" + number_id;
		System.err.println(sql);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {

			if (option == 3) {
				if (getSearchWriters(rs.getInt("id"), search)) {
					History hist = new History();
					hist.setId(rs.getInt("id"));
					hist.setName(rs.getString("name"));
					hist.setHistoy(rs.getString("history"));
					hist.setWriter(getWriters(rs.getInt("id")));
					hist.setDesigner(getDesigners(rs.getInt("id")));
					hist.setLabel(getLabel(rs.getInt("id")));
					hist.setNota(rs.getString("nota"));
					if (result == null) {
						result = new ArrayList<History>();
					}
					result.add(hist);
				}
			}

			if (option == 4) {
				if (getSearchDesigners(rs.getInt("id"), search)) {
					History hist = new History();
					hist.setId(rs.getInt("id"));
					hist.setName(rs.getString("name"));
					hist.setHistoy(rs.getString("history"));
					hist.setWriter(getWriters(rs.getInt("id")));
					hist.setDesigner(getDesigners(rs.getInt("id")));
					hist.setLabel(getLabel(rs.getInt("id")));
					hist.setNota(rs.getString("nota"));
					if (result == null) {
						result = new ArrayList<History>();
					}
					result.add(hist);
				}
			}

			if (option == 2) {
				if (getSearchLabel(rs.getInt("id"), search)) {
					History hist = new History();
					hist.setId(rs.getInt("id"));
					hist.setName(rs.getString("name"));
					hist.setHistoy(rs.getString("history"));
					hist.setWriter(getWriters(rs.getInt("id")));
					hist.setDesigner(getDesigners(rs.getInt("id")));
					hist.setLabel(getLabel(rs.getInt("id")));
					hist.setNota(rs.getString("nota"));
					if (result == null) {
						result = new ArrayList<History>();
					}
					result.add(hist);
				}
			}
		}

		return result;
	}

	public void insertWriter(String writer) throws SQLException {
		String sql = "INSERT INTO writers (writer) VALUE (\"" + writer + "\")";
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
	}

	public void insertLabel(String label) throws SQLException {
		String sql = "INSERT INTO labels (label) VALUE (\"" + label + "\")";
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
	}

	public void insertDesigner(String designer) throws SQLException {
		String sql = "INSERT INTO designers (designer) VALUE (\"" + designer + "\")";
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
	}

	public ArrayList<Writer> getWriters(int history_id) throws SQLException {
		ArrayList<Writer> result = new ArrayList<Writer>();

		String sql = "SELECT w.id,w.writer FROM history_writer hw, histories h, writers w WHERE h.id=hw.history_id AND w.id=hw.writer_id AND h.id="
				+ history_id;
		Statement statement = connection.createStatement();
		System.err.println(sql);
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Writer title = new Writer();
			title.setId(rs.getInt("w.id"));
			title.setWriter(rs.getString("w.writer"));
			result.add(title);
		}
		return result;
	}

	public ArrayList<Designer> getDesigners(int history_id) throws SQLException {
		ArrayList<Designer> result = new ArrayList<Designer>();

		String sql = "SELECT d.id,d.designer FROM history_designer hd, histories h, designers d WHERE h.id=hd.history_id AND d.id=hd.designer_id AND h.id="
				+ history_id;
		Statement statement = connection.createStatement();
		System.err.println(sql);
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Designer title = new Designer();
			title.setId(rs.getInt("d.id"));
			title.setDesigner(rs.getString("d.designer"));
			result.add(title);
		}
		return result;
	}

	public ArrayList<Labels> getLabel(int history_id) throws SQLException {
		ArrayList<Labels> result = new ArrayList<Labels>();

		String sql = "SELECT l.id,l.label FROM history_label hl, histories h, labels l WHERE h.id=hl.history_id AND l.id=hl.label_id AND h.id="
				+ history_id;
		Statement statement = connection.createStatement();
		System.err.println(sql);
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Labels title = new Labels();
			title.setId(rs.getInt("l.id"));
			title.setLabel(rs.getString("l.label"));
			result.add(title);
		}
		return result;
	}

	public Boolean getSearchWriters(int history_id, String search) throws SQLException {

		String sql = "SELECT w.writer FROM history_writer hw, histories h, writers w WHERE h.id=hw.history_id AND w.id=hw.writer_id AND h.id="
				+ history_id + " AND w.writer LIKE \"" + search + "\"";
		System.err.println(sql);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		if (rs.next()) return true;
		else return false;
	}

	public Boolean getSearchDesigners(int history_id, String search) throws SQLException {

		String sql = "SELECT d.designer FROM history_designer hd, histories h, designers d WHERE h.id=hd.history_id AND d.id=hd.designer_id AND h.id="
				+ history_id + " AND d.designer LIKE \"" + search + "\"";
		Statement statement = connection.createStatement();
		System.err.println(sql);
		ResultSet rs = statement.executeQuery(sql);

		if (rs.next()) return true;
		else return false;
	}

	public Boolean getSearchLabel(int history_id, String search) throws SQLException {

		String sql = "SELECT l.label FROM history_label hl, histories h, labels l WHERE h.id=hl.history_id AND l.id=hl.label_id AND h.id="
				+ history_id + " AND l.label LIKE \"" + search + "\"";
		Statement statement = connection.createStatement();
		System.err.println(sql);
		ResultSet rs = statement.executeQuery(sql);

		if (rs.next()) return true;
		else return false;
	}
	
	public Boolean hasWriters(String search) throws SQLException {

		String sql = "SELECT id FROM writers WHERE writer=\"" + search + "\"";
		System.err.println(sql);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		if (rs.next()) return true;
		else return false;
	}

	public Boolean hasDesigners(String search) throws SQLException {

		String sql = "SELECT id FROM designers WHERE designer=\"" + search + "\"";
		Statement statement = connection.createStatement();
		System.err.println(sql);
		ResultSet rs = statement.executeQuery(sql);

		if (rs.next()) return true;
		else return false;
	}

	public Boolean hasLabel(String search) throws SQLException {

		String sql = "SELECT id FROM labels WHERE label=\"" + search + "\"";
		Statement statement = connection.createStatement();
		System.err.println(sql);
		ResultSet rs = statement.executeQuery(sql);

		if (rs.next()) return true;
		else return false;
	}

}
