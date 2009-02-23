package nunes.rabello.client.entities;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class History implements IsSerializable {

	protected int id;
	protected int number_id;
	protected String name;
	protected String histoy;
	protected String nota;
	protected ArrayList<Writer> writer;
	protected ArrayList<Designer> designer;
	protected ArrayList<Labels> label;

	public ArrayList<Writer> getWriter() {
		return writer;
	}

	public void setWriter(ArrayList<Writer> writer) {
		this.writer = writer;
	}

	public ArrayList<Designer> getDesigner() {
		return designer;
	}

	public void setDesigner(ArrayList<Designer> designer) {
		this.designer = designer;
	}

	public ArrayList<Labels> getLabel() {
		return label;
	}

	public void setLabel(ArrayList<Labels> label) {
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber_id() {
		return number_id;
	}

	public void setNumber_id(int number_id) {
		this.number_id = number_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHistoy() {
		return histoy;
	}

	public void setHistoy(String histoy) {
		this.histoy = histoy;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
}
