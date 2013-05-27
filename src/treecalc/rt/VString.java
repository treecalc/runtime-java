package treecalc.rt;

import java.util.ArrayList;
import java.util.List;

public final class VString extends V {
	private final String value;
	public static VString valueOf(String value) {
		return new VString(value);
	}
	private VString(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}

	@Override
	public long longValue() {
		return Long.parseLong(value);
	}
	@Override
	public double doubleValue() {
		try {
			return Double.parseDouble(value.replace(',', '.'));
		} catch (NumberFormatException e) {
			throw new ExceptionCalculation("number expected instead of '" + value + "'", null);
		}
	}
	@Override
	public String stringValue() {
		return value;
	}
	@Override
	public boolean booleanValue() {
		if ("0".equals(value)) return false;
		if ("1".equals(value)) return true;
		throw new ExceptionCalculation("boolean (0 or 1) expected instead of '" + value + "'", null);
	}
	@Override
	public List<V> listValue() {
		ArrayList<V> list = new ArrayList<V>(1);
		list.add(this);
		return list;
	}
	@Override
	public int funcrefValue() {
		throw new ExceptionCalculation("need function reference but got '" + value + "'", null);
	}
	@Override
	public int tabrefValue() {
		throw new ExceptionCalculation("need table reference but got '" + value + "'", null);
	}
	@Override
	public VString  vStringValue()  { 
		return this;
	}
	
	@Override
	public boolean isString()   { return true; }
    @Override
	public boolean isList()     { return true; }
    @Override
	public boolean isFuncref()  { return false; }
    @Override
	public boolean isTabref()   { return false; }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VString))
			return false;
		VString other = (VString) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
