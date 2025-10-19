package com.ithema.cold.common.current;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-10-19  15:22
 */
public class TaskProcessor {
    private final BlockingQueue<Task> tasksQueue;
    private final Worker[] workers;
    private volatile boolean running =true;

    public TaskProcessor(int queueSize, int workerCount) {
        //根据需求不同 选择不同的queue
       if (queueSize>0){
           this.tasksQueue = new ArrayBlockingQueue<>(queueSize);
       }else {
           this.tasksQueue = new LinkedBlockingDeque<>(queueSize);
       }

       this.workers = new Worker[workerCount];
        for (int i = 0; i < workerCount; i++) {
            workers[i] = new Worker("work-"+(i+1));
        }
    }
    /**
     * 提交任务 阻塞直到有空闲可用
     */
      public void submitTask(Task task) throws InterruptedException {
          tasksQueue.put(task);
          System.out.println("任务提交："+task.getId()+",队列大小"+tasksQueue.size());
      }

    /**
     * 提交任务 带超时时间
     */

    public boolean submitTask(Task task, long timeOut, TimeUnit unit) throws InterruptedException{
        boolean result = tasksQueue.offer(task,timeOut,unit);
        if (result){
            System.out.println("任务提交："+task.getId()+",队列大小"+tasksQueue.size());
        }else {
            System.out.println("任务提交超时"+task.getId());
        }
        return result;
    }

    /**
     * 停止处理器
     */
    public void shutdown(){
        running = false;
        for (Worker worker :workers){
            worker.interrupt();
        }
    }

    /**
     * 等待所有任务完成
     */
     public void awaitTermination(long timeout ,TimeUnit unit)throws InterruptedException{
         long endTime = System.currentTimeMillis()+unit.toMillis(timeout);
         for (Worker worker: workers) {
             long remaining = endTime-System.currentTimeMillis();
             if (remaining>0){
                 worker.join(remaining);
             }
         }
     }

    /**
     * 工作线程
     */
    private class Worker extends Thread{

        public Worker(String name) {
            super(name);
        }

        @Override
        public void run() {
           while (running && isInterrupted()){
               try {
                   Task task = tasksQueue.poll(1,TimeUnit.SECONDS);
               }catch (InterruptedException e){
                   Thread.currentThread().interrupt();
                   break;
               }
           }
            System.out.println(getName()+"停止运行");
        }
        private void processTask(Task task){
            System.out.println(getName()+"开始处理任务"+task.getId());
            long startTime = System.currentTimeMillis();
            try{
                TimeUnit.MILLISECONDS.sleep(task.getProcessingTime());
                task.setStatus(Task.Status.COMPLETED);
                System.out.println(getName()+"完成任务"+task.getId()+"耗时"+(System.currentTimeMillis()-startTime)+"ms");
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                task.setStatus(Task.Status.INTERRUPTED);
            }catch (Exception e){
                task.setStatus(Task.Status.FAILED);
                System.out.println(getName()+"处理任务失败"+task.getId()+"错误："+e.getMessage());
            }
        }
    }

    public static class Task{
        public Task(String id, long processingTime) {
            this.id = id;
            this.processingTime = processingTime;
        }

        public enum Status{
            PENDING,PROCESSING,COMPLETED,FAILED,INTERRUPTED
        }

        private final String id;
        private final long processingTime;
        private Status status = Status.PENDING;

        public String getId() {
            return id;
        }

        public long getProcessingTime() {
            return processingTime;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        TaskProcessor taskProcessor = new TaskProcessor(10,3);
        Thread producer1 = new Thread(()->{
           for (int i=0;i<20;i++){
               try {
                   Task task = new Task("Task-P1"+i,200+i*10);
                   if (!taskProcessor.submitTask(task,100,TimeUnit.SECONDS)){
                       System.out.println("生产者1丢弃任务"+task.getId());
                   }
                   TimeUnit.MILLISECONDS.sleep(50);
               }catch (InterruptedException e){
                   Thread.currentThread().interrupt();
                   break;
               }
           }
        },"Producer-1");
        Thread producer2 = new Thread(() -> {
            for (int i = 0; i < 15; i++) {
                try {
                    Task task = new Task("Task-P2-" + i, 300 + i * 15);
                    taskProcessor.submitTask(task); // 阻塞式提交
                    TimeUnit.MILLISECONDS.sleep(80);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Producer-2");

        producer1.start();
        producer2.start();

        // 等待生产者完成
        producer1.join();
        producer2.join();

        System.out.println("所有任务已提交，等待处理完成...");

        // 等待一段时间让任务处理完成
        TimeUnit.SECONDS.sleep(5);

        // 优雅关闭
        taskProcessor.shutdown();
        taskProcessor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("任务处理器已关闭");

    }
}
