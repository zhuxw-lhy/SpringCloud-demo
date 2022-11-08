package com.zxw.springcloud;

import com.zxw.config.MyIRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
/**
 * Copyright: Copyright (c) 2022 Asiainfo-Linkage
 *
 * @ClassName: consumer_8082
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: zhuxw
 * @date: 2022/8/19 17:18
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022/8/19     zhuxw           v1.0.0               修改原因
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "PROVIDER-DEPT",configuration = MyIRule.class)
public class consumer_8082 {
    public static void main(String[] args) {
        SpringApplication.run(consumer_8082.class,args);
    }
}
