import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.nio.charset.*;
import java.io.InputStream;

/**
 * @author Vinicius Silva
 * @version 1.0
 * @since 2020
 */
public class LC{

    private static AnalisadorLexico analisadorLexico;
    private static AnalisadorSintatico analisadorSintatico;
    private static long readskip = 0;
    private static int line = 1;

    public static void main(String[] args)throws IOException{
        Symbol lexeme = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in,Charset.forName("ISO-8859-1")));
        int i;
        
        FileWriter fileWriter = new FileWriter("source");
        while((i = bufferedReader.read()) != -1){
            //System.out.print((char)i);
            fileWriter.write(""+(char)i);
        }//End while
        fileWriter.close();
        //System.out.println("\n");        

        //System.out.println("\n");
        
        try{
                
                     
            do{                  
                    
                analisadorLexico = new AnalisadorLexico(new FileReader("source"),line,readskip);

                
                               
                lexeme = analisadorLexico.analiseLexica();
                setValues();
                analisadorSintatico = new AnalisadorSintatico(analisadorLexico);

                //Log.printLog(lexeme.getLexema());
                //System.out.println("READER " + analisadorLexico.getReadSkip() + " LEX " + lexeme.getLexema());
                analisadorSintatico.setToken(lexeme.getToken());
                analisadorSintatico.procedure_S();       
                
             }while(!lexeme.getLexema().equals("EOF"));
                new Status(analisadorLexico.getLine() + " linhas compiladas.");
        }catch(IOException e){
            e.printStackTrace();
        }// End catch
    }// End main()

    public static void setValues(){
        line = analisadorLexico.getLine();
        readskip = analisadorLexico.getReadSkip();
    }// End setValues
}// End LC