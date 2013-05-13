package net.sf.e4ftrace.ribbon.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import net.sf.e4ftrace.ribbon.ImageCache;
import net.sf.e4ftrace.service.impl.TraceService;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.event.Event;
import org.eclipse.swt.widgets.Label;

import com.hexapixel.widgets.ribbon.ButtonSelectGroup;
import com.hexapixel.widgets.ribbon.QuickAccessShellToolbar;
import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonButtonGroup;
import com.hexapixel.widgets.ribbon.RibbonCheckbox;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.hexapixel.widgets.ribbon.RibbonGroupSeparator;
import com.hexapixel.widgets.ribbon.RibbonShell;
import com.hexapixel.widgets.ribbon.RibbonTab;
import com.hexapixel.widgets.ribbon.RibbonTabFolder;
import com.hexapixel.widgets.ribbon.RibbonToolbar;
import com.hexapixel.widgets.ribbon.RibbonToolbarGrouping;
import com.hexapixel.widgets.ribbon.RibbonTooltip;

public class RibbonPart {

	@Inject private TraceService traceService;
	@Inject private IEclipseContext eclipseContext;
	
	@PostConstruct
	public void createPartControl(final Composite parent){
		
		FillLayout fl_shell = new FillLayout();
		fl_shell.type = SWT.VERTICAL;
		parent.setLayout(fl_shell);
		
		
		final RibbonShell shell = new RibbonShell(Display.getCurrent());
		shell.setButtonImage(ImageCache.getImageFromPath("selection_recycle_24.png"));
		//Shell shell = new Shell(display);
		
		shell.setText("SWT Ribbon Tester");
		shell.setSize(714, 500);
		
		
		QuickAccessShellToolbar mtb = shell.getToolbar();
		RibbonButton mtbtb1 = new RibbonButton(mtb, ImageCache.getImageFromPath("gear_ok_16.gif"), null, SWT.NONE);
		RibbonButton mtbtb2 = new RibbonButton(mtb, ImageCache.getImageFromPath("gantt_16.gif"), null, SWT.NONE);
		shell.setBigButtonTooltip(new RibbonTooltip("Big", "I'm the tooltip for the big button"));
		mtb.setArrowTooltip(new RibbonTooltip("Oh", "Jeez"));
		
		Menu shellMenu = shell.getBigButtonMenu();
		MenuItem btest = new MenuItem(shellMenu, SWT.NONE);
		btest.setText("Testing a menu");
		
		shell.addBigButtonListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				System.err.println("Clicked big button");
				shell.showBigButtonMenu();
			}
			
		});
		
		
		//shell.setLayout(new FillLayout());
		//Composite inner = new Composite(shell, SWT.None);
		//inner.setLayout(new FillLayout(SWT.VERTICAL)); 
		//inner.setBackground(ColorCache.getInstance().getColor(182, 206, 238));		
		
		// Tab folder
		//final RibbonTabFolder ftf = new RibbonTabFolder(inner, SWT.NONE);
		RibbonTabFolder ftf = shell.getRibbonTabFolder();
		Image helpImage = ImageCache.getImageFromPath("questionmark.gif");
		if(helpImage != null) {
			ftf.setHelpImage(helpImage);
			ftf.getHelpButton().setToolTip(new RibbonTooltip("Title", "Get Help Using Whatever This Is"));
		}
		
		//ftf.setDrawEmptyTabs(false);
		// Tabs
		RibbonTab ft0 = new RibbonTab(ftf, "Home");
		ftf.getChildren()[0].setParent(parent);
		
		RibbonTab ft1 = new RibbonTab(ftf, "Insert");
		new RibbonTab(ftf, "Page Layout");
		new RibbonTab(ftf, "References");		
		new RibbonTab(ftf, "Empty");
		
		// Tooltip
		RibbonTooltip toolTip = new RibbonTooltip("Some Action Title", "This is content text that\nsplits over\nmore than one\nline\n\\b\\c255000000and \\xhas \\bdifferent \\c000000200look \\xand \\bfeel.", ImageCache.getImageFromPath("tooltip.jpg"), helpImage, "Press F1 for more help"); 

		// Group

		// toolbar group
		RibbonGroup tbGroup = new RibbonGroup(ft0, "Toolbar Here");
		RibbonToolbar toolbar = new RibbonToolbar(tbGroup, RibbonToolbar.STYLE_BORDERED, 2);
		RibbonToolbarGrouping rtg = new RibbonToolbarGrouping(toolbar, 1);		
		RibbonToolbarGrouping rtg2 = new RibbonToolbarGrouping(toolbar, 1);
		RibbonToolbarGrouping rtg3 = new RibbonToolbarGrouping(toolbar, 1);
		
		RibbonToolbarGrouping rtg4 = new RibbonToolbarGrouping(toolbar, 2);
		
		RibbonButton rbTb1 = new RibbonButton(rtg, ImageCache.getImageFromPath("books_16.gif"), null, RibbonButton.STYLE_ARROW_DOWN_SPLIT | RibbonButton.STYLE_TOGGLE);
		RibbonButton rbTb2 = new RibbonButton(rtg2, ImageCache.getImageFromPath("gear_ok_16.gif"), null, SWT.NONE);
		RibbonButton rbTb3 = new RibbonButton(rtg2, ImageCache.getImageFromPath("gantt_16.gif"), null, RibbonButton.STYLE_ARROW_DOWN);
		RibbonButton rbTb4 = new RibbonButton(rtg3, ImageCache.getImageFromPath("gantt_16.gif"), null, RibbonButton.STYLE_ARROW_DOWN_SPLIT);
		
		RibbonButton rbTb5 = new RibbonButton(rtg4, ImageCache.getImageFromPath("enabled_small.gif"), null, RibbonButton.STYLE_NO_DEPRESS);
		RibbonButton rbTb6 = new RibbonButton(rtg4, ImageCache.getImageFromPath("selection_recycle_16.gif"), null, RibbonButton.STYLE_ARROW_DOWN_SPLIT);
		
		rbTb4.setEnabled(false);
		// end toolbar group
		
		RibbonGroup ftg = new RibbonGroup(ft0, "Category Name", toolTip);
		// Button
		RibbonButton rb = new RibbonButton(ftg, ImageCache.getImageFromPath("olb_picture.gif"), "I have two\nrows", RibbonButton.STYLE_TWO_LINE_TEXT | RibbonButton.STYLE_ARROW_DOWN);//RibbonButton.STYLE_ARROW_DOWN_SPLIT);
		RibbonButton rb2 = new RibbonButton(ftg, ImageCache.getImageFromPath("olb_picture.gif"), "I'm split\ntoggle", RibbonButton.STYLE_ARROW_DOWN_SPLIT | RibbonButton.STYLE_TOGGLE | RibbonButton.STYLE_TWO_LINE_TEXT);
		rb2.setBottomOrRightToolTip(toolTip);
		MenuItem test = new MenuItem(rb2.getMenu(), SWT.NONE);
		test.setText("Testing a menu");

		rb2.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				final RibbonButton rb = (RibbonButton) e.data;
				if (rb.isTopSelected()) 
					System.err.println("Top clicked");
				else {
					System.err.println("Bottom clicked");
					rb.showMenu();					
				}
			}			
		});
		// Empty group
		RibbonGroup cb = new RibbonGroup(ft1, "Checkboxes", toolTip);
		RibbonButtonGroup cbg = new RibbonButtonGroup(cb);
		
		RibbonCheckbox rc = new RibbonCheckbox(cbg, "I'm checked", SWT.NONE);
		rc.setSelected(true);
		new RibbonCheckbox(cbg, "I'm not", SWT.NONE);
		RibbonCheckbox rc3 = new RibbonCheckbox(cbg, "I'm disabled", SWT.NONE);
		new RibbonCheckbox(cbg, "Also a checkbox", SWT.NONE);
		new RibbonCheckbox(cbg, "Many, aren't we?", SWT.NONE);
		rc3.setEnabled(false);
		RibbonTooltip cbTip = new RibbonTooltip("Title", "Checkbox description\n\\b\\c255000000Some bold and red \\xand\nSome not!", null, helpImage, "Press F1 for more help");
		rc3.setToolTip(cbTip);

		rb.setToolTip(toolTip);
		//TODO: Check when a dialog opens as a result of clicking this to see if this button does not redraw for some reason or think it's still selected
		new RibbonButton(ftg, ImageCache.getImageFromPath("olb_picture.gif"), "I am longer and do not depress", RibbonButton.STYLE_NO_DEPRESS);

		RibbonGroup ftg2 = new RibbonGroup(ft1, "Group 1");
		RibbonButton rb1 = new RibbonButton(ftg2, ImageCache.getImageFromPath("olb_picture2.gif"), "Button 1", SWT.NONE);
		//RibbonButton rb2 = new RibbonButton(ftg2, ImageCache.getImageFromPath("olb_picture3.gif"), "Button 2", SWT.NONE);

		RibbonGroup ftg3 = new RibbonGroup(ft1, "Group 2");
		RibbonButton rb3 = new RibbonButton(ftg3, ImageCache.getImageFromPath("olb_picture4.gif"), "Button 3", SWT.NONE);
		RibbonButton rb4 = new RibbonButton(ftg3, ImageCache.getImageFromPath("olb_picture6.gif"), "Button 4", SWT.NONE);
		rb4.setToolTip(toolTip);

		ButtonSelectGroup group = new ButtonSelectGroup();
		
		// native controls example
		RibbonGroup ftg4 = new RibbonGroup(ft1, "Native");
		GridLayout gl = new GridLayout(1, false);
		gl.marginHeight = 7;
		gl.marginLeft = 0;
		gl.marginRight = 0;
		gl.verticalSpacing = 1;
		gl.horizontalSpacing = 0;
		gl.marginBottom = 7;
		ftg4.setLayout(gl);
		Combo foo = new Combo(ftg4, SWT.READ_ONLY);
		foo.setText("Testing");
		foo.add("Testing 2");
		foo.add("Testing 3");
		foo.add("Testing 4");
		Button b = new Button(ftg4, SWT.PUSH);
		b.setText("Test");
				
		// create sub button containing 3 buttons inside it
		new RibbonGroupSeparator(ftg);
		
		RibbonButtonGroup sub = new RibbonButtonGroup(ftg);
		RibbonButton sub1 = new RibbonButton(sub, ImageCache.getImageFromPath("enabled_small.gif"), ImageCache.getImageFromPath("disabled_small.gif"), "Disabled", SWT.NONE);
		sub1.setEnabled(false);
		new RibbonCheckbox(sub, "I'm mixed in", SWT.NONE);

		// make arrow down
		RibbonButton rb5 = new RibbonButton(sub, ImageCache.getImageFromPath("olb_small2.gif"), "I am toggle split", RibbonButton.STYLE_TOGGLE | RibbonButton.STYLE_ARROW_DOWN_SPLIT);
		RibbonButton rb6 = new RibbonButton(sub, ImageCache.getImageFromPath("olb_small3.gif"), "I am a quite long button", SWT.NONE);
		RibbonButton rb7 = new RibbonButton(sub, ImageCache.getImageFromPath("olb_small3.gif"), "I split normal", RibbonButton.STYLE_ARROW_DOWN_SPLIT);
		RibbonButton rb8 = new RibbonButton(sub, ImageCache.getImageFromPath("olb_small3.gif"), "I am arrowed", RibbonButton.STYLE_ARROW_DOWN);

		MenuItem test2 = new MenuItem(rb8.getMenu(), SWT.NONE);
		test2.setText("Testing an arrow down menu");

		rb8.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				final RibbonButton rb = (RibbonButton) e.data;
				rb.showMenu();					
			}
			
		});
		
		rb1.setButtonSelectGroup(group);
		rb2.setButtonSelectGroup(group);
		rb3.setButtonSelectGroup(group);
		rb4.setButtonSelectGroup(group);
		rb5.setButtonSelectGroup(group);
		rb6.setButtonSelectGroup(group);
	}
	
	@Inject
	@Optional
	public void partActivation(@UIEventTopic(UIEvents.UILifeCycle.ACTIVATE) Event event, MApplication application) {

		// Don't inject MPart and IEclipseContext! Need from root context here
		MPart activePart = (MPart) event.getProperty(UIEvents.EventTags.ELEMENT);
		
		IEclipseContext context = application.getContext();
		
		if (activePart != null) {
			
			context.set("myactivePartId", activePart.getElementId());
			
			System.out.println("OutlinePart : activePart : "+ activePart.getElementId());
			
		}else{
			
			System.out.println("activePart : null");
			
		}
	}
}
