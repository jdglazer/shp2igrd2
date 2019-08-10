package com.jdglazer.shp2igrd2.job.task;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class TaskStatus {
	public enum StatusCode {
		STARTED,
		PAUSED,
		INTERRUPTED,
		RUNNING,
		FAILED,
		COMPLETED
	}
	
	public enum MessageLevel {
		UI,
		INTERNAL
	}
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy HH:mm:ss Z");
	
	public String toString(StatusCode statusCode, MessageLevel messageLevel, String message, long timestamp) {
		Date date = new Date(timestamp);
		return sdf.format(date)+" ["+messageLevel+"] "+statusCode+" - "+message;
	}
}
