package com.fulu.game.core.service;

import com.fulu.game.core.entity.ChannelCashDetails;

import java.math.BigDecimal;

/**
 * 渠道商金额流水表
 *
 * @author yanbiao
 * @date 2018-06-13 15:33:45
 */
public interface ChannelCashDetailsService extends ICommonService<ChannelCashDetails, Integer> {

    /**
     * 管理员加款
     * @param channelId
     * @param money
     * @param remark
     * @return
     */
    ChannelCashDetails addCash(Integer channelId, BigDecimal money, String remark);
}
