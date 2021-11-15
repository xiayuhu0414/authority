package com.xyh.authorityManagement.pojo;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 菜单
 * @author xyh
 * @date 2021/11/14 9:47
 */
@Data
public class Menu {
    /**
     * 菜单编号
     */
    private String menuId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单对应url
     */
    private String url;
    /**
     * 菜单图标样式
     */
    private String icon;
    /**
     * 上一级菜单编号
     */
    private String pid;
    /**
     * 菜单是否为父菜单，1为true,0为false;
     */
    private Integer isParent;

    private List<Menu> menus = new LinkedList<>();

}
