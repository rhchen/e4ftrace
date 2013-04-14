package net.sf.e4ftrace.core.impl;

import net.sf.e4ftrace.core.ITraceEvent;

public class TraceEvent implements ITraceEvent {

	private Object data;

	public TraceEvent(Object data) {
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
