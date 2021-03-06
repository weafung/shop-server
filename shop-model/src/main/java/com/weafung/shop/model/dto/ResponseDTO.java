package com.weafung.shop.model.dto;

import com.weafung.shop.common.constant.CodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author weifeng
 */
@Data
public class ResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer code;
    private T data;
    private String msg;

    public ResponseDTO() {
        this(CodeEnum.SUCCESS.getCode(), null, CodeEnum.SUCCESS.getMsg());
    }

    public ResponseDTO(T data) {
        this(CodeEnum.SUCCESS.getCode(), data, CodeEnum.SUCCESS.getMsg());
    }

    public ResponseDTO(T data, String msg) {
        this(CodeEnum.SUCCESS.getCode(), data, msg);
    }

    public ResponseDTO(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> ResponseDTO<T> buildSuccess(T data) {
        return new ResponseDTO<>(CodeEnum.SUCCESS.getCode(), data, CodeEnum.SUCCESS.getMsg());
    }

    public static <T> ResponseDTO<T> build(Integer code, T data, String msg) {
        return new ResponseDTO<>(code, data, msg);
    }

    public static <T> ResponseDTO<T> build(CodeEnum codeEnum) {
        return new ResponseDTO<>(codeEnum.getCode(), null, codeEnum.getMsg());
    }

    public static <T> ResponseDTO<T> build(CodeEnum codeEnum, T data) {
        return new ResponseDTO<>(codeEnum.getCode(), data, codeEnum.getMsg());
    }
}
