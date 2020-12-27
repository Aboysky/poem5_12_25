package com.example.poem5_12_25;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Classname GlobalObject
 * @Description TODO
 * @Author Huan
 * @Date 2020/12/26 22:42
 * @Version 1.0
 */
public class GlobalObject {

    private static GlobalObject globalObject = new GlobalObject();

    private static final Executor executor = Executors.newFixedThreadPool(3);

    private GlobalObject() {
    }

    public static Executor getExecutor(){
        return executor;
    }

    public static void submitTask(Runnable runnable){
        executor.execute(runnable);
    }
}
