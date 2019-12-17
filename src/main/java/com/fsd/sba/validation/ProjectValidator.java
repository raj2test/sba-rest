/**
 * 
 */
package com.fsd.sba.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.fsd.sba.constant.Status;
import com.fsd.sba.model.Project;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public class ProjectValidator implements ConstraintValidator<ProjectValidatorConstraint, Project> {

	@Override
	public boolean isValid(Project value, ConstraintValidatorContext context) {

		boolean flag = true;
		context.disableDefaultConstraintViolation();

		if (null != value) {
			if (StringUtils.isEmpty(value.getProject())) {
				context.buildConstraintViolationWithTemplate("{project.project.required}").addPropertyNode("project")
						.addConstraintViolation();
				flag = false;
			}
			if (StringUtils.isBlank(value.getStatus())) {
				context.buildConstraintViolationWithTemplate("{project.status.required}").addPropertyNode("priority")
						.addConstraintViolation();
				flag = false;
			} else if (Status.getStatus(value.getStatus()) == null) {
				context.buildConstraintViolationWithTemplate("{project.status.invalid}").addPropertyNode("priority")
						.addConstraintViolation();
				flag = false;
			}
			if (null == value.getPriority()) {
				context.buildConstraintViolationWithTemplate("{project.priority.required}").addPropertyNode("priority")
						.addConstraintViolation();
				flag = false;
			} else if (value.getPriority() < 0 || value.getPriority() > 30) {
				flag = false;
				context.buildConstraintViolationWithTemplate("{project.priority.range}").addPropertyNode("priority")
						.addConstraintViolation();
			}
			if ((null != value.getStartDate() && null == value.getEndDate())
					|| (null == value.getStartDate() && null != value.getEndDate()) || (null != value.getStartDate()
							&& null != value.getEndDate() && value.getStartDate().isAfter(value.getEndDate()))) {
				context.buildConstraintViolationWithTemplate("{project.date.range}").addPropertyNode("startDate")
						.addConstraintViolation();
				flag = false;
			}

		} else {
			context.buildConstraintViolationWithTemplate("{project.invalid.payload}").addPropertyNode("project")
					.addConstraintViolation();
			flag = false;
		}

		return flag;
	}

}
