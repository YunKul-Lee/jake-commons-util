package com.jake.common.roulette;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.jake.common.collect.IntRangeMap;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Roulette<T extends RouletteEntry> extends IntRangeMap<T> {

	private int groupSum = -1;  // 이 RangeMap에 들어오는 ProbData의 groupSum값(모두 동일)
	private int probSum = 0;    // 이 RangeMap에 들어오는 ProbData의 prob 합계
	private int offset = 0;
	private final List<T> probDataList = new ArrayList<>();

	public int getGroupSum() {
		return groupSum;
	}

	/**
	 * Don't use this method.
	 * Use append()
	 */
	@Override
	public void put(Range<Integer> range, T value) {
		throw new RuntimeException("Do not call put(). Use append().");
	}

	/**
	 * Don't use this method.
	 * Use append()
	 */
	@Override
	public void putAll(RangeMap<Integer, ? extends T> rangeMap) {
		throw new RuntimeException("Do not call putAll(). Use append().");
	}

	/**
	 * ProbData를 추가한다.
	 *
	 */
	public void append(T probData) {
		Range<Integer> range = Range.closedOpen(this.offset, this.offset + probData.getProb());

		this.track(probData);

		super.put(range, probData);
	}

	/**
	 * ProbData를 추가한다.
	 */
	public void appendAll(List<T> probDataList, boolean doValidate) {
		probDataList.forEach(probData -> {
			this.append(probData);
		});

		if(doValidate) {
			this.validateGroupSum();
		}
	}

	/**
	 * 모든 ProbData의 prob 값의 합과 groupSum의 값이 일치하는지를 검사한다.
	 */
	public void validateGroupSum() {
		if (offset != groupSum) {
			throw new IllegalStateException("Validation error. offset=" + probSum + ", groupSum=" + groupSum + ", probDataList=" + probDataList);
		}
	}

	/**
	 * maxProb 내의 random dice를 굴려서 결정된 값으로,
	 * Roulette 내에 정의된 ProbData를 리턴한다.
	 */
	public T dice() {
		ThreadLocalRandom tlr = ThreadLocalRandom.current();
		int dice = tlr.nextInt(this.groupSum);
		return this.get(dice);
	}

	private void track(T probData) {
		if (this.groupSum >= 0 && this.groupSum != probData.getProbSum()) {
			throw new IllegalArgumentException("MUST have same values. groupSum=" + groupSum + ", value=" + probData);
		}
		this.groupSum = probData.getProbSum();

		// 추가된 value들을 모두 저장
		this.probDataList.add(probData);

		// offset 변경 증가
		this.offset += probData.getProb();
	}

	public static <T extends RouletteEntry> List<T> sortedCopy(List<T> list, Comparator<T> comparator) {
		return list.stream().sorted(comparator).collect(Collectors.toList());
	}

	public static <T extends RouletteEntry> Roulette<T> createRouletteWith(List<T> sortedList, boolean doValidate) {
		Roulette<T> roulette = new Roulette<>();
		sortedList.forEach(e -> {
			roulette.append(e);
		});

		if(doValidate) {
			roulette.validateGroupSum();
		}

		return roulette;
	}

	public static <K, V extends RouletteEntry> Map<K, Roulette<V>> createRouletteMapWith(
			List<V> sortedList,
			Function<V, K> groupKeyFunction,
			boolean doValidate
	) {
		// Map of List => 그룹화
		Map<K, List<V>> listMap = new HashMap<>();
		sortedList.forEach(o -> {
			K groupKey = groupKeyFunction.apply(o);
			List<V> propListInGroup = listMap.get(groupKey);
			if(propListInGroup == null) {
				propListInGroup = new ArrayList<>();
				listMap.put(groupKey, propListInGroup);
			}
			propListInGroup.add(o);
		});

		// group 별로 Roulette를 생성
		Map<K, Roulette<V>> rouletteMap = new HashMap<>();
		listMap.forEach((group, probListInGroup) -> {
			Roulette<V> roulette = new Roulette<>();
			probListInGroup.forEach(e -> roulette.append(e));

			if(doValidate) {
				roulette.validateGroupSum();
			}

			rouletteMap.put(group, roulette);
		});

		return rouletteMap;
	}
}
