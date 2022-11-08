package com.zxw.springcloud;


import com.zxw.springcloud.mapper.DeptMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Copyright: Copyright (c) 2022 Asiainfo-Linkage
 *
 * @ClassName: DeptProvider_01Tests
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: zhuxw
 * @date: 2022/8/19 17:01
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022/8/19     zhuxw           v1.0.0               修改原因
 */
@SpringBootTest
public class DeptProvider_01Tests {


    @Autowired
    DeptMapper deptMapper;

    @Test
    void contextLoads(){

        System.out.println(deptMapper.queryAll());
    }
}
