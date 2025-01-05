package agents.gui;

public class GuiEvent {
	public static final int MESSAGE_SENT = 1;
	public static final int ACTIVE_USERS_UPDATED = 2;

	private int type;
	private Object data;

	public GuiEvent(int type, Object data) {
		this.type = type;
		this.data = data;
	}

	public int getType() {
		return type;
	}

	public Object getData() {
		return data;
	}
}
