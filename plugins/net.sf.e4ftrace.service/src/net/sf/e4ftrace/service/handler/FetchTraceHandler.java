 
package net.sf.e4ftrace.service.handler;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.CanExecute;

@SuppressWarnings("restriction")
@Creatable
public class FetchTraceHandler {
	
	@Execute
	public void execute() {
		
		System.out.println("FetchTraceHandler");
	
	}
		
	@CanExecute
	public boolean canExecute() {
		
		return true;
	
	}
		
}