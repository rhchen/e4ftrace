package net.sf.e4ftrace.dao.ftrace.sched.cache;

import java.nio.channels.FileChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import net.sf.e4ftrace.core.model.IEvent;
import net.sf.e4ftrace.core.model.ITrace;
import net.sf.e4ftrace.dao.ftrace.sched.loader.SchedSwitchLoader;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ImmutableTable;

public class SchedSwitchCache implements RemovalListener {

	private SchedSwitchLoader cacheLoader;
	
	private LoadingCache<Integer, ImmutableTable<Integer, Short, ITrace>> cache;
	
	public void init(FileChannel fc){
		
		cacheLoader = new SchedSwitchLoader(fc);
		
		cache = CacheBuilder.newBuilder().
				maximumSize(100).
				expireAfterAccess(10, TimeUnit.SECONDS).
				removalListener(this).
				build(cacheLoader);
	}
	
	@Override
	public void onRemoval(RemovalNotification notification) {
		
		System.out.println(" associated with the key("+ notification.getKey()+ ") is removed.");
		
	}

	public ImmutableTable<Integer, Short, ITrace> get(Integer pageNum) throws ExecutionException{
		
		return cache.get(pageNum);
	}
	
	private class ResultCallable implements Callable<ImmutableTable<Integer, Short, ITrace>>{

		private LoadingCache<Integer, ImmutableTable<Integer, Short, IEvent>> cache;
		
		@Override
		public ImmutableTable<Integer, Short, ITrace> call() throws Exception {
			
			System.out.println("ResultCallable");
			return ImmutableTable.<Integer, Short, ITrace>builder().build();
		}
		
	}
}
