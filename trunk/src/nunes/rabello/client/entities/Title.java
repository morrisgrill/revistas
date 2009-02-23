package nunes.rabello.client.entities;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Title implements IsSerializable {
	protected int id;
	protected String title;
	protected String editora;
	protected ArrayList<Number> number;

	public ArrayList<Number> getNumber() {
		return number;
	}

	public void setNumber(ArrayList<Number> number) {
		this.number = number;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}
}
