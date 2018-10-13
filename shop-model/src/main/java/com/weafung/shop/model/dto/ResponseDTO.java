package com.weafung.shop.model.dto;

import com.weafung.shop.common.constant.CodeConstant;
import com.weafung.shop.common.constant.CodeEnum;
import lombok.Data;

/**
 * @author weifeng
 */
@Data
public class ResponseDTO<T> {
    private Integer code;
    private T data;
    private String msg;

    public ResponseDTO() {
        this(CodeConstant.SUCCESS, null, "");
    }

    public ResponseDTO(T data) {
        this(CodeConstant.SUCCESS, data, "");
    }

    public ResponseDTO(T data, String msg) {
        this(CodeConstant.SUCCESS, data, msg);
    }

    public ResponseDTO(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> ResponseDTO<T> build(T data) {
        return new ResponseDTO<>(null, data, null);
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