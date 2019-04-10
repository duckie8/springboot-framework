package me.myschools.demo.utils;

import cn.zucc.netdisc.domain.ParamValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandle {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @return
     * @ExceptionHandler(value = Exception.class):声明要捕获的异常类
     */
    @ExceptionHandler(value = Exception.class)
    public HttpResult handle(Exception e) {
        logger.error(e.getMessage(), e);
        if (e instanceof ExceptionUtil) {
            ExceptionUtil exceptionUtil = (ExceptionUtil) e;
            return HttpResultUtil.error(exceptionUtil.getCode(), exceptionUtil.getMessage());
        } else {
            return HttpResultUtil.error(-1, e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpResult handle404Error(HttpServletRequest req, HttpServletResponse rsp, Exception e) throws Exception {
        logger.error(e.getMessage());
        return HttpResultUtil.error(404, e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Object MethodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        //按需重新封装需要返回的错误信息
        List<ParamValidationResult> paramValidationResults = new ArrayList<>();
        //解析原错误信息，封装后返回，此处返回非法的字段名称，错误信息
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            ParamValidationResult validationResult = new ParamValidationResult();
            validationResult.setMessage(error.getDefaultMessage());
            validationResult.setParam(error.getField());
            paramValidationResults.add(validationResult);
        }
        logger.error(ExceptionResultEnum.PARAMETER_VERIFICATION_ERROR.getMsg());
        return HttpResultUtil.error(ExceptionResultEnum.PARAMETER_VERIFICATION_ERROR, paramValidationResults);
    }

    @ExceptionHandler(value = ServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResult ServerError(HttpServletRequest req, HttpServletResponse rsp, Exception e) {
        logger.error(e.getMessage());
        return HttpResultUtil.error(500, e.getMessage());
    }
}
