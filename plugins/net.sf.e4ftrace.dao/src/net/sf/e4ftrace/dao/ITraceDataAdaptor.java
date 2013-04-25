package net.sf.e4ftrace.dao;

import java.net.URI;
import java.nio.channels.FileChannel;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

import net.sf.e4ftrace.core.model.ITrace;

import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.osgi.service.event.EventHandler;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Maps;
import com.google.common.collect.TreeBasedTable;

public interface ITraceDataAdaptor extends IExecutableExtensionFactory, EventHandler{

	public void run();
	
	public void setCurrentTrace(URI uri, TreeBasedTable<Integer, Long, Long> pageTable, FileChannel fileChannel);
	
	public ImmutableTable<Integer, Short, ITrace> get(URI uri, Integer pageNum) throws ExecutionException;
	
	
}
