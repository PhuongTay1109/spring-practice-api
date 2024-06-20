package com.tay.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
//chỉ ra rằng EnumValue là 1 ràng buộc và EnumValueValidator là lớp xử lý cho ràng buộc này
@Constraint(validatedBy = EnumValueValidator.class) 
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValue {
	String name();

	String message() default "{name} must be any of enum {enumClass}";

	Class<? extends Enum<?>> enumClass();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
