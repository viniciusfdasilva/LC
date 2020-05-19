import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.nio.charset.*;

/** 
 * =============================================================
 *  Pontifícia Universidade Católica de Minas Gerais - PUCMINAS
 *  Instituto de Ciências Exatas e Informática - ICEI
 *  Departamento de Ciência da Computação - DCC
 * 
 *  Trabalho da disciplina de Compiladores
 *  Professor: Alexei Machado
 *  Aluno componente: Vinicius Francisco da Silva - 576920
 * ==============================================================
 */ 


/**
 * @author Vinicius Silva
 * @version 1.0
 * @since 2020
 */
class Symbol{
    private String lexema;
    private byte endereco;
    private String token;
    private String classe;
    private String tipo;

    public Symbol(byte endereco,String lexema,String token){
        this.endereco = endereco;
        this.lexema = lexema;
        this.token = token;
    }// End Symbol()

    public Symbol(byte endereco,String lexema,String token,String classe,String tipo){
        this.endereco = endereco;
        this.lexema = lexema;
        this.token = token;
        this.classe = classe;
        this.tipo = tipo;
    }// End Symbol()

    public Symbol(String lexema,String token){
        this.lexema = lexema;
        this.token = token;
    }// End Symbol()

    public void setTipo(String tipo){this.tipo = tipo; }
    public String getTipo(){ return this.tipo; }
    public void setClasse(String classe){ this.classe = classe; }
    public String getClasse() { return this.classe;}
    public String getLexema(){return this.lexema;}
    public void setLexema(String lexema){this.lexema = lexema;}
    public byte getEndereco(){return this.endereco;}
    public void setEndereco(byte endereco){this.endereco = endereco;}
    public String getToken(){return this.token;}
    public void setToken(String token){this.token = token;}
}// End Symbol

/**
 * @author Vinicius Silva
 * @version 1.0
 * @since 2020
 */
class Status{
    public Status(String err){
        System.out.print(err);
        System.exit(0);
    }// End Error
}// End Error


/**
 * @author Vinicius Silva
 * @version 1.0
 * @since 2020
 */
class SymbolTable{
    public HashMap<String,Symbol> symbolTable;

    // Endereços de cada símbolo da linguagem
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


    // Tokens da linguagem
    public static final String CONSTANTE = "const";
    public final String IDENTIFICADOR = "id";
    public static final String EOF = "EOF";
    

    public SymbolTable(){
        // Inicialização da tabela com os Simbolos sendo inseridos
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
        symbolTable.put("TRUE",new Symbol(TRUE,"TRUE","TRUE"));
        symbolTable.put("string",new Symbol(STRING,"string","string"));
        symbolTable.put("!",new Symbol(NEGACAO,"!","!"));
        symbolTable.put(">",new Symbol(MAIOR,">",">"));
        symbolTable.put("-",new Symbol(TRACO,"-","-"));
        symbolTable.put("endif",new Symbol(END_IF,"endif","endif"));
        symbolTable.put("FALSE",new Symbol(FALSE,"FALSE","FALSE"));
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
        proximo = F_EOF+1;
    }// End SymbolTable()

    /**
     * Realiza uma pesquisa na tabela. Caso o valor chave
     * passado por parametro estiver na tabela é retornado
     * o Símbolo correspondente a chave, caso contrário é
     * passado uma valor nulo
     * @param lexema
     * @return
     */
    public Symbol pesquisa(String lexema){
        return symbolTable.get(lexema) != null ? symbolTable.get(lexema) : null;
    }// End pesquisa()

    /**
     * Iserção de elementos na tabela de símbolos.
     * O valor correspondente ao token e ao lexema
     * são inseridos.
     * @param token
     * @param lexema
     * @return
     */
    public Symbol inserir(String token,String lexema){
        symbolTable.put(lexema,new Symbol(++proximo,lexema,token));
        return symbolTable.get(lexema);
    }// End inserir()
}// End SymbolTable

/**
 * @author Vinicius Silva
 * @version 1.0
 * @since 2020
 */
class AnalisadorLexico{
    private final int INITIAL_STATE = 0;
    private final int FINAL_STATE = 1;

    // Final de arquivo do buffer
    private final int EOF = -1;

    // Caracteres permitidos no programa fonte
    final String ALLOWED = "[A-Z]|[a-z]|[0-9]|\\_|\\.|\\,|\\;|\\&|\\:" +
                            "|\\(|\\)|\\[|\\]|\\{|\\}|\\+|\\-|\\\"|\\'|" +
                            "\\/|\\!|\\?|\\>|\\<|\\=|\\n|\\t|\\r|\\s|\\*|\\||";  

    private SymbolTable symbolTable;
    private InputStreamReader fonte;
    private BufferedReader bufferedReader;
    private int line;
    private long readskip;
    
    public AnalisadorLexico(FileReader fonte,int line,long readskip){
        this.symbolTable = new SymbolTable();
        this.fonte = fonte;
        this.bufferedReader = new BufferedReader(this.fonte);
        this.line = line;
        this.readskip = readskip;
    }// End AnalisadorLexico()

    public int getLine(){
        return this.line;
    }// End getLine()

    public void setLine(int line){
        this.line = line;
    }// End setLine()

    public long getReadSkip(){
        return this.readskip;
    }// End getReadSkip()

    public void setReadSkip(long skip){
        this.readskip = skip;
    }// End setReadSkip()

    /**
     * Retorna valor true caso o parametro seja avaliado
     * como um lexema ou um caractere válido no programa 
     * fonte
     * @param lex
     * @return isLexeme
     */
    private boolean isLexeme(String lex){
        return lex.matches(ALLOWED);
    }// End isLexeme()

    /**
     * Realiza a identificação dos lexemas e retorna
     * o token correspondente ao parametro. Caso o token
     * seja uma palavra reservada uma busca é feita na tabela
     * de símbolos onde é returnado seu token, caso seja fim de
     * arquivo é criado um Símbolo e retornado, caso constante
     * a mesma coisa, caso contrário sendo um identificador seu
     * lexema é inserido na tabela de símbolos.
     * @param lexema
     * @return
     */
    private Symbol semanticAction(String lexema){
        Symbol token = null;
       
        if(lexema.equals("TRUE")){
            token = this.symbolTable.inserir(SymbolTable.CONSTANTE,lexema);
        }else if(lexema.equals("FALSE")){
            token = this.symbolTable.inserir(SymbolTable.CONSTANTE,lexema);
        }else if(lexema.equals(SymbolTable.EOF)){
            token = new Symbol(SymbolTable.EOF,SymbolTable.EOF);
        }else if(lexema.equals(SymbolTable.CONSTANTE)){
            token = new Symbol(SymbolTable.CONSTANTE,SymbolTable.CONSTANTE);
        }else{
            token = this.symbolTable.pesquisa(lexema);
            if(token == null) token = this.symbolTable.inserir("id",lexema);
        }// End if

        return token;
    }// End semanticAction()

    /**
     * Recebe um buffer de entrada onde lê caractere a caractere
     * por meio de um loop que realiza a leitura até a identificação
     * de um lexema. O loop é iniciado no estado inicial e é repetido
     * até o valor do estado for igual ao estado final. Dentro do loop
     * caso o caractere lido não seja igual ao fim de arquivo será pro-
     * cessado e consumido e concatenado com o lexema até que um delimi-
     * tador lexico seja encontrado. Quando é encontrado o lexema lido até
     * o momento este valor é enviado para a ação semantica, onde poderá ser
     * identificado como: Palavra reservada que é seu próprio valor, Constante
     * e identificador.
     * @return Symbol
     */
    public Symbol analiseLexica(){
        int s = INITIAL_STATE;
        String lex = "";
        String c = "";
        Symbol symbol = null;
        int read;

        try{
            this.bufferedReader.skip(this.readskip); // Ignora os caracteres já lidos anteriormente
            
            while(s != FINAL_STATE){
                if((read = this.bufferedReader.read()) != EOF){
                    char chr = (char)read;
                    c = (chr + "").toLowerCase();
                    this.readskip++; // Contador do número de caracteres lidos
                    if(!isLexeme(c)){
                        new Status(this.line + "\ncaractere invalido.");
                    }// End if
                }else{            
                    if(s != INITIAL_STATE){
                        // Lexemas ainda não processados depois do encontro do fim de arquivo
                        if(this.symbolTable.pesquisa(lex) != null){
                            s = FINAL_STATE;
                            symbol = semanticAction(lex); // PALAVRA RESERVADA
                        }else if(s == 8){
                            s = FINAL_STATE;
                            symbol = semanticAction(lex); // IDENTIFICADOR OU PALAVRA RESERVADA
                        }else{
                            new Status(this.line + "\nfim de arquivo nao esperado.  ");
                        }//End else
                    }else{
                        return semanticAction(SymbolTable.EOF); // EOF
                    }// End else
                }// End else

                switch(s){
                    case INITIAL_STATE : 
                        if(c.matches("\\n")){
                            this.line++; // Icrementação da linha após cada quebra de linha
                        }else if(c.matches("\\/")){
                            lex += c;
                            s = 2;
                        }else if(c.matches("\\\"")){
                            lex += c;
                            s = 18;
                        }else if(c.matches("\\&")){
                            lex += c;
                            s = 19;
                        }else if(c.matches("\\|")){
                            lex += c;
                            s = 20;
                        }else if(c.matches("\\(|\\;|\\)|\\,|\\+|\\*|\\=|\\-")){
                            lex += c;
                            s = FINAL_STATE;
                            symbol = semanticAction(lex); // PALAVRA RESERVADA
                        }else if(c.matches("<")){
                            lex += c;
                            s = 5;
                        }else if(c.matches(">")){
                            lex += c;
                            s = 6;
                        }else if(c.matches("!")){
                            lex += c;
                            s = 7;
                        }else if(c.matches("[1-9]")){
                            lex += c;
                            s = 11;
                        }else if(c.matches("0")){
                            lex += c;
                            s = 9;
                        }else if(c.matches("[a-z]|[A-Z]|_")){
                            lex += c;
                             s = 8;                        
                        }else if(c.matches("[\\s]|[\\t]|[\\r]")){
                            s = INITIAL_STATE;
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + c + "]."); 
                        }// End if
                    break;

                    case 2 : 
                        if(c.matches("\\*")){;
                            lex = ""; // Comentario ignorado
                            s = 3;
                        }else{
                            s = FINAL_STATE;
                            this.readskip--; // Volta ao caractere lido no instante para ser consumido novamente
                            symbol = semanticAction(lex); // PALAVRA RESERVADA [ * ]
                        }// End else
                    break;

                    case 3 : 
                        if(c.matches("\\n")){
                            this.line++; // Contando também as linhas de comentários
                            s = 3;
                        }else if(c.matches("\\*")){
                            s = 4;
                        }else{
                            s = 3;
                        }// End else
                    break;

                    case 4 : 
                        if(c.matches("/")){
                            s = INITIAL_STATE;
                        }else if(c.matches("\\*")){
                            s = 4;
                        }else{
                            s = 3;
                        }// End else
                    break;

                    case 5 : 
                        if(c.matches("[-]|[=]")){
                            lex += c;
                            s = FINAL_STATE;
                            symbol = semanticAction(lex); // PALAVRA RESERVADA [ <- | <= ]
                        }else{
                            s = FINAL_STATE;
                            this.readskip--; // Volta ao caractere lido no instante para ser consumido novamente
                            symbol = semanticAction(lex); // PALAVRA RESERVADA [ < ]
                        }// End else
                    break;
                            
                    case 6 :
                        if(c.matches("=")){
                            lex += c;
                            s = FINAL_STATE;
                            symbol = semanticAction(lex); // PALAVRA RESERVADA [ >= ]
                        }else{
                            s = FINAL_STATE;
                            this.readskip--; // Volta ao caractere lido no instante para ser consumido novamente
                            symbol = semanticAction(lex); // PALAVRA RESERVADA [ > ]
                        }// End else
                    break;

                    case 7 : 
                        if(c.matches("=")){
                            lex += c;
                            s = FINAL_STATE;
                            symbol = semanticAction(lex); // PALAVRA RESERVADA [ != ]
                        }else{
                            s = FINAL_STATE;
                            this.readskip--; // Volta ao caractere lido no instante para ser consumido novamente
                            symbol = semanticAction(lex); // PALAVRA RESERVADA [ ! ]
                        }// End else
                    break;

                    case 8 :
                        if(c.matches("[a-z]|[A-Z]|[0-9]|_")){
                            lex += c;
                            s = 8;
                        }else{
                            s = FINAL_STATE;
                            this.readskip--; // Volta ao caractere lido no instante para ser consumido novamente
                            symbol = semanticAction(lex); // IDENTIFICADOR OU PALAVRA RESERVADA
                        }// End else
                    break;

                    case 9 :
                        if(c.matches("x")){
                            lex += c;
                            s = 10;
                        }else if(c.matches("[0-9]")){
                            lex += c;
                            s = 11;
                        }else{
                            s = FINAL_STATE;
                            this.readskip--; // Volta ao caractere lido no instante para ser consumido novamente
                            symbol = semanticAction(SymbolTable.CONSTANTE); // CONSTANTE [ 0 ]
                        }// End else 
                   break;

                    case 10 :
                        if(c.matches("[A-F]|[0-9]")){
                            lex += c;
                            s = 12;
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + lex + "]."); 
                        }//End else
                    break;

                    case 11 :
                        if(c.matches("[0-9]")){
                            lex += c;
                            s = 11;
                        }else{
                            s = FINAL_STATE;
                             this.readskip--; // Volta ao caractere lido no instante para ser consumido novamente
                             symbol = semanticAction(SymbolTable.CONSTANTE); // CONSTANTE
                        }// End else
                    break;

                    case 12 :
                        if(c.matches("[A-F]|[0-9]")){
                            lex += c;
                            s = FINAL_STATE;
                            symbol = semanticAction(SymbolTable.CONSTANTE); // CONSTANTE [ 0xDD ]
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + lex + "]."); 
                        }// End else
                    break;

                                       
                    case 18 :
                        if(c.matches("\\\"")){
                            lex += c;
                            s = FINAL_STATE;
                            symbol = semanticAction(SymbolTable.CONSTANTE); // CONSTANTE [ String ]
                        }else if(!c.matches("\\n") && c.matches(ALLOWED)){
                            lex += c;
                            s = 18;
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + lex + "]."); 
                        }// End else
                    break;

                    case 19 :
                        if(c.matches("\\&")){
                            lex += c;
                            s = FINAL_STATE;
                            symbol = semanticAction(lex); // PALAVRA RESERVADA [ && ]
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + lex +"].");
                        }// End else
                    break;

                    case 20 :
                        if(c.matches("\\|")){
                            lex += c;
                            s = FINAL_STATE;
                            symbol = semanticAction(lex); // PALAVRA RESERVADA [ || ]
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + lex+c + "].");
                        }// End else
                    break;
                }// End switch
            }// End while
        }catch(IOException e){
            e.printStackTrace();
        }// End catch 
        return symbol;
    }// End analiseLexica()
}// End AnalidadorLexico

/**
 * @author Vinicius Silva
 * @version 1.0
 * @since 2020
 */
class AnalisadorSintatico{
    private String token;
    private String lexema;

    private long readSkip;
    private int line;
    private String filename;

    public AnalisadorSintatico(String filename,long read,int line) throws IOException{
        this.readSkip = read;
        this.filename = filename;
        this.line = line;
    }// End AnalisadorSintatico()

    public long getReadSkip(){
        return this.readSkip;
    }// End getReadSkip()

    public int getLine(){
        return this.line;
    }// End getLine()

    public String getLexema(){
        return this.lexema;
    }// End getLexema()

    public void setLexema(String lexema){
        this.lexema = lexema;
    }// End setLexema()
    
    public String getToken(){
        return this.token;
    }// End getToken()

    public void setToken(String token){
        this.token = token;
    }// End setToken()

    /**
     * Este método compara dois valores, o token corrente
     * lido na última chamada do analisador léxico e o token
     * que é esperado. Caso o token corrente seja igual ao token
     * esperado o analisador léxico é chamado para ler o próximo
     * token, caso contrário caso o token seja igual ao fim de ar-
     * quivo (EOF) é gerado um erro de fim de arquivo não esperado
     * caso não seja um erro é gerado pelo token corrente não ter
     * sido igual ao token esperado.
     * @param tok_esp
     */
    public void casaToken(String tok_esp){
        Symbol s = null;

        try{
            AnalisadorLexico analisadorLexico = new AnalisadorLexico(new FileReader(this.filename),line,readSkip);
            
            if(this.token.equals(tok_esp)){            
                s = analisadorLexico.analiseLexica();
                this.readSkip = analisadorLexico.getReadSkip(); // Atualiza o valor de caracteres ignorados
                this.line = analisadorLexico.getLine(); // Atualiza o valor das linhas lidas
                this.lexema = s.getLexema();
                this.token = s.getToken(); // Próximo token
            }else{
                if(this.token.equals("EOF")){
                    new Status(this.line + "\nfim de arquivo nao esperado."); 
                }else{
                    new Status(this.line + "\ntoken nao esperado ["+this.lexema+"].");
                }// End else
            }// End else
        }catch(IOException e){
            e.printStackTrace();
        }// End catch
    }// End casaToken()

    public void procedure_S(){
        while(token.equals("int") || token.equals("boolean") || token.equals("byte") || token.equals("string") || token.equals("final")){
            procedure_Declarar();
        }// End while

        while(token.equals("id")||token.equals("while")||token.equals("if")||token.equals(";")||token.equals("readln")||token.equals("write") || token.equals("writeln")){
            procedure_Comando();
        }// End while

        casaToken("EOF");
    }// End procedure_S()x

    public void procedure_Declarar(){
        if(this.token.equals("final")){
            casaToken("final");
            casaToken("id");
            casaToken("<-");
            if(this.token.equals("-")) casaToken("-");
            casaToken("const");
            casaToken(";");
        }else{
            if(this.token.equals("int")){
                casaToken("int");      
            }else if(this.token.equals("boolean")){
                casaToken("boolean");
            }else if(this.token.equals("byte")){
                casaToken("byte");
            }else{
                casaToken("string");
            }// End else if
            casaToken("id");
            procedure_ListaIds();
            casaToken(";");
        }// End else
    }// End procedure_Declarar()

    public void procedure_ListaIds(){
        if(this.token.equals("<-")){
            procedure_Atrib();
        }// End if

        while(this.token.equals(",")){
            casaToken(",");
            casaToken("id");
            if(this.token.equals("<-")){
                procedure_Atrib();
            }// End if
        }// End while
    }// End procedure_ListaIds()

    public void procedure_Atrib(){
        casaToken("<-");
        if(this.token.equals("-")) casaToken("-");
        casaToken("const");
    }// End procedure_Atrib()

    public void procedure_Comando(){
        if(this.token.equals("id")){
            procedure_Atribuicao();
        }else if(this.token.equals("while")){
            procedure_Repeticao();
        }else if(this.token.equals("if")){
            procedure_Teste();
        }else if(this.token.equals(";")){
            procedure_Nulo();
        }else if(this.token.equals("readln")){
            procedure_Leitura();
        }else{
            procedure_Escrita();
        }// End else if
    }// End procedure_Comando()

    public void procedure_Atribuicao(){
        casaToken("id");
        casaToken("<-");
        procedure_Expressao();
        casaToken(";");
    }// End procedure_Atribuicao()

    public void procedure_Repeticao(){
        casaToken("while");
        casaToken("(");
        procedure_Expressao();
        casaToken(")");
        procedure_Blocowhile();
    }// End procedure_Repeticao()

    public void procedure_Blocowhile(){
        if(this.token.equals("begin")){
            casaToken("begin");
            while(token.equals("id")||token.equals("while")||token.equals("if")||token.equals(";")||token.equals("readln")||token.equals("write")||token.equals("writeln")){
                procedure_Comando();
            }// End while
            casaToken("endwhile");
        }else{
            procedure_Comando();
        }// End else
    }// End procedure_Blocowhile()

    public void procedure_Teste(){
        casaToken("if");
        casaToken("(");
        procedure_Expressao();
        casaToken(")");
        if(this.token.equals("begin")){
            procedure_Blocoif();
        }else{
            procedure_Comando();
            if(this.token.equals("else")){
                procedure_Blocoelse();
            }// End if
        }// End else
    }// End procedure_Teste()

    public void procedure_Blocoif(){
        casaToken("begin");
        procedure_Comando();
        while(token.equals("id")||token.equals("while")||token.equals("if")||token.equals(";")||token.equals("readln")||token.equals("write")||token.equals("writeln")){
            procedure_Comando();
        }// End while
        casaToken("endif");
        casaToken("else");
        casaToken("begin");
        procedure_Comando();
        while(token.equals("id")||token.equals("while")||token.equals("if")||token.equals(";")||token.equals("readln")||token.equals("write")||token.equals("writeln")){
            procedure_Comando();
        }// End while
        casaToken("endelse");
    }// End procedure_Blocoif()

    public void procedure_Blocoelse(){
        casaToken("else");
        procedure_Comando();
    }// End procedure_Blocoelse()

    public void procedure_Nulo(){
        casaToken(";");
    }// End procedure_Nulo()

    public void procedure_Leitura(){
        casaToken("readln");
        casaToken("(");
        casaToken("id");
        casaToken(")");
        casaToken(";");
    }// End procedure_Leitura()

    public void procedure_Escrita(){
        if(this.token.equals("write")){
            casaToken("write");
            casaToken("(");
            procedure_ListaExpressoes();
            casaToken(")");
            casaToken(";");
        }else{
            casaToken("writeln");
            casaToken("(");
            procedure_ListaExpressoes();
            casaToken(")");
            casaToken(";");
        }// End else if
    }// End procedure_Escrita()

    public void procedure_ListaExpressoes(){
        procedure_Expressao();

        while(this.token.equals(",")){
            casaToken(",");
            procedure_Expressao();
        }// End while
    }// End procedure_ListaExpressoes()

    public void procedure_Expressao(){
        procedure_Exp();
        if(this.token.equals("=") || this.token.equals("!=") || this.token.equals("<") || this.token.equals(">") || this.token.equals("<=") || this.token.equals(">=")){
            procedure_E();
        }// End if
    }// End procedure_Expressao()

    public void procedure_E(){
        if(token.equals("=")){
            casaToken("=");
        }else if(token.equals("!=")){
            casaToken("!=");
        }else if(token.equals("<")){
            casaToken("<"); 
        }else if(token.equals(">")){
            casaToken(">");
        }else if(token.equals("<=")){
            casaToken("<=");
        }else{
            casaToken(">=");
        }// End else if
        procedure_Exp();
    }// End procedure_E()

    public void procedure_Exp(){
        if(this.token.equals("-")){
            casaToken("-");
        }else if(this.token.equals("+")){
            casaToken("+");
        }// End else
        procedure_T();
        while(this.token.equals("+") || this.token.equals("-") || this.token.equals("||")){
            procedure_D();
        }// End while
    }// End procedure_Exp()

    public void procedure_D(){
        if(this.token.equals("+")){
            casaToken("+");
        }else if(this.token.equals("-")){
            casaToken("-");
        }else{
            casaToken("||");
        }// End else if
        procedure_T();
    }// End procedure_D()

    public void procedure_T(){
        procedure_F();
        while(this.token.equals("*") || this.token.equals("&&") || this.token.equals("/")){
            procedure_precedenciaC();
        }// End while
    }// End procedure_T()

    public void procedure_precedenciaC(){
        if(this.token.equals("*")){
            casaToken("*");
        }else if(this.token.equals("&&")){
            casaToken("&&");
        }else{
            casaToken("/");
        }// End else if
        procedure_F();
    }// End procedure_precedenciaC()

    public void procedure_F(){
        if(this.token.equals("!")){
            casaToken("!");
            procedure_F();
        }else if(this.token.equals("(")){
            casaToken("(");
            procedure_Expressao();
            casaToken(")");
        }else if(this.token.equals("id")){
            casaToken("id");
        }else if(this.token.equals("-")){
            casaToken("-");
            casaToken("const");
        }else{
            casaToken("const");
        }// End else if
    }// End procedure_F()
}// End AnalisadorSintatico()

/**
 * @author Vinicius Silva
 * @version 1.0
 * @since 2020
 */
public class LC{
    public static final String FILE_NAME = "source";

    private static AnalisadorLexico analisadorLexico;
    private static AnalisadorSintatico analisadorSintatico;
    private static long readskip = 0;
    private static int line = 1;

    /**
     * Método principal.
     * Inicia o programa lendo da entrada os valores e
     * inserindo em um buffer. Posteriormente inicializa
     * e chama o analisador léxico para leitura do primeiro
     * lexema. Inicializa e chama o analisador sintático, passa
     * para ele o primeiro token lido e por fim chama a regra ini
     * cial da gramática para o processo da analise sintática também
     * ser iniciado junto a analise léxica.
     */
    public static void main(String[] args)throws IOException{
        Symbol lexeme = null;
        getBuffer(); // Recebe e joga a entrada em um buffer

        try{                                 
            analisadorLexico = new AnalisadorLexico(new FileReader(FILE_NAME),line,readskip);                          
            lexeme = analisadorLexico.analiseLexica();
            setValues(); // Seta a quantidade de caracteres a serem pulados e o valor da linha
            analisadorSintatico = new AnalisadorSintatico(FILE_NAME,readskip,line);
            analisadorSintatico.setToken(lexeme.getToken());
            analisadorSintatico.setLexema(lexeme.getLexema());
            analisadorSintatico.procedure_S();  
            line = analisadorSintatico.getLine(); // Valor atualizado da quantidade de linhas compiladas
            new Status(line + " linhas compiladas.");          
        }catch(IOException e){
            e.printStackTrace();
        }// End catch
    }// End main()

    public static void setValues(){
        line = analisadorLexico.getLine();
        readskip = analisadorLexico.getReadSkip();
    }// End setValues

    public static void getBuffer() throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in,Charset.forName("ISO-8859-1")));
        int i;
        FileWriter fileWriter = new FileWriter(FILE_NAME);
        while((i = bufferedReader.read()) != -1){
            fileWriter.write(""+(char)i);
        }//End while
        fileWriter.close();
    }// End getBuffer()
}// End LC