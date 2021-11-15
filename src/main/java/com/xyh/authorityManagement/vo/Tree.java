package com.xyh.authorityManagement.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xyh.authorityManagement.pojo.Menu;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xyh
 * @date 2021/11/14 10:49
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@Data
@ToString
public class Tree {
    /**
     *菜单ID
     */
    private String id;
    /**
     * 菜单名称
     */
    private String text;
    /**
     * 菜单状态
     */
    private Integer status;
    /**
     * 是否选中
     */
    private boolean checked;
    /**
     * 下级菜单
     */
    private List<Menu> children;

    public List<Menu>getChildren(){
        if (null==children){
            children = new ArrayList<>();
        }
        return children;
    }

}
