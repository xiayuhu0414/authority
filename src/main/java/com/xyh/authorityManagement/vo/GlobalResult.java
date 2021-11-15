package com.xyh.authorityManagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 全局返回对象
 *
 * @author xyh
 * @date 2021/11/14 10:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请求响应状态
     */
    private Integer status;
    /**
     * 请求响应信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private Object data;

    public GlobalResult(Integer status, String msg) {
    }


    public static GlobalResult build(Integer status, String msg) {
        return new GlobalResult(status, msg);
    }
    public static GlobalResult build(Integer status, String msg, Object data) {
        return new GlobalResult(status, msg, data);
    }

    public static GlobalResult ok() {
        return new GlobalResult(null);
    }

    public static GlobalResult ok(Object data) {
        return new GlobalResult(data);
    }

    public GlobalResult(Object data) {
        this.status = 200;
        this.msg = msg;
        this.data = data;
    }

    public Boolean isOk() {
        return this.status == 200;
    }
}
