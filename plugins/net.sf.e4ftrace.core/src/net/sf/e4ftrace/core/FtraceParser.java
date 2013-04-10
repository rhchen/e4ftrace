package net.sf.e4ftrace.core;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;

@Creatable
public class FtraceParser {

	@javax.inject.Inject
	private IEventBroker eventBroker;
	
	public void parse(){
		
		// asynchronously
		eventBroker.post(IEvent.TOPIC_EVENT_DATA_SCHED_SWITCH, "TOPIC_EVENT_DATA_SCHED_SWITCH"); 
		
		// synchronously sending a todo
		// Caller will block until delivery
		//eventBroker.send(IEvent.TOPIC_EVENT_DATA_IRQ, "TOPIC_EVENT_DATA_IRQ"); 
	}
	
	@Inject
	@Optional
	private void getNotified(@UIEventTopic(IEvent.TOPIC_EVENT_DATA) 
	    String s) {
	  
		System.out.println("getNotified : " + s);
		
		
	}
}
