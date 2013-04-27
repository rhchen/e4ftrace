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
package net.sf.e4ftrace.ui.provider;

import java.util.HashMap;
import java.util.Map;

import net.sf.e4ftrace.ui.model.EventImpl;

import org.eclipse.linuxtools.tmf.ui.widgets.timegraph.StateItem;
import org.eclipse.linuxtools.tmf.ui.widgets.timegraph.TimeGraphPresentationProvider;
import org.eclipse.linuxtools.tmf.ui.widgets.timegraph.model.ITimeEvent;
import org.eclipse.swt.graphics.RGB;

/**
 * Time Graph Presentation Provider Stub.
 */
@SuppressWarnings("nls")
public class TsfImplProvider extends TimeGraphPresentationProvider {

	 public static enum Type {ERROR, WARNING, TIMEADJUSTMENT, ALARM, EVENT, INFORMATION, UNKNOWN, INFO1, INFO2, INFO3, INFO4, INFO5, INFO6, INFO7, INFO8, INFO9}
	 private enum State {
		 
		 ERROR         (new RGB(100, 100, 100)),
		 WARNING            (new RGB(200, 200, 200)),
		 TIMEADJUSTMENT        (new RGB(0, 200, 0)),
		 ALARM         (new RGB(0, 0, 200)),
		 EVENT             (new RGB(200, 100, 100)),
		 INFORMATION        (new RGB(200, 150, 100)),
		 UNKNOWN      (new RGB(200, 100, 100)),
		 INFO1 (new RGB(200, 200, 0)),
		 INFO2 (new RGB(200, 150, 100));

        public final RGB rgb;

        private State (RGB rgb) {
            this.rgb = rgb;
        }
    }
	 
	@Override
	public StateItem[] getStateTable() {
		StateItem[] stateTable = new StateItem[State.values().length];
		for (int i = 0; i < stateTable.length; i++) {
			State state = State.values()[i];
			stateTable[i] = new StateItem(state.rgb, state.toString());
		}
		return stateTable;
	}

	// ========================================================================
	// Methods
	// ========================================================================
	@Override
	public int getStateTableIndex(ITimeEvent event) {
		
		if(event instanceof EventImpl){
			
			EventImpl eventImpl = (EventImpl) event;
			
			net.sf.e4ftrace.ui.model.EventImpl.Type t = eventImpl.getType();
			
			return t.ordinal();
			
		}
		
	    return 0;
	}

	@Override
	public Map<String, String> getEventHoverToolTipInfo(ITimeEvent revent) {
		Map<String, String> toolTipEventMsgs = new HashMap<String, String>();
		if (revent instanceof EventImpl) {
			toolTipEventMsgs.put("Test Tip1", "Test Value tip1");
			toolTipEventMsgs.put("Test Tip2", "Test Value tip2");
		}

		return toolTipEventMsgs;
	}

	@Override
	public String getEventName(ITimeEvent event) {
		String name = "Unknown";
		if (event instanceof EventImpl) {
			EventImpl devent = (EventImpl) event;
			name = devent.getType().toString();
		}
		return name;
	}
}
