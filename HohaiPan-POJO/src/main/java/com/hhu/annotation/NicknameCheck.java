package com.hhu.annotation;

import com.hhu.annotation.Validator.NicknameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.hhu.constant.ValidationConstant.*;

@Constraint(validatedBy = NicknameValidator.class) // 绑定校验逻辑
@Target({ElementType.FIELD, ElementType.METHOD}) // 可用在字段或方法上
@Retention(RetentionPolicy.RUNTIME) // 在运行时有效
public @interface NicknameCheck {
    // 默认的校验错误信息
    String message() default NICKNAME_MSG;
    // 支持分组校验
    Class<?>[] groups() default {};

    // 自定义负载（通常用来扩展）
    Class<? extends Payload>[] payload() default {};

    // 自定义属性：指定前缀
    String regexp() default NICKNAME_REGEXP;
}
