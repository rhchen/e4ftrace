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

import java.net.URI;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.e4ftrace.core.model.ITrace;
import net.sf.e4ftrace.core.model.TraceBiMap;
import net.sf.e4ftrace.core.uievent.IUIEvent;
import net.sf.e4ftrace.service.impl.TraceService;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.UnmodifiableIterator;

public class AboutHandler {
	
	@Inject private TraceService traceService;
	
	private URI active_trace_uri;
	
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		
		try {
			
			long timeStart = System.currentTimeMillis();
			
			ImmutableTable<Integer, Short, ITrace> data = traceService.fetch(active_trace_uri, 0);
		
			long delta = System.currentTimeMillis() - timeStart;
			
			UnmodifiableIterator<Integer> it = data.rowKeySet().iterator();
			
			while(it.hasNext()){
				
				int atomId = it.next();
				
				String name = TraceBiMap.getStringValue(atomId);
				
				System.out.println("AboutHandler : name : "+ name);
			}
			
			MessageDialog.openInformation(shell, "About", "time use to load : "+ delta);
			
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Inject
	private void setInfo(@Optional @Named (IUIEvent.ACTIVE_TRACE_URI) URI uri){
		
		System.out.println("AboutHandler IUIEvent.ACTIVE_TRACE_URI value change "+ uri);
		
		active_trace_uri = uri;
	}
}
