/*
 * Created on 02.11.2007
 */
package treecalc.rt;

import java.util.Comparator;

/** Used for numerical sorting  
 * @author VHHNS01
 */
public class CompV_num implements Comparator<V> {
    private static CompV_num instance = new CompV_num();
    
    public static Comparator<V> getInstance() {
        return instance;
    }
    public int compare(V arg1, V arg2) {
        double d1;
        double d2;
        try {
            d1 = arg1.doubleValue();
        }
        catch (Exception e) {
            return -1;
        }
        try { 
            d2 = arg2.doubleValue();
        }
        catch (Exception e) {
            return 1;
        }
        return Double.compare(d1, d2);
    }
}
