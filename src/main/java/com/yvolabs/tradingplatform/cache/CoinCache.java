package com.yvolabs.tradingplatform.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Yvonne N
 * @version 1.0
 * @since 30/10/2024
 */

public class CoinCache {
    private static final ConcurrentHashMap<String, CachedData> cache = new ConcurrentHashMap<>();
    private static final long CACHE_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(5); // Adjust cache duration as needed

    public static synchronized String getCachedResponse(String key) {
        CachedData cachedData = cache.get(key);
        if (cachedData != null && (System.currentTimeMillis() - cachedData.timestamp < CACHE_EXPIRATION_TIME)) {
            return cachedData.response;
        }
        return null;
    }

    public static synchronized void cacheResponse(String key, String response) {
        cache.put(key, new CachedData(response, System.currentTimeMillis()));
    }

    private static class CachedData {
        String response;
        long timestamp;

        CachedData(String response, long timestamp) {
            this.response = response;
            this.timestamp = timestamp;
        }
    }
}
