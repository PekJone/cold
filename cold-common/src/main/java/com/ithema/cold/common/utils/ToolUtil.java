package com.ithema.cold.common.utils;

import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-08-13  8:35
 */
public class ToolUtil {
    public static ArrayList<Integer> getLeastNumbers_Solution(int[] input,int k){
        ArrayList<Integer> result = new ArrayList<>();
        int length = input.length;
        if(k>length||k==0){
            return result;
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        for (int i = 0; i < length; i++) {
            if(maxHeap.size()!=k){
                maxHeap.offer(input[i]);
            }else if(maxHeap.peek()>input[i]){
                Integer temp = maxHeap.poll();
                temp = null ;
                maxHeap.offer(input[i]);
            }
        }
        for (Integer integer: maxHeap){
            System.out.println(integer);
            result.add(integer);
        }
        return result;
    }
}
