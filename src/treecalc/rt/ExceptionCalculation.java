package treecalc.rt;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ExceptionCalculation extends RuntimeException {
	private static final long serialVersionUID = 3815922612630321L;
	private final String inputnameWithIndex;
	private final String msg;
	public ExceptionCalculation(String msg, String inputnameWithIndex) {
		this.msg = msg;
		this.inputnameWithIndex = inputnameWithIndex!=null && inputnameWithIndex.length()>0 ? inputnameWithIndex : null;
	}
	public String getInputnameWithIndex() {
		return inputnameWithIndex;
	}
	public String getMsg() {
		return msg;
	}

	@Override
	public String getMessage() {
		if (inputnameWithIndex==null) {
			return msg;
		} else {
			return msg + ", input " + inputnameWithIndex;
		}
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
		return getMessage();
	}

	@Override
	public void printStackTrace() {
		System.err.println(getMessage());
	}

	@Override
	public void printStackTrace(PrintStream s) {
		s.println(getMessage());
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		s.println(getMessage());
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
