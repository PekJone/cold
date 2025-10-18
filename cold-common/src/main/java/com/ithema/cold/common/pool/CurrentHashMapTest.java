package com.ithema.cold.common.pool;

import springfox.documentation.service.ApiListing;

import java.util.concurrent.*;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-09-14  17:01
 */
public class CurrentHashMapTest {
    public static ConcurrentHashMap<String, Future<String>> cMap = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String,String> cMap2 = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String,Integer> concurrentHashMap = new ConcurrentHashMap<>();

    public static int index = 0;

    public static void main(String[] args) {
        for (int i=0;i<5;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                   concurrentMap2("3");
                   try {
                       concurrentMap("123");
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                }
            }).start();
        }
    }
    private static void concurrentMap2(String key){
        System.out.println(Thread.currentThread().getName()+"start......");
        String f = cMap2.get(key);
        if(f==null){
            try {
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            cMap2.put(key,"dataTest"+index);
            System.out.println(Thread.currentThread().getName()+"compute,index====="+index++);
        }
        System.out.println(Thread.currentThread().getName()+"end...."+cMap2.get(key));
    }
    private static void concurrentMap(String key) throws ExecutionException, InterruptedException, TimeoutException {
        System.out.println(Thread.currentThread().getName()+"start....");
        Future<String> f = null;
        f = cMap.get(key);
        if(f==null){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName()+"compute,index====="+index++);
                    return "456789123";
                }
            });
            f = cMap.putIfAbsent(key,task);
            if(f==null){
                f = task;
                task.run();
            }
        }
        System.out.println("end ===========");
        // get会等待FutureTask的计算结果，可以设置等待超时事件,超时会抛出超时异常
        System.out.println(Thread.currentThread().getName() + " end ....====== " + f.get(3000, TimeUnit.MILLISECONDS));
        // get会等待FutureTask的计算结果，永久等待
        System.out.println(Thread.currentThread().getName() + " end .... " + f.get());



    }


    public void putKeyValuePair(String key ,int value){
        concurrentHashMap.put(key,value);
    }

    public Integer getValue(String key){
        return concurrentHashMap.get(key);
    }

    public void putIfAbsent(String key,int newValue){
        concurrentHashMap.putIfAbsent(key,newValue);
    }

    public void increment(String key){
        concurrentHashMap.compute(key,(k,v)->v==null?1:v+1);
    }

}
