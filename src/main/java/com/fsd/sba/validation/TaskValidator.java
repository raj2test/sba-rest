/**
 * 
 */
package com.fsd.sba.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.fsd.sba.constant.Status;
import com.fsd.sba.model.Task;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public class TaskValidator implements ConstraintValidator<TaskValidatorConstraint, Task> {

	@Override
	public boolean isValid(Task value, ConstraintValidatorContext context) {

		boolean flag = true;
		context.disableDefaultConstraintViolation();

		if (null != value) {
			if (StringUtils.isEmpty(value.getTask())) {
				context.buildConstraintViolationWithTemplate("{task.task.required}").addPropertyNode("task")
						.addConstraintViolation();
				flag = false;
			}
			if (!value.isParentFlag()) {
				if (null == value.getPriority()) {
					context.buildConstraintViolationWithTemplate("{task.priority.required}").addPropertyNode("priority")
							.addConstraintViolation();
					flag = false;
				} else if (value.getPriority() < 0 || value.getPriority() > 30) {
					flag = false;
					context.buildConstraintViolationWithTemplate("{task.priority.range}").addPropertyNode("priority")
							.addConstraintViolation();
				}
				if (null == value.getStartDate()) {
					context.buildConstraintViolationWithTemplate("{task.startdate.required}").addPropertyNode("startDate")
							.addConstraintViolation();
					flag = false;
				}
				if (null == value.getEndDate()) {
					context.buildConstraintViolationWithTemplate("{task.endate.required}").addPropertyNode("endDate")
							.addConstraintViolation();
					flag = false;
				}
				if (null != value.getStartDate() && null != value.getEndDate()
						&& value.getStartDate().isAfter(value.getEndDate())) {
					context.buildConstraintViolationWithTemplate("{task.date.range}").addPropertyNode("startDate")
							.addConstraintViolation();
					flag = false;
				}
				if (null == value.getParentTask() || null == value.getParentTask().getParentId()) {
					context.buildConstraintViolationWithTemplate("{task.parent.mandatory}")
							.addPropertyNode("parentTask.parentId").addConstraintViolation();
					flag = false;
				}
				if (StringUtils.isBlank(value.getStatus())) {
					context.buildConstraintViolationWithTemplate("{task.status.required}").addPropertyNode("priority")
							.addConstraintViolation();
					flag = false;
				} else if (Status.getStatus(value.getStatus()) == null) {
					context.buildConstraintViolationWithTemplate("{task.status.invalid}").addPropertyNode("priority")
							.addConstraintViolation();
					flag = false;
				}
			} else {
				if (null != value.getPriority() || null != value.getStartDate() || null != value.getEndDate()) {
					context.buildConstraintViolationWithTemplate("{task.parent.invalid}").addPropertyNode("parentFlag")
							.addConstraintViolation();
					flag = false;
				}
			}
			if (null == value.getUser() || null == value.getUser().getUserId()) {
				context.buildConstraintViolationWithTemplate("{task.user.required}").addPropertyNode("user")
						.addConstraintViolation();
				flag = false;
			}
			if (null == value.getProject() || null == value.getProject().getProjectId()) {
				context.buildConstraintViolationWithTemplate("{task.project.required}").addPropertyNode("project")
						.addConstraintViolation();
				flag = false;
			}
		} else {
			context.buildConstraintViolationWithTemplate("{task.invalid.payload}").addPropertyNode("task")
					.addConstraintViolation();
			flag = false;
		}

		return flag;
	}

}
