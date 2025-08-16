package com.core.lru.cache;

import com.core.lru.eviction.EvictionPolicy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCache<K, V> extends LinkedHashMap<K, V> {
	private final int maxEntries;
	private final Map<K, Integer> freqMap = new ConcurrentHashMap();
	private final EvictionPolicy<K, V> evictionPolicy;
	
	public MemoryCache(int maxEntries, EvictionPolicy<K, V> evictionPolicy) {
		super(maxEntries+1, 1.0f,true);
		this.maxEntries = maxEntries;
		this.evictionPolicy = evictionPolicy;
	}
	
	@Override 
	public V get(Object key) {
		V value = super.get(key);
		
		if(value != null) { 
			freqMap.put((K) key, freqMap.getOrDefault(key, 0)+1);
		}
		
		return value;
	}
	
	@Override
	public V put(K key, V value) {
		freqMap.put(key,freqMap.getOrDefault(key, 0)+1);
		
		return super.put(key, value);
	}
	
	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return evictionPolicy.shouldEvict(eldest, size(), maxEntries, freqMap);
	}
	
}

