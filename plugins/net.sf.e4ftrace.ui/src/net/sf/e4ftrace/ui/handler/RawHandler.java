 
package net.sf.e4ftrace.ui.handler;

import javax.inject.Inject;

import net.sf.e4ftrace.core.FtraceParser;

import org.eclipse.e4.core.di.annotations.Execute;

public class RawHandler {
	
	@Inject private FtraceParser tracer;
	
	@Execute
	public void execute() {
		
		System.out.println("RawHandler");
		
		tracer.parse();
	}
		
}