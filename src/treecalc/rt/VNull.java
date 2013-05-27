package treecalc.rt;

import java.util.List;

public final class VNull extends V {
	public static final VNull vnull = new VNull();
	@Override
	public String toString() {
		return "<vnull>";
	}
	@Override
	public String stringValue() {
		throw new ExceptionCalculation("->002< did not get any result. check your inclusion criteria", null);
	}
	@Override
	public boolean booleanValue() {
		throw new ExceptionCalculation("->002< did not get any result. check your inclusion criteria", null);
	}
	@Override
	public List<V> listValue() {
		throw new ExceptionCalculation("->002< did not get any result. check your inclusion criteria", null);
	}
	@Override
	public long longValue() {
		throw new ExceptionCalculation("->002< did not get any result. check your inclusion criteria", null); 
	}
	@Override
	public double doubleValue() {
		throw new ExceptionCalculation("->002< did not get any result. check your inclusion criteria", null); 
	}

	@Override
	public int funcrefValue() { 
		throw new ExceptionCalculation("->002< did not get any result. check your inclusion criteria", null); 
	}
	@Override
	public int tabrefValue() {
		throw new ExceptionCalculation("->002< did not get any result. check your inclusion criteria", null); 
	}
	@Override
	public boolean isInteger()  { return false; }
	@Override
	public boolean isDouble()  { return false; }
	@Override
	public boolean isString()   { return false; }
    @Override
	public boolean isList()     { return false; }
    @Override
	public boolean isFuncref() { return false; }
    @Override
	public boolean isTabref()    { return false; }
    @Override
	public boolean isNull()    { return true; }
}
