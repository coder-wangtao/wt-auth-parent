package com.wangtao.system.annotation;

import com.wangtao.system.enums.BusinessType;
import com.wangtao.system.enums.OperatorType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Documented
public @interface Log {
    String title() default "";
    BusinessType businessType() default BusinessType.OTHER;
    OperatorType operatorType() default OperatorType.MANAGER;
    boolean isSaveRequestData() default true;
    boolean isSaveResponseData() default true;
}
