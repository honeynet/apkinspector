package ppg.code;

public class InitCode extends Code
{
	public InitCode (String initCode) {
		value = initCode;
	}

	public Object clone () {
		return new InitCode(value.toString());	
	}
	
	public String toString () {
		return "init code {:\n" + value + "\n:}\n";
	}
}

