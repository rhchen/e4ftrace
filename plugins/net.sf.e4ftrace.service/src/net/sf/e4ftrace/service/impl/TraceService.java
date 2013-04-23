package net.sf.e4ftrace.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;

import com.google.common.collect.Maps;
import com.google.common.collect.TreeBasedTable;

import net.sf.commonstringutil.StringUtil;
import net.sf.e4ftrace.core.uievent.IUIEvent;
import net.sf.e4ftrace.core.uievent.impl.UIEvent;
import net.sf.e4ftrace.dao.ITraceDataAdaptor;
import net.sf.e4ftrace.service.ITraceService;

@SuppressWarnings("restriction")
@Creatable
public class TraceService implements ITraceService {

	private static final String IGREETER_ID = "net.sf.e4ftrace.dao.adaptor";
	
	private ConcurrentMap<URI, TreeBasedTable<Integer, Long, Long>> pageTable = Maps.<URI, TreeBasedTable<Integer, Long, Long>>newConcurrentMap();
	
	@Inject private IEventBroker eventBroker;
	
	@Override
	public void openTrace() {
		
		System.out.println("TraceService : openTrace");
		
	}

	@Override
	public void fetch() {
		
		System.out.println("TraceService : fetch");
		
	}
	
	@Inject
	@Optional
	private void getNotified(@EventTopic(IUIEvent.TOPIC_EVENT_UI) UIEvent event) throws IOException {
	  
		File file = (File) event.getData();
		System.out.println("TraceService : getNotified : " + file.getAbsolutePath());
		
		createPageTable(file);
		
		printPageTable(file);
	}
	
	@PostConstruct
	public void init(IEclipseContext context, IExtensionRegistry registry) {
		
		System.out.println("TraceService init");
		
		evaluate(context, registry);
	}
	
	private void evaluate(IEclipseContext context, IExtensionRegistry registry) {
		
		IConfigurationElement[] config = registry.getConfigurationElementsFor(IGREETER_ID);
		
		try {
			
			for (IConfigurationElement e : config) {
				
				System.out.println("Evaluating extension");
				
				final Object o = e.createExecutableExtension("class");
				
				if (o instanceof ITraceDataAdaptor) {
					
					ITraceDataAdaptor tda = (ITraceDataAdaptor) o;
					
					eventBroker.subscribe(IUIEvent.TOPIC_EVENT_UI, tda);
					
					executeExtension(tda);
				
				}
			}
			
		} catch (CoreException ex) {
			
			System.out.println(ex.getMessage());
		
		}
	}

	private void executeExtension(final Object o) {
		
		ISafeRunnable runnable = new ISafeRunnable() {
			
			@Override
			public void handleException(Throwable e) {
				
				System.out.println("Exception in client");
			
			}

			@Override
			public void run() throws Exception {
				
				((ITraceDataAdaptor) o).run();
				
			}
		};
		
		SafeRunner.run(runnable);
	}
	
	private void createPageTable(File file) throws IOException{
		
		FileInputStream fis = new FileInputStream(file);
		
		FileChannel fileChannel = fis.getChannel();
		
		long size = fileChannel.size();
		
		int M_BYTE = 1024 * 1024;
		
		int pages = (int) (size / M_BYTE);
		
		long positionStart = 0;
		long positionEnd   = 0;
		String firstLine = null;
		String lastLine  = "";
		
		TreeBasedTable<Integer, Long, Long> table = TreeBasedTable.<Integer, Long, Long>create();
		
		for(int i=0; i<=pages; i++){
			
			long limit = (i+1) * M_BYTE > fileChannel.size() ? fileChannel.size() : (i+1) * M_BYTE;
			
			long bufferSize = limit - (i * M_BYTE);
			
			MappedByteBuffer mmb = fileChannel.map(FileChannel.MapMode.READ_ONLY, i * M_BYTE, bufferSize);

			byte[] buffer = new byte[(int) bufferSize];
			
			mmb.get(buffer);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));
			
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				
				firstLine = firstLine == null ? line : firstLine;
				lastLine = line;
				
			}//for
			
			positionEnd = i == pages ? limit : limit - lastLine.getBytes().length;
			
			table.put(i, positionStart, positionEnd);
			
			positionStart = positionEnd;
		}
		
		pageTable.put(file.toURI(), table);
	}
	
	private void printPageTable(File file){
		
		TreeBasedTable<Integer, Long, Long> table = pageTable.get(file.toURI());
		
		Iterator<Integer> it = table.rowKeySet().iterator();
		
		while(it.hasNext()){
			
			int pageNum = it.next();
			
			SortedMap<Long, Long> map = table.row(pageNum);
			
			Iterator<Long> itk = map.keySet().iterator();
			
			while(itk.hasNext()){
				
				long posStart = itk.next();
				
				long posEnd = map.get(posStart);
				
				long bufferSize = posEnd - posStart;
				
				System.out.println("No. "+ pageNum +", posStart "+ posStart + ", posEnd "+ posEnd);
			}
			
		}
	}
	
}
