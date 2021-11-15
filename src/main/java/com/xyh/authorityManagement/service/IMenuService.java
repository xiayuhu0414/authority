package com.xyh.authorityManagement.service;

import com.xyh.authorityManagement.pojo.Menu;
import com.xyh.authorityManagement.vo.GlobalResult;
import com.xyh.authorityManagement.vo.Tree;

import java.util.List;

/**
 * 菜单服务类
 * @author xyh
 * @date 2021/11/14 10:18
 */
public interface IMenuService {

    /**
    * @description: 通过用户ID获取菜单列表
     * @param userId:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Menu>
    * @author xyh
    * @date: 2021/11/14 14:02
    */
    List<Menu> findMenuListByUserId(Integer userId);

    /**
    * @description: 查找所有数据
    * @return: java.util.List<com.xyh.authorityManagement.vo.Tree>
    * @author xyh
    * @date: 2021/11/14 18:15
    */
    List<Tree> findMenuList();

    /**
    * @description: 根据菜单id查找菜单，显示菜单详情
     * @param menuId:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Menu>
    * @author xyh
    * @date: 2021/11/15 7:33
    */
    List<Menu> findMenuById(String menuId);

    /**
    * @description: 添加菜单
     * @param menu:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/15 7:42
    */
    GlobalResult addMenu(Menu menu);

    /**
    * @description: 根据id修改数据
     * @param menu:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/15 8:00
    */
    GlobalResult updateMenuById(Menu menu);

    /**
    * @description: 根据id删除菜单数据

    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/15 8:37
     * @param menuId
    */
    GlobalResult deleteMenuById( String menuId);

    /**
    * @description:  根据userId加载对应菜单
     * @param userId:
    * @return: com.xyh.authorityManagement.pojo.Menu
    * @author xyh
    * @date: 2021/11/15 8:47
    */
    Menu findMenuByUserId(Integer userId);
}
