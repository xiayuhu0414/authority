package com.xyh.authorityManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyh.authorityManagement.mapper.LogMapper;
import com.xyh.authorityManagement.pojo.Log;
import com.xyh.authorityManagement.service.ILogService;
import com.xyh.authorityManagement.vo.EasyUiDataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 日志服务实现类
 * @author xyh
 * @date 2021/11/14 10:20
 */
@Service
public class LogServiceImpl implements ILogService {
    @Autowired
    private LogMapper logMapper;

    /**
    * @description:  日志插入
     * @param log:
    * @return: boolean
    * @author xyh
    * @date: 2021/11/14 16:44
    */
    @Override
    public boolean addLog(Log log) {
        return logMapper.insertLog(log)>0;
    }


    /**
    * @description: 模糊查询用户名
     * @param str:
    * @return: java.util.List<com.xyh.authorityManagement.pojo.Log>
    * @author xyh
    * @date: 2021/11/14 16:44
    */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Log> findLogOperator(String str) {
        return logMapper.selectLogOperator(str);
    }


    /**
    * @description: 日志查询
     * @param page:
    * @param rows:
    * @param log:
    * @return: com.xyh.authorityManagement.vo.EasyUiDataGridResult
    * @author xyh
    * @date: 2021/11/14 16:51
    */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public EasyUiDataGridResult findLogListByPage(Integer page, Integer rows, Log log) {
        PageHelper.startPage(page,rows);
        List<Log> list = logMapper.selectLogListPage(log);
        PageInfo<Log> pageInfo = new PageInfo<>(list);
        EasyUiDataGridResult result = new EasyUiDataGridResult();
        result.setTotal((int)pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }
}
