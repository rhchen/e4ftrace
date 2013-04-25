package net.sf.e4ftrace.dao.impl;

import java.io.File;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

import net.sf.e4ftrace.core.model.ITrace;
import net.sf.e4ftrace.core.uievent.IUIEvent;
import net.sf.e4ftrace.core.uievent.impl.UIEvent;
import net.sf.e4ftrace.dao.ITraceDataAdaptor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.TreeBasedTable;

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
		
		if(o instanceof IUIEvent){
			
			IUIEvent e = (IUIEvent) o;
			
			System.out.println("DefaultDataAdaptor : handleEvent "+ e.getData());
		}
		
	}

	@Override
	public void setCurrentTrace(URI uri, TreeBasedTable<Integer, Long, Long> pageTable, FileChannel fileChannel) {
		
		
	}

	@Override
	public ImmutableTable<Integer, Short, ITrace> get(URI uri, Integer pageNum)
			throws ExecutionException {
		
		return null;
	}
}
