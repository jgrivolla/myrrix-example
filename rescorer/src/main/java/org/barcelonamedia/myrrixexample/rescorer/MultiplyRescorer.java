package org.barcelonamedia.myrrixexample.rescorer;

import net.myrrix.common.collection.FastByIDMap;

import org.apache.mahout.cf.taste.recommender.IDRescorer;

public class MultiplyRescorer implements IDRescorer {

	FastByIDMap<Double> scores;
	
	public boolean isFiltered(long itemId) {
		return !scores.containsKey(itemId);
	}

	/**
	 * rescores items by score map, fails if no score in map (check with isFiltered() first)
	 */
	public double rescore(long itemId, double orgScore) {
		return orgScore * scores.get(itemId);
	}

	public MultiplyRescorer(FastByIDMap<Double> scores) {
		super();
		this.scores = scores;
	}

}
