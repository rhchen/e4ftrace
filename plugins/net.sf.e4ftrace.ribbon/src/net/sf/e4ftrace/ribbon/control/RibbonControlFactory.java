package net.sf.e4ftrace.ribbon.control;

import net.sf.e4ftrace.ribbon.ImageCache;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.hexapixel.widgets.ribbon.RibbonShell;
import com.hexapixel.widgets.ribbon.RibbonTab;
import com.hexapixel.widgets.ribbon.RibbonTabFolder;
import com.hexapixel.widgets.ribbon.RibbonTooltip;

public class RibbonControlFactory {

	static public Control createRibbonControl(Composite parent){
		
		final RibbonShell shell = new RibbonShell(Display.getCurrent());
		shell.setButtonImage(ImageCache.getImageFromPath("selection_recycle_24.png"));
		
		RibbonTabFolder ftf = shell.getRibbonTabFolder();
		Image helpImage = ImageCache.getImageFromPath("questionmark.gif");
		if(helpImage != null) {
			ftf.setHelpImage(helpImage);
			ftf.getHelpButton().setToolTip(new RibbonTooltip("Title", "Get Help Using Whatever This Is"));
		}
		
		RibbonTab ft0 = new RibbonTab(ftf, "Home");
		
		RibbonTooltip toolTip = new RibbonTooltip("Some Action Title", "This is content text that\nsplits over\nmore than one\nline\n\\b\\c255000000and \\xhas \\bdifferent \\c000000200look \\xand \\bfeel.", ImageCache.getImageFromPath("tooltip.jpg"), helpImage, "Press F1 for more help"); 

		RibbonGroup ftg = new RibbonGroup(ft0, "Category Name", toolTip);
		RibbonButton rb2 = new RibbonButton(ftg, ImageCache.getImageFromPath("olb_picture.gif"), "Core 0", RibbonButton.STYLE_ARROW_DOWN_SPLIT | RibbonButton.STYLE_TOGGLE);
		rb2.setBottomOrRightToolTip(toolTip);
		RibbonButton rb3 = new RibbonButton(ftg, ImageCache.getImageFromPath("olb_picture.gif"), "Core 1", RibbonButton.STYLE_ARROW_DOWN_SPLIT | RibbonButton.STYLE_TOGGLE);
		rb2.setBottomOrRightToolTip(toolTip);
		RibbonButton rb4 = new RibbonButton(ftg, ImageCache.getImageFromPath("olb_picture.gif"), "Core 2", RibbonButton.STYLE_ARROW_DOWN_SPLIT | RibbonButton.STYLE_TOGGLE);
		rb2.setBottomOrRightToolTip(toolTip);
		
		Control control = ftf.getChildren()[0];
		control.setParent(parent);
		
		return control;
	}
}
