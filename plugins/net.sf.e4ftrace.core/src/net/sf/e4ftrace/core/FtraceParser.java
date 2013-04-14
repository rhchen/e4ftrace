package net.sf.e4ftrace.core;

import java.io.File;

import javax.inject.Inject;

import net.sf.e4ftrace.core.impl.TraceEvent;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;

@SuppressWarnings("restriction")
@Creatable
public class FtraceParser {

	@javax.inject.Inject
	private IEventBroker eventBroker;
	
	public void parse(){
		
		// asynchronously
		eventBroker.post(ITraceEvent.TOPIC_EVENT_DATA_SCHED_SWITCH, "TOPIC_EVENT_DATA_SCHED_SWITCH"); 
		System.out.println("Post Event" + ITraceEvent.TOPIC_EVENT_DATA_SCHED_SWITCH);
		// synchronously sending a todo
		// Caller will block until delivery
		//eventBroker.send(IEvent.TOPIC_EVENT_DATA_IRQ, "TOPIC_EVENT_DATA_IRQ"); 
	}
	
	@Inject
	@Optional
	private void getNotified(@UIEventTopic(ITraceEvent.TOPIC_EVENT_DATA) 
	    String s) {
	  
		System.out.println("getNotified : " + s);
		
		
	}
	
//	@Inject
//	@Optional
//	private void getNotified(@EventTopic(ITraceEvent.TOPIC_EVENT_UI) TraceEvent event) {
//	  
//		File file = (File) event.getData();
//		System.out.println("getNotified : " + file.getAbsolutePath());
//		
//		
//	}
	
}
