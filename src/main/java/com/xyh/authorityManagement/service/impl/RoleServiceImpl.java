package com.xyh.authorityManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyh.authorityManagement.mapper.MenuMapper;
import com.xyh.authorityManagement.mapper.RoleMapper;
import com.xyh.authorityManagement.pojo.Menu;
import com.xyh.authorityManagement.pojo.Role;
import com.xyh.authorityManagement.service.IMenuService;
import com.xyh.authorityManagement.service.IRoleService;
import com.xyh.authorityManagement.vo.EasyUiDataGridResult;
import com.xyh.authorityManagement.vo.EasyUiOptionalTreeNode;
import com.xyh.authorityManagement.vo.GlobalResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色服务实现类
 *
 * @author xyh
 * @date 2021/11/14 10:21
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public EasyUiDataGridResult findRoleListByPage(Integer page, Integer rows, Role role) {
        //1.分页查询
        PageHelper.startPage(page, rows);
        List<Role> list = roleMapper.selectRoleListByPage(role);
        PageInfo<Role> pageInfo = new PageInfo<>(list);
        //2.封装EasyUiDataGridResult
        EasyUiDataGridResult result = new EasyUiDataGridResult();
        result.setTotal((int) pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        //3.返回分页的结果
        return result;
    }

    @Override
    public List<Role> selectRoleListByPage(Role role) {
        return null;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Role> findRoleName(String str) {
        return roleMapper.selectRoleName(str);
    }

    @Override
    public GlobalResult updateRole(Role role) {
        try {
            if (role != null) {
                Integer row = roleMapper.updateRole(role);
                if (row > 0) {
                    return new GlobalResult(200, "角色信息更新成功", null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new GlobalResult(400, "角色信息更新失败", null);
    }

    @Override
    public GlobalResult addRole(Role role) {
        if (role != null) {
            Integer row = roleMapper.insertRole(role);
            if (row > 0) {
                return new GlobalResult(200, "角色信息添加成功", null);
            }
        }
        return new GlobalResult(400, "角色信息添加失败", null);
    }

    @Override
    public GlobalResult deleteRoleById(Integer id) {
        if (id == null) {
            return new GlobalResult(400, "角色id为空，添加失败！", 400);
        }
        Integer integer = roleMapper.deleteRoleById(id);
        if (integer == 0) {
            return new GlobalResult(400, "用户删除失败", null);
        } else {
            return new GlobalResult(200, "用户删除成功", null);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public EasyUiDataGridResult findRoleList() {
        List<Role> list = roleMapper.selectRoleList();
        EasyUiDataGridResult result = new EasyUiDataGridResult();
        result.setRows(list);
        result.setTotal(list.size());
        return result;
    }

    @Override
    public List<EasyUiOptionalTreeNode> findRoleMenuByRolrId(Integer roleId) {
       //1.根据角色id获取角色对应的菜单id
        List<String> menuIdList=roleMapper.selectRoleMenuIdByRoleId(roleId);
        //2.获取一级菜单
        List<Menu> parentMenu = menuMapper.selectMenuIdName("0");
        //3.当前角色对象对应的菜单权限
        List<EasyUiOptionalTreeNode> treeList = new ArrayList<>();
        //暂存一级菜单
        EasyUiOptionalTreeNode t1=null;
        //暂存二级菜单
        EasyUiOptionalTreeNode t2=null;
        //一级菜单遍历
        for(Menu m1:parentMenu){
            t1=new EasyUiOptionalTreeNode();
            t1.setId(m1.getMenuId());
            t1.setText(m1.getMenuName());
            List<Menu> leafMenu=menuMapper.selectMenuIdName(m1.getMenuId());
            //二级菜单遍历
            for (Menu m2:leafMenu){
                t2=new EasyUiOptionalTreeNode();
                t2.setId(m2.getMenuId());
                t2.setText(m2.getMenuName());
                //如果角色下包含有这个权限菜单，让它勾选上
                for(String menuId:menuIdList){
                    if (m2.getMenuId().equals(menuId)){
                        t2.setChecked(true);
                    }
                }
                t1.getChildren().add(t2);
            }
            treeList.add(t1);
        }
        return treeList;
    }

    @Override
    public GlobalResult updateRoleMenu(Integer roleId, String checkedIds) {
        Jedis jedis = jedisPool.getResource();
        try {
            //清空角色下的权限菜单
            roleMapper.deleteMenuIdByRoleId(roleId);
            //权限角色id
            if (checkedIds!=null){
                String[] ids=checkedIds.split(",");
                for (String menuId:ids){
                    roleMapper.insertRoleMenu(menuId,roleId);
                }
            }
            List<Integer> userIdList=roleMapper.selectUserIdByRoleId(roleId);
            for (Integer userId:userIdList){
                //del删除已存在的键
                jedis.del("menusEasyUi_"+userId);
                jedis.del("menusList_"+userId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (jedis!=null){
                jedis.close();
            }
        }
        return GlobalResult.build(200,"权限设置成功");
    }
}
