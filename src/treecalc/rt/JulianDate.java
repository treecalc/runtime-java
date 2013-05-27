package treecalc.rt;

/** 
 * @author VHHNS01
 */
public final class JulianDate {
    /**
     * Returns the Julian day number that begins at noon of this day,
     * Positive year signifies A.D., negative year B.C. Remember that the
     * year after 1 B.C. was 1 A.D.
     * 
     * Adapted by Stefan Neubauer, 2007
     * 
     * ref : Numerical Recipes in C, 2nd ed., Cambridge University Press
     * 1992
     * 
     * http://www.rgagnon.com/javadetails/java-0506.html
     */
    // Gregorian Calendar adopted Oct. 15, 1582 (2299161)
    private static final int JGREG= 15 + 31*(10+12*1582);
    private final static int[] maxDaysOfMonth = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; 
    
    public static final int getDaysOfMonth(int year, int month) {
        return maxDaysOfMonth[month-1] - ((isLeapYear(year)) ? 0 : 1); 
    }
    
    public static final boolean isLeapYear(int year) {
        return !(year % 4>0) && ((year % 100 > 0) || !(year % 400> 0));
    }
    
    public static final boolean isDate(int julian) {
        return julian>=657072 && julian<=1095362; //dateday("1.1.1800"),dateday("31.12.2999")
    }

    public static final boolean isDate(int[] ymd) {
        if (ymd==null || ymd.length!=3) {
            return false;
        }
        
        int iyear = ymd[0];
        int imonth = ymd[1];
        int iday = ymd[2];
        
        if (iyear<1800 || iyear>2999) {
            return false;
        }
        if (imonth<1 || imonth>12) {
            return false;
        }
                
        /* check if valid date -> could use GregorianCalendar, but that will be slow */
        
        /* day<=28 -> okay for sure */
        if (iday>28) { 
            if (iday>maxDaysOfMonth[imonth-1]) {
                return false;
            }
            if (imonth==2) { // iday==29 here -> not ok if not leap year 
                if (!isLeapYear(iyear)) {
                    return false;
                }
            }
           
        }
        return true;
    }
    
    public static final int toJulian(int[] ymd) {
        int year=ymd[0];
        int month=ymd[1]; // jan=1, feb=2,...
        int day=ymd[2];    
        int julianYear = year;
        if (year < 0) julianYear++;
        int julianMonth = month;
        if (month > 2) {
            julianMonth++;
        } else {
            julianYear--;
            julianMonth += 13;
        }

        double julian = Math.floor(365.25 * julianYear) + Math.floor(30.6001*julianMonth) + day + 1720995.0;
        if (day + 31 * (month + 12 * year) >= JGREG) {
            // change over to Gregorian calendar
            int ja = (int)(0.01 * julianYear);
            julian += 2 - ja + (0.25 * ja);
        }
        return (int) java.lang.Math.floor(julian) - 1721425; /* subtract a couple of days to arrive at the vp/ms numbers */
    }

    /**
     * Converts a Julian day to a calendar date ref : Numerical Recipes in
     * C, 2nd ed., Cambridge University Press 1992.
     * @param injulian
     * @return int[] { year, month, day }
     */
    public static final int[] fromJulian(int injulian) {
        int[] ret = new int[3];
        fromJulian(injulian, ret);
        return ret;
    }

    /**
     * Converts a Julian day to a calendar date ref : Numerical Recipes in
     * C, 2nd ed., Cambridge University Press 1992.
     * Array allocation is avoided in this method.
     * @param injulian
     * @return int[] { year, month, day } is written into values of parameter outjulian
     */
    public static final void fromJulian(int injulian, int[] outjulian) {
        int jalpha,ja,jb,jc,jd,je,year,month,day;
        ja = injulian + 1721425; // add some days from the VP/MS value
        if (ja>= JGREG) {    
            jalpha = (int) (((ja - 1867216) - 0.25) / 36524.25);
            ja = ja + 1 + jalpha - jalpha / 4;
        }

        jb = ja + 1524;
        jc = (int) (6680.0 + ((jb - 2439870) - 122.1) / 365.25);
        jd = 365 * jc + jc / 4;
        je = (int) ((jb - jd) / 30.6001);
        day = jb - jd - (int) (30.6001 * je);
        month = je - 1;
        if (month > 12) month = month - 12;
        year = jc - 4715;
        if (month > 2) year--;
        if (year <= 0) year--;

        outjulian[0] = year;
        outjulian[1] = month;
        outjulian[2] = day;
    }
}
