package treecalc.rt;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class ExceptionNeedMoreInput extends RuntimeException {
	private static final long serialVersionUID = 6211771730911120457L;

	private final String inputname;
	private final int inputindex;
	private final int[] index;
	private final String inputnameWithIndex;
	private final String msg;
	public ExceptionNeedMoreInput(String inputname, int inputindex, int[] index) {
		this.inputname = inputname;
		this.inputindex = inputindex;
		this.index = index;
		if (index==null || index.length==0) {
			this.inputnameWithIndex = inputname; 
		} else {
			this.inputnameWithIndex = inputname + Arrays.toString(index);
		}
		this.msg = "need more input: " + inputnameWithIndex;
	}
	public String getInputnameWithIndex() {
		return inputnameWithIndex;
	}
	public String getInputname() {
		return inputname;
	}
	public int getInputindex() {
		return inputindex;
	}
	public int[] getIndex() {
		return index;
	}

	
	@Override
	public String getMessage() {
		return msg;
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}

	@Override
	public Throwable getCause() {
		return null;
	}

	@Override
	public Throwable initCause(Throwable cause) {
		return null;
	}

	@Override
	public String toString() {
		return msg;
	}

	@Override
	public void printStackTrace() {
		System.err.println(msg);
	}

	@Override
	public void printStackTrace(PrintStream s) {
		s.println(msg);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		s.println(msg);
	}

	@Override
	public Throwable fillInStackTrace() {
		return null;
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return null;
	}

	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
	}
}
