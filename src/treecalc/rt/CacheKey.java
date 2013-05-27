package treecalc.rt;

import java.util.Arrays;

public final class CacheKey {
	private final int hash;
	protected final int id;
	private final V[] args;
	private final long[] counters;

	public CacheKey(int id, V[] args, long[] counters) {
		this.id = id;
		this.args = args;
		this.counters = counters;
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(args);
		result = prime * result + Arrays.hashCode(counters);
		result = prime * result + id;
		this.hash = result;
	}
	@Override
	public int hashCode() {
		return hash;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CacheKey)) {
			return false;
		}
		CacheKey other = (CacheKey) obj;
		if (id != other.id) {
			return false;
		}
		if (!Arrays.equals(args, other.args)) {
			return false;
		}
		if (!Arrays.equals(counters, other.counters)) {
			return false;
		}
		return true;
	}
}

