import java.io.*;
import java.util.ArrayList;
/*
 * The meat of the program. Organizes the input into lines according to the
 * parameters given in the input file. Needs the input file and output file
 * to write to. Need streams to output error messages to?
 */
public class Parsing {
	int lineLength = 80;
	int justification = 1; //0-right, 1-left, 2-center, 3-equal
	boolean wrap = false; //f-wrap off, t-wrap on
	boolean spacing = false; //f-single space, t-double space
	int indent = 0; //paragraph indent
	boolean columns = false; //f-one column, t-two columns
	//title and blank lines are one-time uses and will be integrated into writing
	
	BufferedWriter out;
	Lexer lex;
	ArrayList<Lexer.Token> lineBuffer = new ArrayList<Lexer.Token>();
	
	public Parsing() {
		lex = new Lexer();
		try {
			out = new BufferedWriter(new FileWriter("/Home/Documents/temp/test.txt"));
		} catch (IOException e) {
			System.out.println("ERROR: Failed to write to file");
		}
	}
	public Parsing(File inFile, File outFile) {
		lex = new Lexer(inFile);
		try {
			out = new BufferedWriter(new FileWriter(outFile));
		} catch (IOException e) {
			System.out.println("ERROR: Failed to write to file");
		}
	}
	public Parsing(String inFile, String outFile) {
		lex = new Lexer(inFile);
		try {
			out = new BufferedWriter(new FileWriter(outFile));
		} catch (IOException e) {
			System.out.println("ERROR: Failed to write to file");
		}
	}
	
	/*
	 * Main parsing, runs a loop. In the loop it either parses a command or it
	 * parses a line full of words using the parseCommand or parseWords. When
	 * it parses commands, formatting parameters change. When words are parsed
	 * words are continually grabbed until it reaches a new line. At this
	 * point, if wrapping or double column are not on, it will start outputting
	 * lines according to the formatting rules.
	 */
	/**
	 * Main parser
	 */
	public void parseFile() {}
	
	/**
	 * Handles the input command and changes parameters accordingly
	 */
	public void parseCommand() {}
	
	/**
	 * parses words until it hits a newline
	 */
	public void parseWords() {}
	
	/**
	 * Writes word tokens into strings according to the formatting rules.
	 */
	public void buildLines() {}
	
}
