package com.ithema.cold.common.orderSystem;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-07-24  16:17
 */
public interface FallBackTask<T> extends Runnable{
    //正常执行逻辑
    void execute();
    //降级执行逻辑
    void fallBack();
    //获取任务结果
    T getResult();
    //获取任务异常
    Exception getException();

    default void run(){
        try{
            execute();
        }catch (Exception e){
            this.fallBack();
        }

    }
}
