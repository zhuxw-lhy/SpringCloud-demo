package com.zxw.springcloud.service;

import com.zxw.springcloud.pojo.Dept;
import feign.hystrix.FallbackFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Copyright: Copyright (c) 2022 Asiainfo-Linkage
 *
 * @ClassName: DeptClientServiceFallBack
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: zhuxw
 * @date: 2022/8/22 14:41
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022/8/22     zhuxw           v1.0.0               修改原因
 */
@Component
public class DeptClientServiceFallBack  implements FallbackFactory {
    @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            @Override
            public Dept queryById(Long id) {
                return new Dept()
                        .setDeptno(id)
                        .setDname("id=>"+id+"没有对应的信息，客户端提供了降级，此服务已经被关闭"+throwable);
            }


            @Override
            public List<Dept> queryAll() {
                ArrayList<Dept> depts = new ArrayList<>();
                Dept dept = new Dept()
                        .setDeptno(500L)
                        .setDname("没有对应的信息，客户端提供了降级，此服务已经被关闭");
                depts.add(dept);
                return depts;
            }

            @Override
            public boolean add(Dept dept) {
                return false;
            }
        };
    }
}
