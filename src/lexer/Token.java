package lexer;

import types.TypedValue;

// Пара Имя-Значение
public class Token {
	public final Tag name;
	final String string;

	public Token(Tag n) {
		name = n;
		string=null;
	}
	
	public Token(Tag n, String s) {
		name = n;
		this.string=s;
	}
	
	@Override
	public String toString() {
		return string;
	}
	
	public String toStringWithName() {
		return "'"+name.toString()+"' \""+toString()+"\"";
	}
	
	public static TypedValue createTypedValue(){
		return null;
	}
}
