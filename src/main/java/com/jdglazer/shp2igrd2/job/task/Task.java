package com.jdglazer.shp2igrd2.job.task;

import java.util.ArrayList;

import com.jdglazer.shp2igrd2.job.task.TaskStatus.MessageLevel;
import com.jdglazer.shp2igrd2.job.task.TaskStatus.StatusCode;

public abstract class Task<I,O> implements Runnable {
	
	protected ArrayList<TaskStatusListener> taskStatusListeners;
	
	protected I inputData;
	
	protected O outputData;
	
	protected boolean running = false;
	
	protected boolean completed = false;
	
	public void setInputData(Object inputData) {
		if(inputData==null)
			return;
		
		try {
		    I inputCast = (I) inputData;
		    if(!running && validateInputData(inputCast)) {
			    this.inputData = inputCast;
		    }
		} catch(ClassCastException cce) {
			//logger.error("Invalid object type set as input");
		}
	}
	
	public void addTaskStatusListener(TaskStatusListener taskStatusListener) {
		if(taskStatusListener==null)
			return;
		
		if(taskStatusListeners==null) {
			taskStatusListeners = new ArrayList<>();
		}
		
		taskStatusListeners.add(taskStatusListener);
	}	
	
	public O getOutputData() {
		return outputData;
	}
	
	protected void sendStatusUpdate(Task<?,?> task, StatusCode statusCode, MessageLevel messageLevel, String message) {
		if(statusCode==null)
			return;
		
		for(TaskStatusListener taskStatusListener: taskStatusListeners) {
			taskStatusListener.notifyStatusChange(this, statusCode, messageLevel, message);
		}
	}
	
	public void preRun() {
		running = true;
	}
	
	protected void postRun() {
		running = false;
		completed = true;
		sendStatusUpdate(this, StatusCode.COMPLETED, MessageLevel.INTERNAL, "Completed running task");
	}
	
	protected boolean validateInputData(I inputData) {
		return inputData != null;
	}
}
