package com.xyh.authorityManagement.vo;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xyh
 * @date 2021/11/14 10:48
 */
@Data
@ToString
public class EasyUiOptionalTreeNode {
    /**
     * 菜单id
     */
    private String id;
    /**
     * 菜单名称
     */
    private String text;
    /**
     * 是否为选中
     */
    private boolean checked;
    /**
     * 下级菜单
     */
    private List<EasyUiOptionalTreeNode> children=new ArrayList<>();
}
