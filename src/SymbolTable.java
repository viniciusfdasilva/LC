import java.util.HashMap;

/**
 * @author Vinicius Silva
 * @version 1.0
 * @since 2020
 */
public class SymbolTable{
    public HashMap<String,Symbol> symbolTable;

    public final byte FINAL = 0x000001;
    public final byte ELSE = 0x000002;
    public final byte ABRE_PARENTESIS = 0x000003;
    public final byte MENOR_IGUAL = 0x000004;
    public final byte PONTO_VIRGULA = 0x000005;
    public final byte WRITE = 0x000006;
    public final byte INT = 0x000007;
    public final byte AND = 0x000008;
    public final byte FECHA_PARENTESIS = 0x000009;
    public final byte VIRGULA = 0x000010;
    public final byte BEGIN = 0x000011;
    public final byte WRITELN = 0x000012;
    public final byte BYTE = 0x000013;
    public final byte OR = 0x000014;
    public final byte MENOR = 0x000015;
    public final byte PLUS = 0x000016;
    public final byte END_WHILE = 0x000017;
    public final byte TRUE = 0x00018;
    public final byte STRING = 0x000019;
    public final byte NEGACAO = 0x000020;
    public final byte MAIOR = 0x000021;
    public final byte TRACO = 0x000022;
    public final byte END_IF = 0x000023;
    public final byte FALSE = 0x000024;
    public final byte WHILE = 0x000025;
    public final byte ATRIBUICAO = 0x000026;
    public final byte DIFERENTE = 0x000027;
    public final byte ASTERISCO = 0x000028;
    public final byte END_ELSE = 0x000029;
    public final byte BOOLEAN = 0x000030;
    public final byte IF = 0x000031;
    public final byte IGUAL = 0x000032;
    public final byte MAIOR_IGUAL = 0x000033;
    public final byte BARRA = 0x000034;
    public final byte READ_LN = 0x000035;
    public final byte F_EOF = 0x000036;
    public byte proximo;

    public final String PALAVRA_RESERVADA = "reserved_word";
    public static final String CONSTANTE = "const";
    public final String IDENTIFICADOR = "id";
    public static final String EOF = "EOF";
    

    public SymbolTable(){
        symbolTable = new HashMap<>();

        symbolTable.put("final",new Symbol(FINAL,"final","final"));
        symbolTable.put("else",new Symbol(ELSE,"else","else"));
        symbolTable.put("(",new Symbol(ABRE_PARENTESIS,"(","("));
        symbolTable.put("<=",new Symbol(MENOR_IGUAL,"<=","<="));
        symbolTable.put(";",new Symbol(PONTO_VIRGULA,";",";"));
        symbolTable.put("write",new Symbol(WRITE,"write","write"));
        symbolTable.put("int",new Symbol(INT,"int","int"));
        symbolTable.put("&&",new Symbol(AND,"&&","&&"));
        symbolTable.put(")",new Symbol(FECHA_PARENTESIS,")",")"));
        symbolTable.put(",",new Symbol(VIRGULA,",",","));
        symbolTable.put("begin",new Symbol(BEGIN,"begin","begin"));
        symbolTable.put("writeln",new Symbol(WRITELN,"writeln","writeln"));      
        symbolTable.put("byte",new Symbol(BYTE,"byte","byte"));
        symbolTable.put("||",new Symbol(OR,"||","||"));
        symbolTable.put("<",new Symbol(MENOR,"<","<"));
        symbolTable.put("+",new Symbol(PLUS,"+","+"));
        symbolTable.put("endwhile",new Symbol(END_WHILE,"endwhile","endwhile"));
        symbolTable.put("true",new Symbol(TRUE,"true","true"));
        symbolTable.put("string",new Symbol(STRING,"string","string"));
        symbolTable.put("!",new Symbol(NEGACAO,"!","!"));
        symbolTable.put(">",new Symbol(MAIOR,">",">"));
        symbolTable.put("-",new Symbol(TRACO,"-","-"));
        symbolTable.put("endif",new Symbol(END_IF,"endif","endif"));
        symbolTable.put("false",new Symbol(FALSE,"false","false"));
        symbolTable.put("while",new Symbol(WHILE,"while","while"));
        symbolTable.put("<-",new Symbol(ATRIBUICAO,"<-","<-"));
        symbolTable.put("!=",new Symbol(DIFERENTE,"!=","!="));
        symbolTable.put("*",new Symbol(ASTERISCO,"*","*"));
        symbolTable.put("endelse",new Symbol(END_ELSE,"endelse","endelse"));
        symbolTable.put("boolean",new Symbol(BOOLEAN,"boolean","boolean"));
        symbolTable.put("if",new Symbol(IF,"if","if"));
        symbolTable.put("=",new Symbol(IGUAL,"=","="));
        symbolTable.put(">=",new Symbol(MAIOR_IGUAL,">=",">="));
        symbolTable.put("/",new Symbol(BARRA,"/","/"));
        symbolTable.put("readln",new Symbol(READ_LN,"readln","readln"));
        symbolTable.put("EOF",new Symbol(F_EOF,"EOF","EOF"));
        proximo = F_EOF+1;
    }// End SymbolTable()

    public Symbol pesquisa(String lexema){
        return symbolTable.get(lexema) != null ? symbolTable.get(lexema) : null;
    }// End pesquisa()

    public Symbol inserir(String token,String lexema){
        symbolTable.put(lexema,new Symbol(++proximo,lexema,token));
        return symbolTable.get(lexema);
    }// End inserir()
}// End SymbolTable

