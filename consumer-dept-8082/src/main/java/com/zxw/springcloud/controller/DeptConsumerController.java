package com.zxw.springcloud.controller;

import com.zxw.springcloud.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Copyright: Copyright (c) 2022 Asiainfo-Linkage
 *
 * @ClassName: DeptConsumerController
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
@RestController
@RequestMapping("/consumer")
public class DeptConsumerController {
    @Autowired
    private RestTemplate restTemplate; //提供多种远程便捷访问http服务的方法，简单的restful服务模板

//    private static final String URL_PREFIX ="http://localhost:8081" ;
private static final String URL_PREFIX ="http://PROVIDER-DEPT" ;
    @RequestMapping("/add")
    public boolean addDept( Dept dept){

        return restTemplate.postForObject(URL_PREFIX+"/dept/add",dept,boolean.class);
    }
    @RequestMapping("/queryById/{id}")
    public Dept queryById(@PathVariable("id") Long id) {
        return restTemplate.getForObject(URL_PREFIX+"/dept/queryById/"+id,Dept.class);
    }

    @RequestMapping("/queryAll")
    public List<Dept> queryAll() {
        return restTemplate.getForObject(URL_PREFIX+"/dept/queryAll",List.class);
    }
}
