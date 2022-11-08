package com.zxw.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

/**
 * Copyright: Copyright (c) 2022 Asiainfo-Linkage
 *
 * @ClassName: DeptProvider_hystrix_dashboard
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: zhuxw
 * @date: 2022/8/22 15:56
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022/8/22     zhuxw           v1.0.0               修改原因
 */
@SpringBootApplication
@EnableHystrixDashboard//开启
@EnableZuulProxy
public class Zuul_9527 {
    public static void main(String[] args) {
        SpringApplication.run(Zuul_9527.class,args);
    }
}
