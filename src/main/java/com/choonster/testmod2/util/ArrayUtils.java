package com.choonster.testmod2.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Array utility methods.
 */
public class ArrayUtils {

	/**
	 * Remove the specified elements from the array in-place, replacing them with {@code null}.
	 * <p>
	 * {@link Collection#contains(Object)} is used to determine if an element should be removed.
	 *
	 * @param <T>      The element type
	 * @param array    The array
	 * @param toRemove The elements to remove
	 * @return A map of the removed elements and their indices
	 */
	public static <T> Map<T, Integer> removeAll(T[] array, Collection<T> toRemove) {
		Map<T, Integer> removed = new HashMap<>();

		for (int i = 0; i < array.length; i++) {
			T element = array[i];
			if (toRemove.contains(element)) {
				array[i] = null;
				removed.put(element, i);
			}
		}

		return removed;
	}
}
