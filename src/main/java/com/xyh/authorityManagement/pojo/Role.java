package com.xyh.authorityManagement.pojo;

import lombok.Data;

/**
 * 设置权限的角色
 * @author xyh
 * @date 2021/11/14 10:02
 */
@Data
public class Role {
    /**
     * 角色编号
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
}
