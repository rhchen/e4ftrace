package net.sf.e4ftrace.dao.ftrace.schedswitch;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;

import net.sf.commonstringutil.StringUtil;
import net.sf.e4ftrace.core.uievent.IUIEvent;
import net.sf.e4ftrace.dao.ITraceDataAdaptor;

public class SchedSwitchDataAdaptor implements ITraceDataAdaptor {

	@Override
	public void run() {
		
		System.out.println("SchedSwitchDataAdaptor : run");

	}

	@Override
	public Object create() throws CoreException {
		
		return new SchedSwitchDataAdaptor();
	}

	@Override
	public void handleEvent(Event event) {
		
		Object o = event.getProperty(IEventBroker.DATA);
		
		if(o instanceof IUIEvent){
			
			IUIEvent e = (IUIEvent) o;
			
			final File file = (File) e.getData();
			
			System.out.println("SchedSwitchDataAdaptor : handleEvent "+ e.getData());
			
			if(file.exists() && !file.isDirectory()){
				
				Job job = new Job("ReadLine"){

					@Override
					protected IStatus run(IProgressMonitor monitor) {
						
						try {
							
							readLine(file);
						
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}
						
						return Status.OK_STATUS;
					}
					
				};
				
				job.setSystem(true);
				
				job.schedule();
				
			}
			
		}
		
	}
	
	private void readLine(File file) throws IOException{
		
		FileInputStream fis = new FileInputStream(file);
		
		FileChannel fileChannel = fis.getChannel();
		
		MappedByteBuffer mmb = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());

		byte[] buffer = new byte[(int) fileChannel.size()];
		
		mmb.get(buffer);

		BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));

		String line;
		
		int count = 0;
		
		Pattern pt_sched_switch = Pattern.compile("(?i).*sched_switch.*", Pattern.CASE_INSENSITIVE);
		
		for (line = in.readLine(); line != null; line = in.readLine()) {
			
			boolean st = StringUtil.startsWithIgnoreCase(line, "#");
			
			if (!st) {

				boolean find = StringUtil.countText(line, "sched_switch") >= 1 ? true : false;

				//boolean find = pt_sched_switch.matcher(line).find();
				
				if(find){
				
					count ++;
					
					if(count % 10000 == 0) System.out.println("readLine count : "+ count);
					
				}//if
			}//if
		}//for
	}

}
