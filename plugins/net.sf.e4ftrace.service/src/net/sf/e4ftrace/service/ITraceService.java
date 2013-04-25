package net.sf.e4ftrace.service;

import java.net.URI;
import java.util.concurrent.ExecutionException;

public interface ITraceService {

	public void fetch(URI uri, int pageNum) throws ExecutionException;
}
