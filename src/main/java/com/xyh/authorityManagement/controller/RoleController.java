package com.xyh.authorityManagement.controller;

import com.alibaba.druid.sql.PagerUtils;
import com.sun.org.apache.regexp.internal.RE;
import com.xyh.authorityManagement.annotation.LogAnno;
import com.xyh.authorityManagement.pojo.Role;
import com.xyh.authorityManagement.service.IRoleService;
import com.xyh.authorityManagement.vo.EasyUiDataGridResult;
import com.xyh.authorityManagement.vo.EasyUiOptionalTreeNode;
import com.xyh.authorityManagement.vo.GlobalResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 角色前端控制器
 * @author xyh
 * @date 2021/11/14 10:26
 */
@Controller
public class RoleController {
    @Autowired
    private IRoleService roleService;

    /**
    * @description: 查询角色列表
     * @param role:
    * @param page:
    * @param rows:
    * @return: com.xyh.authorityManagement.vo.EasyUiDataGridResult
    * @author xyh
    * @date: 2021/11/14 18:27
    */
    @RequestMapping(value = "/role/roleListByPage")
    @ResponseBody
    public EasyUiDataGridResult roleListByPage(Role role,
                                               @RequestParam(value = "page",required = true,defaultValue = "1")Integer page,
                                               @RequestParam(value = "rows",required = true,defaultValue = "10")Integer rows){
        return roleService.findRoleListByPage(page,rows,role);
    }

    /**
    * @description: 角色名自动补全
     * @param str:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Role>
    * @author xyh
    * @date: 2021/11/14 18:29
    */
    @RequestMapping(value = "")
    @ResponseBody
    public List<Role> searchRoleName(String str){
        return roleService.findRoleName(str);
    }

    /**
    * @description: 接收更新角色的数据
     * @param role:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/14 18:37
    */
    @LogAnno(operateType = "更新角色")
    @RequestMapping(value = "/role/roleUpdate",method = RequestMethod.POST)
    @ResponseBody
    public GlobalResult updateRole(Role role){
        return roleService.updateRole(role);
    }

    /**
    * @description: 添加角色
     * @param role:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/14 18:40
    */
    @LogAnno(operateType = "添加角色")
    @RequestMapping(value = "/role/roleAdd",method = RequestMethod.POST)
    @ResponseBody
    public GlobalResult addRole(Role role){
        return roleService.addRole(role);
    }

    /**
    * @description: 删除角色
     * @param role:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/14 18:42
    */
    @LogAnno(operateType = "删除角色")
    @RequestMapping(value = "/role/roleDelete",method =RequestMethod.POST)
    @ResponseBody
    public GlobalResult deleteRole(Role role){
        return roleService.deleteRoleById(role.getId());
    }

    /**
    * @description: 返回dataGrid格式json
    * @return: com.xyh.authorityManagement.vo.EasyUiDataGridResult
    * @author xyh
    * @date: 2021/11/14 18:44
    */
    @RequestMapping(value = "/role/roleList")
    @ResponseBody
    public EasyUiDataGridResult roleList(){
        return roleService.findRoleList();
    }

    /**
    * @description: 根据角色id加载对应权限菜单
     * @param roleId:
    * @return: java.util.List<com.xyh.authorityManagement.vo.EasyUiOptionalTreeNode>
    * @author xyh
    * @date: 2021/11/14 18:49
    */
    @RequestMapping(value = "/role/findRoleMenuByRoleId",method = RequestMethod.POST)
    @ResponseBody
    public List<EasyUiOptionalTreeNode> findRoleMenuByRoleId(@RequestParam(value = "id",required = true)Integer roleId){
        return roleService.findRoleMenuByRolrId(roleId);
    }

    /**
    * @description: 更新角色权限菜单
     * @param roleId:
    * @param checkedIds:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/14 18:53
    */
    @LogAnno(operateType = "更新角色权限菜单")
    @RequestMapping(value = "/role/upDateRoleMenu",method = RequestMethod.POST)
    @ResponseBody
    public GlobalResult updateRoleMenu(@RequestParam(value = "id",required = true)Integer roleId,
                                       @RequestParam(value = "checkedIds",required = true)String checkedIds){
        return roleService.updateRoleMenu(roleId,checkedIds);
    }
}
