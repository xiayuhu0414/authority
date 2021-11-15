package com.xyh.authorityManagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author xyh
 * @date 2021/11/14 9:41
 */
@Data
public class Log {
    /**
     * 日志id
     * */
    private Integer id;
    /**
     * 日志操作对象
     */
    private  String operator;
    /**
     *日志类型
     */
    private String operatorType;
    /**
     * 日志日期
     */
    private Date operateDate;
    /**
     * 操作结果
     */
    private String operateResult;
    /**
     * 操作者ip
     */
    private String ip;

}
