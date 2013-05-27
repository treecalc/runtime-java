package treecalc.rt;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public final class VDouble extends V {
	public static final VDouble vdbl0 = new VDouble(0.0d);
	public static final VDouble vdbl1 = new VDouble(1.0d);
	private final double value;
	private String stringvalue;
    private static DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    private static DecimalFormat decformat;
	private int hashcode;
    static {
        symbols.setDecimalSeparator('.');
        decformat = new DecimalFormat("0.0", symbols);
        decformat.setMinimumFractionDigits(0);
//        decformat.setMaximumFractionDigits(34);
        decformat.setMaximumFractionDigits(12);
    }

	private VDouble(double value) {
		this.value = value;
	}
	
	public static VDouble valueOf(double value) {
		return value==0.0 ? vdbl0 : new VDouble(value);
	}
	public static VDouble valueOf(boolean b) {
		return b ? vdbl1 : vdbl0;
	}
	public static VDouble valueOf(long l) {
		return new VDouble((double) l);
	}
	@Override
	public String toString() {
		return stringvalue!=null ? stringvalue : convertToString();
	}
	@Override
	public String stringValue() {
		return stringvalue!=null ? stringvalue : convertToString();
	}
	@Override
	public boolean booleanValue() {
		if (value==0) return false;
		if (value==1) return true;
		throw new ExceptionCalculation("boolean (0 or 1) expected instead of " + toString(), null);
	}
	@Override
	public List<V> listValue() {
		ArrayList<V> list = new ArrayList<V>(1);
		list.add(this);
		return list;
	}
	@Override
	public long longValue() {
		if ((double)((long) value)==value) {
			return (long) value;
		} else {
			throw new ExceptionCalculation("integer needed, but got " + stringValue(), null);
		}
	}
	@Override
	public double doubleValue() {
		return value;
	}
	@Override
	public VDouble vDoublValue() {
		return this;
	}

	@Override
	public int funcrefValue() { 
		throw new ExceptionCalculation("need function reference but got " + stringValue(), null);
	}
	@Override
	public int tabrefValue() {
		throw new ExceptionCalculation("need table reference but got "  + stringValue(), null);
	}
	@Override
	public boolean isInteger()  { return (double)((long) value)==value; }
	@Override
	public boolean isDouble()  { return true; }
	@Override
	public boolean isString()   { return true; }
    @Override
	public boolean isList()     { return true; }
    @Override
	public boolean isFuncref() { return false; }
    @Override
	public boolean isTabref()    { return false; }
    
	private final String convertToString()
	{
		if (value==0d) {
			stringvalue = "0";
		} else {
			if (Math.abs(value)>1e-16 && Math.abs(value)<1e+15) {
	            stringvalue = decformat.format(value);
			} else {
	            stringvalue = Double.toString(value);
			}
		}
		return stringvalue;
	}
	@Override
	public int hashCode() {
		if (hashcode!=0) {
			return hashcode;
		}
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		hashcode = result;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VDouble))
			return false;
		VDouble other = (VDouble) obj;
		if (value!=other.value) {
			return false;
		}
		return true;
	}
	
}
