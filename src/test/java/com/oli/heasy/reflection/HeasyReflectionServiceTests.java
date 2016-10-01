package com.oli.heasy.reflection;

import com.oli.heasy.data.types.HeasyDataTypesService;
import java.util.ArrayList;
import java.util.HashMap;
import junit.framework.TestCase;

/**
 * @author Lukasz Olszewski <lsolszewski@gmail.com>
 */
public class HeasyReflectionServiceTests extends TestCase {
	private final HeasyDataTypesService service;

	public HeasyReflectionServiceTests() {
		this.service = new HeasyDataTypesService();
	}

	public void testShouldProperlyRecognizeObjectTypeComplexity() {
		Object[] primitives = new Object[] { 'a', "test", 1, 10000000, Long.valueOf(1), true };
		for (Object primitive : primitives) {
			Boolean isComplexed = service.isComplex(primitive.getClass());
			assertFalse(isComplexed);
		}

		Object[] complexed = new Object[] { new HashMap<>(), new ArrayList<>() };
		for (Object complex : complexed) {
			Boolean isComplexed = service.isComplex(complex.getClass());
			assertTrue(isComplexed);
		}
	}
}
