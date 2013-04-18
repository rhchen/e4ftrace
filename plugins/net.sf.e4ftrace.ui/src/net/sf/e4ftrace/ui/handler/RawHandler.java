 
package net.sf.e4ftrace.ui.handler;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.e4ftrace.core.uievent.IUIEvent;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

@SuppressWarnings("restriction")
@Creatable
public class RawHandler {
	
	@Inject @Optional private MPart part;
	 
	@Inject @Optional private MWindow window;
	
	@Inject @Optional private MApplication application;
	
	@Inject @Optional private MContext context;
	
	@Inject @Optional private EPartService partService;
	
	@Inject private IEventBroker eventBroker;
	
	@Execute
	public void execute(IEclipseContext context) {
		
		IEclipseContext cont = application.getContext();
		
		String elementId = (String)cont.get("myactivePartId");
		
		String file_path = (String)cont.get("FILE_PATH");
		
		String file_path_2 = (String)context.get("FILE_PATH");
		
		System.out.println("RawHandler : " + elementId + ":" + file_path +":"+ file_path_2);
		
		// Get the part
		MPart part = partService.findPart("net.sf.e4ftrace.ui.part.timeline");
		
		// Required if initial not visible
		part.setVisible(true);

		//Show the part
		partService.showPart(part, PartState.VISIBLE); 
		
		
	}
	
		
}