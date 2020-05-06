import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

/**
 * @author Vinicius Silva
 * @version 1.0
 * @since 2020
 */
public class AnalisadorLexico{
    private final int INITIAL_STATE = 0;
    private final int FINAL_STATE = 1;    
    private final int EOF = -1;

    final String ALLOWED = "[A-Z]|[a-z]|[0-9]|\\_|\\.|\\,|\\;|\\&|\\:" +
                            "|\\(|\\)|\\[|\\]|\\{|\\}|\\+|\\-|\\\"|\\'|" +
                            "\\/|\\!|\\?|\\>|\\<|\\=|\\n|\\t|\\r|\\s|\\*|\\||";  

    private SymbolTable symbolTable;
    private FileReader source;
    private BufferedReader bufferedReader;

    private int line;
    private  long readskip;
    
    public AnalisadorLexico(FileReader fileReader,int line,long readskip) throws FileNotFoundException{
        this.symbolTable = new SymbolTable();
        this.bufferedReader = new BufferedReader(fileReader);
        //this.source = source;
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

    private boolean isLexeme(String lex){
        return lex.matches(ALLOWED);
    }// End isLexeme()

    private Symbol semanticAction(String lexema){
        Symbol token = null;
       
        if(lexema.equals(SymbolTable.CONSTANTE)){
            token = new Symbol(SymbolTable.CONSTANTE,SymbolTable.CONSTANTE);
        }else{
            token = this.symbolTable.pesquisa(lexema);
            if(token == null) token = this.symbolTable.inserir("id",lexema);
        }// End if

        return token;
    }// End semanticAction()

    public Symbol analiseLexica(){
        int s = INITIAL_STATE;
        String lex = "";
        String c = "";
        Symbol symbol = null;
        int read;

        try{
        //    this.bufferedReader = new BufferedReader(this.source);
            this.bufferedReader.skip(this.readskip);
            
            while(s != FINAL_STATE){
                if((read = this.bufferedReader.read()) != EOF){
                    char chr = (char)read;
                    c = (chr + "").toLowerCase();
                    this.readskip++;
                    if(!isLexeme(c)){
                        new Status(this.line + "\ncaractere invalido.");
                    }// End if
                }else{            
                    if(s != INITIAL_STATE){
                        if(this.symbolTable.pesquisa(lex) != null){
                            return semanticAction(lex);
                        }else if(s == 8){
                            return semanticAction(lex); // IDENTIFICADOR
                        }else{
                            new Status("\n" + this.line + "\nfim de arquivo nao esperado. ");
                        }//End else
                    }else{
                        return semanticAction(SymbolTable.EOF); // EOF
                    }// End else
                }// End else

                switch(s){
                    case INITIAL_STATE : 
                        if(c.matches("\\n")){
                            this.line++;
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
                        }else if(c.matches("\\(|\\;|\\)|\\,|\\+|\\*|\\=")){
                            lex += c;
                            s = FINAL_STATE;
                            return semanticAction(lex); // PALAVRA RESERVADA
                        }else if(c.matches("<")){
                            lex += c;
                            s = 5;
                        }else if(c.matches(">")){
                            lex += c;
                            s = 6;
                        }else if(c.matches("!")){
                            lex += c;
                            s = 7;
                        }else if(c.matches("[1-9]|-")){
                            lex += c;
                            s = 11;
                        }else if(c.matches("0")){
                            lex += c;
                            s = 9;
                        }else if(c.matches("F")){
                            lex += c;
                            s = 16;
                        }else if(c.matches("[A-E]|[G-Z]|[a-z]|_")){
                            lex += c;
                             s = 8;                        
                        }else if(c.matches("[\\s]|[\\t]|[\\r]")){
                            s = INITIAL_STATE;
                        }else if(!c.matches("[\\s]|[\\t]|[\\r]")){
                            new Status(this.line + "\nlexema nao identificado [" + c + "]."); 
                        }// End if
                    break;

                    case 2 : 
                        if(c.matches("\\*")){
                            lex = "";
                            s = 3;
                        }else{
                            s = FINAL_STATE;
                            this.readskip--;
                            return semanticAction(lex); // PALAVRA RESERVADA [ * ]
                        }// End else
                    break;

                    case 3 : 
                        if(c.matches("\\*")){
                            s = 4;
                        }else{
                            s = 3;
                        }// End else
                    break;

                    case 4 : 
                        if(c.matches("/")){
                            s = INITIAL_STATE;
                        }else{
                            s = 3;
                        }// End else
                    break;

                    case 5 : 
                        if(c.matches("[-]|[=]")){
                            lex += c;
                            s = FINAL_STATE;
                            return semanticAction(lex); // PALAVRA RESERVADA [ <- | <= ]
                        }else{
                            s = FINAL_STATE;
                            this.readskip--;
                            return semanticAction(lex); // PALAVRA RESERVADA [ < ]
                        }// End else
                   // break;
                            
                    case 6 :
                        if(c.matches("=")){
                            lex += c;
                            s = FINAL_STATE;
                            return semanticAction(lex); // PALAVRA RESERVADA [ >= ]
                        }else{
                            s = FINAL_STATE;
                            this.readskip--;
                            return semanticAction(lex); // PALAVRA RESERVADA [ > ]
                        }// End else
                    //break;

                    case 7 : 
                        if(c.matches("=")){
                            lex += c;
                            s = FINAL_STATE;
                            return semanticAction(lex); // PALAVRA RESERVADA [ != ]
                        }else{
                            s = FINAL_STATE;
                            this.readskip--;
                            return semanticAction(lex); // PALAVRA RESERVADA [ ! ]
                        }// End else
                    //break;

                    case 8 :
                        if(c.matches("[A-Z]|[a-z]|[0-9]|_")){
                            lex += c;
                            s = 8;
                        }else{
                            if(lex.equals("false")){
                                s = FINAL_STATE;
                                this.readskip--;
                                return semanticAction(SymbolTable.CONSTANTE); // CONSTANTE FALSE
                            }else if(lex.equals("true")){
                                s = FINAL_STATE;
                                this.readskip--;
                                return semanticAction(SymbolTable.CONSTANTE); // CONSTANTE TRUE
                            }else{
                                s = FINAL_STATE;
                                this.readskip--;
                                return semanticAction(lex); // IDENTIFICADOR
                            }// End else
                        }// End else
                    break;

                    case 9 :
                        if(c.matches("x")){
                            lex += c;
                            s = 10;
                        }else if(c.matches("h")){
                            lex += c;
                            s = FINAL_STATE;
                            return semanticAction(SymbolTable.CONSTANTE); // CONSTANTE [ 0h ]
                        }else if(c.matches("D")){
                            lex += c;
                            s = 14;
                        }else if(c.matches("A")){
                            lex += c;
                            s = 15;
                        }else if(c.matches("[0-9]")){
                            lex += c;
                            s = 11;
                        }else{
                            s = FINAL_STATE;
                            this.readskip--;
                            return semanticAction(SymbolTable.CONSTANTE); // CONSTANTE [ 0 ]
                        }// End else 
                   break;

                    case 10 :
                        if(c.matches("[A-F]|[a-f]|[0-9]")){
                            lex += c;
                            s = 12;
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + (lex+c) + "]."); 
                        }//End else
                    break;

                    case 11 :
                        if(c.matches("[0-9]")){
                            lex += c;
                            s = 11;
                        }else{
                            s = FINAL_STATE;
                             this.readskip--;
                             return semanticAction(SymbolTable.CONSTANTE); // CONSTANTE
                        }// End else
                    break;

                    case 12 :
                        if(c.matches("[A-F]|[a-f]|[0-9]")){
                            lex += c;
                            s = 13;
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + (lex+c) + "]."); 
                        }// End else
                    break;

                    case 13 :
                        if(!c.matches("[A-F]|[a-f]|[0-9]")){
                            s = FINAL_STATE;
                            this.readskip--;
                            return semanticAction(lex); // CONSTANTE [ 0xDD ]
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + (lex+c) + "]."); 
                        }//End else
                    break;

                    case 14 :
                        if(c.matches("h")){
                            lex += c;
                            s = FINAL_STATE;
                            return semanticAction(lex); // CONSTANTE [ 0Dh ]
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + (lex+c) + "]."); 
                        }// End else 
                    break;

                    case 15 :
                        if(c.matches("h")){
                            lex += c;
                            s = FINAL_STATE;
                            return semanticAction(lex); // CONSTANTE [ 0Ah ]
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + (lex+c) + "]."); 
                        }// End else 
                    break;

                    case 16 :
                        if(c.matches("F")){
                            lex += c;
                            s = 17;
                        }else if(c.matches("[A-E]|[G-Z]|[a-z]|[0-9]|_")){
                            lex += c;
                            s = 8;
                        }else{
                            s = FINAL_STATE;
                            this.readskip--;
                            return semanticAction(lex); // IDENTIFICADOR
                        }// End else 
                    break;

                    case 17 :
                        if(c.matches("h")){
                            lex += c;
                            s = FINAL_STATE;
                            return semanticAction(lex); // CONSTANTE [ FFh ]
                        }else if(c.matches("[A-Z]|[a-g]|[i-z]|[0-9]|_")){
                            lex += c;
                            s = 8;
                        }else{
                            s = FINAL_STATE;
                            this.readskip--;
                            return semanticAction(lex); // IDENTIFICADOR
                        }// End else 
                    break;

                    case 18 :
                        if(c.matches("\\\"")){
                            lex += c;
                            s = FINAL_STATE;
                            if(lex.equals("\"TRUE\"")){

                            }else if(lex.equals("\"FALSE\"")){

                            }else return semanticAction(SymbolTable.CONSTANTE); // CONSTANTE [ String ]
                        }else if(!c.matches("\\n") && !c.matches("\\\"")){
                            lex += c;
                            s = 18;
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + (lex+c) + "]."); 
                        }// End else
                    break;

                    case 19 :
                        if(c.matches("\\&")){
                            lex += c;
                            s = FINAL_STATE;
                            return semanticAction(lex); // PALAVRA RESERVADA [ && ]
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + (lex) + "].");
                        }// End else
                    break;

                    case 20 :
                        if(c.matches("\\|")){
                            lex += c;
                            s = FINAL_STATE;
                            return semanticAction(lex); // PALAVRA RESERVADA [ || ]
                        }else{
                            new Status(this.line + "\nlexema nao identificado [" + (lex) + "].");
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