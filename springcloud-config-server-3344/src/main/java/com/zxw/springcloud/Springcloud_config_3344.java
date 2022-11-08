package com.zxw.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

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
@EnableConfigServer
public class Springcloud_config_3344 {
    public static void main(String[] args) {
        SpringApplication.run(Springcloud_config_3344.class,args);
    }
}
