package com.xyh.authorityManagement.service;

import com.xyh.authorityManagement.pojo.Role;
import com.xyh.authorityManagement.vo.EasyUiDataGridResult;
import com.xyh.authorityManagement.vo.EasyUiOptionalTreeNode;
import com.xyh.authorityManagement.vo.GlobalResult;

import java.util.List;

/**
 * 角色服务类
 * @author xyh
 * @date 2021/11/14 10:19
 */
public interface IRoleService {

    /**
    * @description: 角色分页查询
     * @param page:
    * @param rows:
    * @param role:
    * @return: com.xyh.authorityManagement.vo.EasyUiDataGridResult
    * @author xyh
    * @date: 2021/11/15 9:26
    */
    EasyUiDataGridResult findRoleListByPage(Integer page, Integer rows, Role role);

    /**
    * @description: 根据条件查询所有角色
     * @param role:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Role>
    * @author xyh
    * @date: 2021/11/15 9:33
    */
    List<Role> selectRoleListByPage(Role role);

    /**
    * @description: 自动补全角色名
     * @param str:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Role>
    * @author xyh
    * @date: 2021/11/15 9:43
    */
    List<Role> findRoleName(String str);

    /**
    * @description:  角色更新
     * @param role:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/15 9:50
    */
    GlobalResult updateRole(Role role);

    /**
    * @description: 增加角色
     * @param role:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/15 9:57
    */
    GlobalResult addRole(Role role);

    /**
    * @description:  删除角色
     * @param id:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/15 10:03
    */
    GlobalResult deleteRoleById(Integer id);

    /**
    * @description: 查询所有可用的角色
     * @return: com.xyh.authorityManagement.vo.EasyUiDataGridResult
    * @author xyh
    * @date: 2021/11/15 10:12
    */
    EasyUiDataGridResult findRoleList();

    /**
    * @description: 根据角色id加载对应权限菜单
     * @param roleId:
    * @return: java.util.List<com.xyh.authorityManagement.vo.EasyUiOptionalTreeNode>
    * @author xyh
    * @date: 2021/11/15 10:18
    */
    List<EasyUiOptionalTreeNode> findRoleMenuByRolrId(Integer roleId);

    /**
    * @description: 更新角色权限菜单
     * @param roleId:
    * @param checkedIds:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/15 10:38
    */
    GlobalResult updateRoleMenu(Integer roleId, String checkedIds);
}
