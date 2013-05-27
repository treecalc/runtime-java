package treecalc.rt;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public final class B {
	//TODO private static final String[] WEEKDAY_TEXT_DE = {"Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"};
	private static final String[] WEEKDAY_TEXT_EN = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	private static final Pattern REGEX_YMD = Pattern.compile("^[0-9][0-9][0-9][0-9][-][0-9][0-9]?[-][0-9][0-9]?$");
	private static final Pattern REGEX_DMY = Pattern.compile("^[0-9][0-9]?[.][0-9][0-9]?[.][0-9][0-9][0-9][0-9]$");
	private static final Pattern REGEX_MDY = Pattern.compile("^[0-9][0-9]?[/][0-9][0-9]?[/][0-9][0-9][0-9][0-9]$");

	public static final V bf_false() { 
		return V.getInstance(false); 
	};
	public static final V bf_true() { 
		return V.getInstance(true); 
	};
	
	/**
	 * @param args at least two arguments
	 * @return numerically smallest element, converted to a number (->leading zeros will vanish, vector will get scalar)
	 */
	public static final V bf_min(V ... args) {
		double dmin = args[0].doubleValue();
		int len = args.length;
		for(int i=1; i<len; i++) {
			double dcand = args[i].doubleValue();
			if (dcand<dmin) {
				dmin = dcand;
			}
		} 
		return V.getInstance(dmin);
	}
	/**
	 * @param args at least two arguments
	 * @return numerically biggest element, converted to a number (->leading zeros will vanish, vector will get scalar)
	 */
	public static final V bf_max(V ... args) {
		double dmax = args[0].doubleValue();
		int len = args.length;
		for(int i=1; i<len; i++) {
			double dcand = args[i].doubleValue();
			if (dcand>dmax) {
				dmax = dcand;
			}
		} 
		return V.getInstance(dmax);
	}
	
	public static final V bf_acos(V arg) {
		return V.getInstance(Math.acos(arg.doubleValue()));
	}
	public static final V bf_asin(V arg) { 
		return V.getInstance(Math.asin(arg.doubleValue()));
	}
	public static final V bf_atan(V arg) { 
		return V.getInstance(Math.atan(arg.doubleValue()));
	}
	public static final V bf_atan2(V x, V y) { 
		return V.getInstance(Math.atan2(x.doubleValue(), y.doubleValue()));
	}
	public static final V bf_cos(V arg) { 
		return V.getInstance(Math.cos(arg.doubleValue()));
	}
	public static final V bf_cosh(V arg) { 
		return V.getInstance(Math.cosh(arg.doubleValue()));
	}
	public static final V bf_sin(V arg) { 
		return V.getInstance(Math.sin(arg.doubleValue()));
	}
	public static final V bf_sinh(V arg) { 
		return V.getInstance(Math.sinh(arg.doubleValue()));
	}
	public static final V bf_tan(V arg) { 
		return V.getInstance(Math.tan(arg.doubleValue()));
	}
	public static final V bf_tanh(V arg) { 
		return V.getInstance(Math.tanh(arg.doubleValue()));
	}
	public static final V bf_log(V arg) { 
		return V.getInstance(Math.log(arg.doubleValue()));
	}
	public static final V bf_log10(V arg) { 
		return V.getInstance(Math.log10(arg.doubleValue()));
	}
	public static final V bf_date(V date, V addDays, V addMonth, V addYears) { 
		return bf_date(date, addDays, addMonth, addYears, null); 
	}
	public static final V bf_date(V date, V addDays, V addMonths, V addYears, V format)  { 
        final int[] ymd = convertToDate(date);
        if (ymd==null) {
            return null;
        }
        
        /* add-values can be positive or negative */
        final int iaddYears  = (int) addYears.doubleValue();
        final int iaddMonths = (int) addMonths.doubleValue();
        final int iaddDays   = (int) addDays.doubleValue();

        int year = ymd[0];
        int month = ymd[1];
        final int day = ymd[2];

        /* step1: add years and months */
        year += iaddYears;
        month += iaddMonths;
        if (month>12) {
            final int correctYears = (month-1) / 12;
            year+=correctYears;
            month = ((month-1) % 12) + 1;
        }
        else if (month < 1) {
            final int correctYears = (month-1) / 12 - 1; /* correctYears will be negative for sure */
            year+=correctYears; 
            month -= correctYears*12; /*-- = + -> add Months */ 
        }
        
        /* step2: correct days if intermediate result is not a valid date */
        ymd[0] = year;
        ymd[1] = month;
        ymd[2] = day;
        
        if (!JulianDate.isDate(ymd)) {
            /* changing year/month, we can arrive at a day which is beyond the end of the month 
             * e.g. 31.1.2007 + 1 month -> 31.2.2007
             * do it like VP/MS: add "overshooting" days -> we will be at next month.
             * e.g. 31.2.2007 -> 3.3.2007 
             */
            final int overshoot = day - JulianDate.getDaysOfMonth(year, month);
            ymd[1]++;
            ymd[2] = overshoot;
        }
        
        
        /* step3: add days */
        int serial = JulianDate.toJulian(ymd);
        serial+=iaddDays;
        JulianDate.fromJulian(serial, ymd);
        
        if (!JulianDate.isDate(ymd)) {
            return null;
        }

        return formatDate(ymd, null, format);
    }

    public static final V bf_now(V format) { 
        final GregorianCalendar date = new GregorianCalendar();
        int year = date.get(GregorianCalendar.YEAR);
        int month = date.get(GregorianCalendar.MONTH) + 1;
        int day = date.get(GregorianCalendar.DAY_OF_MONTH);
        int hour = date.get(GregorianCalendar.HOUR_OF_DAY);
        int min = date.get(GregorianCalendar.MINUTE);
        int sec = date.get(GregorianCalendar.SECOND);
        
        return formatDate(new int[] {year, month, day}, new int[] {hour, min, sec}, format);        
    }
	public static final V bf_list(V... elements) {
		boolean filter=false;
		for (V e : elements) {
			if (e==null || e.isNull()) {
				filter=true;
				break;
			}
		}
		if (filter) {
			ArrayList<V> list = new ArrayList<V>(elements.length);
			for (int i=0; i<elements.length; i++) {
				V e = elements[i];
				if (e!=null && !e.isNull()) {
					list.add(e);
				}
			}
			return V.getInstance(elements);
		} else {
			return V.getInstance(elements);
		}
	}
	public static final V bf_vpmtry(V module, V what, V... parameters) { 
		return null; /* TODO */
	}
	public static final V bf_vpmexist(V module) { 
		return null; /* TODO */
	}
	public static final V bf_vpmexist(V module, V numargumentsAndEntrypoint)  { 
		return null; /* TODO */ 
	}
	public static final V bf_vpmexist(V module, V numarguments, V entrypoint) { 
		return null; /* TODO */ 
	}
	public static final V bf_error(V msg) { 
		throw new ExceptionCalculation(msg.stringValue(), null);
	}
	public static final V bf_error(V msg, V ref) { 
		throw new ExceptionCalculation(msg.stringValue(), ref.stringValue());
	}
	public static final V bf_const(V arg) { 
		throw new RuntimeException("const is not supported.");
	}
	public static final V bf_getprivateprofilestring(V section, V name, V defaultvalue, V file) { 
		throw new RuntimeException("const is not supported.");
	}
	public static final V bf_select(V field, V tablename, V where, V... parameters) { 
		throw new RuntimeException("select not yet supported.");
	}
	public static final V bf_selectx(V selectnr, V field, V tablename, V where, V... parameters) { 
		throw new RuntimeException("selectx not yet supported.");
	}
	public static final V bf_v_select(V field, V tablename, V where, V... parameters) { 
		throw new RuntimeException("v_select not yet supported.");
	}
	public static final V bf_v_selectx(V selectnr, V field, V tablename, V where, V... parameters) { 
		throw new RuntimeException("v_selectx not yet supported.");
	}
	
	/* helper functions */
	/* ================ */
    /**
     * @return int[] {year, month, day} is valid; null if date not valid
     */
    public final static int[] convertToDate(V date)  {
    	String s = date.stringValue();
    	if (s==null) {
    		return null;
    	}
        /*
         * yyyy-mm-dd
         * tt.mm.jjjj 
         * mm/dd/yyyy 
         */
        String year;
        String month;
        String day;
        if (s.indexOf('-') > 0) {
            //
            final String[] parts = s.split("-");
            if (parts.length!=3) {
                return null;
            }
            
            if (!REGEX_YMD.matcher(s).matches()) { 
                return null;
            }
            
            year = parts[0];
            month = parts[1];
            day = parts[2];
        } else if (s.indexOf('.') > 0) {
            //m_string.matches("^[0-9][0-9]?[.][0-9][0-9]?[.][0-9][0-9][0-9][0-9]$")
            final String[] parts = s.split("\\.");
            if (parts.length!=3) {
                return null;
            }
            if (!REGEX_DMY.matcher(s).matches()) { 
                return null;
            }
            
            day = parts[0];
            month = parts[1];
            year = parts[2];
        } else if (s.indexOf('/') > 0) {
            final String[] parts = s.split("/");
            if (parts.length!=3) {
                return null;
            }
            if (!REGEX_MDY.matcher(s).matches()) { 
                return null;
            }
            
            month = parts[0];
            day = parts[1];
            year = parts[2];
        } else {
        	return null;
        }
        final int iyear = Integer.parseInt(year);
        final int imonth = Integer.parseInt(month);
        final int iday = Integer.parseInt(day);
        
        final int[] ymd = new int[] { iyear, imonth, iday };
        if (JulianDate.isDate(ymd)) {
            return ymd;
        } else {
            return null;
        }
    }
    
    public static final V formatDate(final int[] ymd, final int[] hms, final V format)  {
    	if (ymd==null) {
            return null;
    	}
        
        final int year  = ymd[0];
        final int month = ymd[1];
        final int day   = ymd[2];
        
    	final StringBuffer buf = new StringBuffer(10);
        if(format==null) {
        	buf.append(day);
        	buf.append('.');
        	buf.append(month);
        	buf.append('.');
        	buf.append(year);
            return V.getInstance(buf.toString());
        }
        String sformat = format.stringValue();
        final CharacterIterator i = new StringCharacterIterator(sformat);
        char c;
        c = i.current();
        while (c!=CharacterIterator.DONE) {
            switch (c) {
            case 'd':  //day - one or two digits
                buf.append(day);
                break;
            case 'D': // day - two digits
                if (day<10)
                    buf.append("0");
                buf.append(day);
                break;
            case 'i': // day of the year
                final int dayInYear = JulianDate.toJulian(ymd) - JulianDate.toJulian(new int[] {year, 1, 1 }) + 1;
                buf.append(Integer.toString(dayInYear));
                break;
            case 'm': // month - one or two digits
                buf.append(month);
                break;
            case 'M': // month - two digits
                if (month<10)
                    buf.append("0");
                buf.append(month);
                break;
            case 'y': // year (is always 4 digits by definition of valid date)
            case 'Y': // year (is always 4 digits by definition of valid date)
                buf.append(year);
                break;  
            case 'j': // year without century - one or two digits
                buf.append(year % 100);
                break;
            case 'J': // year without century - two digits
                final int j = year % 100;
                if (j<10)
                    buf.append("0");
                buf.append(j);
                break;
            case 'h': // hour - one or two digts
                buf.append(hms==null ? 0 : hms[0]);
                break;
            case 'H': // hour - two digits
                final int hour = (hms==null) ? 0 : hms[0];
                if (hour<10)
                    buf.append("0");
                buf.append(hour);
                break;
            case 'n': // minute - one or two digits
                buf.append(hms==null ? 0 : hms[1]);
                break;
            case 'N': // minute - two digits
                final int min = (hms==null) ? 0 : hms[1];
                if (min<10)
                    buf.append("0");
                buf.append(min);
                break;
            case 's': // second - one or two digits
                buf.append(hms==null ? 0 : hms[2]);
                break;
            case 'S': // second - two digits
                final int sec = (hms==null) ? 0 : hms[2];
                if (sec<10)
                    buf.append("0");
                buf.append(sec);
                break;
            case 'w': // day of the week as number, starting with 0 at sunday
                buf.append(new GregorianCalendar(year, month-1, day).get(GregorianCalendar.DAY_OF_WEEK)-1);
                break;
            case 'W': // day of the week as text
                final int indDay = new GregorianCalendar(year, month-1, day).get(GregorianCalendar.DAY_OF_WEEK)-1;
                buf.append(WEEKDAY_TEXT_EN[indDay]);
                break;
            default:
                buf.append(c);
            }
            c = i.next();
        }
        return V.getInstance(buf.toString());
    }
    
    public static V v_construct(final CharacterIterator i) {
        char c;
        c = i.current();
        if (c!='(') { // Scalar
            final StringBuffer s = new StringBuffer(i.getEndIndex());
            while (c!=CharacterIterator.DONE && c!='!' && c!=')') {
                if(c=='/') {
                    c=i.next();
                    if (c==CharacterIterator.DONE) {
                        break;
                    }
                }
                s.append(c);
                c = i.next();
            }
            return V.getInstance(s.toString());
        } else { // vector
            final ArrayList<V> ret = new ArrayList<V>();
            i.next(); // skip '('
            while(i.current()!=CharacterIterator.DONE && i.current()!=')') {
                ret.add(v_construct(i));
                if (i.current()=='!') {
                    i.next();
                }
            }
            if (i.current()!=')') {
                throw new RuntimeException("invalid syntax for v_construct:" + i.toString());
            }
            i.next(); // skip ')'
            return V.getInstance(ret);
        }
    }
}
