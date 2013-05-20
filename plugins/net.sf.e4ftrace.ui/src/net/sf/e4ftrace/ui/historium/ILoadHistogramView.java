package net.sf.e4ftrace.ui.historium;

public interface ILoadHistogramView {

	 /**
     * Broadcast TmfSignal about new selected time range.
     * @param startTime the new start time
     * @param endTime the new end time
     */
    public void updateTimeRange(long startTime, long endTime);
    
    /**
     * Broadcast TmfSignal about new current time value.
     * @param newTime the new current time.
     */
    public void updateCurrentEventTime(long newTime);

    /**
     * Broadcast TmfSignal about new selected time range.
     * @param newDuration new duration (relative to current start time)
     */
    public void updateTimeRange(long newDuration);
}
