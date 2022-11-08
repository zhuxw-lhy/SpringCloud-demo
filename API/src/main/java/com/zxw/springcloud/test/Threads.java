package com.zxw.springcloud.test;

import java.util.concurrent.Callable;

/**
 * Copyright: Copyright (c) 2022 Asiainfo-Linkage
 *
 * @ClassName: Threads
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: zhuxw
 * @date: 2022/8/27 14:40
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022/8/27     zhuxw           v1.0.0               修改原因
 */
public class Threads implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        return 15+16;
    }
}
