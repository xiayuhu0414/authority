package com.xyh.authorityManagement.shiro;

import com.xyh.authorityManagement.pojo.Menu;
import com.xyh.authorityManagement.pojo.User;
import com.xyh.authorityManagement.service.IMenuService;
import com.xyh.authorityManagement.service.IUserService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * shiro数据源
 * @author xyh
 * @date 2021/11/14 10:13
 */
public class Realm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;
    @Autowired
    private IMenuService menuService;

    /**
    * @description: 授权方法
     * @param principalCollection:
    * @return: org.apache.shiro.authz.AuthorizationInfo
    * @author xyh
    * @date: 2021/11/14 10:16
    */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
       //获取当前登录对象
        User user=(User)principalCollection.getPrimaryPrincipal();
        //获取用户的所有菜单
        List<Menu> menus=menuService.findMenuListByUserId(user.getUserId());
        // Shiro 提供用于聚合授权信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (Menu menu : menus){
            info.addStringPermission(menu.getMenuName());
        }
        return info;
    }

    /**
    * @description: 认证方法
     * @param authenticationToken:
    * @return: org.apache.shiro.authc.AuthenticationInfo
    * @author xyh
    * @date: 2021/11/14 10:17
    */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       //UsernamePasswordToken是一个简单的包含username及password即用户名及密码的登录验证用token
        UsernamePasswordToken upt=(UsernamePasswordToken)authenticationToken;
        String pwd = new String(upt.getPassword());
        //根据用户名和密码查找用户
        User user = userService.findUserByCodeAndPwd(upt.getUsername(),pwd);
        if (user!=null){
            //返回认证信息 参数1：主角 参数2：证书 参数3：当前realm
            return new SimpleAuthenticationInfo(user,pwd,getName());
        }
        return null;
    }
}
