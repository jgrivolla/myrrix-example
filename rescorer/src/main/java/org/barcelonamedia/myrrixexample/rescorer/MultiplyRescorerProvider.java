/**
 * 
 */
package org.barcelonamedia.myrrixexample.rescorer;

import net.myrrix.common.MyrrixRecommender;
import net.myrrix.common.collection.FastByIDMap;
import net.myrrix.online.RescorerProvider;
import net.myrrix.online.ServerRecommender;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.Rescorer;
import org.apache.mahout.common.LongPair;

/**
 * Simple rescorer that receives (id,score) tuples and multiplies myrrix scores with those
 * Originally written for Myrrix <= 0.10 and adapted to work with 0.11
 * @author jens.grivolla
 *
 */
public class MultiplyRescorerProvider implements RescorerProvider {

	/**
	 * Logger for this class and subclasses.
	 */
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * @param args comma separated list of id-score pairs (each in id:score format) using string ids
	 * @return map of long id to score, will fail terribly if the format is invalid
	 */
	private FastByIDMap<Double> argsToMap(String[] args) {
		FastByIDMap<Double> scoreMap = new FastByIDMap<Double>();
		for (String arg: args) {
			for (String score: arg.split(",")) {
				String[] idVal = score.split(":");
				if (idVal.length == 2) {
					scoreMap.put(Long.parseLong(idVal[0]), Double.parseDouble(idVal[1]));
				}
			}
		}
		return scoreMap;
	}
	
	/**
	 * @param userIDs user(s) for which recommendations are being made, which may be needed in the rescoring logic.
	 * @param args arguments, if any, that should be used when making the {@link IDRescorer}. This is additional
	 *  information from the request that may be necessary to its logic, like current location. What it means
	 *  is up to the implementation.
	 * @return {@link IDRescorer} to use with {@link ServerRecommender#recommend(long, int, IDRescorer)}
	 *  or {@code null} if none should be used. The resulting {@link IDRescorer} will be passed each candidate
	 *  item ID to {@link IDRescorer#isFiltered(long)}, and each non-filtered candidate with its original score
	 *  to {@link IDRescorer#rescore(long, double)}
	 */
	public IDRescorer getRecommendRescorer(long[] userIDs, String... args) {
		if (args.length == 0) {
			return null;
		}
		return new MultiplyRescorer(argsToMap(args));
	}

	/**
	 * @param itemIDs items that the anonymous user is associated to
	 * @param args arguments, if any, that should be used when making the {@link IDRescorer}. This is additional
	 *  information from the request that may be necessary to its logic, like current location. What it means
	 *  is up to the implementation.
	 * @return {@link IDRescorer} to use with {@link ServerRecommender#recommendToAnonymous(long[], int, IDRescorer)}
	 *  or {@code null} if none should be used. The resulting {@link IDRescorer} will be passed each candidate
	 *  item ID to {@link IDRescorer#isFiltered(long)}, and each non-filtered candidate with its original score
	 *  to {@link IDRescorer#rescore(long, double)}
	 */
	public IDRescorer getRecommendToAnonymousRescorer(long[] itemIDs, String... args){
		return getRecommendRescorer(itemIDs, args);
	}


	/**
	 * @param args arguments, if any, that should be used when making the {@link IDRescorer}. This is additional
	 *  information from the request that may be necessary to its logic, like current location. What it means
	 *  is up to the implementation.
	 * @return {@link Rescorer} to use with {@link ServerRecommender#mostSimilarItems(long[], int, Rescorer)}
	 *  or {@code null} if none should be used. The resulting {@code Rescorer&lt;LongPair&gt;} will be passed
	 *  each candidate item ID pair (IDs of the two similar items) to {@link Rescorer#isFiltered(Object)},
	 *  and each non-filtered candidate item ID pair with its original score to
	 *  {@link Rescorer#rescore(Object, double)}
	 */
	public Rescorer<LongPair> getMostSimilarItemsRescorer(String... args) {
		if (args.length == 0) {
			return null;
		}
		return new LongPairMultiplyRescorer(argsToMap(args));
	}

	@Override
	public IDRescorer getMostPopularItemsRescorer(MyrrixRecommender arg0,
			String... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rescorer<LongPair> getMostSimilarItemsRescorer(MyrrixRecommender arg0,
			String... arg1) {
		return getMostSimilarItemsRescorer(arg1);
	}

	@Override
	public IDRescorer getRecommendRescorer(long[] arg0, MyrrixRecommender arg1,
			String... arg2) {
		return getRecommendRescorer(arg0, arg2);
	}

	@Override
	public IDRescorer getRecommendToAnonymousRescorer(long[] arg0,
			MyrrixRecommender arg1, String... arg2) {
		return getRecommendToAnonymousRescorer(arg0, arg2);
	}

}
