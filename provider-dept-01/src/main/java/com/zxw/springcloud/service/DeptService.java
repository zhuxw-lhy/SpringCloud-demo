package com.zxw.springcloud.service;

import com.zxw.springcloud.pojo.Dept;

import java.util.List;

/**
 * @Function: DeptService
 * @Description: 该函数的功能描述
 * @param:参数描述
 * @return：返回结果描述
 * @throws：异常描述
 * @version: v1.0.0
 * @author: zhuxw
 * @date: 2022/8/19 16:01
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022/8/19     zhuxw           v1.0.0               修改原因
 */
public interface DeptService {

    boolean addDept(Dept dept);

    Dept queryById(Long id);

    List<Dept> queryAll();
}
