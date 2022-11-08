package com.zxw.config;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright: Copyright (c) 2022 Asiainfo-Linkage
 *
 * @ClassName: MyIRuleConfig
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: zhuxw
 * @date: 2022/8/21 16:19
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022/8/21     zhuxw           v1.0.0               修改原因
 */
@Configuration
public class MyIRuleConfig {

    @Bean
    public IRule myRule(){
        return new MyIRule();
    }
}
