package org.barcelonamedia.myrrixexample.rescorer;

import net.myrrix.common.collection.FastByIDMap;

import org.apache.mahout.cf.taste.recommender.Rescorer;
import org.apache.mahout.common.LongPair;

public class LongPairMultiplyRescorer implements Rescorer<LongPair> {

	FastByIDMap<Double> scores;
	
	public LongPairMultiplyRescorer(FastByIDMap<Double> scores) {
		super();
		this.scores = scores;
	}

	public boolean isFiltered(LongPair itemIds) {
		return !scores.containsKey(itemIds.getFirst());
	}

	/**
	 * rescores items by score map, fails if no score in map (check with isFiltered() first)
	 */
	public double rescore(LongPair itemIds, double orgScore) {
		return orgScore * scores.get(itemIds.getFirst());
	}

}
