package net.sf.e4ftrace.dao.ftrace.sched.loader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import net.sf.commonstringutil.StringUtil;
import net.sf.e4ftrace.core.model.IEvent;
import net.sf.e4ftrace.core.model.ITrace;
import net.sf.e4ftrace.core.model.TraceBiMap;
import net.sf.e4ftrace.core.model.TracePrefix;
import net.sf.e4ftrace.core.model.TraceSuffix;
import net.sf.e4ftrace.core.model.impl.Event;
import net.sf.e4ftrace.core.model.impl.Trace;

import com.google.common.cache.CacheLoader;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.ImmutableTable.Builder;
import com.google.common.collect.Lists;

public class SchedSwitchLoader extends CacheLoader<Integer, ImmutableTable<Integer, Short, ITrace>> {

	private FileChannel fileChannel;
	
	public SchedSwitchLoader(FileChannel fileChannel) {
		super();
		this.fileChannel = fileChannel;
	}


	@Override
	public ImmutableTable<Integer, Short, ITrace> load(Integer pageNum)
			throws Exception {
		
		System.out.println("SchedSwitchLoader : load");
		
		Builder<Integer, Short, ITrace>  builder = ImmutableTable.<Integer, Short, ITrace>builder();
		
		HashBasedTable<Integer, Short, ITrace> dataTable = HashBasedTable.<Integer, Short, ITrace>create();
		
		MappedByteBuffer mmb = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());

		byte[] buffer = new byte[(int) fileChannel.size()];
		
		mmb.get(buffer);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));

		String line;
		
		Pattern pt_sched_switch = Pattern.compile("(?i).*sched_switch.*", Pattern.CASE_INSENSITIVE);
		
		ArrayList<ImmutableTable.Builder<TracePrefix, Integer, TraceSuffix>> builderList = Lists.newArrayList();
		
		int pCount = 0;
		
		for (line = in.readLine(); line != null; line = in.readLine()) {
			
			boolean st = StringUtil.startsWithIgnoreCase(line, "#");
			
			if (!st) {

				boolean find = StringUtil.countText(line, "sched_switch") >= 1 ? true : false;

				//boolean find = pt_sched_switch.matcher(line).find();
				
				if(find){
				
					pCount ++;
					
					if(pCount % 10000 == 0) System.out.println("readLine count : "+ pCount);
					
					String aStr = StringUtil.replaceLast(line, "==> ", "");
					
					List<String> list = StringUtil.splitAsList(aStr, "sched_switch: ");
					
					String prefStr = list.get(0);
					String suffStr = list.get(1);
					
					Scanner scan = new Scanner(prefStr);
					
					int count = 0;
					
					int atomId = 0;
					short cpuId = 0;
					long timeStamp = 0L;
					
					while(scan.hasNext()){
						
						String sn = scan.next();
						
						switch(count){
						
						case 0:
							atomId = TraceBiMap.getIntValue(sn);
							break;
						case 1:
							sn = StringUtil.remove(sn, "[");
							sn = StringUtil.remove(sn, "]");
							cpuId = Short.parseShort(sn);
							break;
						case 2:
							sn = StringUtil.remove(sn, ":");
							sn = StringUtil.remove(sn, ".");
							timeStamp =Long.parseLong(sn);
							break;
						}
						
						count++;
						
					}//while
					
					if(dataTable.contains(atomId, cpuId)){
						
						ITrace trace = dataTable.get(atomId, cpuId);
						
						trace.addTraceEvent(new Event());
						
					}else{
						
						ITrace trace = new Trace();
						
						trace.addTraceEvent(new Event());
						
						dataTable.put(atomId, cpuId, trace);
					}
					
				}//if
				
			}//if
			
		}//for
		
		return builder.putAll(dataTable).build();
	}

}
