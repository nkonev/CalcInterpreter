@echo off

:: [�������� ��������� ������] - [��������]

:: auto_end - �������������� ������ END ; � ����� ��������� ������������������, ����� �� ��������� ��� ������� ��� ������������� �����
:: print_tokens - ����� ��������� ������
:: greedy_func - ������ �������: ������ �� �����������, ��, ��� �������� ����� ����� ������� � �� ������ END ; ��������� ���������� �������.

java -classpath ./bin Executor "set auto_end=false" < "in.txt" > "out.txt" 2> "err.txt"
::java -classpath ./bin Main lexer_print < "in.txt" > "out.txt"
::pause