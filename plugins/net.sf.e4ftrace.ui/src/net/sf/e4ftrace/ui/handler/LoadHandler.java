 
package net.sf.e4ftrace.ui.handler;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class LoadHandler {
	
	@Execute
	public void execute(
			@Named(IServiceConstants.ACTIVE_SHELL) Shell shell){
		FileDialog dialog = new FileDialog(shell);
		dialog.open();
	}
		
}