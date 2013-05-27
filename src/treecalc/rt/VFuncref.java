package treecalc.rt;

import java.util.ArrayList;
import java.util.List;

public final class VFuncref extends V {
	private final int    funcid;
	public static VFuncref valueOf(int funcname) {
		return new VFuncref(funcname);
	}
	private VFuncref(int funcid) {
		this.funcid = funcid;
	}
	@Override
	public String toString() {
		return "FUCREF:" + funcid;
	}
	@Override
	public long longValue() {
		throw new NumberFormatException(this.toString() + " can not be converted to long");
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
	public List<V> listValue() {
		ArrayList<V> list = new ArrayList<V>(1);
		list.add(this);
		return list;
	}
	@Override
	public int funcrefValue() {
		return funcid;
	}
	@Override
	public int tabrefValue() {
		throw new NumberFormatException(this.toString() + " can not be converted to table reference");
	}
	
	@Override
	public VFuncref vFuncrefValue() {
		return this;
	}
    @Override
	public boolean isInteger()  { return false; }
    @Override
	public boolean isDouble()      { return false; }
    @Override
	public boolean isString()   { return false; }
    @Override
	public boolean isBool()     { return false; }
    @Override
	public boolean isList()     { return true; }
    @Override
	public boolean isFuncref() { return true; }
    @Override
	public boolean isTabref()    { return false; }
}
