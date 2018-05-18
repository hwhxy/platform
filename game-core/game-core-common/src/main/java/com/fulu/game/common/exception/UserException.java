package com.fulu.game.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserException extends BizException {
    private ExceptionCode exceptionCode;

    @AllArgsConstructor
    @Getter
    public enum ExceptionCode{
        USER_NOT_EXIST_EXCEPTION(20001, "用户不存在"),
        USER_MISMATCH_EXCEPTION(20002, "用户不匹配"),
        IllEGAL_MOBILE_EXCEPTION(20003, "手机号参数为空或不合法"),
        IllEGAL_IMID_EXCEPTION(20004, "imId参数为空或不合法");
        private int code;
        private String msg;
    }

    public UserException(ExceptionCode exceptionCode) {
        super();
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMsg();
        this.exceptionCode = exceptionCode;
    }
}
