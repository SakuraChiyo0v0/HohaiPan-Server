package com.hhu.result;

import com.hhu.constant.ResultCodeConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * 后端同意返回结果
 */
@Data
public class Result<T> implements Serializable {

    private Integer code; //编码：1成功，0和其它数字为失败
    private String msg; //错误信息
    private T data; //数据

    //无数据成功
    public static <T> Result<T> success(){
        Result<T> result=new Result<T>();
        result.code= ResultCodeConstant.SUCCESS;
        return result;
    }

    //有数据成功
    public static <T> Result<T> success(T object){
        Result<T> result=new Result<T>();
        result.code= ResultCodeConstant.SUCCESS;
        result.data=object;
        return result;
    }

    //失败
    public static <T> Result<T> error(String msg){
        Result<T> result=new Result<T>();
        result.code= ResultCodeConstant.ERROR;
        result.msg=msg;
        return result;
    }

}
