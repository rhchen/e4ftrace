package net.sf.e4ftrace.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;

import net.sf.e4ftrace.core.ITraceEvent;
import net.sf.e4ftrace.core.impl.TraceEvent;
import net.sf.e4ftrace.dao.ITraceDataAdaptor;
import net.sf.e4ftrace.service.ITraceService;

@SuppressWarnings("restriction")
@Creatable
public class TraceService implements ITraceService {

	private static final String IGREETER_ID = "net.sf.e4ftrace.dao.adaptor";
	
	@Inject private IEventBroker eventBroker;
	
	@Override
	public void openTrace() {
		
		System.out.println("TraceService : openTrace");
		
	}

	@Override
	public void fetch() {
		
		System.out.println("TraceService : fetch");
		
	}
	
	@Inject
	@Optional
	private void getNotified(@EventTopic(ITraceEvent.TOPIC_EVENT_UI) TraceEvent event) {
	  
		File file = (File) event.getData();
		System.out.println("TraceService : getNotified : " + file.getAbsolutePath());
		
		
	}
	
	@PostConstruct
	public void init(IEclipseContext context, IExtensionRegistry registry) {
		
		System.out.println("TraceService init");
		
		evaluate(context, registry);
	}
	
	private void evaluate(IEclipseContext context, IExtensionRegistry registry) {
		
		IConfigurationElement[] config = registry.getConfigurationElementsFor(IGREETER_ID);
		
		try {
			
			for (IConfigurationElement e : config) {
				
				System.out.println("Evaluating extension");
				
				final Object o = e.createExecutableExtension("class");
				
				if (o instanceof ITraceDataAdaptor) {
					
					ITraceDataAdaptor tda = (ITraceDataAdaptor) o;
					
					eventBroker.subscribe(ITraceEvent.TOPIC_EVENT_UI, tda);
					
					executeExtension(tda);
				
				}
			}
			
		} catch (CoreException ex) {
			
			System.out.println(ex.getMessage());
		
		}
	}

	private void executeExtension(final Object o) {
		
		ISafeRunnable runnable = new ISafeRunnable() {
			
			@Override
			public void handleException(Throwable e) {
				
				System.out.println("Exception in client");
			
			}

			@Override
			public void run() throws Exception {
				
				((ITraceDataAdaptor) o).run();
				
			}
		};
		
		SafeRunner.run(runnable);
	}
}
