/**
 * @author Vinicius Silva
 * @version 1.0
 * @since 2020
 */
public class Symbol{
    private String lexema;
    private byte endereco;
    private String token;

    public Symbol(byte endereco,String lexema,String token){
        this.endereco = endereco;
        this.lexema = lexema;
        this.token = token;
    }// End Symbol()

    public Symbol(String lexema,String token){
        this.lexema = lexema;
        this.token = token;
    }// End Symbol()

    public String getLexema(){return this.lexema;}
    public void setLexema(String lexema){this.lexema = lexema;}
    public byte getEndereco(){return this.endereco;}
    public void setEndereco(byte endereco){this.endereco = endereco;}
    public String getToken(){return this.token;}
    public void setToken(String token){this.token = token;}
}// End Symbol