package net.sf.e4ftrace.ui.parts;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.sf.e4ftrace.chart.control.ChartControlFactory;
import net.sf.e4ftrace.ribbon.control.RibbonControlFactory;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.linuxtools.tmf.core.event.ITmfEvent;
import org.eclipse.linuxtools.tmf.core.event.TmfEvent;
import org.eclipse.linuxtools.tmf.core.event.TmfTimeRange;
import org.eclipse.linuxtools.tmf.core.event.TmfTimestamp;
import org.eclipse.linuxtools.tmf.core.exceptions.TmfTraceException;
import org.eclipse.linuxtools.tmf.core.request.TmfDataRequest;
import org.eclipse.linuxtools.tmf.core.request.ITmfDataRequest.ExecutionType;
import org.eclipse.linuxtools.tmf.core.signal.TmfRangeSynchSignal;
import org.eclipse.linuxtools.tmf.core.signal.TmfSignalManager;
import org.eclipse.linuxtools.tmf.core.signal.TmfTimeSynchSignal;
import org.eclipse.linuxtools.tmf.core.trace.ITmfTrace;
import org.eclipse.linuxtools.tmf.core.trace.TmfExperiment;
import org.eclipse.linuxtools.tmf.ui.views.histogram.HistogramRequest;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.layout.FillLayout;

import net.sf.e4ftrace.ui.historium.ILoadHistogramView;
import net.sf.e4ftrace.ui.historium.LoadFullTraceHistogram;
import net.sf.e4ftrace.ui.historium.LoadHistogram;
import net.sf.e4ftrace.ui.historium.LoadHistogramDataModel;
import net.sf.e4ftrace.ui.historium.LoadHistogramTextControl;
import net.sf.e4ftrace.ui.historium.LoadHistogramView;
import net.sf.e4ftrace.ui.historium.LoadTimeRangeHistogram;
import net.sf.e4ftrace.ui.stub.TmfExperimentStub;
import net.sf.e4ftrace.ui.stub.TmfTraceStub;

public class TracePart extends LoadHistogramView{

	/**
	 * Create contents of the view part.
	 */
	
	@Override
	@PostConstruct
	public void createPartControl(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_composite_1 = new GridLayout(2, false);
		gl_composite_1.horizontalSpacing = 0;
		gl_composite_1.verticalSpacing = 5;
		gl_composite_1.marginWidth = 5;
		gl_composite_1.marginHeight = 9;
		composite_1.setLayout(gl_composite_1);
		
		//Group grpA = new Group(composite_1, SWT.NONE);
		Control grpA = RibbonControlFactory.createRibbonControl(composite_1);
		
		GridData gd_grpA = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_grpA.widthHint = 200;
		grpA.setLayoutData(gd_grpA);
		
		super.createPartControl(composite_1);
		
		CTabFolder tabFolder_0 = new CTabFolder(composite_1, SWT.BORDER | SWT.BOTTOM);
		tabFolder_0.setTabPosition(SWT.BOTTOM);
		GridData gd_tabFolder_0 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_tabFolder_0.widthHint = 200;
		tabFolder_0.setLayoutData(gd_tabFolder_0);
		tabFolder_0.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tabItem_0 = new CTabItem(tabFolder_0, SWT.NONE);
		tabItem_0.setText("1");
		
		CTabItem tabItem_01 = new CTabItem(tabFolder_0, SWT.NONE);
		tabItem_01.setText("2");
		
		super.createTimeRangeHistogramControl(composite_1);
	       
		//Group group = new Group(composite_1, SWT.NONE);
		//group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		//group.setBounds(0, 0, 70, 82);
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Composite composite_2 = new Composite(scrolledComposite, SWT.NONE);
		composite_2.setLayout(new GridLayout(2, false));
		
		CTabFolder tabFolder = new CTabFolder(composite_2, SWT.BORDER | SWT.BOTTOM);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd.verticalIndent = 22;
		tabFolder.setLayoutData(gd);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("1");
		
		CTabItem tabItem_3 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_3.setText("2");
		
//		Group grpF = new Group(composite_2, SWT.NONE);
//		grpF.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
//		grpF.setText("f");
		
		ChartControlFactory.createChartControl(composite_2);
		
		CTabFolder tabFolder_1 = new CTabFolder(composite_2, SWT.BORDER | SWT.BOTTOM);
		tabFolder_1.setTabPosition(SWT.BOTTOM);
		GridData gd_tabFolder_1 = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_tabFolder_1.verticalIndent = 22;
		gd_tabFolder_1.widthHint = 100;
		tabFolder_1.setLayoutData(gd_tabFolder_1);
		tabFolder_1.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tabItem_1 = new CTabItem(tabFolder_1, SWT.NONE);
		tabItem_1.setText("2");
		
		CTabItem tabItem_2 = new CTabItem(tabFolder_1, SWT.NONE);
		tabItem_2.setText("4");
		
//		Group grpB = new Group(composite_2, SWT.NONE);
//		grpB.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
//		grpB.setText("b");
		
		ChartControlFactory.createChartControl(composite_2);
		
		scrolledComposite.setContent(composite_2);
		scrolledComposite.setMinSize(composite_2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	@Focus
	public void setFocus() {
		super.setFocus();
	}

}
