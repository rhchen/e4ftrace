package net.sf.e4ftrace.core.uievent.impl;

import net.sf.e4ftrace.core.uievent.IUIEvent;

public class UIEvent implements IUIEvent {

	private Object data;

	public UIEvent(Object data) {
		super();
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
