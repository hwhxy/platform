package com.fulu.game.core.entity.vo;

import com.fulu.game.core.entity.UserComment;
import lombok.Data;

/**
 * 用户(打手)星级评论表
 * @author yanbiao
 * @date 2018-04-29 13:26:30
 */
@Data
public class UserCommentVO  extends UserComment {

    private String nickName;

    private String headUrl;

}
