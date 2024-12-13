package com.hhu.annotation.Validator;

import cn.hutool.core.util.StrUtil;
import com.hhu.annotation.PasswordCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordCheck, String> {

    private Pattern regexp;
    @Override
    public void initialize(PasswordCheck constraintAnnotation) {
        this.regexp = Pattern.compile(constraintAnnotation.regexp());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if (StrUtil.isEmpty(value)) {
            // 禁用默认的错误信息
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("密码不能为空") // 自定义错误信息
                    .addConstraintViolation(); // 添加错误信息
            return false;   //value不能为空
        }
        // 使用正则进行匹配
        return regexp.matcher(value).matches(); // 使用正则匹配
    }
}
