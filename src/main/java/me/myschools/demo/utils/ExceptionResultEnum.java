package me.myschools.demo.utils;

public enum ExceptionResultEnum {
    UNKNOW_ERROR(-1, "未知错误"),
    MISSING_PARAMETERS(100, "缺少参数"),
    INVALID_PARAMETER(101, "无效参数"),
    INCORRECT_PARAMETER_TYPE(102, "参数类型错误"),
    PARAMETER_VERIFICATION_ERROR(103, "参数校验错误"),
    PERMISSION_DENIED(104, "没有权限"),
    UPDATE_FAILED(105, "数据更新失败"),
    FAILED_TO_DELETE(106, "数据删除失败"),
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
