package com.zxw.springcloud.controller;

import checkers.units.quals.A;
import com.zxw.springcloud.pojo.Dept;
import com.zxw.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    DeptClientService deptClientService = null;

    @RequestMapping("/add")
    public boolean addDept( Dept dept){

        return this.deptClientService.add(dept);
    }
    @RequestMapping("/queryById/{id}")
    public Dept queryById(@PathVariable("id") Long id) {
        return deptClientService.queryById(id);
    }

    @RequestMapping("/queryAll")
    public List<Dept> queryAll() {
        return deptClientService.queryAll();
    }
}
