package net.sf.e4ftrace.outline.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import net.sf.e4ftrace.service.impl.TraceService;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.nebula.widgets.collapsiblebuttons.CollapsibleButtons;
import org.eclipse.nebula.widgets.collapsiblebuttons.CustomButton;
import org.eclipse.nebula.widgets.collapsiblebuttons.IButtonListener;
import org.eclipse.nebula.widgets.collapsiblebuttons.IColorManager;
import org.eclipse.nebula.widgets.collapsiblebuttons.IMenuListener;
import org.eclipse.nebula.widgets.collapsiblebuttons.ImageCache;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.osgi.service.event.Event;
import org.eclipse.swt.widgets.Label;

public class OutlinePart {

	@Inject private TraceService traceService;
	@Inject private IEclipseContext eclipseContext;
	
	@PostConstruct
	public void createPartControl(final Composite parent){
		
		FillLayout fl_shell = new FillLayout();
		fl_shell.type = SWT.VERTICAL;
		parent.setLayout(fl_shell);
		
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		Group group = new Group(composite, SWT.NONE);
		group.setText("1");

		Composite inner = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout(1, true);
		gl.marginBottom = 0;
		gl.marginHeight = 0;
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		inner.setLayout(gl);

		CollapsibleButtons cp = new CollapsibleButtons(inner, SWT.NONE, IColorManager.SKIN_OFFICE_2007);
		cp.addButtonListener(new IButtonListener() {

			@Override
			public void buttonClicked(CustomButton arg0, MouseEvent arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void buttonEnter(CustomButton arg0, MouseEvent arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void buttonExit(CustomButton arg0, MouseEvent arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void buttonHover(CustomButton arg0, MouseEvent arg1) {
				// TODO Auto-generated method stub
				
			}
			
		});

		cp.setLayoutData(new GridData(GridData.GRAB_VERTICAL | GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_END));
		cp.addButton("Synchronize", null, ImageCache.getImage("icons/selection_recycle_24.png"), ImageCache.getImage("icons/selection_recycle_16.gif"));
		cp.addButton("Gantt", null, ImageCache.getImage("icons/gantt_24.png"), ImageCache.getImage("icons/gantt_16.gif"));
		cp.addButton("Gear", null, ImageCache.getImage("icons/gear_ok_24.png"), ImageCache.getImage("icons/gear_ok_16.gif"));

		cp.addMenuListener(new IMenuListener() {

			public void postMenuItemsCreated(Menu menu) {
				System.err.println("postCreate " + menu);
				
			}

			public void preMenuItemsCreated(Menu menu) {
				System.err.println("preCreate " + menu);
				
			}
			
		});
	}
	
	@Inject
	@Optional
	public void partActivation(@UIEventTopic(UIEvents.UILifeCycle.ACTIVATE) Event event, MApplication application) {

		// Don't inject MPart and IEclipseContext! Need from root context here
		MPart activePart = (MPart) event.getProperty(UIEvents.EventTags.ELEMENT);
		
		IEclipseContext context = application.getContext();
		
		if (activePart != null) {
			
			context.set("myactivePartId", activePart.getElementId());
			
			System.out.println("OutlinePart : activePart : "+ activePart.getElementId());
			
		}else{
			
			System.out.println("activePart : null");
			
		}
	}
}
