package com.core.lru.eviction;

import java.util.Map;

public class LfuLruEvictionPolicy<K, V> implements EvictionPolicy<K, V> {
	
	@Override
	public boolean shouldEvict(Map.Entry<K, V> eldest, int currentSize, int maxSize, Map<K, Integer> freqMap) {
		if(currentSize <= maxSize) {
			return false;
		}
		
		int minFreq= freqMap.getOrDefault(eldest.getKey(), 1);
		
		for (K key : freqMap.keySet()) {
				int freq = freqMap.getOrDefault(key, 1);
				if(freq < minFreq) minFreq = freq;
		}
			
		return freqMap.getOrDefault(eldest.getKey(), 1) <= minFreq;
	}
	
}
