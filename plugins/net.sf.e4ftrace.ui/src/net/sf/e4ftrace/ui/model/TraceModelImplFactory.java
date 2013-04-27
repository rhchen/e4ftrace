/*******************************************************************************
 * Copyright (c) 2009, 2010 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alvaro Sanchez-Leon (alvsan09@gmail.com) - Initial API and implementation
 *******************************************************************************/
package net.sf.e4ftrace.ui.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;

import net.sf.e4ftrace.core.model.IEvent;
import net.sf.e4ftrace.core.model.ITrace;
import net.sf.e4ftrace.core.model.TraceBiMap;
import net.sf.e4ftrace.core.uievent.IUIEvent;
import net.sf.e4ftrace.service.impl.TraceService;

@SuppressWarnings({"javadoc", "nls"})
@Creatable
public class TraceModelImplFactory {

	public TraceImpl[] createFTraces(TraceService traceService, URI uri) throws ExecutionException {
		
		ImmutableTable<Integer, Short, ITrace> data = traceService.fetch(uri, 0);
		
		UnmodifiableIterator<Integer> it = data.rowKeySet().iterator();
		
		ArrayList<TraceImpl> rList = Lists.<TraceImpl>newArrayList();
		
		Random random = new Random();
		
		/* While Tasks */
		while(it.hasNext()){
			
			int atomId = it.next();
			
			String name = TraceBiMap.getStringValue(atomId);
			
			ImmutableMap<Short, ITrace> traces = data.row(atomId);
			
			UnmodifiableIterator<Short> ck = traces.keySet().iterator();
			
			/* While Cores */
			while(ck.hasNext()){
				
				short cpuid = ck.next();
				ITrace trace = traces.get(cpuid);
				
				TraceImpl traceImpl = new TraceImpl(name, name);
				
				Iterator<IEvent> ei = trace.getEventsIterator();
				
				long prevStamp = 0;
				EventImpl prevEventImpl = null;
				TreeSet<Long> tsSet = Sets.<Long>newTreeSet();
				
				/* While Events */
				while(ei.hasNext()){
					
					IEvent event = ei.next();
					
					long timestamp = event.getTime();
					
					tsSet.add(timestamp);
					
					int type = random.nextInt(8) % 7;
					
					EventImpl eventImpl = new EventImpl(timestamp, traceImpl, getEventType(type));
					
					if(prevStamp != 0){
						
						long duration = timestamp - prevStamp;
						
						prevEventImpl.setDuration(duration);
						
						traceImpl.addTraceEvent(prevEventImpl);
					}
					
					prevStamp = timestamp;
					prevEventImpl = eventImpl;
					
				}
				
				long timeStart = tsSet.first();
				long timeEnd = tsSet.last();
				traceImpl.setStartTime(timeStart);
				traceImpl.setStopTime(timeEnd);
				
				rList.add(traceImpl);
			}
		}
		
		TraceImpl[] ra = rList.toArray(new TraceImpl[rList.size()]);
		
		return ra;
		
	}
	
	// ========================================================================
	// Data
	// ========================================================================
	private int count = 0;
	private final TraceStrings[] traceNames;
	private static final long msTons = 1000000;
	private final Long timeRef = new Date().getTime() * msTons;

	// ========================================================================
	// Constructor
	// ========================================================================
	public TraceModelImplFactory() {
		traceNames = new TraceStrings[17];
		loadTraceNameStrings();
	}

	// ========================================================================
	// Methods
	// ========================================================================
	public TraceImpl[] createTraces() {
		TraceImpl trace;
		TraceImpl[] traceArr = new TraceImpl[17];
		for (int i = 0; i < traceArr.length; i++) {
			trace = new TraceImpl(traceNames[i].name, timeRef, timeRef + 40,
					traceNames[i].classNmme);
			count = i;
			createEvents(trace);
			traceArr[i] = trace;
		}
		return traceArr;
	}

	/**
	 * 5000 Events per Trace.
	 * @param number
	 * @return
	 */
	public TraceImpl[] createLargeTraces(int number) {
		TraceImpl trace;
		TraceImpl[] traceArr = new TraceImpl[number];
		for (int i = 0; i < traceArr.length; i++) {
			int counter = i%17;
			long sTime = i * (long) 1E6;
			trace = new TraceImpl(traceNames[counter].name, sTime  , sTime + 20000,
					traceNames[counter].classNmme);
			create5000Events(trace);
			traceArr[i] = trace;
		}
		return traceArr;
	}

	private static void create5000Events(TraceImpl trace) {
		EventImpl event;
		Long eventTime;
		int numEvents = 5000;
		long sTime = trace.getStartTime();
		long eTime = trace.getEndTime();
		long duration = (eTime - sTime)/numEvents;
		for (int i = 0; i < numEvents; i++) {
			eventTime = sTime + (i * duration);
			// eventTime = timeRef + (5 * (count % 4) + (5 * (int) (i/2) ));
			// System.out.println("Trace: " + trace.getName() + " EventTime: "
			// + eventTime);
//			duration = i * msTons + (long) ((i % 4));

			event = new EventImpl(eventTime, trace, getEventType(i%16));
			event.setDuration(duration);
			trace.addTraceEvent(event);
		}
	}

	private void createEvents(TraceImpl trace) {
		EventImpl event;
		Long eventTime;
		int numEvents = 17;
		long duration = 0;
		for (int i = 0; i < numEvents; i++) {
			eventTime = timeRef + msTons * i + (5 * msTons * count) + (5 * i);
			duration = msTons + i * msTons + ((i % 4));
//			duration = i  + (long) ((i % 4));
			event = new EventImpl(eventTime, trace, getEventType(i));
			event.setDuration(duration);
			trace.addTraceEvent(event);
		}
	}

    private static EventImpl.Type getEventType(int val) {
        if (EventImpl.Type.ALARM.ordinal() == val) {
            return EventImpl.Type.ALARM;
        }
        if (EventImpl.Type.ERROR.ordinal() == val) {
            return EventImpl.Type.ERROR;
        }
        if (EventImpl.Type.EVENT.ordinal() == val) {
            return EventImpl.Type.EVENT;
        }
        if (EventImpl.Type.INFORMATION.ordinal() == val) {
            return EventImpl.Type.INFORMATION;
        }
        if (EventImpl.Type.TIMEADJUSTMENT.ordinal() == val) {
            return EventImpl.Type.TIMEADJUSTMENT;
        }
        if (EventImpl.Type.WARNING.ordinal() == val) {
            return EventImpl.Type.WARNING;
        }
        if (EventImpl.Type.INFO1.ordinal() == val) {
            return EventImpl.Type.INFO1;
        }
        if (EventImpl.Type.INFO2.ordinal() == val) {
            return EventImpl.Type.INFO2;
        }
        if (EventImpl.Type.INFO3.ordinal() == val) {
            return EventImpl.Type.INFO3;
        }
        if (EventImpl.Type.INFO4.ordinal() == val) {
            return EventImpl.Type.INFO4;
        }
        if (EventImpl.Type.INFO5.ordinal() == val) {
            return EventImpl.Type.INFO5;
        }
        if (EventImpl.Type.INFO6.ordinal() == val) {
            return EventImpl.Type.INFO6;
        }
        if (EventImpl.Type.INFO7.ordinal() == val) {
            return EventImpl.Type.INFO7;
        }
        if (EventImpl.Type.INFO8.ordinal() == val) {
            return EventImpl.Type.INFO8;
        }
        if (EventImpl.Type.INFO9.ordinal() == val) {
            return EventImpl.Type.INFO9;
        }
        return EventImpl.Type.UNKNOWN;
    }

	private void loadTraceNameStrings() {
		traceNames[0] = new TraceStrings();
		traceNames[0].name = "TE Log - TATA BSC11";
		traceNames[0].classNmme = "All Boards";

		traceNames[1] = new TraceStrings();
		traceNames[1].name = "System Log";
		traceNames[1].classNmme = "BSC11";

		traceNames[2] = new TraceStrings();
		traceNames[2].name = "Alarm Log";
		traceNames[2].classNmme = "BSC11";

		traceNames[3] = new TraceStrings();
		traceNames[3].name = "Events Log";
		traceNames[3].classNmme = "BSC 11";

		traceNames[4] = new TraceStrings();
		traceNames[4].name = "CPU Load";
		traceNames[4].classNmme = "All Boards";

		traceNames[5] = new TraceStrings();
		traceNames[5].name = "Performance Log";
		traceNames[5].classNmme = "BSC11";

		traceNames[6] = new TraceStrings();
		traceNames[6].name = "TE Log  - TATA BSC14";
		traceNames[6].classNmme = "Board 24";

		traceNames[7] = new TraceStrings();
		traceNames[7].name = "TE Log - TATA BSC14";
		traceNames[7].classNmme = "Board 23";

		traceNames[8] = new TraceStrings();
		traceNames[8].name = "TE Log - TATA BSC14";
		traceNames[8].classNmme = "Board 11";

		traceNames[9] = new TraceStrings();
		traceNames[9].name = "TE Log - TATA BSC14";
		traceNames[9].classNmme = "Board 14, SPO";

		traceNames[10] = new TraceStrings();
		traceNames[10].name = "INFO 1";
		traceNames[10].classNmme = "All Boards";

		traceNames[11] = new TraceStrings();
		traceNames[11].name = "INFO2";
		traceNames[11].classNmme = "BSC11";

		traceNames[12] = new TraceStrings();
		traceNames[12].name = "INFO3";
		traceNames[12].classNmme = "Board 24";

		traceNames[13] = new TraceStrings();
		traceNames[13].name = "MISC1";
		traceNames[13].classNmme = "Board 23";

		traceNames[14] = new TraceStrings();
		traceNames[14].name = "MISC2";
		traceNames[14].classNmme = "Board 11";

		traceNames[15] = new TraceStrings();
		traceNames[15].name = "MISC3";
		traceNames[15].classNmme = "Board 23";

		traceNames[16] = new TraceStrings();
		traceNames[16].name = "MISC4";
		traceNames[16].classNmme = "Board 11";

	}

	// ========================================================================
	// Inner Class
	// ========================================================================
	private static class TraceStrings {
		public String name = "";
		public String classNmme = name + " class";
	}
}
