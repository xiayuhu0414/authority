package com.xyh.authorityManagement.aop;

import com.xyh.authorityManagement.annotation.LogAnno;
import com.xyh.authorityManagement.pojo.Log;
import com.xyh.authorityManagement.pojo.User;
import com.xyh.authorityManagement.service.ILogService;
import com.xyh.authorityManagement.util.UserUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;

/**
 * 使用 AOP 实现日志功能
 *
 * @author xyh
 * @date 2021/11/14 9:39
 */
@Order(3)
@Component
@Aspect
public class LogAopAspect {
    @Autowired
    private ILogService logService;

    /**
     * @param pjp:
     * @description: 环绕通知记录日志注解匹配到需要增加日志功能的方法
     * @return: java.lang.Object
     * @author xyh
     * @date: 2021/11/14 15:13
     */
    @Around("@annotation(com.xyh.authorityManagement.annotation.LogAnno)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        //1.方法执行前的处理，相当于前置通知
        //获取方法签名
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        //获取方法
        Method method = methodSignature.getMethod();
        //获取方法上面的注解
        LogAnno logAnno = method.getAnnotation(LogAnno.class);
        //获取操作描述的属性值
        String operateType = logAnno.operateType();
        //创建一个日志对象（准备记录日志）
        Log log = new Log();
        User user = UserUtils.getSubjectUser();
        String ip = UserUtils.getIpAddress();
        log.setOperatorType(operateType);
        log.setOperator(user.getUserCode());
        log.setIp(ip);
        Object result = null;
        try {
            //让代理方法执行
            result =pjp.proceed();
            //相当于后置通知
            log.setOperateResult("正常");
        }catch (SQLException e){
            //相当于异常通知
            log.setOperateResult("异常操作");
        }finally {
            //相当于最终通知
            log.setOperateDate(new Date());
            logService.addLog(log);
        }
        return result;
    }
}
