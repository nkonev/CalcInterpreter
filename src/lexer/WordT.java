package lexer;

public class WordT extends Token{
	public final String value;
	public WordT(Tag n, String value) {
		super(n);
		this.value=value;
	}
}
