package net.sf.e4ftrace.service;

import java.net.URI;
import java.util.concurrent.ExecutionException;

import net.sf.e4ftrace.core.model.ITrace;

import com.google.common.collect.ImmutableTable;

public interface ITraceService {

	public ImmutableTable<Integer, Short, ITrace> fetch(URI uri, int pageNum) throws ExecutionException;
	
}
