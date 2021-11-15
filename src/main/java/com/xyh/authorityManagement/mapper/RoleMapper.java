package com.xyh.authorityManagement.mapper;

import com.xyh.authorityManagement.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 * @author xyh
 * @date 2021/11/14 10:33
 */
public interface RoleMapper {
    /**
    * @description: 根据条件查询所有角色
     * @param role:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Role>
    * @author xyh
    * @date: 2021/11/15 9:38
    */
    List<Role> selectRoleListByPage(Role role);

    /**
    * @description: 自动补全角色名
     * @param str:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Role>
    * @author xyh
    * @date: 2021/11/15 9:45
    */
    List<Role> selectRoleName(String str);

    /**
    * @description: 更新角色
     * @param role:
    * @return: java.lang.Integer
    * @author xyh
    * @date: 2021/11/15 9:54
    */
    Integer updateRole(Role role);

    /**
    * @description: 添加角色
     * @param role:
    * @return: java.lang.Integer
    * @author xyh
    * @date: 2021/11/15 10:01
    */
    Integer insertRole(Role role);

    /**
    * @description: 通过id删除角色
     * @param id:
    * @return: java.lang.Integer
    * @author xyh
    * @date: 2021/11/15 10:08
    */
    Integer deleteRoleById(@Param("id") Integer id);

    /**
    * @description: 查询所有可用的角色
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Role>
    * @author xyh
    * @date: 2021/11/15 10:16
    */
    List<Role> selectRoleList();

    /**
    * @description: 根据角色id获取角色对应的菜单id
     * @param roleId:
    * @return: java.util.List<java.lang.String>
    * @author xyh
    * @date: 2021/11/15 10:31
    */
    List<String> selectRoleMenuIdByRoleId(Integer roleId);

    /**
    * @description: 根据roleuuid删除拥有的角色信息
     * @param roleId:
    * @return: void
    * @author xyh
    * @date: 2021/11/15 10:48
    */
    void deleteMenuIdByRoleId(Integer roleId);

    /**
    * @description: 新增角色权限菜单关系
     * @param menuId:
    * @param roleId:
    * @return: void
    * @author xyh
    * @date: 2021/11/15 10:50
    */
    void insertRoleMenu(@Param("menuId") String menuId, @Param("roleId") Integer roleId);

    /**
    * @description: 根据角色id获取对应的用户id
     * @param roleId:
    * @return: java.util.List<java.lang.Integer>
    * @author xyh
    * @date: 2021/11/15 10:53
    */
    List<Integer> selectUserIdByRoleId(@Param("roleId") Integer roleId);
}
