import java.io.*;
import java.util.ArrayList;
/*
 * The meat of the program. Organizes the input into lines according to the
 * parameters given in the input file. Needs the input file and output file
 * to write to. Need streams to output error messages to?
 */
public class Parsing {
	int lineLength = 80; //number of characters in a line
	int justification = 1; //0-right, 1-left, 2-center, 3-equal
	boolean wrap = false; //f-wrap off, t-wrap on
	boolean spacing = false; //f-single space, t-double space
	int indent = 0; //paragraph indent by the number of spaces
	boolean columns = false; //f-one column, t-two columns
	int blanks = 0; //used to hold the number of blank lines to be output
	boolean title = false; //temporary marker to tell if next line is a title line
	
	BufferedWriter out;
	Lexer lex;
	ArrayList<Lexer.Token> wordBuffer = new ArrayList<Lexer.Token>();
	ArrayList<String> lineBuffer = new ArrayList<String>();
	
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
	public void parseFile() {
		Lexer.Token temp;
		temp = lex.GetToken();
		while(temp.type != Lexer.TokenType.EOF) {
			if(temp.type == Lexer.TokenType.NEWLINE) {
				
			}
			else if(temp.type != Lexer.TokenType.WORD) {
				lex.UnGetToken(temp);
				parseCommand();
			}
			else {
				lex.UnGetToken(temp);
				parseWords();
				buildLines();
			}
			temp = lex.GetToken();
		}
	}
	
	/**
	 * Handles the input command and changes parameters accordingly
	 */
	public void parseCommand() {
		Lexer.Token temp = lex.GetToken();
		String numString = "";
		int num = 0;
		switch(temp.type) {
		case LINELENGTH:
			numString = temp.lexeme.substring(2);
			num = 0;
			try {
				num = Integer.parseInt(numString);
			} catch (NumberFormatException e) {
				num = 0;
			}
			lineLength = num;
			break;
		case LEFTJUST:
			justification = 1;
			break;
		case RIGHTJUST:
			justification = 0;
			break;
		case CENTERJUST:
			justification = 2;
			break;
		case EQUALJUST:
			justification = 3;
			break;
		case WRAPPING:
			numString = temp.lexeme.substring(2);
			if(numString == "+") {
				wrap = true;
			}
			else if(numString == "-") {
				wrap = false;
			}
			break;
		case SINGLESPACE:
			spacing = false;
			break;
		case DOUBLESPACE:
			spacing = true;
			break;
		case TITLE:
			title = true;
			break;
		case INDENT:
			numString = temp.lexeme.substring(2);
			num = 0;
			try {
				num = Integer.parseInt(numString);
			} catch (NumberFormatException e) {
				num = 0;
			}
			indent = num;
			break;
		case BLANKLINES:
			numString = temp.lexeme.substring(2);
			num = 0;
			try {
				num = Integer.parseInt(numString);
			} catch (NumberFormatException e) {
				num = 0;
			}
			blanks = num;
			break;
		case COLUMNS:
			numString = temp.lexeme.substring(2);
			num = 1;
			try {
				num = Integer.parseInt(numString);
			} catch (NumberFormatException e) {
				num = 1;
			}
			if(num == 1) {
				columns = false;
			}
			else if(num == 2) {
				columns = true;
			}
			break;
		default://error case
			break;
		}
	}
	
	/**
	 * parses words until it hits a newline
	 */
	public void parseWords() {
		Lexer.Token temp;
		temp = lex.GetToken();
		while(temp.type != Lexer.TokenType.EOF && temp.type != Lexer.TokenType.NEWLINE) {
			wordBuffer.add(temp);
			temp = lex.GetToken();
		}
		if(temp.type != Lexer.TokenType.NEWLINE) {
			wordBuffer.add(temp);
		}
	}
	
	/**
	 * Writes word tokens into strings according to the formatting rules.
	 */
	public void buildLines() {
		
	}
	
}
