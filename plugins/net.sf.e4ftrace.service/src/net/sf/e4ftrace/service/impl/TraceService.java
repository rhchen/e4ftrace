package net.sf.e4ftrace.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.di.UIEventTopic;

import net.sf.e4ftrace.core.ITraceEvent;
import net.sf.e4ftrace.core.impl.TraceEvent;
import net.sf.e4ftrace.service.ITraceService;

@SuppressWarnings("restriction")
@Creatable
public class TraceService implements ITraceService {

	@Override
	public void openTrace() {
		
		System.out.println("openTrace");
		
	}

	@Override
	public void fetch() {
		
		System.out.println("fetch");
		
	}
	
	@Inject
	@Optional
	private void getNotified(@EventTopic(ITraceEvent.TOPIC_EVENT_UI) TraceEvent event) {
	  
		File file = (File) event.getData();
		System.out.println("getNotified : " + file.getAbsolutePath());
		
		
	}
	
	@PostConstruct
	public void init(IEclipseContext context) {
		
		System.out.println("TraceService init");
		
	}
}
