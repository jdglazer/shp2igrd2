package com.jdglazer.shp2igrd2.job.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Links tasks in a dependency tree and returns tasks based on the completion state of 
 * the dependencies of the task. It also provides the ability to get the data provider task
 * for any given task. This is important because a task might depend on more than one other
 * task so we need to be able to differentiate it's data provider task. This also keeps track
 * of remaining tasks counts so that we know when we're all done.
 * 
 * @author jdglazer
 *
 */
public class DependentTaskSet implements Iterator<Task<?,?>> {
	
	protected int                              notCompleteCount = 0;
	private Map<Task<?,?>, TaskDependencyInfo> dependencies     = new HashMap<>();
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Task<?,?> next() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Task<?,?> getDataProviderForTask(Task<?,?> task) {
		// TODO return task that provides data for argument
		return null;
	}
	
	public void update() {
		
	}
	
	public boolean allTasksComplete() {
		return notCompleteCount==0;
	}
	
	private class TaskDependencyInfo {
		Task<?,?>       dataProviderTask;
		List<Task<?,?>> beforeDeps = new ArrayList<>();
		List<Task<?,?>> afterDeps  = new ArrayList<>();
	}
}
