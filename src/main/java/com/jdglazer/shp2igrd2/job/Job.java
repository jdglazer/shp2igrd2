package com.jdglazer.shp2igrd2.job;

import java.util.concurrent.ThreadPoolExecutor;

import com.jdglazer.shp2igrd2.job.task.DependentTaskSet;
import com.jdglazer.shp2igrd2.job.task.Task;
import com.jdglazer.shp2igrd2.job.task.TaskStatus;
import com.jdglazer.shp2igrd2.job.task.TaskStatusListener;

public abstract class Job implements TaskStatusListener {
	protected Task<?,?>                validatorTask;
	protected Task<?,DependentTaskSet> taskBuilderTask;
	protected ThreadPoolExecutor       executorPool;
	protected DependentTaskSet         dependentTaskSet;
	
	Job(ThreadPoolExecutor executorPool, Task<?,DependentTaskSet> taskBuilderTask, Task<?,?> validatorTask, Object initialInputData) {
		if(executorPool==null||taskBuilderTask==null)
			throw new NullPointerException("Thread pool and task builder must be set!");
		
		if(validatorTask!=null) {
		    validatorTask.addTaskStatusListener(this);
		    validatorTask.setInputData(initialInputData);
		} else {
			taskBuilderTask.setInputData(initialInputData);
		}
		
		taskBuilderTask.addTaskStatusListener(this);
		this.validatorTask= validatorTask;
		this.taskBuilderTask = taskBuilderTask;
		this.executorPool = executorPool;
	}
	
	public synchronized void notifyStatusChange(Task<?,?> task, TaskStatus.StatusCode statusCode, TaskStatus.MessageLevel messageLevel, String message) {
		switch(statusCode) {
		case COMPLETED:
			if(dependentTaskSet.allTasksComplete()) {
				//logger.info("Final tasks in conversion completed");
				// Tell the UI that we are done
				return;
			}
			if(task==validatorTask) {
				Object validatorOut = validatorTask.getOutputData();
				if(validatorOut != null) {
					taskBuilderTask.setInputData(validatorOut);
					executorPool.execute(taskBuilderTask);
				} else {
					// Might want to notify the ui that initialization failed
					//logger.error("");
				}
			} else if(task==taskBuilderTask) {
				if((dependentTaskSet=taskBuilderTask.getOutputData())!=null) {
					// set initialization complete and successful (to ui as well)
					executeAllReady();
				} else {
					// Might want to notify ui that initialization failed
					//logger.error("");
				}
			} else {
				executeAllReady();
			}
		case FAILED:
		case STARTED:
		case RUNNING:
		case INTERRUPTED:
		case PAUSED:
		default:
			break;
		}
	}
	
	protected void executeAllReady() {
		if(dependentTaskSet!=null) {
			dependentTaskSet.update();
			while(dependentTaskSet.hasNext()) {
				Task<?,?> task = dependentTaskSet.next();
				Task<?,?> dataProvider = dependentTaskSet.getDataProviderForTask(task);
				task.setInputData(dataProvider.getOutputData());
				executorPool.execute(dependentTaskSet.next());
				// need to handle rejected exception, might consider having a rejected task listener on pool
			}
		}
	}
	
	public void start() {
		if(validatorTask!=null) {
			executorPool.execute(validatorTask);
		} else {
			//logger.warn("No validator task set! Starting initialization with task builder.");
			executorPool.execute(taskBuilderTask);
		}
	}
}
