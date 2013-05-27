package treecalc.rt;

import java.util.ArrayList;
import java.util.List;

/**
 * can pass around table references -> better than in other products
 * @author Stefan
 *
 */
public final class VTabref extends V {
	private final int tabid;
	public static VTabref valueOf(int tabid) {
		return new VTabref(tabid); 
	}
	private VTabref(int tabid) {
		this.tabid = tabid;
	}
	@Override
	public String toString() {
		return "TABREF:" + tabid;
	}
	@Override
	public long longValue() {
		throw new NumberFormatException(this.toString() + " can not be converted to double");
	}
	@Override
	public double doubleValue() {
		throw new NumberFormatException(this.toString() + " can not be converted to double");
	}
	@Override
	public String stringValue() {
		throw new NumberFormatException(this.toString() + " can not be converted to string");
	}
	@Override
	public boolean booleanValue() {
		throw new NumberFormatException(this.toString() + " can not be converted to boolean");
	}
	@Override
	public List<V> listValue() {
		ArrayList<V> list = new ArrayList<V>(1);
		list.add(this);
		return list;
	}
	@Override
	public int funcrefValue() {
		throw new NumberFormatException(this.toString() + " can not be converted to function reference");
	}
	@Override
	public int tabrefValue() {
		return tabid;
	}
	@Override
	public VTabref vTabrefValue() {
		return this;
	}
    @Override
	public boolean isDouble()  { return false; }
    @Override
	public boolean isString()   { return false; }
    @Override
	public boolean isList()     { return true; }
    @Override
	public boolean isFuncref() { return false; }
    @Override
	public boolean isTabref()    { return true; }
    @Override
	public boolean isBool()     { return false; }
    @Override
	public boolean isInteger()  { return false; }
}
