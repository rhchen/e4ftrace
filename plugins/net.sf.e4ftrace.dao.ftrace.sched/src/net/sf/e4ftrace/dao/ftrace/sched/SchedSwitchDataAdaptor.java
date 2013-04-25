package net.sf.e4ftrace.dao.ftrace.sched;

import java.io.File;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Maps;
import com.google.common.collect.TreeBasedTable;

import net.sf.e4ftrace.core.model.ITrace;
import net.sf.e4ftrace.core.uievent.IUIEvent;
import net.sf.e4ftrace.dao.ITraceDataAdaptor;
import net.sf.e4ftrace.dao.ftrace.sched.cache.SchedSwitchCache;

public class SchedSwitchDataAdaptor implements ITraceDataAdaptor {

	private static ConcurrentMap<URI, SchedSwitchCache> cacheMap = Maps.<URI, SchedSwitchCache>newConcurrentMap();
	
	@Override
	public void run() {
		
		System.out.println("SchedSwitchDataAdaptor : run");

	}
	
	@Override
	public Object create() throws CoreException {
		
		return new SchedSwitchDataAdaptor();
	}

	@Override
	public void handleEvent(Event event) {
		
		Object o = event.getProperty(IEventBroker.DATA);
		
		if(o instanceof IUIEvent){
			
			IUIEvent e = (IUIEvent) o;
			
			final File file = (File) e.getData();
			
			System.out.println("SchedSwitchDataAdaptor : handleEvent "+ e.getData());
			
		}
		
	}
	
	@Override
	public void setCurrentTrace(URI uri,  TreeBasedTable<Integer, Long, Long> pageTable, FileChannel fileChannel) {
		
		SchedSwitchCache cache = new SchedSwitchCache();
		
		cache.init(fileChannel, pageTable);
		
		cacheMap.put(uri, cache);
		
		System.out.println("SchedSwitchDataAdaptor : setCurrentTrace : "+ uri);
		
		
	}

	@Override
	public ImmutableTable<Integer, Short, ITrace> get(URI uri, Integer pageNum)
			throws ExecutionException {
		
		return cacheMap.get(uri).get(pageNum);
		
	}
	

}
