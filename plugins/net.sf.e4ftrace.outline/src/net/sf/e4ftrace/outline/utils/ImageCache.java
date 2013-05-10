package net.sf.e4ftrace.outline.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;

public class ImageCache {

	public static final String PLUGIN_ID = "net.sf.e4ftrace.outline";

	public static final String ICONS_PATH = "icons/"; //$NON-NLS-1$

	public static final String IMG_UI_GEAR_16 = ICONS_PATH + "gear_ok_16.gif";
	public static final String IMG_UI_GEAR_24 = ICONS_PATH + "gear_ok_24.png";
	
	public static ImageRegistry imageRegistry = null;

	public static ImageRegistry getImageRegistry() {
		if (imageRegistry == null) {
			imageRegistry = createImageRegistry();
			initializeImageRegistry(imageRegistry);
		}
		return imageRegistry;
	}

	protected static ImageRegistry createImageRegistry() {

		// If we are in the UI Thread use that
		if (Display.getCurrent() != null) {
			return new ImageRegistry(Display.getCurrent());
		}

		if (PlatformUI.isWorkbenchRunning()) {
			return new ImageRegistry(PlatformUI.getWorkbench().getDisplay());
		}

		// Invalid thread access if it is not the UI Thread
		// and the workbench is not created.
		throw new SWTError(SWT.ERROR_THREAD_INVALID_ACCESS);
	}

	public static void initializeImageRegistry(ImageRegistry reg) {
		reg.put(ImageCache.IMG_UI_GEAR_16,
				getImageFromPath(ImageCache.IMG_UI_GEAR_16));
		reg.put(ImageCache.IMG_UI_GEAR_24,
				getImageFromPath(ImageCache.IMG_UI_GEAR_24));
		
	}

	public static Image getImageFromPath(String path) {
		return getImageDescripterFromPath(path).createImage();
	}

	public static ImageDescriptor getImageDescripterFromPath(String path) {

		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public static ImageDescriptor imageDescriptorFromPlugin(String pluginId,
			String imageFilePath) {
		if (pluginId == null || imageFilePath == null) {
			throw new IllegalArgumentException();
		}

		IWorkbench workbench = PlatformUI.isWorkbenchRunning() ? PlatformUI
				.getWorkbench() : null;
		ImageDescriptor imageDescriptor = workbench == null ? null : workbench
				.getSharedImages().getImageDescriptor(imageFilePath);
		if (imageDescriptor != null)
			return imageDescriptor; // found in the shared images

		// if the bundle is not ready then there is no image
		Bundle bundle = Platform.getBundle(pluginId);
		if (!BundleUtility.isReady(bundle)) {
			return null;
		}

		// look for the image (this will check both the plugin and fragment
		// folders
		URL fullPathString = BundleUtility.find(bundle, imageFilePath);
		if (fullPathString == null) {
			try {
				fullPathString = new URL(imageFilePath);
			} catch (MalformedURLException e) {
				return null;
			}
			URL platformURL = FileLocator.find(fullPathString);
			if (platformURL != null) {
				fullPathString = platformURL;
			}
		}

		return ImageDescriptor.createFromURL(fullPathString);
	}
}
