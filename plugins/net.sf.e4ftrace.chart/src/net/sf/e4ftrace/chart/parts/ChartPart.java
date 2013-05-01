package net.sf.e4ftrace.chart.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import net.sf.e4ftrace.service.impl.TraceService;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.event.Event;

public class ChartPart {

	@Inject private TraceService traceService;
	
	@Inject private IEclipseContext eclipseContext;
	
	@PostConstruct
	public void createPartControl(final Composite parent){
		
	}
	
	@Inject
	@Optional
	public void partActivation(
			@UIEventTopic(UIEvents.UILifeCycle.ACTIVATE) Event event,
			MApplication application) {

		// Don't inject MPart and IEclipseContext! Need from root context here
		MPart activePart = (MPart) event.getProperty(UIEvents.EventTags.ELEMENT);
		
		
		IEclipseContext context = application.getContext();
		
		if (activePart != null) {
			context.set("myactivePartId", activePart.getElementId());
			System.out.println("activePart : "+ activePart.getElementId());
		}else{
			System.out.println("activePart : null");
		}
	}
}
