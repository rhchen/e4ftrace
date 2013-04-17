package net.sf.e4ftrace.dao.impl;

import net.sf.e4ftrace.dao.ITraceDataAdaptor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtensionFactory;

public class DefaultDataAdaptor implements ITraceDataAdaptor {

	@Override
	public Object create() throws CoreException {
		
		return new DefaultDataAdaptor();
	
	}

	public void run(){
		
		
		System.out.println("DefaultDataAdaptor : run");
	}
}
