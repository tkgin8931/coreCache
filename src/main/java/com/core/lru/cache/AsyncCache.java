package com.core.lru.cache;

import java.util.concurrent.CompletableFuture;

public class AsyncCache<K,V> {
	public final MemoryCache<K,V> cache;
	public AsyncCache(MemoryCache<K, V> cache) {
		this.cache = cache;
	}
	
	public CompletableFuture<V> getAsync(K key, CacheLoader<K, V> loader) {
		V value = cache.get(key);
		if(value != null) {
			return CompletableFuture.completedFuture(value);
		}
		
		return CompletableFuture.supplyAsync(() -> {
			V loaded = loader.load(key);
			cache.put(key, loaded);
			
			return loaded;
		});
	}
}