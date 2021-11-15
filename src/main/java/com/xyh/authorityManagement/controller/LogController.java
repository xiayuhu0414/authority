package com.xyh.authorityManagement.controller;

import com.xyh.authorityManagement.pojo.Log;
import com.xyh.authorityManagement.service.ILogService;
import com.xyh.authorityManagement.vo.EasyUiDataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 日志前端控制器
 *
 * @author xyh
 * @date 2021/11/14 10:26
 */
@Controller
public class LogController {
    @Autowired
    private ILogService logService;

    /**
     * @param log:
     * @param page:
     * @param rows:
     * @description: 日志分页查询
     * @return: com.xyh.authorityManagement.vo.EasyUiDataGridResult
     * @author xyh
     * @date: 2021/11/14 16:37
     */
    @RequestMapping("/log/logListByPage")
    @ResponseBody
    public EasyUiDataGridResult roleListByPage(Log log,
                                               @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                                               @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows) {
        return logService.findLogListByPage(page, rows, log);
    }


    /**
     * @param str:
     * @description: 模糊查询用户名
     * @return: java.util.List<com.xyh.authorityManagement.pojo.Log>
     * @author xyh
     * @date: 2021/11/14 16:40
     */
    @RequestMapping(value = "/log/searchLogOperator",method = RequestMethod.POST)
    public List<Log> searchLogOperator(String str) {
        return logService.findLogOperator(str);
    }
}
