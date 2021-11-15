package com.xyh.authorityManagement.mapper;

import com.xyh.authorityManagement.pojo.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 * @author xyh
 * @date 2021/11/14 10:32
 */
public interface LogMapper {

    /**
    * @description:  日志添加
     * @param log:
    * @return: boolean
    * @author xyh
    * @date: 2021/11/14 15:29
    */
    int insertLog(Log log);

    /**
    * @description: 模糊查询用户名
     * @param str:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Log>
    * @author xyh
    * @date: 2021/11/14 16:45
    */
    List<Log> selectLogOperator(@Param("operator") String str);

    /**
    * @description: 日志分页查询
     * @param log:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Log>
    * @author xyh
    * @date: 2021/11/14 17:06
    */
    List<Log> selectLogListPage(Log log);
}
