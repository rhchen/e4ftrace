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

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.e4ftrace.service.handler.OpenTraceHandler;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

@SuppressWarnings("restriction")
public class OpenHandler {

	public static final String OPEN_TRACE_COMMAND = "net.sf.e4ftrace.service.command.open";
	
	@Inject private ECommandService commandService;
	@Inject private EHandlerService handlerService;
	
	@Inject private OpenTraceHandler openTraceHandler;
	
	
	@Execute
	public void execute(
			@Named(IServiceConstants.ACTIVE_SHELL) Shell shell){
		FileDialog dialog = new FileDialog(shell);
		dialog.open();
		
//		ParameterizedCommand cmd = commandService.createCommand("org.eclipse.ui.help.aboutAction", null);
//		System.out.println("handlerService.canExecute(cmd) : "+ handlerService.canExecute(cmd));
//		handlerService.executeHandler(cmd);
		
		
		
		ParameterizedCommand cmd = commandService.createCommand(OPEN_TRACE_COMMAND, null);
		System.out.println("handlerService.canExecute(cmd) : "+ handlerService.canExecute(cmd));
		
		if(!handlerService.canExecute(cmd)){
			
			Command command = commandService.getCommand(OPEN_TRACE_COMMAND);
			System.out.println("command.isDefined() : "+ command.isDefined() + ":" + command.isEnabled());
			handlerService.activateHandler(OPEN_TRACE_COMMAND, openTraceHandler);
			
		}
		
		handlerService.executeHandler(cmd); 
	}
}
