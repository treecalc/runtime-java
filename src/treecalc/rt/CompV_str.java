/*
 * Created on 02.11.2007
 */
package treecalc.rt;

import java.util.Comparator;

/** Used for alphanumerical sorting of V-Lists 
 * @author VHHNS01
 *
 */
public class CompV_str implements Comparator<V> {
    public static CompV_str instance = new CompV_str();
    
    public static Comparator<V> getInstance() {
        return instance;
    }
    public int compare(V v1, V v2) {
        String s1 = v1.stringValue();
        String s2 = v2.stringValue();
        if (s1==null)
            return -1;
        return s1.compareTo(s2);
    }
}
