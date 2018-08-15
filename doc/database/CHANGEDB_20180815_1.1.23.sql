--分期乐订单拓展表
DROP TABLE IF EXISTS `t_fenqile_order`;
CREATE TABLE `t_fenqile_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(128) NOT NULL COMMENT '订单编号',
  `payment_no` varchar(512) DEFAULT NULL COMMENT '支付编号',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) COMMENT='分期乐订单拓展表';

--分期乐对账表
DROP TABLE IF EXISTS `t_fenqile_reconciliation`;
CREATE TABLE `t_fenqile_reconciliation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_no` varchar(128) NOT NULL COMMENT '订单编号',
  `status` tinyint(1) DEFAULT '0' COMMENT '对账状态（0：未对账（默认）；1：已对账）',
  `amount` decimal(11,2) DEFAULT '0.00' COMMENT '对账金额',
  `process_time` datetime DEFAULT NULL COMMENT '对账时间',
  `admin_id` int(11) DEFAULT NULL COMMENT '对账人id',
  `admin_name` varchar(255) DEFAULT NULL COMMENT '对账人用户名',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) COMMENT='分期乐对账表';