package ppg.code;

public abstract class Code
{
	protected String value;
	public abstract Object clone();
	
	public void append(String s) {
		value += "\n" + s;								  
	}
	
	public void prepend(String s) {
		value = s + "\n" + value;					   
	}
	
	public abstract String toString();
}
