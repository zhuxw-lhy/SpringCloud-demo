package com.zxw.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zxw.springcloud.pojo.Dept;
import com.zxw.springcloud.service.DeptClientService;
import com.zxw.springcloud.service.DeptService;
import com.zxw.springcloud.service.DeptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Copyright: Copyright (c) 2022 Asiainfo-Linkage
 *
 * @ClassName: DeptController
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: zhuxw
 * @date: 2022/8/19 16:03
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022/8/19     zhuxw           v1.0.0               修改原因
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    DeptServiceImpl deptService;


    @PostMapping("/add")
    public boolean addDept(@RequestBody Dept dept){

        return deptService.addDept(dept);
    }

    @GetMapping("/queryById/{id}")
    @HystrixCommand(fallbackMethod = "hystrixGet")
    public Dept queryById(@PathVariable("id") Long id) {
        Dept dept = deptService.queryById(id);

        if (dept==null){
            throw new RuntimeException("id=>"+id+"没有对应的信息");
        }
        return dept;
    }

    public Dept hystrixGet(@PathVariable("id") Long id){

        return new Dept().setDeptno(id)
                .setDname("id=>"+id+"没有对应的信息")
                .setDb_source("no this database in MySql");

    }
    @GetMapping("/queryAll")
    public List<Dept> queryAll() {
        return deptService.queryAll();
    }

}
