package com.jake.common.collect;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import org.checkerframework.checker.nullness.qual.Nullable;

import javax.annotation.CheckForNull;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Adapter class to replace RangeMap<Integer, T>
 * uses TreeRangeMap internally
 *
 * @author Jake
 */
public class IntRangeMap<T> implements RangeMap<Integer, T> {

	protected final RangeMap<Integer, T> wrapped = TreeRangeMap.create();

	@CheckForNull
	@Override
	public T get(Integer key) {
		return wrapped.get(key);
	}

	@CheckForNull
	@Override
	public Map.Entry<Range<Integer>, T> getEntry(Integer key) {
		return wrapped.getEntry(key);
	}

	@Override
	public Range<Integer> span() {
		return wrapped.span();
	}

	@Override
	public void put(Range<Integer> range, T value) {
		wrapped.put(range, value);
	}

	@Override
	public void putAll(RangeMap<Integer, ? extends T> rangeMap) {
		wrapped.putAll(rangeMap);
	}

	@Override
	public void putCoalescing(Range<Integer> range, T value) {
		wrapped.putCoalescing(range, value);
	}

	@Override
	public void clear() {
		wrapped.clear();
	}

	@Override
	public void remove(Range<Integer> range) {
		wrapped.remove(range);
	}

	@Override
	public void merge(Range<Integer> range, @CheckForNull T value, BiFunction<? super T, ? super @Nullable T, ? extends @Nullable T> remappingFunction) {
		wrapped.merge(range, value, remappingFunction);
	}

	@Override
	public Map<Range<Integer>, T> asMapOfRanges() {
		return wrapped.asMapOfRanges();
	}

	@Override
	public Map<Range<Integer>, T> asDescendingMapOfRanges() {
		return wrapped.asDescendingMapOfRanges();
	}

	@Override
	public RangeMap<Integer, T> subRangeMap(Range<Integer> range) {
		return wrapped.subRangeMap(range);
	}
}
