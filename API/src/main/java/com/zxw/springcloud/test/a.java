package com.zxw.springcloud.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Copyright: Copyright (c) 2022 Asiainfo-Linkage
 *
 * @ClassName: a
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: zhuxw
 * @date: 2022/8/27 14:47
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022/8/27     zhuxw           v1.0.0               修改原因
 */
public class a {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Threads threads = new Threads();

        FutureTask futureTask = new FutureTask<>(threads);

        new Thread(futureTask,"有返回值").start();

        System.out.println("子线程的返回值"+futureTask.get());

    }
}
