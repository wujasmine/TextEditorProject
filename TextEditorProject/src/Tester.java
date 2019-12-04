//Created to test the lexical analyzer
import java.io.*;
public class Tester {
	public static void main(String[] args) {
		//Need to enter a file name for the constructor below
		File in = new File("/home/serec/git/TextEditorTest/TextEditor/Test.txt");
		File out = new File("/home/serec/git/TextEditorTest/TextEditor/Testout.txt");
		Parsing p = new Parsing(in, out);
		
		p.parseFile();
	}
}
