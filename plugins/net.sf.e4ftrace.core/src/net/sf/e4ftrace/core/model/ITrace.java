package net.sf.e4ftrace.core.model;

import java.util.Iterator;

public interface ITrace {

	public Iterator<IEvent> getEventsIterator();
	
	public void addTraceEvent(IEvent event);
}
