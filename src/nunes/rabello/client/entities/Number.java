package nunes.rabello.client.entities;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Number implements IsSerializable {

	protected int id;
	protected int titleId;
	protected int number;
	protected String image;
	protected ArrayList<History> history;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ArrayList<History> getHistory() {
		return history;
	}

	public void setHistory(ArrayList<History> history) {
		this.history = history;
	}
}