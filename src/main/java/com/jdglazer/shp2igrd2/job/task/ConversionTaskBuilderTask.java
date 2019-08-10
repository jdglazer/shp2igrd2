package com.jdglazer.shp2igrd2.job.task;

import com.jdglazer.shp2igrd2.job.task.data.Shp2IgrdJobInput;

public class ConversionTaskBuilderTask extends Task<Shp2IgrdJobInput,DependentTaskSet> {

	@Override
	public void run() {
		preRun();
		
		postRun();
	}
}
