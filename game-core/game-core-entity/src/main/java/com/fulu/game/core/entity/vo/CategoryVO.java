package com.fulu.game.core.entity.vo;

import com.fulu.game.core.entity.Category;
import com.fulu.game.core.entity.Tag;
import com.fulu.game.core.entity.TechValue;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 分类表
 * 
 * @author wangbin
 * @date 2018-04-18 15:55:39
 */
@Data
public class CategoryVO extends Category {

    private List<TechValue>  salesModeList;

    private List<TechValue> danList;

    private List<Tag> tagList;

}