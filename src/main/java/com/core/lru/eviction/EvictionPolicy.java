package com.core.lru.eviction;

import java.util.Map;

public interface EvictionPolicy<K, V> {
	boolean shouldEvict(Map.Entry<K, V> eldest, int currentSize, int maxSize, Map<K, Integer> freqMap);
}