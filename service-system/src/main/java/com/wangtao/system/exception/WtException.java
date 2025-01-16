package com.wangtao.system.exception;

import com.atguigu.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * 自定义的全局异常类
 */
@Data
public class WtException extends RuntimeException{
    private Integer code;
    private String message;

    public WtException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public WtException(ResultCodeEnum resultCodeEnum){
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "GuiguException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
