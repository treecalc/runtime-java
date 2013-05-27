package treecalc.rt;

import java.math.BigDecimal;
import java.text.CharacterIterator;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static treecalc.rt.B.convertToDate;
import static treecalc.rt.B.formatDate;

public abstract class V  {
	private final static char CHAR_EMPTY = Character.MIN_VALUE;

	/* instantiation methods */
	public static VDouble  getInstance(boolean value)    { return VDouble.valueOf(value); };
	public static VDouble  getInstance(int value)        { return VDouble.valueOf(value); };
	public static VDouble  getInstance(long value)       { return VDouble.valueOf(value); };
	public static VDouble  getInstance(float value)      { return VDouble.valueOf(value); };
	public static VDouble  getInstance(double value)     { return VDouble.valueOf(value); };
	public static VDouble  getInstance(BigDecimal value) { return VDouble.valueOf(value.doubleValue()); };
	public static VString  getInstance(String value)     { return VString.valueOf(value); };
	public static VList    getInstance(List<V> elements) { return VList.valueOf(elements); };
	public static VList    getInstance(V[] elements)     { return VList.valueOf(elements); };
	public static VFuncref getInstanceFuncref(int funcid)       { return VFuncref.valueOf(funcid); };
	public static VTabref  getInstanceTabref(int tabid)         { return VTabref.valueOf(tabid); };
	
	/* get native data types / objects */
	public abstract String stringValue();
	public abstract double doubleValue();
	public abstract boolean booleanValue();
	public abstract long    longValue();
	public abstract List<V> listValue();
	public abstract int     funcrefValue();
	public abstract int     tabrefValue();

	/* for conversion in Constant world -> override conversions in subclasses for performance */
	public VDouble  vDoublValue()   { return getInstance(this.doubleValue()); };
	public VString  vStringValue()  { return getInstance(this.stringValue()); };
	public VList    vListValue()    { return getInstance(this.listValue()); };
	public VFuncref vFuncrefValue() { return getInstanceFuncref(this.funcrefValue()); };
	public VTabref  vTabrefValue()  { return getInstanceTabref(this.tabrefValue()); };

	public boolean isDouble()  { 
		try {
			this.doubleValue();
			return true;
		} catch (java.lang.ArithmeticException e) {
			return false;
		} catch (java.lang.NumberFormatException e) {
			return false;
		} catch (ExceptionCalculation e) {
			return false;
		}
	}
	public boolean isString()   { 
		try {
			this.stringValue();
			return true;
		} catch (java.lang.ArithmeticException e) {
			return false;
		} catch (java.lang.NumberFormatException e) {
			return false;
		} catch (ExceptionCalculation e) {
			return false;
		}
	}
	public boolean isList()     { 
		try {
			this.listValue();
			return true;
		} catch (java.lang.ArithmeticException e) {
			return false;
		} catch (java.lang.NumberFormatException e) {
			return false;
		} catch (ExceptionCalculation e) {
			return false;
		}
	}
	public boolean isFuncref() { 
		try {
			this.funcrefValue();
			return true;
		} catch (java.lang.ArithmeticException e) {
			return false;
		} catch (java.lang.NumberFormatException e) {
			return false;
		} catch (ExceptionCalculation e) {
			return false;
		}
	}
	public boolean isTabref()    { 
		try {
			this.tabrefValue();
			return true;
		} catch (java.lang.ArithmeticException e) {
			return false;
		} catch (java.lang.NumberFormatException e) {
			return false;
		}
	}
	public boolean isBool()     { 
		try {
			this.booleanValue();
			return true;
		} catch (java.lang.ArithmeticException e) {
			return false;
		} catch (java.lang.NumberFormatException e) {
			return false;
		} catch (ExceptionCalculation e) {
			return false;
		}
	}
	public boolean isInteger()  {
		try {
			double d = this.doubleValue();
			double dint = Math.rint(d);
			return d==dint || Math.abs(d - dint)<1e-7; 
		} catch (java.lang.ArithmeticException e) {
			return false;
		} catch (java.lang.NumberFormatException e) {
			return false;
		} catch (ExceptionCalculation e) {
			return false;
		}
	}
	public boolean isNull() {
		return false;
	}

	/* ====================== */ 
	/* BEGIN binary operators */
	/* ====================== */
	public V unplus () {
		return getInstance(doubleValue());
	}
	
	public V unminus() {
		return getInstance(-doubleValue());
	}
	/* =================== */ 
	/* END unary operators */
	/* =================== */
	
	/* ====================== */ 
	/* BEGIN binary operators */
	/* ====================== */
	public V or    (final V r) { return getInstance(booleanValue() || r.booleanValue());	}
	public V and   (final V r) { return getInstance(booleanValue() && r.booleanValue());	} 
	public V xor   (final V r) { return getInstance(booleanValue() ^ r.booleanValue());	}
	public V eq     (final V r) {  // ==
		double left = doubleValue();
		double right = r.doubleValue();
		return getInstance(left==right || Math.abs(left-right)<1e-7);
	}
	public V neq    (final V r) {  // <>   
		double left = doubleValue();
		double right = r.doubleValue();
		return getInstance(Math.abs(left-right)>=1e-7);
	}
	public V sml    (final V r) {  // <     compare numerical less
		double left = doubleValue();
		double right = r.doubleValue();
		return getInstance(left<right);
	}
	public V big    (final V r) {  // >     compare numerical greater
		double left = doubleValue();
		double right = r.doubleValue();
		return getInstance(left>right);
	}
	public V smleq  (final V r) {  // <=    compare numerical less equal
		double left = doubleValue();
		double right = r.doubleValue();
		return getInstance(left<=right || Math.abs(left-right)<1e-7);
	}
	public V bigeq  (final V r) {  // >=    compare numerical greater equal
		double left = doubleValue();
		double right = r.doubleValue();
		return getInstance(left>=right || Math.abs(left-right)<1e-7);
	}
	public V seq   (final V r) {  // s=    compare string equal
		String left = stringValue();
		String right = r.stringValue();
		return getInstance(left.equals(right));
	}
	public V sneq  (final V r) {  // s<>   compare string not equal
		String left = stringValue();
		String right = r.stringValue();
		return getInstance(!left.equals(right));
	}
	public V seqi  (final V r) {  // si=   compare string equal caseinsensitive
		String left = stringValue();
		String right = r.stringValue();
		return getInstance(left.equalsIgnoreCase(right));
	}
	public V sneqi (final V r) {  // si<>  compare string not equal caseinsensitive
		String left = stringValue();
		String right = r.stringValue();
		return getInstance(!left.equalsIgnoreCase(right));
	}
	public V sl    (final V r) {  // s<    compare string less
		String left = stringValue();
		String right = r.stringValue();
		return getInstance(left.compareTo(right)<0);
	}
	public V sgeq  (final V r) {  // s>=   compare string greater equal
		String left = stringValue();
		String right = r.stringValue();
		return getInstance(left.compareTo(right)>=0);
	}
	public V sli   (final V r) {  // si<   compare string less caseinsensitive
		String left = stringValue();
		String right = r.stringValue();
		return getInstance(left.compareToIgnoreCase(right)<0);
	}
	public V sgeqi (final V r) {  // si>=  compare string greater equal caseinsensitive
		String left = stringValue();
		String right = r.stringValue();
		return getInstance(left.compareToIgnoreCase(right)>=0);
	}
	public V sg    (final V r) {  // s>    compare string greater 
		String left = stringValue();
		String right = r.stringValue();
		return getInstance(left.compareTo(right)>0);
	}
	public V sleq  (final V r) {  // s<=   compare string less equal
		String left = stringValue();
		String right = r.stringValue();
		return getInstance(left.compareTo(right)<=0);
	}
	public V sgi   (final V r) {  // si>   compare string greater caseinsensitive
		String left = stringValue();
		String right = r.stringValue();
		return getInstance(left.compareToIgnoreCase(right)>0);
	}
	public V sleqi (final V r) {  // si<=  compare string less equal caseinsensitive
		String left = stringValue();
		String right = r.stringValue();
		return getInstance(left.compareToIgnoreCase(right)<=0);
	}
	public V append (final V r) { // &      string append
		String left = stringValue();
		String right = r.stringValue();
		if (right.length()==0) {
			return this;
		} else {
			return getInstance(left + right);
		}
	}
	public V power   (final V r) {
		double left = doubleValue();
		double right = r.doubleValue();
		return getInstance(Math.pow(left, right));
	}
	public V div (final V r) {
		double left = doubleValue();
		double right = r.doubleValue();
		return getInstance(left / right);
	}
	public V mult (final V r) {
		double left = doubleValue();
		double right = r.doubleValue();
		return getInstance(left * right);
	}
	public V divint(final V r) {  
		double left = doubleValue();
		double right = r.doubleValue(); /* bug in other language: just integer value of divisor is used */
		double divisor = right>=0 ? Math.floor(right) : Math.ceil(right);
		double result = left / divisor;
		double ret = result>=0 ? Math.floor(result) : Math.ceil(result); 
		return getInstance(ret);
	}
	public V modint(final V r) {
		double left = doubleValue();
		double right = r.doubleValue();
		double result = left / right;
		double ganzzahl = result>=0 ? Math.floor(result) : Math.ceil(result);
		double ret = left - right * ganzzahl;
		return getInstance(ret);
	}
	public V add    (final V r) {  
		double left = doubleValue();
		double right = r.doubleValue();
		return getInstance(left + right);
	}
	public V sub    (final V r) {  
		double left = doubleValue();
		double right = r.doubleValue();
		return getInstance(left - right);
	}
	/* ==================== */ 
	/* END binary operators */
	/* ==================== */
	
	/* ===================== */ 
	/* BEGIN basic functions */
	/* ===================== */
	public final V bf_not() {
		return getInstance(!this.booleanValue()); 
	}
	public final V bf_format(V formatstr) {
		double d = this.doubleValue();
		String sformat = formatstr.stringValue();
    	DecimalFormat decformat=null;
        char leadingChar = '0';
        boolean negativeParenthesis=false;
        int numLeadingChars=0;
        int offsetTrailingChars=-1;
        boolean leadingPlus=false;
        boolean leadingMinus=false;
        boolean trailingPlus=false;
        boolean trailingMinus=false;
        int preDigitsOffset = -1;
        //int preDigitsLength = 0;
        int preDigitsValue = 0;
        int postDigitsOffset = -1;
        //int postDigitsLength = 0;
        int postDigitsValue = 0;
        char thousandSeparator = CHAR_EMPTY; 
        char decimalSeparator = CHAR_EMPTY;
        
        /* START OF FORMAT STRING PARSING */
        final CharacterIterator i = new StringCharacterIterator(sformat);
        char c=i.first();
        /* 1: <prefix any characters>
         * 2: ( 
         * 3: ~ *          (only one of them) 
         * 4: + - 
         * 5: . , 
         * 6: <digits>
         * 7: . ,          (stronger than . and , as prefix)
         * 8: <digits>
         * 9: + -
         * 10: <postfix any characters>
         */
        // <prefix any characters>
        while (c!=CharacterIterator.DONE && 
                c!='(' && c!='~' && c!='*' && c!='+' && c!='-' && 
                c<'0' && c>'9') {
            numLeadingChars++;
            c = i.next();
        }
        // 2: ( 
        if (c=='(') {
            negativeParenthesis = true;
            c = i.next();
        }
            
        // 3: ~ *          (only on of them) 
        if (c=='~' || c=='*') {
            leadingChar = c; 
            c = i.next();
        }
        
        // 4: + -          (can also be both!)
        if (c=='+') {
            leadingPlus = true;
            c=i.next();
            if (c=='-') {
                leadingMinus = true;
                c=i.next();
            }
        } else if (c=='-') {
            leadingMinus = true;
            c=i.next();
            if (c=='+') {
                leadingPlus=true;
                c=i.next();
            }
        }
        
        // 5: . ,
        if (c=='.' || c==',') { /* could be decimal separator -> depends on what comes next */
            thousandSeparator = c;
            c = i.next();
        }
        
        // 6: <digits>
        if (c>='0' && c<='9') {
            preDigitsOffset = i.getIndex();
            preDigitsValue = c - '0'; 
            c = i.next();
            while (c>='0' && c<='9') {
                //preDigitsLength++;
                preDigitsValue *= 10;
                preDigitsValue += c - '0';
                c = i.next();
            }
        } 
        
        // 7: . ,
        if (c=='.' || c==',') { /* could be decimal separator -> depends on what comes next */
            decimalSeparator = c;
            c = i.next();
        }
        
        // 8: <digits>
        if (c>='0' && c<='9') {
            postDigitsOffset = i.getIndex();
            postDigitsValue = c - '0'; 
            c = i.next();
            while (c>='0' && c<='9') {
                //postDigitsLength++;
                postDigitsValue *= 10;
                postDigitsValue += c - '0';
                c = i.next();
            }
        } 

        if (postDigitsOffset<0 && decimalSeparator==CHAR_EMPTY && thousandSeparator!=CHAR_EMPTY) {
            postDigitsOffset = preDigitsOffset;
            //postDigitsLength = preDigitsLength;
            postDigitsValue  = preDigitsValue;
            
            preDigitsOffset = -1;
            //preDigitsLength = 0;
            preDigitsValue = 0;
            
            decimalSeparator = thousandSeparator;
            thousandSeparator = CHAR_EMPTY;
         }
         
        
        // 9: + -          (both + and - is ok)
        if (c=='+') {
            trailingPlus = true;
            c=i.next();
            if (c=='-') {
                trailingMinus = true;
                c=i.next();
            }
        } else if (c=='-') {
            trailingMinus = true;
            c=i.next();
            if (c=='+') {
                trailingPlus=true;
                c=i.next();
            }
        }
        
        if (c!=CharacterIterator.DONE) {
            offsetTrailingChars = i.getIndex();
        }
        
        /* nothing for negative value specified -> put '-' somewhere */
        if (!leadingMinus && !trailingMinus && !negativeParenthesis) {
            if (trailingPlus)
                trailingMinus = true;
            else
                leadingMinus = true;
        }
        /* END OF FORMAT STRING PARSING */
        
        final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        if (decimalSeparator!=CHAR_EMPTY) {
            symbols.setDecimalSeparator(decimalSeparator);
        } else {
            symbols.setDecimalSeparator('.');
        }
            
        if (thousandSeparator!=CHAR_EMPTY)
            symbols.setGroupingSeparator(thousandSeparator);
        if (leadingChar=='0')
            decformat = new DecimalFormat("0.0", symbols);
        else if (leadingChar=='~')
            decformat = new DecimalFormat("#.0", symbols);
        else if (leadingChar=='*')
            throw new RuntimeException ("'*' for format not supported yet");
        else 
            throw new RuntimeException ("'" + leadingChar + "' for format not supported");

        if (preDigitsOffset>=0) {
            decformat.setMinimumIntegerDigits(preDigitsValue);
        } else {
        	decformat.setMinimumIntegerDigits(0);
        }
        
        if (postDigitsOffset<0 && preDigitsOffset<0) {
            decformat.setMaximumFractionDigits(33);
        }
        else { 
            decformat.setMinimumFractionDigits(postDigitsValue);
            decformat.setMaximumFractionDigits(postDigitsValue);
        }
            
        if (thousandSeparator!=CHAR_EMPTY) {
            decformat.setGroupingUsed(true);
            decformat.setGroupingSize(3);
        }
        
        if (leadingPlus)
            decformat.setPositivePrefix("+");
        if (trailingPlus)
            decformat.setPositiveSuffix("+");

        if (negativeParenthesis) {
            if (leadingMinus)
                decformat.setNegativePrefix("(-");
            else
                decformat.setNegativePrefix("(");
        }
        
        if (negativeParenthesis || trailingMinus) {
            if (negativeParenthesis && trailingMinus)
                decformat.setNegativeSuffix("-)");
            else if (negativeParenthesis)
                decformat.setNegativeSuffix(")");
            else 
                decformat.setNegativeSuffix("-");
        }
        
        if (numLeadingChars<=0 && offsetTrailingChars<0) {
            final String ret = decformat.format(d);
            return V.getInstance(ret);
        }

        final String retnum = decformat.format(d);
        final int lenTrailing = offsetTrailingChars>=0 ? sformat.length() - offsetTrailingChars : 0;
        final StringBuffer sb = new StringBuffer(retnum.length() + numLeadingChars + lenTrailing);
        sb.append(sformat.substring(0, numLeadingChars));
        sb.append(retnum);
        if (lenTrailing>=0) {
        	sb.append(sformat.substring(offsetTrailingChars));
        }
        return getInstance(sb.toString());
    }
    
	public final V bf_instr(V searchstring) {
		String s = this.stringValue();
		String search = searchstring.stringValue();
		return getInstance(s.indexOf(search) + 1);
	}
	public final V bf_left(V n) {
		String s = this.stringValue();
		int slen = s.length();
		int len = (int) n.doubleValue();
		if (len>slen) {
			len = slen;
		}
		return getInstance(s.substring(0, len));
	}
	public final V bf_length() {
		return getInstance(this.stringValue().length()); 
	}
	public final V bf_mid(V start) {
		final int indbeg = ((int) start.doubleValue()) - 1;
		return getInstance(this.stringValue().substring(indbeg));
	}
	public final V bf_mid(V start, V n) {
		final int indbeg = (int) start.doubleValue() - 1;
		final String s = this.stringValue();
		final int slen = s.length();
		int posend = indbeg + (int) n.doubleValue();
		if (posend>slen) {
			posend = slen;
		}
		return getInstance(this.stringValue().substring(indbeg, posend));
	}
	public final V bf_right(V n) {
		final String s = this.stringValue();
		final int slen = s.length();
		int indstart = slen - (int) n.doubleValue();
		if (indstart<0) {
			indstart = 0;
		}
		return getInstance(s.substring(indstart));
	}
	public final V bf_strcmp(V otherstring) {
		return getInstance(this.stringValue().compareTo(otherstring.stringValue())); 
	}
	public final V bf_stricmp(V otherstring) {
		return getInstance(this.stringValue().compareToIgnoreCase(otherstring.stringValue())); 
	}
	public final V bf_subst(V searchstr, V replacestr) {
		String s = this.stringValue();
		String ssearch = searchstr.stringValue();
		int lensearch = ssearch.length();
		int ind = s.indexOf(ssearch);
		if (ind<0) {
			return getInstance(s);
		}
		String sreplace = replacestr.stringValue();
		int lenreplace = sreplace.length();
		StringBuffer sb = new StringBuffer(s.length() - lensearch + lenreplace);
		if (ind>0) {
			sb.append(s.substring(0, ind));
		}
		sb.append(sreplace);
		sb.append(s.substring(ind+lensearch));
		return V.getInstance(sb.toString());
	}
	public final V bf_tolower() {
		return getInstance(this.stringValue().toLowerCase()); 
	}
	public final V bf_toupper() {
		return getInstance(this.stringValue().toUpperCase()); 
	}
	public final V bf_trim() {
		String s = this.stringValue();
		String strimmed = s.trim();
		if (s.length()==strimmed.length()) {
			return this;
		} else {
			return getInstance(strimmed); 
		}
	}
	public final V bf_chr() {
		char cp = (char) this.doubleValue();
		return getInstance(String.valueOf(new char[] { cp })); 
	}
	public final V bf_ansi() {
		return getInstance(this.stringValue()); 
	}
	public final V bf_oem() {
		return getInstance(this.stringValue()); 
	}
	public final V bf_round() {
		double d = this.doubleValue();
		if (d>0) {
			d = Math.floor(d + 0.5); //0000001d);
		} else {
			d = Math.ceil (d - 0.5); //49999999d);
		}
		return getInstance(d); 
	}
	public final V bf_round(V digits) {
		int n = (int) digits.doubleValue();
		double d = this.doubleValue();
		switch(n) {
		case 0: {
			if (d>=0) {
				d = Math.floor(d + 0.5); //0000001d);
			} else {
				d = Math.ceil (d - 0.5); //49999999d);
			}
			return getInstance(d);
		}
		case 1: {
			if (d>=0) {
				d = Math.floor(d*10 + 0.5) / 10; //000001d) / 10;
			} else {
				d = Math.ceil (d*10 - 0.5) / 10; //4999999d) / 10;
			}
			return getInstance(d);
		}
		case 2: {
			if (d>=0) {
				d = Math.floor(d*100 + 0.5) / 100;
			} else {
				d = Math.ceil (d*100 - 0.5) / 100;
			}
		}
		default: {
			final double factor = Math.pow(10, n);
			d *= factor;
			if (d>=0) {
				d = Math.floor(d + 0.5);
			} else {
				d = Math.floor(d - 0.5);
			}
			d /= factor;
			return getInstance(d);
		}
		}
	}
	public final V bf_ceil() {
		return getInstance(Math.ceil(this.doubleValue())); 
	}
	public final V bf_floor() {
		return getInstance(Math.floor(this.doubleValue())); 
	}
	public final V bf_sqrt() {
		return getInstance(Math.sqrt(this.doubleValue())); 
	}
	public final V bf_exp() {
		return getInstance(Math.exp(this.doubleValue())); 
	}
	public final V bf_fmod(V divisor) {
		return getInstance(Math.IEEEremainder(this.doubleValue(), divisor.doubleValue())); 
	}
	public final V bf_abs() {
		return getInstance(Math.abs(this.doubleValue())); 
	}
	public final V bf_dateday() {
    	final int[] ymd = convertToDate(this);
    	if(ymd==null) {
        	throw new ExceptionCalculation("->007< Date expected instead of " + this, null);
    	}
    	return getInstance(JulianDate.toJulian(ymd));
	}
	public final V bf_day() {
    	final int[] ymd = convertToDate(this);
    	if(ymd==null) {
        	throw new ExceptionCalculation("->007< Date expected instead of " + this, null);
    	}
    	return getInstance(ymd[2]);
	}
	public final V bf_daydate(V format) {
        final int juldate = (int) doubleValue();
        if (!JulianDate.isDate(juldate)) {
        	throw new ExceptionCalculation("->007< Can not be converted to a date: " + this, null);
        }
        final int[] ymd = JulianDate.fromJulian(juldate);
        return formatDate(ymd, null, format);
	}
	public final V bf_days() {
        final int[] ymd = B.convertToDate(this);
        if (ymd==null) {
        	throw new ExceptionCalculation("->007< Date expected instead of " + this, null);
        }
        final int year  = ymd[0];
        final int month = ymd[1];
        final int day   = ymd[2];

        /* 360 days a year, 30 days a month */
        int dayCommercial = 
        		28 // 1.1.1800 -> 29 (one will be added for 1st below) 
            + (year - 1800) * 360
            + (month-1) * 30;
        if (day>30) {
            dayCommercial+=30;
        } else {
            dayCommercial+=day;
        }
        return getInstance(dayCommercial);
	}
	public final V bf_month() {
    	final int[] ymd = convertToDate(this);
    	if(ymd==null) {
        	throw new ExceptionCalculation("->007< Date expected instead of " + this, null);
    	}
    	return getInstance(ymd[1]);
	}
	public final V bf_months() {
        final int[] ymd = convertToDate(this);
        if (ymd==null) {
        	throw new ExceptionCalculation("->007< Date expected instead of " + this, null);
        }
        final int year  = ymd[0];
        final int month = ymd[1];
        final int day   = ymd[2];

        /* 360 days a year, 30 days a month */
        int dayCommercial = 
        		(year - 1800) * 360
        		+ (month-1) * 30;
        if (day>30) {
            dayCommercial+=29;
        } else {
            dayCommercial+=day-1;
        }
        final int months   = dayCommercial / 30;
        final int daysRest = dayCommercial % 30;
        final double ret = months + daysRest / 30.0d;
        return getInstance(ret);
	}
	public final V bf_year() {
    	final int[] ymd = convertToDate(this);
    	if(ymd==null) {
        	throw new ExceptionCalculation("->007< Date expected instead of " + this, null);
    	}
    	return getInstance(ymd[0]);
	}
	public final V bf_years() {
        final int[] ymd = convertToDate(this);
        if (ymd==null) {
        	throw new ExceptionCalculation("->007< Date expected instead of " + this, null);
        }
        final int year  = ymd[0];
        final int month = ymd[1];
        final int day   = ymd[2];

        /* 360 days a year, 30 days a month */
        int dayCommercial = 
        		(year - 1800) * 360
            + (month-1) * 30;
        if (day>30) {
            dayCommercial+=29;
        } else {
            dayCommercial+=day-1;
        }
        dayCommercial+=29;
        final int years = dayCommercial / 360;
        final int daysRest = dayCommercial % 360;
        final double ret = years + daysRest / 360.0d;
        return getInstance(ret);
	}
	public final V bf_v_concat(final V list) {
		 final List<V> list1 = this.listValue();
		 final List<V> list2 = list.listValue();
		 final List<V> ret = new ArrayList<V>(list1.size() + list2.size());
		 ret.addAll(list1);
		 ret.addAll(list2);
		 return getInstance(ret);
	}
    /** Reconstruct Vector from Skalar
     * @param x: String as delivered by v_stringx
     * @return VPMS-Scalar or VPMS-Vector
       z.B. v_stringx(v_(1;v_(21;22);3;"()/!")) -> "(1!(21!22)!3!/(/)///!)
         special chars: 
          !: element delimiter
          (: start of vector (can be ragged)
          ): end of vector
          /: coding of special char
     */
	public final V bf_v_construct() {
		return B.v_construct(new StringCharacterIterator(this.stringValue()));
	}

	public final V bf_v_delete(final V position) {
		final List<V> list = this.listValue();
		int len = list.size();
		int ind = (int) position.doubleValue();
		if (ind<0 || ind>=len) {
        	throw new RuntimeException("invalid index " + ind);
		}
		final ArrayList<V> ret = new ArrayList<V>(list);
		ret.remove(ind);
		return getInstance(ret); 
	}
	public final V bf_v_element(final V ... indices) {
		V current=this;
		for (V index : indices) {
			final List<V> list = current.listValue();
			int ind = (int) index.doubleValue();
			if (ind>=0 && ind<list.size()) {
				current = list.get(ind);
			} else {
				throw new RuntimeException("invalid index " + ind);
			}
		}
		return current;
	}
	public final V bf_v_elements(final V indstart, final V n) {
		int startind = (int) indstart.doubleValue();
		int endpos  = (int) n.doubleValue() + startind; 
		final List<V> list = this.listValue();
		int len = list.size();
		if (startind<0 || startind>=len) {
			throw new RuntimeException("invalid index " + startind);
		}
		if (endpos<=startind) {
			return VList.EMPTYLIST;
		}
		if (endpos>len) {
			endpos = len;
		}
		return getInstance(list.subList(startind, endpos)); 
	}
	public final V bf_v_first() {
		return this.listValue().get(0); 
	}
	public final V bf_v_front() {
		final List<V> list = this.listValue();
		final int len = list.size();
		if (len==0) {
			throw new RuntimeException("list is already empty, cannot take 'front'");
		}
		final List<V> sublist = new ArrayList<V>(list);
		sublist.remove(len-1);
		return getInstance(sublist);
	}
	public final V bf_v_insert(final V newelement, final V position) {
		final List<V> list = this.listValue();
		int len = list.size();
		int ind = (int) position.doubleValue();
		if (ind<0 || ind>len) {
			throw new RuntimeException("can not insert at position " + ind + " in vector of length " + len);
		}
		final List<V> newlist = new ArrayList<V>(list.size()+1);
		newlist.addAll(list);
		newlist.add(ind, newelement);
		return getInstance(newlist); 
	}
	public final V bf_v_last() {
		final List<V> list = this.listValue();
		final int len = list.size();
		if (len==0) {
			throw new RuntimeException("can not take last element from empty vector");
		}
		return list.get(len-1);
	}
	public final V bf_v_length() {
		if (this instanceof VList) {
			final VList vlist = (VList) this;
			int len = vlist.value.size();
			return getInstance(len==0 ? -1 : len);
		}
		return getInstance(0);
	}
	public final V bf_v_max() {
		final List<V> list = this.listValue();
		final int len = list.size();
		if (len==0) {
			return this;
		} 
		if (len==1) {
			return list.get(0);
		}
		V winner = list.get(0);
		double dmax;
		try {
			dmax = winner.doubleValue();
		} catch (NumberFormatException e) {
			dmax = Double.MIN_VALUE;
		}
		for(int i=1; i<len; i++) {
			final V candidate = list.get(i);
			try {
				final double dcand = candidate.doubleValue();
				if (dcand>dmax) {
					dmax = dcand;
					winner = candidate;
				}
			} catch (NumberFormatException e) {
			}
		} 
		return winner;
	}
	public final V bf_v_min() {
		final List<V> list = this.listValue();
		final int len = list.size();
		if (len==0) {
			return this;
		} 
		if (len==1) {
			return list.get(0);
		}
		V winner = list.get(0);
		double dmin;
		try {
			dmin = winner.doubleValue();
		} catch (NumberFormatException e) {
			dmin = Double.MAX_VALUE;
		}
		for(int i=1; i<len; i++) {
			final V candidate = list.get(i);
			try {
				double dcand = candidate.doubleValue();
				if (dcand<dmin) {
					dmin = dcand;
					winner = candidate;
				}
			} catch (NumberFormatException e) {
			}
		} 
		return winner;
	}
	public final V bf_v_numsort() {
        final ArrayList<V> list = new ArrayList<V>(this.listValue());
        Collections.sort(list, CompV_num.getInstance());
        return getInstance(list);
		
	}
	public final V bf_v_replace(V element, V position) {
		final List<V> list = this.listValue();
		int len = list.size();
		int ind = (int) position.doubleValue();
		if (ind<0 || ind>=len) {
			throw new RuntimeException("invalid index " + ind + " for list of length " + len);
		}
		list.set(ind,element);
		return getInstance(list);
	}
	public final V bf_v_rest() {
		final List<V> list = this.listValue();
		final int len = list.size();
		if (len==0) {
			throw new RuntimeException("list is already empty, cannot take 'rest'");
		}
		final List<V> sublist = new ArrayList<V>(list);
		sublist.remove(0);
		return getInstance(sublist);
	}
	public final V bf_v_sort() {
        final ArrayList<V> list = new ArrayList<V>(this.listValue());
        Collections.sort(list, CompV_str.getInstance());
        return getInstance(list);
	}
	public final V bf_v_string(V delimiter) {
		final List<V> list = this.listValue();
        String sdelim = delimiter.stringValue();
        StringBuffer s=new StringBuffer();
		int len = list.size();
        for (int i=0; i<len; i++) {
        	if (i>0) {
        		s.append(sdelim);
        	} 
        	V current = list.get(i);
        	s.append(current.stringValue());
        }
        return getInstance(s.toString());
	}
	private final void internal_v_stringx(StringBuffer sb) {
		if (!(this instanceof VList)) {
			String str = this.stringValue();
			int inds=sb.length();
			sb.append(str);
            final CharacterIterator is = new StringCharacterIterator(str);
            for (char c = is.current(); c!=CharacterIterator.DONE; c = is.next(), inds++) {
                if (c=='!' || c=='(' || c==')' || c=='/' || c=='#') {
                    sb.insert(inds, '/');
                    inds++;
                }
            }
            return;
        }
        sb.append('(');
        List<V> list = this.listValue();
        int len = list.size();
        if (len==0) {
        	sb.append('#');
        } else {
        	V first = list.get(0);
        	first.internal_v_stringx(sb);
            for (int i=1; i<len; i++) {
           		sb.append('!');
            	V current = list.get(i);
            	current.internal_v_stringx(sb);
            }
        }
        sb.append(')');
	}
	
	public final V bf_v_stringx() {
        final StringBuffer s = new StringBuffer(64);
        internal_v_stringx(s);
        return getInstance(s.toString());
	}
	public final V bf_v_trans() {
		if (!(this instanceof VList)) {
			return this;
		}
		final List<V> list = this.listValue();
        final int len = list.size();
        if (len<2) {
            return this;
        }
        ArrayList<ArrayList<V>> ret = null;
        for (int i=0; i<len; i++) {
            final V elem = list.get(i);
            if(elem instanceof VList) {
                final List<V> listElem = elem.listValue();
                if (ret==null) {
                    /* create new list. number of elements = number of elements of one original vector element.
                     * each of the entries is a list the size of the original list
                     */
                    final int newlen = listElem.size();
                    ret = new ArrayList<ArrayList<V>>(newlen);
                    for (int j=0; j<newlen; j++) {
                        ret.add(new ArrayList<V>(len));
                    }
                }
                final int lenToAdd = listElem.size();
                if (lenToAdd!=ret.size())
                    throw new Error ("matrix expected");
                
                for (int j=0; j<lenToAdd;j++) {
                    final ArrayList<V> listToAdd = ret.get(j);
                    listToAdd.add(listElem.get(j));
                }
            } else { //any scalar in there -> unchanged result
            	return this;
            }
        }
        ArrayList<V> trueret = new ArrayList<V>(ret.size());
        for (ArrayList<V> onerow : ret) {
        	V row = V.getInstance(onerow);
        	trueret.add(row);
        }
        return getInstance(trueret);
	}
	public final V bf_isbool() {
		return getInstance(this.isBool()); 
	}
	public final V bf_isdate() {
		final int[]	ymd = convertToDate(this);
		return ymd != null ? VDouble.vdbl1 : VDouble.vdbl0; 
	}
	public final V bf_isinteger() {
		return this.isInteger() ? VDouble.vdbl1 : VDouble.vdbl0;
	}
	public final V bf_isnumber() {
		return this.isDouble() ? VDouble.vdbl1 : VDouble.vdbl0;
	}
	public final V bf_istime() {
		String s = this.stringValue();
		// blanks hour (1 or 2 digits) ':' blanks min (1 or 2 digits) anything
		StringCharacterIterator i = new StringCharacterIterator(s);
		while (i.current()==' ' || i.current()=='\t' || i.current()=='\f' || i.current()=='\r' || i.current()=='\r') {
			i.next();
		}
		int hour=0;
		// first digit of hour
		switch(i.current()) {
		case '0': hour=0; break;
		case '1': hour=1; break;
		case '2': hour=2; break;
		case '3': hour=3; break;
		case '4': hour=4; break;
		case '5': hour=5; break;
		case '6': hour=6; break;
		case '7': hour=7; break;
		case '8': hour=8; break;
		case '9': hour=9; break;
		default: return VDouble.vdbl0;
		}
		// optional: second digit of hour
		i.next();
		while (Character.isDigit(i.current())) {
			switch(i.current()) {
			case '0': hour*=10; hour+=0; i.next(); break;
			case '1': hour*=10; hour+=1; i.next(); break;
			case '2': hour*=10; hour+=2; i.next(); break;
			case '3': hour*=10; hour+=3; i.next(); break;
			case '4': hour*=10; hour+=4; i.next(); break;
			case '5': hour*=10; hour+=5; i.next(); break;
			case '6': hour*=10; hour+=6; i.next(); break;
			case '7': hour*=10; hour+=7; i.next(); break;
			case '8': hour*=10; hour+=8; i.next(); break;
			case '9': hour*=10; hour+=9; i.next(); break;
			default: break; //isDigit but not '0'..'9' -> is not time because ':' should follow
			}
		}
		if (hour>23 || i.current()!=':') {
			return VDouble.vdbl0;
		}
		i.next(); //consume ':'
		while(i.current()==' ' || i.current()=='\t' || i.current()=='\f' || i.current()=='\r' || i.current()=='\r') {
			i.next();
		}
		int min=0;
		// first digit of hour
		loop: while(true) {
			switch(i.current()) {
			case '0': min*=10; min+=0; break;
			case '1': min*=10; min+=1; break;
			case '2': min*=10; min+=2; break;
			case '3': min*=10; min+=3; break;
			case '4': min*=10; min+=4; break;
			case '5': min*=10; min+=5; break;
			case '6': min*=10; min+=6; break;
			case '7': min*=10; min+=7; break;
			case '8': min*=10; min+=8; break;
			case '9': min*=10; min+=9; break;
			default: break loop;
			}
			i.next();
		}
		if (min>60) {
			return VDouble.vdbl0;
		}
		/* whatever follows doesn't matter! */
		return VDouble.vdbl1;
	}
	/* =================== */ 
	/* END basic functions */
	/* =================== */
}
