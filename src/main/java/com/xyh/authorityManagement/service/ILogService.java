package com.xyh.authorityManagement.service;

import com.xyh.authorityManagement.pojo.Log;
import com.xyh.authorityManagement.vo.EasyUiDataGridResult;

import java.util.List;

/**
 * 日志服务类
 * @author xyh
 * @date 2021/11/14 10:18
 */
public interface ILogService {

    /**
    * @description: 添加日志
     * @param log:
    * @return: void
    * @author xyh
    * @date: 2021/11/14 15:27
    */
    boolean addLog(Log log);

    /**
    * @description:  模糊查询用户名
     * @param str:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Log>
    * @author xyh
    * @date: 2021/11/14 16:41
    */
    List<Log> findLogOperator(String str);

    /**
    * @description: 日志查询
     * @param page:
    * @param rows:
    * @param log:
    * @return: com.xyh.authorityManagement.vo.EasyUiDataGridResult
    * @author xyh
    * @date: 2021/11/14 16:51
    */
    EasyUiDataGridResult findLogListByPage(Integer page, Integer rows, Log log);
}
