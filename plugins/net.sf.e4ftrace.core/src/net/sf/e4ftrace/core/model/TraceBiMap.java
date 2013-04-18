package net.sf.e4ftrace.core.model;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.runtime.Assert;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class TraceBiMap {

	private static final BiMap<Integer, String> biMap = HashBiMap.<Integer, String>create();
	
	private static final AtomicInteger seed = new AtomicInteger();
	
	synchronized protected static int getIntValue(String name){
		
		Assert.isNotNull(name, "field name is null");
		
		BiMap<String, Integer> bm = biMap.inverse();
		
		if(bm.containsKey(name)){
			
			return bm.get(name);
		
		}else{
			
			int i = seed.incrementAndGet();
			
			biMap.put(i, name.intern());
			
			return i;
		}
		
	}
	
	protected static String getStringValue(int intKey){
		
		Assert.isNotNull(intKey, "key index is null");
		
		return biMap.get(intKey);
		
	}
}
