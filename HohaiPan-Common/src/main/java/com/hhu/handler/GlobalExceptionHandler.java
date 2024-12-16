package com.hhu.handler;

import com.hhu.constant.ResultCodeConstant;
import com.hhu.exception.BaseException;
import com.hhu.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 捕获参数校验异常
     */
    @ExceptionHandler
    public Result validationExceptionHandler(MethodArgumentNotValidException ex){
        log.error("参数校验异常：{}", ex.getMessage());

        // 创建 StringBuilder 来构建最终的错误信息
        StringBuilder errors = new StringBuilder();

        // 获取所有的错误信息
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        if(fieldErrors.size() == 1){
            errors.append(fieldErrors.get(0).getDefaultMessage());
        }else {
            // 为错误信息添加序号和换行
            for (int i = 0; i < fieldErrors.size(); i++) {
                FieldError error = fieldErrors.get(i);
                String errorMessage = error.getDefaultMessage();

                // 格式化错误信息：添加序号、字段名和错误信息，换行
                errors.append(String.format("[%d] %s ", i + 1, errorMessage));
            }
        }

        // 返回格式化后的错误信息
        return Result.error(errors.toString());
    }

}
