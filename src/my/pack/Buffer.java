package my.pack;
/* Буфер между лексером и парсером.
 * Нужен для соединения между собой
 * лексера, обрабатывающего строку и выдающего список токенов
 * и парсера, требующего один токен.
 * */

import java.io.BufferedReader;
import java.util.ArrayList;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

public class Buffer {
	private ArrayList<Token> tokens; // Массив токенов <название, значение>
	private Lexer lexer;
	private String[] args;
	private Options options = null;
	
	// Конструктор
	public Buffer(Lexer lexer, String[] args, BufferedReader stdin, Options options){
		tokens = new ArrayList<Token>();
		this.stdin = stdin; // stdin=null используется при тестировании: сначала вызываем Lexer::scan("тестируемая строка"), затем Parser::exprList
		this.lexer=lexer;
		this.args=args;
		this.options=options;
	}
	
	private long lineNum = 0;
	private BufferedReader stdin=null;
	private int tokNum=0;
	private String str;
	private int numAgrs=0;
	private enum NowProcessed{NOTHING, ARGS, STDIN};
	private NowProcessed now=NowProcessed.NOTHING;
	
	// Главметод, гарантированно возвращает токен (в том числе Tokens.EXIT при невозможности дальнейшего считывания)
	public Token getToken() throws Exception {
		// Если все токены из списка уже были получены, либо список пуст 
		if(tokNum==tokens.size()){
			tokens.clear();
			tokNum=0;// Для нормальной работы ^
						
			str = null;
			
			do{
				if(numAgrs<args.length) now=NowProcessed.ARGS; // Если args - не пустой массив
				else if(stdin!=null) now=NowProcessed.STDIN; // если есть поток ввода и мы уже обработали все args
				else now=NowProcessed.NOTHING; // когда исчерпали все токены в args и нельзя читать из stdin, то возвращаем EXIT
				
				switch(now){
				case ARGS:
					str = args[numAgrs];
					break;
				case STDIN:
					str = stdin.readLine(); // Считываем строку..., null когда строки закончились
					if(str==null) return new Token(Tag.EXIT);
					lineNum++;
					break;
				case NOTHING: 
					return new Token(Tag.EXIT);
				}
				
				if(!str.isEmpty()) {
					lexer.scan(str, tokens);
					
					// autoending :)
					switch(now){
					case ARGS:
						if(options.getBoolean(OptId.ARGS_AUTO_END)) tokens.add(new Token(Tag.END)); // Автодобавление токена END
						break;
					case STDIN:
						if(options.getBoolean(OptId.AUTO_END)) tokens.add(new Token(Tag.END)); // Автодобавление токена END
						break;
					default:
						break;
					}
					
					if(options.getBoolean(OptId.PRINT_TOKENS)) printTokens();
				}
				
				if(numAgrs<args.length) numAgrs++;
			}while(tokens.isEmpty());
		}
		
		// Возвращаем очередной токен
		return tokens.get(tokNum++);
	}
	
	// Вывод найденных токенов
	public void printTokens() {
		System.out.print("lexer at line "+ lineNum+" ");
		if(!tokens.isEmpty()){
			System.out.println("\""+str+"\" found next tokens:");
			//System.out.println("<name> <value>\n");
			for (int i =0; i < tokens.size(); i++) {
				Token t = tokens.get(i);
				//System.out.println(""+i+ " " + t.name + " " + t.value);
				System.out.println(t);
			}
		}else
			System.out.println("Nothing found for \""+ str+"\".");
		//System.out.println();
	}
	
	public String getLineNum() {
		switch(now){
		case ARGS:
			return "входном параметре №"+numAgrs;
		case STDIN:
			return "строке №"+lineNum;
		default:
			return "нигде, numAgrs=" + numAgrs + ", lineNum=" + lineNum;
		}
	}
	
	public int getTokNum() {
		return tokNum-1; // т. к . в getToken() используется ++
	}
	
	// Для тестов
	public void setArgs(String[] args){
		this.args = args;
	}
}