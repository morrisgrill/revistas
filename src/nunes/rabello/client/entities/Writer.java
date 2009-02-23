package nunes.rabello.client.entities;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Writer implements IsSerializable {

	protected int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	protected String writer;

}
