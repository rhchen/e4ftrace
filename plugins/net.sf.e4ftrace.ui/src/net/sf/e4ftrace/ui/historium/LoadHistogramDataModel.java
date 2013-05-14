package net.sf.e4ftrace.ui.historium;

import java.util.Random;

import org.eclipse.linuxtools.tmf.ui.views.histogram.HistogramDataModel;
import org.eclipse.linuxtools.tmf.ui.views.histogram.HistogramScaledData;
import org.eclipse.linuxtools.tmf.ui.views.histogram.IHistogramDataModel;

public class LoadHistogramDataModel extends HistogramDataModel implements IHistogramDataModel{

	@Override
	public void complete() {
		super.complete();
	}

	@Override
	public void countEvent(long eventCount, long timestamp) {
		super.countEvent(eventCount, timestamp);
	}

	@Override
	public HistogramScaledData scaleTo(int width, int height, int barWidth) {
		
		HistogramScaledData result = super.scaleTo(width, height, barWidth);
		
		if(result.fMaxValue == 0) return result;
		
		result.fMaxValue = 0;
		
		int[] da = result.fData;
		
		Random r = new Random(10);
		
		for(int i=0; i<da.length; i++){
			
			//da[i] = r.nextInt(20);
			da[i] = da[i] % 7;
			
			if (result.fMaxValue < da[i] ) {
				result.fMaxValue = da[i] ;
            }
		}
		
		// Scale vertically
        if (result.fMaxValue > 0) {
            result.fScalingFactor = (double) height / result.fMaxValue;
        }

       
        return result;
        
		
	}

}
