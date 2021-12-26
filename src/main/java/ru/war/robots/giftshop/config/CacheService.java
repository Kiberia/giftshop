package ru.war.robots.giftshop.config;

import java.util.concurrent.ConcurrentHashMap;

public class CacheService {
    private static final ConcurrentHashMap<Object, Boolean> cacheMap = new ConcurrentHashMap<>();
    
    public static Object get(Object o) {
        return cacheMap.get(o);
    }
    
    public static Object put(Object o) {
        return cacheMap.put(o, true);
    }
    
    public static Object remove(Object o) {
        return cacheMap.remove(o);
    }
}
