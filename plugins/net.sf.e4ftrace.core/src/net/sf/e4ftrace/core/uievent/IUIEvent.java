package net.sf.e4ftrace.core.uievent;

public interface IUIEvent {

	public static final String ACTIVE_TRACE_URI				 = "TOPIC_EVENT_ACTIVE_TRACE_URI";
	
	/* Data Events */
	public static final String TOPIC_EVENT_DATA              = "TOPIC_EVENT_DATA/*";

	public static final String TOPIC_EVENT_DATA_SCHED_SWITCH = "TOPIC_EVENT_DATA/SCHED_SWITCH";

	public static final String TOPIC_EVENT_DATA_IRQ          = "TOPIC_EVENT_DATA/IRQ";

	public static final String TOPIC_EVENT_DATA_SOFT_IRQ     = "TOPIC_EVENT_DATA/SOFT_IRQ";
	
	/* UI Events */
	public static final String TOPIC_EVENT_UI                = "TOPIC_EVENT_UI/*";

	public static final String TOPIC_EVENT_UI_OPEN_FILE      = "TOPIC_EVENT_UI/OPEN_FILE";

	public static final String TOPIC_EVENT_UI_ZOOM_IN        = "TOPIC_EVENT_UI/ZOOM_IN";

	public static final String TOPIC_EVENT_UI_ZOOM_OUT       = "TOPIC_EVENT_UI/ZOOM_OUT";
	
	/* Get Event Data */
	public Object getData();
}
