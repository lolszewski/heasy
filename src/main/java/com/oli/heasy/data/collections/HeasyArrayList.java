package com.oli.heasy.data.collections;

import java.util.ArrayList;

/**
 * @author Lukasz Olszewski <lsolszewski@gmail.com>
 */
public class HeasyArrayList<T> extends ArrayList<T> {

	public HeasyArrayList<T> where() {
		HeasyArrayList<T> array = new HeasyArrayList<>();

		return array;
	}
}
