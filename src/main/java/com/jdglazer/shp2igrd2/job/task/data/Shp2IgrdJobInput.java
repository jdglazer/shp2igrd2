package com.jdglazer.shp2igrd2.job.task.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// TO DO: should implement/extend ui form class
public class Shp2IgrdJobInput {
	public File igrdOutFile;
	public double latLonInterval;
	public List<ShapeSetPath> shapeSetPaths = new ArrayList<>();
	
	public Shp2IgrdJobInput(File igrdOutFile, double latLonInterval, ShapeSetPath...shapeSetPaths) {
		this.igrdOutFile = igrdOutFile;
		this.latLonInterval = latLonInterval;
		for(ShapeSetPath shapeSetPath : shapeSetPaths) {
			if(shapeSetPath != null)
			    this.shapeSetPaths.add(shapeSetPath);
		}
	}
	
	public class ShapeSetPath {
		public File shpFile;
		public File shxFile;
		public File dbfFile;
	}
}
