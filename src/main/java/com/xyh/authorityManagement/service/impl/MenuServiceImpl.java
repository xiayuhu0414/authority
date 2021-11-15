package com.xyh.authorityManagement.service.impl;

import com.alibaba.fastjson.JSON;
import com.xyh.authorityManagement.mapper.MenuMapper;
import com.xyh.authorityManagement.pojo.Menu;
import com.xyh.authorityManagement.service.IMenuService;
import com.xyh.authorityManagement.vo.GlobalResult;
import com.xyh.authorityManagement.vo.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;


/**
 * 菜单服务实现类
 *
 * @author xyh
 * @date 2021/11/14 10:20
 */
@Service
public class MenuServiceImpl implements IMenuService {
    private static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findMenuListByUserId(Integer userId) {
        Jedis jedis = jedisPool.getResource();
        List<Menu> menuList;
        try {
            String menuListJson = jedis.get("menusList_" + userId);
            if (menuListJson == null) {
                //从数据库中查询并放入缓存
                menuList = menuMapper.selectMenuByUserid(userId);
                jedis.set("menusList_" + userId, JSON.toJSONString(menuList));
                logger.debug("从数据库中查询menuList");
            } else {
                //从缓存中获得
                logger.debug("从缓存中查询menuList" + menuListJson);
                menuList = JSON.parseArray(menuListJson, Menu.class);
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return menuList;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Tree> findMenuList() {
        return menuMapper.selectMenuList();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Menu> findMenuById(String menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    @Override
    public GlobalResult addMenu(Menu menu) {
        //设置默认添加的菜单的状态为使用中
        Integer integerCount = menuMapper.insertMenu(menu);
        if (integerCount != null && integerCount > 0) {
            //更新标签为父标签
            Menu m = new Menu();
            m.setMenuId(menu.getPid());
            m.setIsParent(1);
            int status = 200;
            if (status == updateMenuById(m).getStatus()) {
                //清除redis缓存
                batchDel();
                return new GlobalResult(200, "数据添加成功", null);
            } else {
                return new GlobalResult(400, "数据添加失败", null);
            }
        } else {

            return new GlobalResult(400, "数据添加失败", null);
        }
    }

    /**
     * @param menu:
     * @description: 通过id修改菜单
     * @return: com.xyh.authorityManagement.vo.GlobalResult
     * @author xyh
     * @date: 2021/11/15 8:06
     */
    @Override
    public GlobalResult updateMenuById(Menu menu) {
        Integer updateCount = menuMapper.updateMenuById(menu);
        if (updateCount != null && updateCount > 0) {
            batchDel();
            return new GlobalResult(200, "修改数据成功", null);
        } else {
            return new GlobalResult(400, "数据修改失败", null);
        }
    }


    /**
     * @param menuId
     * @description: 通过id删除菜单数据
     * @return: com.xyh.authorityManagement.vo.GlobalResult
     * @author xyh
     * @date: 2021/11/15 8:38
     */
    @Override
    public GlobalResult deleteMenuById(String menuId) {
        Integer deleteCount = menuMapper.deleteMenuById(menuId);
        if (deleteCount != null && deleteCount > 0) {
            batchDel();
            return new GlobalResult(200, "数据删除成功", null);
        } else {
            return new GlobalResult(400, "数据删除失败", null);
        }
    }

    @Override
    public Menu findMenuByUserId(Integer userId) {
        //从缓存中读取数据
        Jedis jedis = jedisPool.getResource();
        Menu menu;
        try {
            String easyUiMenusJson = jedis.get("menusEasyUi_"+userId);
            if (easyUiMenusJson==null){
                //获取根菜单
                List<Menu> root = menuMapper.selectMenu("-1");
                //缓存中用户下的菜单集合
                List<Menu> userMenu=findMenuListByUserId(userId);
                //根菜单
                menu = cloneMenu(root.get(0));
                //暂存一级菜单
                Menu _m1=null;
                //暂存二级菜单
                Menu _m2=null;
                //获取全部的一级菜单
                List<Menu> parentMenus = menuMapper.selectMenu("0");
                //循环一级菜单
                for (Menu m1 : parentMenus){
                    _m1=cloneMenu(m1);
                    //获取当前一级菜单的所有二级菜单
                    List<Menu> leafMenu = menuMapper.selectMenu(_m1.getMenuId());
                    //循环匹配二级菜单
                    for(Menu m2 :leafMenu){
                        for (Menu userM :userMenu){
                            if (userM.getMenuId().equals(m2.getMenuId())){
                                //将二级菜单加入一级菜单
                                _m2=cloneMenu(m2);
                                _m1.getMenus().add(_m2);
                            }
                        }
                    }
                    //有二级菜单才加进去
                    if (_m1.getMenus().size()>0){
                        //把一级菜单加入到根菜单下
                        menu.getMenus().add(_m1);
                    }
                }
                logger.debug("从数据库读取，设置缓存");
                jedis.set("menuEasyUi_"+userId,JSON.toJSONString(menu));
            }else {
                menu=JSON.parseObject(easyUiMenusJson,Menu.class);
                logger.debug("从缓存中读取");
            }
        }finally {
            if (jedis!=null){
                jedis.close();
            }
        }
        return menu;
    }


    /**
     * @description: 清除缓存
     * @return: void
     * @author xyh
     * @date: 2021/11/15 7:59
     */
    private void batchDel() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.flushAll();
        } catch (Exception ignored) {
        }
    }

    /**
    * @description: 菜单复制
     * @param menu:
    * @return: com.xyh.authorityManagement.pojo.Menu
    * @author xyh
    * @date: 2021/11/15 9:12
    */
    private Menu cloneMenu(Menu menu){
        Menu menu1=new Menu();
        menu1.setMenuId(menu.getMenuId());
        menu1.setMenuName(menu.getMenuName());
        menu1.setUrl(menu.getUrl());
        menu1.setIcon(menu.getIcon());
        menu1.setMenus(new ArrayList<Menu>());
        return menu;
    }
}
