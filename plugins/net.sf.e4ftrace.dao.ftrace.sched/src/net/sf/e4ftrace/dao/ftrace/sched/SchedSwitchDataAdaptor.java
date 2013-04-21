package net.sf.e4ftrace.dao.ftrace.sched;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableTable.Builder;

import net.sf.commonstringutil.StringUtil;
import net.sf.e4ftrace.core.model.TracePrefix;
import net.sf.e4ftrace.core.model.TraceSuffix;
import net.sf.e4ftrace.core.uievent.IUIEvent;
import net.sf.e4ftrace.dao.ITraceDataAdaptor;
import net.sf.e4ftrace.dao.ftrace.sched.cache.SchedSwitchCache;

public class SchedSwitchDataAdaptor implements ITraceDataAdaptor {

	private ArrayList<ImmutableTable<TracePrefix, Integer, TraceSuffix>> tableList = Lists.newArrayList();
	
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
							
							Grep.compile("(?i).*sched_switch.*");
							Grep.grep(file);
							
							FileInputStream fis = new FileInputStream(file);
							
							//RandomAccessFile fis = new RandomAccessFile(file, "r");
							
							FileChannel fileChannel = fis.getChannel();
							
							SchedSwitchCache cache = new SchedSwitchCache();
							
							
							cache.init(fileChannel);
							
							long start = System.currentTimeMillis();
							cache.get(0);
							long delta = System.currentTimeMillis() - start;
							System.out.println("time use : "+ delta);
							cache.get(0);
							
							cache.get(0);
							
							//readLine(file);
						
						} catch (IOException e1) {
							
							e1.printStackTrace();
							
						} catch (ExecutionException e) {
							
							e.printStackTrace();
							
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
		
		ArrayList<ImmutableTable.Builder<TracePrefix, Integer, TraceSuffix>> builderList = Lists.newArrayList();
		
		for (line = in.readLine(); line != null; line = in.readLine()) {
			
			boolean st = StringUtil.startsWithIgnoreCase(line, "#");
			
			if (!st) {

				boolean find = StringUtil.countText(line, "sched_switch") >= 1 ? true : false;

				//boolean find = pt_sched_switch.matcher(line).find();
				
				if(find){
				
					count ++;
					
					if(count % 10000 == 0) System.out.println("readLine count : "+ count);
					
					toTraceEvent(line, builderList);
				}//if
				
			}//if
			
		}//for
		
		Iterator<Builder<TracePrefix, Integer, TraceSuffix>> it = builderList.iterator();
		
		while(it.hasNext()){
			
			tableList.add(it.next().build());
			
		}
	}
	
	private void toTraceEvent(String line, ArrayList<ImmutableTable.Builder<TracePrefix, Integer, TraceSuffix>> builderList){
		
		String aStr = StringUtil.replaceLast(line, "==> ", "");
		
		List<String> list = StringUtil.splitAsList(aStr, "sched_switch: ");
		
		String prefStr = list.get(0);
		String suffStr = list.get(1);
		
		TracePrefix prefObj = TracePrefix.create(prefStr);
		TraceSuffix suffObj = TraceSuffix.create(suffStr);
		
		int cpuNum = prefObj.getCpuNum();
		
		if(cpuNum + 1 > builderList.size()){
			
			for(int i=builderList.size(); i<cpuNum + 1; i++){
				
				builderList.add(ImmutableTable.<TracePrefix, Integer, TraceSuffix>builder());
				
			}
			
		}
		
		
		builderList.get(cpuNum).put(prefObj, prefObj.getAtomId(), suffObj);
	}

	

}
