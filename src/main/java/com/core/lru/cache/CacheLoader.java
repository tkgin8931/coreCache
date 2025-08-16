package com.core.lru.cache;

@FunctionalInterface
public interface CacheLoader<K, V> {
	V load(K key);
}