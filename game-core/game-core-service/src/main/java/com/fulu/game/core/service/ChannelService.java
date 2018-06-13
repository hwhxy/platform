package com.fulu.game.core.service;

import com.fulu.game.core.entity.Channel;
import com.fulu.game.core.entity.vo.ChannelVO;

import java.util.List;

/**
 * 渠道商表
 *
 * @author yanbiao
 * @date 2018-06-13 15:33:34
 */
public interface ChannelService extends ICommonService<Channel, Integer> {

    /**
     * 参数查询
     * @param channelVO
     * @return
     */
    List<Channel> findByParam(ChannelVO channelVO);

    /**
     * 渠道新增
     * @param name
     * @return
     */
    Channel save(String name);
}