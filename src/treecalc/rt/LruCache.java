package treecalc.rt;

import java.util.LinkedHashMap;
import java.util.Map;

public final class LruCache<Key,Value> extends LinkedHashMap<Key, Value> {
	private static final long serialVersionUID = 1L;
	private final int threshold;
	int hit;
	int miss;
	int kill;
	public LruCache(int maxEntries, float loadFactor) {
		super((int) (maxEntries * loadFactor), loadFactor, true);
		threshold = maxEntries - 2;
	}
	@Override
	public Value get(Object key) {
		Value ret = super.get(key);
		if(ret!=null) {
			hit++;
		} else {
			miss++;
		}
		return ret;
	}
	protected boolean removeEldestEntry(Map.Entry<Key, Value> eldest) {
		if (size() >= threshold) {
			kill++;
			return true;
		} else {
			return false;
		}
	}
	//    	if (size() >= threshold) { 
	//  		 Key key = eldest.getKey();
	//  		 if (key instanceof CacheKey) {
	//  			 CacheKey cachekey = (CacheKey) key;
	//  			 int id = cachekey.id;
	//  			 MemoStatistic memostatistic = memostatistics.get(id);
	//  			 if (memostatistic==null) {
	//  				 memostatistic = new MemoStatistic(id);
	//  				 memostatistics.put(id, memostatistic);
	//  			 }
	//  			 memostatistic.incRemoved();
	//  		 }
	//  		 return true;
	//  	 }
	//  	 return false;
	//    }
	@Override
	public String toString() {
//		return "<statistics turned off>";
		return "total:" + (hit+miss) + ", hit:" + hit + ", miss:" + miss + ", kill:" 
				 + kill;
		//  	  StringBuffer s = new StringBuffer(4096);
		//  	  for (Map.Entry<Integer, MemoStatistic> entry : memostatistics.entrySet()) {
		//  		  s.append(entry.getValue().toString());
		//  		  s.append('\n');
		//  	  }
		//  	  return s.toString();
		//  	  return removedIds.toString();
	}
}
