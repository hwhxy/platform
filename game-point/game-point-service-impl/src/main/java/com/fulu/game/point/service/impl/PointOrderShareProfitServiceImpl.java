package com.fulu.game.point.service.impl;


import com.fulu.game.core.entity.Order;
import com.fulu.game.core.service.impl.OrderShareProfitServiceImpl;
import com.github.binarywang.wxpay.exception.WxPayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@Slf4j
public class PointOrderShareProfitServiceImpl extends OrderShareProfitServiceImpl {

    @Autowired
    private PointMiniAppPayServiceImpl pointMiniAppPayService;

    @Override
    public Boolean refund(Order order, BigDecimal actualMoney, BigDecimal refundUserMoney) throws WxPayException {
        return pointMiniAppPayService.refund(order.getOrderNo(), actualMoney, refundUserMoney);
    }
}
