/**
 * 
 */
package com.fsd.sba.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProjectValidator.class)
public @interface ProjectValidatorConstraint {

	String message() default "{project.invalid.payload}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
