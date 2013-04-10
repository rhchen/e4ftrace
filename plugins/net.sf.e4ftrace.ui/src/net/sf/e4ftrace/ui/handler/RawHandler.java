 
package net.sf.e4ftrace.ui.handler;

import org.eclipse.e4.core.di.annotations.Execute;

public class RawHandler {
	@Execute
	public void execute() {
		System.out.println("RawHandler");
	}
		
}