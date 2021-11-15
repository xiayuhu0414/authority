package com.xyh.authorityManagement.util;

import com.xyh.authorityManagement.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户工具类
 *
 * @author xyh
 * @date 2021/11/14 10:37
 */
public class UserUtils {

    /**
     * @description: 获取shiro中登录的用户
     * @return: com.xyh.authorityManagement.pojo.User
     * @author xyh
     * @date: 2021/11/14 10:40
     */
    public static User getSubjectUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    /**
     * @description: 从shiro中移除登录的用户
     * @return: void
     * @author xyh
     * @date: 2021/11/14 10:41
     */
    public static void removeSubjectUser() {
        SecurityUtils.getSubject().logout();
    }

    /**
    * @description: 获取当前request
    * @return: javax.servlet.http.HttpServletRequest
    * @author xyh
    * @date: 2021/11/14 10:44
    */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
    * @description: 获取ip地址
    * @return: java.lang.String
    * @author xyh
    * @date: 2021/11/14 10:45
    */
    public static String getIpAddress() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        String un="unknown";
        if (ip == null || ip.length() == 0 || un.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || un.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || un.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || un.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || un.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
