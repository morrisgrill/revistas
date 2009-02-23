package nunes.rabello.client.entities;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Designer implements IsSerializable {

	protected int id;
	protected String designer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesigner() {
		return designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}
}
