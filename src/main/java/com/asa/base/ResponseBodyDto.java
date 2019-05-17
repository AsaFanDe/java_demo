	package com.asa.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 返回内容体
 * @author ALIENWARE
 *
 */
@Data
public class ResponseBodyDto<T> implements Serializable{

    private static final long serialVersionUID = -2134842711205889282L;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 	响应数据体
     */
    private T dataBody;

    /**
     * 响应时间
     */
    private Date responseDate;

    public ResponseBodyDto() {
    }

    public ResponseBodyDto(T dataBody) {
        this.code = 200;
        this.msg = "操作成功";
        this.dataBody = dataBody;
    }
}
