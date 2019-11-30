import java.io.*;
import java.util.ArrayList;

/**
 * Reads text files character by character.
 * Default constructor reads input from System.in
 * Other constructors take a string or file object and read
 * from the specified file.
 * @author serec
 *
 */
public class InBuf {
	private BufferedReader reader;
	private ArrayList<Character> buffer = new ArrayList<Character>();
	
	public InBuf() {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}
	public InBuf(String fileName) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(fileName));
	}
	public InBuf(File f) throws FileNotFoundException{
		reader = new BufferedReader(new FileReader(f));
	}
	
	/**
	 * Returns a character pulled from the buffer or from the file.
	 * It is recommended to run EndOfFile before calling this function.
	 * @return a character
	 */
	public char GetChar() {
		char c;
		if(!buffer.isEmpty()) {
			c = buffer.remove(buffer.size()-1);
		}
		else {
			try {
				c = (char)reader.read();
			} catch (IOException e){
				c = (char)-1;
				buffer.add(c);
			}
			
		}
		return c;
	}
	
	/**
	 * Checks if the end of the file has been reached. Peeks the next character
	 * to do so, storing it in the buffer.
	 * @return end of file
	 */
	public boolean EndOfFile() {
		boolean end = true;
		if(!buffer.isEmpty()) {
			char c = buffer.get(buffer.size()-1);
			if((int)c == -1) {
				end = true;
			}
			else {
				end = false;
			}
		}
		else {
			int i = -1;
			try {
				i = reader.read();
			}catch(IOException e){
				i = -1;
			}
			
			if(i == -1) {
				end = true;
			}
			else {
				end = false;
				buffer.add((char)i);
			}
		}
		return end;
	}
	
	/**
	 * pushes the character back into the buffer for parsing purposes.
	 * Get and UnGet use the buffer as a stack. 
	 * It is therefore important to unget in the reverse order that you get.
	 * @param c
	 */
	public void UnGetChar(char c) {
		buffer.add(c);
	}
}
