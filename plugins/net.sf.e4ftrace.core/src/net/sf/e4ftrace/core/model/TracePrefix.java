package net.sf.e4ftrace.core.model;

import java.util.Scanner;

import net.sf.commonstringutil.StringUtil;

public class TracePrefix extends TraceBiMap{

	private int atomId;
	private int cpuNum;
	private long timeStamp;
	
	public TracePrefix(int atomId, int cpuNum, long timeStamp) {
		super();
		this.atomId = atomId;
		this.cpuNum = cpuNum;
		this.timeStamp = timeStamp;
	}
	
	
	public String getTaskName() {
		return getStringValue(atomId);
	}
	
	public int getAtomId() {
		return atomId;
	}


	public int getCpuNum() {
		return cpuNum;
	}


	public long getTimeStamp() {
		return timeStamp;
	}


	public static TracePrefix create(String tracePreStr){
		
		Scanner scan = new Scanner(tracePreStr);
		
		int count = 0;
		
		int taskId = 0;
		int cpuId = 0;
		long timeStamp = 0L;
		
		while(scan.hasNext()){
			
			String sn = scan.next();
			
			switch(count){
			
			case 0:
				taskId = getIntValue(sn);
				break;
			case 1:
				sn = StringUtil.remove(sn, "[");
				sn = StringUtil.remove(sn, "]");
				cpuId = Integer.parseInt(sn);
				break;
			case 2:
				sn = StringUtil.remove(sn, ":");
				sn = StringUtil.remove(sn, ".");
				timeStamp =Long.parseLong(sn);
				break;
			}
			
			count++;
			
		}
		
		return new TracePrefix(taskId, cpuId, timeStamp);
	}
}
