package com.fulu.game.point.service.impl;

import com.fulu.game.common.config.WxMaServiceSupply;
import com.fulu.game.common.exception.OrderException;
import com.fulu.game.common.exception.PayException;
import com.fulu.game.core.entity.Order;
import com.fulu.game.core.entity.User;
import com.fulu.game.core.service.impl.pay.MiniAppPayServiceImpl;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PointMiniAppPayServiceImpl extends MiniAppPayServiceImpl {

    @Autowired
    private WxMaServiceSupply wxMaServiceSupply;

    @Autowired
    private PointMiniAppOrderServiceImpl pointMiniAppOrderService;

    @Override
    protected void payOrder(String orderNo, BigDecimal actualMoney) {
        pointMiniAppOrderService.payOrder(orderNo, actualMoney);
    }

    @Override
    public WxPayMpOrderResult pay(Order order, User user, String requestIp) {
        WxPayUnifiedOrderRequest orderRequest = buildWxPayRequest(order, requestIp);
        try {
            orderRequest.setOpenid(user.getPointOpenId());
            return wxMaServiceSupply.pointWxPayService().createOrder(orderRequest);
        } catch (Exception e) {
            log.error("上分订单支付错误", e);
            throw new OrderException(orderRequest.getOutTradeNo(), "上分订单无法支付!");
        }
    }

    @Override
    public WxPayOrderNotifyResult parseResult(String xmlResult) {
        try {
            return wxMaServiceSupply.pointWxPayService().parseOrderNotifyResult(xmlResult);

        } catch (Exception e) {
            throw new PayException(PayException.ExceptionCode.CALLBACK_FAIL);
        }
    }

    @Override
    public WxPayRefundResult refund(WxPayRefundRequest wxPayRefundRequest) {
        try {
            return wxMaServiceSupply.pointWxPayService().refund(wxPayRefundRequest);
        } catch (Exception e) {
            throw new PayException(PayException.ExceptionCode.CALLBACK_FAIL);
        }
    }

}
