package com.xyh.authorityManagement.controller;

import com.xyh.authorityManagement.annotation.LogAnno;
import com.xyh.authorityManagement.pojo.Menu;
import com.xyh.authorityManagement.pojo.User;
import com.xyh.authorityManagement.service.IMenuService;
import com.xyh.authorityManagement.util.UserUtils;
import com.xyh.authorityManagement.vo.GlobalResult;
import com.xyh.authorityManagement.vo.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 菜单前端控制器
 *
 * @author xyh
 * @date 2021/11/14 10:26
 */
@Controller
public class MenuController {
    @Autowired
    private IMenuService menuService;

    /**
     * @description:查找所有菜单
     * @return: java.util.List<com.xyh.authorityManagement.vo.Tree>
     * @author xyh
     * @date: 2021/11/14 17:55
     */
    @RequestMapping(value = "/menu/menuList")
    @ResponseBody
    public List<Tree> findAll() {
        return menuService.findMenuList();
    }

    /**
     * @param menuId:
     * @description: 根据菜单id查找菜单，显示菜单详情
     * @return: java.util.List<com.xyh.authorityManagement.pojo.Menu>
     * @author xyh
     * @date: 2021/11/14 17:58
     */
    @RequestMapping("/menu/menuFindById")
    @ResponseBody
    public List<Menu> findById(String menuId) {
        return menuService.findMenuById(menuId);
    }


    /**
     * @param menu:
     * @description: 添加权限菜单
     * @return: com.xyh.authorityManagement.vo.GlobalResult
     * @author xyh
     * @date: 2021/11/14 18:01
     */
    @LogAnno(operateType = "添加权限菜单")
    @RequestMapping(value = "/menu/menuAdd")
    @ResponseBody
    public GlobalResult insert(Menu menu) {
        return menuService.addMenu(menu);
    }

    /**
     * @param menuId:
     * @description: 修改权限菜单
     * @return: com.xyh.authorityManagement.vo.GlobalResult
     * @author xyh
     * @date: 2021/11/14 18:04
     */
    @LogAnno(operateType = "修改权限菜单")
    @RequestMapping(value = "/menu/menuDelete")
    @ResponseBody
    public GlobalResult deleteById(String menuId) {
        return menuService.deleteMenuById(menuId);
    }

    /**
    * @description: 根据id修改数据
     * @param menu:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/14 18:11
    */
    @LogAnno(operateType = "更新权限菜单")
    @RequestMapping(value = "/menu/menuUpdate")
    @ResponseBody
    public GlobalResult updateById(Menu menu) {
        return menuService.updateMenuById(menu);
    }

    /**
    * @description:根据session中的userID加载菜单
    * @return: com.xyh.authorityManagement.pojo.Menu
    * @author xyh
    * @date: 2021/11/14 18:12
    */
    @RequestMapping(value = "/menu/loadMenus")
    @ResponseBody
    public Menu loadMenus(){
        User user = UserUtils.getSubjectUser();
        Menu menu=null;
        if (user!=null){
            menu=menuService.findMenuByUserId(user.getUserId());
        }
        return menu;
    }
}
