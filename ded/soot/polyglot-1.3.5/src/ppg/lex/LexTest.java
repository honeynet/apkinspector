package ppg.lex;

import java.io.*;
import ppg.parse.*;

public class LexTest
{
	private static final String HEADER = "ppg [lexertest]: ";
	
	public LexTest() {}
	
	public static void main(String args[]) {
		FileInputStream fileInput;
		String filename = null;
		try {
			filename = args[0];
			fileInput = new FileInputStream(filename);
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: "+filename+" is not found.");
			return;
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(HEADER+"Error: No file name given.");
			return;
		}

		File f = new File(filename);
		String simpleName = f.getName();

		Lexer lex = new Lexer(fileInput, simpleName);
		Token t = null;
		try {
			while (true) {
				t=lex.getToken();
				t.unparse(System.out);
				if (t.getCode() == Constant.EOF) {
					break;
				}
				System.out.println();
			}
			fileInput.close();
		} catch (Error e) {
			System.out.println(e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
}
