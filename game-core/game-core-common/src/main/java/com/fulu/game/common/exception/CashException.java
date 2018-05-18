package com.fulu.game.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 提款打款业务自定义异常
 * yanbiao
 * 2018.4.25
 */
@Getter
public class CashException extends BizException {
    private ExceptionCode exceptionCode;

    @AllArgsConstructor
    @Getter
    public enum ExceptionCode {
        CASH_NEGATIVE_EXCEPTION(30001, "金额小于0"),
        CASH_EXCEED_EXCEPTION(30002, "提款金额超出余额");
        private int code;
        private String msg;
    }

    public CashException(ExceptionCode exceptionCode) {
        super();
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMsg();
        this.exceptionCode = exceptionCode;
    }
}
