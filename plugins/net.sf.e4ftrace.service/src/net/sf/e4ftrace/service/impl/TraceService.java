package net.sf.e4ftrace.service.impl;

import org.eclipse.e4.core.di.annotations.Creatable;

import net.sf.e4ftrace.service.ITraceService;

@SuppressWarnings("restriction")
@Creatable
public class TraceService implements ITraceService {

	@Override
	public void openTrace() {
		
		System.out.println("openTrace");
		
	}

	@Override
	public void fetch() {
		
		System.out.println("fetch");
		
	}

}
