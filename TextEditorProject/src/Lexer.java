import java.util.ArrayList;
import java.io.*;

/**
 * Reads an input file so that it can return tokens which hold individual words
 * extracted from the text
 * @author serec
 *
 */
public class Lexer {
	enum TokenType{
		WORD, SPACE, NEWLINE, LINELENGTH, RIGHTJUST, 
		LEFTJUST, CENTERJUST, EQUALJUST, WRAPPING,
		SINGLESPACE, DOUBLESPACE, TITLE, INDENT,
		BLANKLINES, COLUMNS, EOF, ERROR
	}
	
	/**
	 * A part of lexer, meant to contain important information about strings
	 * it pulls from the text, such as, what command it is, if it is a word,
	 * and the actual string for more processing if needed.
	 * @author serec
	 *
	 */
	public class Token {
		//remove line_no for memory conservation?	
		int line_no;
		String lexeme;
		TokenType type;
		
		Token(){
			line_no = 0;
			lexeme = "";
			type = TokenType.ERROR;
		}
		
		/**
		 * Prints the info of the token
		 */
		public String toString() {
			String s = "";
			s += "lexeme: " + lexeme;
			s += "\nline: " + line_no;
			s += "\ntype: " + type + "\n";
			return s;
		}
		
	}
	
	InBuf input;
	ArrayList<Token> TokBuffer = new ArrayList<Token>();
	
	Token temp = new Token();
	int line = 0;
	
	Lexer(){
		input = new InBuf();
	}
	Lexer(String fileName){
		try {
			input = new InBuf(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Please try again.");
			System.exit(0);
		}
		
	}
	Lexer(File f){
		try {
			input = new InBuf(f);
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Please try again.");
			System.exit(0);
		}
	}
	
	/**
	 * Pushes a token back into the buffer
	 * @param a Token
	 */
	public void UnGetToken(Token t) {
		TokBuffer.add(t);
	}
	
	/**
	 * Grabs the next word or command and returns it as a token. Pulls from the
	 * buffer first if it's not empty
	 * @return a Token
	 */
	public Token GetToken() {	
		if(!TokBuffer.isEmpty()) {
			//System.out.println("Grabbing from buffer");
			temp = TokBuffer.remove(TokBuffer.size()-1);
		}
		else if(input.EndOfFile()) {
			temp.type = TokenType.EOF;
		}
		else if(HandleWhiteSpace()) {
			//System.out.println("Checking whitespace");
			MakeNewLineToken(false);
		}
		else {
			//System.out.println("Reading new Token");
			temp.lexeme = "";
			temp.line_no = line;
			temp.type = TokenType.ERROR;
			char c = input.GetChar();
			if(c == '-') {
				temp.lexeme += c;
				c = input.GetChar();
				switch (c) {
				case 'n':
					temp.lexeme += c;
					c = input.GetChar();
					if(Character.isDigit(c)){
						while(Character.isDigit(c) && !input.EndOfFile()) {
							temp.lexeme += c;
							c = input.GetChar();
						}
						input.UnGetChar(c);
						boolean SpaceEncountered = Character.isWhitespace(c);
						if(HandleWhiteSpace()) {
							temp.type = TokenType.LINELENGTH;
						}
						else if(SpaceEncountered) {
							temp.type = TokenType.WORD;
						}
						else {
							ScanWord();
						}
							
					}
					else if(c == ' ') {
						temp.type = TokenType.WORD;
					}
					else if(c == '\n') {
						MakeNewLineToken(true);
						temp.type = TokenType.WORD;
					}
					else {
						ScanWord();
					}
					break;
				case 'r':
					temp.lexeme += c;
					if(!ValidateCommand(TokenType.RIGHTJUST)) {
						ScanWord();
					}
					break;
				case 'l':
					temp.lexeme += c;
					if(!ValidateCommand(TokenType.LEFTJUST)) {
						ScanWord();
					}			
					break;
				case 'c':
					temp.lexeme += c;
					if(!ValidateCommand(TokenType.CENTERJUST)) {
						ScanWord();
					}
					break;
				case 'e':
					temp.lexeme += c;
					if(!ValidateCommand(TokenType.EQUALJUST)) {
						ScanWord();
					}
					break;
				case 'w':
					temp.lexeme += c;
					c = input.GetChar();
					if(c == '+') {
						temp.lexeme += c;
						if(!ValidateCommand(TokenType.WRAPPING)) {
							ScanWord();
						}
					}
					else if(c == '-') {
						temp.lexeme += c;
						if(!ValidateCommand(TokenType.WRAPPING)) {
							ScanWord();
						}
					}
					else {
						input.UnGetChar(c);
						ScanWord();
					}
					break;
				case 's':
					temp.lexeme += c;
					if(!ValidateCommand(TokenType.SINGLESPACE)) {
						ScanWord();
					}
					break;
				case 'd':
					temp.lexeme += c;
					if(!ValidateCommand(TokenType.DOUBLESPACE)) {
						ScanWord();
					}
					break;
				case 't':
					temp.lexeme += c;
					if(!ValidateCommand(TokenType.TITLE)) {
						ScanWord();
					}
					break;
				case 'p':
					/*
					temp.lexeme += c;
					c = input.GetChar();
					if(!Character.isDigit(c)) {
						temp.type = TokenType.WORD;
						if(!Character.isWhitespace(c)) {
							ScanWord();
						}
					}
					else {
						while(Character.isDigit(c) && !input.EndOfFile()) {
							temp.lexeme += c;
							c = input.GetChar();
						}
						input.UnGetChar(c);
						if(!ValidateCommand(TokenType.INDENT)) {
							ScanWord();
						}
					}
					*/
					temp.lexeme += c;
					c = input.GetChar();
					if(Character.isDigit(c)){
						while(Character.isDigit(c) && !input.EndOfFile()) {
							temp.lexeme += c;
							c = input.GetChar();
						}
						input.UnGetChar(c);
						boolean SpaceEncountered = Character.isWhitespace(c);
						if(HandleWhiteSpace()) {
							temp.type = TokenType.INDENT;
						}
						else if(SpaceEncountered) {
							temp.type = TokenType.WORD;
						}
						else {
							ScanWord();
						}
							
					}
					else if(c == ' ') {
						temp.type = TokenType.WORD;
					}
					else if(c == '\n') {
						MakeNewLineToken(true);
						temp.type = TokenType.WORD;
					}
					else {
						ScanWord();
					}
					
					break;
				case 'b':
					temp.lexeme += c;
					c = input.GetChar();
					if(Character.isDigit(c)){
						while(Character.isDigit(c) && !input.EndOfFile()) {
							temp.lexeme += c;
							c = input.GetChar();
						}
						input.UnGetChar(c);
						boolean SpaceEncountered = Character.isWhitespace(c);
						if(HandleWhiteSpace()) {
							temp.type = TokenType.BLANKLINES;
						}
						else if(SpaceEncountered) {
							temp.type = TokenType.WORD;
						}
						else {
							ScanWord();
						}
							
					}
					else if(c == ' ') {
						temp.type = TokenType.WORD;
					}
					else if(c == '\n') {
						MakeNewLineToken(true);
						temp.type = TokenType.WORD;
					}
					else {
						ScanWord();
					}
					
					break;
				case 'a':
					temp.lexeme += c;
					c = input.GetChar();
					if(c == '1') {
						temp.lexeme += c;
						if(!ValidateCommand(TokenType.COLUMNS)) {
							ScanWord();
						}
					}
					else if(c == '2') {
						temp.lexeme += c;
						if(!ValidateCommand(TokenType.COLUMNS)) {
							ScanWord();
						}
					}
					else {
						//error message
						
					}
					break;
				default:
					temp.lexeme += c;
					ScanWord();
					break;
					
				}
			}
			else if(input.EndOfFile()) {
				temp.type = TokenType.EOF;
			}
			else {
				temp.lexeme += c;
				ScanWord();
			}
		}
		
		return temp;
	}
	
	/**
	 * Scans a sequence of characters until it encounters a space.
	 */
	private void ScanWord() {
		boolean isEnd = input.EndOfFile();
		char c = input.GetChar();
		while(!Character.isWhitespace(c) && !isEnd) {
			temp.lexeme += c;
			isEnd = input.EndOfFile();
			c = input.GetChar();
		}
		//temp.lexeme += c;
		input.UnGetChar(c);
		temp.type = TokenType.WORD;
	}
	
	/**
	 * Skips over spaces. Returns true if it finds a newline
	 * @return lineFound
	 */
	private boolean HandleWhiteSpace() {
		char c;
		boolean lineFound = false;
		
		c = input.GetChar();
		if(c == '\n') {
			line++;
			//NewLineToken();
			//TokBuffer.add(temp);
			lineFound = true;
		}
		while(!lineFound && !input.EndOfFile() && Character.isWhitespace(c)) {
			c = input.GetChar();
			if(c == '\n') {
				line++;
				//NewLineToken();
				//TokBuffer.add(temp);
				lineFound = true;
			}
		}
		
		if(!input.EndOfFile() && !Character.isWhitespace(c)) {
			input.UnGetChar(c);
		}		
		
		return lineFound;
	}
	
	/**
	 * If push is true it adds a Newline token to the buffer so as not to
	 * interrupt the reading of a command or the order.
	 * If push is false it assigns a Newline token to temp
	 * @param push
	 */
	private void MakeNewLineToken(boolean push) {
		if(push) {
			Token t = new Token();
			t.line_no = line;
			t.type = TokenType.NEWLINE;
			TokBuffer.add(t);
		}
		else {
			temp.lexeme = "";
			temp.type = TokenType.NEWLINE;
			temp.line_no = line;
		}
	}
	
	/**
	 * Ensures that the command is followed by a newline.
	 * If it is followed by a newline assign commandType to the token to be returned
	 * Otherwise assign the WORD type to it
	 * @param commandType
	 * @return if it is a valid command
	 */
	private boolean ValidateCommand(TokenType commandType) {
		/*
		boolean isValid = false;
		isValid = HandleWhiteSpace();
		if(isValid) {
			temp.type = commandType;
		}
		else {
			temp.type = TokenType.WORD;
		}
		
		return isValid;
		*/
		char c;
		boolean lineFound = false;
		boolean spaceFound = false;
		
		c = input.GetChar();
		if(c == '\n') {
			line++;
			lineFound = true;
		}
		else if(c == ' ') {
			spaceFound = true;
		}
		while(!lineFound && !spaceFound && !input.EndOfFile() && Character.isWhitespace(c)) {
			spaceFound = true;
			c = input.GetChar();
			if(c == '\n') {
				line++;
				lineFound = true;
			}
		}
		
		if(lineFound) {
			input.UnGetChar(c);
			temp.type = commandType;
		}
		else if(spaceFound) {
			input.UnGetChar(' ');
		}
		
		return lineFound;
	}
}
