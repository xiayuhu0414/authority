package com.xyh.authorityManagement.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * easyUi分页
 * @author xyh
 * @date 2021/11/14 10:47
 */
@Data
public class EasyUiDataGridResult implements Serializable {
    public static final long serialVersionUID=1L;
    /**
     * 数据库中总记录数
     */
    private Integer total;
    /**
     * 当前页数据
     */
    private List<?> rows;

}
