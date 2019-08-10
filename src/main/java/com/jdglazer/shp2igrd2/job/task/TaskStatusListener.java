package com.jdglazer.shp2igrd2.job.task;

public interface TaskStatusListener {
	public void notifyStatusChange(Task<?,?> task, TaskStatus.StatusCode statusCode, TaskStatus.MessageLevel messageLevel, String message);
}
