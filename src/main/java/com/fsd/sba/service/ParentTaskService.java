package com.fsd.sba.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsd.sba.entity.ParentTaskEt;
import com.fsd.sba.model.ParentTask;
import com.fsd.sba.repository.ParentTaskRepository;
import com.fsd.sba.transformer.Transformer;

@Service
public class ParentTaskService {
	
	@Autowired
	private ParentTaskRepository repository;
	
	@Autowired
	private Transformer transformer;

	public List<ParentTask> getParentTaskForProject(Integer projectId) {
		
		List<ParentTaskEt> parentTasks = repository.findByProjectId(projectId);
		
		List<ParentTask> parentTaskList = new ArrayList<ParentTask>();
		
		if (CollectionUtils.isNotEmpty(parentTasks)) {
			for (ParentTaskEt pt: parentTasks) {
				parentTaskList.add(transformer.buildParentTaskModel(pt));
			}
		}
		
		return parentTaskList;
	}

}
