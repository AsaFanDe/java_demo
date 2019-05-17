package com.asa.exception;

/**
 * @className: BusinessException
 * @Description 事务信息错误类
 * @Date 2018-09-21 14:46
 * @Author Asa
 * @Version 1.0
 **/
public class BusinessException extends RuntimeException{

    private int code;

    public BusinessException(){

    }

    public BusinessException(int code, String message){
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
