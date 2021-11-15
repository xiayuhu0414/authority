package com.xyh.authorityManagement.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 * @author xyh
 * @date 2021/11/14 10:04
 */
@Data
public class User implements Serializable {
    public static final long serialVersionUID=1L;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户账号
     */
    private String userCode;
    /**
     * 用户密码，不转换json字符串
     */
    @JsonIgnore
    private String password;
    /**
     * 用户出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date userBirthday;
}
