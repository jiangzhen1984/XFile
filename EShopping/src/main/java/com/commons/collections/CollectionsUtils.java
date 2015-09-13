package com.commons.collections;

import java.util.Collection;
import java.util.Iterator;

public class CollectionsUtils {
	
	public static <T> void filter(final Iterable<T> collection, Collection<? super T> filtered, Criteria<? super T> eri) {
		if (collection != null && eri != null) {
			for(final Iterator<T> it = collection.iterator(); it.hasNext(); ) {
				T obj = it.next();
				if (eri.evaluate(obj)) {
					filtered.add(obj);
				}
			}
		}
	}
	
	
	public static <T> void filter(final Iterable<T> collection, Collection<? super T> filtered, Criteria<? super T>[] eriArr) {
		if (collection != null && eriArr != null && eriArr.length > 0) {
			for(final Iterator<T> it = collection.iterator(); it.hasNext(); ) {
				T obj = it.next();
				for (Criteria<? super T> eri : eriArr) {
					if (eri.evaluate(obj)) {
						filtered.add(obj);
						break;
					}
				}
			}
		}
	}

}
