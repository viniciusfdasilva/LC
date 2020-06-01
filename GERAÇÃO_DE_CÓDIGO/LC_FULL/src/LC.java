import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
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
        this.classe = "";
        this.tipo = "";
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
        this.classe = "";
        this.tipo = "";
    }// End Symbol()

    public void setTipo(String tipo){this.tipo = tipo;}
    public String getTipo(){ return this.tipo; }
    public void setClasse(String classe){ this.classe = classe;}
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
        symbolTable = new HashMap<>(0);

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

    public void print(){
        
    }
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

    public SymbolTable getSymbolTable(){
        return this.symbolTable;
    }// End getSymbolTable()

    public void setSymbolTable(SymbolTable symbolTable){
        this.symbolTable = symbolTable;
    }// End setSymbolTable()

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
    private Symbol semanticAction(String tok, String lexema){
        Symbol token = null;
       
        if(lexema.equals("true") || lexema.equals("false")){
            lexema = lexema.equals("true") ? "FFh" : "0h";
            token = this.symbolTable.inserir(SymbolTable.CONSTANTE,lexema);
            token.setTipo("tipo-lógico");
        }else if(lexema.equals(SymbolTable.EOF)){
            token = new Symbol(SymbolTable.EOF,SymbolTable.EOF);
        }else if(tok.equals(SymbolTable.CONSTANTE)){
            token = new Symbol(lexema,SymbolTable.CONSTANTE);
            if(token.getLexema().charAt(0) == '"' && token.getLexema().charAt(token.getLexema().length()-1) == '"'){
                token.setTipo("tipo-string");
            }// End if
        }else{
            token = this.symbolTable.pesquisa(lexema);
            if(token == null){
                token = this.symbolTable.inserir("id",lexema);
            }// End if
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
                            symbol = semanticAction(lex,lex); // PALAVRA RESERVADA
                        }else if(s == 8){
                            s = FINAL_STATE;
                            symbol = semanticAction(lex,lex); // IDENTIFICADOR OU PALAVRA RESERVADA
                        }else{
                            new Status(this.line + "\nfim de arquivo nao esperado.  ");
                        }//End else
                    }else{
                        return semanticAction(SymbolTable.EOF,SymbolTable.EOF); // EOF
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
                            symbol = semanticAction(lex,lex); // PALAVRA RESERVADA
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
                            symbol = semanticAction(lex,lex); // PALAVRA RESERVADA [ * ]
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
                            symbol = semanticAction(lex,lex); // PALAVRA RESERVADA [ <- | <= ]
                        }else{
                            s = FINAL_STATE;
                            this.readskip--; // Volta ao caractere lido no instante para ser consumido novamente
                            symbol = semanticAction(lex,lex); // PALAVRA RESERVADA [ < ]
                        }// End else
                    break;
                            
                    case 6 :
                        if(c.matches("=")){
                            lex += c;
                            s = FINAL_STATE;
                            symbol = semanticAction(lex,lex); // PALAVRA RESERVADA [ >= ]
                        }else{
                            s = FINAL_STATE;
                            this.readskip--; // Volta ao caractere lido no instante para ser consumido novamente
                            symbol = semanticAction(lex,lex); // PALAVRA RESERVADA [ > ]
                        }// End else
                    break;

                    case 7 : 
                        if(c.matches("=")){
                            lex += c;
                            s = FINAL_STATE;
                            symbol = semanticAction(lex,lex); // PALAVRA RESERVADA [ != ]
                        }else{
                            s = FINAL_STATE;
                            this.readskip--; // Volta ao caractere lido no instante para ser consumido novamente
                            symbol = semanticAction(lex,lex); // PALAVRA RESERVADA [ ! ]
                        }// End else
                    break;

                    case 8 :
                        if(c.matches("[a-z]|[A-Z]|[0-9]|_")){
                            lex += c;
                            s = 8;
                        }else{
                            s = FINAL_STATE;
                            this.readskip--; // Volta ao caractere lido no instante para ser consumido novamente
                            symbol = semanticAction(lex,lex); // IDENTIFICADOR OU PALAVRA RESERVADA
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
                            symbol = semanticAction(SymbolTable.CONSTANTE,lex); // CONSTANTE [ 0 ]
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
                             symbol = semanticAction(SymbolTable.CONSTANTE,lex); // CONSTANTE
                        }// End else
                    break;

                    case 12 :
                        if(c.matches("[A-F]|[0-9]")){
                            lex += c;
                            s = FINAL_STATE;
                            symbol = semanticAction(SymbolTable.CONSTANTE,lex); // CONSTANTE [ 0xDD ]
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + lex + "]."); 
                        }// End else
                    break;

                                       
                    case 18 :
                        if(c.matches("\\\"")){
                            lex += c;
                            s = FINAL_STATE;
                            symbol = semanticAction(SymbolTable.CONSTANTE,lex); // CONSTANTE [ String ]
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
                            symbol = semanticAction(lex,lex); // PALAVRA RESERVADA [ && ]
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + lex +"].");
                        }// End else
                    break;

                    case 20 :
                        if(c.matches("\\|")){
                            lex += c;
                            s = FINAL_STATE;
                            symbol = semanticAction(lex,lex); // PALAVRA RESERVADA [ || ]
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
    private Symbol symbol;
    private Assembly assembly;
    private GerenciadorMemoria gerenciadorMemoria;
    private Rotulo rotulo;

    // Atributos das Regras de derivação
    private boolean Declarar_isnegativo;
    private String Declarar_tipo;
    private String Exp_tipo;
    private String Expressao_tipo;
    private String T_tipo;
    private boolean Atrib_isnegativo;
    private boolean F_isnegativo;
    private String F_tipo;


    private byte F_end;
    private byte T_end;
    private byte Exp_end;
    private byte Expressao_end;
    private String T_op;
    private String Exp_op;
    private String Expressao_op;

    private byte end;

    private long readSkip;
    private int line;
    private String filename;
    SymbolTable ts;

    int memoria_count;

    public AnalisadorSintatico(String filename,long read,int line,SymbolTable ts) throws IOException{
        this.readSkip = read;
        this.filename = filename;
        this.line = line;
        this.symbol = null;
        this.ts = ts;
        this.memoria_count = 0;
        
        this.assembly = new Assembly();
        this.gerenciadorMemoria = new GerenciadorMemoria();
        this.rotulo = new Rotulo();
        this.end = this.gerenciadorMemoria.contador;
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
            analisadorLexico.setSymbolTable(this.ts);
            if(this.token.equals(tok_esp)){            
                s = analisadorLexico.analiseLexica();
                this.readSkip = analisadorLexico.getReadSkip(); // Atualiza o valor de caracteres ignorados
                this.line = analisadorLexico.getLine(); // Atualiza o valor das linhas lidas
                this.symbol = s;
                this.ts = analisadorLexico.getSymbolTable();
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

        try{
            assembly.getAssembly();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(0);
        }// End catch
    }// End procedure_S()x

    public void procedure_Declarar(){
        if(this.token.equals("final")){
            casaToken("final");

            Symbol id_aux = this.symbol;
            
            casaToken("id");

            // RULE [1] {se id.classe != NULO entao ERRO senao id.classe = classe-const}
            if(!id_aux.getClasse().equals("")){
                new Status(this.line + "\nidentificador ja declarado ["+ id_aux.getLexema() +"].");
            }else{
                id_aux.setClasse("classe-const");
            }// End else

            casaToken("<-");
            if(this.token.equals("-")){
                // RULE [11] {Declarar.isnegativo = true}
                this.Declarar_isnegativo = true; 
                
                casaToken("-");
            }// End if

            Symbol const_aux = this.symbol;
            casaToken("const");
            
            //[15] {se Declarar.isnegativo = true entao const.tipo = tipo-inteiro}
            if(Declarar_isnegativo){
                const_aux.setTipo("tipo-inteiro");
            }// End if

            /**
             * RULE [21] {se const.tipo != tipo-string e const.tipo != tipo-lógico e 
             * const.tipo != NULO e const.lexema >= 0 e const.lexema <= 255 entao 
             * const.tipo = tipo-byte else const.tipo = tipo-inteiro}
             */
            String tipo = const_aux.getTipo();

            if(!tipo.equals("tipo-string") && !tipo.equals("tipo-lógico") && tipo.equals("")){
                int constant;
                if(const_aux.getLexema().contains("0x")){
                    constant = Integer.decode(const_aux.getLexema());
                }else{
                    constant = Integer.parseInt(const_aux.getLexema());
                }// End else
                if(constant >= 0 && constant <= 255){
                    const_aux.setTipo("tipo-byte");
                }else{
                    const_aux.setTipo("tipo-inteiro");
                }// End else
            }// End if

            // RULE [22] {se id.tipo == NULO entao id.tipo = const.tipo senaose id.tipo != const.tipo entao ERRO}
            id_aux.setTipo(const_aux.getTipo());

            /**
             * ASSEMBLY [1] {se const.tipo = tipo-inteiro assembly.getDeclaracoes().put("sword const.lex");
             *               senao se const.tipo = tipo-string assembly.getDeclaracoes().put("byte const.lex+$");
             *               senao se const.tipo = byte assembly.getDeclaracoes().put("byte const.lex"); 
             *              senao se const.tipo = tipo-lógico assembly.getDeclaracoes().put("sword const.lex");}
             */
            if(const_aux.getTipo().equals("tipo-inteiro")){
                end = gerenciadorMemoria.newEndInt();
                this.assembly.getDeclaracao().add("sword "+const_aux.getLexema()+"; int em "+end);
            }else if(const_aux.getTipo().equals("tipo-string")){
                const_aux.setLexema(const_aux.getLexema().replace("\"",""));
                end = gerenciadorMemoria.newEndString(const_aux.getLexema().length());
                this.assembly.getDeclaracao().add("byte "+const_aux.getLexema()+"$"+" ; string em "+end);
            }else if(const_aux.getTipo().equals("tipo-byte")){
                end = gerenciadorMemoria.newEndByte();
                this.assembly.getDeclaracao().add("byte "+const_aux.getLexema()+" ; byte em "+end);
            }else{
                end = gerenciadorMemoria.newEndBoolean();
                this.assembly.getDeclaracao().add("byte "+const_aux.getLexema()+" ; boolean em "+end);
            }// End else

            id_aux.setEndereco(end);
            casaToken(";");
        }else{
            if(this.token.equals("int")){
                // RULE [5] {Declarar.tipo = tipo-inteiro}
                this.Declarar_tipo = "tipo-inteiro";

                casaToken("int");      
            }else if(this.token.equals("boolean")){
                // RULE [6] {Declarar.tipo = tipo-lógico}
                this.Declarar_tipo = "tipo-lógico";

                casaToken("boolean");
            }else if(this.token.equals("byte")){
                // RULE [7] {Declarar.tipo = tipo-byte}
                this.Declarar_tipo = "tipo-byte";

                casaToken("byte");
            }else{
                // RULE [8] {Declarar.tipo = tipo-string}
                this.Declarar_tipo = "tipo-string";

                casaToken("string");
            }// End else if

            Symbol id_aux = this.symbol;
            casaToken("id");

            // RULE [2] {se id.classe != NULO entao ERRO senao id.classe = classe-var}
            if(!id_aux.getClasse().equals("")){
                new Status(this.line + "\nidentificador ja declarado ["+ id_aux.getLexema() +"].");
            }else{
                id_aux.setClasse("classe-var");
            }// End else

            // RULE [10] {id.tipo = Declarar.tipo}
            id_aux.setTipo(Declarar_tipo);


            // RULE [18] {Listaids.tipo = id.tipo}
            procedure_ListaIds(id_aux);
            casaToken(";");
        }// End else
    }// End procedure_Declarar()

    public void procedure_ListaIds(Symbol id){
        String Listaids_tipo = id.getTipo();
        String lex = id.getLexema();
        String Listaids_classe = id.getClasse();

        boolean ListaIds_isatribuicao = false;
        if(this.token.equals("<-")){
            if(Listaids_classe.equals("classe-const")){
                new Status(this.line + "\nclasse de identificador incompativel ["+ lex +"].");
            }// End if

            // RULE [19] {Atrib.tipo = Listaids.tipo}
            procedure_Atrib(id);

            // ASSEMBLY [2] {ListaIds.isatribuicao = true}
            ListaIds_isatribuicao = true;
        }// End if

        /**
         * ASSEMBLY [3] {se ListaIds.isatribuicao = false se ListaIds.tipo = tipo-inteiro 
	            assembly.getDeclaracoes().put("sword ?"); senao se ListsIds.tipo = tipo-byte
	            assembly.getDeclaracoes().put("byte ?"); senao se ListaIds.tipo = tipo-string
	            assembly.getDeclaracoes().put("byte ?"); senao se Lista.tipo = tipo-lógico
	            assembly.getDeclaracoes().put("byte ? ");}
         */
        if(!ListaIds_isatribuicao){
            if(Listaids_tipo.equals("tipo-inteiro")){
                end = gerenciadorMemoria.newEndInt();
                this.assembly.getDeclaracao().add("sword ?");
            }else if(Listaids_tipo.equals("tipo-byte")){
                end = gerenciadorMemoria.newEndByte();
                this.assembly.getDeclaracao().add("byte ?");
            }else if(Listaids_tipo.equals("tipo-string")){
                end = gerenciadorMemoria.newEndString();
                this.assembly.getDeclaracao().add("byte ?");
            }else{
                end = gerenciadorMemoria.newEndBoolean();
                this.assembly.getDeclaracao().add("byte ?");
            }// End else
        }// End if

        id.setEndereco(end);
        ListaIds_isatribuicao = false;

        while(this.token.equals(",")){
            casaToken(",");
            Symbol id_aux = this.symbol;
            casaToken("id");
            
            // RULE [2] {se id.classe != NULO entao ERRO senao id.classe = classe-var}
            if(!id_aux.getClasse().equals("")){
                new Status(this.line + "\nidentificador ja declarado ["+ id_aux.getLexema() +"].");
            }else{
                id_aux.setClasse("classe-var");
            }// End else

            // RULE [9] {id.tipo = Listaids.tipo}
            id_aux.setTipo(Listaids_tipo);

            if(this.token.equals("<-")){
                if(id_aux.getClasse().equals("classe-const")){
                    new Status(this.line + "\nclasse de identificador incompativel ["+ id_aux.getLexema() +"].");
                }// End if
                
                // RULE [17] {Atrib.tipo = id.tipo}
                procedure_Atrib(id_aux);

                // ASSEMBLY [2] {ListaIds.isatribuicao = true}
                ListaIds_isatribuicao = true;
            }// End if

             /**
             * ASSEMBLY [3] {se ListaIds.isatribuicao = false se ListaIds.tipo = tipo-inteiro 
                 assembly.getDeclaracoes().put("sword ?"); senao se ListsIds.tipo = tipo-byte
                assembly.getDeclaracoes().put("byte ?"); senao se ListaIds.tipo = tipo-string
                assembly.getDeclaracoes().put("byte ?"); senao se Lista.tipo = tipo-lógico
                assembly.getDeclaracoes().put("byte ? ");}
            */
            if(!ListaIds_isatribuicao){
                if(Listaids_tipo.equals("tipo-inteiro")){
                    end = gerenciadorMemoria.newEndInt();
                    this.assembly.getDeclaracao().add("sword ?");
                }else if(Listaids_tipo.equals("tipo-byte")){
                    end = gerenciadorMemoria.newEndByte();
                    this.assembly.getDeclaracao().add("byte ?");
                }else if(Listaids_tipo.equals("tipo-string")){
                    end = gerenciadorMemoria.newEndString();
                    this.assembly.getDeclaracao().add("byte ?");
                }else{
                    end = gerenciadorMemoria.newEndBoolean();
                    this.assembly.getDeclaracao().add("byte ?");
                }// End else
            }// End if
            
            id.setEndereco(end);
        }// End while
    }// End procedure_ListaIds()

    public void procedure_Atrib(Symbol id){
        String Atrib_tipo = id.getTipo();
        casaToken("<-");
        if(this.token.equals("-")){
            // RULE [12] {Atrib.isnegativo = true}
            Atrib_isnegativo = true;

            casaToken("-");
        }// End else

        Symbol const_aux = this.symbol;
        casaToken("const");

        // RULE [16] {se Atrib.isnegativo = true entao id.tipo = tipo-inteiro}
        if(Atrib_isnegativo){
            const_aux.setTipo("tipo-inteiro");
        }// End if

        /**
         * RULE [21] {se const.tipo != tipo-string e const.tipo != tipo-lógico e 
         * const.tipo != NULO e const.lexema >= 0 e const.lexema <= 255 entao 
         * const.tipo = tipo-byte else const.tipo = tipo-inteiro}
         */
        String tipo = const_aux.getTipo();

        if(!tipo.equals("tipo-string") && !tipo.equals("tipo-lógico") && tipo.equals("")){
            int constant = Integer.parseInt(const_aux.getLexema());
            if(constant >= 0 && constant <= 255){
                const_aux.setTipo("tipo-byte");
            }else{
                const_aux.setTipo("tipo-inteiro");
            }// End else
        }// End if

        // RULE [20] {se Atrib.tipo != const.tipo  entao ERRO}
        if(!Atrib_tipo.equals(const_aux.getTipo())){
            new Status(this.line + "\ntipos incompativeis.");
        }// End if

            /**
             * ASSEMBLY [1] {se const.tipo = tipo-inteiro assembly.getDeclaracoes().put("sword const.lex");
             *               senao se const.tipo = tipo-string assembly.getDeclaracoes().put("byte const.lex+$");
             *               senao se const.tipo = byte assembly.getDeclaracoes().put("byte const.lex"); 
             *              senao se const.tipo = tipo-lógico assembly.getDeclaracoes().put("sword const.lex");}
             */
            if(const_aux.getTipo().equals("tipo-inteiro")){
                end = gerenciadorMemoria.newEndInt();
                this.assembly.getDeclaracao().add("sword "+const_aux.getLexema()+"; int em "+end);
            }else if(const_aux.getTipo().equals("tipo-string")){
                const_aux.setLexema(const_aux.getLexema().replace("\"",""));
                end = gerenciadorMemoria.newEndString(const_aux.getLexema().length());
                this.assembly.getDeclaracao().add("byte "+const_aux.getLexema()+"$"+" ; string em "+end);
            }else if(const_aux.getTipo().equals("tipo-byte")){
                end = gerenciadorMemoria.newEndByte();
                this.assembly.getDeclaracao().add("byte "+const_aux.getLexema()+" ; byte em "+end);
            }else{
                end = gerenciadorMemoria.newEndBoolean();
                this.assembly.getDeclaracao().add("byte "+const_aux.getLexema()+" ; boolean em "+end);
            }// End else

            id.setEndereco(end);
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
        Symbol id_aux = this.symbol;
        casaToken("id");

        // RULE [3] {se id.classe = NULO entao ERRO}
        if(id_aux.getClass().equals("")){
            new Status(this.line + "\nidentificador nao declarado ["+ id_aux.getLexema() +"].");
        }// End if

        // RULE [4] {se id.classe != classe-var entao ERRO}
        if(!id_aux.getClasse().equals("classe-var")){
            new Status(this.line + "\nclasse de identificador incompativel ["+ id_aux.getLexema() +"].");
        }// End if

        casaToken("<-");
        
        String Expressao_aux = procedure_Expressao();

        // [42] {se id.tipo != Expressao.tipo e (id.tipo != tipo-inteiro e const.tipo != tipo-byte) entao ERRO}
        if(!id_aux.getTipo().equals(Expressao_aux) && (!id_aux.equals("tipo-inteiro") && !Expressao_aux.equals("tipo-byte"))){
            new Status(this.line + "\ntipos incompativeis.");
        }// End if

        /**
         *     ASSEMBLY [4] {mov ax, DS: [Expressao.end]}
         *     {se id.tipo != Expressao.tipo entao  cwd}
         *     {mov DS:[id.end], ax}
         */
          this.assembly.getComandos().add("mov ax, DS:["+ Expressao_end +"] ; movimentar o valor da expressao para ax");
          if(!id_aux.getTipo().equals(Expressao_aux)){
            this.assembly.getComandos().add("cwd ; casting");
          }// End if
          this.assembly.getComandos().add("mov DS:["+id_aux.getEndereco()+"],ax");

        casaToken(";");
    }// End procedure_Atribuicao()

    public void procedure_Repeticao(){
        casaToken("while");

        /**
         * ASSEMBLY [23] {RotInicio = NovoRot}
                         {RotFim = NovoRot}
                         {RotInicio:}
         */
        String rotInicio = rotulo.newRot();
        String rotFim = rotulo.newRot();
        this.assembly.getComandos().add(rotInicio + " :");

        casaToken("(");
        String Expressao_aux = procedure_Expressao();
        int Expressao_endereco = Expressao_end;

        /**
         * ASSEMBLY [24] {mov ax, DS:[Expressao.end] }
                         {cmp ax, 0 
                         je RotFim}
         */
        this.assembly.getComandos().add("mov ax, DS:["+Expressao_endereco+"]");
        this.assembly.getComandos().add("cmp ax,0");
        this.assembly.getComandos().add("je "+rotFim);

        // RULE [43] {se Expressao.tipo != tipo-lógico entao ERRO}
        if(!Expressao_aux.equals("tipo-lógico")){
            new Status(this.line + "\ntipos incompativeis.");
        }// End if 

        casaToken(")");
        procedure_Blocowhile();

        /**
         * ASSEMBLY [25]  {jmp RotInicio}
                          {RotFim:}
         */
        this.assembly.getComandos().add("jmp " + rotInicio);
        this.assembly.getComandos().add(rotFim + " :");
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

        // ASSEMBLY [19] {RotFalso = NovoRot} {RotFim = NovoRot}
        String rotFalso = rotulo.newRot();
        String rotFim = rotulo.newRot();

        casaToken("(");
        String Expressao_aux = procedure_Expressao();
        int Expressao_endereco = Expressao_end;

        // ASSEMBLY [20] {mov ax, DS:[Expressao.end] } {cmp ax, 0} {je RotFalso}
        this.assembly.getComandos().add("mov ax, DS:["+Expressao_endereco+"]");
        this.assembly.getComandos().add("cmp ax,0");
        this.assembly.getComandos().add("je ["+rotFalso+"]");

        // RULE [43] {se Expressao.tipo != tipo-lógico entao ERRO}
        if(!Expressao_aux.equals("tipo-lógico")){
            new Status(this.line + "\ntipos incompativeis.");
        }// End if 

        casaToken(")");
        if(this.token.equals("begin")){
            procedure_Blocoif(rotFalso,rotFim);
        }else{
            procedure_Comando();
            if(this.token.equals("else")){
                procedure_Blocoelse(rotFalso,rotFim);
            }// End if
        }// End else
    }// End procedure_Teste()

    public void procedure_Blocoif(String rotFalso,String rotFim){
        casaToken("begin");
        procedure_Comando();
        while(token.equals("id")||token.equals("while")||token.equals("if")||token.equals(";")||token.equals("readln")||token.equals("write")||token.equals("writeln")){
            procedure_Comando();
        }// End while
        casaToken("endif");
        casaToken("else");

        // ASSEMBLY [21] {jmp RotFim } {RotFalso:}
        this.assembly.getComandos().add("jmp ["+rotFim+"]");
        this.assembly.getComandos().add(rotFalso + " :");

        casaToken("begin");
        procedure_Comando();
        while(token.equals("id")||token.equals("while")||token.equals("if")||token.equals(";")||token.equals("readln")||token.equals("write")||token.equals("writeln")){
            procedure_Comando();
        }// End while

        // ASSEMBLY [22] {RotFim:}
        this.assembly.getComandos().add(rotFim + " :");

        casaToken("endelse");
    }// End procedure_Blocoif()

    public void procedure_Blocoelse(String rotFalso,String rotFim){
        casaToken("else");

        // ASSEMBLY [21] {jmp RotFim } {RotFalso:}
        this.assembly.getComandos().add("jmp ["+rotFim+"]");
        this.assembly.getComandos().add(rotFalso + " :");

        procedure_Comando();

        // ASSEMBLY [22] {RotFim:}
        this.assembly.getComandos().add(rotFim + " :");
    }// End procedure_Blocoelse()

    public void procedure_Nulo(){
        casaToken(";");
    }// End procedure_Nulo()

    public void procedure_Leitura(){
        casaToken("readln");
        casaToken("(");
        Symbol id_aux = this.symbol;
        casaToken("id");

        // RULE [3] {se id.classe = NULO entao ERRO}
        if(id_aux.getClasse().equals("")){
            new Status(this.line + "\nidentificador nao declarado ["+ id_aux.getLexema() +"].");
        }// End if

        // RULE [4] {se id.classe != classe-var entao ERRO}
        if(!id_aux.getClasse().equals("classe-var")){
            new Status(this.line + "\nclasse de identificador incompativel ["+ id_aux.getLexema() +"].");
        }// End if

        // RULE [44] {se id.tipo != tipo-byte e id.tipo != tipo-inteiro e tipo.tipo != tipo-string}
        if(!id_aux.getTipo().equals("tipo-byte") && !id_aux.getTipo().equals("tipo-inteiro") && !id_aux.getTipo().equals("tipo-string")){
            new Status(this.line + "\ntipos incompativeis.");
        }// End if

        casaToken(")");

        int endereco_buffer = gerenciadorMemoria.newEndString();
        gerenciadorMemoria.contador += 3;

        this.assembly.getComandos().add("mov dx,"+endereco_buffer);
        this.assembly.getComandos().add("mov al, 0FFh");
        this.assembly.getComandos().add("mov DS:["+endereco_buffer+"],al");
        this.assembly.getComandos().add("mov ah, 0Ah");
        this.assembly.getComandos().add("int 21h");

        this.assembly.getComandos().add("mov ah,02h");
        this.assembly.getComandos().add("mov dl,0Dh");
        this.assembly.getComandos().add("int 21h");
        this.assembly.getComandos().add("mov DL, 0Ah");
        this.assembly.getComandos().add("int 21h");

        this.assembly.getComandos().add("mov di," + endereco_buffer+2 + ";posição do string");
        if(!id_aux.getTipo().equals("tipo-string")){
            this.assembly.getComandos().add("mov ax, 0");
            this.assembly.getComandos().add("mov cx, 10");
            this.assembly.getComandos().add("mov dx, 1");
            this.assembly.getComandos().add("mov bh, 0");
            this.assembly.getComandos().add("mov bl, DS:[di]");
            this.assembly.getComandos().add("cmp bx, 2Dh");
			String rot = rotulo.newRot();
            this.assembly.getComandos().add("jne " + rot);
            this.assembly.getComandos().add("mov dx, -1");
            this.assembly.getComandos().add("add di, 1");
            this.assembly.getComandos().add("mov bl, DS:[di]");
            this.assembly.getComandos().add(rot + " :");
            this.assembly.getComandos().add("push dx");
            this.assembly.getComandos().add("mov dx, 0");
			String rot1 = rotulo.newRot();
            this.assembly.getComandos().add(rot1 + " :");
            this.assembly.getComandos().add("cmp bx, 0Dh");
			String rot2 = rotulo.newRot();
            this.assembly.getComandos().add("je " + rot2);
            this.assembly.getComandos().add("imul cx");
            this.assembly.getComandos().add("add bx, -48");
            this.assembly.getComandos().add("add ax, bx");
            this.assembly.getComandos().add("add di, 1");
            this.assembly.getComandos().add("mov bh, 0");
            this.assembly.getComandos().add("mov bl, DS:[di]");
            this.assembly.getComandos().add("jmp " + rot1);
            this.assembly.getComandos().add(rot2 + " :");
            this.assembly.getComandos().add("pop cx");
            this.assembly.getComandos().add("imul cx");
            this.assembly.getComandos().add("mov DS:[" + id_aux.getEndereco() + "], ax");
        }else{
            this.assembly.getComandos().add("mov si, " + id_aux.getEndereco());
			String rotString = rotulo.newRot();
            this.assembly.getComandos().add(rotString + " :");
		    this.assembly.getComandos().add("mov al, DS:[di]");	
            this.assembly.getComandos().add("cmp al, 0dh ; verifica fim string");
			String rot2 = rotulo.newRot();
            this.assembly.getComandos().add("je " + rot2 + " ; salta se fim string");	
            this.assembly.getComandos().add("mov DS:[si], al ; próximo caractere");
            this.assembly.getComandos().add("add di, 1 ; incrementa base");
            this.assembly.getComandos().add("add si, 1");
            this.assembly.getComandos().add("jmp " + rotString + " ; loop");
            this.assembly.getComandos().add(rot2 + " :");
            this.assembly.getComandos().add("mov al, 024h ; fim de string");
            this.assembly.getComandos().add("mov DS:[si], al ;grava $");
        }// End else
        
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

            this.assembly.getComandos().add("mov ah, 02h");	
		    this.assembly.getComandos().add("mov dl, 0Dh");
			this.assembly.getComandos().add("int 21h");	
			this.assembly.getComandos().add("mov DL, 0Ah");	
            this.assembly.getComandos().add("int 21h");
            
            casaToken(")");
            casaToken(";");
        }// End else if
    }// End procedure_Escrita()

    public void procedure_ListaExpressoes(){
        String exp_tipo = procedure_Expressao();
        int stringEnd = gerenciadorMemoria.getTemporario();

        if(Exp_tipo.equals("tipo_string")){
            this.assembly.getComandos().add("mov dx, " + Exp_end);
            this.assembly.getComandos().add("mov ah, 09h");
            this.assembly.getComandos().add("int 21h");
        }else{
            this.assembly.getComandos().add("mov ax, DS:[" + Exp_end + "]");
            this.assembly.getComandos().add("mov di, " + stringEnd + " ; end. string temp."); 
            this.assembly.getComandos().add("mov cx, 0 ;contador");
            this.assembly.getComandos().add("cmp ax,0 ;verifica sinal");
            String rot = rotulo.newRot();
            this.assembly.getComandos().add("jge " + rot + " ;salta se numero positivo");
            this.assembly.getComandos().add("mov bl, 2Dh ;senao, escreve sinal ");
            this.assembly.getComandos().add("mov DS:[di], bl");
            this.assembly.getComandos().add("add di, 1 ;incrementa indice");
            this.assembly.getComandos().add("neg ax ;toma modulo do numero");
            this.assembly.getComandos().add(rot + ":");
            this.assembly.getComandos().add("mov bx, 10 ;divisor"); 
            String rot1 = rotulo.newRot();
            this.assembly.getComandos().add(rot1 + ":");
            this.assembly.getComandos().add("add cx, 1 ;incrementa contador");
            this.assembly.getComandos().add("mov dx, 0 ;estende 32bits p/ div.");
            this.assembly.getComandos().add("idiv bx ;divide DXAX por BX");
            this.assembly.getComandos().add("push dx ;empilha valor do resto");  
            this.assembly.getComandos().add("cmp ax, 0 ;verifica se quoc.  0");
            this.assembly.getComandos().add("jne " + rot1 + " ;se nao  0, continua");              
            String rot2 = rotulo.newRot();
            this.assembly.getComandos().add(rot2 + ":");
            this.assembly.getComandos().add("pop dx ;desempilha valor");
            this.assembly.getComandos().add("add dx, 30h ;transforma em caractere");
            this.assembly.getComandos().add("mov DS:[di],dl ;escreve caractere");
            this.assembly.getComandos().add("add di, 1 ;incrementa base");
            this.assembly.getComandos().add("add cx, -1 ;decrementa contador");
            this.assembly.getComandos().add("cmp cx, 0 ;verifica pilha vazia");
            this.assembly.getComandos().add("jne " + rot2 + " ;se nao pilha vazia, loop");
            this.assembly.getComandos().add("mov dl, 024h ;fim de string");
            this.assembly.getComandos().add("mov DS:[di], dl ;grava '$'");
            this.assembly.getComandos().add("mov dx, " + stringEnd);
            this.assembly.getComandos().add("mov ah, 09h");
            this.assembly.getComandos().add("int 21h");
        }// End else

        // RULE [45] {se Expressao.tipo != tipo-inteiro e Expressao.tipo != tipo-byte e Expressao.tipo != tipo-string}
        if(!exp_tipo.equals("tipo-inteiro") && !exp_tipo.equals("tipo-byte") && !exp_tipo.equals("tipo-string")){
            new Status(this.line + "\ntipos incompativeis.");
        }// End if

        while(this.token.equals(",")){
            casaToken(",");

            exp_tipo = procedure_Expressao();

            if(Exp_tipo.equals("tipo_string")){
                this.assembly.getComandos().add("mov dx, " + Exp_end);
                this.assembly.getComandos().add("mov ah, 09h");
                this.assembly.getComandos().add("int 21h");
            }else{
                this.assembly.getComandos().add("mov ax, DS:[" + Exp_end + "]");
                this.assembly.getComandos().add("mov di, " + stringEnd + " ;end. string temp."); 
                this.assembly.getComandos().add("mov cx, 0 ;contador");
                this.assembly.getComandos().add("cmp ax,0 ;verifica sinal");
                String rot = rotulo.newRot();
                this.assembly.getComandos().add("jge " + rot + " ;salta se numero positivo");
                this.assembly.getComandos().add("mov bl, 2Dh ;senao, escreve sinal ");
                this.assembly.getComandos().add("mov DS:[di], bl");
                this.assembly.getComandos().add("add di, 1 ;incrementa indice");
                this.assembly.getComandos().add("neg ax ;toma modulo do numero");
                this.assembly.getComandos().add(rot + ":");
                this.assembly.getComandos().add("mov bx, 10 ;divisor"); 
                String rot1 = rotulo.newRot();
                this.assembly.getComandos().add(rot1 + ":");
                this.assembly.getComandos().add("add cx, 1 ;incrementa contador");
                this.assembly.getComandos().add("mov dx, 0 ;estende 32bits p/ div.");
                this.assembly.getComandos().add("idiv bx ;divide DXAX por BX");
                this.assembly.getComandos().add("push dx ;empilha valor do resto");  
                this.assembly.getComandos().add("cmp ax, 0 ;verifica se quoc.  0");
                this.assembly.getComandos().add("jne " + rot1 + " ;se nao  0, continua");              
                String rot2 = rotulo.newRot();
                this.assembly.getComandos().add(rot2 + ":");
                this.assembly.getComandos().add("pop dx ;desempilha valor");
                this.assembly.getComandos().add("add dx, 30h ;transforma em caractere");
                this.assembly.getComandos().add("mov DS:[di],dl ;escreve caractere");
                this.assembly.getComandos().add("add di, 1 ;incrementa base");
                this.assembly.getComandos().add("add cx, -1 ;decrementa contador");
                this.assembly.getComandos().add("cmp cx, 0 ;verifica pilha vazia");
                this.assembly.getComandos().add("jne " + rot2 + " ;se nao pilha vazia, loop");
                this.assembly.getComandos().add("mov dl, 024h ;fim de string");
                this.assembly.getComandos().add("mov DS:[di], dl ;grava '$'");
                this.assembly.getComandos().add("mov dx, " + stringEnd);
                this.assembly.getComandos().add("mov ah, 09h");
                this.assembly.getComandos().add("int 21h");
            }// End else

            // RULE [45] {se Expressao.tipo != tipo-inteiro e Expressao.tipo != tipo-byte e Expressao.tipo != tipo-string}
            if(!exp_tipo.equals("tipo-inteiro") && !exp_tipo.equals("tipo-byte") && !exp_tipo.equals("tipo-string")){
                new Status(this.line + "\ntipos incompativeis.");
            }// End if
        }// End while
    }// End procedure_ListaExpressoes()

    public String procedure_Expressao(){
        // RULE [23] {Expressao.tipo = Exp.tipo}
        Expressao_tipo = procedure_Exp();
        byte Exp1_end = Exp_end;
         
        // ASSEMBLY [16] {Expressao.end = Exp1.end}
        Expressao_end = Exp1_end;

        if(this.token.equals("=") || this.token.equals("!=") || this.token.equals("<") || this.token.equals(">") || this.token.equals("<=") || this.token.equals(">=")){
            Expressao_tipo = procedure_E(Expressao_tipo);
        }// End if
        return Expressao_tipo;
    }// End procedure_Expressao()

    public String procedure_E(String Expressao_tipo){
        if(token.equals("=")){
            /**
             * RULE [33] {se Expressao.tipo != tipo-string e Expressao.tipo != 
             * tipo-inteiro e Expressao.tipo != tipo-lógico e Expressao.tipo != 
             * tipo-byte entao ERRO} 
             */
             if(!Expressao_tipo.equals("tipo-string") && !Expressao_tipo.equals("tipo-inteiro") && 
                !Expressao_tipo.equals("tipo-lógico") && !Expressao_tipo.equals("tipo-byte")){
                new Status(this.line + "\ntipos incompativeis.");
            }// End if

            // ASSEMBLY [17.1] {Expressao.op =  =}
            Expressao_op = "=";

            casaToken("=");
        }else if(token.equals("!=")){
            // RULE [30] {se Expressao.tipo = tipo-string entao ERRO}
            if(Expressao_tipo.equals("tipo-string")){
                new Status(this.line + "\ntipos incompativeis.");
            }// End if

            // ASSEMBLY [17.2] {Expressao.op =  !=}
            Expressao_op = "!=";

            casaToken("!=");
        }else if(token.equals("<")){
            // RULE [30] {se Expressao.tipo = tipo-string entao ERRO}
            if(Expressao_tipo.equals("tipo-string")){
                new Status(this.line + "\ntipos incompativeis.");
            }// End if

            // ASSEMBLY [17.3] {Expressao.op =  <}
            Expressao_op = "<";

            casaToken("<"); 
        }else if(token.equals(">")){
             // RULE [30] {se Expressao.tipo = tipo-string entao ERRO}
             if(Expressao_tipo.equals("tipo-string")){
                new Status(this.line + "\ntipos incompativeis.");
            }// End if

            // ASSEMBLY [17.4] {Expressao.op =  >}
            Expressao_op = ">";

            casaToken(">");
        }else if(token.equals("<=")){
             // RULE [30] {se Expressao.tipo = tipo-string entao ERRO}
             if(Expressao_tipo.equals("tipo-string")){
                new Status(this.line + "\ntipos incompativeis.");
            }// End if

            // ASSEMBLY [17.5] {Expressao.op =  <=}
            Expressao_op = "<=";

            casaToken("<=");
        }else{
             // RULE [30] {se Expressao.tipo = tipo-string entao ERRO}
             if(Expressao_tipo.equals("tipo-string")){
                new Status(this.line + "\ntipos incompativeis.");
            }// End if

            // ASSEMBLY [17.6] {Expressao.op =  >=}
            Expressao_op = ">=";

            casaToken(">=");
        }// End else if
        String Exp2_tipo = procedure_Exp();
        int Exp2_end = Exp_end;
        
        /**
         * RULE [36] {se Expressao.tipo != Exp2.tipo e (Expressao.tipo !=
         * tipo-inteiro e Exp2.tipo != tipo-byte e Expressao.tipo != tipo-byte 
         * e Exp2.tipo != tipo-inteiro) entao ERRO}
         */
        if(!Expressao_tipo.equals(Exp2_tipo) && (!Expressao_tipo.equals("tipo-inteiro") && 
           !Exp2_tipo.equals("tipo-byte")) && (!Expressao_tipo.equals("tipo-byte") && 
           !Exp2_tipo.equals("tipo-inteiro"))){
            new Status(this.line + "\ntipos incompativeis.");
        }// End if
         
         // RULE [41] {Expressao.tipo = tipo-lógico}
         Expressao_tipo = "tipo-lógico";

         /**
          * ASSEMBLY [18]  { mov ax, DS:[Expressao.end]}
                    mov bx, DS:{Exp2.end]
                    cwd
                    mov cx, DS:[ax]
                    mov ax, DS[bx]
                    cwd
                    mov DS:[bx],ax
                    mov DS:[ax],cx}
                    {cmp ax, bx}
                    { RotVerdadeiro = NovoRot }
                    { se Exp.op = = então
                        je RotVerdadeiro
                    senao se Exp.op = != então
                        jne RotVerdadeiro
                    senao se Exp.op = < então
                        jl RotVerdadeiro
                    senao se Exp.op = > então
                        jg RotVerdadeiro
                    senao se Exp.op = >= então
                        jge RotVerdadeiro
                    senao
                        jle RotVerdadeiro }
                    { mov al, 0 }
                    { RotFim = NovoRot }
                    { jmp RotFim }
                    { RotVerdadeiro: }
                    { mov AL, 0FFh }
                    { RotFim: }
                    { Exp.end:=NovoTemp }
                    { Exp.tipo:=tipo-lógico }
                    { mov Exp.end, AL }
          */
          this.assembly.getComandos().add("mov ax, DS:["+Expressao_end+"]");
          this.assembly.getComandos().add("mov bx, DS:["+Exp2_end+"]");
          this.assembly.getComandos().add("cwd");
          this.assembly.getComandos().add("mov bx,ax");
          this.assembly.getComandos().add("mov ax,cx");

          this.assembly.getComandos().add("cmp ax, bx");
          String rotuloVerdadeiro = rotulo.newRot();

          if(Expressao_op.equals("=")){
            this.assembly.getComandos().add("je " + rotuloVerdadeiro);
          }else if(Expressao_op.equals("!=")){    
            this.assembly.getComandos().add("jne " + rotuloVerdadeiro);
          }else if(Expressao_op.equals("<")){
            this.assembly.getComandos().add("jl " + rotuloVerdadeiro);
          }else if(Expressao_op.equals(">")){
            this.assembly.getComandos().add("jg " + rotuloVerdadeiro);
          }else if(Expressao_op.equals(">=")){
            this.assembly.getComandos().add("jge " + rotuloVerdadeiro);
          }else{
            this.assembly.getComandos().add("jle " + rotuloVerdadeiro);
          }// End else
          this.assembly.getComandos().add("mov al, 0");
          String rotFim = rotulo.newRot();
          this.assembly.getComandos().add("jmp " + rotFim);
          this.assembly.getComandos().add(rotuloVerdadeiro + " :");
          this.assembly.getComandos().add("mov al, 0FFh");
          this.assembly.getComandos().add(rotFim + " :");
          Expressao_end = gerenciadorMemoria.getTemporario();
          Expressao_tipo = "tipo-lógico";
          this.assembly.getComandos().add("mov DS:["+Expressao_end+"],al");

          return Expressao_tipo;
    }// End procedure_E()

    public String procedure_Exp(){
        boolean expnegado = false;

        if(this.token.equals("-")){
            casaToken("-");

            // ASSEMBLY [12] {Exp.negado = true}
            expnegado = true;
        }// End if

        // RULE [24] {Exp.tipo = T.tipo}
        Exp_tipo = procedure_T();
        byte T1_end = T_end;

        /**
         * ASSEMBLY [13] {se Exp.negado = true
          	     Exp.end = NovoTemp
     	         mov ax, DS:[T1.end]
          	     neg ax
          	     mov DS:[Exp.end], ax}
                 {Exp.end = T1.end}
         */
        if(expnegado){
            Exp_end = gerenciadorMemoria.getTemporario();
            this.assembly.getComandos().add("mov ax, DS:["+T1_end+"]");
            this.assembly.getComandos().add("neg ax");
            this.assembly.getComandos().add("mov DS:["+Exp_end+"],ax");
        }// Endif
        Exp_end = T1_end;

        while(this.token.equals("+") || this.token.equals("-") || this.token.equals("||")){
            Exp_tipo = procedure_D(Exp_tipo);
        }// End while
        return Exp_tipo;
    }// End procedure_Exp()

    public String procedure_D(String Exp_tipo){
        String T1_tipo = Exp_tipo;

        if(this.token.equals("+")){
            /**
             * RULE [34] {se Exp.tipo != tipo-string e Exp.tipo != 
             * tipo-inteiro e Exp.tipo != tipo-lógico e Exp.tipo != 
             * tipo-byte entao ERRO}
             */
              if(!Exp_tipo.equals("tipo-string") && !Exp_tipo.equals("tipo-inteiro") && 
                 !Exp_tipo.equals("tipo-lógico") && !Exp_tipo.equals("tipo-byte")){
                 new Status(this.line + "\ntipos incompativeis.");
              }// End if
              
              // ASSEMBLY [14.1] {Exp.operador = +}
              Exp_op = "+";

            casaToken("+");
        }else if(this.token.equals("-")){
            // RULE [31] {se Exp.tipo = tipo-string entao ERRO}
            if(Exp_tipo.equals("tipo-string")){
                new Status(this.line + "\ntipos incompativeis.");
            }// End if

            // ASSEMBLY [14.2] {Exp.operador = -}
            Exp_op = "-";
        
            casaToken("-");
        }else{
            // RULE [31] {se Exp.tipo = tipo-string entao ERRO}
            if(Exp_tipo.equals("tipo-string")){
                new Status(this.line + "\ntipos incompativeis.");
            }// End if

            // ASSEMBLY [14.3] {Exp.operador = ||}
            Exp_op = "||";

            casaToken("||");
        }// End else if
        String T2_tipo = procedure_T();
        int T2_exp = T_end;

        /**
         * RULE [37] {se Exp.tipo != T2.tipo e (Exp.tipo != tipo-inteiro e T2.tipo != 
         * tipo-byte e Exp.tipo != tipo-byte e T2.tipo != tipo-inteiro) entao ERRO}
         */
         if(!Exp_tipo.equals(T2_tipo) && (!Exp_tipo.equals("tipo-inteiro") && !T2_tipo.equals("tipo-byte") &&
            (!Exp_tipo.equals("tipo-byte") && !T2_tipo.equals("tipo-inteiro")))){
                new Status(this.line + "\ntipos incompativeis.");
         }// End if   

         /**
          * [40] {se (T1.tipo = tipo-inteiro e T2.tipo = tipo-byte) ou (T1.tipo = 
          *  tipo-byte e T2.tipo = tipo-inteiro) entao Exp.tipo = tipo-inteiro}
          */
          if((T1_tipo.equals("tipo-inteiro") && T2_tipo.equals("tipo-byte")) || (T1_tipo.equals("tipo-byte") && 
              T2_tipo.equals("tipo-inteiro"))){
              Exp_tipo = "tipo-inteiro";
          }// End if

          /**
           * ASSEMBLY [15] {mov ax, DS:[Exp.end]
                            mov bx, DS:[T2.end]
                            se Exp.tipo != tipo-inteiro entao
	                        cwd 
                            se T2.tipo != tipo-inteiro entao
                            mov cx, DS:[ax]
                            mov ax, DS:[bx]
                            cwd 
                            mov DS:[bx], ax
                            mov DS:[ax], cx}
                            se Exp.op = + então
                            add
                            senao se Exp.op = - então
                            sub
                            senao 
                            and          
                            Exp.end = NovoTemp
                            mov DS:[Exp.end], ax 
           */
           this.assembly.getComandos().add("mov ax, DS:["+Exp_end+"]");
           this.assembly.getComandos().add("mov bx, DS:["+T2_exp+"]");
           if(!Exp_tipo.equals("tipo-inteiro")){
                this.assembly.getComandos().add("cwd");
           }// End if
           if(!T2_tipo.equals("tipo-inteiro")){
                this.assembly.getComandos().add("mov cx,ax");
                this.assembly.getComandos().add("mov ax,bx");
                this.assembly.getComandos().add("cwd");
                this.assembly.getComandos().add("mov bx, ax");
                this.assembly.getComandos().add("mov ax, cx");
           }// End if
           if(Exp_op.equals("+")){
                this.assembly.getComandos().add("add ax,bx");
           }else if(Exp_op.equals("-")){
                this.assembly.getComandos().add("sub ax,bx");
           }else{
                this.assembly.getComandos().add("or ax,bx");
           }// End else
           Exp_end = gerenciadorMemoria.getTemporario();
           this.assembly.getComandos().add("mov DS:["+Exp_end+"],ax");

           return Exp_tipo;
    }// End procedure_D()

    public String procedure_T(){

        // RULE [25] {T.tipo = F.tipo}
        T_tipo = procedure_F();

        // ASSEMBLY [9] {T.end = F1.end}
        T_end = F_end; 
        
        while(this.token.equals("*") || this.token.equals("&&") || this.token.equals("/")){
            T_tipo = procedure_precedenciaC(T_tipo);
        }// End while

        return T_tipo;
    }// End procedure_T()

    public String procedure_precedenciaC(String T_tipo){
        String F1_tipo = T_tipo;
        boolean T_isdiv = false;

        if(this.token.equals("*")){
            
            // RULE [32] {se T.tipo = tipo-string entao ERRO}
            if(T_tipo.equals("tipo-string")){
                new Status(this.line + "\ntipos incompativeis.");
            }// End if

            // ASSEMBLY [10.1] {T.operador = *}
            T_op = "*";
            
            casaToken("*");
        }else if(this.token.equals("&&")){
            
            // RULE [32] {se T.tipo = tipo-string entao ERRO}
            if(T_tipo.equals("tipo-string")){
                new Status(this.line + "\ntipos incompativeis.");
            }// End if

            // ASSEMBLY [10.2] {T.operador = &&]
            T_op = "&&";

            casaToken("&&");
        }else{

            // RULE [32] {se T.tipo = tipo-string entao ERRO}
            if(T_tipo.equals("tipo-string")){
                new Status(this.line + "\ntipos incompativeis.");
            }// End if

            // RULE [35] {se T.tipo = tipo-byte entao T.tipo = tipo-inteiro, T.isdiv = true}
            if(T_tipo.equals("tipo-byte")){
                T_tipo = "tipo-inteiro";
                T_isdiv = true;
            }// End if

            // ASSEMBLY [10.3] {T.operador = /]
            T_op = "/";

            casaToken("/");
        }// End else if

        String F2_aux = procedure_F();
        int F2_end = F_end;

        /**
         * [38] {se T.tipo != F2.tipo e (T.tipo != tipo-inteiro e F2.tipo != 
         * tipo-byte ou T.tipo != tipo-byte e F2.tipo != tipo-inteiro) entao ERRO}
         */
        if(!F1_tipo.equals(F2_aux) && (!F1_tipo.equals("tipo-inteiro") && 
           !F2_aux.equals("tipo-byte")) || (!F1_tipo.equals("tipo-byte") && !F2_aux.equals("tipo-inteiro"))){
                new Status(this.line + "\ntipos incompativeis.");
        }// End if


        /**
         * [39] {se T.isdiv = true ou (F1.tipo = tipo-inteiro e F2.tipo = tipo-byte) ou (F1.tipo = 
         * tipo-byte e F2.tipo = tipo-inteiro) entao T.tipo = tipo-inteiro}
         */
         if(T_isdiv == true || (F1_tipo.equals("tipo-inteiro") && F2_aux.equals("tipo-byte")) || (F1_tipo.equals("tipo-byte") && 
             F2_aux.equals("tipo-inteiro"))){
             T_tipo = "tipo-inteiro";
         }// End if

          /**
           * ASSEMBLY [11] {mov ax, DS:[T.end]}
                  {mov bx, DS:[F2.end]}
                  {se T.tipo != tipo-inteiro entao
	               cwd 
                   se F2.tipo != tipo-inteiro entao
	               mov cx, DS:[ax]
	               mov ax, DS:[bx]
	               cwd 
	               mov DS:[bx], ax
	               mov DS:[ax], cx}
                   se T.op = * então
		           add
                   senao se T.op = && então
	               sub
                   senao 
	               and          
                   Exps.end = NovoTemp
                   mov DS:[T.end], ax 
           */
          this.assembly.getComandos().add("mov ax, DS:["+T_end+"]");
          this.assembly.getComandos().add("mox bx, DS:["+ F2_end +"]");
          if(!T_tipo.equals("tipo-inteiro")){
              this.assembly.getComandos().add("cwd");
          }// Endif
          if(F2_aux.equals("tipo-inteiro")){
            this.assembly.getComandos().add("mov cx,ax");
            this.assembly.getComandos().add("mov ax,bx");
            this.assembly.getComandos().add("cwd");
            this.assembly.getComandos().add("mov bx,ax");
            this.assembly.getComandos().add("mov ax,cx");
          }// End if

          if(T_op.equals("*")){
            this.assembly.getComandos().add("imul bx");
          }else if(T_op.equals("&&")){ 
            this.assembly.getComandos().add("and ax,bx ; conjuncao");
          }else{
            this.assembly.getComandos().add("idiv bx");
            this.assembly.getComandos().add("sub ax, 256");
          }// End else
          T_end = gerenciadorMemoria.getTemporario();
          this.assembly.getComandos().add("mov DS:["+T_end+"],ax"); 

         return T_tipo;
    }// End procedure_precedenciaC()

    public String procedure_F(){
        if(this.token.equals("!")){
            casaToken("!");

            // RULE [26] {F.tipo = F1.tipo}
            F_tipo = procedure_F();
            int F1_end = F_end;

            /**
             * [8] {F.end = NovoTemp
             *      mov regA, F1.end
             *      neg regA
             *      add regA, 1
             *      mov F.end, regA}
             */
             F_end = gerenciadorMemoria.getTemporario();
             this.assembly.getComandos().add("mov ax,"+F1_end);
             this.assembly.getComandos().add("neg ax");
             this.assembly.getComandos().add("add ax,1");
             this.assembly.getComandos().add("mov "+ F_end +",ax");
        }else if(this.token.equals("(")){
            casaToken("(");

            // RULE [27] {F.tipo = Expressao.tipo}
            F_tipo = procedure_Expressao();
            
            // ASSEMBLY [7] {F.end = Expressao.end}
            F_end = Expressao_end;

            casaToken(")");
        }else if(this.token.equals("id")){

            Symbol id_aux = this.symbol;
            casaToken("id");

            // RULE [3] {se id.classe = NULO entao ERRO}
            if(id_aux.getClasse().equals("")){
                new Status(this.line + "\nidentificador nao declarado ["+ id_aux.getLexema() +"].");
            }// End if

            // RULE [29] {F.tipo = id.tipo}
            F_tipo = id_aux.getTipo();

            // ASSEMBLY [5] {F.end = id.end}
            F_end = id_aux.getEndereco();

        }else if(this.token.equals("-")){
            // RULE [13] {F.isnegativo = true}
            this.F_isnegativo = true;

            casaToken("-");
            
            Symbol const_aux = this.symbol;
            casaToken("const");

            /**
             * RULE [21] {se const.tipo != tipo-string e const.tipo != tipo-lógico e 
             * const.tipo != NULO e const.lexema >= 0 e const.lexema <= 255 entao 
             * const.tipo = tipo-byte else const.tipo = tipo-inteiro}
             */
            String tipo = const_aux.getTipo();

            if(!tipo.equals("tipo-string") && !tipo.equals("tipo-lógico") && tipo.equals("")){
                int constant = Integer.parseInt(const_aux.getLexema());
                if(constant >= 0 && constant <= 255){
                    const_aux.setTipo("tipo-byte");
                }else{
                    const_aux.setTipo("tipo-inteiro");
                }// End else
            }// End if
            
           // RULE [28] {F.tipo = const.tipo}
           F_tipo = const_aux.getTipo();

           /**
            * ASSEMBLY [6] {se const.tipo = tipo-string entao 
	        *          dseg SEGMENT PUBLIC
	        *          byte const.lexema$
	        *          dseg ENDS
	        *          F.end = contator_dados
            *          contator_dados += 256
            *       senao
	        *          F.end = NovoTemp
		    *          mov regA, const.lexema
		    *          mov F.end, regA}
            */
            if(const_aux.getTipo().equals("tipo-string")){
                this.assembly.getComandos().add("dseg SEGMENT PUBLIC ; inicio da segmentacao");
                String constant = const_aux.getLexema().replace("\"","");
                this.assembly.getComandos().add("byte "+constant+"$ ; declaracao string");
                this.assembly.getComandos().add("dseg ENDS");
                F_end = gerenciadorMemoria.contador;
                gerenciadorMemoria.newEndString(constant.length());
            }else{
                F_end = gerenciadorMemoria.getTemporario();
                this.assembly.getComandos().add("mov ax,"+const_aux.getLexema()+" ; constante "+const_aux.getLexema());
                this.assembly.getComandos().add("mov "+F_end+",ax");

                if(const_aux.getTipo().equals("tipo-string")){
                    gerenciadorMemoria.newEndString();
                }else if(const_aux.getTipo().equals("tipo-inteiro")){
                    gerenciadorMemoria.newEndInt();
                }else if(const_aux.getTipo().equals("tipo-byte")){
                    gerenciadorMemoria.newEndByte();
                }else{
                    gerenciadorMemoria.newEndBoolean();
                }// End else
            }// End else

        }else{

            Symbol const_aux = this.symbol;
            casaToken("const");

            /**
             * RULE [21] {se const.tipo != tipo-string e const.tipo != tipo-lógico e 
             * const.tipo != NULO e const.lexema >= 0 e const.lexema <= 255 entao 
             * const.tipo = tipo-byte else const.tipo = tipo-inteiro}
             */
            String tipo = const_aux.getTipo();

            if(!tipo.equals("tipo-string") && !tipo.equals("tipo-lógico") && tipo.equals("")){
                int constant = Integer.parseInt(const_aux.getLexema());
                if(constant >= 0 && constant <= 255){
                    const_aux.setTipo("tipo-byte");
                }else{
                    const_aux.setTipo("tipo-inteiro");
                }// End else
            }// End if

           // RULE [28] {F.tipo = const.tipo}
           F_tipo = const_aux.getTipo();
        }// End else if
        return F_tipo;
    }// End procedure_F()
}// End AnalisadorSintatico()

class GerenciadorMemoria{
    public byte contador;
    public byte contador_temporario;

    public GerenciadorMemoria(){
        contador = 0;
        contador_temporario = 0;
    }// End GerenciadorMemoria()

    public void zerarTemporario(){
        contador_temporario = 0;
    }// End zerarTemporario()

    public byte newEndTemp(){
        byte temporario = contador;
        contador++;
        return temporario;
    }// End newEndTemp()

    public byte newEndBoolean(){
		byte temporario = contador;
		contador++;
		return temporario;
    }// End newEndBoolean()

    public byte newEndByte(){
		byte temporario = contador;
		contador++;
		return temporario;
	}// End newEndByte()
    
    public byte newEndInt(){
        byte temporario = contador;
        contador += 2;
        return temporario;
    }//End newEndInt()

    public byte getTemporario(){
        return contador_temporario;
    }// End getTemporario()

    public byte newEndString(){
        byte temporario = contador;
        contador += 256;
        return temporario;
    }// End newEndString()

    public byte newEndString(int tam){
		byte temporario = contador;
		contador += tam;
		return temporario;
    }// End newEndString()
    
    public byte newByteTemporario(){
        byte temporario = contador_temporario;
        contador_temporario++;
        return temporario;
    }// End newByteTemporario()

    public byte newStringTemporario(){
        byte temporario = contador_temporario;
        contador_temporario += 256;
        return temporario;
    }// End newStringTemporario()

    public byte newLogicoTemporario(){
        byte temporario = contador_temporario;
        contador_temporario++;
        return temporario;
    }// End newLogicoTemporario()

    public byte newIntTemporario(){
        byte temporario = contador_temporario;
        contador_temporario += 2;
        return temporario;
    }// End newIntTemporario()
}// End GerenciadorMemoria


class Rotulo{
    static int contator;

    public Rotulo(){
        contator = 0;
    }// End Rotulo()

    public void zerarRotulo(){
        contator = 0;
    }// End zerarRotulo()

    public String newRot(){
        return "R" + contator++;
    }// End newRot()
}// End classRotulo

class Assembly{
    private ArrayList<String> declaracao; 
    private ArrayList<String> comandos;
    private BufferedWriter bufferedWriter;

    public ArrayList<String> getDeclaracao(){ return this.declaracao;}// End getDeclaracao()
    public void setDeclaracao(ArrayList<String> declaracao){ this.declaracao = declaracao;}// End setDeclaracao()
    public ArrayList<String> getComandos(){ return this.comandos;}// End getComandos()
    public void setComandos(ArrayList<String> comandos){ this.comandos = comandos;}// End setComandos()

    public Assembly() throws IOException{
        this.bufferedWriter = new BufferedWriter(new FileWriter("c:/8086/codigo.asm")); 
        this.declaracao = new ArrayList<>(0);
        this.comandos = new ArrayList<>(0);
    }// End Assembly()

    public void getAssembly() throws IOException{
        List<String> allcode = new ArrayList<String>(0);

        allcode.add("sseg SEGMENT STACK ;inicia segmento pilha");
        allcode.add("byte 4000h DUP(?) ;dimensiona pilha");
        allcode.add("sseg ENDS ;fim seg. pilha");
        allcode.add("dseg SEGMENT PUBLIC ;inicio seg. dados");
        allcode.add("byte 4000h DUP(?) ;dimensiona pilha");
        allcode.addAll(declaracao);
        allcode.add("dseg ENDS ;fim seg. dados");
        allcode.add("cseg SEGMENT PUBLIC ;início seg. código");
        allcode.add("ASSUME CS:cseg, DS:dseg");
        allcode.add("strt:");
        allcode.add("mov AX, dseg");
        allcode.add("mov ds, AX");
        allcode.addAll(comandos);
        allcode.add("mov ah, 4Ch");
        allcode.add("int 21h");
        allcode.add("cseg ENDS ;fim seg. codigo");
        allcode.add("END strt ;fim programa");

        for(String asembly_code : allcode){
            this.bufferedWriter.write(asembly_code + "\n");
        }// End for
        this.bufferedWriter.close();
    }// End getAssembly()
}// End class Assembly

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
            analisadorSintatico = new AnalisadorSintatico(FILE_NAME,readskip,line,analisadorLexico.getSymbolTable());   
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