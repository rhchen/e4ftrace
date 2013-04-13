 
package net.sf.e4ftrace.service.handler;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.CanExecute;

@SuppressWarnings("restriction")
@Creatable
public class OpenTraceHandler {
	
	@Execute
	public void execute() {
		
		System.out.println("OpenTraceHandler");
	
	}
	
	@CanExecute
	public boolean canExecute() {
		
		return true;
	
	}
		
}