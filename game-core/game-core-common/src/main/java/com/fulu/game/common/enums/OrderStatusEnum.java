package com.fulu.game.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    SYSTEM_CLOSE(100, "订单关闭"),//系统关闭订单
    ADMIN_CLOSE(110, "订单关闭"),//管理员关闭订单
    USER_CANCEL(101, "订单关闭"),//用户取消订单
    SERVER_CANCEL(160, "订单关闭"),//陪玩师取消订单
    NON_PAYMENT(200, "待付款"),
    WAIT_SERVICE(210, "等待接单"),//已付款
    ALREADY_RECEIVING(213, "等待开始"),//陪玩师已接单
    SERVICING(220, "陪玩中"), //服务进行中
    CHECK(300, "等待验收"),   //陪玩师提交验收
    CONSULTING(350,"协商中"),
    CONSULT_REJECT(352,"协商拒绝"),
    APPEALING(400, "仲裁中"), //用户申诉订单
    APPEALING_ADMIN(401, "仲裁中"), //管理员申诉订单
    ADMIN_REFUND(410, "仲裁完成:用户胜诉"),//管理员退款用户
    CONSULT_COMPLETE(415, "协商完成"),//陪玩师退钱给用户
    ADMIN_NEGOTIATE(420, "仲裁完成:协商处理"),//管理员处理订单一部分用户一部分陪玩师
    COMPLETE(500, "待评价"),//用户验收订单
    SYSTEM_COMPLETE(501, "待评价"),//系统自动完成订单
    ADMIN_COMPLETE(502, "仲裁完成:陪玩师胜诉"),//管理员强制完成订单
    ALREADY_APPRAISE(600, "已评价");//已评价


    private Integer status;
    private String msg;

    public static String getMsgByStatus(Integer status) {
        for (OrderStatusEnum statusEnum : OrderStatusEnum.values()) {
            if (statusEnum.status.equals(status)) {
                return statusEnum.msg;
            }
        }
        return null;
    }


}
