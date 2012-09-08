package ppg.code;

public class ScanCode extends Code
{
	public ScanCode (String scanCode) {
		value = scanCode;
	}

	public Object clone () {
		return new ScanCode(value.toString());	
	}
	
	public String toString () {
		return "scan with {:" + value + ":};";
	}

}
