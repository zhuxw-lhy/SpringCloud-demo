package com.zxw.springcloud.service;

import checkers.units.quals.C;
import com.zxw.springcloud.pojo.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Function: DeptClientService
 * @Description: 该函数的功能描述
 * @param:参数描述
 * @return：返回结果描述
 * @throws：异常描述
 * @version: v1.0.0
 * @author: zhuxw
 * @date: 2022/8/21 16:52
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022/8/21     zhuxw           v1.0.0               修改原因
 */
@Component
@FeignClient(value = "PROVIDER-DEPT",fallbackFactory = DeptClientServiceFallBack.class)
public interface DeptClientService {

     @GetMapping("/dept/queryById/{id}")
     Dept queryById(@PathVariable("id")Long id);
     @GetMapping("/dept/queryAll")
     List<Dept> queryAll();
     @PostMapping("/dept/add")
     boolean add(Dept dept);

}
