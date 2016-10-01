package com.oli.heasy.data.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * @author Lukasz Olszewski <lsolszewski@gmail.com>
 */
@Service
public class HeasyDataTypesService {
	private HashMap<Class, HeasyDataTypes> primitiveDataTypesMap;
	private HashMap<Class, HeasyDataTypes> complexDataTypesMap;

	public Boolean isComplex(Class clazz) {
		if (getPrimitiveDataTypesMap().containsKey(clazz)) {
			return getPrimitiveDataTypesMap().get(clazz) == HeasyDataTypes.OBJECT;
		}

		return true;
	}

	public HashMap<Class, HeasyDataTypes> getPrimitiveDataTypesMap() {
		if (this.primitiveDataTypesMap == null) {
			this.primitiveDataTypesMap = new HashMap<>();
			this.primitiveDataTypesMap.put(Character.class, HeasyDataTypes.CHARACTER);
			this.primitiveDataTypesMap.put(String.class, HeasyDataTypes.STRING);
			this.primitiveDataTypesMap.put(Boolean.class, HeasyDataTypes.BOOLEAN);
			this.primitiveDataTypesMap.put(Byte.class, HeasyDataTypes.BYTE);
			this.primitiveDataTypesMap.put(Short.class, HeasyDataTypes.SHORT);
			this.primitiveDataTypesMap.put(Integer.class, HeasyDataTypes.INTEGER);
			this.primitiveDataTypesMap.put(Long.class, HeasyDataTypes.LONG);
			this.primitiveDataTypesMap.put(Float.class, HeasyDataTypes.FLOAT);
			this.primitiveDataTypesMap.put(Double.class, HeasyDataTypes.DOUBLE);
			this.primitiveDataTypesMap.put(Object.class, HeasyDataTypes.OBJECT);
		}

		return this.primitiveDataTypesMap;
	}

	public HashMap<Class, HeasyDataTypes> getComplexDataTypesMap() {
		if (this.primitiveDataTypesMap == null) {
			this.primitiveDataTypesMap = new HashMap<>();
			this.primitiveDataTypesMap.put(Map.class, HeasyDataTypes.MAP);
		}

		return this.primitiveDataTypesMap;
	}
}