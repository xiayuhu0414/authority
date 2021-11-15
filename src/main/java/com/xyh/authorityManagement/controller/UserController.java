package com.xyh.authorityManagement.controller;

import com.xyh.authorityManagement.annotation.LogAnno;
import com.xyh.authorityManagement.pojo.Log;
import com.xyh.authorityManagement.pojo.User;
import com.xyh.authorityManagement.service.ILogService;
import com.xyh.authorityManagement.service.IUserService;
import com.xyh.authorityManagement.util.UserUtils;
import com.xyh.authorityManagement.vo.EasyUiDataGridResult;
import com.xyh.authorityManagement.vo.EasyUiOptionalTreeNode;
import com.xyh.authorityManagement.vo.GlobalResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 用户前端控制器
 *
 * @author xyh
 * @date 2021/11/14 10:27
 */
@Controller
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ILogService logService;

    /**
     * @param userCode:
     * @param password:
     * @description: 用户登录
     * @return: com.xyh.authorityManagement.vo.GlobalResult
     * @author xyh
     * @date: 2021/11/14 19:10
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResult login(String userCode, String password) {
        try {
            //1.创建令牌
            UsernamePasswordToken token = new UsernamePasswordToken(userCode, password);
            //2.获取主题subject
            Subject subject = SecurityUtils.getSubject();
            //3.执行login方法
            subject.login(token);
            //4.登录日志记录
            Log log = new Log();
            log.setOperateDate(new Date());
            log.setOperator(userCode);
            log.setOperateResult("正常");
            log.setOperatorType("登录");
            log.setIp(UserUtils.getIpAddress());
            logService.addLog(log);
            return GlobalResult.build(200, "");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return GlobalResult.build(400, "账号密码错误");
        }
    }

    /**
     * @param user:
     * @param page:
     * @param rows:
     * @description: 用户分页查询
     * @return: com.xyh.authorityManagement.vo.EasyUiDataGridResult
     * @author xyh
     * @date: 2021/11/14 19:15
     */
    @RequestMapping(value = "/user/userListByPage", method = RequestMethod.POST)
    @ResponseBody
    public EasyUiDataGridResult uiDateGridResult(User user,
                                                 @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                                                 @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows) {
        return userService.findUserListByPage(user, page, rows);

    }

    /**
     * @param str:
     * @description: 用户名自动补全
     * @return: java.util.List<com.xyh.authorityManagement.pojo.User>
     * @author xyh
     * @date: 2021/11/14 19:18
     */
    @RequestMapping(value = "/user/searchUserName", method = RequestMethod.POST)
    @ResponseBody
    public List<User> searchUserName(String str) {
        return userService.findUserName(str);
    }

    /**
     * @param user:
     * @description: 添加用户
     * @return: com.xyh.authorityManagement.vo.GlobalResult
     * @author xyh
     * @date: 2021/11/14 19:24
     */
    @LogAnno(operateType = "添加用户")
    @RequestMapping(value = "/user/userAdd", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResult userAdd(User user) {
        return userService.addUser(user);
    }

    /**
     * @param user:
     * @description: 更新用户信息
     * @return: com.xyh.authorityManagement.vo.GlobalResult
     * @author xyh
     * @date: 2021/11/14 19:27
     */
    @LogAnno(operateType = "更新用户")
    @RequestMapping(value = "/user/userUpdate", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResult userUpdate(User user) {
        return userService.updateUser(user);
    }

    /**
     * @param oldPassword:
     * @param newPassword:
     * @description: 更新密码
     * @return: com.xyh.authorityManagement.vo.GlobalResult
     * @author xyh
     * @date: 2021/11/14 19:32
     */
    @LogAnno(operateType = "更新密码")
    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResult updatePassword(String oldPassword, String newPassword) {
        User user = UserUtils.getSubjectUser();
        UserUtils.removeSubjectUser();
        return userService.updatePassword(user, oldPassword, newPassword);
    }

    /**
     * @param user:
     * @description: 删除用户
     * @return: com.xyh.authorityManagement.vo.GlobalResult
     * @author xyh
     * @date: 2021/11/14 19:34
     */
    @LogAnno(operateType = "删除用户")
    @RequestMapping(value = "/user/userDelete", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResult userDelete(User user) {
        return userService.deleteUser(user.getUserId());
    }

    /**
     * @param userId:
     * @description: 通过用户id查询用户角色
     * @return: java.util.List<com.xyh.authorityManagement.vo.EasyUiOptionalTreeNode>
     * @author xyh
     * @date: 2021/11/14 19:39
     */
    @RequestMapping(value = "/user/findUserRole", method = RequestMethod.POST)
    @ResponseBody
    public List<EasyUiOptionalTreeNode> findUserRole(@RequestParam(value = "id", required = true) Integer userId) {
        return userService.findUserRole(userId);
    }

    /**
     * @param userId:
     * @param checkedIds:
     * @description: 更新用户对应权限
     * @return: com.xyh.authorityManagement.vo.GlobalResult
     * @author xyh
     * @date: 2021/11/14 19:43
     */
    @LogAnno(operateType = "更新用户对应角色")
    @RequestMapping(value = "/user/updateUserRole", method = {RequestMethod.POST})
    @ResponseBody
    public GlobalResult updateUserRole(@RequestParam(value = "id", required = true) Integer userId,
                                       @RequestParam(value = "checkIds", required = true) String checkedIds) {
        return userService.updateUserRole(userId, checkedIds);
    }

    /**
     * @description: 显示用户名
     * @return: com.xyh.authorityManagement.vo.GlobalResult
     * @author xyh
     * @date: 2021/11/14 19:49
     */
    @RequestMapping(value = "/user/showName")
    @ResponseBody
    public GlobalResult showName() {
        GlobalResult result = null;
        if (UserUtils.getSubjectUser() == null) {
            result = GlobalResult.build(400, "用户未登陆");
        } else {
            result = GlobalResult.build(200, UserUtils.getSubjectUser().getUserName());
        }
        return result;
    }

    /**
    * @description: 登出
    * @return: java.lang.String
    * @author xyh
    * @date: 2021/11/14 19:54
    */
    @RequestMapping(value = "/user/logout")
    @ResponseBody
    public String logout(){
        //登出日志记录
        Log log =new Log();
        log.setOperateDate(new Date());
        log.setOperator(UserUtils.getSubjectUser().getUserCode());
        log.setOperateResult("正常");
        log.setOperatorType("注销");
        log.setIp(UserUtils.getIpAddress());
        logService.addLog(log);
        UserUtils.removeSubjectUser();
        return null;
    }
    /**
    * @description: 根据user条件，导出对应的数据
     * @param user:
    * @param response:
    * @return: void
    * @author xyh
    * @date: 2021/11/14 20:28
    */
    @LogAnno(operateType = "导出用户信息Excel")
    @RequestMapping(value = "/user/userExport",method = RequestMethod.POST)
    @ResponseBody
    public void userExport(User user,HttpServletResponse response){
        String fileName="User_exportBy"+UserUtils.getSubjectUser().getUserName()+".xls";
        //响应对象
        try {
            //设置输出流，实现下载文件
            response.setHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes(),"ISO-8859-1"));
            userService.export(response.getOutputStream(),user);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
    * @description: 导入用户信息Excel
     * @param file:
    * @return: com.xyh.authorityManagement.vo.GlobalResult
    * @author xyh
    * @date: 2021/11/14 20:33
    */
    @LogAnno(operateType = "导入用户信息Excel")
    @RequestMapping(value = "/user/userDoImport",method = RequestMethod.POST)
    @ResponseBody
    public GlobalResult userDoImport(MultipartFile file){
        try {
            userService.doImport(file.getInputStream());
            return new GlobalResult(200,"文件上传成功",null);
        }catch (IOException e){
            e.printStackTrace();
            return new GlobalResult(400,"文件上传失败",null);
        }
    }
}
