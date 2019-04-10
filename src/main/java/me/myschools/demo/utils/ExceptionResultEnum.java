package me.myschools.demo.utils;

public enum ExceptionResultEnum {
    UNKNOW_ERROR(-1, "未知错误"),
    MISSING_PARAMETERS(1000, "缺少参数"),
    INVALID_PARAMETER(1001, "无效参数"),
    INCORRECT_PARAMETER_TYPE(1002, "参数校验错误"),
    PARAMETER_VERIFICATION_ERROR(1003, "用户未登陆"),
    PERMISSION_DENIED(1004, "没有权限"),
    UPDATE_FAILED(1005, "数据更新失败"),
    FAILED_TO_DELETE(1006, "数据删除失败"),
    USERNAME_IS_ALREADY_OCCUPIED(1007,"用户名已被占用"),
    NOT_FOUND(404, "not found"),
    ACCESS_ERROR(500, "内部访问接口错误");

    private Integer code;
    private String msg;

    ExceptionResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
