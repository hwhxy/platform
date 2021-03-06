package com.fulu.game.core.entity.vo;

import com.fulu.game.core.entity.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserInfoVO {

    private Integer userId;
    private Integer type;

    private String imId;
    private String imPsw;
    private String nickName;

    private String headUrl;

    private String mainPhotoUrl;

    //评分
    private BigDecimal scoreAvg;

    private Integer age;

    private Integer gender;

    private String city;

    //声音
    private String voice;

    //声音时长
    private Integer voiceDuration;


    private Integer orderCount;

    private List<String> tags;

    //陪玩师照片集
    private List<String> photos;
    private List<String> techs;
    private List<Product> productList;
    private UserTechAuthVO userTechAuthVO;

}
