package kr.msleague.mslibrary.cache.impl;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.LoadingCache;

public class CaffeineCache<K, V> {

    Cache<K, V> handle;
    LoadingCache<K, V> loadingCache;
    AsyncCache<K, V> asyncCache;
    AsyncLoadingCache<K, V> asyncLoadingCache;
}
