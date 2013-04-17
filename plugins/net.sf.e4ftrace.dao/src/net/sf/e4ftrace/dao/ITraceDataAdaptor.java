package net.sf.e4ftrace.dao;

import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.osgi.service.event.EventHandler;

public interface ITraceDataAdaptor extends IExecutableExtensionFactory, EventHandler{

	public void run();
	
}
