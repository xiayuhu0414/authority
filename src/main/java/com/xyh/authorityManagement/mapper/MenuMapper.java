package com.xyh.authorityManagement.mapper;

import com.xyh.authorityManagement.pojo.Menu;
import com.xyh.authorityManagement.vo.Tree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 * @author xyh
 * @date 2021/11/14 10:33
 */
public interface MenuMapper {

    /**
    * @description: 根据userID加载对应菜单
     * @param userId:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Menu>
    * @author xyh
    * @date: 2021/11/14 14:17
    */
    List<Menu> selectMenuByUserid(Integer userId);

    /**
    * @description: 查找所有数据
    * @return: java.util.List<com.xyh.authorityManagement.vo.Tree>
    * @author xyh
    * @date: 2021/11/14 18:16
    */
    List<Tree> selectMenuList();

    /**
    * @description: 根据菜单id查找菜单
     * @param menuId:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Menu>
    * @author xyh
    * @date: 2021/11/15 7:36
    */
    List<Menu> selectMenuById(String menuId);

    /**
    * @description: 添加数据
     * @param menu:
    * @return: java.lang.Integer
    * @author xyh
    * @date: 2021/11/15 8:08
    */
    Integer insertMenu(Menu menu);

    /**
    * @description: 通过id修改菜单
     * @param menu:
    * @return: java.lang.Integer
    * @author xyh
    * @date: 2021/11/15 8:22
    */
    Integer updateMenuById(Menu menu);

    /**
    * @description: 通过id删除数据
     * @param menuId:
    * @return: java.lang.Integer
    * @author xyh
    * @date: 2021/11/15 8:44
    */
    Integer deleteMenuById(String menuId);

    /**
    * @description:  根据菜单编号查询菜单所有属性
     * @param s:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Menu>
    * @author xyh
    * @date: 2021/11/15 9:13
    */
    List<Menu> selectMenu(@Param("pid") String pid);

    /**
    * @description: 根据pid获取所有权限菜单(menuId,menuName)
     * @param pid:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Menu>
    * @author xyh
    * @date: 2021/11/15 10:34
    */
    List<Menu> selectMenuIdName(@Param("pid") String pid);
}
