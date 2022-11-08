package com.zxw.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Copyright: Copyright (c) 2022 Asiainfo-Linkage
 *
 * @ClassName: eureka_7001
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: zhuxw
 * @date: 2022/8/20 11:35
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022/8/20     zhuxw           v1.0.0               修改原因
 */
@SpringBootApplication
@EnableEurekaServer
public class eureka_7001 {
    public static void main(String[] args) {
        SpringApplication.run(eureka_7001.class,args);
    }
}
