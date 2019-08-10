package com.jdglazer.shp2igrd2;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PropertiesLoaderTest {
	
	private final String APPLICATION_NAME = "test_app";
	
	PropertiesLoader     propertiesLoader;
	
	@Before
	public void setUp() throws Exception {
		propertiesLoader = new PropertiesLoader(APPLICATION_NAME);
	}

	@Test
	public void testGetsNumericValues() {
		assertEquals("Verify we can get a numerical property" , "3", propertiesLoader.getApplicationProperty("test_app.internal.threading.pool.core.size"));
		assertEquals("Verify we can get a numerical property" , "6", propertiesLoader.getApplicationProperty("test_app.internal.threading.pool.max.size"));
	}

}
