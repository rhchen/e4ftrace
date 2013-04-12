 
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
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

@SuppressWarnings("restriction")
public class RawHandler {
	
	@Inject @Optional private MPart part;
	 
	@Inject @Optional private MWindow window;
	
	@Inject @Optional private MApplication application;
	
	@Inject @Optional private MContext context;
	
	@Inject @Optional private EPartService partService;
	
	@Inject private FtraceParser tracer;
	
	@Execute
	public void execute() {
		
		IEclipseContext context = application.getContext();
		
		String elementId = (String)context.get("myactivePartId");
		
		System.out.println("RawHandler : " + elementId);
		
		// Get the part
		MPart part = partService.findPart("net.sf.e4ftrace.ui.part.timeline");
		
		// Required if initial not visible
		part.setVisible(true);

		//Show the part
		partService.showPart(part, PartState.VISIBLE); 
		
		tracer.parse();
	}
		
}