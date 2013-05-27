package treecalc.rt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class VList extends V {
	protected final List<V> value;
	public static final VList EMPTYLIST = new VList(new ArrayList<V>(0));

	private VList(int size) {
		value = new ArrayList<V>(size);
	}
	private VList(List<V> value) {
		this.value = value;
	}

	public static VList getInstanceMaxSize(int size) {
		return new VList(size);
	}
	public static VList valueOf(List<V> elements) {
		int len = elements.size();
		if (len==0) {
			return EMPTYLIST;
		} else {
			return new VList(elements);
		}
	}
	public static VList valueOf(V ... elements) {
		int len = elements.length;
		if (len==0) {
			return EMPTYLIST;
		} else {
			return new VList(Arrays.asList(elements));
		}
	}
	@Override
	public String toString() {
		return value.toString();
	}
	@Override
	public long longValue() {
		return value.get(0).longValue();
	}
	@Override
	public double doubleValue() {
		return value.get(0).doubleValue();
	}
	@Override
	public String stringValue() {
		return value.get(0).stringValue();
	}
	@Override
	public boolean booleanValue() {
		return value.get(0).booleanValue();
	}
	@Override
	public List<V> listValue() {
		return value;
	}
	@Override
	public int funcrefValue() {
		return value.get(0).funcrefValue();
	}
	@Override
	public int tabrefValue() {
		return value.get(0).tabrefValue();
	}
	@Override
	public VList vListValue() {
		return this;
	}
    @Override
	public boolean isDouble()  { return value.size()>0 && value.get(0).isDouble(); }
    @Override
	public boolean isString()   { return value.size()>0 && value.get(0).isString(); }
    @Override
	public boolean isList()     { return true; }
    @Override
	public boolean isFuncref() { return value.size()>0 && value.get(0).isFuncref(); }
    @Override
	public boolean isTabref()    { return value.size()>0 && value.get(0).isTabref(); }
    @Override
	public boolean isBool()     { return value.size()>0 && value.get(0).isBool(); }
    @Override
	public boolean isInteger()  { return value.size()>0 && value.get(0).isInteger(); }
}
