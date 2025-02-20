package net.sf.e4ftrace.core.model.impl;

import java.util.Iterator;
import java.util.Vector;

import net.sf.e4ftrace.core.model.IEvent;
import net.sf.e4ftrace.core.model.ITrace;

public class Trace implements ITrace {

	private String name = "traceDefaultName";
	
	private Vector<IEvent> traceEvents = new Vector<IEvent>();
	
	public Trace(String name) {
		super();
		this.name = name;
	}

	public Iterator<IEvent> getEventsIterator() {
        return traceEvents.iterator();
    }

    public void addTraceEvent(IEvent event) {
        traceEvents.add(event);
    }
}
