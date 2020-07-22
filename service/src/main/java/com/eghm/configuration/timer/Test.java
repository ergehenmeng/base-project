package com.eghm.configuration.timer;

import com.eghm.configuration.timer.job.TaskOperation;

/**
 * @author 二哥很猛
 * @date 2018/9/11 12:00
 */
public class Test {
    public static void main(String[] args) {
        SystemTimer timer = new SystemTimer(1L,20);
        System.out.println(System.nanoTime());
        for (long i = 0; i < 100L; i++ ){
            timer.addTask(new TaskOperation(500 + i * 500 ));
        }
        while (true){
            timer.advanceClock(200);
        }

    }
}
