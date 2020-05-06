/**
 * @author Vinicius Silva
 * @version 1.0
 * @since 2020
 */
class AnalisadorSintatico{
    private String token;
    private AnalisadorLexico analisadorLexico;
   
    public AnalisadorSintatico(AnalisadorLexico analisadorLexico){
        this.analisadorLexico = analisadorLexico;
    }// End AnalisadorSintatico()
    
    public String getToken(){
        return this.token;
    }// End getToken()

    public void setToken(String token){
        this.token = token;
    }// End setToken()

    public AnalisadorLexico getAnalisadorLexico(){
        return this.analisadorLexico;
    }// End AnalisadorLexico()

    public void setAnalisadorLexico(AnalisadorLexico analisadorLexico){
        this.analisadorLexico = analisadorLexico;
    }// End setAnalisadorLexico()

    public void casaToken(String tok_esp){
        Symbol s = null;
        System.out.println("TOK " + token +" TOKEN ESP " + tok_esp);
        if(this.token.equals(tok_esp)){
            s = this.analisadorLexico.analiseLexica(); 
            this.token = s.getToken();
        }else{
            if(this.token.equals("EOF")){
                new Status(this.analisadorLexico.getLine() + "\nfim de arquivo nao esperado."); 
            }else{
                new Status(this.analisadorLexico.getLine() + "\ntoken nao esperado ["+this.token+"].");
            }// End else
        }// End else
    }// End casaToken()

    public void procedure_S(){
        while(token.equals("int") || token.equals("boolean") || token.equals("byte") || token.equals("string")){
            procedure_Declarar();
        }// End while

        while(token.equals("id")||token.equals("while")||token.equals("if")||token.equals(";")||token.equals("readln")||token.equals("write") || token.equals("writeln")){
            procedure_Comando();
        }// End while

        casaToken("EOF");
    }// End procedure_S()

    public void procedure_Declarar(){
        if(this.token.equals("int")){
            casaToken("int");      
        }else if(this.token.equals("boolean")){
            casaToken("boolean");
        }else if(this.token.equals("byte")){
            casaToken("byte");
        }else if(this.token.equals("string")){
            casaToken("string");
        }// End else if
        casaToken("id");
        procedure_ListaIds();
        casaToken(";");
    }// End procedure_Declarar()

    public void procedure_ListaIds(){
        while(this.token.equals(",")){
            casaToken(",");
            casaToken("id");
            procedure_Atrib();
        }// End while
    }// End procedure_ListaIds()

    public void procedure_Atrib(){
        if(this.token.equals("<-")){
            casaToken("<-");
            if(this.token.equals("-")) casaToken("-");
            casaToken("const");
        }// End if
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
        }else if(this.token.equals("write") || this.token.equals("writeln")){
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
            while(token.equals("id")||token.equals("while")||token.equals("if")||token.equals(";")||token.equals("readln")||token.equals("write")){
                procedure_Comando();
            }// End while
            casaToken("endWhile");
        }else{
            procedure_Comando();
        }// End else
    }// End procedure_Blocowhile()

    public void procedure_Teste(){
        casaToken("if");
        casaToken("(");
        procedure_Expressao();
        casaToken(")");
        procedure_Comando();
        procedure_Blocoif();
    }// End procedure_Teste()

    public void procedure_Blocoif(){
        if(this.token.equals("else")){
            casaToken("else");
            procedure_Comando();
        }// End if
    }// End procedure_Blocoif()

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
        }else if(this.token.equals("writeln")){
            casaToken("writeln");
            casaToken("(");
            procedure_ListaExpressoes();
            casaToken(")");
            casaToken(";");
        }// End else if
    }// End procedure_Escrita()

    public void procedure_ListaExpressoes(){
        procedure_Expressao();;

        while(this.token.equals(",")){
            casaToken(",");
            procedure_Expressao();
        }// End while
    }// End procedure_ListaExpressoes()

    public void procedure_Expressao(){
        procedure_Exp();
        if(token.equals("=") || token.equals("!=") || token.equals("<") || token.equals(">") || token.equals("<=") || token.equals(">=")){
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
        }else if(token.equals(">=")){
            casaToken(">=");
        }// End else if
        procedure_Exp();
    }// End procedure_E()

    public void procedure_Exp(){
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
        }else if(this.token.equals("||")){
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
        }else if(this.token.equals("/")){
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
        }else if(this.token.equals("const")){
            casaToken("const");
        }// End else if
    }// End procedure_F()
}// End AnalisadorSintatico()