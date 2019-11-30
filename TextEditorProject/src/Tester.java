//Created to test the lexical analyzer
public class Tester {
	public static void main(String[] args) {
		//Need to enter a file name for the constructor below
		Lexer lex = new Lexer("/home/serec/git/TextEditorProject/TextEditorProject/TestText.txt");
		Lexer.Token t = lex.GetToken();
		//int i = 0;
		while(t.type != Lexer.TokenType.EOF ) {
			System.out.println(t.toString());
			t = lex.GetToken();
			//i++;
		}
	}
}
