/* 
 * Начало: Лексер-Java:			lab-2-tokens-2013-03-30--03-08.zip
 * Начало: Парсер&Лексер-C++: 	calculatorsStroustroup-2013-04-29--16-54.zip
 * Начало: Парсер-Java: 		CalcInterpreter-java-2013-05-10--00-18-relese.zip
 * */

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws Exception {
    	System.out.println("Добро пожаловать в интерпретатор.");
    	BufferedReader stdin = null;
		stdin = new BufferedReader(new InputStreamReader(System.in));
		
		Options o = new Options();
	    Lexer l = new Lexer();
	    Buffer b = new Buffer(l,  args, stdin,  o.getBoolean(Id.LEXER_AUTO_END), o.getBoolean(Id.LEXER_PRINT_TOKENS));
	    Parser p = new Parser(b, o.getBoolean(Id.AUTO_PRINT), o.getBoolean(Id.GREEDY_FUNC));
	    p.reset(Parser.what.ALL);
	    
	    while(true){
		    try{
			    p.exprList();
			   	if (p.getCurrTok().name==Names.RF) Parser.error("Неправильный выход из expr_list, из-за лишней RF }");
			   	if (p.getCurrTok().name==Names.EXIT) break;
		    }catch(MyException m){
		    	System.err.println("Ошибка на строке №" + b.getLineNum() + " на токене №" + b.getTokNum() + " "+p.getCurrTok() + ":");
		    	System.err.println(m.getMessage() + "\n");
		    	continue;
		    }catch(Exception e){
		    	System.err.println("Критическая ошибка на строке "+b.getLineNum()+", продолжение работы невозможно.");
		    	System.err.println(e.getMessage() + "\n");
		    	e.printStackTrace();
		    	break;//while
		    }
	    }//while
	    System.out.println("Выход...");
    }
}