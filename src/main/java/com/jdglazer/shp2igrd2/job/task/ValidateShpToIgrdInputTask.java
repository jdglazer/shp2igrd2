package com.jdglazer.shp2igrd2.job.task;

import com.jdglazer.shp2igrd2.job.task.data.Shp2IgrdJobInput;
import com.jdglazer.shp2igrd2.job.task.data.Shp2IgrdJobInput.ShapeSetPath;

public class ValidateShpToIgrdInputTask extends Task<Shp2IgrdJobInput,Shp2IgrdJobInput> {
	// Logger logger = LoggerFactory.getLogger(ValidateShpToIgrdInputTask.class);
	
	@Override
	public void run() {
		preRun();
		// If we get here, the input passed the validation and we can just pass it back out
		if(inputData!=null) {
			outputData = inputData;
		}
		postRun();
	}

	@Override
	protected boolean validateInputData(Shp2IgrdJobInput inputData) {
		if(inputData == null) {
			//logger.error("Null conversion input data");
			return false;
		}
		
		if (!inputData.igrdOutFile.getParentFile().canWrite()) {
			//logger.error("Found no writable directory for igrd file at "+inputData.igrdOutFile.getParentFile());
			return false;
		}
		
		if(inputData.latLonInterval<=0) {
			//logger.error("Must set a positive latitude-longitude interval value");
			return false;
		}
		
		if(inputData.shapeSetPaths.isEmpty()) {
			//logger.error("Must set at least one shape file set for conversion");
			return false;
		}

		for(ShapeSetPath shapeSetPath : inputData.shapeSetPaths) {
			if(!shapeSetPath.shpFile.canRead()) {
				//logger.error("No readable file found at "+shpSetPath.shpFile);
				return false;
			}
			if(!shapeSetPath.shxFile.canRead()) {
				//logger.error("No readable file found at "+shpSetPath.shxFile);
				return false;
			}
			if(!shapeSetPath.dbfFile.canRead()) {
				//logger.error("No readable file found at "+shpSetPath.dbfFile);
				return false;
			}
		}
		
		return true;
	}

}
