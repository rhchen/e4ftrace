 
package net.sf.e4ftrace.core.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.CanExecute;

public class OpenFileHandler {
	
	@Execute
	public void execute() {
		
		System.out.println("OpenFileHandler : execute");
	}
	
	
	@CanExecute
	public boolean canExecute() {
		return true;
	}
		
}