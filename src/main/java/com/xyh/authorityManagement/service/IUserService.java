package com.xyh.authorityManagement.service;

import com.xyh.authorityManagement.pojo.User;
import com.xyh.authorityManagement.vo.EasyUiDataGridResult;
import com.xyh.authorityManagement.vo.EasyUiOptionalTreeNode;
import com.xyh.authorityManagement.vo.GlobalResult;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 用户服务类
 * @author xyh
 * @date 2021/11/14 10:19
 */
public interface IUserService {

    /**
    * @description: 根据账号和密码查找用户
     * @param username:
    * @param pwd:
    * @return: com.xyh.authorityManagement.pojo.User
    * @author xyh
    * @date: 2021/11/14 14:44
    */
    User findUserByCodeAndPwd(String username, String pwd);

    /**
    * @description: 用户分页查询
     * @param user:
    * @param page:
    * @param rows:
    * @return: com.xyh.authorityManagement.vo.EasyUiDataGridResult
    * @author xyh
    * @date: 2021/11/15 13:06
    */
    EasyUiDataGridResult findUserListByPage(User user, Integer page, Integer rows);

    /**
    * @description: 用户名自动补全
     * @param str:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.User>
    * @author xyh
    * @date: 2021/11/15 13:16
    */
    List<User> findUserName(String str);

    /**
    * @description: 添加用户
     * @param user:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/15 13:20
    */
    GlobalResult addUser(User user);

    /**
    * @description: 更新用户信息
     * @param user:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/15 13:35
    */
    GlobalResult updateUser(User user);

    /**
    * @description:  更新用密码
     * @param user:
    * @param oldPassword:
    * @param newPassword:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/15 13:52
    */
    GlobalResult updatePassword(User user, String oldPassword, String newPassword);

    /**
    * @description: 删除用户
     * @param userId:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/15 14:05
    */
    GlobalResult deleteUser(Integer userId);

    /**
    * @description: 查询所有角色，并设置选中的用户角色为true
     * @param userId:
    * @return: java.util.List<com.xyh.authorityManagement.vo.EasyUiOptionalTreeNode>
    * @author xyh
    * @date: 2021/11/15 14:20
    */
    List<EasyUiOptionalTreeNode> findUserRole(Integer userId);

    /**
    * @description: 根据userid获取用户对应的角色
     * @param userId:
    * @param checkedIds:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/15 14:38
    */
    GlobalResult updateUserRole(Integer userId, String checkedIds);

    /**
    * @description: 导出EXCEl
     * @param outputStream:
    * @param user:
    * @return: void
    * @author xyh
    * @date: 2021/11/15 15:01
    */
    void export(OutputStream outputStream, User user);

    /**
    * @description: 导入EXCEl
     * @param inputStream:
    * @return: void
    * @author xyh
    * @date: 2021/11/15 15:12
    */
    void doImport(InputStream inputStream) throws IOException;
}
