package com.zxw.springcloud.service;

import checkers.units.quals.A;
import com.zxw.springcloud.mapper.DeptMapper;
import com.zxw.springcloud.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright: Copyright (c) 2022 Asiainfo-Linkage
 *
 * @ClassName: DeptServiceImpl
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: zhuxw
 * @date: 2022/8/19 16:01
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022/8/19     zhuxw           v1.0.0               修改原因
 */
@Service
public class DeptServiceImpl implements DeptService{

    @Autowired
    DeptMapper deptMapper;

    @Override
    public boolean addDept(Dept dept) {
        return deptMapper.addDept(dept);
    }

    @Override
    public Dept queryById(Long id) {
        return deptMapper.queryById(id);
    }

    @Override
    public List<Dept> queryAll() {
        return deptMapper.queryAll();
    }
}
