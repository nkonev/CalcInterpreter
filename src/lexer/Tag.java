package lexer;

/**
 * Перечисление названий всех возможных терминалов
 * */

public enum Tag {
	DOUBLE, TRUE, FALSE, 
	NAME, IF, ELSE,
	ARGS_AUTO_END, AUTO_END, PRINT_TOKENS,
	TABLE, PRECISION, ERRORS, STRICTED, AUTO_PRINT, GREEDY_FUNC,
	ASSIGN, PLUS, MINUS, MUL, DIV, POW, FACTORIAL,
	LP, RP, LF, RF,
	SKIPABLE, L_COMMENT, R_COMMENT,
	END,
	EXIT, 
	COS, SIN, LOG,
	PRINT, ADD, DEL, RESET, SET, HELP, STATE,
 	 	
	ILLEGAL_TOKEN, BOOLEAN, INTEGER;
}
