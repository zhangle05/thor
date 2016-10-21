package com.jotunheim.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {

	/**
	 * 是否要求登录，false表示用户无需登录
	 * @return
	 */
	boolean required() default true;

	/**
	 * 页面等级，大于或等于该等级的用户才可访问
	 * @return
	 */
	int rank() default 1;
	
}
