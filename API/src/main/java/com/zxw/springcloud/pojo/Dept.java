package com.zxw.springcloud.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Copyright: Copyright (c) 2022 Asiainfo-Linkage
 *
 * @ClassName: Dept
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: zhuxw
 * @date: 2022/8/19 15:22
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022/8/19     zhuxw           v1.0.0               修改原因
 */
@Data
@NoArgsConstructor
@Accessors(chain = true) //支持链式写法
public class Dept implements Serializable {//Dept 实体类  orm 类表关系映射

    private Long deptno;
//吱吱吱吱
    private String dname;

    //这个数据存在哪个数据库的字段 ~微服务 一个服务对应一个数据库，同一个信息可能存在不同的数据库中
    private String db_source;

    public Dept(String dname){
        this.dname=dname;
    }
}
