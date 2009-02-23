package nunes.rabello.client.entities;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Labels implements IsSerializable {
	
	protected int id;
	protected String label;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
