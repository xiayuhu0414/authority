package com.xyh.authorityManagement.mapper;

import com.xyh.authorityManagement.pojo.Role;
import com.xyh.authorityManagement.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 * @author xyh
 * @date 2021/11/14 10:34
 */
public interface UserMapper {

    /**
    * @description: 根据用户名和密码查询
     * @param username:
    * @param userPwd:
    * @return: com.xyh.authorityManagement.pojo.User
    * @author xyh
    * @date: 2021/11/14 14:51
    */
    User selectUserByCodeAndPwd(@Param("username") String username,
                                @Param("userPwd") String userPwd);


    /**
    * @description: 查询所有用户信息
     * @param user:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.User>
    * @author xyh
    * @date: 2021/11/15 13:11
    */
    List<User> selectUserListByPage(User user);

    /**
    * @description: 查询用户名，自动补全
     * @param str:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.User>
    * @author xyh
    * @date: 2021/11/15 13:18
    */
    List<User> selectUserName(String str);

    /**
    * @description: 添加用户
     * @param user:
    * @return: java.lang.Integer
    * @author xyh
    * @date: 2021/11/15 13:28
    */
    Integer insertUser(User user);

    /**
    * @description: 更新用户信息
     * @param user:
    * @return: java.lang.Integer
    * @author xyh
    * @date: 2021/11/15 13:38
    */
    Integer updateUser(User user);

    /**
    * @description: 更新密码
     * @param userId:
    * @param password:
    * @return: java.lang.Integer
    * @author xyh
    * @date: 2021/11/15 14:01
    */
    Integer updatePasswordById(@Param("userId") Integer userId,@Param("password") String password);

    /**
    * @description: 根据主键删除用户信息
     * @param userId:
    * @return: java.lang.Integer
    * @author xyh
    * @date: 2021/11/15 14:14
    */
    Integer deleteUserById(@Param("userId") Integer userId);

    /**
    * @description: 删除用户关联的角色
     * @param userId:
    * @return: void
    * @author xyh
    * @date: 2021/11/15 14:17
    */
    void deleteUserRole(@Param("userId") Integer userId);

    /**
    * @description: 根据userid获取用户对应的角色
     * @param userId:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Role>
    * @author xyh
    * @date: 2021/11/15 14:31
    */
    List<Role> selectUserRole(@Param("userId") Integer userId);

    /**
    * @description: 给用户添加角色
     * @param userId:
    * @param roleId:
    * @return: void
    * @author xyh
    * @date: 2021/11/15 14:57
    */
    void insertUserRole(@Param("userId") Integer userId,@Param("roleId") Integer roleId);

    /**
    * @description:  查询所有用户信息
     * @param user:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.User>
    * @author xyh
    * @date: 2021/11/15 15:09
    */
    List<User> selectUserlistByPage(User user);

    /**
    * @description:  查询账号对应用户
     * @param userCode:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.User>
    * @author xyh
    * @date: 2021/11/15 15:20
    */
    List<User> selectUserByUserCode(String userCode);

    /**
    * @description: 更新用户信息根据用户账号
     * @param user:
    * @return: void
    * @author xyh
    * @date: 2021/11/15 15:23
    */
    void updateUserByUserCode(User user);
}
