package com.fulu.game.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户表
 *
 * @author wangbin
 * @date 2018-04-20 11:12:12
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键id
    private Integer id;
    //手机号
    private String mobile;
    //昵称
    private String nickname;
    //性别(0不公开,1男,2女)
    private Integer gender;
    //头像URL
    private String headPortraitsUrl;

    private Integer age;
    //密码
    @JsonIgnore
    private String password;
    //密码盐
    @JsonIgnore
    private String salt;
    //真实姓名
    private String realname;
    //身份证
    private String idcard;
    //1:普通用户,2:打手,3:渠道商
    private Integer type;
    //信息认证(0未完善,1已完善,2审核通过)
    private Integer userInfoAuth;
    //零钱
    @JsonIgnore
    private BigDecimal balance;
    //状态(0封禁,1为解封)
    private Integer status;

    @JsonIgnore
    private String openId;
    private String city;
    private String province;
    private String country;
    //综合得星评分数
    private BigDecimal scoreAvg;
    //星座
    private String constellation;
    //生日
    private String birth;
    private String imId;
    private String imPsw;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    //修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
