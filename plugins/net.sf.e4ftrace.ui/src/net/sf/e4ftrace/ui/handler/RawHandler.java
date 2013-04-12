 
package net.sf.e4ftrace.ui.handler;

import javax.inject.Inject;

import net.sf.e4ftrace.core.FtraceParser;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;

@SuppressWarnings("restriction")
public class RawHandler {
	
	@Inject @Optional private MPart part;
	 
	@Inject @Optional private MWindow window;
	
	@Inject @Optional private MApplication application;
	
	@Inject @Optional private MContext context;
	
	@Inject private FtraceParser tracer;
	
	@Execute
	public void execute() {
		
		IEclipseContext context = application.getContext();
		
		String elementId = (String)context.get("myactivePartId");
		
		System.out.println("RawHandler : " + elementId);
		
		
		
		tracer.parse();
	}
		
}