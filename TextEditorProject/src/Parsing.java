import java.io.*;
import java.util.ArrayList;
//import org.apache.commons.lang3.StringUtils;
/*
 * The meat of the program. Organizes the input into lines according to the
 * parameters given in the input file. Needs the input file and output file
 * to write to. Need streams to output error messages to?
 */
public class Parsing {
	private int lineLength = 80; //number of characters in a line
	private int justification = 1; //0-right, 1-left, 2-center, 3-equal
	private boolean wrap = false; //f-wrap off, t-wrap on
	private boolean spacing = false; //f-single space, t-double space
	private int indent = 0; //paragraph indent by the number of spaces
	private boolean columns = false; //f-one column, t-two columns
	private int blanks = 0; //used to hold the number of blank lines to be output
	private boolean title = false; //temporary marker to tell if next line is a title line
	
	private BufferedWriter out;
	private Lexer lex;
	private ArrayList<Lexer.Token> wordBuffer = new ArrayList<Lexer.Token>();
	private ArrayList<String> lineBuffer = new ArrayList<String>();
	
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
		System.out.println("Begin Parsing");
		Lexer.Token temp;
		temp = lex.GetToken();
		while(temp.type != Lexer.TokenType.EOF) {
			if(temp.type == Lexer.TokenType.NEWLINE) {
				System.out.println("Parse newline");
				//newline string
			}
			else if(temp.type != Lexer.TokenType.WORD) {
				System.out.println("Parse command");
				lex.UnGetToken(temp);
				parseCommand();
			}
			else {
				System.out.println("Parse Word");
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
			if(!columns) {
				lineLength = num;
			}
			
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
				lineLength = 35;
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
		//lex.UnGetToken(temp);
		//String t2 = "";	//debug delete later
		while(temp.type != Lexer.TokenType.EOF && temp.type != Lexer.TokenType.NEWLINE) {
			Lexer.Token temp2 = lex.new Token();	//work around for wierd arraylist behavior
			temp2.lexeme = temp.lexeme;
			temp2.type = temp.type;
			//System.out.println("lexer getting token: " + temp.lexeme);
			wordBuffer.add(temp2);
			//t2 = wordBuffer.get(wordBuffer.size()-1).lexeme;
			//System.out.println("WordBuf test: " + t2 + " " + wordBuffer.get(wordBuffer.size()-1).type + " " + wordBuffer.size());
			//System.out.println("WordBuf test: " + wordBuffer.get(0).lexeme + " " + wordBuffer.get(0).type);

			temp = lex.GetToken();
		}

		//System.out.println("WordBuf size: " + wordBuffer.size());
		
	}
	
	/**
	 * Writes word tokens into strings according to the formatting rules.
	 */
	public void buildLines() {
		System.out.println("Buffer size: " + wordBuffer.size());

		String line = "";
		Lexer.Token currentWord;
		boolean empty = false;
		if (blanks > 0) {
			for (int i = 0; i < blanks - 1; i++) {
				line += '\n';
			}
			// reset to 0 so next line won't also repeat command?
			blanks = 0;
		}
		if (indent > 0 && justification == 1) {
			for (int i = 0; i < indent - 1; i++) {
				line = " " + line;
			}
			// reset to 0 so next line won't also repeat command?
			indent = 0;
		}
		
		
		while(!wordBuffer.isEmpty()) {
			//System.out.println("WordBuf test: " + wordBuffer.get(0).lexeme + " " + wordBuffer.get(0).type + " " + wordBuffer.size());

			currentWord = wordBuffer.remove(0);//pop from beginning of word buffer
			//System.out.println("Storing word: " + currentWord.lexeme + currentWord.type);
			//System.out.println("outerloop size: " + wordBuffer.size());
			while((line.length() + currentWord.lexeme.length()) <= lineLength && !empty) {
				//System.out.println("Storing word: " + currentWord.lexeme);
				line += currentWord.lexeme + " ";
				
				if(!wordBuffer.isEmpty()) {
					currentWord = wordBuffer.remove(0);
				}
				else {
					empty = true;
				}
				
			}
			//System.out.println("Buffer size: " + wordBuffer.size());
			line = line.substring(0, line.length()-2);//remove last space from above loop.
			if (title) {
				String lineTemp = "";
				for(int i = 0; i < line.length();i++) {
					lineTemp += "-";//or whatever character we want to use
				}
				int remaining = lineLength - line.length();
				remaining = remaining / 2;
				for(int i = 0; i < remaining; i++) {
					lineTemp = " " + lineTemp;
					line = " " + line;
				}
				line += "\n" + lineTemp;
				//line = "\u0332" + line;
				//StringUtils.center(line, lineLength);
				title = false;
			}
			else if(justification == 0) {
				int remaining = lineLength - line.length();
				for(int i = 0; i < remaining; i++) {
					line = " " + line;
				}
			}
			else if(justification == 1) {
				
			}
			else if(justification == 2) {
				int remaining = lineLength - line.length();
				remaining = remaining / 2;
				for(int i = 0; i < remaining; i++) {
					line = " " + line;
				}
			}
			else if(justification == 3) {
				line = equalSpaces(line);
			}
			
			
			if (spacing) {
				// does this work?
				line += "\n";
				line += '\n';
			} else {
				line += '\n';
			}
			System.out.println("Writeing line: " + line);
			try {
				out.write(line);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			line = "";
		}
		
		//write the line
		//still need logic for the various parameters like justification
		//adding spaces for indents (line + "    " ...)
		//need to figure out writing to two columns. Use an arraylist of strings?
		
		
		
		
	}
	
	private String equalSpaces(String str) {
		int remainingSpace = lineLength - str.length();
		String equalStr = str;
		if(remainingSpace == 0) {
			equalStr = str;
		}
		else {
			int originalSpaces = 0;
			char[] temp = str.toCharArray();
			for(int i = 0; i < temp.length; i++) {
				if(temp[i] == ' ') {
					originalSpaces++;
				}
			}
			int spaceLength = remainingSpace/originalSpaces;
			int extraSpaces = remainingSpace%originalSpaces;
			String spaceString = "";
			for(int i = 0; i < spaceLength; i++) {
				spaceString += " ";
			}
			int i = 0;
			while(i < lineLength && i < equalStr.length()) {
				if(equalStr.charAt(i) == ' ') {
					equalStr = equalStr.substring(0,i) + spaceString + equalStr.substring(i+1, equalStr.length());
					if(extraSpaces > 0) {
						equalStr = equalStr.substring(0, i) + " " + equalStr.substring(i+1, equalStr.length());
						extraSpaces--;
						i++;
					}
					i += spaceLength;
				}
				i++;
			}		
		}
		return equalStr;
	}
	
}
