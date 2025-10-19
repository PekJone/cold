package com.ithema.cold.common.current;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-18  16:44
 */
public class ConcurrentCache<K,V> {
    private final ConcurrentHashMap<K,V> cache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<K,Long> expireTimes = new ConcurrentHashMap<>();

    private final long defaultExpireMills;

    public ConcurrentCache(long defaultExpireMills){
        this.defaultExpireMills = defaultExpireMills;
        startCleanTask();
    }


    /**
     * 获取数据 如果不存在则通过loader加载
     * @param key
     * @param loader
     * @return
     */
    public V get(K key, Function<K,V> loader){
        if(isExpired(key)){
            cache.remove(key);
            expireTimes.remove(key);
        }
        return cache.computeIfAbsent(key,K->{
          V value = loader.apply(K);
          expireTimes.put(K,System.currentTimeMillis()+defaultExpireMills);
          return value;
        });
    }


    public void put(K key,V value, long expiryMillis){
        cache.put(key,value);
        expireTimes.put(key,System.currentTimeMillis()+expiryMillis);
    }

    /**
     * 批量放入数据
     * @param map
     */

    public void putAll(Map<? extends  K, ? extends V> map){
        cache.putAll(map);
        long expiryTime = System.currentTimeMillis()+defaultExpireMills;
        map.keySet().forEach(key->expireTimes.put(key,expiryTime));
    }

    /**
     * 放入数据 带有过期时间
     * @param key
     * @return
     */
    private boolean isExpired(K key) {
        Long expiryTime = expireTimes.get(key);
        return expiryTime !=null && System.currentTimeMillis()>expiryTime;
    }

    private void startCleanTask() {
       Thread cleanupThread = new Thread(()->{
           while (!Thread.currentThread().isInterrupted()) {
               try {
                   TimeUnit.MINUTES.sleep(1);
                   cleanupExpiredEntries();
               } catch (InterruptedException e) {
                   Thread.currentThread().interrupt();
                   break;
               }
           }
       });

    }

    private void cleanupExpiredEntries() {
        long currentTime = System.currentTimeMillis();
        expireTimes.entrySet().removeIf(entry->{
            if (currentTime>entry.getValue()){
                cache.remove(entry.getKey());
                return true;
            }
            return false;
        });
    }

    public static void main(String[] args) throws InterruptedException {
        ConcurrentCache<String, String> cache = new ConcurrentCache<>(5000); // 5秒过期

        // 模拟多个线程并发访问缓存
        Runnable task = () -> {
            for (int i = 0; i < 10; i++) {
                String key = "key-" + Thread.currentThread().getName() + "-" + i;
                String value = cache.get(key, k -> {
                    // 模拟从数据库加载数据
                    System.out.println(Thread.currentThread().getName() + " 加载数据: " + k);
                    try {
                        TimeUnit.MILLISECONDS.sleep(100); // 模拟加载耗时
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return "value-for-" + k;
                });
                System.out.println(Thread.currentThread().getName() + " 获取到: " + value);
            }
        };

        // 启动多个线程测试并发
        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        Thread t3 = new Thread(task, "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("缓存最终大小: " + cache.cache.size());
    }

}
