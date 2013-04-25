package net.sf.e4ftrace.core.model.impl;

import net.sf.e4ftrace.core.model.IEvent;

public class Event implements IEvent {

	private long time = 0;

	public Event(long time) {
		super();
		this.time = time;
	}
	
	
}
