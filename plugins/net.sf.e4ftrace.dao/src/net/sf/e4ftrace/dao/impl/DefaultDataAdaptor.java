package net.sf.e4ftrace.dao.impl;

import java.io.File;

import javax.inject.Inject;

import net.sf.e4ftrace.core.ITraceEvent;
import net.sf.e4ftrace.core.impl.TraceEvent;
import net.sf.e4ftrace.dao.ITraceDataAdaptor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;

public class DefaultDataAdaptor implements ITraceDataAdaptor {

	@Override
	public Object create() throws CoreException {
		
		return new DefaultDataAdaptor();
	
	}

	public void run(){
		
		
		System.out.println("DefaultDataAdaptor : run");
	}
	
	@Inject
	@Optional
	private void getNotified(@EventTopic(ITraceEvent.TOPIC_EVENT_UI) TraceEvent event) {
	  
		File file = (File) event.getData();
		System.out.println("DefaultDataAdaptor : getNotified : " + file.getAbsolutePath());
		
		
	}
}
