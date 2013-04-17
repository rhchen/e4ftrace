package net.sf.e4ftrace.dao.impl;

import java.io.File;

import net.sf.e4ftrace.core.ITraceEvent;
import net.sf.e4ftrace.core.impl.TraceEvent;
import net.sf.e4ftrace.dao.ITraceDataAdaptor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class DefaultDataAdaptor implements ITraceDataAdaptor {

	@Override
	public Object create() throws CoreException {
		
		return new DefaultDataAdaptor();
	
	}

	public void run(){
		
		System.out.println("DefaultDataAdaptor : run");
	}
	
	@Override
	public void handleEvent(Event event) {
		
		Object o = event.getProperty(IEventBroker.DATA);
		
		if(o instanceof ITraceEvent){
			
			ITraceEvent e = (ITraceEvent) o;
			
			System.out.println("DefaultDataAdaptor : handleEvent "+ e.getData());
		}
		
		System.out.println("DefaultDataAdaptor : handleEvent");
	}
}
