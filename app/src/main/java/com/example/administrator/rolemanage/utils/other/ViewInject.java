package com.example.administrator.rolemanage.utils.other;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Box
 * 
 * @since Jdk1.6 或 Jdk1.7
 * 
 * @version v1.0
 *
 * @date 2015年10月21日 上午11:50:49
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInject {

	int value() default -1;

}
