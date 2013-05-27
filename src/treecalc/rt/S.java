package treecalc.rt;

import java.util.List;


/**
 * Status including input values, times-counter etc.
 * @author Stefan
 *
 */
public interface S {
	/** INPUT 
	 *  ===== */
	/**
	 * reset all input values
	 */
	public void reset();
	/**
	 * @param inputid
	 * @param index will be converted to int values. trailing 0 elements in the array are discarded
	 * @return current value of Input with given id
	 */
	public V getInput(int inputid, V...index) throws ExceptionNeedMoreInput;
	/**
	 * @param inputid
	 * @param autocounters: array of counters to care about. those are looked up in the stack of active counters
	 * @return current value
	 */
	public V getInputAutocounter(int inputid, int[] autocounters) throws ExceptionNeedMoreInput;
	public V[] getAutocounterValues(int[] autocounters);
	
	/**
	 * @param inputid
	 * @param value
	 * @param index: trailing 0 elements are discarded
	 */
	public void setInput(int inputid, V value, int...index);
	/**
	 * @param name: inputname case-insensitive. may include indizes in [] with , as separator.
	 * @param value
	 */
	public void setInput(String name, String value);
	public List<String[]> getInputList(int inputid, int...index);
	/**
	 * @param name: inputname case-insensitive. may include indizes in [] with , as separator.
	 * @return result of calculation
	 */
	public List<String[]> getInputList(String name);
	
	/**
	 * @param calcindex: index of the thing to be calculated
	 * @param args: the arguments to be passed. They are handled as Strings
	 * @return result of calculation
	 */
	public String calculateMaintree(int calcindex, String...args);
	/**
	 * @param name: propertyname case-insensitive. optional parameters are passed within (), separated with ,
	 * @return result of calculation
	 */
	public String calculateMaintree(String name);
	public String calculateInput(String name); // I_Age, I_Age[0], A_Age[0,0]
	public String calculateInputCalc(String name, String calcname); //name=A_Age, calcname=visible; name=A_Age[0], calcname=visible
	public String calculateFunction(String name); //F_Test, F_Test1(0), F_Test2(1,2), F_Test3(3,"abc")
	public String calculateTable(String name); //T_Value[1], T_Value[1,2].text
	public long getCalculateId(String name);

	public String calculate(String name);
    public String calculate(long id);
	public String calculate(long id, String...args);
	public String calculate(long id, int...args);
	
	public boolean needed(String inputname, String calcname);

	/**
	 * COUNTERS
	 * ========
	 * When entering a 'times'-node, a counter is pushed to the counters-stack.
	 * For each instance of the 'times'-node, this counter is set to the current index,
	 * starting with 0.
	 */
	
	/**
	 * push a counter to the stack of active counters
	 * @param timesid
	 */
	public void pushTimesCounter(int timesid);
	/**
	 * increment the topmost counter by 1
	 */
	public void incTimesCounterTop();
	/**
	 * Set the value of the topmost counter to the given 'value'
	 * @param value
	 */
	public void setTimesCounterTop(int value);
	/**
	 * remove topmost counter from the stack of active counters
	 */
	public void popTimesCounter();
	/**
	 * Get current value of the timer with the given id. 
	 * If multiple of counters with that id are active, the topmost one is used. 
	 * @param timesid: id of counter
	 * @return current value of counter or -1 if it is not active
	 */
	public int  getTimesCounter(int timesid);
    /**
     * see popTimesCounter. 
     * @param timesid: id of counter
     * @return the current value of the counter
     */
    public V    getTimesCounterV(int timesid);
    
    /* caching */
	public Object getCacheKey(final int id, final V...args);
    public V    readCache(Object key);
    public void writeCache(Object key, V value);

    public S getSubmodel(String vpmname);
    public boolean getInputIsNull(String inputname);
    
    public void trace (String msg, String ret, Traceaction traceaction); //0:msg, 1:call, 2:ret(simple), 3:ret(miss), 4:ret(hit)
    public enum Traceaction {
    	MESSAGE,
    	CALL,
    	RETURN_SIMPLE,
    	RETURN_MISS,
    	RETURN_HIT
    }
    
}
