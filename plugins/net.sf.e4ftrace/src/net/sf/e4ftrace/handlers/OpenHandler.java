/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package net.sf.e4ftrace.handlers;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.e4ftrace.core.ITraceEvent;
import net.sf.e4ftrace.core.impl.TraceEvent;
import net.sf.e4ftrace.service.handler.OpenTraceHandler;
import net.sf.e4ftrace.service.impl.TraceService;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.contexts.Context;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.GroupUpdates;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.EContextService;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

@SuppressWarnings("restriction")
public class OpenHandler {

	public static final String OPEN_TRACE_COMMAND = "net.sf.e4ftrace.service.command.open";
	
	@Inject private ECommandService commandService;
	@Inject private EHandlerService handlerService;
	@Inject private IEclipseContext cont;
	@Inject private OpenTraceHandler openTraceHandler;
	
	@Inject private IEventBroker eventBroker;
	
	@Inject private TraceService traceService;
	
	@Inject
	private void setInfo(@Optional @Named ("FILE_PATH_W") String s){
		
		System.out.println("value change "+ s);
		
		
	}
	
	@Execute
	public void execute(
			@Named(IServiceConstants.ACTIVE_SHELL) Shell shell, final IEclipseContext context, MContext mcont, MApplication app){
		
		FileDialog dialog = new FileDialog(shell);
		
		String filePath = dialog.open();
		
		File fileToOpen = new File(filePath);
		
		if(fileToOpen.exists() && !fileToOpen.isDirectory()){
			
			eventBroker.send(ITraceEvent.TOPIC_EVENT_UI_OPEN_FILE, new TraceEvent(fileToOpen));
			
			traceService.openTrace();
		}
		
		final IEclipseContext wContext = app.getContext();
		wContext.set("FILE_PATH_W", "fff");
		wContext.set("FILE_PATH_W", "ggg");
		
//		ParameterizedCommand cmd = commandService.createCommand("org.eclipse.ui.help.aboutAction", null);
//		System.out.println("handlerService.canExecute(cmd) : "+ handlerService.canExecute(cmd));
//		handlerService.executeHandler(cmd);
		context.set("FILE_PATH", "bbb");
		//context.set("FILE_PATH", "aaa");
		String str = (String)mcont.getContext().get("FILE_PATH");
		
		System.out.println("Str : "+ str);
		
		ParameterizedCommand cmd = commandService.createCommand(OPEN_TRACE_COMMAND, null);
		System.out.println("handlerService.canExecute(cmd) : "+ handlerService.canExecute(cmd));
		
		if(!handlerService.canExecute(cmd)){
			
			Command command = commandService.getCommand(OPEN_TRACE_COMMAND);
			System.out.println("command.isDefined() : "+ command.isDefined() + ":" + command.isEnabled());
			
			OpenTraceHandler th = new OpenTraceHandler();
			
			handlerService.activateHandler(OPEN_TRACE_COMMAND, openTraceHandler);
			
		}
		
		handlerService.executeHandler(cmd); 
	}
}
